package com.ternak.sapi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.github.javafaker.Bool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Inseminasi;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.Pkb;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.model.TujuanPemeliharaan;
import com.ternak.sapi.model.Vaksin;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.HewanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.InseminasiRepository;
import com.ternak.sapi.repository.JenisHewanRepository;
import com.ternak.sapi.repository.KandangRepository;
import com.ternak.sapi.repository.KelahiranRepository;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.repository.PkbRepository;
import com.ternak.sapi.repository.RumpunHewanRepository;
import com.ternak.sapi.repository.TujuanPemeliharaanRepository;
import com.ternak.sapi.repository.VaksinRepository;
import com.ternak.sapi.util.AppConstants;

@Service
public class HewanService {
    private HewanRepository hewanRepository = new HewanRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private KandangRepository kandangRepository = new KandangRepository();
    private JenisHewanRepository jenisHewanRepository = new JenisHewanRepository();
    private RumpunHewanRepository rumpunHewanRepository = new RumpunHewanRepository();
    private TujuanPemeliharaanRepository tujuanPemeliharaanRepository = new TujuanPemeliharaanRepository();
    private VaksinRepository vaksinRepository = new VaksinRepository();
    private InseminasiRepository inseminasiRepository = new InseminasiRepository();
    private KelahiranRepository kelahiranRepository = new KelahiranRepository();
    private PkbRepository pkbRepository = new PkbRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(HewanService.class);

    public PagedResponse<Hewan> getAllHewan(int page, int size, String peternakID, String petugasId, String kandangId,
            String userId)
            throws IOException {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        List<Hewan> hewanResponse = new ArrayList<>();

        if (peternakID.equalsIgnoreCase("*")) {
            hewanResponse = hewanRepository.findAll(size);
        } else {
            hewanResponse = hewanRepository.findHewanByPeternak(peternakID, size);
        }

        return new PagedResponse<>(hewanResponse, hewanResponse.size(), "Successfully get data", 200);
    }

    public Hewan createHewan(HewanRequest hewanRequest, String savePath) throws IOException {
        // Validasi ID Hewan
        if (hewanRepository.existsByIdHewan(hewanRequest.getIdHewan())) {
            throw new IllegalArgumentException("ID Hewan sudah terdaftar!");
        }

        // Validasi kode eartag nasional
        if (hewanRepository.existsByKodeEartagNasional(hewanRequest.getKodeEartagNasional())) {
            throw new IllegalArgumentException("Kode Eartag Nasional sudah terdaftar!");
        }

        Hewan hewan = new Hewan();
        Peternak peternakResponse = peternakRepository.findById(hewanRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(hewanRequest.getPetugasId().toString());
        Kandang kandangResponse = kandangRepository.findById(hewanRequest.getIdKandang().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(hewanRequest.getJenisHewanId().toString());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(hewanRequest.getRumpunHewanId().toString());
        TujuanPemeliharaan tujuanPemeliharaanResponse = tujuanPemeliharaanRepository
                .findById(hewanRequest.getIdTujuanPemeliharaan().toString());

        if (peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && kandangResponse.getIdKandang() != null && jenisHewanResponse.getIdJenisHewan() != null
                && rumpunHewanResponse.getIdRumpunHewan() != null
                && tujuanPemeliharaanResponse.getIdTujuanPemeliharaan() != null) {
            hewan.setIdHewan(hewanRequest.getIdHewan());
            hewan.setKodeEartagNasional(hewanRequest.getKodeEartagNasional());
            hewan.setNoKartuTernak(hewanRequest.getNoKartuTernak());
            hewan.setIdIsikhnasTernak(hewanRequest.getIdIsikhnasTernak());
            hewan.setSex(hewanRequest.getSex());
            hewan.setUmur(hewanRequest.getUmur());
            hewan.setTanggalTerdaftar(hewanRequest.getTanggalTerdaftar());
            hewan.setTanggalLahir(hewanRequest.getTanggalLahir());
            hewan.setTempatLahir(hewanRequest.getTempatLahir());
            hewan.setIdentifikasiHewan(hewanRequest.getIdentifikasiHewan());
            hewan.setFile_path(savePath);
            hewan.setPeternak(peternakResponse);
            hewan.setTujuanPemeliharaan(tujuanPemeliharaanResponse);
            hewan.setPetugas(petugasResponse);
            hewan.setKandang(kandangResponse);
            hewan.setJenisHewan(jenisHewanResponse);
            hewan.setRumpunHewan(rumpunHewanResponse);
            return hewanRepository.save(hewan);
        } else {
            return null;
        }
    }

    public DefaultResponse<Hewan> getHewanById(String idHewan) throws IOException {
        // Retrieve Hewan
        Hewan hewanResponse = hewanRepository.findById(idHewan);
        return new DefaultResponse<>(hewanResponse.isValid() ? hewanResponse : null, hewanResponse.isValid() ? 1 : 0,
                "Successfully get data");
    }

    public Hewan updateHewan(String idHewan, HewanRequest hewanRequest, String savePath) throws IOException {

        Peternak peternakResponse = peternakRepository.findById(hewanRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(hewanRequest.getPetugasId().toString());
        Kandang kandangResponse = kandangRepository.findById(hewanRequest.getIdKandang().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(hewanRequest.getJenisHewanId().toString());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(hewanRequest.getRumpunHewanId().toString());
        TujuanPemeliharaan tujuanPemeliharaanResponse = tujuanPemeliharaanRepository
                .findById(hewanRequest.getIdTujuanPemeliharaan().toString());
        Hewan hewanResponse = hewanRepository.findByFotoHewan(idHewan);
        System.out.println("hewan response " + hewanResponse.getFile_path());
        if (hewanResponse != null && peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && kandangResponse.getIdKandang() != null && jenisHewanResponse.getIdJenisHewan() != null
                && rumpunHewanResponse.getIdRumpunHewan() != null && tujuanPemeliharaanResponse != null) {
            Hewan hewan = new Hewan();
            hewan.setKodeEartagNasional(hewanRequest.getKodeEartagNasional());
            hewan.setNoKartuTernak(hewanRequest.getNoKartuTernak());
            hewan.setIdIsikhnasTernak(hewanRequest.getIdIsikhnasTernak());
            hewan.setSex(hewanRequest.getSex());
            hewan.setUmur(hewanRequest.getUmur());
            hewan.setTanggalTerdaftar(hewanRequest.getTanggalTerdaftar());
            hewan.setTanggalLahir(hewanRequest.getTanggalLahir());
            hewan.setTempatLahir(hewanRequest.getTempatLahir());
            hewan.setIdentifikasiHewan(hewanRequest.getIdentifikasiHewan());
            hewan.setPeternak(peternakResponse);
            hewan.setTujuanPemeliharaan(tujuanPemeliharaanResponse);
            hewan.setPetugas(petugasResponse);
            hewan.setKandang(kandangResponse);
            hewan.setJenisHewan(jenisHewanResponse);
            hewan.setRumpunHewan(rumpunHewanResponse);

            if (savePath == null || savePath.isEmpty()) {
                hewan.setFile_path(hewanResponse.getFile_path() == null ? "" : hewanResponse.getFile_path());
            } else {
                hewan.setFile_path(savePath);
            }

            List<Vaksin> vaksinList = vaksinRepository.findByHewanId(idHewan);
            if (vaksinList != null) {
                for (Vaksin vaksin : vaksinList) {
                    vaksin.setHewan(hewan);
                    vaksinRepository.updateHewanByVaksin(vaksin.getIdVaksin(), vaksin);
                }
            }

            List<Inseminasi> inseminasiList = inseminasiRepository.findByHewanId(idHewan);
            if (inseminasiList != null) {
                for (Inseminasi inseminasi : inseminasiList) {
                    inseminasi.setHewan(hewan);
                    inseminasiRepository.updateHewanByInseminasi(inseminasi.getIdInseminasi(), inseminasi);
                }
            }

            List<Kelahiran> kelahiranList = kelahiranRepository.findByHewanId(idHewan);
            if (kelahiranList != null) {
                for (Kelahiran kelahiran : kelahiranList) {
                    kelahiran.setHewan(hewan);
                    kelahiranRepository.updateHewanByKelahiran(kelahiran.getIdKelahiran(), kelahiran);
                }
            }

            List<Pkb> pkbList = pkbRepository.findByHewanId(idHewan);
            if (pkbList != null) {
                for (Pkb pkb : pkbList) {
                    pkb.setHewan(hewan);
                    pkbRepository.updateHewanByPkb(pkb.getIdPkb(), pkb);
                }
            }

            return hewanRepository.update(idHewan, hewan);
        } else {
            return null;
        }
    }

    public void deleteHewanById(String idHewan) throws IOException {
        hewanRepository.deleteById(idHewan);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    @Transactional
    public void createBulkHewan(List<HewanRequest> hewanRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data Ternak Hewan secara bulk...");

        List<Hewan> hewanList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>(); // Set untuk melacak idHewan yang sudah diproses
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (HewanRequest request : hewanRequests) {
            try {
                // Cek apakah idHewan sudah ada di Set
                if (existingIds.contains(request.getIdHewan())) {
                    System.err.println("Duplicate idHewan ditemukan, data di-skip: " + request.getIdHewan());
                    skippedExisting++;
                    continue; // Lewati iterasi jika idHewan duplikat
                }

                Peternak peternakResponse = peternakRepository.findByNikPeternak(request.getNikPeternak());
                if (peternakResponse == null) {
                    System.out.println("Peternak dengan NIK: " + request.getNikPeternak()
                            + " tidak ditemukan. Membuat default peternak.");
                    peternakResponse = new Peternak();
                    peternakResponse.setNikPeternak(request.getNikPeternak());
                    peternakResponse.setNamaPeternak("Peternak Tidak Diketahui");
                }

                Petugas petugasResponse = petugasRepository.findByNik(request.getNikPetugas());
                if (petugasResponse == null) {
                    System.out.println("Petugas dengan NIK: " + request.getNikPetugas()
                            + " tidak ditemukan. Membuat default petugas.");
                    petugasResponse = new Petugas();
                    petugasResponse.setNikPetugas(request.getNikPetugas());
                    petugasResponse.setNamaPetugas("Petugas Tidak Diketahui");
                }

                System.out.println("Jenis Hewan diterima dari frontend: " + request.getJenis());

                JenisHewan jenisHewanResponse = jenisHewanRepository.findByJenis(request.getJenis());
                if (jenisHewanResponse == null) {
                    System.out.println("Jenis Hewan tidak ditemukan: " + request.getJenis());
                    jenisHewanResponse = new JenisHewan();
                    jenisHewanResponse.setJenis("Jenis Hewan Tidak Diketahui");
                }

                Kandang kandangResponse = kandangRepository.findByNamaKandang(request.getNamaKandang());
                if (kandangResponse == null) {
                    System.out.println("Nama Kandang Tidak ditemukan");
                    kandangResponse = new Kandang();
                    kandangResponse.setNamaKandang("Nama Kandang Tidak Diketahui");
                }

                System.out.println("Rumpun Hewan diterima dari frontend: " + request.getRumpun());
                RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findByRumpun(request.getRumpun());
                if (rumpunHewanResponse == null) {
                    System.out.println("Rumpun Hewan Tidak Ditemukan");
                    rumpunHewanResponse = new RumpunHewan();
                    rumpunHewanResponse.setRumpun("Rumpun Hewan Tidak Diketahui");
                }

                TujuanPemeliharaan tujuanPemeliharaan = tujuanPemeliharaanRepository
                        .findByTujuan(request.getTujuanPemeliharaan());
                if (tujuanPemeliharaan == null) {
                    System.out.println("Tujuan Pemeliharaan Tidak Ditemukan");
                    tujuanPemeliharaan = new TujuanPemeliharaan();
                    tujuanPemeliharaan.setTujuanPemeliharaan("Tujuan Pemeliharaan Tidak Diketahui");
                }

                // Buat objek Hewan
                Hewan hewan = new Hewan();
                hewan.setPetugas(petugasResponse);
                hewan.setPeternak(peternakResponse);
                hewan.setJenisHewan(jenisHewanResponse);
                hewan.setKandang(kandangResponse);
                hewan.setRumpunHewan(rumpunHewanResponse);
                hewan.setTujuanPemeliharaan(tujuanPemeliharaan);
                hewan.setIdHewan(request.getIdHewan());
                hewan.setIdIsikhnasTernak(request.getIdIsikhnasTernak());
                hewan.setNoKartuTernak(request.getNoKartuTernak());
                hewan.setKodeEartagNasional(request.getKodeEartagNasional());
                hewan.setSex(request.getSex());
                hewan.setUmur(request.getUmur());
                hewan.setIdentifikasiHewan(request.getIdentifikasiHewan());
                hewan.setTanggalLahir(request.getTanggalLahir());
                hewan.setTempatLahir(request.getTempatLahir());
                hewan.setTanggalTerdaftar(request.getTanggalTerdaftar());

                hewanList.add(hewan);

                System.out.println("Menambahkan data hewan ke dalam daftar: " + hewan.getIdHewan());

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data hewan: " + request.getIdHewan());
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        if (!hewanList.isEmpty()) {
            System.out.println("Menyimpan data hewan yang valid...");
            hewanRepository.saveAll(hewanList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + hewanList.size());
        } else {
            System.out.println("Tidak ada data hewan baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);
    }

    @Transactional
    public void createBulkHewanImport(List<HewanRequest> hewanRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data Ternak Hewan secara bulk...");

        List<Hewan> hewanList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>(); // Set untuk melacak idHewan yang sudah diproses
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (HewanRequest request : hewanRequests) {
            try {
                // Cek apakah idHewan sudah ada di Set
                if (existingIds.contains(request.getIdHewan())) {
                    System.err.println("Duplicate idHewan ditemukan, data di-skip: " + request.getIdHewan());
                    skippedExisting++;
                    continue; // Lewati iterasi jika idHewan duplikat
                }

                Petugas petugasResponse = petugasRepository.findByNamaPetugas(request.getNamaPetugas());
                if (petugasResponse == null) {
                    System.out.println("Petugas dengan Nama: " + request.getNamaPetugas()
                            + " tidak ditemukan");
                    skippedIncomplete++;
                    continue;
                }

                Peternak peternakResponse = peternakRepository.findByNamaPeternak(request.getNamaPeternak());
                if (peternakResponse == null) {
                    System.out.println("Peternak dengan Nama: " + request.getNamaPeternak()
                            + " tidak ditemukan");
                    skippedIncomplete++;
                    continue;
                }

                System.out.println("id Kandang diterima dari frontend: " + request.getIdKandang());

                Kandang kandangResponse = kandangRepository.findByIdKandang(request.getIdKandang());

                System.out.println("Jenis Hewan diterima dari frontend: " + request.getJenis());

                JenisHewan jenisHewanResponse = jenisHewanRepository.findByJenis(request.getJenis());

                System.out.println("Rumpun Hewan diterima dari frontend: " + request.getRumpun());
                RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findByRumpun(request.getRumpun());

                // Buat objek Hewan
                Hewan hewan = new Hewan();
                hewan.setPetugas(petugasResponse);
                hewan.setPeternak(peternakResponse);
                hewan.setKandang(kandangResponse);
                hewan.setJenisHewan(jenisHewanResponse);
                hewan.setRumpunHewan(rumpunHewanResponse);
                hewan.setIdHewan(request.getIdHewan());
                hewan.setIdIsikhnasTernak(request.getIdIsikhnasTernak());
                hewan.setKodeEartagNasional(request.getKodeEartagNasional());
                hewan.setNoKartuTernak(request.getNoKartuTernak());
                hewan.setSex(request.getSex());
                hewan.setUmur(request.getUmur());
                hewan.setIdentifikasiHewan(request.getIdentifikasiHewan());
                hewan.setTanggalLahir(request.getTanggalLahir());
                hewan.setTempatLahir(request.getTempatLahir());
                hewan.setTanggalTerdaftar(request.getTanggalTerdaftar());

                hewanList.add(hewan);

                System.out.println("Menambahkan data hewan ke dalam daftar: " + hewan.getIdHewan());

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data hewan: " + request.getIdHewan());
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        if (!hewanList.isEmpty()) {
            System.out.println("Menyimpan data hewan yang valid...");
            hewanRepository.saveBulkImport(hewanList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + hewanList.size());
        } else {
            System.out.println("Tidak ada data hewan baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);
    }

    @Transactional
    public void createHewanImport(List<HewanRequest> hewanRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data Ternak Hewan secara bulk...");

        List<Hewan> hewanList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>(); // Set untuk melacak idHewan yang sudah diproses
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (HewanRequest request : hewanRequests) {
            try {
                // Cek apakah idHewan sudah ada di Set
                if (existingIds.contains(request.getIdHewan())) {
                    System.err.println("Duplicate idHewan ditemukan, data di-skip: " + request.getIdHewan());
                    skippedExisting++;
                    continue; // Lewati iterasi jika idHewan duplikat
                }

                JenisHewan jenisHewanResponse = null;
                if (request.getJenis() == null || request.getJenis().trim().isEmpty()) {
                    System.out.println("Jenis Hewan tidak ditemukan. Menambahkan jenis hewan baru...");
                    JenisHewan defaultJenisHewan = new JenisHewan();
                    defaultJenisHewan.setIdJenisHewan(UUID.randomUUID().toString());
                    defaultJenisHewan.setJenis("Jenis hewan tidak ditemukan waktu import hewan");
                    defaultJenisHewan.setDeskripsi("Jenis hewan tidak ditemukan waktu import hewan");

                    // // Save Jenis Hewan Baru
                    jenisHewanResponse = jenisHewanRepository.save(defaultJenisHewan);
                    System.out.println("Jenis Hewan baru ditambahkan: " + defaultJenisHewan.getIdJenisHewan());
                } else {
                    jenisHewanResponse = jenisHewanRepository.findByJenis(request.getJenis());
                    if (jenisHewanResponse == null) {
                        JenisHewan newJenisHewan = new JenisHewan();
                        newJenisHewan.setIdJenisHewan(request.getJenisHewanId() != null ? request.getJenisHewanId()
                                : UUID.randomUUID().toString());
                        newJenisHewan.setJenis(request.getJenis() != null ? request.getJenis()
                                : "Jenis hewan tidak ditemukan waktu import hewan");
                        newJenisHewan.setDeskripsi(
                                request.getDeskripsiJenisHewan() != null ? request.getDeskripsiJenisHewan() : "-");

                        // // Save Jenis Hewan Baru
                        jenisHewanResponse = jenisHewanRepository.save(newJenisHewan);
                        System.out.println("Jenis Hewan baru ditambahkan: " + newJenisHewan.getIdJenisHewan());
                    }
                }

                Petugas petugasResponse = null;
                if (request.getNamaPetugas() == null || request.getNamaPetugas().trim().isEmpty()) {
                    System.out.println("Nama Petugas kosong. Data ini tidak akan diproses.");
                    continue;
                } else {
                    petugasResponse = petugasRepository.findByNamaPetugas(request.getNamaPetugas());
                    if (petugasResponse == null) {
                        System.out.println("Nama Petugas tidak ditemukan di database. Membuat petugas baru...");

                        Petugas newPetugas = new Petugas();
                        newPetugas
                                .setPetugasId(request.getPetugasId() != null ? request.getPetugasId()
                                        : UUID.randomUUID().toString());
                        newPetugas
                                .setNikPetugas(request.getNikPetugas() != null ? request.getNikPetugas()
                                        : "nik belum dimasukkan");
                        newPetugas.setNamaPetugas(request.getNamaPetugas() != null ? request.getNamaPetugas()
                                : "nama petugas tidak ditemukan waktu import hewan");
                        newPetugas.setEmail(
                                request.getEmailPetugas() != null ? request.getEmailPetugas() : "-");
                        newPetugas.setNoTelp(
                                request.getNoTeleponPetugas() != null ? request.getNoTeleponPetugas() : "-");
                        newPetugas.setJob(request.getJob() != null ? request.getJob() : "Pendataan");
                        newPetugas.setWilayah(request.getWilayah() != null ? request.getWilayah() : "-");

                        petugasResponse = petugasRepository.saveImport(newPetugas);

                        System.out.println("Petugas baru berhasil dibuat: " + newPetugas.getNamaPetugas());
                    } else {
                        System.out.println("Petugas ditemukan di database: " + petugasResponse.getNamaPetugas());
                    }
                }

                Peternak peternakResponse = null;

                if (request.getNikPeternak() == null || request.getNikPeternak().trim().isEmpty()) {
                    System.out.println("Nik Peternak kosong. Menggunakan peternak default: 'Peternak Tidak Valid'.");
                    peternakResponse = peternakRepository.findByNikPeternak("Peternak Tidak Valid");

                    if (peternakResponse == null) {
                        System.out.println(
                                "Peternak default tidak ditemukan. Menambahkan peternak default ke database...");
                        Peternak defaultPeternak = new Peternak();
                        defaultPeternak.setIdPeternak(UUID.randomUUID().toString());
                        defaultPeternak.setNikPeternak("Peternak Tidak Valid");
                        defaultPeternak.setNamaPeternak("Peternak Tidak Valid");
                        defaultPeternak.setAlamat("Alamat Peternak Tidak Valid");
                        defaultPeternak.setProvinsi("Provinsi Tidak Valid");
                        defaultPeternak.setKabupaten("Kabupaten Tidak Valid");
                        defaultPeternak.setKecamatan("Kecamatan Tidak Valid");
                        defaultPeternak.setDesa("Desa Tidak Valid");
                        defaultPeternak.setDusun("Dusun Tidak Valid");
                        defaultPeternak.setEmail("Email Tidak Valid");
                        defaultPeternak.setNoTelepon("08123456789");
                        defaultPeternak.setPetugasId(petugasResponse.getPetugasId());
                        defaultPeternak.setPetugas(petugasResponse);
                        peternakResponse = peternakRepository.save(defaultPeternak);

                        System.out
                                .println("Peternak default berhasil ditambahkan: " + defaultPeternak.getNikPeternak());
                    }
                } else {
                    System.out.println("Mencoba menemukan peternak dengan NIK: " + request.getNikPeternak());
                    peternakResponse = peternakRepository.findByNikPeternak(request.getNikPeternak());

                    if (peternakResponse == null) {
                        // Jika peternak tidak ditemukan, tambahkan peternak baru
                        System.out.println("Peternak tidak ditemukan. Menambahkan peternak baru...");
                        Peternak newPeternak = new Peternak();

                        newPeternak.setIdPeternak(UUID.randomUUID().toString());
                        newPeternak.setNikPeternak(request.getNikPeternak());
                        newPeternak.setNamaPeternak(
                                request.getNamaPeternak() != null ? request.getNamaPeternak() : "Nama Tidak Diketahui");
                        newPeternak.setLokasi(
                                request.getLokasi() != null ? request.getLokasi() : "Lokasi Tidak Diketahui");
                        newPeternak.setTanggalPendaftaran(
                                request.getTanggalPendaftaran() != null ? request.getTanggalPendaftaran()
                                        : "Tanggal Pendaftaran Tidak Diketahui");

                        newPeternak
                                .setNoTelepon(request.getNoTelepon() != null ? request.getNoTelepon() : "08123456789");
                        newPeternak.setEmail(
                                request.getEmail() != null ? request.getEmail() : "Email Tidak Diketahui");
                        newPeternak.setAlamat(
                                request.getAlamat() != null ? request.getAlamat() : "Alamat Tidak Diketahui");
                        newPeternak.setJenisKelamin(
                                request.getJenisKelamin() != null ? request.getJenisKelamin() : "Lainnya");
                        newPeternak.setTanggalLahir(
                                request.getTanggalLahir() != null ? request.getTanggalLahir()
                                        : "Tanggal Lahir Tidak Diketahui");
                        newPeternak.setIdIsikhnas(
                                request.getIdIsikhnas() != null ? request.getIdIsikhnas()
                                        : "Id Isikhnas Tidak Diketahui");
                        newPeternak.setProvinsi(
                                request.getProvinsi() != null ? request.getProvinsi() : "Provinsi Tidak Diketahui");
                        newPeternak.setDusun(request.getDusun() != null ? request.getDusun() : "Dusun Tidak Diketahui");
                        newPeternak.setDesa(request.getDesa() != null ? request.getDesa() : "Desa Tidak Diketahui");
                        newPeternak.setKecamatan(
                                request.getKecamatan() != null ? request.getKecamatan() : "Kecamatan Tidak Diketahui");
                        newPeternak.setKabupaten(
                                request.getKabupaten() != null ? request.getKabupaten() : "Kabupaten Tidak Diketahui");
                        newPeternak.setLatitude(
                                request.getLatitude() != null ? request.getLatitude()
                                        : "Latitude Tidak Diketahui");
                        newPeternak.setLongitude(
                                request.getLongitude() != null ? request.getLongitude() : "Longitude Tidak Diketahui");
                        newPeternak.setPetugasId(petugasResponse.getPetugasId());
                        newPeternak.setPetugas(petugasResponse);
                        peternakResponse = peternakRepository.saveByNamaPeternak(newPeternak);

                        System.out.println("Peternak baru ditambahkan: " + newPeternak.getNikPeternak());
                    } else {
                        System.out.println("Peternak ditemukan di database: " + peternakResponse.getNikPeternak());
                    }
                }

                Kandang kandangResponse = null;
                if (request.getNamaPeternak() == null || request.getNamaPeternak().trim().isEmpty()) {
                    System.out.println("Nama Kandang kosong. Data ini tidak akan diproses.");
                    continue;
                } else {
                    kandangResponse = kandangRepository
                            .findByNamaKandang("Kandang " + jenisHewanResponse.getJenis() + " "
                                    + peternakResponse.getNamaPeternak());
                    if (kandangResponse == null) {
                        // Jika nama kandang tidak ditemukan, tambahkan petugas baru berdasarkan nama
                        // dari frontend
                        System.out.println("Nama Kandang tidak ditemukan di database. Membuat kandang baru...");

                        Kandang newKandang = new Kandang();
                        newKandang.setIdKandang(request.getIdKandang() != null ? request.getIdKandang()
                                : UUID.randomUUID().toString());
                        if (jenisHewanResponse.getJenis() != null && peternakResponse.getNamaPeternak() != null) {
                            newKandang.setNamaKandang("Kandang " + jenisHewanResponse.getJenis() + " "
                                    + peternakResponse.getNamaPeternak());
                        } else if (jenisHewanResponse.getJenis() == null) {
                            newKandang.setNamaKandang("Kandang hewan umum" + peternakResponse.getNamaPeternak());
                        } else if (peternakResponse.getNamaPeternak() == null) {
                            newKandang.setNamaKandang(
                                    "Kandang " + jenisHewanResponse.getJenis() + "nya peternak tidak dikenal");
                        } else {
                            newKandang.setNamaKandang("Nama Kandang tidak ketahui waktu import hewan");
                        }
                        newKandang.setPeternak(peternakResponse);
                        newKandang.setJenisHewan(jenisHewanResponse);
                        newKandang.setAlamat(request.getAlamat() != null ? request.getAlamat() : "-");
                        newKandang.setLuas(request.getLuas() != null ? request.getLuas() : "-");
                        newKandang.setKapasitas(request.getKapasitas() != null ? request.getKapasitas() : "-");
                        newKandang.setJenisKandang(request.getJenisKandang() != null ? request.getJenisKandang() : "-");
                        newKandang.setNilaiBangunan(
                                request.getNilaiBangunan() != null ? request.getNilaiBangunan() : "-");
                        newKandang.setLongitude(request.getLongitude() != null ? request.getLongitude() : "-");
                        newKandang.setLatitude(request.getLatitude() != null ? request.getLatitude() : "-");
                        kandangResponse = kandangRepository.saveImportByHewan(newKandang);
                        System.out.println("Pemilik Kandang : " + peternakResponse.getNamaPeternak());
                        System.out.println("Kandang baru berhasil dibuat: " + newKandang.getNamaKandang());
                    } else {
                        System.out.println("Kandang ditemukan di database: " + kandangResponse.getNamaKandang());
                    }
                }

                RumpunHewan rumpunResponse = null;
                Boolean requestRumpun = request.getJenis() != null && request.getJenis().toLowerCase().contains("sapi");
                if (requestRumpun) {
                    rumpunResponse = rumpunHewanRepository.findByRumpun(request.getJenis());
                    if (rumpunResponse == null) {
                        RumpunHewan defaultRumpunHewan = new RumpunHewan();
                        defaultRumpunHewan
                                .setIdRumpunHewan(request.getRumpunHewanId() != null ? request.getRumpunHewanId()
                                        : UUID.randomUUID().toString());
                        defaultRumpunHewan.setRumpun(request.getJenis());
                        defaultRumpunHewan.setDeskripsi("Rumpun hewan ternak " + request.getJenis());
                        rumpunResponse = rumpunHewanRepository.save(defaultRumpunHewan);

                        System.out.println("Rumpun baru berhasil dibuat: " + defaultRumpunHewan.getRumpun());
                    } else {
                        System.out.println("Rumpun hewan ditemukan didatabase");
                    }
                } else {
                    rumpunResponse = rumpunHewanRepository.findByRumpun(request.getRumpun());
                    if (rumpunResponse == null) {
                        RumpunHewan defaultRumpunHewan = new RumpunHewan();
                        defaultRumpunHewan
                                .setIdRumpunHewan(request.getRumpunHewanId() != null ? request.getRumpunHewanId()
                                        : UUID.randomUUID().toString());
                        defaultRumpunHewan.setRumpun(request.getRumpun() != null ? request.getRumpun()
                                : "Nama rumpun hewan tidak ditemukan waktu import hewan");
                        defaultRumpunHewan
                                .setDeskripsi(request.getDeskripsiRumpun() != null ? request.getDeskripsiRumpun()
                                        : "Deskripsi Rumpun hewan tidak ditemukan waktu import hewan");

                        rumpunResponse = rumpunHewanRepository.save(defaultRumpunHewan);
                    } else {
                        System.out.println("Rumpun hewan ditemukan didatabase");
                    }
                }

                TujuanPemeliharaan tujuanResponse = tujuanPemeliharaanRepository
                        .findByTujuan(request.getTujuanPemeliharaan());
                if (tujuanResponse == null) {
                    TujuanPemeliharaan defaultTujuan = new TujuanPemeliharaan();
                    defaultTujuan.setIdTujuanPemeliharaan(
                            request.getIdTujuanPemeliharaan() != null ? request.getIdTujuanPemeliharaan()
                                    : UUID.randomUUID().toString());
                    defaultTujuan.setTujuanPemeliharaan(
                            request.getTujuanPemeliharaan() != null ? request.getTujuanPemeliharaan()
                                    : "Tujuan pemeliharaan tidak ditemukan waktu import hewan");
                    defaultTujuan.setDeskripsi(request.getDeskripsiTujuanPemeliharaan() != null
                            ? "Tujuan pemeliharaan ini adalah " + request.getTujuanPemeliharaan()
                            : "Deskripsi tujuan pemeliharaan tidak ditemukan waktu import hewan");

                    tujuanResponse = tujuanPemeliharaanRepository.saveImport(defaultTujuan);
                    System.out.println("Tujuan baru berhasil dibuat: " + defaultTujuan.getTujuanPemeliharaan());
                } else {
                    System.out.println("Tujuan Pemeliharaan ditemukan di database");
                }

                // Buat objek Hewan
                Hewan hewan = new Hewan();
                hewan.setIdHewan(request.getIdHewan());
                hewan.setIdIsikhnasTernak(request.getIdIsikhnasTernak());
                hewan.setNoKartuTernak(request.getNoKartuTernak());
                hewan.setKodeEartagNasional(request.getKodeEartagNasional());
                hewan.setSex(request.getSex());
                hewan.setUmur(request.getUmur());
                hewan.setTanggalTerdaftar(request.getTanggalTerdaftar());
                hewan.setIdentifikasiHewan(request.getIdentifikasiHewan());
                hewan.setTanggalLahir(request.getTanggalLahir());
                hewan.setTempatLahir(request.getTempatLahir());
                hewan.setPetugasId(petugasResponse.getPetugasId());

                // Set relasi ke objek Hewan
                hewan.setPeternak(peternakResponse);
                hewan.setJenisHewan(jenisHewanResponse);
                hewan.setPetugas(petugasResponse);
                hewan.setKandang(kandangResponse);
                hewan.setRumpunHewan(rumpunResponse);
                hewan.setTujuanPemeliharaan(tujuanResponse);

                // Tambahkan idHewan ke Set dan Hewan ke List
                existingIds.add(request.getIdHewan());
                hewanList.add(hewan);

                System.out.println("Menambahkan data hewan ke dalam daftar: " + hewan.getIdHewan());

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data hewan: " + request.getIdHewan());
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        if (!hewanList.isEmpty()) {
            System.out.println("Menyimpan data hewan yang valid...");
            hewanRepository.saveImport(hewanList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + hewanList.size());
        } else {
            System.out.println("Tidak ada data hewan baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);
    }

}
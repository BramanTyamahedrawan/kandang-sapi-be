package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.*;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.HewanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.*;
import com.ternak.sapi.util.AppConstants;
// import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Service;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.model.TujuanPemeliharaan;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.JenisHewanRepository;
import com.ternak.sapi.repository.KandangRepository;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.repository.RumpunHewanRepository;
import com.ternak.sapi.repository.TujuanPemeliharaanRepository;

@Service
public class HewanService {
    private HewanRepository hewanRepository = new HewanRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private KandangRepository kandangRepository = new KandangRepository();
    private JenisHewanRepository jenisHewanRepository = new JenisHewanRepository();
    private RumpunHewanRepository rumpunHewanRepository = new RumpunHewanRepository();
    private TujuanPemeliharaanRepository tujuanPemeliharaanRepository = new TujuanPemeliharaanRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(HewanService.class);

    public PagedResponse<Hewan> getAllHewan(int page, int size, String peternakID, String petugasId, String kandangId)
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
        Petugas petugasResponse = petugasRepository.findById(hewanRequest.getPetugas_id().toString());
        Kandang kandangResponse = kandangRepository.findById(hewanRequest.getIdKandang().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(hewanRequest.getJenisHewanId().toString());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(hewanRequest.getRumpunHewanId().toString());

        if (peternakResponse.getNamaPeternak() != null && petugasResponse.getNamaPetugas() != null
                && kandangResponse.getAlamat() != null && jenisHewanResponse.getJenis() != null
                && rumpunHewanResponse.getRumpun() != null) {
            hewan.setIdHewan(hewanRequest.getIdHewan());
            hewan.setKodeEartagNasional(hewanRequest.getKodeEartagNasional());
            hewan.setSex(hewanRequest.getSex());
            hewan.setUmur(hewanRequest.getUmur());
            hewan.setTanggalTerdaftar(hewanRequest.getTanggalTerdaftar());
            hewan.setLatitude(hewanRequest.getLatitude());
            hewan.setLongitude(hewanRequest.getLongitude());
            hewan.setTanggalLahir(hewanRequest.getTanggalLahir());
            hewan.setTempatLahir(hewanRequest.getTempatLahir());
            // hewan.setTujuanPemeliharaan(hewanRequest.getTujuanPemeliharaan());
            hewan.setFile_path(savePath);
            hewan.setIdentifikasiHewan(hewanRequest.getIdentifikasiHewan());

            hewan.setPeternak(peternakResponse);
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
        Hewan hewan = new Hewan();
        Peternak peternakResponse = peternakRepository.findById(hewanRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(hewanRequest.getPetugas_id().toString());
        Kandang kandangResponse = kandangRepository.findById(hewanRequest.getIdKandang().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(hewanRequest.getJenisHewanId().toString());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(hewanRequest.getRumpunHewanId().toString());

        if (peternakResponse.getNamaPeternak() != null && petugasResponse.getNamaPetugas() != null
                && kandangResponse.getAlamat() != null && jenisHewanResponse.getJenis() != null
                && rumpunHewanResponse.getRumpun() != null) {
            hewan.setIdHewan(hewanRequest.getIdHewan());
            hewan.setKodeEartagNasional(hewanRequest.getKodeEartagNasional());
            hewan.setSex(hewanRequest.getSex());
            hewan.setUmur(hewanRequest.getUmur());
            hewan.setTanggalTerdaftar(hewanRequest.getTanggalTerdaftar());
            hewan.setLatitude(hewanRequest.getLatitude());
            hewan.setLongitude(hewanRequest.getLongitude());
            hewan.setTanggalLahir(hewanRequest.getTanggalLahir());
            hewan.setTempatLahir(hewanRequest.getTempatLahir());
            // hewan.setTujuanPemeliharaan(hewanRequest.getTujuanPemeliharaan());
            hewan.setFile_path(savePath);
            hewan.setIdentifikasiHewan(hewanRequest.getIdentifikasiHewan());

            hewan.setPeternak(peternakResponse);
            hewan.setPetugas(petugasResponse);
            hewan.setKandang(kandangResponse);
            hewan.setJenisHewan(jenisHewanResponse);
            hewan.setRumpunHewan(rumpunHewanResponse);
            return hewanRepository.update(idHewan, hewan);
        } else {
            return null;
        }
    }

    public void deleteHewanById(String idHewan) throws IOException {
        Hewan hewanResponse = hewanRepository.findById(idHewan);
        if (hewanResponse.isValid()) {
            hewanRepository.deleteById(idHewan);
        } else {
            throw new ResourceNotFoundException("Hewan", "id", idHewan);
        }
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

                // Buat objek Hewan
                Hewan hewan = new Hewan();
                hewan.setIdHewan(request.getIdHewan());
                hewan.setNoKartuTernak(request.getNoKartuTernak());
                hewan.setKodeEartagNasional(request.getKodeEartagNasional());
                hewan.setSex(request.getSex());
                hewan.setUmur(request.getUmur());
                hewan.setTanggalTerdaftar(request.getTanggalTerdaftar());
                hewan.setIdentifikasiHewan(request.getIdentifikasiHewan());
                hewan.setTanggalLahir(request.getTanggalLahir());
                hewan.setTempatLahir(request.getTempatLahir());

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
                        // defaultPeternak.setProvinsi("Provinsi Tidak Valid");
                        defaultPeternak.setKabupaten("Kabupaten Tidak Valid");
                        defaultPeternak.setKecamatan("Kecamatan Tidak Valid");
                        defaultPeternak.setDesa("Desa Tidak Valid");
                        defaultPeternak.setDusun("Dusun Tidak Valid");
                        defaultPeternak.setEmail("Email Tidak Valid");
                        defaultPeternak.setNoTelepon("08123456789");
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
                        // newPeternak.setProvinsi(
                        // request.getProvinsi() != null ? request.getProvinsi() : "Provinsi Tidak
                        // Diketahui");

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
                            .findByNamaKandang("Kandang " + peternakResponse.getNamaPeternak());
                    if (kandangResponse == null) {
                        // Jika nama kandang tidak ditemukan, tambahkan petugas baru berdasarkan nama
                        // dari frontend
                        System.out.println("Nama Kandang tidak ditemukan di database. Membuat kandang baru...");

                        Kandang newKandang = new Kandang();
                        newKandang.setIdKandang(request.getIdKandang() != null ? request.getIdKandang()
                                : UUID.randomUUID().toString());
                        newKandang.setNamaKandang(peternakResponse.getNamaPeternak() != null
                                ? "Kandang " + peternakResponse.getNamaPeternak()
                                : "Nama Kandang tidak ditemukan");
                        newKandang.setLongitude(request.getLongitude());
                        newKandang.setLatitude(request.getLatitude());
                        newKandang.setPeternak(peternakResponse);
                        newKandang.setAlamat(request.getAlamat());

                        kandangResponse = kandangRepository.saveImportByHewan(newKandang);
                        System.out.println("Pemilik Kandang : " + peternakResponse.getNamaPeternak());
                        System.out.println("Kandang baru berhasil dibuat: " + newKandang.getNamaKandang());
                    } else {
                        System.out.println("Kandang ditemukan di database: " + kandangResponse.getNamaKandang());
                    }
                }

                RumpunHewan rumpunResponse = null;

                if (request.getRumpun() != null) {
                    if (request.getJenis() != null && request.getJenis().toLowerCase().contains("sapi")) {
                        RumpunHewan defaultRumpunHewan = new RumpunHewan();
                        defaultRumpunHewan
                                .setIdRumpunHewan(request.getRumpunHewanId() != null ? request.getRumpunHewanId()
                                        : UUID.randomUUID().toString());
                        defaultRumpunHewan.setRumpun(request.getJenis());
                        defaultRumpunHewan.setDeskripsi("Rumpun hewan ternak " + request.getJenis());

                        rumpunResponse = rumpunHewanRepository.save(defaultRumpunHewan);

                        System.out.println("Rumpun baru berhasil dibuat: " + defaultRumpunHewan.getRumpun());
                    } else {
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
                    }
                }

                TujuanPemeliharaan tujuanResponse = null;
                if (request.getTujuanPemeliharaan() != null) {

                    TujuanPemeliharaan defaultTujuan = new TujuanPemeliharaan();
                    defaultTujuan.setIdTujuanPemeliharaan(
                            request.getIdTujuanPemeliharaan() != null ? request.getIdTujuanPemeliharaan()
                                    : UUID.randomUUID().toString());
                    defaultTujuan.setTujuanPemeliharaan(request.getTujuanPemeliharaan());
                    defaultTujuan.setDeskripsi(request.getDeskripsiTujuanPemeliharaan() != null
                            ? "Tujuan pemeliharaan ini adalah " + request.getTujuanPemeliharaan()
                            : "Deskripsi tujuan pemeliharaan tidak ditemukan waktu import hewan");

                    tujuanResponse = tujuanPemeliharaanRepository.saveImport(defaultTujuan);

                    System.out.println("Tujuan baru berhasil dibuat: " + defaultTujuan.getTujuanPemeliharaan());
                }

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
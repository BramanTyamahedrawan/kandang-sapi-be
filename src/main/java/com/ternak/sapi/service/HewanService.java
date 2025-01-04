package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.*;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.HewanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.*;
import com.ternak.sapi.util.AppConstants;
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

@Service
public class HewanService {
    private HewanRepository hewanRepository = new HewanRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private KandangRepository kandangRepository = new KandangRepository();
    private JenisHewanRepository jenisHewanRepository = new JenisHewanRepository();
    private RumpunHewanRepository rumpunHewanRepository = new RumpunHewanRepository();

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
        Peternak peternakResponse = peternakRepository.findById(hewanRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(hewanRequest.getPetugas_id().toString());
        Kandang kandangResponse = kandangRepository.findById(hewanRequest.getKandang_id().toString());
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
            hewan.setTujuanPemeliharaan(hewanRequest.getTujuanPemeliharaan());
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
        Peternak peternakResponse = peternakRepository.findById(hewanRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(hewanRequest.getPetugas_id().toString());
        Kandang kandangResponse = kandangRepository.findById(hewanRequest.getKandang_id().toString());
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
            hewan.setTujuanPemeliharaan(hewanRequest.getTujuanPemeliharaan());
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

                // Buat objek Hewan
                Hewan hewan = new Hewan();
                hewan.setIdHewan(request.getIdHewan());
                hewan.setKodeEartagNasional(request.getKodeEartagNasional());
                hewan.setSex(request.getSex());
                hewan.setLatitude(request.getLatitude());
                hewan.setLongitude(request.getLongitude());
                hewan.setTanggalLahir(request.getTanggalLahir());
                hewan.setTempatLahir(request.getTempatLahir());

                // Fetch relasi data dari repository
                Peternak peternakResponse = peternakRepository.findByNikPeternak(request.getNikPeternak());
                Petugas petugasResponse = petugasRepository.findByNik(request.getNikPetugas());
                Kandang kandangResponse = kandangRepository.findById(request.getKandang_id().toString());
                JenisHewan jenisHewanResponse = jenisHewanRepository.findById(request.getJenisHewanId().toString());
                RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(request.getRumpunHewanId().toString());

                // Set relasi ke objek Hewan
                hewan.setPetugas(petugasResponse);
                hewan.setPeternak(peternakResponse);
                hewan.setKandang(kandangResponse);
                hewan.setJenisHewan(jenisHewanResponse);
                hewan.setRumpunHewan(rumpunHewanResponse);

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
            hewanRepository.saveAll(hewanList);
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

                System.out.println("Nik peternak diterima dari frontend: " + request.getNikPeternak());

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

                        peternakResponse = peternakRepository.saveByNIKPeternak(newPeternak);

                        System.out.println("Peternak baru ditambahkan: " + newPeternak.getNikPeternak());
                    } else {
                        System.out.println("Peternak ditemukan di database: " + peternakResponse.getNikPeternak());
                    }
                }

                // Fetch relasi data dari repository
                System.out.println("Jenis diterima dari frontend: " + request.getJenis());

                JenisHewan jenisHewanResponse = null;

                if (request.getJenis() == null || request.getJenis().trim().isEmpty()) {
                    System.out.println("Jenis Hewan kosong. Menggunakan jenis default: 'Jenis Hewan Tidak Valid'.");

                    // Cari jenis default di database
                    jenisHewanResponse = jenisHewanRepository.findByJenis("Jenis Hewan Tidak Valid");

                    if (jenisHewanResponse == null) {
                        // Jika jenis default tidak ada, tambahkan ke database
                        System.out.println("Jenis default tidak ditemukan. Menambahkan jenis default ke database...");
                        JenisHewan defaultJenisHewan = new JenisHewan();
                        defaultJenisHewan.setIdJenisHewan(UUID.randomUUID().toString());
                        defaultJenisHewan.setJenis("Jenis Hewan Tidak Valid");
                        defaultJenisHewan.setDeskripsi("Deskripsi Jenis Hewan Tidak Valid");
                        jenisHewanResponse = jenisHewanRepository.save(defaultJenisHewan); // Simpan jenis default

                        System.out
                                .println("Jenis default berhasil ditambahkan: " + defaultJenisHewan.getIdJenisHewan());
                    } else {
                        System.out.println(
                                "Jenis default ditemukan di database: " + jenisHewanResponse.getIdJenisHewan());
                    }
                } else {
                    jenisHewanResponse = jenisHewanRepository.findByJenis(request.getJenis());
                    if (jenisHewanResponse == null) {
                        // Jika jenis tidak ditemukan, tambahkan jenis baru
                        System.out.println("Jenis Hewan tidak ditemukan. Menambahkan jenis hewan baru...");
                        JenisHewan newJenisHewan = new JenisHewan();
                        newJenisHewan.setIdJenisHewan(UUID.randomUUID().toString());
                        newJenisHewan.setJenis(request.getJenis());
                        newJenisHewan.setDeskripsi("Deskripsi " + request.getJenis());
                        jenisHewanResponse = jenisHewanRepository.save(newJenisHewan);

                        System.out.println("Jenis Hewan baru ditambahkan: " + newJenisHewan.getIdJenisHewan());
                    } else {
                        System.out
                                .println("Jenis Hewan ditemukan di database: " + jenisHewanResponse.getIdJenisHewan());
                    }
                }

                System.out.println("Petugas diterima dari frontend: " + request.getNamaPetugas());

                Petugas petugasResponse = null;

                if (request.getNamaPetugas() == null || request.getNamaPetugas().trim().isEmpty()) {
                    System.out.println("Nama Petugas kosong. Data ini tidak akan diproses.");
                    continue;
                } else {
                    // Coba cari petugas berdasarkan nama dari frontend
                    petugasResponse = petugasRepository.findByNamaPetugas(request.getNamaPetugas());
                    if (petugasResponse == null) {
                        // Jika nama petugas tidak ditemukan, tambahkan petugas baru berdasarkan nama
                        // dari frontend
                        System.out.println("Nama Petugas tidak ditemukan di database. Membuat petugas baru...");

                        Petugas newPetugas = new Petugas();
                        newPetugas
                                .setNikPetugas(request.getNikPetugas() != null ? request.getNikPetugas()
                                        : "nik belum dimasukkan");
                        newPetugas.setNamaPetugas(request.getNamaPetugas());
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

                // Set relasi ke objek Hewan
                hewan.setPeternak(peternakResponse);
                hewan.setJenisHewan(jenisHewanResponse);
                hewan.setPetugas(petugasResponse);

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
            hewanRepository.saveAll(hewanList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + hewanList.size());
        } else {
            System.out.println("Tidak ada data hewan baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);
    }

}
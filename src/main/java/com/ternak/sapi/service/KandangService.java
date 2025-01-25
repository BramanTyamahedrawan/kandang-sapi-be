package com.ternak.sapi.service;

import java.io.IOException;
import java.util.*;

import com.ternak.sapi.model.*;
import com.ternak.sapi.repository.HewanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.KandangRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.JenisHewanRepository;
import com.ternak.sapi.repository.KandangRepository;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.util.AppConstants;

@Service
public class KandangService {
    private KandangRepository kandangRepository = new KandangRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private JenisHewanRepository jenisHewanRepository = new JenisHewanRepository();
    private HewanRepository hewanRepository = new HewanRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(KandangService.class);

    public PagedResponse<Kandang> getAllKandang(int page, int size, String peternakID) throws IOException {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        List<Kandang> kandangResponse = new ArrayList<>();

        if (peternakID.equalsIgnoreCase("*")) {
            kandangResponse = kandangRepository.findAll(size);
        } else {
            kandangResponse = kandangRepository.findKandangByPeternak(peternakID, size);
        }

        return new PagedResponse<>(kandangResponse, kandangResponse.size(), "Successfully get data", 200);
    }

    public Kandang createKandang(KandangRequest kandangRequest, String savePath) throws IOException {
        // Validasi ID Kandang
        if (kandangRepository.existsByIdKandang(kandangRequest.getIdKandang())) {
            throw new IllegalArgumentException("ID Kandang sudah terdaftar!");
        }

        // Validasi Alamat
        // if (kandangRepository.existsByAlamat(kandangRequest.getAlamat())) {
        // throw new IllegalArgumentException("Alamat Kandang sudah terdaftar!");
        // }

        Kandang kandang = new Kandang();
        Peternak peternakResponse = peternakRepository.findById(kandangRequest.getIdPeternak().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(kandangRequest.getIdJenisHewan());
        if (peternakResponse.getIdPeternak() != null && jenisHewanResponse.getIdJenisHewan() != null) {
            kandang.setIdKandang(kandangRequest.getIdKandang());
            kandang.setLuas(kandangRequest.getLuas());
            kandang.setKapasitas(kandangRequest.getKapasitas());
            kandang.setJenisKandang(kandangRequest.getJenisKandang());
            kandang.setNilaiBangunan(kandangRequest.getNilaiBangunan());
            kandang.setAlamat(kandangRequest.getAlamat());
            kandang.setLatitude(kandangRequest.getLatitude());
            kandang.setLongitude(kandangRequest.getLongitude());
            kandang.setFile_path(savePath);
            kandang.setNamaKandang(kandangRequest.getNamaKandang());
            kandang.setJenisHewan(jenisHewanResponse);
            kandang.setPeternak(peternakResponse);
            return kandangRepository.save(kandang);
        } else {
            return null;
        }
    }

    public DefaultResponse<Kandang> getKandangById(String kandangId) throws IOException {
        // Retrieve Kandang
        Kandang kandangResponse = kandangRepository.findById(kandangId);
        return new DefaultResponse<>(kandangResponse.isValid() ? kandangResponse : null,
                kandangResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Kandang updateKandang(String kandangId, KandangRequest kandangRequest, String savePath) throws IOException {
        Kandang kandangSave = null;
        Peternak peternakResponse = peternakRepository.findById(kandangRequest.getIdPeternak().toString());
        Kandang kandangResponse = kandangRepository.findByFotoKandang(kandangId);
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(kandangRequest.getIdJenisHewan().toString());
        if (kandangResponse != null && peternakResponse != null && peternakResponse.getIdPeternak() != null
                && jenisHewanResponse.getIdJenisHewan() != null) {
            System.out.println("file = " + kandangResponse.getFile_path());

            Kandang kandang = new Kandang();
            kandang.setLuas(kandangRequest.getLuas());
            kandang.setKapasitas(kandangRequest.getKapasitas());
            kandang.setJenisKandang(kandangRequest.getJenisKandang());
            kandang.setNilaiBangunan(kandangRequest.getNilaiBangunan());
            kandang.setAlamat(kandangRequest.getAlamat());
            kandang.setLatitude(kandangRequest.getLatitude());
            kandang.setLongitude(kandangRequest.getLongitude());
            kandang.setNamaKandang(kandangRequest.getNamaKandang());

            if (savePath == null || savePath.isEmpty()) {
                kandang.setFile_path(kandangResponse.getFile_path().isEmpty() ? "" : kandangResponse.getFile_path());
            } else {
                System.out.println("save file " + savePath);
                kandang.setFile_path(savePath);
            }

            kandang.setPeternak(peternakResponse);
            kandang.setJenisHewan(jenisHewanResponse);

            List<Hewan> hewanList = hewanRepository.findByKandangId(kandangId);
            if (hewanList != null) {
                for (Hewan hewan : hewanList) {
                    hewan.setKandang(kandang);
                    hewanRepository.updateKandangByHewan(hewan.getIdHewan(), hewan);
                }
            }

            kandangSave = kandangRepository.update(kandangId, kandang);
        }

        return kandangSave;
    }

    public void deleteKandangById(String kandangId) throws IOException {
        // Kandang kandangResponse = kandangRepository.findById(kandangId);
        // if(kandangResponse.isValid()){
        kandangRepository.deleteById(kandangId);
        // }else{
        // throw new ResourceNotFoundException("Kandang", "id", kandangId);
        // }
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
    public void createBulkKandang(List<KandangRequest> kandangRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data kandang secara bulk...");

        List<Kandang> kandangList = new ArrayList<>();
        int skippedIncomplete = 0;
        int dataTidakLengkap = 0;

        // Set untuk menyimpan nama kandang yang sudah diproses
        Set<String> namaKandangSet = new HashSet<>();

        for (KandangRequest request : kandangRequests) {
            try {
                if (request.getIdKandang() == null || request.getNikPeternak() == null) {
                    System.out.println("Data kandang tidak lengkap: " + request);
                    dataTidakLengkap++;
                    continue;
                }

                // Validasi: Temukan Peternak berdasarkan NIK
                Peternak peternakResponse = peternakRepository.findByNikPeternak(request.getNikPeternak());
                if (peternakResponse == null) {
                    System.out.println("Peternak dengan NIK: " + request.getNikPeternak()
                            + " tidak ditemukan. Membuat default peternak.");
                    peternakResponse = new Peternak();
                    peternakResponse.setNikPeternak(request.getNikPeternak());
                    peternakResponse.setNamaPeternak("Peternak Tidak Diketahui");
                }

                JenisHewan jenisHewanResponse = jenisHewanRepository.findByJenis(request.getJenis());
                if (jenisHewanResponse == null) {
                    jenisHewanResponse = new JenisHewan();
                    jenisHewanResponse.setIdJenisHewan(request.getIdJenisHewan() != null ? request.getIdJenisHewan() : "-");
                    jenisHewanResponse.setJenis(request.getJenis() != null ? request.getJenis() : "Jenis hewan tidak ditemukan");
                    jenisHewanResponse.setDeskripsi("-");
                } else {
                    System.out.println("Jenis hewan ditemukan: ID " + jenisHewanResponse.getIdJenisHewan() + ", Nama: " + jenisHewanResponse.getJenis());
                }

                // Pengecekan jika nama kandang sudah ada di database
                Kandang kandangResponse = kandangRepository.findByNamaKandang(request.getNamaKandang());
                if (kandangResponse != null) {
                    System.out.println("Kandang dengan nama " + request.getNamaKandang() + " sudah ada di database.");
                    continue; // Lewatkan data ini dan tidak perlu disimpan
                }

                // Pengecekan jika nama kandang sudah ada di dalam list sementara
                if (namaKandangSet.contains(request.getNamaKandang())) {
                    System.out.println("Kandang dengan nama " + request.getNamaKandang() + " sudah ada dalam list sementara.");
                    continue; // Lewatkan data ini dan tidak perlu disimpan
                }

                // Jika tidak ada kandang yang sama, buat objek Kandang baru
                Kandang kandang = new Kandang();
                kandang.setPeternak(peternakResponse);
                kandang.setJenisHewan(jenisHewanResponse);
                kandang.setIdKandang(request.getIdKandang());
                kandang.setAlamat(request.getAlamat());
                kandang.setNamaKandang(request.getNamaKandang() != null ? request.getNamaKandang()
                        : "Kandang " + request.getIdKandang());
                kandang.setLuas(request.getLuas());
                kandang.setJenisKandang(request.getJenisKandang() != null ? request.getJenisKandang() : "Permanen");
                kandang.setNilaiBangunan(request.getNilaiBangunan());
                kandang.setLatitude(request.getLatitude());
                kandang.setLongitude(request.getLongitude());
                kandang.setNikPeternak(request.getNikPeternak());
                kandang.setIdJenisHewan(request.getIdJenisHewan());
                kandang.setKapasitas(request.getKapasitas());

                // Menambahkan nama kandang ke dalam set untuk validasi selanjutnya
                namaKandangSet.add(request.getNamaKandang());
                kandangList.add(kandang);

                System.out.println("Menambahkan data kandang ke dalam daftar: " + kandang);

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data: " + request);
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        // Jika ada data kandang yang valid untuk disimpan
        if (!kandangList.isEmpty()) {
            System.out.println("Menyimpan data kandang yang valid...");
            kandangRepository.saveAll(kandangList);
            System.out.println("Data kandang berhasil disimpan. Total: " + kandangList.size());
        } else {
            System.out.println("Tidak ada data kandang baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai.");
        System.out.println("Data tidak lengkap: " + dataTidakLengkap);
        System.out.println("Data yang di-skip karena kesalahan: " + skippedIncomplete);
    }

    @Transactional
    public void createImportKandang(List<KandangRequest> kandangRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data kandang secara bulk...");

        List<Kandang> kandangList = new ArrayList<>();
        int skippedIncomplete = 0;
        int dataTidakLengkap = 0;

        Set<String> namaKandangSet = new HashSet<>();
        Set<String> namaPeternakSet  = new HashSet<>();

        for (KandangRequest request : kandangRequests) {
            try {
                if (request.getIdKandang() == null || request.getNamaPeternak() == null) {
                    System.out.println("Data kandang tidak lengkap: " + request);
                    dataTidakLengkap++;
                    continue;
                }

                Peternak peternakResponse = peternakRepository.findByNamaPeternak(request.getNamaPeternak());
                if (peternakResponse == null) {
                    System.out.println("Peternak dengan Nama: " + request.getNamaPeternak()
                            + " tidak ditemukan. Membuat default peternak.");
                    Peternak newPeternak = new Peternak();
                    newPeternak.setIdPeternak(UUID.randomUUID().toString());
                    newPeternak.setNikPeternak(request.getNikPeternak() != null ? request.getNikPeternak() : "Nik belum dimasukan");
                    newPeternak.setNamaPeternak(
                            request.getNamaPeternak() != null ? request.getNamaPeternak() : "Nama Tidak Diketahui");
                    newPeternak.setLokasi("Lokasi Tidak Diketahui");
                    newPeternak.setTanggalPendaftaran("Tanggal Pendaftaran Tidak Diketahui");
                    newPeternak
                            .setNoTelepon("08123456789");
                    newPeternak.setEmail("Email Tidak Diketahui");
                    newPeternak.setAlamat(
                            request.getAlamat() != null ? request.getAlamat() : "Alamat Tidak Diketahui");
                    newPeternak.setJenisKelamin("Lainnya");
                    newPeternak.setTanggalLahir("Tanggal Lahir Tidak Diketahui");
                    newPeternak.setIdIsikhnas("Id Isikhnas Tidak Diketahui");
                    newPeternak.setProvinsi("Provinsi Tidak Diketahui");
                    newPeternak.setDusun("Dusun Tidak Diketahui");
                    newPeternak.setDesa("Desa Tidak Diketahui");
                    newPeternak.setKecamatan("Kecamatan Tidak Diketahui");
                    newPeternak.setKabupaten("Kabupaten Tidak Diketahui");
                    newPeternak.setLatitude(
                            request.getLatitude() != null ? request.getLatitude()
                                    : "Latitude Tidak Diketahui");
                    newPeternak.setLongitude(
                            request.getLongitude() != null ? request.getLongitude() : "Longitude Tidak Diketahui");
                    peternakResponse = peternakRepository.saveByNamaPeternak(newPeternak);
                }

                if(namaPeternakSet.contains(request.getNamaPeternak().trim().toLowerCase())){
                    System.out.println("Peternak sudah ada dalam list");
                    continue;
                }

                JenisHewan jenisHewanResponse = jenisHewanRepository.findByJenis(request.getJenis());
                if (jenisHewanResponse == null) {
                    JenisHewan newJenisHewan = new JenisHewan();
                    newJenisHewan.setIdJenisHewan(request.getIdJenisHewan() != null ? request.getIdJenisHewan() : UUID.randomUUID().toString());
                    newJenisHewan.setJenis(request.getJenis() != null ? request.getJenis() : "Jenis hewan tidak ditemukan");
                    newJenisHewan.setDeskripsi("-");
                    jenisHewanResponse = jenisHewanRepository.saveByJenis(newJenisHewan);
                } else {
                    System.out.println("Jenis hewan ditemukan: ID " + jenisHewanResponse.getIdJenisHewan() + ", Nama: " + jenisHewanResponse.getJenis());
                }

                Kandang kandangResponse = kandangRepository.findByNamaKandang(request.getNamaKandang());
                if (kandangResponse != null) {
                    System.out.println("Kandang dengan nama " + request.getNamaKandang() + " sudah ada di database.");
                    continue;
                }

                if (namaKandangSet.contains(request.getNamaKandang())) {
                    System.out.println("Kandang dengan nama " + request.getNamaKandang() + " sudah ada dalam list sementara.");
                    continue;
                }

                // Jika tidak ada kandang yang sama, buat objek Kandang baru
                Kandang kandang = new Kandang();
                kandang.setPeternak(peternakResponse);
                kandang.setJenisHewan(jenisHewanResponse);
                kandang.setIdKandang(request.getIdKandang());
                kandang.setAlamat(request.getAlamat());
                kandang.setNamaKandang(request.getNamaKandang() != null ? request.getNamaKandang()
                        : "Kandang " + request.getIdKandang());
                kandang.setLuas(request.getLuas());
                kandang.setJenisKandang(request.getJenisKandang() != null ? request.getJenisKandang() : "Permanen");
                kandang.setNilaiBangunan(request.getNilaiBangunan());
                kandang.setLatitude(request.getLatitude());
                kandang.setLongitude(request.getLongitude());
                kandang.setNikPeternak(request.getNikPeternak());
                kandang.setIdJenisHewan(request.getIdJenisHewan());
                kandang.setKapasitas(request.getKapasitas());

                // Menambahkan nama kandang ke dalam set untuk validasi selanjutnya
                namaKandangSet.add(request.getNamaKandang());
                namaPeternakSet.add(request.getNamaPeternak());
                kandangList.add(kandang);

                System.out.println("Menambahkan data kandang ke dalam daftar: " + kandang);

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data: " + request);
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        // Jika ada data kandang yang valid untuk disimpan
        if (!kandangList.isEmpty()) {
            System.out.println("Menyimpan data kandang yang valid...");
            kandangRepository.saveAll(kandangList);
            System.out.println("Data kandang berhasil disimpan. Total: " + kandangList.size());
        } else {
            System.out.println("Tidak ada data kandang baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai.");
        System.out.println("Data tidak lengkap: " + dataTidakLengkap);
        System.out.println("Data yang di-skip karena kesalahan: " + skippedIncomplete);
    }


    @Transactional
    public void createImportKandangByNama(List<KandangRequest> kandangRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data kandang secara bulk...");

        List<Kandang> kandangList = new ArrayList<>();
        int skippedIncomplete = 0;
        int dataTidakLengkap = 0;

        for (KandangRequest request : kandangRequests) {
            try {
                if (request.getNamaKandang() == null) {
                    System.out.println("Data kandang tidak lengkap: " + request);
                    dataTidakLengkap++;
                    continue;
                }

                // Validasi: Temukan Peternak berdasarkan NIK
                Peternak peternakResponse = peternakRepository.findByNamaPeternak(request.getNamaPeternak());
                if (peternakResponse == null) {
                    System.out.println("Peternak dengan Nama: " + request.getNamaPeternak()
                            + " tidak ditemukan");
                    skippedIncomplete++;
                    continue;
                }

                // Buat objek Kandang
                Kandang kandang = new Kandang();
                kandang.setIdKandang(request.getIdKandang());
                kandang.setAlamat(request.getAlamat() != null ? request.getAlamat() : "-");
                kandang.setNamaKandang(request.getNamaKandang() != null ? request.getNamaKandang()
                        : "Kandang " + request.getIdKandang());
                kandang.setLuas(request.getLuas() != null ? request.getLuas() : "-");
                kandang.setJenisKandang(request.getJenisKandang() != null ? request.getJenisKandang() : "Permanen");
                kandang.setNilaiBangunan(request.getNilaiBangunan() != null ? request.getNilaiBangunan() : "-");
                kandang.setLatitude(request.getLatitude() != null ? request.getLatitude() : "-");
                kandang.setLongitude(request.getLongitude() != null ? request.getLongitude() : "-");
                kandang.setNikPeternak(request.getNikPeternak() != null ? request.getNikPeternak() : "-");
                kandang.setKapasitas(request.getKapasitas() != null ? request.getKapasitas() : "-");

                kandang.setPeternak(peternakResponse);

                // Tambahkan ke list
                kandangList.add(kandang);
                System.out.println("Menambahkan data kandang ke dalam daftar: " + kandang);
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data: " + request);
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        if (!kandangList.isEmpty()) {
            System.out.println("Menyimpan data kandang yang valid...");
            kandangRepository.saveID(kandangList);
            System.out.println("Data kandang berhasil disimpan. Total: " + kandangList.size());
        } else {
            System.out.println("Tidak ada data kandang baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai.");
        System.out.println("Data tidak lengkap: " + dataTidakLengkap);
        System.out.println("Data yang di-skip karena kesalahan: " + skippedIncomplete);
    }

}
package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Inseminasi;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.InseminasiRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.InseminasiRepository;
import com.ternak.sapi.repository.JenisHewanRepository;
import com.ternak.sapi.repository.KandangRepository;
import com.ternak.sapi.repository.KelahiranRepository;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.repository.RumpunHewanRepository;
import com.ternak.sapi.util.AppConstants;
import org.springframework.stereotype.Service;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
// import java.util.Optional;
import java.util.Set;
// import java.util.UUID;
import java.util.UUID;

// import com.ternak.sapi.model.*;
// import com.ternak.sapi.payload.HewanRequest;
// import com.ternak.sapi.repository.*;
// import org.checkerframework.checker.units.qual.K;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

@Service
public class InseminasiService {
    private InseminasiRepository inseminasiRepository = new InseminasiRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private HewanRepository hewanRepository = new HewanRepository();
    private KandangRepository kandangRepository = new KandangRepository();
    private RumpunHewanRepository rumpunHewanRepository = new RumpunHewanRepository();
    private JenisHewanRepository jenisHewanRepository = new JenisHewanRepository();
    private KelahiranRepository kelahiranRepository = new KelahiranRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(InseminasiService.class);

    public PagedResponse<Inseminasi> getAllInseminasi(int page, int size, String peternakId, String petugasId,
            String kandangId, String jenisHewanId, String rumpunHewanId,
            String hewanId) throws IOException {
        validatePageNumberAndSize(page, size);
        List<Inseminasi> inseminasiResponse;
        if (peternakId.equalsIgnoreCase("*")) {
            inseminasiResponse = inseminasiRepository.findAll(size);
        } else {
            inseminasiResponse = inseminasiRepository.findInseminasiByPeternak(peternakId, size);
        }

        // Retrieve Polls

        return new PagedResponse<>(inseminasiResponse, inseminasiResponse.size(), "Successfully get data", 200);
    }

    public Inseminasi createInseminasi(InseminasiRequest inseminasiRequest) throws IOException {
        if (inseminasiRepository.existsById(inseminasiRequest.getIdInseminasi())) {
            throw new BadRequestException("Inseminasi ID " + inseminasiRequest.getIdInseminasi() + " already exists");
        }

        Inseminasi inseminasi = new Inseminasi();
        Peternak peternakResponse = peternakRepository.findById(inseminasiRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(inseminasiRequest.getPetugasId().toString());
        Hewan hewanResponse = hewanRepository.findById(inseminasiRequest.getIdHewan().toString());
        Kandang kandangResponse = kandangRepository.findById(inseminasiRequest.getIdKandang().toString());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository
                .findById(inseminasiRequest.getIdRumpunHewan().toString());
        if (peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && hewanResponse.getIdHewan() != null && rumpunHewanResponse.getIdRumpunHewan() != null) {
            inseminasi.setIdInseminasi(inseminasiRequest.getIdInseminasi());
            inseminasi.setTanggalIB(inseminasiRequest.getTanggalIB());
            inseminasi.setIb1(inseminasiRequest.getIb1());
            inseminasi.setIb2(inseminasiRequest.getIb2());
            inseminasi.setIb3(inseminasiRequest.getIb3());
            inseminasi.setIbLain(inseminasiRequest.getIbLain());
            inseminasi.setIdPejantan(inseminasiRequest.getIdPejantan());
            inseminasi.setIdPembuatan(inseminasiRequest.getIdPembuatan());
            inseminasi.setBangsaPejantan(inseminasiRequest.getBangsaPejantan());
            inseminasi.setProdusen(inseminasiRequest.getProdusen());
            inseminasi.setPeternak(peternakResponse);
            inseminasi.setPetugas(petugasResponse);
            inseminasi.setHewan(hewanResponse);
            inseminasi.setKandang(kandangResponse);
            inseminasi.setRumpunHewan(rumpunHewanResponse);

            return inseminasiRepository.save(inseminasi);
        } else {
            return null;
        }
    }

    public DefaultResponse<Inseminasi> getInseminasiById(String inseminasiId) throws IOException {
        // Retrieve Inseminasi
        Inseminasi inseminasiResponse = inseminasiRepository.findInseminasiById(inseminasiId);
        return new DefaultResponse<>(inseminasiResponse.isValid() ? inseminasiResponse : null,
                inseminasiResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Inseminasi updateInseminasi(String inseminasiId, InseminasiRequest inseminasiRequest) throws IOException {
        Inseminasi inseminasi = new Inseminasi();
        Peternak peternakResponse = peternakRepository.findById(inseminasiRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(inseminasiRequest.getPetugasId().toString());
        Hewan hewanResponse = hewanRepository.findById(inseminasiRequest.getIdHewan().toString());
        Kandang kandangResponse = kandangRepository.findById(inseminasiRequest.getIdKandang().toString());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository
                .findById(inseminasiRequest.getIdRumpunHewan().toString());
        if (peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && hewanResponse.getIdHewan() != null && rumpunHewanResponse.getIdRumpunHewan() != null) {

            inseminasi.setTanggalIB(inseminasiRequest.getTanggalIB());
            inseminasi.setIb1(inseminasiRequest.getIb1());
            inseminasi.setIb2(inseminasiRequest.getIb2());
            inseminasi.setIb3(inseminasiRequest.getIb3());
            inseminasi.setIbLain(inseminasiRequest.getIbLain());
            inseminasi.setIdPejantan(inseminasiRequest.getIdPejantan());
            inseminasi.setIdPembuatan(inseminasiRequest.getIdPembuatan());
            inseminasi.setBangsaPejantan(inseminasiRequest.getBangsaPejantan());
            inseminasi.setProdusen(inseminasiRequest.getProdusen());
            inseminasi.setPeternak(peternakResponse);
            inseminasi.setPetugas(petugasResponse);
            inseminasi.setHewan(hewanResponse);
            inseminasi.setKandang(kandangResponse);
            inseminasi.setRumpunHewan(rumpunHewanResponse);

            List<Kelahiran> kelahiranList = kelahiranRepository.findByInseminasiId(inseminasiId);
            for (Kelahiran kelahiran : kelahiranList) {
                kelahiran.setInseminasi(inseminasi);
                kelahiranRepository.updateInseminasiByKelahiran(kelahiran.getIdKelahiran(), kelahiran);
            }

            return inseminasiRepository.update(inseminasiId, inseminasi);
        } else {
            return null;
        }

    }

    public void deleteInseminasiById(String inseminasiId) throws IOException {
        Inseminasi inseminasiResponse = inseminasiRepository.findInseminasiById(inseminasiId);
        if (inseminasiResponse.isValid()) {
            inseminasiRepository.deleteById(inseminasiId);
        } else {
            throw new ResourceNotFoundException("Inseminasi", "id", inseminasiId);
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
    public void createInseminasiImport(List<InseminasiRequest> inseminasiRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data inseminasi buatan secara bulk...");

        List<Inseminasi> inseminasiList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>(); // Set untuk melacak idHewan yang sudah diproses
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (InseminasiRequest request : inseminasiRequests) {
            try {
                if (existingIds.contains(request.getIdInseminasi())) {
                    skippedExisting++;
                    continue;
                }

                JenisHewan jenisHewanResponse = null;
                if (request.getJenis() == null || request.getJenis().trim().isEmpty()) {
                    System.out.println("Jenis Hewan kosong. Lewati proses pencarian jenis hewan...");
                    JenisHewan defaultJenisHewan = new JenisHewan();
                    defaultJenisHewan.setIdJenisHewan(UUID.randomUUID().toString());
                    defaultJenisHewan.setJenis("Jenis hewan tidak ditemukan waktu import Inseminasi");
                    defaultJenisHewan.setDeskripsi("Jenis hewan tidak ditemukan waktu import Inseminasi");

                    jenisHewanResponse = jenisHewanRepository.save(defaultJenisHewan);
                    System.out.println("Jenis Hewan default berhasil ditambahkan: " + defaultJenisHewan.getJenis());
                } else {
                    jenisHewanResponse = jenisHewanRepository.findByJenis(request.getJenis());
                    if (jenisHewanResponse == null) {
                        System.out.println("Jenis Hewan tidak ditemukan di database. Membuat jenis hewan baru...");

                        JenisHewan newJenisHewan = new JenisHewan();
                        newJenisHewan.setIdJenisHewan(request.getIdJenisHewan() != null ? request.getIdJenisHewan()
                                : UUID.randomUUID().toString());
                        newJenisHewan.setJenis(request.getJenis() != null ? request.getJenis()
                                : "Jenis hewan tidak ditemukan waktu import Inseminasi");
                        newJenisHewan.setDeskripsi(request.getDeskripsiJenis() != null ? request.getDeskripsiJenis()
                                : "Deskripsi jenis hewan tidak ditemukan waktu import Inseminasi");

                        jenisHewanResponse = jenisHewanRepository.save(newJenisHewan);

                        System.out.println("Jenis Hewan baru berhasil dibuat: " + newJenisHewan.getJenis());
                    } else {
                        System.out.println("Jenis Hewan ditemukan di database: " + jenisHewanResponse.getJenis());
                    }
                }

                RumpunHewan rumpunHewanResponse = null;
                if (request.getRumpun() == null || request.getRumpun().trim().isEmpty()) {
                    System.out.println("Rumpun Hewan kosong. Lewati proses pencarian rumpun hewan...");
                    RumpunHewan defaultRumpunHewan = new RumpunHewan();
                    defaultRumpunHewan.setIdRumpunHewan(UUID.randomUUID().toString());
                    defaultRumpunHewan.setRumpun("Rumpun hewan tidak ditemukan waktu import Inseminasi");
                    defaultRumpunHewan.setDeskripsi("Rumpun hewan tidak ditemukan waktu import Inseminasi");

                    rumpunHewanResponse = rumpunHewanRepository.save(defaultRumpunHewan);
                    System.out.println("Rumpun Hewan default berhasil ditambahkan: " + defaultRumpunHewan.getRumpun());
                } else {
                    rumpunHewanResponse = rumpunHewanRepository.findByRumpun(request.getRumpun());
                    if (rumpunHewanResponse == null) {
                        System.out.println("Rumpun Hewan tidak ditemukan di database. Membuat rumpun hewan baru...");

                        RumpunHewan newRumpunHewan = new RumpunHewan();
                        newRumpunHewan.setIdRumpunHewan(request.getIdRumpunHewan() != null ? request.getIdRumpunHewan()
                                : UUID.randomUUID().toString());
                        newRumpunHewan.setRumpun(request.getRumpun() != null ? request.getRumpun()
                                : "Rumpun hewan tidak ditemukan waktu import Inseminasi");
                        newRumpunHewan.setDeskripsi(request.getDeskripsiRumpun() != null ? request.getDeskripsiRumpun()
                                : "Deskripsi rumpun hewan tidak ditemukan waktu import Inseminasi");

                        rumpunHewanResponse = rumpunHewanRepository.save(newRumpunHewan);

                        System.out.println("Rumpun Hewan baru berhasil dibuat: " + newRumpunHewan.getRumpun());
                    } else {
                        System.out.println("Rumpun Hewan ditemukan di database: " + rumpunHewanResponse.getRumpun());
                    }
                }

                System.out.println("Nama petugas diterima dari frontend: " + request.getNamaPetugas());

                Petugas petugasResponse = null;
                if (request.getNamaPetugas() == null || request.getNamaPetugas().trim().isEmpty()) {
                    System.out.println("Nama Petugas kosong. Lewati proses pencarian petugas...");
                    skippedIncomplete++;
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
                                : "nama petugas tidak ditemukan waktu import Inseminasi");
                        newPetugas.setEmail(
                                request.getEmailPetugas() != null ? request.getEmailPetugas() : "-");
                        newPetugas.setNoTelp(
                                request.getNoTelp() != null ? request.getNoTelepon() : "-");
                        newPetugas.setJob(request.getJob() != null ? request.getJob() : "Inseminator");
                        newPetugas.setWilayah(request.getWilayah() != null ? request.getWilayah() : "-");

                        petugasResponse = petugasRepository.saveImport(newPetugas);

                        System.out.println("Petugas baru berhasil dibuat: " + newPetugas.getNamaPetugas());
                    } else {
                        System.out.println("Petugas ditemukan di database: " + petugasResponse.getNamaPetugas());
                    }
                }

                System.out.println("nama peternak diterima dari frontend: " + request.getNamaPeternak());

                Peternak peternakResponse = null;
                if (request.getNamaPeternak() == null || request.getNamaPeternak().trim().isEmpty()) {
                    System.out.println("Nama Peternak kosong. Lewati proses pencarian peternak...");
                    skippedIncomplete++;
                    continue;
                } else {
                    System.out.println("Mencoba menemukan peternak dengan id: " + request.getIdPeternak());
                    peternakResponse = peternakRepository.findByNamaPeternak(request.getNamaPeternak());

                    if (peternakResponse == null) {
                        // Jika peternak tidak ditemukan, tambahkan peternak baru
                        System.out.println("Peternak tidak ditemukan. Menambahkan peternak baru...");
                        Peternak newPeternak = new Peternak();

                        newPeternak.setIdPeternak(request.getIdPeternak() != null ? request.getIdPeternak()
                                : UUID.randomUUID().toString());
                        newPeternak.setNikPeternak(
                                request.getNikPeternak() != null ? request.getNikPeternak() : "NIK Peternak Kosong");
                        newPeternak.setNamaPeternak(
                                request.getNamaPeternak() != null ? request.getNamaPeternak() : "Nama Peternak Kosong");
                        newPeternak.setEmail(request.getEmail() != null ? request.getEmail() : "default@gmail.com");
                        newPeternak.setNoTelepon(
                                request.getNoTelp() != null ? request.getNoTelepon() : "No Telepon Kosong");
                        newPeternak.setAlamat(
                                request.getAlamat() != null ? request.getAlamat() : "Alamat Kosong");
                        newPeternak.setTanggalPendaftaran(
                                request.getTanggalPendaftaran() != null ? request.getTanggalPendaftaran()
                                        : "Tanggal Pendaftaran Kosong");
                        newPeternak.setLokasi(
                                request.getLokasiPeternak() != null ? request.getLokasiPeternak() : "Lokasi Kosong");
                        newPeternak.setDusun(
                                request.getDusunPeternak() != null ? request.getDusunPeternak() : "Dusun Kosong");
                        newPeternak
                                .setDesa(request.getDesaPeternak() != null ? request.getDesaPeternak() : "Desa Kosong");
                        newPeternak.setKecamatan(
                                request.getKecamatanPeternak() != null ? request.getKecamatanPeternak()
                                        : "Kecamatan Kosong");
                        newPeternak.setKabupaten(
                                request.getKabupatenPeternak() != null ? request.getKabupatenPeternak()
                                        : "Kabupaten Kosong");
                        newPeternak.setProvinsi(
                                request.getProvinsiPeternak() != null ? request.getProvinsiPeternak()
                                        : "Provinsi Kosong");
                        newPeternak
                                .setJenisKelamin(
                                        request.getJenisKelamin() != null ? request.getJenisKelamin() : "Lainnya");
                        newPeternak.setIdIsikhnas(
                                request.getIdIsikhnas() != null ? request.getIdIsikhnas() : "ID Isikhnas Kosong");
                        newPeternak
                                .setLatitude(request.getLatitude() != null ? request.getLatitude() : "Latitude Kosong");
                        newPeternak.setLongitude(
                                request.getLongitude() != null ? request.getLongitude() : "Longitude Kosong");
                        newPeternak.setTanggalLahir(
                                request.getTanggalLahirPeternak() != null ? request.getTanggalLahirPeternak()
                                        : "Tanggal Lahir Kosong");
                        newPeternak.setPetugas(petugasResponse);

                        peternakResponse = peternakRepository.saveByNamaPeternak(newPeternak);

                        System.out.println("Peternak baru ditambahkan: " + newPeternak.getNamaPeternak());
                    } else {
                        System.out.println("Peternak ditemukan di database: " + peternakResponse.getNamaPeternak());
                    }
                }

                Kandang kandangResponse = null;
                if (request.getNamaKandang() == null || request.getNamaKandang().trim().isEmpty()) {
                    System.out.println("Nama Kandang Kosong");

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
                    kandangResponse = kandangRepository.findByNamaKandang(request.getNamaKandang());
                    if (kandangResponse == null) {
                        System.out.println("Kandang tidak ditemukan di database. Membuat kandang baru...");

                        Kandang newKandang = new Kandang();
                        newKandang.setIdKandang(request.getIdKandang() != null ? request.getIdKandang()
                                : UUID.randomUUID().toString());
                        newKandang.setNamaKandang(request.getNamaKandang() != null ? request.getNamaKandang()
                                : "Nama Kandang Kosong");
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

                        System.out.println("Kandang baru berhasil dibuat: " + newKandang.getNamaKandang());
                    } else {
                        System.out.println("Kandang ditemukan di database: " + kandangResponse.getNamaKandang());
                    }
                }

                System.out.println("ID Hewan diterima dari frontend: " + request.getIdHewan());

                Hewan hewanResponse = null;

                if (request.getIdHewan() == null || request.getIdHewan().trim().isEmpty()) {
                    System.out.println("ID Hewan kosong.");
                    skippedExisting++;
                    continue;

                } else {
                    System.out.println("Mencoba menemukan hewan dengan ID: " + request.getIdHewan());
                    hewanResponse = hewanRepository.findById(request.getIdHewan());

                    if (hewanResponse == null) {
                        // Jika hewan tidak ditemukan, tambahkan hewan baru
                        System.out.println("Hewan tidak ditemukan. Menambahkan hewan baru...");
                        Hewan newHewan = new Hewan();

                        newHewan.setIdHewan(request.getIdHewan());
                        newHewan.setKodeEartagNasional(request.getKodeEartagNasional() != null
                                ? request.getKodeEartagNasional()
                                : "kode eartag nasional kosong");
                        newHewan.setNoKartuTernak(
                                request.getNoKartuTernak() != null ? request.getNoKartuTernak() : "no kartu kosong");
                        newHewan.setIdIsikhnasTernak(
                                request.getIdIsikhnasTernak() != null ? request.getIdIsikhnasTernak()
                                        : "id isikhnas kosong");
                        newHewan.setIdentifikasiHewan(
                                request.getIdentifikasiHewan() != null ? request.getIdentifikasiHewan() : "-");
                        newHewan.setSex(request.getSex() != null ? request.getSex() : "-");
                        newHewan.setUmur(request.getUmur() != null ? request.getUmur() : "-");
                        newHewan.setTanggalTerdaftar(
                                request.getTanggalTerdaftar() != null ? request.getTanggalTerdaftar()
                                        : "-");
                        newHewan.setTempatLahir(request.getTempatLahir() != null ? request.getTempatLahir() : "-");
                        newHewan.setTanggalLahir(request.getTanggalLahir() != null ? request.getTanggalLahir() : "-");

                        hewanResponse = hewanRepository.saveByIDHewan(newHewan);

                        System.out.println("Hewan baru ditambahkan: " + newHewan.getIdHewan());
                    } else {
                        System.out.println("Hewan ditemukan di database: " + hewanResponse.getIdHewan());
                    }
                }
                Inseminasi inseminasi = new Inseminasi();
                inseminasi.setIdInseminasi(request.getIdInseminasi());
                inseminasi.setTanggalIB(request.getTanggalIB());
                inseminasi.setIb1(request.getIb1());
                inseminasi.setIb2(request.getIb2());
                inseminasi.setIb3(request.getIb3());
                inseminasi.setIbLain(request.getIbLain());
                inseminasi.setIdPejantan(request.getIdPejantan());
                inseminasi.setIdPembuatan(request.getIdPembuatan());
                inseminasi.setBangsaPejantan(request.getBangsaPejantan());
                inseminasi.setProdusen(request.getProdusen());

                inseminasi.setJenisHewan(jenisHewanResponse);
                inseminasi.setRumpunHewan(rumpunHewanResponse);
                inseminasi.setPeternak(peternakResponse);
                inseminasi.setKandang(kandangResponse);
                inseminasi.setHewan(hewanResponse);
                inseminasi.setPetugas(petugasResponse);

                existingIds.add(request.getIdInseminasi());
                inseminasiList.add(inseminasi);

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data hewan: " + request);
                e.printStackTrace();
                skippedIncomplete++;
            }
        }
        if (!inseminasiList.isEmpty()) {
            System.out.println("Menyimpan data inseminasi yang valid...");
            inseminasiRepository.saveImport(inseminasiList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + inseminasiList.size());
        } else {
            System.out.println("Tidak ada data hewan baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);
    }

    @Transactional
    public void createBulkInseminasi(List<InseminasiRequest> inseminasiRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data inseminasi buatan secara bulk...");

        List<Inseminasi> inseminasiList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>(); // Set untuk melacak idHewan yang sudah diproses
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (InseminasiRequest request : inseminasiRequests) {
            try {
                if (existingIds.contains(request.getIdInseminasi())) {
                    skippedExisting++;
                    continue;
                }

                System.out.println("nama peternak diterima dari frontend (inseminasi): " + request.getNamaPeternak());

                Peternak peternakResponse = peternakRepository.findByNamaPeternak(request.getNamaPeternak());
                if (request.getNamaPeternak() == null || request.getNamaPeternak().trim().isEmpty()) {
                    System.out.println("Nama Peternak kosong. Lewati proses pencarian peternak...");
                    skippedIncomplete++;
                    continue;
                }

                System.out.println("Nama petugas diterima dari frontend (inseminasi): " + request.getNamaPetugas());
                Petugas petugasResponse = petugasRepository.findByNamaPetugas(request.getNamaPetugas());
                if (request.getNamaPetugas() == null || request.getNamaPetugas().trim().isEmpty()) {
                    System.out.println("Nama Petugas kosong. Lewati proses pencarian petugas...");
                    skippedIncomplete++;
                    continue;

                }

                System.out.println("id Kandang diterima dari frontend (inseminasi): " + request.getIdKandang());
                Kandang kandangResponse = kandangRepository.findById(request.getIdKandang());

                System.out.println("Jenis Hewan diterima dari frontend (inseminasi): " +
                        request.getJenis());
                JenisHewan jenisHewanResponse = jenisHewanRepository.findByJenis(request.getJenis());

                System.out.println("Rumpun Hewan diterima dari frontend (inseminasi): " + request.getRumpun());
                RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findByRumpun(request.getRumpun());

                System.out.println("ID Hewan diterima dari frontend (inseminasi): " + request.getIdHewan());
                Hewan hewanResponse = hewanRepository.findById(request.getIdHewan());
                if (request.getIdHewan() == null || request.getIdHewan().trim().isEmpty()) {
                    System.out.println("ID Hewan kosong.");
                    skippedExisting++;
                    continue;
                }

                Inseminasi inseminasi = new Inseminasi();
                inseminasi.setIdInseminasi(request.getIdInseminasi());
                inseminasi.setTanggalIB(request.getTanggalIB());
                inseminasi.setIb1(request.getIb1());
                inseminasi.setIb2(request.getIb2());
                inseminasi.setIb3(request.getIb3());
                inseminasi.setIbLain(request.getIbLain());
                inseminasi.setIdPejantan(request.getIdPejantan());
                inseminasi.setIdPembuatan(request.getIdPembuatan());
                inseminasi.setBangsaPejantan(request.getBangsaPejantan());
                inseminasi.setProdusen(request.getProdusen());

                inseminasi.setPetugas(petugasResponse);
                inseminasi.setPeternak(peternakResponse);
                inseminasi.setKandang(kandangResponse);
                inseminasi.setJenisHewan(jenisHewanResponse);
                inseminasi.setRumpunHewan(rumpunHewanResponse);
                inseminasi.setHewan(hewanResponse);

                existingIds.add(request.getIdInseminasi());
                inseminasiList.add(inseminasi);

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data inseminasi: " + request);
                e.printStackTrace();
                skippedIncomplete++;
            }
        }
        if (!inseminasiList.isEmpty()) {
            System.out.println("Menyimpan data inseminasi yang valid...");
            inseminasiRepository.saveBulkImport(inseminasiList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + inseminasiList.size());
        } else {
            System.out.println("Tidak ada data inseminasi baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);
    }
}

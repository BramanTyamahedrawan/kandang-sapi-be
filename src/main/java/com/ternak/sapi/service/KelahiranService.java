package com.ternak.sapi.service;

import com.ternak.sapi.exception.*;
import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Inseminasi;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.KelahiranRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.KelahiranRepository;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.InseminasiRepository;
import com.ternak.sapi.repository.JenisHewanRepository;
import com.ternak.sapi.repository.KandangRepository;
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
import java.util.UUID;

@Service
public class KelahiranService {
    private KelahiranRepository kelahiranRepository = new KelahiranRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private HewanRepository hewanRepository = new HewanRepository();
    private KandangRepository kandangRepository = new KandangRepository();
    private RumpunHewanRepository rumpunHewanRepository = new RumpunHewanRepository();
    private JenisHewanRepository jenisHewanRepository = new JenisHewanRepository();
    private InseminasiRepository inseminasiRepository = new InseminasiRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(KelahiranService.class);

    public PagedResponse<Kelahiran> getAllKelahiran(int page, int size, String peternakId, String petugasId,
            String hewanId, String kandangID, String jenisHewanID,
            String rumpunHewanID, String inseminasiId) throws IOException {
        validatePageNumberAndSize(page, size);
        List<Kelahiran> kelahiranResponse;
        if (peternakId.equalsIgnoreCase("*")) {
            kelahiranResponse = kelahiranRepository.findAll(size);
        } else {
            kelahiranResponse = kelahiranRepository.findKelahiranByPeternak(peternakId, size);
        }

        // Retrieve Polls

        return new PagedResponse<>(kelahiranResponse, kelahiranResponse.size(), "Successfully get data", 200);
    }

    public Kelahiran createKelahiran(KelahiranRequest kelahiranRequest) throws IOException {

        if (kelahiranRepository.existsById(kelahiranRequest.getIdKelahiran())) {
            throw new BadRequestException("Kelahiran ID " + kelahiranRequest.getIdKelahiran() + " already exists");
        }

        Kelahiran kelahiran = new Kelahiran();
        Peternak peternakResponse = peternakRepository.findById(kelahiranRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(kelahiranRequest.getPetugasId().toString());
        Hewan hewanResponse = hewanRepository.findById(kelahiranRequest.getIdHewan().toString());
        Kandang kandangResponse = kandangRepository.findById(kelahiranRequest.getIdKandang().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(kelahiranRequest.getIdJenisHewan().toString());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository
                .findById(kelahiranRequest.getIdRumpunHewan().toString());
        Inseminasi inseminasiResponse = null;
        if (kelahiranRequest.getIdInseminasi() != null) {
            inseminasiResponse = inseminasiRepository.findById(kelahiranRequest.getIdInseminasi().toString());
        }
        if (peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && hewanResponse.getIdHewan() != null) {
            kelahiran.setIdKelahiran(kelahiranRequest.getIdKelahiran());
            kelahiran.setIdKejadian(kelahiranRequest.getIdKejadian());
            kelahiran.setTanggalLaporan(kelahiranRequest.getTanggalLaporan());
            kelahiran.setTanggalLahir(kelahiranRequest.getTanggalLahir());
            kelahiran.setIdHewanAnak(kelahiranRequest.getIdHewanAnak());
            kelahiran.setEartagAnak(kelahiranRequest.getEartagAnak());
            kelahiran.setJenisKelaminAnak(kelahiranRequest.getJenisKelaminAnak());
            kelahiran.setNoKartuTernakAnak(kelahiranRequest.getNoKartuTernakAnak());
            kelahiran.setSpesies(kelahiranRequest.getSpesies());
            kelahiran.setKategori(kelahiranRequest.getKategori());
            kelahiran.setJumlah(kelahiranRequest.getJumlah());
            kelahiran.setUrutanIB(kelahiranRequest.getUrutanIB());
            kelahiran.setPeternak(peternakResponse);
            kelahiran.setPetugas(petugasResponse);
            kelahiran.setHewan(hewanResponse);
            kelahiran.setKandang(kandangResponse);
            kelahiran.setJenisHewan(jenisHewanResponse);
            kelahiran.setRumpunHewan(rumpunHewanResponse);
            kelahiran.setInseminasi(inseminasiResponse);

            return kelahiranRepository.save(kelahiran);
        } else {
            return null;
        }
    }

    @Transactional
    public void createBulkKelahiran(List<KelahiranRequest> kelahiranRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data kelahiran secara bulk...");

        List<Kelahiran> kelahiranList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>(); // Set untuk melacak idHewan yang sudah diproses
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (KelahiranRequest request : kelahiranRequests) {
            try {
                if (existingIds.contains(request.getIdKelahiran())) {
                    skippedExisting++;
                    continue;
                }

                Kelahiran kelahiran = new Kelahiran();
                kelahiran.setIdKelahiran(request.getIdKelahiran());
                kelahiran.setIdKejadian(request.getIdKejadian());
                kelahiran.setTanggalLaporan(request.getTanggalLaporan());
                kelahiran.setTanggalLahir(request.getTanggalLahir());
                kelahiran.setIdHewanAnak(request.getIdHewanAnak());
                kelahiran.setEartagAnak(request.getEartagAnak());
                kelahiran.setJenisKelaminAnak(request.getJenisKelaminAnak());
                kelahiran.setNoKartuTernakAnak(request.getNoKartuTernakAnak());
                kelahiran.setSpesies(request.getSpesies());
                kelahiran.setKategori(request.getKategori());
                kelahiran.setJumlah(request.getJumlah());
                kelahiran.setUrutanIB(request.getUrutanIB());

                System.out.println("Petugas diterima dari frontend (kelahiran): " + request.getNamaPetugas());

                Petugas petugasResponse = petugasRepository.findByNamaPetugas(request.getNamaPetugas());
                if (request.getNamaPetugas() == null || request.getNamaPetugas().trim().isEmpty()) {
                    System.out.println("Nama Petugas kosong.");
                    skippedExisting++;
                    continue;
                }

                System.out.println("Peternak diterima dari frontend: " + request.getNamaPeternak());

                Peternak peternakResponse = peternakRepository.findByNamaPeternak(request.getNamaPeternak());
                if (request.getNamaPeternak() == null || request.getNamaPeternak().trim().isEmpty()) {
                    System.out.println("Nama Peternak kosong.");
                    skippedExisting++;
                    continue;
                }

                System.out.println("id kandang diterima dari frontend: " + request.getIdKandang());

                Kandang kandangResponse = kandangRepository.findById(request.getIdKandang());

                System.out.println("rumpun hewan diterima dari frontend: " + request.getRumpun());
                RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findByRumpun(request.getRumpun());

                System.out.println("jenis hewan diterima dari frontend: " + request.getJenis());
                JenisHewan jenisHewanResponse = jenisHewanRepository.findByJenis(request.getJenis());

                System.out.println("ID Hewan diterima dari frontend: " + request.getIdHewan());

                Hewan hewanResponse = hewanRepository.findById(request.getIdHewan());

                System.out.println("ID Pejantan diterima dari frontend: " + request.getIdPejantan());

                System.out.println("Mencoba menemukan inseminasi dengan ID: " + request.getIdPejantan());
                Inseminasi inseminasiResponse = inseminasiRepository
                        .findInseminasiByIdPejantan(request.getIdPejantan());

                kelahiran.setPetugas(petugasResponse);
                kelahiran.setPeternak(peternakResponse);
                kelahiran.setKandang(kandangResponse);
                kelahiran.setRumpunHewan(rumpunHewanResponse);
                kelahiran.setJenisHewan(jenisHewanResponse);
                kelahiran.setHewan(hewanResponse);
                kelahiran.setInseminasi(inseminasiResponse);

                existingIds.add(request.getIdKelahiran());
                kelahiranList.add(kelahiran);

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data kelahiran: " + request);
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        if (!kelahiranList.isEmpty())

        {
            System.out.println("Menyimpan data kelahiran yang valid...");
            kelahiranRepository.saveBulkImport(kelahiranList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + kelahiranList.size());
        } else {
            System.out.println("Tidak ada data kelahiran baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);

    }

    @Transactional
    public void createImportKelahiran(List<KelahiranRequest> kelahiranRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data kelahiran secara bulk...");

        List<Kelahiran> kelahiranList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>(); // Set untuk melacak idHewan yang sudah diproses
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (KelahiranRequest request : kelahiranRequests) {
            try {
                if (existingIds.contains(request.getIdKelahiran())) {
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
                                request.getNoTelpPetugas() != null ? request.getNoTelpPetugas() : "-");
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
                        newPeternak.setEmail(
                                request.getEmailPeternak() != null ? request.getEmailPeternak() : "default@gmail.com");
                        newPeternak.setNoTelepon(
                                request.getNoTelpPeternak() != null ? request.getNoTelpPeternak()
                                        : "No Telepon Kosong");
                        newPeternak.setAlamat(
                                request.getAlamatPeternak() != null ? request.getAlamatPeternak() : "Alamat Kosong");
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
                                        request.getJenisKelaminPeternak() != null ? request.getJenisKelaminPeternak()
                                                : "Lainnya");
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
                    newKandang.setAlamat(request.getAlamatKandang() != null ? request.getAlamatKandang() : "-");
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
                        newKandang.setAlamat(request.getAlamatKandang() != null ? request.getAlamatKandang() : "-");
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

                Inseminasi inseminasiResponse = null;
                if (request.getIdPejantan() == null || request.getIdPejantan().trim().isEmpty()) {
                    System.out.println("ID Pejantan kosong. Lewati proses pencarian inseminasi...");
                    Inseminasi defaultInseminasi = new Inseminasi();
                    defaultInseminasi.setIdInseminasi(UUID.randomUUID().toString());
                    defaultInseminasi.setIdPejantan("-");
                    defaultInseminasi.setBangsaPejantan("-");
                    defaultInseminasi.setIdPembuatan("-");
                    defaultInseminasi.setProdusen("-");

                    inseminasiResponse = inseminasiRepository.save(defaultInseminasi);
                    System.out
                            .println("Inseminasi default berhasil ditambahkan: " + defaultInseminasi.getIdInseminasi());
                } else {
                    System.out.println("Mencoba menemukan inseminasi dengan ID: " + request.getIdPejantan());
                    inseminasiResponse = inseminasiRepository.findInseminasiByIdPejantan(request.getIdPejantan());
                    if (inseminasiResponse == null) {
                        System.out.println("Inseminasi tidak ditemukan di database. Membuat inseminasi baru...");

                        Inseminasi newInseminasi = new Inseminasi();
                        newInseminasi.setIdInseminasi(request.getIdInseminasi() != null ? request.getIdInseminasi()
                                : UUID.randomUUID().toString());
                        newInseminasi.setIdPejantan(request.getIdPejantan() != null ? request.getIdPejantan() : "-");
                        newInseminasi
                                .setBangsaPejantan(request.getBangsaPejantan() != null ? request.getBangsaPejantan()
                                        : "-");
                        newInseminasi.setIdPembuatan(request.getIdPembuatan() != null ? request.getIdPembuatan() : "-");
                        newInseminasi.setProdusen(request.getProdusen() != null ? request.getProdusen() : "-");
                        newInseminasi.setTanggalIB(request.getTanggalIB() != null ? request.getTanggalIB() : "-");
                        newInseminasi.setIb1(request.getIb1() != null ? request.getIb1() : "-");
                        newInseminasi.setIb2(request.getIb2() != null ? request.getIb2() : "-");
                        newInseminasi.setIb3(request.getIb3() != null ? request.getIb3() : "-");
                        newInseminasi.setIbLain(request.getIbLain() != null ? request.getIbLain() : "-");

                        inseminasiResponse = inseminasiRepository.save(newInseminasi);

                        System.out.println("Inseminasi baru berhasil dibuat: " + newInseminasi.getIdInseminasi());
                    } else {
                        System.out.println("Inseminasi ditemukan di database: " + inseminasiResponse.getIdInseminasi());
                    }
                }

                Kelahiran kelahiran = new Kelahiran();
                kelahiran.setIdKelahiran(request.getIdKelahiran());
                kelahiran.setIdKejadian(request.getIdKejadian());
                kelahiran.setTanggalLaporan(request.getTanggalLaporan());
                kelahiran.setTanggalLahir(request.getTanggalLahir());
                kelahiran.setIdHewanAnak(request.getIdHewanAnak());
                kelahiran.setEartagAnak(request.getEartagAnak());
                kelahiran.setJenisKelaminAnak(request.getJenisKelaminAnak());
                kelahiran.setNoKartuTernakAnak(request.getNoKartuTernakAnak());
                kelahiran.setSpesies(request.getSpesies());
                kelahiran.setKategori(request.getKategori());
                kelahiran.setJumlah(request.getJumlah());
                kelahiran.setUrutanIB(request.getUrutanIB());

                kelahiran.setPetugas(petugasResponse);
                kelahiran.setPeternak(peternakResponse);
                kelahiran.setKandang(kandangResponse);
                kelahiran.setRumpunHewan(rumpunHewanResponse);
                kelahiran.setJenisHewan(jenisHewanResponse);
                kelahiran.setHewan(hewanResponse);
                kelahiran.setInseminasi(inseminasiResponse);

                existingIds.add(request.getIdKelahiran());
                kelahiranList.add(kelahiran);

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data kelahiran: " + request);
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        if (!kelahiranList.isEmpty())

        {
            System.out.println("Menyimpan data kelahiran yang valid...");
            kelahiranRepository.saveImport(kelahiranList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + kelahiranList.size());
        } else {
            System.out.println("Tidak ada data kelahiran baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);

    }

    public DefaultResponse<Kelahiran> getKelahiranById(String kelahiranId) throws IOException {
        // Retrieve Kelahiran
        Kelahiran kelahiranResponse = kelahiranRepository.findKelahiranById(kelahiranId);
        return new DefaultResponse<>(kelahiranResponse.isValid() ? kelahiranResponse : null,
                kelahiranResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Kelahiran updateKelahiran(String kelahiranId, KelahiranRequest kelahiranRequest) throws IOException {
        Kelahiran kelahiran = new Kelahiran();

        Peternak peternakResponse = peternakRepository.findById(kelahiranRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(kelahiranRequest.getPetugasId().toString());
        Hewan hewanResponse = hewanRepository.findById(kelahiranRequest.getIdHewan().toString());
        Kandang kandangResponse = kandangRepository.findById(kelahiranRequest.getIdKandang().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(kelahiranRequest.getIdJenisHewan().toString());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository
                .findById(kelahiranRequest.getIdRumpunHewan().toString());
        Inseminasi inseminasiResponse = null;
        if (kelahiranRequest.getIdInseminasi() != null) {
            inseminasiResponse = inseminasiRepository.findById(kelahiranRequest.getIdInseminasi().toString());
        }
        if (peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && hewanResponse.getIdHewan() != null) {
            kelahiran.setIdKejadian(kelahiranRequest.getIdKejadian());
            kelahiran.setTanggalLaporan(kelahiranRequest.getTanggalLaporan());
            kelahiran.setTanggalLahir(kelahiranRequest.getTanggalLahir());
            kelahiran.setIdHewanAnak(kelahiranRequest.getIdHewanAnak());
            kelahiran.setNoKartuTernakAnak(kelahiranRequest.getNoKartuTernakAnak());
            kelahiran.setEartagAnak(kelahiranRequest.getEartagAnak());
            kelahiran.setJenisKelaminAnak(kelahiranRequest.getJenisKelaminAnak());
            kelahiran.setSpesies(kelahiranRequest.getSpesies());
            kelahiran.setKategori(kelahiranRequest.getKategori());
            kelahiran.setJumlah(kelahiranRequest.getJumlah());
            kelahiran.setUrutanIB(kelahiranRequest.getUrutanIB());
            kelahiran.setPeternak(peternakResponse);
            kelahiran.setPetugas(petugasResponse);
            kelahiran.setHewan(hewanResponse);
            kelahiran.setKandang(kandangResponse);
            kelahiran.setJenisHewan(jenisHewanResponse);
            kelahiran.setRumpunHewan(rumpunHewanResponse);
            kelahiran.setInseminasi(inseminasiResponse);

            return kelahiranRepository.update(kelahiranId, kelahiran);
        } else {
            return null;
        }
    }

    public void deleteKelahiranById(String kelahiranId) throws IOException {
        Kelahiran kelahiranResponse = kelahiranRepository.findKelahiranById(kelahiranId);
        if (kelahiranResponse.isValid()) {
            kelahiranRepository.deleteById(kelahiranId);
        } else {
            throw new ResourceNotFoundException("Kelahiran", "id", kelahiranId);
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

}

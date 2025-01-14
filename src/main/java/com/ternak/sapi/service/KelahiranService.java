package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
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
        Kelahiran kelahiran = new Kelahiran();
        Peternak peternakResponse = peternakRepository.findById(kelahiranRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(kelahiranRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(kelahiranRequest.getIdHewan().toString());
        Kandang kandangResponse = kandangRepository.findById(kelahiranRequest.getIdKandang().toString());
        Inseminasi inseminasiResponse = inseminasiRepository
                .findInseminasiById(kelahiranRequest.getIdInseminasi().toString());
        if (peternakResponse.getNamaPeternak() != null && petugasResponse.getNamaPetugas() != null) {
            kelahiran.setIdKejadian(kelahiranRequest.getIdKejadian());
            kelahiran.setTanggalLaporan(kelahiranRequest.getTanggalLaporan());
            kelahiran.setTanggalLahir(kelahiranRequest.getTanggalLahir());
            kelahiran.setEartagAnak(kelahiranRequest.getEartagAnak());
            kelahiran.setJenisKelaminAnak(kelahiranRequest.getJenisKelaminAnak());
            kelahiran.setSpesies(kelahiranRequest.getSpesies());
            kelahiran.setPeternak(peternakResponse);
            kelahiran.setPetugas(petugasResponse);
            kelahiran.setHewan(hewanResponse);
            kelahiran.setKandang(kandangResponse);
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
                if (existingIds.contains(request.getIdKejadian())) {
                    skippedExisting++;
                    continue;
                }

                Kelahiran kelahiran = new Kelahiran();
                kelahiran.setIdKejadian(request.getIdKejadian());
                kelahiran.setTanggalLaporan(request.getTanggalLaporan());
                kelahiran.setTanggalLahir(request.getTanggalLahir());
                kelahiran.setEartagAnak(request.getEartagAnak());
                kelahiran.setJenisKelaminAnak(request.getJenisKelaminAnak());
                kelahiran.setSpesies(request.getSpesies());
                kelahiran.setKategori(request.getKategori());
                kelahiran.setJumlah(request.getJumlah());
                kelahiran.setLokasi(request.getLokasi());
                kelahiran.setIdHewanAnak(request.getIdHewanAnak());
                kelahiran.setUrutanIB(request.getUrutanIB());

                System.out.println("Petugas diterima dari frontend (kelahiran): " + request.getNamaPetugas());

                Petugas petugasResponse = null;
                if (request.getNamaPetugas() == null || request.getNamaPetugas().trim().isEmpty()) {
                    System.out.println("Nama Petugas kosong.");
                    skippedExisting++;
                    continue;

                } else {
                    System.out.println("Mencoba menemukan petugas dengan nama: " + request.getNamaPetugas());
                    petugasResponse = petugasRepository.findByNamaPetugas(request.getNamaPetugas());

                    if (petugasResponse == null) {
                        // Jika petugas tidak ditemukan, tambahkan petugas baru
                        System.out.println("Petugas tidak ditemukan. Menambahkan petugas baru...");
                        Petugas newPetugas = new Petugas();

                        newPetugas.setNikPetugas(request.getNikPetugas() != null ? request.getNikPetugas() : "-");
                        newPetugas.setNamaPetugas(request.getNamaPetugas() != null ? request.getNamaPetugas() : "-");
                        newPetugas.setEmail(request.getEmailPetugas() != null ? request.getEmailPetugas() : "-");
                        newPetugas.setNoTelp(request.getNoTelpPetugas() != null ? request.getNoTelpPetugas() : "-");
                        newPetugas.setJob(request.getJob() != null ? request.getJob() : "-");

                        petugasResponse = petugasRepository.saveNama(newPetugas);

                        System.out.println("Petugas baru ditambahkan: " + newPetugas.getNamaPetugas());
                    } else {
                        System.out.println("Petugas ditemukan di database: " + petugasResponse.getNamaPetugas());
                    }

                }

                System.out.println("Peternak diterima dari frontend: " + request.getNamaPeternak());

                Peternak peternakResponse = null;
                if (request.getNamaPeternak() == null || request.getNamaPeternak().trim().isEmpty()) {
                    System.out.println("Nama Peternak kosong.");
                    skippedExisting++;
                    continue;

                } else {
                    System.out.println("Mencoba menemukan peternak dengan nama: " + request.getNamaPeternak());
                    peternakResponse = peternakRepository.findByNamaPeternak(request.getNamaPeternak());

                    if (peternakResponse == null) {
                        // Jika peternak tidak ditemukan, tambahkan peternak baru
                        System.out.println("Peternak tidak ditemukan. Menambahkan peternak baru...");
                        Peternak newPeternak = new Peternak();

                        newPeternak.setIdPeternak(request.getIdPeternak() != null ? request.getIdPeternak() : "-");
                        newPeternak.setNikPeternak(request.getNikPeternak() != null ? request.getNikPeternak() : "-");
                        newPeternak
                                .setNamaPeternak(request.getNamaPeternak() != null ? request.getNamaPeternak() : "-");
                        newPeternak.setEmail(request.getEmailPeternak() != null ? request.getEmailPeternak() : "-");
                        newPeternak
                                .setNoTelepon(request.getNoTelpPeternak() != null ? request.getNoTelpPeternak() : "-");
                        newPeternak.setTanggalPendaftaran(
                                request.getTanggalPendaftaran() != null ? request.getTanggalPendaftaran() : "-");
                        newPeternak.setLokasi(request.getLokasiPeternak() != null ? request.getLokasiPeternak() : "-");
                        newPeternak.setAlamat(request.getAlamatPeternak() != null ? request.getAlamatPeternak() : "-");
                        newPeternak.setIdIsikhnas(request.getIdIsikhnas() != null ? request.getIdIsikhnas() : "-");
                        newPeternak.setJenisKelamin(
                                request.getJenisKelaminPeternak() != null ? request.getJenisKelaminPeternak() : "-");
                        newPeternak.setTanggalLahir(
                                request.getTanggalLahirPeternak() != null ? request.getTanggalLahirPeternak() : "-");
                        newPeternak.setProvinsi(
                                request.getProvinsiPeternak() != null ? request.getProvinsiPeternak() : "-");
                        newPeternak.setKabupaten(
                                request.getKabupatenPeternak() != null ? request.getKabupatenPeternak() : "-");
                        newPeternak.setKecamatan(
                                request.getKecamatanPeternak() != null ? request.getKecamatanPeternak() : "-");
                        newPeternak.setDesa(request.getDesaPeternak() != null ? request.getDesaPeternak() : "-");
                        newPeternak.setDusun(request.getDusunPeternak() != null ? request.getDusunPeternak() : "-");

                        peternakResponse = peternakRepository.saveByNamaPeternak(newPeternak);

                        System.out.println("Peternak baru ditambahkan: " +
                                newPeternak.getNamaPeternak());
                    } else {
                        System.out.println("Peternak ditemukan di database: " +
                                peternakResponse.getNamaPeternak());
                    }
                }

                System.out.println("id kandang diterima dari frontend: " + request.getIdKandang());

                Kandang kandangResponse = null;

                if (request.getIdKandang() == null || request.getIdKandang().trim().isEmpty()) {
                    System.out.println("ID Kandang kosong.");
                    skippedExisting++;
                    continue;

                } else {
                    System.out.println("Mencoba menemukan kandang dengan nama: " + request.getNamaKandang());
                    kandangResponse = kandangRepository.findByIdKandang(request.getIdKandang());

                    if (kandangResponse == null) {
                        // Jika kandang tidak ditemukan, tambahkan kandang baru
                        System.out.println("Kandang tidak ditemukan. Menambahkan kandang baru...");
                        Kandang newKandang = new Kandang();

                        newKandang.setIdKandang(request.getIdKandang() != null ? request.getIdKandang() : "-");
                        newKandang.setNamaKandang(request.getNamaKandang() != null ? request.getNamaKandang() : "-");
                        newKandang.setJenisKandang(request.getJenisKandang() != null ? request.getJenisKandang() : "-");
                        newKandang.setKapasitas(request.getKapasitas() != null ? request.getKapasitas() : "-");
                        newKandang.setLuas(request.getLuas() != null ? request.getLuas() : "-");
                        newKandang.setNilaiBangunan(
                                request.getNilaiBangunan() != null ? request.getNilaiBangunan() : "-");
                        newKandang.setAlamat(request.getAlamatKandang() != null ? request.getAlamatKandang() : "-");
                        newKandang.setLatitude(
                                request.getLatitudeKandang() != null ? request.getLongitudeKandang() : "-");

                        kandangResponse = kandangRepository.saveKandangById(newKandang);

                        System.out.println("Kandang baru ditambahkan: " + newKandang.getNamaKandang());
                    } else {
                        System.out.println("Kandang ditemukan di database: " + kandangResponse.getNamaKandang());
                    }

                }

                RumpunHewan rumpunHewanResponse = null;

                System.out.println("Mencoba menemukan rumpun hewan dengan ID: " + request.getRumpun());
                rumpunHewanResponse = rumpunHewanRepository.findByRumpun(request.getRumpun());

                if (rumpunHewanResponse == null) {
                    // Jika rumpun hewan tidak ditemukan, tambahkan rumpun hewan baru
                    System.out.println("Rumpun Hewan tidak ditemukan. Menambahkan rumpun hewan baru...");
                    RumpunHewan newRumpunHewan = new RumpunHewan();

                    newRumpunHewan.setIdRumpunHewan(request.getIdRumpunHewan());
                    newRumpunHewan.setRumpun(request.getRumpun() != null ? request.getRumpun() : "-");
                    newRumpunHewan.setDeskripsi(
                            request.getDeskripsiRumpun() != null ? request.getDeskripsiRumpun() : "-");

                    rumpunHewanResponse = rumpunHewanRepository.saveByRumpun(newRumpunHewan);

                    System.out.println("Rumpun Hewan baru ditambahkan: " + newRumpunHewan.getIdRumpunHewan());
                } else {
                    System.out.println(
                            "Rumpun Hewan ditemukan di database: " + rumpunHewanResponse.getIdRumpunHewan());
                }

                JenisHewan jenisHewanResponse = null;

                System.out.println("Mencoba menemukan jenis hewan dengan ID: " + request.getJenis());
                jenisHewanResponse = jenisHewanRepository.findByJenis(request.getJenis());

                if (jenisHewanResponse == null) {
                    // Jika jenis hewan tidak ditemukan, tambahkan jenis hewan baru
                    System.out.println("Jenis Hewan tidak ditemukan. Menambahkan jenis hewan baru...");
                    JenisHewan newJenisHewan = new JenisHewan();

                    newJenisHewan.setIdJenisHewan(request.getIdJenisHewan());
                    newJenisHewan.setJenis(request.getJenis() != null ? request.getJenis() : "-");
                    newJenisHewan.setDeskripsi(
                            request.getDeskripsiJenis() != null ? request.getDeskripsiJenis() : "-");

                    jenisHewanResponse = jenisHewanRepository.saveByJenis(newJenisHewan);

                    System.out.println("Jenis Hewan baru ditambahkan: " + newJenisHewan.getJenis());
                } else {
                    System.out.println("Jenis Hewan ditemukan di database: " + jenisHewanResponse.getJenis());
                }

                System.out.println("ID Hewan diterima dari frontend: " + request.getIdHewan());

                Hewan hewanResponse = null;

                if (request.getIdHewan() == null || request.getIdHewan().trim().isEmpty()) {
                    System.out.println("ID Hewan kosong.");
                    skippedExisting++;
                    continue;

                } else {
                    System.out.println("Mencoba menemukan hewan dengan ID: " + request.getIdHewan());
                    hewanResponse = hewanRepository.findByIdNoKandang(request.getIdHewan());

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

                System.out.println("ID Pejantan diterima dari frontend: " + request.getIdPejantan());

                Inseminasi inseminasiResponse = null;

                System.out.println("Mencoba menemukan inseminasi dengan ID: " + request.getIdPejantan());
                inseminasiResponse = inseminasiRepository.findInseminasiByIdPejantan(request.getIdPejantan());

                kelahiran.setPetugas(petugasResponse);
                kelahiran.setPeternak(peternakResponse);
                kelahiran.setKandang(kandangResponse);
                kelahiran.setRumpunHewan(rumpunHewanResponse);
                kelahiran.setJenisHewan(jenisHewanResponse);
                kelahiran.setHewan(hewanResponse);
                kelahiran.setInseminasi(inseminasiResponse);

                existingIds.add(request.getIdKejadian());
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

    public DefaultResponse<Kelahiran> getKelahiranById(String kelahiranId) throws IOException {
        // Retrieve Kelahiran
        Kelahiran kelahiranResponse = kelahiranRepository.findKelahiranById(kelahiranId);
        return new DefaultResponse<>(kelahiranResponse.isValid() ? kelahiranResponse : null,
                kelahiranResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Kelahiran updateKelahiran(String kelahiranId, KelahiranRequest kelahiranRequest) throws IOException {
        Kelahiran kelahiran = new Kelahiran();
        Peternak peternakResponse = peternakRepository.findById(kelahiranRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(kelahiranRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(kelahiranRequest.getIdHewan().toString());
        Kandang kandangResponse = kandangRepository.findById(kelahiranRequest.getIdKandang().toString());
        Inseminasi inseminasiResponse = inseminasiRepository
                .findInseminasiById(kelahiranRequest.getIdInseminasi().toString());
        if (peternakResponse.getNamaPeternak() != null && petugasResponse.getNamaPetugas() != null
                && hewanResponse.getIdHewan() != null) {
            kelahiran.setTanggalLaporan(kelahiranRequest.getTanggalLaporan());
            kelahiran.setTanggalLahir(kelahiranRequest.getTanggalLahir());
            kelahiran.setEartagAnak(kelahiranRequest.getEartagAnak());
            kelahiran.setJenisKelaminAnak(kelahiranRequest.getJenisKelaminAnak());
            kelahiran.setSpesies(kelahiranRequest.getSpesies());
            kelahiran.setPeternak(peternakResponse);
            kelahiran.setPetugas(petugasResponse);
            kelahiran.setHewan(hewanResponse);
            kelahiran.setKandang(kandangResponse);
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

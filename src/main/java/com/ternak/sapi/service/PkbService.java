package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Pkb;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.PkbRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.PkbRepository;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.JenisHewanRepository;
import com.ternak.sapi.repository.KandangRepository;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.util.AppConstants;
import com.ternak.sapi.repository.RumpunHewanRepository;
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
public class PkbService {
    private PkbRepository pkbRepository = new PkbRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private HewanRepository hewanRepository = new HewanRepository();
    private RumpunHewanRepository rumpunHewanRepository = new RumpunHewanRepository();
    private JenisHewanRepository jenisHewanRepository = new JenisHewanRepository();
    private KandangRepository kandangRepository = new KandangRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(PkbService.class);

    public PagedResponse<Pkb> getAllPkb(int page, int size, String peternakId, String petugasId, String jenisHewanID,
            String rumpunHewanID, String kandangID, String hewanId)
            throws IOException {
        validatePageNumberAndSize(page, size);
        List<Pkb> pkbResponse;
        if (peternakId.equalsIgnoreCase("*")) {
            pkbResponse = pkbRepository.findAll(size);
        } else {
            pkbResponse = pkbRepository.findPkbByPeternak(peternakId, size);
        }

        // Retrieve Polls

        return new PagedResponse<>(pkbResponse, pkbResponse.size(), "Successfully get data", 200);
    }

    public Pkb createPkb(PkbRequest pkbRequest) throws IOException {
        Pkb pkb = new Pkb();
        Peternak peternakResponse = peternakRepository.findById(pkbRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(pkbRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(pkbRequest.getIdHewan().toString());
        if (peternakResponse.getNamaPeternak() != null && petugasResponse.getNamaPetugas() != null
                && hewanResponse.getIdHewan() != null) {
            pkb.setIdKejadian(pkbRequest.getIdKejadian());
            pkb.setTanggalPkb(pkbRequest.getTanggalPkb());
            pkb.setSpesies(pkbRequest.getSpesies());
            pkb.setUmurKebuntingan(pkbRequest.getUmurKebuntingan());
            pkb.setPeternak(peternakResponse);
            pkb.setPetugas(petugasResponse);
            pkb.setHewan(hewanResponse);

            return pkbRepository.save(pkb);
        } else {
            return null;
        }
    }

    public DefaultResponse<Pkb> getPkbById(String pkbId) throws IOException {
        // Retrieve Pkb
        Pkb pkbResponse = pkbRepository.findPkbById(pkbId);
        return new DefaultResponse<>(pkbResponse.isValid() ? pkbResponse : null, pkbResponse.isValid() ? 1 : 0,
                "Successfully get data");
    }

    public Pkb updatePkb(String pkbId, PkbRequest pkbRequest) throws IOException {
        Pkb pkb = new Pkb();
        Peternak peternakResponse = peternakRepository.findById(pkbRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(pkbRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(pkbRequest.getIdHewan().toString());
        if (peternakResponse.getNamaPeternak() != null && petugasResponse.getNamaPetugas() != null
                && hewanResponse.getIdHewan() != null) {
            pkb.setTanggalPkb(pkbRequest.getTanggalPkb());
            pkb.setSpesies(pkbRequest.getSpesies());
            pkb.setUmurKebuntingan(pkbRequest.getUmurKebuntingan());
            pkb.setPeternak(peternakResponse);
            pkb.setPetugas(petugasResponse);
            pkb.setHewan(hewanResponse);

            return pkbRepository.update(pkbId, pkb);
        } else {
            return null;
        }
    }

    public void deletePkbById(String pkbId) throws IOException {
        Pkb pkbResponse = pkbRepository.findPkbById(pkbId);
        if (pkbResponse.isValid()) {
            pkbRepository.deleteById(pkbId);
        } else {
            throw new ResourceNotFoundException("Pkb", "id", pkbId);
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
    public void createPkbImport(List<PkbRequest> pkbRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data pkb secara bulk...");

        List<Pkb> pkbList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>(); // Set untuk melacak idHewan yang sudah diproses
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (PkbRequest request : pkbRequests) {
            try {
                if (existingIds.contains(request.getIdKejadian())) {
                    skippedExisting++;
                    continue;
                }

                Pkb pkb = new Pkb();
                pkb.setIdKejadian(request.getIdKejadian());
                pkb.setTanggalPkb(request.getTanggalPkb());
                pkb.setLokasi(request.getLokasi());
                pkb.setSpesies(request.getSpesies());
                pkb.setUmurKebuntingan(request.getUmurKebuntingan());
                pkb.setJumlah(request.getJumlah());

                System.out.println("Petugas diterima dari frontend: " + request.getNamaPetugas());

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

                RumpunHewan rumpunHewanResponse = null;

                if (request.getRumpun() == null || request.getRumpun().trim().isEmpty()) {
                    System.out.println("Rumpun Hewan kosong.");
                    skippedExisting++;
                    continue;

                } else {
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
                }

                JenisHewan jenisHewanResponse = null;

                if (request.getJenis() == null || request.getJenis().trim().isEmpty()) {
                    System.out.println("Jenis Hewan kosong.");
                    skippedExisting++;
                    continue;

                } else {
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
                }

                pkb.setPetugas(petugasResponse);
                pkb.setPeternak(peternakResponse);
                pkb.setKandang(kandangResponse);
                pkb.setRumpunHewan(rumpunHewanResponse);
                pkb.setJenisHewan(jenisHewanResponse);
                pkb.setHewan(hewanResponse);

                existingIds.add(request.getIdKejadian());
                pkbList.add(pkb);

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data pkb: " + request);
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        if (!pkbList.isEmpty()) {
            System.out.println("Menyimpan data pkb yang valid...");
            pkbRepository.saveImport(pkbList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + pkbList.size());
        } else {
            System.out.println("Tidak ada data pkb baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);

    }

}

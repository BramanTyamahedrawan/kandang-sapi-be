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

        if (kelahiranRepository.existsById(kelahiranRequest.getIdKejadian())) {
            throw new BadRequestException("Kelahiran ID " + kelahiranRequest.getIdKejadian() + " already exists");
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
        if (peternakResponse.getNamaPeternak() != null && petugasResponse.getNamaPetugas() != null
                && hewanResponse.getIdHewan() != null) {
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
                if (existingIds.contains(request.getIdKejadian())) {
                    skippedExisting++;
                    continue;
                }

                Kelahiran kelahiran = new Kelahiran();
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

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

        if (pkbRepository.existsById(pkbRequest.getIdPkb())) {
            throw new BadRequestException("Id PKB " + pkbRequest.getIdPkb() + " sudah terdaftar.");
        }

        Pkb pkb = new Pkb();
        Peternak peternakResponse = peternakRepository.findById(pkbRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(pkbRequest.getPetugasId().toString());
        Hewan hewanResponse = hewanRepository.findById(pkbRequest.getIdHewan().toString());
        Kandang kandangResponse = kandangRepository.findById(pkbRequest.getIdKandang());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(pkbRequest.getIdJenisHewan());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(pkbRequest.getIdRumpunHewan());

        if (peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && hewanResponse.getIdHewan() != null) {
            pkb.setIdPkb(pkbRequest.getIdPkb());
            pkb.setIdKejadian(pkbRequest.getIdKejadian());
            pkb.setTanggalPkb(pkbRequest.getTanggalPkb());
            pkb.setJumlah(pkbRequest.getJumlah());
            pkb.setUmurKebuntingan(pkbRequest.getUmurKebuntingan());
            pkb.setPeternak(peternakResponse);
            pkb.setPetugas(petugasResponse);
            pkb.setKandang(kandangResponse);
            pkb.setJenisHewan(jenisHewanResponse);
            pkb.setRumpunHewan(rumpunHewanResponse);
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
        Petugas petugasResponse = petugasRepository.findById(pkbRequest.getPetugasId().toString());
        Hewan hewanResponse = hewanRepository.findById(pkbRequest.getIdHewan().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(pkbRequest.getIdJenisHewan());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(pkbRequest.getIdRumpunHewan());
        Kandang kandangResponse = kandangRepository.findById(pkbRequest.getIdKandang());

        if (peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && hewanResponse.getIdHewan() != null) {

            pkb.setIdKejadian(pkbRequest.getIdKejadian());
            pkb.setTanggalPkb(pkbRequest.getTanggalPkb());
            pkb.setJumlah(pkbRequest.getJumlah());
            pkb.setUmurKebuntingan(pkbRequest.getUmurKebuntingan());
            pkb.setPeternak(peternakResponse);
            pkb.setPetugas(petugasResponse);
            pkb.setKandang(kandangResponse);
            pkb.setJenisHewan(jenisHewanResponse);
            pkb.setRumpunHewan(rumpunHewanResponse);
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
                if (existingIds.contains(request.getIdPkb())) {
                    skippedExisting++;
                    continue;
                }

                Pkb pkb = new Pkb();
                pkb.setIdPkb(request.getIdPkb());
                pkb.setIdKejadian(request.getIdKejadian());
                pkb.setTanggalPkb(request.getTanggalPkb());
                pkb.setUmurKebuntingan(request.getUmurKebuntingan());
                pkb.setJumlah(request.getJumlah());

                System.out.println("Petugas diterima dari frontend: " + request.getNamaPetugas());

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

                pkb.setPetugas(petugasResponse);
                pkb.setPeternak(peternakResponse);
                pkb.setRumpunHewan(rumpunHewanResponse);
                pkb.setJenisHewan(jenisHewanResponse);
                pkb.setKandang(kandangResponse);
                pkb.setHewan(hewanResponse);

                existingIds.add(request.getIdPkb());
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

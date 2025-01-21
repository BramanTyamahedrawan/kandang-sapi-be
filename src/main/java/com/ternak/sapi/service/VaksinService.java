package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Vaksin;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.model.NamaVaksin;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.VaksinRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.JenisVaksinRepository;
import com.ternak.sapi.repository.NamaVaksinRepository;
import com.ternak.sapi.repository.VaksinRepository;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.util.AppConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VaksinService {
    private VaksinRepository vaksinRepository = new VaksinRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private NamaVaksinRepository namaVaksinRepository = new NamaVaksinRepository();
    private JenisVaksinRepository jenisVaksinRepository = new JenisVaksinRepository();
    private HewanRepository hewanRepository = new HewanRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(VaksinService.class);

    public PagedResponse<Vaksin> getAllVaksin(int page, int size, String peternakId, String petugasId, String hewanId)
            throws IOException {
        validatePageNumberAndSize(page, size);
        List<Vaksin> vaksinResponse;
        if (peternakId.equalsIgnoreCase("*")) {
            vaksinResponse = vaksinRepository.findAll(size);
        } else {
            vaksinResponse = vaksinRepository.findVaksinByPeternak(peternakId, size);
        }

        return new PagedResponse<>(vaksinResponse, vaksinResponse.size(), "Successfully get data", 200);
    }

    public Vaksin createVaksin(VaksinRequest vaksinRequest) throws IOException {
        // Validasi ID Vaksin
        if (vaksinRepository.existsByIdVaksin(vaksinRequest.getIdVaksin())) {
            throw new IllegalArgumentException("Vaksin ID sudah terdaftar!");
        }

        Vaksin vaksin = new Vaksin();
        Peternak peternakResponse = peternakRepository.findById(vaksinRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(vaksinRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(vaksinRequest.getHewan_id().toString());
        JenisVaksin jenisVaksin = jenisVaksinRepository.findById(vaksinRequest.getIdJenisVaksin().toString());
        NamaVaksin namaVaksin = namaVaksinRepository.findById(vaksinRequest.getIdNamaVaksin().toString());
        if (peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && hewanResponse.getIdHewan() != null && jenisVaksin.getIdJenisVaksin() != null
                && namaVaksin.getIdNamaVaksin() != null) {
            vaksin.setIdVaksin(vaksinRequest.getIdVaksin());
            vaksin.setTglVaksin(vaksinRequest.getTglVaksin());
            vaksin.setBatchVaksin(vaksinRequest.getBatchVaksin());
            vaksin.setVaksinKe(vaksinRequest.getVaksinKe());
            vaksin.setJenisVaksin(jenisVaksin);
            vaksin.setNamaVaksin(namaVaksin);
            vaksin.setPeternak(peternakResponse);
            vaksin.setPetugas(petugasResponse);
            vaksin.setHewan(hewanResponse);
        }
        return vaksinRepository.save(vaksin);
    }

    public DefaultResponse<Vaksin> getVaksinById(String vaksinId) throws IOException {
        // Retrieve Vaksin
        Vaksin vaksinResponse = vaksinRepository.findVaksinById(vaksinId);
        return new DefaultResponse<>(vaksinResponse.isValid() ? vaksinResponse : null, vaksinResponse.isValid() ? 1 : 0,
                "Successfully get data");
    }

    public Vaksin updateVaksin(String vaksinId, VaksinRequest vaksinRequest) throws IOException {
        Vaksin vaksin = new Vaksin();
        Peternak peternakResponse = peternakRepository.findById(vaksinRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(vaksinRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(vaksinRequest.getHewan_id().toString());
        JenisVaksin jenisVaksin = jenisVaksinRepository.findById(vaksinRequest.getIdJenisVaksin().toString());
        NamaVaksin namaVaksin = namaVaksinRepository.findById(vaksinRequest.getIdNamaVaksin().toString());
        if (peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && hewanResponse.getIdHewan() != null && jenisVaksin.getIdJenisVaksin() != null
                && namaVaksin.getIdNamaVaksin() != null) {
            vaksin.setTglVaksin(vaksinRequest.getTglVaksin());
            vaksin.setBatchVaksin(vaksinRequest.getBatchVaksin());
            vaksin.setVaksinKe(vaksinRequest.getVaksinKe());
            vaksin.setJenisVaksin(jenisVaksin);
            vaksin.setNamaVaksin(namaVaksin);
            vaksin.setPeternak(peternakResponse);
            vaksin.setPetugas(petugasResponse);
            vaksin.setHewan(hewanResponse);

            return vaksinRepository.update(vaksinId, vaksin);
        } else {
            return null;
        }
    }

    public void deleteVaksinById(String vaksinId) throws IOException {
        vaksinRepository.deleteById(vaksinId);
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
    public void createBulkVaksin(List<VaksinRequest> vaksinRequests) throws IOException {

        System.out.println("Memulai proses penyimpanan data vaksin secara bulk...");

        List<Vaksin> vaksinList = new ArrayList<>();
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (VaksinRequest request : vaksinRequests) {
            try {
                // if (vaksinRepository.existsByIdVaksin(request.getIdVaksin())) {
                // System.out.println("Data vaksin dengan ID " + request.getIdVaksin() + " sudah
                // terdaftar.");
                // skippedExisting++;
                // continue;
                // }

                Petugas petugasResponse = petugasRepository.findByNik(request.getNikPetugas());
                if (petugasResponse == null) {
                    System.out
                            .println("Data petugas vaksin dengan NIK " + request.getNikPetugas() + " tidak ditemukan.");
                    skippedIncomplete++;
                    continue;
                }

                Hewan hewanResponse = hewanRepository.findByNoEartag(request.getKodeEartagNasional());
                if (hewanResponse == null) {
                    System.out.println("Data hewan dengan ID " + request.getKodeEartagNasional() + " tidak ditemukan.");
                    skippedIncomplete++;
                    continue;
                }

                if (request.getVaksinKe() == null) {
                    System.out.println(
                            "Data vaksin Ke-  " + request.getVaksinKe() + " tidak memiliki urutan vaksin.");
                    skippedIncomplete++;
                    continue;
                }

                JenisVaksin jenisVaksinResponse = jenisVaksinRepository.findByJenisVaksin(request.getJenis());
                if (jenisVaksinResponse == null) {
                    System.out.println("Data jenis vaksin dengan jenis " + request.getJenis()
                            + " tidak ditemukan");
                }

                NamaVaksin namaVaksinResponse = namaVaksinRepository.findByNamaVaksin(request.getNama());
                if (namaVaksinResponse == null) {
                    System.out.println("Data nama vaksin dengan nama " + request.getNama()
                            + " tidak ditemukan");

                    // NamaVaksin namaVaksin = new NamaVaksin();
                    // namaVaksin.setIdNamaVaksin(request.getNamaVaksin());
                    // namaVaksin.setNamaVaksin("nama vaksin tidak valid");
                    // namaVaksin.setDeskripsi("deskripsi tidak valid");
                }

                Peternak peternakResponse = peternakRepository.findByNikPeternak(request.getNikPeternak());
                if (peternakResponse == null) {
                    System.out.println("Data peternak dengan NIK " + request.getNikPeternak()
                            + " tidak ditemukan. Membuat peternak default");

                    Peternak peternak = new Peternak();
                    peternak.setNikPeternak(request.getNikPeternak());
                    peternak.setNamaPeternak("nama peternak tidak valid");
                }

                Vaksin vaksin = new Vaksin();
                vaksin.setPeternak(peternakResponse);
                vaksin.setPetugas(petugasResponse);
                vaksin.setJenisVaksin(jenisVaksinResponse);
                vaksin.setNamaVaksin(namaVaksinResponse);
                vaksin.setHewan(hewanResponse);
                vaksin.setIdVaksin(request.getIdVaksin());
                vaksin.setTglVaksin(request.getTglVaksin());
                vaksin.setBatchVaksin(request.getBatchVaksin());
                vaksin.setVaksinKe(request.getVaksinKe());
                vaksin.setTglPendataan(request.getTglPendataan());

                // namaVaksin.setJenisVaksin(jenisVaksinResponse);

                vaksinList.add(vaksin);
                System.out.println("Menambahkan data vaksin ke dalam daftar: " + vaksin.getIdVaksin());
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data vaksin: " + request.getIdVaksin());
                e.printStackTrace();
            }
        }
        if (!vaksinList.isEmpty()) {
            System.out.println("Menyimpan data vaksin yang valid...");
            vaksinRepository.saveAll(vaksinList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + vaksinList.size());
        } else {
            System.out.println("Tidak ada data peternak baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);

    }

}

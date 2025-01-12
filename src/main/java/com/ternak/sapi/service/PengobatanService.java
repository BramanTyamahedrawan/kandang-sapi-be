package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Inseminasi;
import com.ternak.sapi.model.Pengobatan;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.InseminasiRequest;
import com.ternak.sapi.payload.PengobatanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.PengobatanRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.util.AppConstants;

import org.apache.hadoop.yarn.webapp.hamlet.Hamlet.LI;
import org.springframework.stereotype.Service;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PengobatanService {
    private PengobatanRepository pengobatanRepository = new PengobatanRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(PengobatanService.class);

    public PagedResponse<Pengobatan> getAllPengobatan(int page, int size, String petugasId) throws IOException {
        validatePageNumberAndSize(page, size);
        List<Pengobatan> pengobatanResponse;
        if (petugasId.equalsIgnoreCase("*")) {
            pengobatanResponse = pengobatanRepository.findAll(size);
        } else {
            pengobatanResponse = pengobatanRepository.findPengobatanByPetugas(petugasId, size);
        }

        // Retrieve Polls

        return new PagedResponse<>(pengobatanResponse, pengobatanResponse.size(), "Successfully get data", 200);
    }

    public Pengobatan createPengobatan(PengobatanRequest pengobatanRequest) throws IOException {
        Pengobatan pengobatan = new Pengobatan();
        Petugas petugasResponse = petugasRepository.findById(pengobatanRequest.getPetugas_id().toString());
        if (petugasResponse.getNamaPetugas() != null) {
            pengobatan.setIdKasus(pengobatanRequest.getIdKasus());
            pengobatan.setTanggalPengobatan(pengobatanRequest.getTanggalPengobatan());
            pengobatan.setTanggalKasus(pengobatanRequest.getTanggalKasus());
            pengobatan.setNamaInfrastruktur(pengobatanRequest.getNamaInfrastruktur());
            pengobatan.setLokasi(pengobatanRequest.getLokasi());
            pengobatan.setDosis(pengobatanRequest.getDosis());
            pengobatan.setSindrom(pengobatanRequest.getSindrom());
            pengobatan.setDiagnosaBanding(pengobatanRequest.getDiagnosaBanding());
            pengobatan.setPetugas(petugasResponse);

            return pengobatanRepository.save(pengobatan);
        } else {
            return null;
        }
    }

    public DefaultResponse<Pengobatan> getPengobatanById(String pengobatanId) throws IOException {
        // Retrieve Pengobatan
        Pengobatan pengobatanResponse = pengobatanRepository.findPengobatanById(pengobatanId);
        return new DefaultResponse<>(pengobatanResponse.isValid() ? pengobatanResponse : null,
                pengobatanResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Pengobatan updatePengobatan(String pengobatanId, PengobatanRequest pengobatanRequest) throws IOException {
        Pengobatan pengobatan = new Pengobatan();
        Petugas petugasResponse = petugasRepository.findById(pengobatanRequest.getPetugas_id().toString());
        if (petugasResponse.getNamaPetugas() != null) {
            pengobatan.setIdKasus(pengobatanRequest.getIdKasus());
            pengobatan.setTanggalPengobatan(pengobatanRequest.getTanggalPengobatan());
            pengobatan.setTanggalKasus(pengobatanRequest.getTanggalKasus());
            pengobatan.setNamaInfrastruktur(pengobatanRequest.getNamaInfrastruktur());
            pengobatan.setLokasi(pengobatanRequest.getLokasi());
            pengobatan.setDosis(pengobatanRequest.getDosis());
            pengobatan.setSindrom(pengobatanRequest.getSindrom());
            pengobatan.setDiagnosaBanding(pengobatanRequest.getDiagnosaBanding());
            pengobatan.setPetugas(petugasResponse);

            return pengobatanRepository.update(pengobatanId, pengobatan);
        } else {
            return null;
        }
    }

    public void deletePengobatanById(String pengobatanId) throws IOException {
        Pengobatan pengobatanResponse = pengobatanRepository.findPengobatanById(pengobatanId);
        if (pengobatanResponse.isValid()) {
            pengobatanRepository.deleteById(pengobatanId);
        } else {
            throw new ResourceNotFoundException("Pengobatan", "id", pengobatanId);
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
    public void createPengobatanImport(List<PengobatanRequest> pengobatanRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data Pengobatan buatan secara bulk...");

        List<Pengobatan> pengobatanList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>();
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (PengobatanRequest request : pengobatanRequests) {
            try {
                if (existingIds.contains(request.getIdKasus())) {
                    skippedExisting++;
                    continue;
                }

                Pengobatan pengobatan = new Pengobatan();
                pengobatan.setIdKasus(request.getIdKasus());
                pengobatan.setTanggalPengobatan(request.getTanggalPengobatan() != null ? request.getTanggalPengobatan()
                        : "-");
                pengobatan.setTanggalKasus(request.getTanggalKasus() != null ? request.getTanggalKasus() : "-");
                pengobatan.setNamaInfrastruktur(request.getNamaInfrastruktur() != null ? request.getNamaInfrastruktur()
                        : "-");
                pengobatan.setLokasi(request.getLokasi() != null ? request.getLokasi() : "-");
                pengobatan.setDosis(request.getDosis() != null ? request.getDosis() : "-");
                pengobatan.setSindrom(request.getSindrom() != null ? request.getSindrom() : "-");
                pengobatan
                        .setDiagnosaBanding(request.getDiagnosaBanding() != null ? request.getDiagnosaBanding() : "-");
                pengobatan.setProvinsiPengobatan(
                        request.getProvinsiPengobatan() != null ? request.getProvinsiPengobatan() : "-");
                pengobatan.setKabupatenPengobatan(
                        request.getKabupatenPengobatan() != null ? request.getKabupatenPengobatan() : "-");
                pengobatan.setKecamatanPengobatan(
                        request.getKecamatanPengobatan() != null ? request.getKecamatanPengobatan() : "-");
                pengobatan.setDesaPengobatan(request.getDesaPengobatan() != null ? request.getDesaPengobatan() : "-");

                System.out.println("nama petugas diterima dari frontend: " + request.getNamaPetugas());

                Petugas petugasResponse = null;
                if (request.getNamaPetugas() == null) {
                    System.out.println("Nama petugas tidak ditemukan. lewati proses penyimpanan data pengobatan ini.");
                    skippedIncomplete++;
                    continue;
                } else {
                    petugasResponse = petugasRepository.findByNamaPetugas(request.getNamaPetugas());
                    if (petugasResponse == null) {
                        System.out.println("Nama Petugas tidak ditemukan di database. Membuat petugas baru...");

                        Petugas newPetugas = new Petugas();
                        newPetugas
                                .setNikPetugas(request.getNikPetugas() != null ? request.getNikPetugas()
                                        : "NIK Kosong");
                        newPetugas.setNamaPetugas(request.getNamaPetugas());
                        newPetugas.setEmail(
                                request.getEmail() != null ? request.getEmail() : "-");
                        newPetugas.setNoTelp(
                                request.getNoTelp() != null ? request.getNoTelp() : "-");
                        newPetugas.setJob(request.getJob() != null ? request.getJob() : "Pengobatan");
                        newPetugas.setWilayah(request.getWilayah() != null ? request.getWilayah() : "-");

                        petugasResponse = petugasRepository.saveImport(newPetugas);
                        System.out.println("Petugas baru berhasil dibuat: " + newPetugas.getNamaPetugas());
                    } else {
                        System.out.println("Petugas ditemukan di database: " + petugasResponse.getNamaPetugas());
                    }
                }

                pengobatan.setPetugas(petugasResponse);
                existingIds.add(request.getIdKasus());
                pengobatanList.add(pengobatan);

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data pengobatan: " + request);
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        if (!pengobatanList.isEmpty()) {
            System.out.println("Menyimpan data pengobatan yang valid...");
            pengobatanRepository.saveImport(pengobatanList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + pengobatanList.size());
        } else {
            System.out.println("Tidak ada data hewan baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);
    }

}

package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Pengobatan;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.PengobatanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.PengobatanRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.util.AppConstants;

import org.springframework.stereotype.Service;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

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

        if (pengobatanRepository.existsById(pengobatanRequest.getIdPengobatan())) {
            throw new BadRequestException("Id Pengobatan " + pengobatanRequest.getIdPengobatan() + " sudah terdaftar.");
        }

        Pengobatan pengobatan = new Pengobatan();
        Petugas petugasResponse = petugasRepository.findById(pengobatanRequest.getPetugasId().toString());
        if (petugasResponse.getPetugasId() != null) {
            pengobatan.setIdPengobatan(pengobatanRequest.getIdPengobatan());
            pengobatan.setIdKasus(pengobatanRequest.getIdKasus());
            pengobatan.setTanggalPengobatan(pengobatanRequest.getTanggalPengobatan());
            pengobatan.setTanggalKasus(pengobatanRequest.getTanggalKasus());
            pengobatan.setNamaInfrastruktur(pengobatanRequest.getNamaInfrastruktur());
            pengobatan.setDosis(pengobatanRequest.getDosis());
            pengobatan.setSindrom(pengobatanRequest.getSindrom());
            pengobatan.setDiagnosaBanding(pengobatanRequest.getDiagnosaBanding());
            pengobatan.setLokasi(pengobatanRequest.getLokasi());
            pengobatan.setProvinsiPengobatan(pengobatanRequest.getProvinsiPengobatan());
            pengobatan.setKabupatenPengobatan(pengobatanRequest.getKabupatenPengobatan());
            pengobatan.setKecamatanPengobatan(pengobatanRequest.getKecamatanPengobatan());
            pengobatan.setDesaPengobatan(pengobatanRequest.getDesaPengobatan());
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
        Petugas petugasResponse = petugasRepository.findById(pengobatanRequest.getPetugasId().toString());
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
                if (existingIds.contains(request.getIdPengobatan())) {
                    skippedExisting++;
                    continue;
                }

                Pengobatan pengobatan = new Pengobatan();
                pengobatan.setIdPengobatan(request.getIdPengobatan());
                pengobatan.setIdKasus(request.getIdKasus());
                pengobatan.setTanggalPengobatan(request.getTanggalPengobatan());
                pengobatan.setTanggalKasus(request.getTanggalKasus());
                pengobatan.setNamaInfrastruktur(request.getNamaInfrastruktur());
                pengobatan.setLokasi(request.getLokasi());
                pengobatan.setDosis(request.getDosis());
                pengobatan.setSindrom(request.getSindrom());
                pengobatan.setDiagnosaBanding(request.getDiagnosaBanding());
                pengobatan.setProvinsiPengobatan(request.getProvinsiPengobatan());
                pengobatan.setKabupatenPengobatan(request.getKabupatenPengobatan());
                pengobatan.setKecamatanPengobatan(request.getKecamatanPengobatan());
                pengobatan.setDesaPengobatan(request.getDesaPengobatan());

                System.out.println("nama petugas diterima dari frontend: " + request.getNamaPetugas());

                Petugas petugasResponse = null;
                if (request.getNamaPetugas() == null) {
                   continue;
                }else{
                    petugasResponse = petugasRepository.findByNamaPetugas(request.getNamaPetugas());
                    if(petugasResponse == null) {
                        System.err.println("Nama petugas"+ request.getNamaPetugas() +" tidak ditemukan didata base, new petugas..");
                        Petugas petugas = new Petugas();
                        petugas.setPetugasId(UUID.randomUUID().toString());
                        petugas.setNikPetugas(request.getNikPetugas() != null ? request.getNikPetugas() : "-");
                        petugas.setNamaPetugas(request.getNamaPetugas() != null ? request.getNamaPetugas() : "Nama petugas tidak ditemukan");
                        petugas.setEmail(request.getEmail() != null ? request.getEmail() : "-");
                        petugas.setNoTelp(request.getNoTelp() != null ? request.getNoTelp() : "-");
                        petugas.setWilayah(request.getWilayah() != null ? request.getWilayah() : "-");
                        petugas.setJob("Petugas Pengobatan");

                        petugasResponse = petugasRepository.saveImport(petugas);
                    }else{
                        System.err.println("Nama Petugas " + request.getNamaPetugas() + "ditemukan");
                    }

                }

                pengobatan.setPetugas(petugasResponse);
                existingIds.add(request.getIdPengobatan());
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

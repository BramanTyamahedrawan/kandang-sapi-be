package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Pengobatan;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.PengobatanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.PengobatanRepository;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.util.AppConstants;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PengobatanService {
    private PengobatanRepository pengobatanRepository = new PengobatanRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();

    private static final Logger logger = LoggerFactory.getLogger(PengobatanService.class);


    public PagedResponse<Pengobatan> getAllPengobatan(int page, int size, String petugasId) throws IOException {
        validatePageNumberAndSize(page, size);
        List<Pengobatan> pengobatanResponse;
        if(petugasId.equalsIgnoreCase("*")){
            pengobatanResponse = pengobatanRepository.findAll(size);
        }else{
            pengobatanResponse = pengobatanRepository.findPengobatanByPetugas(petugasId, size);
        }

        // Retrieve Polls


        return new PagedResponse<>(pengobatanResponse, pengobatanResponse.size(), "Successfully get data", 200);
    }

    public Pengobatan createPengobatan(PengobatanRequest pengobatanRequest) throws IOException {
        Pengobatan pengobatan = new Pengobatan();
        Petugas petugasResponse = petugasRepository.findById(pengobatanRequest.getPetugas_id().toString());
        if (petugasResponse.getNamaPetugas()!= null) {
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
        return new DefaultResponse<>(pengobatanResponse.isValid() ? pengobatanResponse : null, pengobatanResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Pengobatan updatePengobatan(String pengobatanId, PengobatanRequest pengobatanRequest) throws IOException {
        Pengobatan pengobatan = new Pengobatan();
        Petugas petugasResponse = petugasRepository.findById(pengobatanRequest.getPetugas_id().toString());
        if (petugasResponse.getNamaPetugas()!= null) {
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
        if(pengobatanResponse.isValid()){
            pengobatanRepository.deleteById(pengobatanId);
        }else{
            throw new ResourceNotFoundException("Pengobatan", "id", pengobatanId);
        }
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

}

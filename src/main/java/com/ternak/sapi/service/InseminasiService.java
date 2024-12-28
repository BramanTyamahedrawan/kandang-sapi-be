package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Inseminasi;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.InseminasiRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.InseminasiRepository;
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
public class InseminasiService {
    private InseminasiRepository inseminasiRepository = new InseminasiRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private HewanRepository hewanRepository = new HewanRepository();

    private static final Logger logger = LoggerFactory.getLogger(InseminasiService.class);


    public PagedResponse<Inseminasi> getAllInseminasi(int page, int size, String peternakId, String petugasId, String hewanId) throws IOException {
        validatePageNumberAndSize(page, size);
        List<Inseminasi> inseminasiResponse;
        if(peternakId.equalsIgnoreCase("*")){
            inseminasiResponse = inseminasiRepository.findAll(size);
        }else{
            inseminasiResponse = inseminasiRepository.findInseminasiByPeternak(peternakId, size);
        }

        // Retrieve Polls


        return new PagedResponse<>(inseminasiResponse, inseminasiResponse.size(), "Successfully get data", 200);
    }

    public Inseminasi createInseminasi(InseminasiRequest inseminasiRequest) throws IOException {
        Inseminasi inseminasi = new Inseminasi();
        Peternak peternakResponse = peternakRepository.findById(inseminasiRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(inseminasiRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(inseminasiRequest.getHewan_id().toString());
        if (peternakResponse.getNamaPeternak()!= null && petugasResponse.getNamaPetugas()!= null && hewanResponse.getIdHewan()!= null) {
        inseminasi.setIdInseminasi(inseminasiRequest.getIdInseminasi());
        inseminasi.setTanggalIB(inseminasiRequest.getTanggalIB());
        inseminasi.setIb(inseminasiRequest.getIb());
        inseminasi.setIdPejantan(inseminasiRequest.getIdPejantan());
        inseminasi.setIdPembuatan(inseminasiRequest.getIdPembuatan());
        inseminasi.setBangsaPejantan(inseminasiRequest.getBangsaPejantan());
        inseminasi.setProdusen(inseminasiRequest.getProdusen());
        inseminasi.setPeternak(peternakResponse);
        inseminasi.setPetugas(petugasResponse);
        inseminasi.setHewan(hewanResponse);

        return inseminasiRepository.save(inseminasi);
        } else {
            return null;
        }
    }

    public DefaultResponse<Inseminasi> getInseminasiById(String inseminasiId) throws IOException {
        // Retrieve Inseminasi
        Inseminasi inseminasiResponse = inseminasiRepository.findInseminasiById(inseminasiId);
        return new DefaultResponse<>(inseminasiResponse.isValid() ? inseminasiResponse : null, inseminasiResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Inseminasi updateInseminasi(String inseminasiId, InseminasiRequest inseminasiRequest) throws IOException {
        Inseminasi inseminasi = new Inseminasi();
        Peternak peternakResponse = peternakRepository.findById(inseminasiRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(inseminasiRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(inseminasiRequest.getHewan_id().toString());
        
            inseminasi.setTanggalIB(inseminasiRequest.getTanggalIB());
            inseminasi.setIb(inseminasiRequest.getIb());
            inseminasi.setIdPejantan(inseminasiRequest.getIdPejantan());
            inseminasi.setIdPembuatan(inseminasiRequest.getIdPembuatan());
            inseminasi.setBangsaPejantan(inseminasiRequest.getBangsaPejantan());
            inseminasi.setProdusen(inseminasiRequest.getProdusen());
            inseminasi.setPeternak(peternakResponse);
            inseminasi.setPetugas(petugasResponse);
            inseminasi.setHewan(hewanResponse);

            return inseminasiRepository.update(inseminasiId, inseminasi);
        
    }

    public void deleteInseminasiById(String inseminasiId) throws IOException {
        Inseminasi inseminasiResponse = inseminasiRepository.findInseminasiById(inseminasiId);
        if(inseminasiResponse.isValid()){
            inseminasiRepository.deleteById(inseminasiId);
        }else{
            throw new ResourceNotFoundException("Inseminasi", "id", inseminasiId);
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

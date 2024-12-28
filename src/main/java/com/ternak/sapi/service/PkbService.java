package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Pkb;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.PkbRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.PkbRepository;
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
public class PkbService {
    private PkbRepository pkbRepository = new PkbRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private HewanRepository hewanRepository = new HewanRepository();

    private static final Logger logger = LoggerFactory.getLogger(PkbService.class);


    public PagedResponse<Pkb> getAllPkb(int page, int size, String peternakId, String petugasId, String hewanId) throws IOException {
        validatePageNumberAndSize(page, size);
        List<Pkb> pkbResponse;
        if(peternakId.equalsIgnoreCase("*")){
            pkbResponse = pkbRepository.findAll(size);
        }else{
            pkbResponse = pkbRepository.findPkbByPeternak(peternakId, size);
        }

        // Retrieve Polls


        return new PagedResponse<>(pkbResponse, pkbResponse.size(), "Successfully get data", 200);
    }

    public Pkb createPkb(PkbRequest pkbRequest) throws IOException {
        Pkb pkb = new Pkb();
        Peternak peternakResponse = peternakRepository.findById(pkbRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(pkbRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(pkbRequest.getHewan_id().toString());
        if (peternakResponse.getNamaPeternak()!= null && petugasResponse.getNamaPetugas()!= null && hewanResponse.getIdHewan()!= null) {
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
        return new DefaultResponse<>(pkbResponse.isValid() ? pkbResponse : null, pkbResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Pkb updatePkb(String pkbId, PkbRequest pkbRequest) throws IOException {
        Pkb pkb = new Pkb();
        Peternak peternakResponse = peternakRepository.findById(pkbRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(pkbRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(pkbRequest.getHewan_id().toString());
        if (peternakResponse.getNamaPeternak()!= null && petugasResponse.getNamaPetugas()!= null && hewanResponse.getIdHewan()!= null) {
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
        if(pkbResponse.isValid()){
            pkbRepository.deleteById(pkbId);
        }else{
            throw new ResourceNotFoundException("Pkb", "id", pkbId);
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

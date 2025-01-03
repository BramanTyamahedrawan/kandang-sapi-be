package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Inseminasi;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.KelahiranRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.KelahiranRepository;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.InseminasiRepository;
import com.ternak.sapi.repository.KandangRepository;
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
public class KelahiranService {
    private KelahiranRepository kelahiranRepository = new KelahiranRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private HewanRepository hewanRepository = new HewanRepository();
    private KandangRepository kandangRepository = new KandangRepository();
    private InseminasiRepository inseminasiRepository = new InseminasiRepository();

    private static final Logger logger = LoggerFactory.getLogger(KelahiranService.class);


    public PagedResponse<Kelahiran> getAllKelahiran(int page, int size, String peternakId, String petugasId, String hewanId, String inseminasiId) throws IOException {
        validatePageNumberAndSize(page, size);
        List<Kelahiran> kelahiranResponse;
        if(peternakId.equalsIgnoreCase("*")){
            kelahiranResponse = kelahiranRepository.findAll(size);
        }else{
            kelahiranResponse = kelahiranRepository.findKelahiranByPeternak(peternakId, size);
        }

        // Retrieve Polls


        return new PagedResponse<>(kelahiranResponse, kelahiranResponse.size(), "Successfully get data", 200);
    }

    public Kelahiran createKelahiran(KelahiranRequest kelahiranRequest) throws IOException {
        Kelahiran kelahiran = new Kelahiran();
        Peternak peternakResponse = peternakRepository.findById(kelahiranRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(kelahiranRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(kelahiranRequest.getHewan_id().toString());
        Kandang kandangResponse = kandangRepository.findById(kelahiranRequest.getKandang_id().toString());
        Inseminasi inseminasiResponse = inseminasiRepository.findInseminasiById(kelahiranRequest.getInseminasi_id().toString());
        if (peternakResponse.getNamaPeternak()!= null && petugasResponse.getNamaPetugas()!= null ) {
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

    public DefaultResponse<Kelahiran> getKelahiranById(String kelahiranId) throws IOException {
        // Retrieve Kelahiran
        Kelahiran kelahiranResponse = kelahiranRepository.findKelahiranById(kelahiranId);
        return new DefaultResponse<>(kelahiranResponse.isValid() ? kelahiranResponse : null, kelahiranResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Kelahiran updateKelahiran(String kelahiranId, KelahiranRequest kelahiranRequest) throws IOException {
        Kelahiran kelahiran = new Kelahiran();
        Peternak peternakResponse = peternakRepository.findById(kelahiranRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(kelahiranRequest.getPetugas_id().toString());
        Hewan hewanResponse = hewanRepository.findById(kelahiranRequest.getHewan_id().toString());
        Kandang kandangResponse = kandangRepository.findById(kelahiranRequest.getKandang_id().toString());
        Inseminasi inseminasiResponse = inseminasiRepository.findInseminasiById(kelahiranRequest.getInseminasi_id().toString());
        if (peternakResponse.getNamaPeternak()!= null && petugasResponse.getNamaPetugas()!= null && hewanResponse.getIdHewan()!= null) {
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
        if(kelahiranResponse.isValid()){
            kelahiranRepository.deleteById(kelahiranId);
        }else{
            throw new ResourceNotFoundException("Kelahiran", "id", kelahiranId);
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

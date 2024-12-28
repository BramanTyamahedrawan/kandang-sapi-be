package com.ternak.sapi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.repository.JenisHewanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.KandangRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.KandangRepository;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.util.AppConstants;

@Service
public class KandangService {
    private KandangRepository kandangRepository = new KandangRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private JenisHewanRepository jenisHewanRepository = new JenisHewanRepository();

    private static final Logger logger = LoggerFactory.getLogger(KandangService.class);


    public PagedResponse<Kandang> getAllKandang(int page, int size, String peternakID) throws IOException {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        List<Kandang> kandangResponse = new ArrayList<>();

        if(peternakID.equalsIgnoreCase("*")){
            kandangResponse = kandangRepository.findAll(size);
        }else{
            kandangResponse = kandangRepository.findKandangByPeternak(peternakID, size);
        }

        return new PagedResponse<>(kandangResponse, kandangResponse.size(), "Successfully get data", 200);
    }

     public Kandang createKandang(KandangRequest kandangRequest, String savePath) throws IOException {
         // Validasi ID Kandang
         if (kandangRepository.existsByIdKandang(kandangRequest.getIdKandang())) {
             throw new IllegalArgumentException("ID Kandang sudah terdaftar!");
         }

         // Validasi Alamat
//         if (kandangRepository.existsByAlamat(kandangRequest.getAlamat())) {
//             throw new IllegalArgumentException("Alamat Kandang sudah terdaftar!");
//         }

        Kandang kandang = new Kandang();
        Peternak peternakResponse = peternakRepository.findById(kandangRequest.getPeternak_id().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(kandangRequest.getJenisHewanId().toString());

        if (peternakResponse.getNamaPeternak() != null && jenisHewanResponse.getJenis() != null) {
            kandang.setIdKandang(kandangRequest.getIdKandang());
            kandang.setLuas(kandangRequest.getLuas());
            kandang.setKapasitas(kandangRequest.getKapasitas());
            kandang.setJenisKandang(kandangRequest.getJenisKandang());
            kandang.setNilaiBangunan(kandangRequest.getNilaiBangunan());
            kandang.setAlamat(kandangRequest.getAlamat());
            kandang.setLatitude(kandangRequest.getLatitude());
            kandang.setLongitude(kandangRequest.getLongitude());
            kandang.setFile_path(savePath);
            kandang.setNamaKandang(kandangRequest.getNamaKandang());
            kandang.setPeternak(peternakResponse);
            kandang.setJenisHewan(jenisHewanResponse);
            System.out.println(kandangRequest.getIdKandang());
            System.out.println(kandangRequest.getLuas());
            System.out.println(kandangRequest.getKapasitas());
            System.out.println(kandangRequest.getJenisKandang());
            System.out.println(kandangRequest.getNilaiBangunan());
            System.out.println(kandangRequest.getAlamat());
            System.out.println(kandangRequest.getLatitude());
            System.out.println(kandangRequest.getLongitude());
            System.out.println(savePath);
            System.out.println(kandangRequest.getNamaKandang());
            return kandangRepository.save(kandang);
        } else {
            return null;
        }
    }


    public DefaultResponse<Kandang> getKandangById(String kandangId) throws IOException {
        // Retrieve Kandang
        Kandang kandangResponse = kandangRepository.findById(kandangId);
        return new DefaultResponse<>(kandangResponse.isValid() ? kandangResponse : null, kandangResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Kandang updateKandang(String kandangId, KandangRequest kandangRequest, String savePath) throws IOException {
        Kandang kandang = new Kandang();
        Peternak peternakResponse = peternakRepository.findById(kandangRequest.getPeternak_id().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(kandangRequest.getJenisHewanId().toString());

        if (peternakResponse.getNamaPeternak()!= null) {
            kandang.setLuas(kandangRequest.getLuas());
            kandang.setKapasitas(kandangRequest.getKapasitas());
            kandang.setJenisKandang(kandangRequest.getJenisKandang());
            kandang.setNilaiBangunan(kandangRequest.getNilaiBangunan());
            kandang.setAlamat(kandangRequest.getAlamat());
            kandang.setLatitude(kandangRequest.getLatitude());
            kandang.setLongitude(kandangRequest.getLongitude());
            kandang.setNamaKandang(kandangRequest.getNamaKandang());
            kandang.setFile_path(savePath);
            kandang.setPeternak(peternakResponse);
            kandang.setJenisHewan(jenisHewanResponse);
            return kandangRepository.update(kandangId, kandang);
        } else {
            return null;
        }
    }

    public void deleteKandangById(String kandangId) throws IOException {
        Kandang kandangResponse = kandangRepository.findById(kandangId);
//        if(kandangResponse.isValid()){
            kandangRepository.deleteById(kandangId);
//        }else{
//            throw new ResourceNotFoundException("Kandang", "id", kandangId);
//        }
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
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Penghijauan;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.KandangRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.PenghijauanRequest;
import com.ternak.sapi.repository.PenghijauanRepository;
import com.ternak.sapi.util.AppConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author reyza1
 */
public class PenghijauanService {
     private PenghijauanRepository penghijauanRepository = new PenghijauanRepository();
     
     private static final Logger logger = LoggerFactory.getLogger(PenghijauanService.class);
     
     public PagedResponse<Penghijauan> getAllPenghijauan(int page, int size, String petugasID) throws IOException {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        List<Penghijauan> penghijauanResponse = new ArrayList<>();
         return null;
     }
     
      public Penghijauan createPenghijauan(PenghijauanRequest penghijauanRequest, String savePath) throws IOException {
        Penghijauan penghijauan = new Penghijauan();
        if (penghijauanRequest.getNamaTempat()!= null) {
            penghijauan.setIdPenghijauan(penghijauanRequest.getIdPenghijauan());
            penghijauan.setLokasi(penghijauanRequest.getLokasi());
            penghijauan.setKeterangan(penghijauanRequest.getKeterangan());
            penghijauan.setFile_path(savePath);
            return penghijauanRepository.save(penghijauan);
        } else {
            return null;
        }
    }
      
      public DefaultResponse<Penghijauan> getPenghijauanById(String penghijauanId) throws IOException {
        // Retrieve Kandang
        Penghijauan penghijauanResponse = penghijauanRepository.findById(penghijauanId);
        return new DefaultResponse<>(penghijauanResponse.isValid() ? penghijauanResponse : null, penghijauanResponse.isValid() ? 1 : 0, "Successfully get data");
    }
       public Penghijauan updatePenghijauan(String penghijauanId, PenghijauanRequest penghijauanRequest) throws IOException {
        Penghijauan penghijauan = new Penghijauan();
        if (penghijauanRequest.getNamaTempat()!= null) {
            penghijauan.setIdPenghijauan(penghijauanRequest.getIdPenghijauan());
            penghijauan.setLokasi(penghijauanRequest.getLokasi());
            penghijauan.setKeterangan(penghijauanRequest.getKeterangan());
            String savePath = null;
            penghijauan.setFile_path(savePath);
            return penghijauanRepository.update(penghijauanId, penghijauan);
        } else {
            return null;
        }
    }
       
       public void deletePenghijauanById(String penghijauanId) throws IOException {
        Penghijauan penghijauanResponse = penghijauanRepository.findById(penghijauanId);
        if(penghijauanResponse.isValid()){
            penghijauanRepository.deleteById(penghijauanId);
        }else{
            throw new ResourceNotFoundException("Kandang", "id", penghijauanId);
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

    public Penghijauan createPenghijauan(PenghijauanRequest penghijauanRequest) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

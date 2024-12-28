package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.JenisHewanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.JenisHewanRepository;
import com.ternak.sapi.repository.KandangRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.util.AppConstants;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JenisHewanService {
    private JenisHewanRepository jenishewanRepository = new JenisHewanRepository();


    public PagedResponse<JenisHewan> getAllJenisHewan(int page, int size, String peternakId, String hewanId, String kandangId) throws IOException {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        List<JenisHewan> jenishewanResponse = new ArrayList<>();

        jenishewanResponse = jenishewanRepository.findAll(size);

        return new PagedResponse<>(jenishewanResponse, jenishewanResponse.size(), "Successfully get data", 200);
    }

    public JenisHewan createJenisHewan(JenisHewanRequest jenishewanRequest) throws IOException {
        // Validasi jika jenis hewan sudah ada
        if (jenishewanRepository.existsByJenis(jenishewanRequest.getJenis())) {
            throw new IllegalArgumentException("Jenis Hewan sudah terdaftar!");
        }

        JenisHewan jenishewan = new JenisHewan();
        jenishewan.setIdJenisHewan(jenishewanRequest.getIdJenisHewan());
        jenishewan.setJenis(jenishewanRequest.getJenis());
        jenishewan.setDeskripsi(jenishewanRequest.getDeskripsi());
        return jenishewanRepository.save(jenishewan);
    }


    public DefaultResponse<JenisHewan> getJenisHewanById(String jenishewanId) throws IOException {
        // Retrieve Hewan
        JenisHewan jenishewanResponse = jenishewanRepository.findById(jenishewanId);
        return new DefaultResponse<>(jenishewanResponse.isValid() ? jenishewanResponse : null, jenishewanResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public JenisHewan updateJenisHewan(String jenishewanId, JenisHewanRequest jenishewanRequest, String savePath) throws IOException {
        JenisHewan jenishewan = new JenisHewan();
        jenishewan.setIdJenisHewan(jenishewanRequest.getIdJenisHewan());
        jenishewan.setJenis(jenishewanRequest.getJenis());
        jenishewan.setDeskripsi(jenishewanRequest.getDeskripsi());
        return jenishewanRepository.update(jenishewanId, jenishewan);
    }

    public void deleteJenisHewanById(String jenishewanId) throws IOException {
        JenisHewan jenishewanResponse = jenishewanRepository.findById(jenishewanId);
        if(jenishewanResponse.isValid()){
            jenishewanRepository.deleteById(jenishewanId);
        }else{
            throw new ResourceNotFoundException("JenisHewan", "id", jenishewanId);
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
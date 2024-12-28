package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Berita;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.BeritaRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.BeritaRepository;
import com.ternak.sapi.util.AppConstants;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BeritaService {
    private BeritaRepository beritaRepository = new BeritaRepository();

    private static final Logger logger = LoggerFactory.getLogger(BeritaService.class);


    public PagedResponse<Berita> getAllBerita(int page, int size) throws IOException {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        List<Berita> beritaResponse = beritaRepository.findAll(size);


        return new PagedResponse<>(beritaResponse, beritaResponse.size(), "Successfully get data", 200);
    }

    public Berita createBerita(BeritaRequest beritaRequest, String savePath) throws IOException {
        Berita berita = new Berita();

        berita.setJudul(beritaRequest.getJudul());
        berita.setTglPembuatan(beritaRequest.getTglPembuatan());
        berita.setIsiBerita(beritaRequest.getIsiBerita());
        berita.setPembuat(beritaRequest.getPembuat());
        berita.setFile_path(savePath);

        return beritaRepository.save(berita);
    }

    public DefaultResponse<Berita> getBeritaById(String beritaId) throws IOException {
        // Retrieve Berita
        Berita beritaResponse = beritaRepository.findById(beritaId);
        return new DefaultResponse<>(beritaResponse.isValid() ? beritaResponse : null, beritaResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Berita updateBerita(String beritaId, BeritaRequest beritaRequest, String savePath) throws IOException {
        Berita berita = new Berita();
        berita.setJudul(beritaRequest.getJudul());
        berita.setTglPembuatan(beritaRequest.getTglPembuatan());
        berita.setIsiBerita(beritaRequest.getIsiBerita());
        berita.setPembuat(beritaRequest.getPembuat());
        berita.setFile_path(savePath);

        return beritaRepository.update(beritaId, berita);
    }

    public void deleteBeritaById(String beritaId) throws IOException {
        Berita beritaResponse = beritaRepository.findById(beritaId);
        if(beritaResponse.isValid()){
            beritaRepository.deleteById(beritaId);
        }else{
            throw new ResourceNotFoundException("Berita", "id", beritaId);
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

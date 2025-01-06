package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.payload.JenisVaksinRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.JenisVaksinRepository;
// import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import com.ternak.sapi.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;

public class JenisVaksinService {
    private JenisVaksinRepository jenisVaksinRepository = new JenisVaksinRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(JenisVaksinService.class);

    public PagedResponse<JenisVaksin> getAllJenisVaksin(int page, int size, String userID) throws IOException {
        validatePageNumberAndSize(page, size);
        List<JenisVaksin> jenisVaksinResponse = new ArrayList<>();

        if (userID.equalsIgnoreCase("*"))
            jenisVaksinResponse = jenisVaksinRepository.findAll(size);
        if (!userID.equalsIgnoreCase("*"))
            jenisVaksinResponse = jenisVaksinRepository.findAllByUserID(userID, size);

        return new PagedResponse<>(jenisVaksinResponse, jenisVaksinResponse.size(), "Successfully get data", 200);
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
    public void createBulkJenisVaksin(List<JenisVaksinRequest> jenisVaksinRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data jenis hewan secara bulk...");

        List<JenisVaksin> jenisVaksinList = new ArrayList<>();
        int skippedIncomplete = 0;

        for (JenisVaksinRequest request : jenisVaksinRequests) {
            try {
                JenisVaksin jenisVaksin = new JenisVaksin();
                jenisVaksin.setIdJenisVaksin(request.getIdJenisVaksin());
                jenisVaksin.setNamaVaksin(request.getNamaVaksin());
                jenisVaksin.setDeskripsi(request.getDeskripsi());

                jenisVaksinList.add(jenisVaksin);
                System.out.println("Menambahkan data Jenis Vaksin ke dalam daftar: "
                        + jenisVaksin.getNamaVaksin());
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data: " + request);
                e.printStackTrace();
            }
        }

        if (!jenisVaksinList.isEmpty()) {
            System.out.println("Menyimpan data jenis vaksin yang valid...");
            jenisVaksinRepository.saveAll(jenisVaksinList);
            System.out.println("Data jenis vaksin berhasil disimpan. Total: " + jenisVaksinList.size());
        } else {
            System.out.println("Tidak ada data jenis vaksin baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete);
    }
}

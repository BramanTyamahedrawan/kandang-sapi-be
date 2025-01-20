package com.ternak.sapi.controller;

import org.springframework.web.bind.annotation.*;

import com.ternak.sapi.service.JenisVaksinService;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.model.NamaVaksin;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.JenisVaksinRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.ternak.sapi.service.NamaVaksinService;
import com.ternak.sapi.util.AppConstants;
import com.ternak.sapi.payload.NamaVaksinRequest;
import com.ternak.sapi.payload.PagedResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/namavaksin")
public class NamaVaksinController {

    private NamaVaksinService namaVaksinService = new NamaVaksinService();

    @GetMapping
    public PagedResponse<NamaVaksin> getNamaVaksin(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "peternakID", defaultValue = "*") String peternakID,
            @RequestParam(value = "jenisHewanID", defaultValue = "*") String jenisHewanID,
            @RequestParam(value = "namaVaksinID", defaultValue = "*") String namaVaksinID,
            @RequestParam(value = "userID", defaultValue = "*") String userID) throws IOException {
        return namaVaksinService.getAllNamaVaksin(page, size, userID, jenisHewanID, peternakID, namaVaksinID);
    }

    @PostMapping
    public ResponseEntity<?> createNamaVaksin(@Valid @RequestBody NamaVaksinRequest namaVaksinRequest) {
        try {
            NamaVaksin namaVaksin = namaVaksinService.create(namaVaksinRequest);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{idNamaVaksin}")
                    .buildAndExpand(namaVaksin.getIdNamaVaksin()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Nama Vaksin Created Successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An unexpected error occurred."));
        }
    }

    @PutMapping("/{idNamaVaksin}")
    public ResponseEntity<?> updateNamaVaksin(@PathVariable String idNamaVaksin,
                                               @Valid @RequestBody NamaVaksinRequest namaVaksinRequest) throws IOException {

        NamaVaksin namaVaksin = namaVaksinService.update(idNamaVaksin,namaVaksinRequest);

        if (namaVaksin == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Nama Vaksin ID not found"));
        } else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{idNamaVaksin}")
                    .buildAndExpand(namaVaksin.getIdNamaVaksin()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Nama Vaksin Updated Successfully"));
        }
    }

    @DeleteMapping("/{idNamaVaksin}")
    public HttpStatus deleteNamaVaksin(@PathVariable(value = "idNamaVaksin") String idNamaVaksin) throws IOException {
        namaVaksinService.deleteById(idNamaVaksin);
        return HttpStatus.NO_CONTENT;
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulkNamaVaksin(
            @RequestBody List<NamaVaksinRequest> namaVaksinListRequests) throws IOException {
        try {
            namaVaksinService.createBulkNamaVaksin(namaVaksinListRequests);
            return ResponseEntity.ok(new ApiResponse(true, "Bulk Nama Vaksin created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }
}

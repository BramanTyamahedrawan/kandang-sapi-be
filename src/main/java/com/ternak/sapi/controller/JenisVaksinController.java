package com.ternak.sapi.controller;

import com.ternak.sapi.model.TujuanPemeliharaan;
import com.ternak.sapi.payload.TujuanPemeliharaanRequest;
import org.springframework.web.bind.annotation.*;
import com.ternak.sapi.service.JenisVaksinService;
import com.ternak.sapi.util.AppConstants;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.JenisVaksinRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.JenisVaksinRepository;

import org.springframework.http.ResponseEntity;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos.GetResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/jenisvaksin")
public class JenisVaksinController {

    private JenisVaksinService jenisVaksinService = new JenisVaksinService();

    JenisVaksinRepository jenisVaksinRepository;

    @GetMapping
    public PagedResponse<JenisVaksin> getJenisVaksin(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "peternakID", defaultValue = "*") String peternakID,
            @RequestParam(value = "userID", defaultValue = "*") String userID) throws IOException {
        return jenisVaksinService.getAllJenisVaksin(page, size, userID, peternakID);
    }

    @PostMapping
    public ResponseEntity<?> createJenisVaksin(@Valid @RequestBody JenisVaksinRequest jenisVaksinRequest) {
        try {
            JenisVaksin jenisVaksin = jenisVaksinService.createJenisVaksin(jenisVaksinRequest);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{idJenisVaksin}")
                    .buildAndExpand(jenisVaksinRequest.getIdJenisVaksin()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Jenis Vaksin Created Successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An unexpected error occurred."));
        }
    }

    @PutMapping("/{idJenisVaksin}")
    public ResponseEntity<?> updateJenisVaksin(@PathVariable String idJenisVaksin,
            @Valid @RequestBody JenisVaksinRequest jenisVaksinRequest) throws IOException {

        JenisVaksin jenisVaksin = jenisVaksinService.update(idJenisVaksin, jenisVaksinRequest);

        if (jenisVaksin == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Tujuan ID not found"));
        } else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{idJenisVaksin}")
                    .buildAndExpand(jenisVaksin.getIdJenisVaksin()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Tujuan Updated Successfully"));
        }
    }

    @DeleteMapping("/{idJenisVaksin}")
    public HttpStatus deleteJenisVaksin(@PathVariable(value = "idJenisVaksin") String idJenisVaksin)
            throws IOException {
        jenisVaksinService.deleteById(idJenisVaksin);
        return HttpStatus.NO_CONTENT;
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulkJenisVaksin(
            @RequestBody List<JenisVaksinRequest> jenisVaksinListRequests) throws IOException {
        try {
            jenisVaksinService.createBulkJenisVaksin(jenisVaksinListRequests);
            return ResponseEntity.ok(new ApiResponse(true, "Bulk Jenis Vaksin created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }

}

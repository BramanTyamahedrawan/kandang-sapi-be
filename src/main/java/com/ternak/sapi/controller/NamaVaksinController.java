package com.ternak.sapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ternak.sapi.service.JenisVaksinService;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.JenisVaksinRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.ternak.sapi.service.NamaVaksinService;
import com.ternak.sapi.payload.NamaVaksinRequest;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/namavaksin")
public class NamaVaksinController {

    private NamaVaksinService namaVaksinService = new NamaVaksinService();

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

package com.ternak.sapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ternak.sapi.service.JenisVaksinService;
import com.ternak.sapi.util.AppConstants;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.JenisVaksinRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.JenisVaksinRepository;

import org.springframework.http.ResponseEntity;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos.GetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/jenisvaksin")
public class JenisVaksinController {

    private JenisVaksinService JenisVaksinService = new JenisVaksinService();

    JenisVaksinRepository jenisVaksinRepository;

    @GetMapping
    public PagedResponse<JenisVaksin> getJenisVaksin(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "peternakID", defaultValue = "*") String peternakID,
            @RequestParam(value = "jenisHewanID", defaultValue = "*") String jenisHewanID,
            @RequestParam(value = "userID", defaultValue = "*") String userID) throws IOException {
        return JenisVaksinService.getAllJenisVaksin(page, size, userID, jenisHewanID, peternakID);
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulkJenisVaksin(
            @RequestBody List<JenisVaksinRequest> jenisVaksinListRequests) throws IOException {
        try {
            JenisVaksinService.createBulkJenisVaksin(jenisVaksinListRequests);
            return ResponseEntity.ok(new ApiResponse(true, "Bulk Jenis Vaksin created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }

}

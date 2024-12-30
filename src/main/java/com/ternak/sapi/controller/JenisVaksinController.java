package com.ternak.sapi.controller;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.JenisVaksin;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ternak.sapi.repository.JenisVaksinRepository;
import com.ternak.sapi.service.JenisVaksinService;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.JenisVaksinRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/jenisvaksin")
public class JenisVaksinController {

    private JenisVaksinService JenisVaksinService = new JenisVaksinService();

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

package com.ternak.sapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
            @RequestParam(value = "userID", defaultValue = "*") String userID) throws IOException {
        return JenisVaksinService.getAllJenisVaksin(page, size, userID, peternakID);
    }

    @PostMapping
    public ResponseEntity<?> createJenisVaksin(@Valid @RequestBody JenisVaksinRequest jenisVaksinRequest)
            throws IOException {
        try {
            JenisVaksin jenisVaksin = JenisVaksinService.createJenisVaksin(jenisVaksinRequest);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{idJenisVaksin}")
                    .buildAndExpand(jenisVaksin.getIdJenisVaksin()).toUri();

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

    @GetMapping("/{jenisvaksinId}")
    public DefaultResponse<JenisVaksin> getJenisVaksinById(@PathVariable String jenisvaksinId) throws IOException {
        return JenisVaksinService.getJenisVaksinById(jenisvaksinId);
    }

    @PutMapping("/{jenisvaksinId}")
    public ResponseEntity<?> updateJenisVaksin(@PathVariable String jenisvaksinId,
            @Valid @RequestBody JenisVaksinRequest jenisVaksinRequest)
            throws IOException {
        JenisVaksin jenisVaksin = JenisVaksinService.updateJenisVaksin(jenisvaksinId, jenisVaksinRequest);

        if (jenisVaksin == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Jenis Vaksin ID not found"));
        } else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/jenisvaksinId}")
                    .buildAndExpand(jenisVaksin.getIdJenisVaksin()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Jenis Hewan Updated Successfully"));
        }

    }

    @DeleteMapping("/{jenisvaksinId}")
    public HttpStatus deleteJenisVaksin(@PathVariable(value = "jenisvaksinId") String jenisvaksinId)
            throws IOException {
        JenisVaksinService.deleteJenisVaksinById(jenisvaksinId);
        return HttpStatus.FORBIDDEN;
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

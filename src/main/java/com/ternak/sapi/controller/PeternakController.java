package com.ternak.sapi.controller;

import com.ternak.sapi.payload.*;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.service.PeternakService;
import com.ternak.sapi.service.PeternakService;
import com.ternak.sapi.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/peternak")
public class PeternakController {
    private PeternakService peternakService = new PeternakService();
    private static final Logger logger = LoggerFactory.getLogger(PeternakController.class);

    PeternakRepository peternakRepository;

    @GetMapping
    public PagedResponse<Peternak> getPeternaks(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "userID", defaultValue = "*") String userID) throws IOException {
        return peternakService.getAllPeternak(page, size, userID);
    }

    @PostMapping
    public ResponseEntity<?> createPeternak(@Valid @RequestBody PeternakRequest peternakRequest) {
        try {
            Peternak peternak = peternakService.createPeternak(peternakRequest);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{peternakId}")
                    .buildAndExpand(peternak.getIdPeternak()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Peternak Created Successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An unexpected error occurred."));
        }
    }

    @GetMapping("/{peternakId}")
    public DefaultResponse<Peternak> getPeternakById(@PathVariable String peternakId) throws IOException {
        return peternakService.getPeternakById(peternakId);
    }

    @PutMapping("/{peternakId}")
    public ResponseEntity<?> updatePeternak(@PathVariable String peternakId,
            @Valid @RequestBody PeternakRequest peternakRequest) throws IOException {
        Peternak peternak = peternakService.updatePeternak(peternakId, peternakRequest);

        if (peternak == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Peternak ID / User ID not found"));
        } else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{peternakId}")
                    .buildAndExpand(peternak.getIdPeternak()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Peternak Updated Successfully"));
        }
    }

    @DeleteMapping("/{peternakId}")
    public HttpStatus deletePeternak(@PathVariable(value = "peternakId") String peternakId) throws IOException {
        peternakService.deletePeternakById(peternakId);
        return HttpStatus.FORBIDDEN;
    }

    // @PostMapping("/bulk")
    // public ResponseEntity<?> createBulkPeternak(@RequestBody
    // List<PeternakRequest> peternakRequests) {
    // try {
    // // Log jumlah data yang diterima
    // logger.info("Jumlah data peternak yang diterima: {}",
    // peternakRequests.size());

    // // Log setiap data yang diterima
    // peternakRequests.forEach(request -> logger.info("Data peternak: {}",
    // request));

    // // Simpan data ke service (jika diperlukan nanti)
    // // peternakService.createBulkPeternak(peternakRequests);

    // return ResponseEntity.ok(new ApiResponse(true, "Data berhasil diterima,
    // tetapi belum disimpan."));
    // } catch (Exception e) {
    // logger.error("Terjadi kesalahan saat memproses data bulk: ", e);
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(new ApiResponse(false, "Failed to process bulk data: " +
    // e.getMessage()));
    // }
    // }

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulkPeternak(@RequestBody List<PeternakRequest> peternakRequests) {
        try {
            peternakService.createBulkPeternak(peternakRequests);
            return ResponseEntity.ok(new ApiResponse(true, "All Peternak Created Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create bulk data: " + e.getMessage()));
        }
    }
}

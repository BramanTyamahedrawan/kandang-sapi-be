package com.ternak.sapi.controller;

import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.KelahiranRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.service.KelahiranService;
import com.ternak.sapi.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/kelahiran")
public class KelahiranController {
    private KelahiranService kelahiranService = new KelahiranService();

    @GetMapping
    public PagedResponse<Kelahiran> getKelahirans(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "peternakID", defaultValue = "*") String peternakID,
            @RequestParam(value = "petugasID", defaultValue = "*") String petugasID,
            @RequestParam(value = "hewanID", defaultValue = "*") String hewanID,
            @RequestParam(value = "kandangID", defaultValue = "*") String kandangID,
            @RequestParam(value = "jenisHewanID", defaultValue = "*") String jenisHewanID,
            @RequestParam(value = "rumpunHewanID", defaultValue = "*") String rumpunHewanID,
            @RequestParam(value = "inseminasiID", defaultValue = "*") String inseminasiID) throws IOException {
        return kelahiranService.getAllKelahiran(page, size, peternakID, petugasID, hewanID, kandangID, jenisHewanID,
                rumpunHewanID,
                inseminasiID);
    }

    @PostMapping
    public ResponseEntity<?> createKelahiran(@Valid @RequestBody KelahiranRequest kelahiranRequest) throws IOException {
        Kelahiran kelahiran = kelahiranService.createKelahiran(kelahiranRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{kelahiranId}")
                .buildAndExpand(kelahiran.getIdKelahiran()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Kelahiran Created Successfully"));
    }

    @GetMapping("/{kelahiranId}")
    public DefaultResponse<Kelahiran> getKelahiranById(@PathVariable String kelahiranId) throws IOException {
        return kelahiranService.getKelahiranById(kelahiranId);
    }

    @PutMapping("/{kelahiranId}")
    public ResponseEntity<?> updateKelahiran(@PathVariable String kelahiranId,
            @Valid @RequestBody KelahiranRequest kelahiranRequest) throws IOException {
        Kelahiran kelahiran = kelahiranService.updateKelahiran(kelahiranId, kelahiranRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{kelahiranId}")
                .buildAndExpand(kelahiran.getIdKelahiran()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Kelahiran Updated Successfully"));
    }

    @DeleteMapping("/{kelahiranId}")
    public HttpStatus deleteKelahiran(@PathVariable(value = "kelahiranId") String kelahiranId) throws IOException {
        kelahiranService.deleteKelahiranById(kelahiranId);
        return HttpStatus.FORBIDDEN;
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulkKelahiran(@RequestBody List<KelahiranRequest> kelahiranRequests)
            throws IOException {
        try {
            System.out.println("Jumlah data yang diterima: " + kelahiranRequests.size());
            kelahiranService.createBulkKelahiran(kelahiranRequests);
            return ResponseEntity.ok(new ApiResponse(true, "Hewan Created Successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create bulk data: " + e.getMessage()));
        }
    }
}

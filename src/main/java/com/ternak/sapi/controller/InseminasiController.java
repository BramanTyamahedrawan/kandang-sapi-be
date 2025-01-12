package com.ternak.sapi.controller;

import com.ternak.sapi.model.Inseminasi;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.HewanRequest;
import com.ternak.sapi.payload.InseminasiRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.service.InseminasiService;
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
@RequestMapping("/api/inseminasi")
public class InseminasiController {
    private InseminasiService inseminasiService = new InseminasiService();

    @GetMapping
    public PagedResponse<Inseminasi> getInseminasis(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "peternakID", defaultValue = "*") String peternakID,
            @RequestParam(value = "petugasID", defaultValue = "*") String petugasID,
            @RequestParam(value = "hewanID", defaultValue = "*") String hewanID) throws IOException {
        return inseminasiService.getAllInseminasi(page, size, peternakID, petugasID, hewanID);
    }

    @PostMapping
    public ResponseEntity<?> createInseminasi(@Valid @RequestBody InseminasiRequest inseminasiRequest)
            throws IOException {
        Inseminasi inseminasi = inseminasiService.createInseminasi(inseminasiRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{inseminasiId}")
                .buildAndExpand(inseminasi.getIdInseminasi()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Inseminasi Created Successfully"));
    }

    @GetMapping("/{inseminasiId}")
    public DefaultResponse<Inseminasi> getInseminasiById(@PathVariable String inseminasiId) throws IOException {
        return inseminasiService.getInseminasiById(inseminasiId);
    }

    @PutMapping("/{inseminasiId}")
    public ResponseEntity<?> updateInseminasi(@PathVariable String inseminasiId,
            @Valid @RequestBody InseminasiRequest inseminasiRequest) throws IOException {
        Inseminasi inseminasi = inseminasiService.updateInseminasi(inseminasiId, inseminasiRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{inseminasiId}")
                .buildAndExpand(inseminasi.getIdInseminasi()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Inseminasi Updated Successfully"));
    }

    @DeleteMapping("/{inseminasiId}")
    public HttpStatus deleteInseminasi(@PathVariable(value = "inseminasiId") String inseminasiId) throws IOException {
        inseminasiService.deleteInseminasiById(inseminasiId);
        return HttpStatus.FORBIDDEN;
    }

    @PostMapping("/import")
    public ResponseEntity<?> createInseminasiImport(@RequestBody List<InseminasiRequest> inseminasiRequests)
            throws IOException {
        try {
            System.out.println("Jumlah data yang diterima: " + inseminasiRequests.size());
            inseminasiService.createInseminasiImport(inseminasiRequests);
            return ResponseEntity.ok(new ApiResponse(true, "Hewan Created Successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create bulk data: " + e.getMessage()));
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulkInseminasi(@RequestBody List<InseminasiRequest> inseminasiRequests)
            throws IOException {
        try {
            System.out.println("Jumlah data yang diterima: " + inseminasiRequests.size());
            inseminasiService.createBulkInseminasi(inseminasiRequests);
            return ResponseEntity.ok(new ApiResponse(true, "Hewan Created Successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create bulk data: " + e.getMessage()));
        }
    }

}

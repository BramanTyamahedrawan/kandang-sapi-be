package com.ternak.sapi.controller;

import com.ternak.sapi.model.Pkb;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.PkbRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.PengobatanRequest;
import com.ternak.sapi.service.PkbService;
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
@RequestMapping("/api/pkb")
public class PkbController {
    private PkbService pkbService = new PkbService();

    @GetMapping
    public PagedResponse<Pkb> getPkbs(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "peternakID", defaultValue = "*") String peternakID,
            @RequestParam(value = "petugasID", defaultValue = "*") String petugasID,
            @RequestParam(value = "jenisHewanID", defaultValue = "*") String jenisHewanID,
            @RequestParam(value = "rumpunHewanID", defaultValue = "*") String rumpunHewanID,
            @RequestParam(value = "hewanID", defaultValue = "*") String hewanID) throws IOException {
        return pkbService.getAllPkb(page, size, peternakID, petugasID, jenisHewanID, rumpunHewanID, hewanID);
    }

    @PostMapping
    public ResponseEntity<?> createPkb(@Valid @RequestBody PkbRequest pkbRequest) throws IOException {
        Pkb pkb = pkbService.createPkb(pkbRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pkbId}")
                .buildAndExpand(pkb.getIdKejadian()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Pkb Created Successfully"));
    }

    @GetMapping("/{pkbId}")
    public DefaultResponse<Pkb> getPkbById(@PathVariable String pkbId) throws IOException {
        return pkbService.getPkbById(pkbId);
    }

    @PutMapping("/{pkbId}")
    public ResponseEntity<?> updatePkb(@PathVariable String pkbId,
            @Valid @RequestBody PkbRequest pkbRequest) throws IOException {
        Pkb pkb = pkbService.updatePkb(pkbId, pkbRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pkbId}")
                .buildAndExpand(pkb.getIdKejadian()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Pkb Updated Successfully"));
    }

    @DeleteMapping("/{pkbId}")
    public HttpStatus deletePkb(@PathVariable(value = "pkbId") String pkbId) throws IOException {
        pkbService.deletePkbById(pkbId);
        return HttpStatus.FORBIDDEN;
    }

    @PostMapping("/import")
    public ResponseEntity<?> createPkbImport(@RequestBody List<PkbRequest> pkbRequests)
            throws IOException {
        try {
            System.out.println("Jumlah data yang diterima: " + pkbRequests.size());
            pkbService.createPkbImport(pkbRequests);
            return ResponseEntity.ok(new ApiResponse(true, "Pkb Created Successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create bulk data: " + e.getMessage()));
        }
    }
}

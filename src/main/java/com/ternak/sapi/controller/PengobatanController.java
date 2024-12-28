package com.ternak.sapi.controller;

import com.ternak.sapi.model.Pengobatan;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.PengobatanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.service.PengobatanService;
import com.ternak.sapi.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/pengobatan")
public class PengobatanController {
    private PengobatanService pengobatanService = new PengobatanService();

    @GetMapping
    public PagedResponse<Pengobatan> getPengobatans(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                                    @RequestParam(value = "petugasID", defaultValue = "*") String petugasID) throws IOException {
        return pengobatanService.getAllPengobatan(page, size, petugasID);
    }

    @PostMapping
    public ResponseEntity<?> createPengobatan(@Valid @RequestBody PengobatanRequest pengobatanRequest) throws IOException {
        Pengobatan pengobatan = pengobatanService.createPengobatan(pengobatanRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pengobatanId}")
                .buildAndExpand(pengobatan.getIdKasus()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Pengobatan Created Successfully"));
    }

    @GetMapping("/{pengobatanId}")
    public DefaultResponse<Pengobatan> getPengobatanById(@PathVariable String pengobatanId) throws IOException {
        return pengobatanService.getPengobatanById(pengobatanId);
    }


    @PutMapping("/{pengobatanId}")
    public ResponseEntity<?> updatePengobatan(@PathVariable String pengobatanId,
                                              @Valid @RequestBody PengobatanRequest pengobatanRequest) throws IOException {
        Pengobatan pengobatan = pengobatanService.updatePengobatan(pengobatanId, pengobatanRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pengobatanId}")
                .buildAndExpand(pengobatan.getIdKasus()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Pengobatan Updated Successfully"));
    }

    @DeleteMapping("/{pengobatanId}")
    public HttpStatus deletePengobatan(@PathVariable (value = "pengobatanId") String pengobatanId) throws IOException {
        pengobatanService.deletePengobatanById(pengobatanId);
        return HttpStatus.FORBIDDEN;
    }
}

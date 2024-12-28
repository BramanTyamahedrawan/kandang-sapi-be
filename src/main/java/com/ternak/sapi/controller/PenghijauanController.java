/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ternak.sapi.controller;

import com.ternak.sapi.model.Penghijauan;
import com.ternak.sapi.model.Pengobatan;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.PenghijauanRequest;
import com.ternak.sapi.payload.PengobatanRequest;
import com.ternak.sapi.service.PenghijauanService;
import com.ternak.sapi.util.AppConstants;
import java.io.IOException;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/api/penghijauan")
public class PenghijauanController {
      private PenghijauanService penghijauanService = new PenghijauanService();

    @GetMapping
    public PagedResponse<Penghijauan> getPenghijauans(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                                    @RequestParam(value = "petugasID", defaultValue = "*") String petugasID) throws IOException {
        return penghijauanService.getAllPenghijauan(page, size, petugasID);
        
    }
    
         @PostMapping
    public ResponseEntity<?> createPenghijauan(@Valid @RequestBody PenghijauanRequest penghijauanRequest) throws IOException {
        Penghijauan penghijauan = penghijauanService.createPenghijauan(penghijauanRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{penghijauanId}")
                .buildAndExpand(penghijauan.getIdPenghijauan()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Penghijauan Created Successfully"));
    }

    @GetMapping("/{penghijauanId}")
    public DefaultResponse<Penghijauan> getPenghijauanById(@PathVariable String penghijauanId) throws IOException {
        return penghijauanService.getPenghijauanById(penghijauanId);
    }
    
    @PutMapping("/{penghijauanId}")
    public ResponseEntity<?> updatePenghijauan(@PathVariable String penghijauanId,
                                              @Valid @RequestBody PenghijauanRequest penghijauanRequest) throws IOException {
        Penghijauan penghijauan = penghijauanService.updatePenghijauan(penghijauanId, penghijauanRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{penghijauanId}")
                .buildAndExpand(penghijauan.getIdPenghijauan()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Penghijauan Updated Successfully"));
    }
    
     @DeleteMapping("/{penghijauanId}")
    public HttpStatus deletePenghijauan(@PathVariable (value = "penghijauanId") String penghijauanId) throws IOException {
        penghijauanService.deletePenghijauanById(penghijauanId);
        return HttpStatus.FORBIDDEN;
    }
    
}

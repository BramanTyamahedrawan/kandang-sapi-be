package com.ternak.sapi.controller;

import com.ternak.sapi.config.PathConfig;
import com.ternak.sapi.payload.*;
import com.ternak.sapi.service.TujuanPemeliharaanService;
import com.ternak.sapi.util.AppConstants;
import java.io.ByteArrayOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.apache.hadoop.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/tujuanpemeliharaan")
public class TujuanPemeliharaanController {

    private TujuanPemeliharaanService tujuanPemeliharaanService = new TujuanPemeliharaanService();

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulkTujuanPemeliharaan(
            @RequestBody List<TujuanPemeliharaanRequest> tujuanPemeliharaanRequests) throws IOException {
        try {
            tujuanPemeliharaanService.createBulkTujuanPemeliharaan(tujuanPemeliharaanRequests);
            return ResponseEntity.ok(new ApiResponse(true, "Bulk Tujuan Pemeliharaan created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }
}
package com.ternak.sapi.controller;

import com.ternak.sapi.config.PathConfig;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.payload.*;
import com.ternak.sapi.service.RumpunHewanService;
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
@RequestMapping("/api/rumpunhewan")
public class RumpunHewanController {
    private RumpunHewanService rumpunhewanService = new RumpunHewanService();

    @GetMapping
    public PagedResponse<RumpunHewan> getRumpunHewans(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) throws IOException {
        return rumpunhewanService.getAllRumpunHewan(page, size);
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<byte[]> getFileFromHDFS(@PathVariable String fileName) {
        String uri = "hdfs://hadoop-primary:9000/rumpunhewan/" + fileName;
        // String uri = "hdfs://h-primary:6912/hewan/" + fileName;
        Configuration configuration = new Configuration();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            FileSystem fs = FileSystem.get(URI.create(uri), configuration);
            Path filePath = new Path(uri);

            if (!fs.exists(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            InputStream inputStream = fs.open(filePath);
            IOUtils.copyBytes(inputStream, outputStream, 4096, false);
            inputStream.close();
            fs.close();

            byte[] fileBytes = outputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(fileBytes.length);

            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> createRumpunHewan(@Valid @RequestBody RumpunHewanRequest rumpunhewanRequest) {
        try {
            RumpunHewan rumpunhewan = rumpunhewanService.createRumpunHewan(rumpunhewanRequest);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{idRumpunHewan}")
                    .buildAndExpand(rumpunhewan.getIdRumpunHewan()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Rumpun Hewan Created Successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An unexpected error occurred."));
        }
    }

    @GetMapping("/{rumpunhewanId}")
    public DefaultResponse<RumpunHewan> getRumpunHewanById(@PathVariable String rumpunhewanId) throws IOException {
        return rumpunhewanService.getRumpunHewanById(rumpunhewanId);
    }

    @PutMapping("/{rumpunhewanId}")
    public ResponseEntity<?> updateRumpunHewan(@PathVariable String rumpunhewanId,
            @RequestParam("file") MultipartFile file, @ModelAttribute RumpunHewanRequest rumpunhewanRequest)
            throws IOException {
        // upload file
        try {
            // Mendapatkan nama file asli
            String originalFileName = file.getOriginalFilename();

            // Mendapatkan ekstensi file
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // Mendapatkan timestamp saat ini
            String timestamp = String.valueOf(System.currentTimeMillis());

            // Membuat UUID baru
            String uuid = UUID.randomUUID().toString();

            // Menggabungkan timestamp dan UUID
            String newFileName = "file_" + timestamp + "_" + uuid;
            String filePath = PathConfig.storagePath + "/" + newFileName + fileExtension;
            File newFile = new File(filePath);

            // Menyimpan file ke lokasi yang ditentukan di server
            file.transferTo(newFile);

            // Mendapatkan local path dari file yang disimpan
            String localPath = newFile.getAbsolutePath();
            String uri = "hdfs://hadoop-primary:9000";
            String hdfsDir = "hdfs://hadoop-primary:9000/rumpunhewan/" + newFileName + fileExtension;
            // String uri = "hdfs://h-primary:6912";
            // String hdfsDir = "hdfs://h-primary:6912/rumpunhewan/" + newFileName +
            // fileExtension;
            Configuration configuration = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(uri), configuration);
            fs.copyFromLocalFile(new Path(localPath), new Path(hdfsDir));
            String savePath = "file/" + newFileName + fileExtension;

            newFile.delete();
            RumpunHewan rumpunhewan = rumpunhewanService.updateRumpunHewan(rumpunhewanId, rumpunhewanRequest, savePath);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{rumpunhewanId}")
                    .buildAndExpand(rumpunhewan.getIdRumpunHewan()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "RumpunHewan Updated Successfully"));
        } catch (IOException e) {
            // Penanganan kesalahan saat menyimpan file
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Cannot Upload File into Hadoop"));
        }

    }

    @DeleteMapping("/{rumpunhewanId}")
    public HttpStatus deleteRumpunHewan(@PathVariable(value = "rumpunhewanId") String rumpunhewanId)
            throws IOException {
        rumpunhewanService.deleteRumpunHewanById(rumpunhewanId);
        return HttpStatus.FORBIDDEN;
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulkRumpunHewan(@RequestBody List<RumpunHewanRequest> rumpunhewanRequests) {
        try {
            rumpunhewanService.createBulkRumpunHewan(rumpunhewanRequests);
            return ResponseEntity.ok(new ApiResponse(true, "Bulk Rumpun Hewan Created Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An unexpected error occurred."));
        }
    }
}

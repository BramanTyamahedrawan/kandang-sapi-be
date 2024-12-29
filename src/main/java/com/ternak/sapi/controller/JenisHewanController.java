package com.ternak.sapi.controller;

import com.ternak.sapi.config.PathConfig;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.payload.*;
import com.ternak.sapi.service.JenisHewanService;
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
@RequestMapping("/api/jenishewan")
public class JenisHewanController {
    private JenisHewanService jenishewanService = new JenisHewanService();

    @GetMapping
    public PagedResponse<JenisHewan> getJenisHewans(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "peternakID", defaultValue = "*") String peternakID,
            @RequestParam(value = "hewanID", defaultValue = "*") String hewanID,
            @RequestParam(value = "kandangID", defaultValue = "*") String kandangID) throws IOException {
        return jenishewanService.getAllJenisHewan(page, size, peternakID, hewanID, kandangID);
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<byte[]> getFileFromHDFS(@PathVariable String fileName) {
        String uri = "hdfs://hadoop-primary:9000/jenishewan/" + fileName;
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
    public ResponseEntity<?> createJenisHewan(@Valid @RequestBody JenisHewanRequest jenishewanRequest)
            throws IOException {
        try {
            JenisHewan jenishewan = jenishewanService.createJenisHewan(jenishewanRequest);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{idJenisHewan}")
                    .buildAndExpand(jenishewan.getIdJenisHewan()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Jenis Hewan Created Successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An unexpected error occurred."));
        }
    }

    @GetMapping("/{jenishewanId}")
    public DefaultResponse<JenisHewan> getJenisHewanById(@PathVariable String jenishewanId) throws IOException {
        return jenishewanService.getJenisHewanById(jenishewanId);
    }

    @PutMapping("/{jenishewanId}")
    public ResponseEntity<?> updateJenisHewan(@PathVariable String jenishewanId,
            @RequestParam("file") MultipartFile file, @ModelAttribute JenisHewanRequest jenishewanRequest)
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
            String hdfsDir = "hdfs://hadoop-primary:9000/jenishewan/" + newFileName + fileExtension;
            // String uri = "hdfs://h-primary:6912";
            // String hdfsDir = "hdfs://h-primary:6912/jenishewan/" + newFileName +
            // fileExtension;
            Configuration configuration = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(uri), configuration);
            fs.copyFromLocalFile(new Path(localPath), new Path(hdfsDir));
            String savePath = "file/" + newFileName + fileExtension;

            newFile.delete();
            JenisHewan jenishewan = jenishewanService.updateJenisHewan(jenishewanId, jenishewanRequest, savePath);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{jenishewanId}")
                    .buildAndExpand(jenishewan.getIdJenisHewan()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "JenisHewan Updated Successfully"));
        } catch (IOException e) {
            // Penanganan kesalahan saat menyimpan file
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Cannot Upload File into Hadoop"));
        }

    }

    @DeleteMapping("/{jenishewanId}")
    public HttpStatus deleteJenisHewan(@PathVariable(value = "jenishewanId") String jenishewanId) throws IOException {
        jenishewanService.deleteJenisHewanById(jenishewanId);
        return HttpStatus.FORBIDDEN;
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulkJenisHewan(@RequestBody List<JenisHewanRequest> jenishewanRequests) {
        try {
            jenishewanService.createBulkJenisHewan(jenishewanRequests);
            return ResponseEntity.ok(new ApiResponse(true, "All JenisHewan Created Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create bulk data: " + e.getMessage()));
        }
    }
}

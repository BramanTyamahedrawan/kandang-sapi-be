package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.RumpunHewanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.RumpunHewanRepository;
import com.ternak.sapi.util.AppConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RumpunHewanService {
    private RumpunHewanRepository rumpunhewanRepository = new RumpunHewanRepository();
    private HewanRepository hewanRepository = new HewanRepository();

    public PagedResponse<RumpunHewan> getAllRumpunHewan(int page, int size) throws IOException {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        List<RumpunHewan> rumpunhewanResponse = new ArrayList<>();

        rumpunhewanResponse = rumpunhewanRepository.findAll(size);

        return new PagedResponse<>(rumpunhewanResponse, rumpunhewanResponse.size(), "Successfully get data", 200);
    }

    public RumpunHewan createRumpunHewan(RumpunHewanRequest rumpunhewanRequest) throws IOException {
        // Validasi jika rumpun hewan sudah ada
        if (rumpunhewanRepository.existsByRumpun(rumpunhewanRequest.getRumpun())) {
            throw new IllegalArgumentException("Rumpun Hewan sudah terdaftar!");
        }

        RumpunHewan rumpunhewan = new RumpunHewan();
        rumpunhewan.setIdRumpunHewan(rumpunhewanRequest.getIdRumpunHewan());
        rumpunhewan.setRumpun(rumpunhewanRequest.getRumpun());
        rumpunhewan.setDeskripsi(rumpunhewanRequest.getDeskripsi());
        return rumpunhewanRepository.save(rumpunhewan);
    }

    public DefaultResponse<RumpunHewan> getRumpunHewanById(String rumpunhewanId) throws IOException {
        // Retrieve Hewan
        RumpunHewan rumpunhewanResponse = rumpunhewanRepository.findById(rumpunhewanId);
        return new DefaultResponse<>(rumpunhewanResponse.isValid() ? rumpunhewanResponse : null,
                rumpunhewanResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public RumpunHewan updateRumpunHewan(String rumpunhewanId, RumpunHewanRequest rumpunhewanRequest)
            throws IOException {
        RumpunHewan rumpunhewan = new RumpunHewan();
        rumpunhewan.setRumpun(rumpunhewanRequest.getRumpun());
        rumpunhewan.setDeskripsi(rumpunhewanRequest.getDeskripsi());

        List<Hewan> hewanList = hewanRepository.findByRumpunId(rumpunhewanId);
        if (hewanList != null) {
            for (Hewan hewan : hewanList) {
                hewan.setRumpunHewan(rumpunhewan);
                hewanRepository.updateRumpunHewanByHewan(hewan.getIdHewan(), hewan);
            }
        }
        return rumpunhewanRepository.update(rumpunhewanId, rumpunhewan);
    }

    public void deleteRumpunHewanById(String rumpunhewanId) throws IOException {
       rumpunhewanRepository.deleteById(rumpunhewanId);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    @Transactional
    public void createBulkRumpunHewan(List<RumpunHewanRequest> rumpunhewanRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data jenis hewan secara bulk...");

        List<RumpunHewan> rumpunhewanList = new ArrayList<>();
        int skippedIncomplete = 0;
        // int skippedExisting = 0;

        for (RumpunHewanRequest request : rumpunhewanRequests) {
            try {
                RumpunHewan rumpunhewan = new RumpunHewan();
                rumpunhewan.setIdRumpunHewan(request.getIdRumpunHewan());
                rumpunhewan.setRumpun(request.getRumpun());
                rumpunhewan.setDeskripsi(request.getDeskripsi());

                rumpunhewanList.add(rumpunhewan);
                System.out.println("Menambahkan data rumpun hewan ke dalam daftar: " + rumpunhewan.getRumpun());
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data: " + request);
                e.printStackTrace();
            }
        }

        if (!rumpunhewanList.isEmpty()) {
            System.out.println("Menyimpan data jenis hewan yang valid...");
            rumpunhewanRepository.saveAll(rumpunhewanList);
            System.out.println("Data jenis hewan berhasil disimpan. Total: " + rumpunhewanList.size());
        } else {
            System.out.println("Tidak ada data jenis hewan baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete);
    }

}
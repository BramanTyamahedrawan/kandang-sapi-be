package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.TujuanPemeliharaan;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.PetugasRequest;
import com.ternak.sapi.payload.TujuanPemeliharaanRequest;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.TujuanPemeliharaanRepository;
import com.ternak.sapi.util.AppConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TujuanPemeliharaanService {
    private TujuanPemeliharaanRepository tujuanpemeliharaanRepository = new TujuanPemeliharaanRepository();
    private HewanRepository hewanRepository = new HewanRepository();
    // private static final Logger logger =
    // LoggerFactory.getLogger(BeritaService.class);

    public PagedResponse<TujuanPemeliharaan> getAllTujuanPemeliharaan(int page, int size) throws IOException {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        List<TujuanPemeliharaan> tujuanPemeliharaanResponse = tujuanpemeliharaanRepository.findAll(size);

        return new PagedResponse<>(tujuanPemeliharaanResponse, tujuanPemeliharaanResponse.size(),
                "Successfully get data", 200);
    }


    public TujuanPemeliharaan createTujuan(TujuanPemeliharaanRequest tujuanRequest) throws IOException {
        TujuanPemeliharaan tujuan = new TujuanPemeliharaan();
        tujuan.setIdTujuanPemeliharaan(tujuanRequest.getIdTujuanPemeliharaan());
        tujuan.setTujuanPemeliharaan(tujuanRequest.getTujuanPemeliharaan());
        tujuan.setDeskripsi(tujuanRequest.getDeskripsi());
        return tujuanpemeliharaanRepository.save(tujuan);
    }

    public TujuanPemeliharaan update(String idTujuanPemeliharaan, TujuanPemeliharaanRequest tujuanRequest) throws IOException {
        TujuanPemeliharaan tujuan = new TujuanPemeliharaan();
        tujuan.setTujuanPemeliharaan(tujuanRequest.getTujuanPemeliharaan());
        tujuan.setDeskripsi(tujuanRequest.getDeskripsi());

        List<Hewan> hewanList = hewanRepository.findByTujuanPemeliharaanId(idTujuanPemeliharaan);
        if (hewanList != null) {
            for (Hewan hewan : hewanList) {
                hewan.setTujuanPemeliharaan(tujuan);
                hewanRepository.updateTujuanPemeliharaanByHewan(hewan.getIdHewan(), hewan);
            }
        }

        return tujuanpemeliharaanRepository.update(idTujuanPemeliharaan, tujuan);
    }

    public void deleteTujuanById(String tujuanPemeliharaanId) throws IOException {
        tujuanpemeliharaanRepository.deleteById(tujuanPemeliharaanId);
    }

    @Transactional
    public void createBulkTujuanPemeliharaan(List<TujuanPemeliharaanRequest> tujuanPemeliharaanRequests)
            throws IOException {
        System.out.println("Memulai proses penyimpanan data jenis hewan secara bulk...");

        List<TujuanPemeliharaan> tujuanPemeliharaanList = new ArrayList<>();
        Set<String> tujuanSet = new HashSet<>();
        int skippedIncomplete = 0;

        for (TujuanPemeliharaanRequest request : tujuanPemeliharaanRequests) {
            try {

                String filterTujuan = request.getTujuanPemeliharaan() != null ? request.getTujuanPemeliharaan().trim().toLowerCase() :null;
                if(filterTujuan == null){
                    System.out.println("Tujuan Pemeliharaan tidak valid");
                    continue;
                }

                TujuanPemeliharaan tujuanPemeliharaanResponse = tujuanpemeliharaanRepository.findByTujuan(filterTujuan);
                if(tujuanPemeliharaanResponse != null){
                    System.out.println("Tujuan Pemeliharaan '"+ filterTujuan +"'sudah ada");
                    continue;
                }

                if(tujuanSet.contains(filterTujuan)){
                    System.out.println("Tujuan '" + filterTujuan + "' sudah ada dalam list");
                    continue;
                }

                TujuanPemeliharaan tujuanPemeliharaan = new TujuanPemeliharaan();
                tujuanPemeliharaan.setIdTujuanPemeliharaan(request.getIdTujuanPemeliharaan());
                tujuanPemeliharaan.setTujuanPemeliharaan(request.getTujuanPemeliharaan());
                tujuanPemeliharaan.setDeskripsi(request.getDeskripsi());

                tujuanPemeliharaanList.add(tujuanPemeliharaan);
                tujuanSet.add(filterTujuan);
                System.out.println("Menambahkan data Tujuan Pemeliharaan ke dalam daftar: "
                        + tujuanPemeliharaan.getTujuanPemeliharaan());
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data: " + request);
                e.printStackTrace();
            }
        }

        if (!tujuanPemeliharaanList.isEmpty()) {
            System.out.println("Menyimpan data tujuan pemeliharaan yang valid...");
            tujuanpemeliharaanRepository.saveAll(tujuanPemeliharaanList);
            System.out.println("Data tujuan pemeliharaan berhasil disimpan. Total: " + tujuanPemeliharaanList.size());
        } else {
            System.out.println("Tidak ada data tujuan pemeliharaan baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

}

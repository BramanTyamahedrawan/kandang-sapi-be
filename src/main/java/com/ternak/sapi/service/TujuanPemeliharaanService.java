package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.TujuanPemeliharaan;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.JenisHewanRequest;
import com.ternak.sapi.payload.KandangRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.TujuanPemeliharaanRequest;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.JenisHewanRepository;
import com.ternak.sapi.repository.KandangRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.repository.TujuanPemeliharaanRepository;
import com.ternak.sapi.util.AppConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TujuanPemeliharaanService {
    private TujuanPemeliharaanRepository tujuanpemeliharaanRepository = new TujuanPemeliharaanRepository();

    private static final Logger logger = LoggerFactory.getLogger(TujuanPemeliharaanService.class);

    @Transactional
    public void createBulkTujuanPemeliharaan(List<TujuanPemeliharaanRequest> tujuanPemeliharaanRequests)
            throws IOException {
        System.out.println("Memulai proses penyimpanan data jenis hewan secara bulk...");

        List<TujuanPemeliharaan> tujuanPemeliharaanList = new ArrayList<>();
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (TujuanPemeliharaanRequest request : tujuanPemeliharaanRequests) {
            try {
                TujuanPemeliharaan tujuanPemeliharaan = new TujuanPemeliharaan();
                tujuanPemeliharaan.setIdTujuanPemeliharaan(request.getIdTujuanPemeliharaan());
                tujuanPemeliharaan.setTujuanPemeliharaan(request.getTujuanPemeliharaan());
                tujuanPemeliharaan.setDeskripsi(request.getDeskripsi());

                tujuanPemeliharaanList.add(tujuanPemeliharaan);
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

}

package com.ternak.sapi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ternak.sapi.repository.JenisHewanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.NamaVaksin;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.payload.KandangRequest;
import com.ternak.sapi.payload.NamaVaksinRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.NamaVaksinRepository;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.repository.JenisVaksinRepository;
import com.ternak.sapi.util.AppConstants;

public class NamaVaksinService {

    private NamaVaksinRepository namaVaksinRepository = new NamaVaksinRepository();
    private JenisVaksinRepository jenisVaksinRepository = new JenisVaksinRepository();

    @Transactional
    public void createBulkNamaVaksin(List<NamaVaksinRequest> namaVaksinRequests) throws IOException {

        System.out.println("Memulai proses penyimpanan data peternak secara bulk...");

        List<NamaVaksin> namaVaksinList = new ArrayList<>();
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (NamaVaksinRequest request : namaVaksinRequests) {
            try {
                JenisVaksin jenisVaksinResponse = jenisVaksinRepository.findById(request.getIdJenisVaksin());

                NamaVaksin namaVaksin = new NamaVaksin();
                namaVaksin.setIdNamaVaksin(request.getIdNamaVaksin());
                namaVaksin.setNamaVaksin(request.getNamaVaksin());
                namaVaksin.setDeskripsi(request.getDeskripsi());
                namaVaksin.setJenisVaksin(jenisVaksinResponse);

                namaVaksinList.add(namaVaksin);
                System.out.println("Menambahkan data peternak ke dalam daftar: " + namaVaksin.getIdNamaVaksin());
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data peternak: " + request.getIdNamaVaksin());
                e.printStackTrace();
            }
        }
        if (!namaVaksinList.isEmpty()) {
            System.out.println("Menyimpan data peternak yang valid...");
            namaVaksinRepository.saveAll(namaVaksinList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + namaVaksinList.size());
        } else {
            System.out.println("Tidak ada data peternak baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);

    }
}

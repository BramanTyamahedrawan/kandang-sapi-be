package com.ternak.sapi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.model.NamaVaksin;
import com.ternak.sapi.payload.NamaVaksinRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.NamaVaksinRepository;
import com.ternak.sapi.util.AppConstants;
import com.ternak.sapi.repository.JenisVaksinRepository;

public class NamaVaksinService {

    private NamaVaksinRepository namaVaksinRepository = new NamaVaksinRepository();
    private JenisVaksinRepository jenisVaksinRepository = new JenisVaksinRepository();

    public PagedResponse<NamaVaksin> getAllNamaVaksin(int page, int size, String userID) throws IOException {
        validatePageNumberAndSize(page, size);
        List<NamaVaksin> namaVaksinResponse = new ArrayList<>();

        if (userID.equalsIgnoreCase("*"))
            namaVaksinResponse = namaVaksinRepository.findAll(size);
        if (!userID.equalsIgnoreCase("*"))
            namaVaksinResponse = namaVaksinRepository.findAllByUserID(userID, size);

        return new PagedResponse<>(namaVaksinResponse, namaVaksinResponse.size(), "Successfully get data", 200);
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
    public void createBulkNamaVaksin(List<NamaVaksinRequest> namaVaksinRequests) throws IOException {

        System.out.println("Memulai proses penyimpanan data peternak secara bulk...");

        List<NamaVaksin> namaVaksinList = new ArrayList<>();
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (NamaVaksinRequest request : namaVaksinRequests) {
            try {
                System.out.println("Jenis Vaksin diterima dari frontend: " + request.getIdJenisVaksin());

                JenisVaksin jenisVaksinResponse = jenisVaksinRepository.findById(request.getIdJenisVaksin());
                if (jenisVaksinResponse == null) {
                    System.err.println("Data jenis vaksin tidak ditemukan: " + request.getIdJenisVaksin());
                    jenisVaksinResponse = new JenisVaksin();
                    jenisVaksinResponse.setIdJenisVaksin("id jenis vaksin tidak valid");
                    jenisVaksinResponse.setJenisVaksin("nama vaksin tidak valid");

                }

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

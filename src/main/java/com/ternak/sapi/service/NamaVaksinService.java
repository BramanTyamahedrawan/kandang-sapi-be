package com.ternak.sapi.service;

import java.io.IOException;
import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.ternak.sapi.exception.BadRequestException;
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

    public PagedResponse<NamaVaksin> getAllNamaVaksin(int page, int size, String userID, String jenisHewanID,
            String peternakID, String namaVaksinID) throws IOException {
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

    public NamaVaksin create(NamaVaksinRequest namaVaksinRequest)throws IOException{
        NamaVaksin namaVaksin = new NamaVaksin();
        JenisVaksin jenisVaksin = jenisVaksinRepository.findById(namaVaksinRequest.getIdJenisVaksin());
        if(jenisVaksin != null){
            namaVaksin.setIdNamaVaksin(namaVaksinRequest.getIdNamaVaksin());
            namaVaksin.setJenisVaksin(jenisVaksin);
            namaVaksin.setNama(namaVaksinRequest.getNama());
            namaVaksin.setDeskripsi(namaVaksinRequest.getDeskripsi());
        }
        return namaVaksinRepository.save(namaVaksin);
    }

    public NamaVaksin update(String idNamaVaksin, NamaVaksinRequest namaVaksinRequest) throws IOException{
        NamaVaksin namaVaksin = new NamaVaksin();
        JenisVaksin jenisVaksin = jenisVaksinRepository.findById(namaVaksinRequest.getIdJenisVaksin());
        if(jenisVaksin != null){
            namaVaksin.setJenisVaksin(jenisVaksin);
            namaVaksin.setNama(namaVaksinRequest.getNama());
            namaVaksin.setDeskripsi(namaVaksinRequest.getDeskripsi());
        }
        return namaVaksinRepository.update(idNamaVaksin,namaVaksin);
    }

    public void deleteById(String idNamaVaksin)throws IOException{
        namaVaksinRepository.deleteById(idNamaVaksin);
    }

    @Transactional
    public void createBulkNamaVaksin(List<NamaVaksinRequest> namaVaksinRequests) throws IOException {

        System.out.println("Memulai proses penyimpanan data peternak secara bulk...");

        List<NamaVaksin> namaVaksinList = new ArrayList<>();
        Set<String> exitingNamaVaksin = new HashSet<>();
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (NamaVaksinRequest request : namaVaksinRequests) {
            try {
                // System.out.println("Jenis Vaksin diterima dari frontend: " +
                // request.getJenis());

                String filterNamaVaksin = request.getNama() != null ? request.getNama().trim().toLowerCase() : null;

                if (filterNamaVaksin == null) {
                    skippedIncomplete++;
                    continue;
                }

                if(exitingNamaVaksin.contains(filterNamaVaksin)){
                    System.out.println("Nama Vaksin " + filterNamaVaksin + "sudah ada");
                    continue;
                }

                JenisVaksin jenisVaksinResponse = jenisVaksinRepository.findByJenisVaksin(request.getJenis());
                if (jenisVaksinResponse == null) {
                    System.err.println("Data jenis vaksin tidak ditemukan: " +
                            request.getJenis());
                    System.out.println("Membuat jenis vaksin baru....");
                     JenisVaksin jenisVaksin = new JenisVaksin();
                     jenisVaksin.setIdJenisVaksin(UUID.randomUUID().toString());
                     jenisVaksin.setJenis(request.getJenis() != null ? request.getJenis() : "Jenis vaksin tidak ditemukan waktu import nama vaksin");
                     jenisVaksin.setDeskripsi(request.getJenis() != null ? "Deskripsi " +request.getJenis() : "-");
                     jenisVaksinResponse = jenisVaksinRepository.save(jenisVaksin);
                }

                NamaVaksin namaVaksinResponse = namaVaksinRepository.findByNamaVaksin(filterNamaVaksin);
                if(namaVaksinResponse!= null ){
                    System.out.println("Nama Vaksin sudah ada didata base");
                    continue;
                }

                NamaVaksin namaVaksin = new NamaVaksin();
                namaVaksin.setIdNamaVaksin(request.getIdNamaVaksin());
                namaVaksin.setNama(request.getNama());
                namaVaksin.setDeskripsi(request.getDeskripsi());
                namaVaksin.setJenisVaksin(jenisVaksinResponse);

                namaVaksinList.add(namaVaksin);
                exitingNamaVaksin.add(filterNamaVaksin);
                System.out.println("Menambahkan data peternak ke dalam daftar: " + namaVaksin.getNama());
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data peternak: " + request.getNama());
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

package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.model.NamaVaksin;
import com.ternak.sapi.model.Vaksin;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.JenisVaksinRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.JenisVaksinRepository;
import com.ternak.sapi.repository.NamaVaksinRepository;

// import org.springframework.stereotype.Service;
import com.ternak.sapi.repository.VaksinRepository;
import org.springframework.transaction.annotation.Transactional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import com.ternak.sapi.util.AppConstants;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class JenisVaksinService {
    private JenisVaksinRepository jenisVaksinRepository = new JenisVaksinRepository();
    private NamaVaksinRepository namaVaksinRepository = new NamaVaksinRepository();
    private VaksinRepository vaksinRepository = new VaksinRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(JenisVaksinService.class);

    public PagedResponse<JenisVaksin> getAllJenisVaksin(int page, int size, String userID,
            String peternakID) throws IOException {
        validatePageNumberAndSize(page, size);
        List<JenisVaksin> jenisVaksinResponse = new ArrayList<>();

        if (userID.equalsIgnoreCase("*"))
            jenisVaksinResponse = jenisVaksinRepository.findAll(size);
        if (!userID.equalsIgnoreCase("*"))
            jenisVaksinResponse = jenisVaksinRepository.findAllByUserID(userID, size);

        return new PagedResponse<>(jenisVaksinResponse, jenisVaksinResponse.size(), "Successfully get data", 200);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public JenisVaksin createJenisVaksin(JenisVaksinRequest jenisVaksinRequest) throws IOException {
        // Validasi jika jenis vaksin sudah ada
        if (jenisVaksinRepository.existsByJenis(jenisVaksinRequest.getJenis())) {
            throw new IllegalArgumentException("Jenis Vaksin sudah terdaftar!");
        }

        JenisVaksin jenisVaksin = new JenisVaksin();
        jenisVaksin.setIdJenisVaksin(jenisVaksinRequest.getIdJenisVaksin());
        jenisVaksin.setJenis(jenisVaksinRequest.getJenis());
        jenisVaksin.setDeskripsi(jenisVaksinRequest.getDeskripsi());
        return jenisVaksinRepository.save(jenisVaksin);
    }

    public DefaultResponse<JenisVaksin> getJenisVaksinById(String jenisvaksinId) throws IOException {
        // Retrieve Hewan
        JenisVaksin jenisVaksinResponse = jenisVaksinRepository.findById(jenisvaksinId);
        return new DefaultResponse<>(jenisVaksinResponse.isValid() ? jenisVaksinResponse : null,
                jenisVaksinResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public JenisVaksin update(String idJenisVaksin, JenisVaksinRequest jenisVaksinRequest) throws IOException {
        JenisVaksin jenisVaksin = new JenisVaksin();
        jenisVaksin.setJenis(jenisVaksinRequest.getJenis());
        jenisVaksin.setDeskripsi(jenisVaksinRequest.getDeskripsi());

        List<NamaVaksin> namaVaksinList = namaVaksinRepository.findIdJenisVaksin(idJenisVaksin);
        if(namaVaksinList != null){
            for(NamaVaksin namaVaksin : namaVaksinList){
                namaVaksin.setJenisVaksin(jenisVaksin);
                namaVaksinRepository.updateJenisVaksin(namaVaksin.getIdNamaVaksin(),namaVaksin);
            }
        }

        List<Vaksin> vaksinList = vaksinRepository.findIdJenisVaksin(idJenisVaksin);
        if(vaksinList != null){
            for(Vaksin vaksin : vaksinList){
                vaksin.setJenisVaksin(jenisVaksin);
                vaksinRepository.updateJenisVaksin(vaksin.getIdVaksin(),vaksin);
            }
        }

        return jenisVaksinRepository.update(idJenisVaksin, jenisVaksin);
    }

    public void deleteById(String idJenisVaksin) throws IOException {
        jenisVaksinRepository.deleteById(idJenisVaksin);
    }

    @Transactional
    public void createBulkJenisVaksin(List<JenisVaksinRequest> jenisVaksinRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data jenis hewan secara bulk...");

        List<JenisVaksin> jenisVaksinList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>();
        Set<String> exitingJenisVaksin = new HashSet<>();
        int skippedIncomplete = 0;

        for (JenisVaksinRequest request : jenisVaksinRequests) {
            try {

                if(request.getJenis() == null){
                    System.out.println("Data tidak lengkap");
                    skippedIncomplete++;
                    continue;
                }

                if (existingIds.contains(request.getIdJenisVaksin())) {
                    System.out.println("Data jenis vaksin sudah ada di dalam database.");
                    skippedIncomplete++;
                    continue;
                }
                 JenisVaksin jenisVaksinResponse = jenisVaksinRepository.findByJenisVaksin(request.getJenis());
                if(jenisVaksinResponse != null ){
                    System.out.println("Jenis Vaksin '" + request.getJenis() +"' sudah ada");
                    continue;
                }

                if(exitingJenisVaksin.contains(request.getJenis())){
                    System.out.println("Jenis Vaksin'"+ request.getJenis() +"' sudah ada dalam list");
                    continue;
                }

                JenisVaksin jenisVaksin = new JenisVaksin();
                jenisVaksin.setIdJenisVaksin(request.getIdJenisVaksin());
                jenisVaksin.setJenis(request.getJenis());
                jenisVaksin.setDeskripsi(request.getDeskripsi());

                jenisVaksinList.add(jenisVaksin);
                exitingJenisVaksin.add(request.getJenis());
                System.out.println("Menambahkan data Jenis Vaksin ke dalam daftar: "
                        + jenisVaksin.getJenis());
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data: " + request);
                e.printStackTrace();
            }
        }

        if (!jenisVaksinList.isEmpty()) {
            System.out.println("Menyimpan data jenis vaksin yang valid...");
            jenisVaksinRepository.saveAll(jenisVaksinList);
            System.out.println("Data jenis vaksin berhasil disimpan. Total: " + jenisVaksinList.size());
        } else {
            System.out.println("Tidak ada data jenis vaksin baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete);
    }

}

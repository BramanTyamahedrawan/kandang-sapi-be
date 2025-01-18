package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.JenisHewanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.JenisHewanRepository;
import com.ternak.sapi.repository.KandangRepository;
import com.ternak.sapi.util.AppConstants;
import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JenisHewanService {
    private JenisHewanRepository jenishewanRepository = new JenisHewanRepository();
    private HewanRepository hewanRepository = new HewanRepository();
    private KandangRepository kandangRepository = new KandangRepository();

    public PagedResponse<JenisHewan> getAllJenisHewan(int page, int size, String peternakId, String hewanId,
            String kandangId) throws IOException {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        List<JenisHewan> jenishewanResponse = new ArrayList<>();

        jenishewanResponse = jenishewanRepository.findAll(size);

        return new PagedResponse<>(jenishewanResponse, jenishewanResponse.size(), "Successfully get data", 200);
    }

    public JenisHewan createJenisHewan(JenisHewanRequest jenishewanRequest) throws IOException {
        // Validasi jika jenis hewan sudah ada
        if (jenishewanRepository.existsByJenis(jenishewanRequest.getJenis())) {
            throw new IllegalArgumentException("Jenis Hewan sudah terdaftar!");
        }

        JenisHewan jenishewan = new JenisHewan();
        jenishewan.setIdJenisHewan(jenishewanRequest.getIdJenisHewan());
        jenishewan.setJenis(jenishewanRequest.getJenis());
        jenishewan.setDeskripsi(jenishewanRequest.getDeskripsi());
        return jenishewanRepository.save(jenishewan);
    }

    public DefaultResponse<JenisHewan> getJenisHewanById(String jenishewanId) throws IOException {
        // Retrieve Hewan
        JenisHewan jenishewanResponse = jenishewanRepository.findById(jenishewanId);
        return new DefaultResponse<>(jenishewanResponse.isValid() ? jenishewanResponse : null,
                jenishewanResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public JenisHewan updateJenisHewan(String jenishewanId, JenisHewanRequest jenishewanRequest)
            throws IOException {
        JenisHewan jenishewan = new JenisHewan();
        jenishewan.setJenis(jenishewanRequest.getJenis());
        jenishewan.setDeskripsi(jenishewanRequest.getDeskripsi());

        List<Hewan> hewanList = hewanRepository.findByJenisHewanId(jenishewanId);
        if(hewanList != null){
            for(Hewan hewan : hewanList){
                hewan.setJenisHewan(jenishewan);
                hewanRepository.updateJenisHewanByHewan(hewan.getIdHewan(),hewan);
            }
        }

        List<Kandang> kandangList = kandangRepository.findByJenisHewanId(jenishewanId);
        if(kandangList != null){
            for (Kandang kandang : kandangList){
                kandang.setJenisHewan(jenishewan);
                kandangRepository.updateJenisHewanByKandang(kandang.getIdKandang(),kandang);
            }
        }

        return jenishewanRepository.update(jenishewanId, jenishewan);
    }

    public void deleteJenisHewanById(String jenishewanId) throws IOException {
            jenishewanRepository.deleteById(jenishewanId);
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
    public void createBulkJenisHewan(List<JenisHewanRequest> jenishewanRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data jenis hewan secara bulk...");

        List<JenisHewan> jenishewanList = new ArrayList<>();
        int skippedIncomplete = 0;
        // int skippedExisting = 0;

        for (JenisHewanRequest request : jenishewanRequests) {
            try {
                JenisHewan jenishewan = new JenisHewan();
                jenishewan.setIdJenisHewan(request.getIdJenisHewan());
                jenishewan.setJenis(request.getJenis());
                jenishewan.setDeskripsi(request.getDeskripsi());

                jenishewanList.add(jenishewan);
                System.out.println("Menambahkan data jenis hewa ke dalam daftar: " + jenishewan.getJenis());
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data: " + request);
                e.printStackTrace();
            }
        }

        if (!jenishewanList.isEmpty()) {
            System.out.println("Menyimpan data jenis hewan yang valid...");
            jenishewanRepository.saveAll(jenishewanList);
            System.out.println("Data jenis hewan berhasil disimpan. Total: " + jenishewanList.size());
        } else {
            System.out.println("Tidak ada data jenis hewan baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete);
    }

}
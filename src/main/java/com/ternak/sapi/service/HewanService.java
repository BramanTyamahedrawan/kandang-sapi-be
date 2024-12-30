package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.*;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.HewanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.*;
import com.ternak.sapi.util.AppConstants;
import org.springframework.stereotype.Service;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HewanService {
    private HewanRepository hewanRepository = new HewanRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private KandangRepository kandangRepository = new KandangRepository();
    private JenisHewanRepository jenisHewanRepository = new JenisHewanRepository();
    private RumpunHewanRepository rumpunHewanRepository = new RumpunHewanRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(HewanService.class);

    public PagedResponse<Hewan> getAllHewan(int page, int size, String peternakId, String petugasId, String kandangId)
            throws IOException {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        List<Hewan> hewanResponse = new ArrayList<>();

        if (peternakId.equalsIgnoreCase("*")) {
            hewanResponse = hewanRepository.findAll(size);
        } else {
            hewanResponse = hewanRepository.findHewanByPeternak(peternakId, size);
        }

        return new PagedResponse<>(hewanResponse, hewanResponse.size(), "Successfully get data", 200);
    }

    public Hewan createHewan(HewanRequest hewanRequest, String savePath) throws IOException {
        // Validasi ID Hewan
        if (hewanRepository.existsByIdHewan(hewanRequest.getIdHewan())) {
            throw new IllegalArgumentException("ID Hewan sudah terdaftar!");
        }

        // Validasi kode eartag nasional
        if (hewanRepository.existsByKodeEartagNasional(hewanRequest.getKodeEartagNasional())) {
            throw new IllegalArgumentException("Kode Eartag Nasional sudah terdaftar!");
        }

        Hewan hewan = new Hewan();
        Peternak peternakResponse = peternakRepository.findById(hewanRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(hewanRequest.getPetugas_id().toString());
        Kandang kandangResponse = kandangRepository.findById(hewanRequest.getKandang_id().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(hewanRequest.getJenisHewanId().toString());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(hewanRequest.getRumpunHewanId().toString());

        if (peternakResponse.getNamaPeternak() != null && petugasResponse.getNamaPetugas() != null
                && kandangResponse.getAlamat() != null && jenisHewanResponse.getJenis() != null
                && rumpunHewanResponse.getRumpun() != null) {
            hewan.setIdHewan(hewanRequest.getIdHewan());
            hewan.setKodeEartagNasional(hewanRequest.getKodeEartagNasional());
            hewan.setSex(hewanRequest.getSex());
            hewan.setUmur(hewanRequest.getUmur());
            hewan.setTanggalTerdaftar(hewanRequest.getTanggalTerdaftar());
            hewan.setLatitude(hewanRequest.getLatitude());
            hewan.setLongitude(hewanRequest.getLongitude());
            hewan.setTanggalLahir(hewanRequest.getTanggalLahir());
            hewan.setTempatLahir(hewanRequest.getTempatLahir());
            hewan.setTujuanPemeliharaan(hewanRequest.getTujuanPemeliharaan());
            hewan.setFile_path(savePath);
            hewan.setIdentifikasiHewan(hewanRequest.getIdentifikasiHewan());

            hewan.setPeternak(peternakResponse);
            hewan.setPetugas(petugasResponse);
            hewan.setKandang(kandangResponse);
            hewan.setJenisHewan(jenisHewanResponse);
            hewan.setRumpunHewan(rumpunHewanResponse);
            return hewanRepository.save(hewan);
        } else {
            return null;
        }
    }

    public DefaultResponse<Hewan> getHewanById(String idHewan) throws IOException {
        // Retrieve Hewan
        Hewan hewanResponse = hewanRepository.findById(idHewan);
        return new DefaultResponse<>(hewanResponse.isValid() ? hewanResponse : null, hewanResponse.isValid() ? 1 : 0,
                "Successfully get data");
    }

    public Hewan updateHewan(String idHewan, HewanRequest hewanRequest, String savePath) throws IOException {
        Hewan hewan = new Hewan();
        Peternak peternakResponse = peternakRepository.findById(hewanRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(hewanRequest.getPetugas_id().toString());
        Kandang kandangResponse = kandangRepository.findById(hewanRequest.getKandang_id().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(hewanRequest.getJenisHewanId().toString());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(hewanRequest.getRumpunHewanId().toString());

        if (peternakResponse.getNamaPeternak() != null && petugasResponse.getNamaPetugas() != null
                && kandangResponse.getAlamat() != null && jenisHewanResponse.getJenis() != null
                && rumpunHewanResponse.getRumpun() != null) {
            hewan.setIdHewan(hewanRequest.getIdHewan());
            hewan.setKodeEartagNasional(hewanRequest.getKodeEartagNasional());
            hewan.setSex(hewanRequest.getSex());
            hewan.setUmur(hewanRequest.getUmur());
            hewan.setTanggalTerdaftar(hewanRequest.getTanggalTerdaftar());
            hewan.setLatitude(hewanRequest.getLatitude());
            hewan.setLongitude(hewanRequest.getLongitude());
            hewan.setTanggalLahir(hewanRequest.getTanggalLahir());
            hewan.setTempatLahir(hewanRequest.getTempatLahir());
            hewan.setTujuanPemeliharaan(hewanRequest.getTujuanPemeliharaan());
            hewan.setFile_path(savePath);
            hewan.setIdentifikasiHewan(hewanRequest.getIdentifikasiHewan());

            hewan.setPeternak(peternakResponse);
            hewan.setPetugas(petugasResponse);
            hewan.setKandang(kandangResponse);
            hewan.setJenisHewan(jenisHewanResponse);
            hewan.setRumpunHewan(rumpunHewanResponse);
            return hewanRepository.update(idHewan, hewan);
        } else {
            return null;
        }
    }

    public void deleteHewanById(String idHewan) throws IOException {
        Hewan hewanResponse = hewanRepository.findById(idHewan);
        if (hewanResponse.isValid()) {
            hewanRepository.deleteById(idHewan);
        } else {
            throw new ResourceNotFoundException("Hewan", "id", idHewan);
        }
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
    public void createBulkHewan(List<HewanRequest> hewanRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data Ternak Hewan secara bulk...");

        List<Hewan> hewanList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>(); // Set untuk melacak idHewan yang sudah diproses
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (HewanRequest request : hewanRequests) {
            try {
                // Cek apakah idHewan sudah ada di Set
                if (existingIds.contains(request.getIdHewan())) {
                    System.err.println("Duplicate idHewan ditemukan, data di-skip: " + request.getIdHewan());
                    skippedExisting++;
                    continue; // Lewati iterasi jika idHewan duplikat
                }

                // Buat objek Hewan
                Hewan hewan = new Hewan();
                hewan.setIdHewan(request.getIdHewan());
                hewan.setKodeEartagNasional(request.getKodeEartagNasional());
                hewan.setSex(request.getSex());
                hewan.setLatitude(request.getLatitude());
                hewan.setLongitude(request.getLongitude());
                hewan.setTanggalLahir(request.getTanggalLahir());
                hewan.setTempatLahir(request.getTempatLahir());

                // Fetch relasi data dari repository
                Peternak peternakResponse = peternakRepository.findByNikPeternak(request.getNikPeternak());
                Petugas petugasResponse = petugasRepository.findByNik(request.getNikPetugas());
                Kandang kandangResponse = kandangRepository.findById(request.getKandang_id().toString());
                JenisHewan jenisHewanResponse = jenisHewanRepository.findById(request.getJenisHewanId().toString());
                RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(request.getRumpunHewanId().toString());

                // Set relasi ke objek Hewan
                hewan.setPetugas(petugasResponse);
                hewan.setPeternak(peternakResponse);
                hewan.setKandang(kandangResponse);
                hewan.setJenisHewan(jenisHewanResponse);
                hewan.setRumpunHewan(rumpunHewanResponse);

                // Tambahkan idHewan ke Set dan Hewan ke List
                existingIds.add(request.getIdHewan());
                hewanList.add(hewan);

                System.out.println("Menambahkan data hewan ke dalam daftar: " + hewan.getIdHewan());

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data hewan: " + request.getIdHewan());
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        if (!hewanList.isEmpty()) {
            System.out.println("Menyimpan data hewan yang valid...");
            hewanRepository.saveAll(hewanList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + hewanList.size());
        } else {
            System.out.println("Tidak ada data hewan baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);
    }

}
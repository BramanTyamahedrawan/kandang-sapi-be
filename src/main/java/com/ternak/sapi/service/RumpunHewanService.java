package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Inseminasi;
import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.model.Pkb;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.RumpunHewanRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.InseminasiRepository;
import com.ternak.sapi.repository.KelahiranRepository;
import com.ternak.sapi.repository.PkbRepository;
import com.ternak.sapi.repository.RumpunHewanRepository;
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
public class RumpunHewanService {
    private RumpunHewanRepository rumpunhewanRepository = new RumpunHewanRepository();
    private HewanRepository hewanRepository = new HewanRepository();
    private InseminasiRepository inseminasiRepository = new InseminasiRepository();
    private KelahiranRepository kelahiranRepository = new KelahiranRepository();
    private PkbRepository pkbRepository = new PkbRepository();

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

        List<Inseminasi> inseminasiList = inseminasiRepository.findByRumpunId(rumpunhewanId);
        if (inseminasiList != null) {
            for (Inseminasi inseminasi : inseminasiList) {
                inseminasi.setRumpunHewan(rumpunhewan);
                inseminasiRepository.updateRumpunHewanByInseminasi(inseminasi.getIdInseminasi(), inseminasi);
            }
        }

        List<Kelahiran> kelahiranList = kelahiranRepository.findByRumpunId(rumpunhewanId);
        if (kelahiranList != null) {
            for (Kelahiran kelahiran : kelahiranList) {
                kelahiran.setRumpunHewan(rumpunhewan);
                kelahiranRepository.updateRumpunHewanByKelahiran(kelahiran.getIdKelahiran(), kelahiran);
            }
        }

        List<Pkb> pkbList = pkbRepository.findByRumpunId(rumpunhewanId);
        if (pkbList != null) {
            for (Pkb pkb : pkbList) {
                pkb.setRumpunHewan(rumpunhewan);
                pkbRepository.updateRumpunHewanByPkb(pkb.getIdPkb(), pkb);
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
        Set<String> rumpunSet = new HashSet<>();
        int skippedIncomplete = 0;
        // int skippedExisting = 0;

        for (RumpunHewanRequest request : rumpunhewanRequests) {

            String namaRumpun = request.getRumpun() != null ? request.getRumpun().trim().toLowerCase() : null;
            if (namaRumpun == null) {
                System.out.println("Data Tidak lengkap");
                continue;
            }

            RumpunHewan rumpunHewanResponse = rumpunhewanRepository.findByRumpun(request.getRumpun());
            if (rumpunHewanResponse != null) {
                System.out.println("Rumpun " + request.getRumpun() + "sudah ada");
                continue;
            }

            if (rumpunSet.contains(namaRumpun)) {
                System.out.println("Rumpun '" + namaRumpun + "'sudah ada dilist");
                continue;
            }
            try {
                RumpunHewan rumpunhewan = new RumpunHewan();
                rumpunhewan.setIdRumpunHewan(request.getIdRumpunHewan());
                rumpunhewan.setRumpun(namaRumpun);
                rumpunhewan.setDeskripsi(request.getDeskripsi());

                rumpunhewanList.add(rumpunhewan);
                rumpunSet.add(namaRumpun);
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
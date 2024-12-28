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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HewanService {
    private HewanRepository hewanRepository = new HewanRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private KandangRepository kandangRepository = new KandangRepository();
    private JenisHewanRepository jenisHewanRepository = new JenisHewanRepository();
    private RumpunHewanRepository rumpunHewanRepository = new RumpunHewanRepository();

    private static final Logger logger = LoggerFactory.getLogger(HewanService.class);


    public PagedResponse<Hewan> getAllHewan(int page, int size, String peternakId, String petugasId, String kandangId) throws IOException {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        List<Hewan> hewanResponse = new ArrayList<>();

        if(peternakId.equalsIgnoreCase("*")){
            hewanResponse = hewanRepository.findAll(size);
        }else{
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

        if (peternakResponse.getNamaPeternak()!= null && petugasResponse.getNamaPetugas()!= null && kandangResponse.getAlamat()!= null && jenisHewanResponse.getJenis() != null && rumpunHewanResponse.getRumpun() != null){
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
        return new DefaultResponse<>(hewanResponse.isValid() ? hewanResponse : null, hewanResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Hewan updateHewan(String idHewan, HewanRequest hewanRequest, String savePath) throws IOException {
        Hewan hewan = new Hewan();
        Peternak peternakResponse = peternakRepository.findById(hewanRequest.getPeternak_id().toString());
        Petugas petugasResponse = petugasRepository.findById(hewanRequest.getPetugas_id().toString());
        Kandang kandangResponse = kandangRepository.findById(hewanRequest.getKandang_id().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(hewanRequest.getJenisHewanId().toString());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(hewanRequest.getRumpunHewanId().toString());

        if (peternakResponse.getNamaPeternak()!= null && petugasResponse.getNamaPetugas()!= null && kandangResponse.getAlamat()!= null && jenisHewanResponse.getJenis() != null && rumpunHewanResponse.getRumpun() != null){
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
        if(hewanResponse.isValid()){
            hewanRepository.deleteById(idHewan);
        }else{
            throw new ResourceNotFoundException("Hewan", "id", idHewan);
        }
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

}
package com.ternak.sapi.service;

import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.model.User;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.PeternakRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.util.AppConstants;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PeternakService {
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private UserRepository userRepository = new UserRepository();

    private static final Logger logger = LoggerFactory.getLogger(PeternakService.class);


    public PagedResponse<Peternak> getAllPeternak(int page, int size, String userID) throws IOException {
        validatePageNumberAndSize(page, size);
        List<Peternak> peternakResponse = new ArrayList<>();

        if(userID.equalsIgnoreCase("*")) peternakResponse = peternakRepository.findAll(size);
        if(!userID.equalsIgnoreCase("*")) peternakResponse = peternakRepository.findAllByUserID(userID, size);

        return new PagedResponse<>(peternakResponse, peternakResponse.size(), "Successfully get data", 200);
    }

    public Peternak createPeternak(PeternakRequest peternakRequest) throws IOException {
        // Validasi jika NIK Peternak sudah ada
        if (peternakRepository.existsByNikPeternak(peternakRequest.getNikPeternak())) {
            throw new IllegalArgumentException("NIK Peternak sudah terdaftar!");
        }

        // Validasi jika Email Peternak sudah ada
        if (peternakRepository.existsByEmail(peternakRequest.getEmail())) {
            throw new IllegalArgumentException("Email sudah digunakan!");
        }

        // Validasi jika Nomor Telepon Peternak sudah ada
        if (peternakRepository.existsByNoTelepon(peternakRequest.getNoTelepon())) {
            throw new IllegalArgumentException("Nomor Telepon sudah terdaftar!");
        }

        Peternak peternak = new Peternak();

        Petugas petugasResponse = petugasRepository.findById(peternakRequest.getPetugas_id().toString());

        if (petugasResponse.getNamaPetugas()!= null ) {
            peternak.setIdPeternak(peternakRequest.getIdPeternak());
            peternak.setNikPeternak(peternakRequest.getNikPeternak());
            peternak.setNamaPeternak(peternakRequest.getNamaPeternak());
            peternak.setLokasi(peternakRequest.getLokasi());
            peternak.setTanggalPendaftaran(peternakRequest.getTanggalPendaftaran());
            peternak.setPetugas(petugasResponse);
            peternak.setNoTelepon(peternakRequest.getNoTelepon());
            peternak.setEmail(peternakRequest.getEmail());
            peternak.setJenisKelamin(peternakRequest.getJenisKelamin());
            peternak.setTanggalLahir(peternakRequest.getTanggalLahir());
            peternak.setIdIsikhnas(peternakRequest.getIdIsikhnas());
            peternak.setDusun(peternakRequest.getDusun());
            peternak.setDesa(peternakRequest.getDesa());
            peternak.setKecamatan(peternakRequest.getKecamatan());
            peternak.setKabupaten(peternakRequest.getKabupaten());
            peternak.setAlamat(peternakRequest.getAlamat());
            peternak.setLatitude(peternakRequest.getLatitude());
            peternak.setLongitude(peternakRequest.getLongitude());

            return peternakRepository.save(peternak);
        } else {
            return null;
        }
    }

    public DefaultResponse<Peternak> getPeternakById(String peternakId) throws IOException {
        // Retrieve Peternak
        Peternak peternakResponse = peternakRepository.findById(peternakId);
        return new DefaultResponse<>(peternakResponse.isValid() ? peternakResponse : null, peternakResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Peternak updatePeternak(String peternakId, PeternakRequest peternakRequest) throws IOException {
        Peternak peternak = new Peternak();
        Petugas petugasResponse = petugasRepository.findById(peternakRequest.getPetugas_id().toString());

        if (petugasResponse.getNamaPetugas()!= null ) {
            peternak.setNikPeternak(peternakRequest.getNikPeternak());
            peternak.setNamaPeternak(peternakRequest.getNamaPeternak());
            peternak.setLokasi(peternakRequest.getLokasi());
            peternak.setTanggalPendaftaran(peternakRequest.getTanggalPendaftaran());
            peternak.setPetugas(petugasResponse);
            peternak.setNoTelepon(peternakRequest.getNoTelepon());
            peternak.setEmail(peternakRequest.getEmail());
            peternak.setJenisKelamin(peternakRequest.getJenisKelamin());
            peternak.setTanggalLahir(peternakRequest.getTanggalLahir());
            peternak.setIdIsikhnas(peternakRequest.getIdIsikhnas());
            peternak.setDusun(peternakRequest.getDusun());
            peternak.setDesa(peternakRequest.getDesa());
            peternak.setKecamatan(peternakRequest.getKecamatan());
            peternak.setKabupaten(peternakRequest.getKabupaten());
            peternak.setAlamat(peternakRequest.getAlamat());
            peternak.setLatitude(peternakRequest.getLatitude());
            peternak.setLongitude(peternakRequest.getLongitude());
            return peternakRepository.update(peternakId, peternak);
        } else {
            return null;
        }
    }

    public void deletePeternakById(String peternakId) throws IOException {
        Peternak peternakResponse = peternakRepository.findById(peternakId);
        if(peternakResponse.isValid()){
            peternakRepository.deleteById(peternakId);
        }else{
            throw new ResourceNotFoundException("Peternak", "id", peternakId);
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

    @Transactional
    public void createBulkPeternak(List<PeternakRequest> peternakRequests) throws IOException {
        // Extract lists of unique identifiers (NIK, Email, NoTelepon) from the incoming requests
        List<String> nikList = peternakRequests.stream().map(PeternakRequest::getNikPeternak).collect(Collectors.toList());
        List<String> emailList = peternakRequests.stream().map(PeternakRequest::getEmail).collect(Collectors.toList());
        List<String> noTeleponList = peternakRequests.stream().map(PeternakRequest::getNoTelepon).collect(Collectors.toList());

        System.out.println(nikList);

        // Check which NIK, Email, and NoTelepon already exist in a batch using HBase
        Set<String> existingNikSet = new HashSet<>(peternakRepository.findExistingNik(nikList));
        Set<String> existingEmailSet = new HashSet<>(peternakRepository.findExistingEmail(emailList));
        Set<String> existingNoTelpSet = new HashSet<>(peternakRepository.findExistingNoTelp(noTeleponList));

        List<Peternak> peternakList = new ArrayList<>();

        // Process each PeternakRequest
        for (PeternakRequest request : peternakRequests) {
            // Skip if NIK, Email, or NoTelepon already exist
            if (existingNikSet.contains(request.getNikPeternak())) {
                System.out.println("NIK Peternak sudah terdaftar: " + request.getNikPeternak());
                continue;
            }
            if (existingEmailSet.contains(request.getEmail())) {
                System.out.println("Email sudah digunakan: " + request.getEmail());
                continue;
            }
            if (existingNoTelpSet.contains(request.getNoTelepon())) {
                System.out.println("Nomor Telepon sudah terdaftar: " + request.getNoTelepon());
                continue;
            }

            // Validasi Petugas berdasarkan ID
            Petugas petugasResponse = petugasRepository.findById(request.getPetugas_id().toString());
            if (petugasResponse != null && petugasResponse.getNikPetugas() != null) {
                // Create Peternak object after validation
                Peternak peternak = new Peternak();
                peternak.setIdPeternak(request.getIdPeternak());
                peternak.setNikPeternak(request.getNikPeternak());
                peternak.setNamaPeternak(request.getNamaPeternak());
                peternak.setLokasi(request.getLokasi());
                peternak.setTanggalPendaftaran(request.getTanggalPendaftaran());
                peternak.setPetugas(petugasResponse);
                peternak.setNoTelepon(request.getNoTelepon());
                peternak.setEmail(request.getEmail());
                peternak.setJenisKelamin(request.getJenisKelamin());
                peternak.setTanggalLahir(request.getTanggalLahir());
                peternak.setIdIsikhnas(request.getIdIsikhnas());
                peternakList.add(peternak);
            } else {
                System.out.println("Petugas dengan ID " + request.getPetugas_id() + " tidak ditemukan.");
            }
        }


        // Save only valid Peternak data
        if (!peternakList.isEmpty()) {
            peternakRepository.saveAll(peternakList);
            System.out.println("Bulk Peternak berhasil disimpan.");
        } else {
            System.out.println("Tidak ada Peternak baru untuk disimpan.");
        }
    }
}

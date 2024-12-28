package com.ternak.sapi.service;

import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.model.User;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.PetugasRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.util.AppConstants;
import create_structure.HBaseCustomClient;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
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
public class PetugasService {
    Configuration conf = HBaseConfiguration.create();
    String tableName = "petugasdev";
    private PetugasRepository petugasRepository = new PetugasRepository();
    private UserRepository userRepository = new UserRepository();

    private static final Logger logger = LoggerFactory.getLogger(PetugasService.class);


    public PagedResponse<Petugas> getAllPetugas(int page, int size, String userID) throws IOException {
        validatePageNumberAndSize(page, size);
        List<Petugas> petugasResponse = new ArrayList<>();

        if(userID.equalsIgnoreCase("*")) petugasResponse = petugasRepository.findAll(size);
        if(!userID.equalsIgnoreCase("*")) petugasResponse = petugasRepository.findAllByUserID(userID, size);

        return new PagedResponse<>(petugasResponse, petugasResponse.size(), "Successfully get data", 200);
    }


    public Petugas createPetugas(PetugasRequest petugasRequest) throws IOException {

        if (petugasRepository.existsByNikPetugas(petugasRequest.getNikPetugas())) {
            throw new IllegalArgumentException("NIK Petugas sudah terdaftar!");
        }

        // Validasi jika Email Petugas sudah ada
        if (petugasRepository.existsByEmail(petugasRequest.getEmail())) {
            throw new IllegalArgumentException("Email sudah digunakan!");
        }

        // Validasi jika Nomor Telepon sudah ada
        if (petugasRepository.existsByNoTelp(petugasRequest.getNoTelp())) {
            throw new IllegalArgumentException("Nomor Telepon sudah terdaftar!");
        }

        Petugas petugas = new Petugas();
            petugas.setNikPetugas(petugasRequest.getNikPetugas());
            petugas.setNamaPetugas(petugasRequest.getNamaPetugas());
            petugas.setNoTelp(petugasRequest.getNoTelp());
            petugas.setEmail(petugasRequest.getEmail());
            petugas.setJob(petugasRequest.getJob());
            petugas.setWilayah(petugasRequest.getWilayah());
            return petugasRepository.save(petugas);
    }

    public DefaultResponse<Petugas> getPetugasById(String petugasId) throws IOException {
        // Retrieve Petugas
        Petugas petugasResponse = petugasRepository.findById(petugasId);
        return new DefaultResponse<>(petugasResponse.isValid() ? petugasResponse : null, petugasResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Petugas updatePetugas(String petugasId, PetugasRequest petugasRequest) throws IOException {
        Petugas petugas = new Petugas();
        
        petugas.setNamaPetugas(petugasRequest.getNamaPetugas());
        petugas.setNoTelp(petugasRequest.getNoTelp());
        petugas.setEmail(petugasRequest.getEmail());
        petugas.setJob(petugasRequest.getJob());
        petugas.setWilayah(petugasRequest.getWilayah());
        return petugasRepository.update(petugasId, petugas);
    }

    public void deletePetugasById(String petugasId) throws IOException {
        Petugas petugasResponse = petugasRepository.findById(petugasId);
        if(petugasResponse.isValid()){
            petugasRepository.deleteById(petugasId);
        }else{
            throw new ResourceNotFoundException("Petugas", "id", petugasId);
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
    public void createBulkPetugas(List<PetugasRequest> petugasRequests) throws IOException {
        // Extract lists of unique identifiers (NIK, Email, NoTelp) from the incoming requests
        List<String> nikList = petugasRequests.stream().map(PetugasRequest::getNikPetugas).collect(Collectors.toList());
        List<String> emailList = petugasRequests.stream().map(PetugasRequest::getEmail).collect(Collectors.toList());
        List<String> noTelpList = petugasRequests.stream().map(PetugasRequest::getNoTelp).collect(Collectors.toList());

        // Check which NIK, Email, and NoTelp already exist
        Set<String> existingNikSet = new HashSet<>(petugasRepository.findExistingNik(nikList));
        Set<String> existingEmailSet = new HashSet<>(petugasRepository.findExistingEmail(emailList));
        Set<String> existingNoTelpSet = new HashSet<>(petugasRepository.findExistingNoTelp(noTelpList));

        List<Petugas> petugasList = new ArrayList<>();

        // Process each PetugasRequest
        for (PetugasRequest request : petugasRequests) {
            // Skip if NIK, Email or NoTelp already exist
            if (existingNikSet.contains(request.getNikPetugas())) {
                System.out.println("NIK Petugas sudah terdaftar: " + request.getNikPetugas());
                continue;
            }
            if (existingEmailSet.contains(request.getEmail())) {
                System.out.println("Email sudah digunakan: " + request.getEmail());
                continue;
            }
            if (existingNoTelpSet.contains(request.getNoTelp())) {
                System.out.println("Nomor Telepon sudah terdaftar: " + request.getNoTelp());
                continue;
            }

            // Create the Petugas object and add to the list
            Petugas petugas = new Petugas();
            petugas.setNikPetugas(request.getNikPetugas());
            petugas.setNamaPetugas(request.getNamaPetugas());
            petugas.setNoTelp(request.getNoTelp());
            petugas.setEmail(request.getEmail());
            petugas.setJob(request.getJob());
            petugasList.add(petugas);
        }

        // Save only valid Petugas data
        if (!petugasList.isEmpty()) {
            petugasRepository.saveAll(petugasList);
        } else {
            System.out.println("Tidak ada petugas baru untuk disimpan.");
        }
    }
}

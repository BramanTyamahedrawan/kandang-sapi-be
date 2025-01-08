package com.ternak.sapi.service;

import com.ternak.sapi.repository.PetugasRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PeternakService {
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    // private UserRepository userRepository = new UserRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(PeternakService.class);

    public PagedResponse<Peternak> getAllPeternak(int page, int size, String userID) throws IOException {
        validatePageNumberAndSize(page, size);
        List<Peternak> peternakResponse = new ArrayList<>();

        if (userID.equalsIgnoreCase("*"))
            peternakResponse = peternakRepository.findAll(size);
        if (!userID.equalsIgnoreCase("*"))
            peternakResponse = peternakRepository.findAllByUserID(userID, size);

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

        Petugas petugasResponse = petugasRepository.findByNamaPetugas(peternakRequest.getNamaPetugas());

        if (petugasResponse.getNamaPetugas() != null) {
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
        return new DefaultResponse<>(peternakResponse.isValid() ? peternakResponse : null,
                peternakResponse.isValid() ? 1 : 0, "Successfully get data");
    }

    public Peternak updatePeternak(String peternakId, PeternakRequest peternakRequest) throws IOException {
        Peternak peternak = new Peternak();
        Petugas petugasResponse = petugasRepository.findById(peternakRequest.getPetugas_id().toString());

        if (petugasResponse.getNamaPetugas() != null) {
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
        if (peternakResponse.isValid()) {
            peternakRepository.deleteById(peternakId);
        } else {
            throw new ResourceNotFoundException("Peternak", "id", peternakId);
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
    public void createBulkPeternak(List<PeternakRequest> peternakRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data peternak secara bulk...");

        // Ekstraksi data unik
        List<String> nikList = peternakRequests.stream()
                .map(PeternakRequest::getNikPeternak)
                .collect(Collectors.toList());
        List<String> emailList = peternakRequests.stream()
                .map(PeternakRequest::getEmail)
                .collect(Collectors.toList());
        List<String> noTelpList = peternakRequests.stream()
                .map(PeternakRequest::getNoTelepon)
                .collect(Collectors.toList());

        System.out.println("Memeriksa NIK, Email, dan NoTelp yang sudah terdaftar...");
        Set<String> existingNikSet = new HashSet<>(peternakRepository.findExistingNik(nikList));
        Set<String> existingEmailSet = new HashSet<>(peternakRepository.findExistingEmail(emailList));
        Set<String> existingNoTelpSet = new HashSet<>(peternakRepository.findExistingNoTelp(noTelpList));

        List<Peternak> peternakList = new ArrayList<>();
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (PeternakRequest request : peternakRequests) {
            try {
                // Validasi data tidak lengkap
                if (request.getNikPeternak() == null || request.getNamaPeternak() == null ||
                        request.getNoTelepon() == null || request.getEmail() == null ||
                        request.getNikPetugas() == null) {
                    System.out.println("Data tidak lengkap atau nikPetugas null, melewati data: " + request);
                    skippedIncomplete++;
                    continue;
                }

                // Skip jika data sudah ada
                if (existingNikSet.contains(request.getNikPeternak())) {
                    System.out.println("NIK Peternak sudah terdaftar: " + request.getNikPeternak());
                    skippedExisting++;
                    continue;
                }
                if (existingEmailSet.contains(request.getEmail())) {
                    System.out.println("Email sudah digunakan: " + request.getEmail());
                    skippedExisting++;
                    continue;
                }
                if (existingNoTelpSet.contains(request.getNoTelepon())) {
                    System.out.println("Nomor Telepon sudah terdaftar: " + request.getNoTelepon());
                    skippedExisting++;
                    continue;
                }

                // Validasi nikPetugas
                Petugas petugasResponse = petugasRepository.findByNik(request.getNikPetugas());
                if (petugasResponse == null) {
                    System.out.println("Petugas dengan NIK " + request.getNikPetugas() + " tidak ditemukan.");
                    continue;
                }

                // Buat objek Peternak
                Peternak peternak = new Peternak();
                peternak.setIdPeternak(request.getIdPeternak());
                peternak.setNikPeternak(request.getNikPeternak());
                peternak.setEmail(request.getEmail());
                peternak.setNoTelepon(request.getNoTelepon());
                peternak.setNamaPeternak(request.getNamaPeternak());
                peternak.setLokasi(request.getLokasi() != null ? request.getLokasi() : "Lokasi tidak diketahui");
                peternak.setAlamat(request.getAlamat() != null ? request.getAlamat() : "Alamat tidak diketahui");
                peternak.setDusun(request.getDusun());
                peternak.setDesa(request.getDesa());
                peternak.setKecamatan(request.getKecamatan());
                peternak.setKabupaten(request.getKabupaten());
                peternak.setTanggalPendaftaran(request.getTanggalPendaftaran());
                peternak.setPetugas(petugasResponse); // Masukkan objek lengkap Petugas
                peternak.setTanggalLahir(request.getTanggalLahir());
                peternak.setJenisKelamin(request.getJenisKelamin());
                peternak.setIdIsikhnas(request.getIdIsikhnas());
                peternak.setLongitude(request.getLongitude());
                peternak.setLatitude(request.getLatitude());

                peternakList.add(peternak);
                System.out.println("Menambahkan data peternak ke dalam daftar: " + peternak.getNikPeternak());
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data peternak: " + request.getNikPeternak());
                e.printStackTrace();
            }
        }

        if (!peternakList.isEmpty()) {
            System.out.println("Menyimpan data peternak yang valid...");
            peternakRepository.saveAll(peternakList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + peternakList.size());
        } else {
            System.out.println("Tidak ada data peternak baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);
    }

    @Transactional
    public void createImportPeternak(List<PeternakRequest> peternakRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data peternak secara bulk...");

        // Ekstraksi data unik
        List<String> nikList = peternakRequests.stream()
                .map(PeternakRequest::getNikPeternak)
                .collect(Collectors.toList());
        List<String> emailList = peternakRequests.stream()
                .map(PeternakRequest::getEmail)
                .collect(Collectors.toList());
        List<String> noTelpList = peternakRequests.stream()
                .map(PeternakRequest::getNoTelepon)
                .collect(Collectors.toList());

        System.out.println("Memeriksa NIK, Email, dan NoTelp yang sudah terdaftar...");
        Set<String> existingNikSet = new HashSet<>(peternakRepository.findExistingNik(nikList));
        Set<String> existingEmailSet = new HashSet<>(peternakRepository.findExistingEmail(emailList));
        Set<String> existingNoTelpSet = new HashSet<>(peternakRepository.findExistingNoTelp(noTelpList));

        List<Peternak> peternakList = new ArrayList<>();
        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (PeternakRequest request : peternakRequests) {
            try {
                // Validasi data tidak lengkap
                if (request.getNikPeternak() == null || request.getNamaPeternak() == null ||
                        request.getNoTelepon() == null || request.getEmail() == null) {
                    System.out.println("Data tidak lengkap atau nikPeternak null, melewati data: " + request);
                    skippedIncomplete++;
                    continue;
                }

                // Skip jika data sudah ada
                if (existingNikSet.contains(request.getNikPeternak())) {
                    System.out.println("NIK Peternak sudah terdaftar: " + request.getNikPeternak());
                    skippedExisting++;
                    continue;
                }
                if (existingEmailSet.contains(request.getEmail())) {
                    System.out.println("Email sudah digunakan: " + request.getEmail());
                    skippedExisting++;
                    continue;
                }
                if (existingNoTelpSet.contains(request.getNoTelepon())) {
                    System.out.println("Nomor Telepon sudah terdaftar: " + request.getNoTelepon());
                    skippedExisting++;
                    continue;
                }

                Petugas petugasResponse = null;

                if (request.getNamaPetugas() == null || request.getNamaPetugas().trim().isEmpty()) {
                    System.out.println("Nama Petugas kosong. Data ini tidak akan diproses.");
                    continue;
                } else {
                    // Coba cari petugas berdasarkan nama dari frontend
                    petugasResponse = petugasRepository.findByNamaPetugas(request.getNamaPetugas());
                    if (petugasResponse == null) {
                        // Jika nama petugas tidak ditemukan, tambahkan petugas baru berdasarkan nama
                        // dari frontend
                        System.out.println("Nama Petugas tidak ditemukan di database. Membuat petugas baru...");

                        Petugas newPetugas = new Petugas();
                        newPetugas
                                .setNikPetugas(request.getNikPetugas() != null ? request.getNikPetugas()
                                        : "nik belum dimasukkan");
                        newPetugas.setNamaPetugas(request.getNamaPetugas());
                        newPetugas.setEmail(
                                request.getEmailPetugas() != null ? request.getEmailPetugas() : "-");
                        newPetugas.setNoTelp(
                                request.getNoTeleponPetugas() != null ? request.getNoTeleponPetugas() : "-");
                        newPetugas.setJob(request.getJob() != null ? request.getJob() : "Pendataan");
                        newPetugas.setWilayah(request.getWilayah() != null ? request.getWilayah() : "-");

                        petugasResponse = petugasRepository.saveImport(newPetugas);

                        System.out.println("Petugas baru berhasil dibuat: " + newPetugas.getNamaPetugas());
                    } else {
                        System.out.println("Petugas ditemukan di database: " + petugasResponse.getNamaPetugas());
                    }
                }

                // Buat objek Peternak
                Peternak peternak = new Peternak();
                peternak.setIdPeternak(request.getIdPeternak());
                peternak.setNikPeternak(request.getNikPeternak());
                peternak.setEmail(request.getEmail());
                peternak.setNoTelepon(request.getNoTelepon());
                peternak.setNamaPeternak(request.getNamaPeternak());
                peternak.setLokasi(request.getLokasi() != null ? request.getLokasi() : "Lokasi tidak diketahui");
                peternak.setAlamat(request.getAlamat() != null ? request.getAlamat() : "Alamat tidak diketahui");
                peternak.setDusun(request.getDusun() != null ? request.getDusun() : "Dusun tidak diketahui");
                peternak.setDesa(request.getDesa());
                peternak.setKecamatan(request.getKecamatan());
                peternak.setKabupaten(request.getKabupaten());
                peternak.setTanggalPendaftaran(request.getTanggalPendaftaran());
                peternak.setPetugas(petugasResponse); // Masukkan objek lengkap Petugas
                peternak.setTanggalLahir(request.getTanggalLahir());
                peternak.setJenisKelamin(request.getJenisKelamin());
                peternak.setIdIsikhnas(request.getIdIsikhnas());
                peternak.setLongitude(request.getLongitude());
                peternak.setLatitude(request.getLatitude());
                peternakList.add(peternak);
                System.out.println("Menambahkan data peternak ke dalam daftar: " + peternak.getNikPeternak());
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data peternak: " + request.getNikPeternak());
                e.printStackTrace();
            }
        }

        if (!peternakList.isEmpty()) {
            System.out.println("Menyimpan data peternak yang valid...");
            peternakRepository.saveAll(peternakList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + peternakList.size());
        } else {
            System.out.println("Tidak ada data peternak baru yang valid untuk disimpan.");

        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);
    }

    // Helper untuk memformat tanggal
    private String formatDate(String date) {
        if (date == null || date.isEmpty()) {
            return "";
        }
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return localDate.toString();
        } catch (DateTimeParseException e) {
            System.err.println("Format tanggal tidak valid: " + date);
            return "";
        }
    }
}

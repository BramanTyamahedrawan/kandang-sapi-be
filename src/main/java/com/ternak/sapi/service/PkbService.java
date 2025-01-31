package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.*;
import com.ternak.sapi.payload.DefaultResponse;
import com.ternak.sapi.payload.PkbRequest;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.repository.PkbRepository;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.JenisHewanRepository;
import com.ternak.sapi.repository.KandangRepository;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.repository.PetugasRepository;
import com.ternak.sapi.util.AppConstants;
import com.ternak.sapi.repository.RumpunHewanRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
// import java.util.Optional;


@Service
public class PkbService {
    private PkbRepository pkbRepository = new PkbRepository();
    private PeternakRepository peternakRepository = new PeternakRepository();
    private PetugasRepository petugasRepository = new PetugasRepository();
    private HewanRepository hewanRepository = new HewanRepository();
    private RumpunHewanRepository rumpunHewanRepository = new RumpunHewanRepository();
    private JenisHewanRepository jenisHewanRepository = new JenisHewanRepository();
    private KandangRepository kandangRepository = new KandangRepository();

    // private static final Logger logger =
    // LoggerFactory.getLogger(PkbService.class);

    public PagedResponse<Pkb> getAllPkb(int page, int size, String peternakId, String petugasId, String jenisHewanID,
            String rumpunHewanID, String kandangID, String hewanId)
            throws IOException {
        validatePageNumberAndSize(page, size);
        List<Pkb> pkbResponse;
        if (peternakId.equalsIgnoreCase("*")) {
            pkbResponse = pkbRepository.findAll(size);
        } else {
            pkbResponse = pkbRepository.findPkbByPeternak(peternakId, size);
        }

        // Retrieve Polls

        return new PagedResponse<>(pkbResponse, pkbResponse.size(), "Successfully get data", 200);
    }

    public Pkb createPkb(PkbRequest pkbRequest) throws IOException {

        if (pkbRepository.existsById(pkbRequest.getIdPkb())) {
            throw new BadRequestException("Id PKB " + pkbRequest.getIdPkb() + " sudah terdaftar.");
        }

        Pkb pkb = new Pkb();
        Peternak peternakResponse = peternakRepository.findById(pkbRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(pkbRequest.getPetugasId().toString());
        Hewan hewanResponse = hewanRepository.findById(pkbRequest.getIdHewan().toString());
        Kandang kandangResponse = kandangRepository.findById(pkbRequest.getIdKandang());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(pkbRequest.getIdJenisHewan());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(pkbRequest.getIdRumpunHewan());

        if (peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && hewanResponse.getIdHewan() != null) {
            pkb.setIdPkb(pkbRequest.getIdPkb());
            pkb.setIdKejadian(pkbRequest.getIdKejadian());
            pkb.setTanggalPkb(pkbRequest.getTanggalPkb());
            pkb.setJumlah(pkbRequest.getJumlah());
            pkb.setUmurKebuntingan(pkbRequest.getUmurKebuntingan());
            pkb.setPeternak(peternakResponse);
            pkb.setPetugas(petugasResponse);
            pkb.setKandang(kandangResponse);
            pkb.setJenisHewan(jenisHewanResponse);
            pkb.setRumpunHewan(rumpunHewanResponse);
            pkb.setHewan(hewanResponse);

            return pkbRepository.save(pkb);
        } else {
            return null;
        }
    }

    public DefaultResponse<Pkb> getPkbById(String pkbId) throws IOException {
        // Retrieve Pkb
        Pkb pkbResponse = pkbRepository.findPkbById(pkbId);
        return new DefaultResponse<>(pkbResponse.isValid() ? pkbResponse : null, pkbResponse.isValid() ? 1 : 0,
                "Successfully get data");
    }

    public Pkb updatePkb(String pkbId, PkbRequest pkbRequest) throws IOException {
        Pkb pkb = new Pkb();

        Peternak peternakResponse = peternakRepository.findById(pkbRequest.getIdPeternak().toString());
        Petugas petugasResponse = petugasRepository.findById(pkbRequest.getPetugasId().toString());
        Hewan hewanResponse = hewanRepository.findById(pkbRequest.getIdHewan().toString());
        JenisHewan jenisHewanResponse = jenisHewanRepository.findById(pkbRequest.getIdJenisHewan());
        RumpunHewan rumpunHewanResponse = rumpunHewanRepository.findById(pkbRequest.getIdRumpunHewan());
        Kandang kandangResponse = kandangRepository.findById(pkbRequest.getIdKandang());

        if (peternakResponse.getIdPeternak() != null && petugasResponse.getPetugasId() != null
                && hewanResponse.getIdHewan() != null) {

            pkb.setIdKejadian(pkbRequest.getIdKejadian());
            pkb.setTanggalPkb(pkbRequest.getTanggalPkb());
            pkb.setJumlah(pkbRequest.getJumlah());
            pkb.setUmurKebuntingan(pkbRequest.getUmurKebuntingan());
            pkb.setPeternak(peternakResponse);
            pkb.setPetugas(petugasResponse);
            pkb.setKandang(kandangResponse);
            pkb.setJenisHewan(jenisHewanResponse);
            pkb.setRumpunHewan(rumpunHewanResponse);
            pkb.setHewan(hewanResponse);

            return pkbRepository.update(pkbId, pkb);
        } else {
            return null;
        }
    }

    public void deletePkbById(String pkbId) throws IOException {
        Pkb pkbResponse = pkbRepository.findPkbById(pkbId);
        if (pkbResponse.isValid()) {
            pkbRepository.deleteById(pkbId);
        } else {
            throw new ResourceNotFoundException("Pkb", "id", pkbId);
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
    public void createPkbImport(List<PkbRequest> pkbRequests) throws IOException {
        System.out.println("Memulai proses penyimpanan data pkb secara bulk...");

        List<Pkb> pkbList = new ArrayList<>();
        Set<String> existingIds = new HashSet<>(); // Set untuk melacak idHewan yang sudah diproses

        int skippedIncomplete = 0;
        int skippedExisting = 0;

        for (PkbRequest request : pkbRequests) {
            try {
                if (existingIds.contains(request.getIdPkb())) {
                    skippedExisting++;
                    continue;
                }

                Pkb pkb = new Pkb();
                pkb.setIdPkb(request.getIdPkb());
                pkb.setIdKejadian(request.getIdKejadian());
                pkb.setTanggalPkb(request.getTanggalPkb());
                pkb.setUmurKebuntingan(request.getUmurKebuntingan());
                pkb.setJumlah(request.getJumlah());

                System.out.println("Petugas diterima dari frontend: " + request.getNamaPetugas());

                Petugas petugasResponse =  null;
                if (request.getNamaPetugas() == null || request.getNamaPetugas().trim().isEmpty()) {
                    System.err.println("Data petugas tidak lengkap dari frontend scip");
                   continue;
                }else{
                    petugasResponse = petugasRepository.findByNamaPetugas(request.getNamaPetugas());
                    if(petugasResponse == null){
                        System.err.println("nama petugas " +request.getNamaPetugas() + "tidak ditemukan, new.." );
                        Petugas petugas = new Petugas();
                        petugas.setPetugasId(UUID.randomUUID().toString());
                        petugas.setNikPetugas(request.getNikPetugas() != null ? request.getNikPetugas() : "-");
                        petugas.setNamaPetugas(request.getNamaPetugas() != null ? request.getNamaPetugas() : "Nama petugas tidak ditemukan");
                        petugas.setEmail("-");
                        petugas.setNoTelp("-");
                        petugas.setWilayah(request.getWilayah() != null ? request.getWilayah() : "-");
                        petugas.setJob("Pemeriksa Kebuntingan");
                        petugasResponse = petugasRepository.saveImport(petugas);
                    }else{
                        System.err.println("nama petugas " +petugasResponse.getNamaPetugas() + " ditemukan" );
                    }
                }

                System.out.println("Peternak diterima dari frontend: " + request.getNamaPeternak());
                Peternak peternakResponse = null;
                if (request.getNamaPeternak() == null || request.getNamaPeternak().trim().isEmpty()) {
                    System.err.println("Data peternak tidak lengkap dari frontend scip");
                    continue;
                }else{
                    peternakResponse =  peternakRepository.findByNamaPeternak(request.getNamaPeternak());
                    if(peternakResponse == null) {
                        System.err.println("nama peternak " +request.getNamaPeternak() + "tidak ditemukan, new.." );
                        Peternak newPeternak = new Peternak();
                        newPeternak.setIdPeternak(UUID.randomUUID().toString());
                        newPeternak.setNikPeternak(request.getNikPeternak() != null ? request.getNikPeternak() : "-");
                        newPeternak.setNamaPeternak(
                                request.getNamaPeternak() != null ? request.getNamaPeternak() : "Nama Tidak Diketahui");
                        newPeternak.setLokasi(
                                request.getLokasi() != null ? request.getLokasi() : "Lokasi Tidak Diketahui");
                        newPeternak.setTanggalPendaftaran(
                                request.getTanggalPendaftaran() != null ? request.getTanggalPendaftaran()
                                        : "Tanggal Pendaftaran Tidak Diketahui");

                        newPeternak
                                .setNoTelepon("08123456789");
                        newPeternak.setEmail("Email Tidak Diketahui");
                        newPeternak.setAlamat("Alamat Tidak Diketahui");
                        newPeternak.setJenisKelamin("Lainnya");
                        newPeternak.setTanggalLahir(
                                request.getTanggalLahir() != null ? request.getTanggalLahir()
                                        : "Tanggal Lahir Tidak Diketahui");
                        newPeternak.setIdIsikhnas(
                                request.getIdIsikhnas() != null ? request.getIdIsikhnas()
                                        : "Id Isikhnas Tidak Diketahui");
                        newPeternak.setProvinsi("Provinsi Tidak Diketahui");
                        newPeternak.setDusun("Dusun Tidak Diketahui");
                        newPeternak.setDesa("Desa Tidak Diketahui");
                        newPeternak.setKecamatan("Kecamatan Tidak Diketahui");
                        newPeternak.setKabupaten("Kabupaten Tidak Diketahui");
                        newPeternak.setLatitude(
                                request.getLatitude() != null ? request.getLatitude()
                                        : "Latitude Tidak Diketahui");
                        newPeternak.setLongitude(
                                request.getLongitude() != null ? request.getLongitude() : "Longitude Tidak Diketahui");
                        newPeternak.setPetugas(petugasResponse);
                        peternakResponse = peternakRepository.save(newPeternak);
                    }else{
                        System.err.println("nama peternak " +peternakResponse.getNamaPeternak() + " ditemukan" );
                    }

                }

                JenisHewan jenisHewanResponse = null;
                if (request.getJenis() == null || request.getJenis().trim().isEmpty()) {
                    System.err.println("Data jenis hewan tidak lengkap dari frontend scip");
                    continue;
                } else {
                    jenisHewanResponse = jenisHewanRepository.findByJenis(request.getJenis());
                    if (jenisHewanResponse == null) {
                        System.err.println("jenis hewan " +request.getJenis() + "tidak ditemukan, new.." );
                        JenisHewan newJenisHewan = new JenisHewan();
                        newJenisHewan.setIdJenisHewan(UUID.randomUUID().toString());
                        newJenisHewan.setJenis(request.getJenis() != null ? request.getJenis()
                                : "Jenis hewan tidak ditemukan waktu import pkb");
                        newJenisHewan.setDeskripsi( "-");

                        // // Save Jenis Hewan Baru
                        jenisHewanResponse = jenisHewanRepository.save(newJenisHewan);
                        System.out.println("Jenis Hewan baru ditambahkan: " + newJenisHewan.getIdJenisHewan());
                    }else {
                        System.err.println("jenis hewan " +jenisHewanResponse.getJenis() + "ditemukan" );
                    }
                }

                System.out.println("id kandang diterima dari frontend: " + request.getNamaKandang());
                Kandang kandangResponse = null;
                if (request.getNamaPeternak() == null || request.getNamaPeternak().trim().isEmpty()) {
                    System.err.println("Nama Kandang kosong. Data ini tidak akan diproses.");
                    continue;
                } else {
                    kandangResponse = kandangRepository
                            .findByNamaKandang("Kandang " + jenisHewanResponse.getJenis() + " "
                                    + peternakResponse.getNamaPeternak());
                    if (kandangResponse == null) {
                        // Jika nama kandang tidak ditemukan, tambahkan petugas baru berdasarkan nama
                        // dari frontend
                        System.err.println("Nama Kandang tidak ditemukan di database. Membuat kandang baru...");

                        Kandang newKandang = new Kandang();
                        newKandang.setIdKandang(request.getIdKandang() != null ? request.getIdKandang()
                                : UUID.randomUUID().toString());
                        if (jenisHewanResponse.getJenis() != null && peternakResponse.getNamaPeternak() != null) {
                            newKandang.setNamaKandang("Kandang " + jenisHewanResponse.getJenis() + " "
                                    + peternakResponse.getNamaPeternak());
                        } else if (jenisHewanResponse.getJenis() == null) {
                            newKandang.setNamaKandang("Kandang hewan umum" + peternakResponse.getNamaPeternak());
                        } else if (peternakResponse.getNamaPeternak() == null) {
                            newKandang.setNamaKandang(
                                    "Kandang " + jenisHewanResponse.getJenis() + "nya peternak tidak dikenal");
                        } else {
                            newKandang.setNamaKandang("Nama Kandang tidak ketahui waktu import hewan");
                        }
                        newKandang.setPeternak(peternakResponse);
                        newKandang.setJenisHewan(jenisHewanResponse);
                        newKandang.setAlamat("-");
                        newKandang.setLuas(request.getLuas() != null ? request.getLuas() : "-");
                        newKandang.setKapasitas(request.getKapasitas() != null ? request.getKapasitas() : "-");
                        newKandang.setJenisKandang(request.getJenisKandang() != null ? request.getJenisKandang() : "-");
                        newKandang.setNilaiBangunan(
                                request.getNilaiBangunan() != null ? request.getNilaiBangunan() : "-");
                        newKandang.setLongitude(request.getLongitude() != null ? request.getLongitude() : "-");
                        newKandang.setLatitude(request.getLatitude() != null ? request.getLatitude() : "-");
                        kandangResponse = kandangRepository.saveImportByHewan(newKandang);
                        System.out.println("Pemilik Kandang : " + peternakResponse.getNamaPeternak());
                        System.out.println("Kandang baru berhasil dibuat: " + newKandang.getNamaKandang());
                    } else {
                        System.err.println("Kandang ditemukan di database: " + kandangResponse.getNamaKandang());
                    }
                }

                RumpunHewan rumpunResponse = null;
                if (request.getRumpun() == null) {
                    System.err.println("Nama rumpun kosong. Data ini tidak akan diproses.");
                    continue;
                } else {
                    rumpunResponse = rumpunHewanRepository.findByRumpun(request.getRumpun());
                    if (rumpunResponse == null) {
                        System.err.println("nama rumpun " +request.getRumpun()+ " tidak ditemukan, new..");
                        RumpunHewan defaultRumpunHewan = new RumpunHewan();
                        defaultRumpunHewan
                                .setIdRumpunHewan(UUID.randomUUID().toString());
                        defaultRumpunHewan.setRumpun(request.getRumpun() != null ? request.getRumpun()
                                : "Nama rumpun hewan tidak ditemukan waktu import pkb");
                        defaultRumpunHewan
                                .setDeskripsi(request.getDeskripsiRumpun() != null ? request.getDeskripsiRumpun()
                                        : "Deskripsi Rumpun hewan tidak ditemukan waktu import pkb");

                        rumpunResponse = rumpunHewanRepository.save(defaultRumpunHewan);
                    } else {
                        System.err.println("Rumpun hewan ditemukan didatabase");
                    }
                }

                Hewan hewanResponse = null;
                if(request.getNoKartuTernak() == null && request.getKodeEartagNasional() == null){
                    System.err.println("No kartu dan no eartag hewan kosong dari front end scip");
                    continue;
                }else{
                   hewanResponse = request.getKodeEartagNasional() != null
                                ? hewanRepository.findByNoEartag(request.getKodeEartagNasional())
                                : hewanRepository.findByNoKartuTernak(request.getNoKartuTernak());

                   if(hewanResponse == null){
                       System.err.println("hewan tidak ditemukan new...");
                       Hewan hewan = new Hewan();
                       hewan.setIdHewan(request.getIdHewan());
                       hewan.setIdIsikhnasTernak("-");
                       hewan.setNoKartuTernak(request.getNoKartuTernak() != null ? request.getNoKartuTernak() : "-");
                       hewan.setKodeEartagNasional(request.getKodeEartagNasional() != null ? request.getKodeEartagNasional() : "-");
                       hewan.setSex(request.getSex() !=  null ? request.getSex() : "-");
                       hewan.setUmur(request.getUmur()!= null ? request.getUmur() : "-");
                       hewan.setTanggalTerdaftar(request.getTanggalTerdaftar() != null ? request.getTanggalTerdaftar() : "-");
                       hewan.setIdentifikasiHewan(request.getIdentifikasiHewan() != null ? request.getIdentifikasiHewan() : "-");
                       hewan.setTanggalLahir(request.getTanggalLahir() != null ? request.getTanggalLahir() : "-");
                       hewan.setTempatLahir(request.getTempatLahir() != null ? request.getTempatLahir() : "-");

                       // Set relasi ke objek Hewan
                       hewan.setPeternak(peternakResponse);
                       hewan.setJenisHewan(jenisHewanResponse);
                       hewan.setPetugas(petugasResponse);
                       hewan.setKandang(kandangResponse);
                       hewan.setRumpunHewan(rumpunResponse);

                       hewanResponse = hewanRepository.saveByIDHewan(hewan);
                   }else{
                       System.err.println("hewan ditemukan by no kartu " + hewanResponse.getNoKartuTernak() + "dan eartag " + hewanResponse.getKodeEartagNasional());
                   }
                }


                pkb.setPetugas(petugasResponse);
                pkb.setPeternak(peternakResponse);
                pkb.setRumpunHewan(rumpunResponse);
                pkb.setJenisHewan(jenisHewanResponse);
                pkb.setKandang(kandangResponse);
                pkb.setHewan(hewanResponse);

                existingIds.add(request.getIdPkb());
                pkbList.add(pkb);

            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat memproses data pkb: " + request);
                e.printStackTrace();
                skippedIncomplete++;
            }
        }

        if (!pkbList.isEmpty()) {
            System.out.println("Menyimpan data pkb yang valid...");
            pkbRepository.saveImport(pkbList);
            System.out.println("Proses penyimpanan selesai. Total data yang disimpan: " + pkbList.size());
        } else {
            System.out.println("Tidak ada data pkb baru yang valid untuk disimpan.");
        }

        System.out.println("Proses selesai. Data tidak lengkap: " + skippedIncomplete + ", Data sudah terdaftar: "
                + skippedExisting);

    }

}

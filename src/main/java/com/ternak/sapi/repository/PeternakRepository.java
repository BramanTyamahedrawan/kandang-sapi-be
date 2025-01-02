package com.ternak.sapi.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;

public class PeternakRepository {
    Configuration conf = HBaseConfiguration.create();
    String tableName = "peternakdev";

    public List<Peternak> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idPeternak", "idPeternak");
        columnMapping.put("nikPeternak", "nikPeternak");
        columnMapping.put("namaPeternak", "namaPeternak");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("tanggalPendaftaran", "tanggalPendaftaran");
        columnMapping.put("noTelepon", "noTelepon");
        columnMapping.put("email", "email");
        columnMapping.put("jenisKelamin", "jenisKelamin");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("idIsikhnas", "idIsikhnas");
        columnMapping.put("dusun", "dusun");
        columnMapping.put("desa", "desa");
        columnMapping.put("kecamatan", "kecamatan");
        columnMapping.put("kabupaten", "kabupaten");
        columnMapping.put("alamat", "alamat");
        columnMapping.put("latitude", "latitude");
        columnMapping.put("longitude", "longitude");

        return client.showListTable(tableUsers.toString(), columnMapping, Peternak.class, size);
    }

    public List<Peternak> findAllByUserID(String userID, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idPeternak", "idPeternak");
        columnMapping.put("nikPeternak", "nikPeternak");
        columnMapping.put("namaPeternak", "namaPeternak");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("tanggalPendaftaran", "tanggalPendaftaran");
        columnMapping.put("noTelepon", "noTelepon");
        columnMapping.put("email", "email");
        columnMapping.put("jenisKelamin", "jenisKelamin");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("idIsikhnas", "idIsikhnas");
        columnMapping.put("dusun", "dusun");
        columnMapping.put("desa", "desa");
        columnMapping.put("kecamatan", "kecamatan");
        columnMapping.put("kabupaten", "kabupaten");
        columnMapping.put("alamat", "alamat");
        columnMapping.put("latitude", "latitude");
        columnMapping.put("longitude", "longitude");

        return client.getDataListByColumn(tableUsers.toString(), columnMapping, "user", "id", userID, Peternak.class,
                size);
    }

    public Peternak save(Peternak peternak) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = peternak.getIdPeternak();

        TableName tablePeternak = TableName.valueOf(tableName);
        client.insertRecord(tablePeternak, rowKey, "main", "idPeternak", peternak.getIdPeternak());
        client.insertRecord(tablePeternak, rowKey, "main", "nikPeternak", peternak.getNikPeternak());
        client.insertRecord(tablePeternak, rowKey, "main", "namaPeternak", peternak.getNamaPeternak());
        client.insertRecord(tablePeternak, rowKey, "main", "lokasi", peternak.getLokasi());
        client.insertRecord(tablePeternak, rowKey, "main", "tanggalPendaftaran", peternak.getTanggalPendaftaran());

        client.insertRecord(tablePeternak, rowKey, "main", "noTelepon", peternak.getNoTelepon());
        client.insertRecord(tablePeternak, rowKey, "main", "email", peternak.getEmail());
        client.insertRecord(tablePeternak, rowKey, "main", "jenisKelamin", peternak.getJenisKelamin());
        client.insertRecord(tablePeternak, rowKey, "main", "tanggalLahir", peternak.getTanggalLahir());
        client.insertRecord(tablePeternak, rowKey, "main", "idIsikhnas", peternak.getIdIsikhnas());

        client.insertRecord(tablePeternak, rowKey, "main", "dusun", peternak.getIdIsikhnas());
        client.insertRecord(tablePeternak, rowKey, "main", "desa", peternak.getDesa());
        client.insertRecord(tablePeternak, rowKey, "main", "kecamatan", peternak.getKecamatan());
        client.insertRecord(tablePeternak, rowKey, "main", "kabupaten", peternak.getKabupaten());
        client.insertRecord(tablePeternak, rowKey, "main", "alamat", peternak.getAlamat());
        client.insertRecord(tablePeternak, rowKey, "main", "latitude", peternak.getLatitude());
        client.insertRecord(tablePeternak, rowKey, "main", "longitude", peternak.getLongitude());

        client.insertRecord(tablePeternak, rowKey, "petugas", "nikPetugas", peternak.getPetugas().getNikPetugas());
        client.insertRecord(tablePeternak, rowKey, "petugas", "namaPetugas", peternak.getPetugas().getNamaPetugas());
        client.insertRecord(tablePeternak, rowKey, "petugas", "noTelp", peternak.getPetugas().getNoTelp());
        client.insertRecord(tablePeternak, rowKey, "petugas", "email", peternak.getPetugas().getEmail());
        client.insertRecord(tablePeternak, rowKey, "petugas", "job", peternak.getPetugas().getJob());
        client.insertRecord(tablePeternak, rowKey, "detail", "createdBy", "Polinema");
        return peternak;
    }

    public List<Peternak> saveAll(List<Peternak> peternakList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePeternak = TableName.valueOf(tableName);

        System.out.println("Memulai penyimpanan data ke HBase...");
        List<String> failedRows = new ArrayList<>();

        for (Peternak peternak : peternakList) {
            try {
                // Validasi nilai-nilai penting tidak boleh null
                if (peternak.getNikPeternak() == null || peternak.getNoTelepon() == null
                        || peternak.getEmail() == null) {
                    System.out.println("Data tidak lengkap, melewati penyimpanan NIK: " + peternak.getNikPeternak());
                    continue;
                }

                // Safe value jika ada nilai yang null
                String rowKey = safeString(peternak.getIdPeternak());

                // Insert records into HBase dengan validasi null
                client.insertRecord(tablePeternak, rowKey, "main", "idPeternak", safeString(peternak.getIdPeternak()));
                client.insertRecord(tablePeternak, rowKey, "main", "nikPeternak",
                        safeString(peternak.getNikPeternak()));
                client.insertRecord(tablePeternak, rowKey, "main", "email", safeString(peternak.getEmail()));
                client.insertRecord(tablePeternak, rowKey, "main", "noTelepon", safeString(peternak.getNoTelepon()));
                client.insertRecord(tablePeternak, rowKey, "main", "namaPeternak",
                        safeString(peternak.getNamaPeternak()));

                if (peternak.getPetugas() != null) {
                    Petugas petugas = peternak.getPetugas();
                    client.insertRecord(tablePeternak, rowKey, "petugas", "nikPetugas",
                            safeString(petugas.getNikPetugas()));
                    client.insertRecord(tablePeternak, rowKey, "petugas", "namaPetugas",
                            safeString(petugas.getNamaPetugas()));
                    client.insertRecord(tablePeternak, rowKey, "petugas", "email",
                            safeString(petugas.getEmail()));
                    client.insertRecord(tablePeternak, rowKey, "petugas", "job", safeString(petugas.getJob()));
                    client.insertRecord(tablePeternak, rowKey, "petugas", "noTelp", safeString(petugas.getNoTelp()));
                    // Tambahkan kolom lainnya sesuai dengan atribut di `Petugas`
                }

                client.insertRecord(tablePeternak, rowKey, "main", "lokasi", safeString(peternak.getLokasi()));
                client.insertRecord(tablePeternak, rowKey, "main", "alamat", safeString(peternak.getAlamat()));
                client.insertRecord(tablePeternak, rowKey, "main", "dusun", safeString(peternak.getDusun()));
                client.insertRecord(tablePeternak, rowKey, "main", "desa", safeString(peternak.getDesa()));
                client.insertRecord(tablePeternak, rowKey, "main", "kecamatan", safeString(peternak.getKecamatan()));
                client.insertRecord(tablePeternak, rowKey, "main", "kabupaten", safeString(peternak.getKabupaten()));
                client.insertRecord(tablePeternak, rowKey, "main", "latitude", safeString(peternak.getLatitude()));
                client.insertRecord(tablePeternak, rowKey, "main", "longitude", safeString(peternak.getLongitude()));
                client.insertRecord(tablePeternak, rowKey, "main", "jenisKelamin",
                        safeString(peternak.getJenisKelamin()));
                client.insertRecord(tablePeternak, rowKey, "main", "tanggalPendaftaran",
                        safeString(peternak.getTanggalPendaftaran()));
                client.insertRecord(tablePeternak, rowKey, "main", "tanggalLahir",
                        safeString(peternak.getTanggalLahir()));
                client.insertRecord(tablePeternak, rowKey, "main", "idIsikhnas", safeString(peternak.getIdIsikhnas()));
                client.insertRecord(tablePeternak, rowKey, "detail", "created_by", "Polinema");

                System.out.println("Berhasil menyimpan NIK: " + peternak.getNikPeternak());
            } catch (Exception e) {
                failedRows.add(peternak.getNikPeternak());
                System.err.println(
                        "Failed to insert record for NIK: " + peternak.getNikPeternak() + ", Error: " + e.getMessage());
            }
        }

        if (!failedRows.isEmpty()) {
            throw new IOException("Failed to save records for NIKs: " + String.join(", ", failedRows));
        }

        return peternakList;
    }

    // Utility untuk memastikan nilai string tidak null
    private String safeString(String value) {
        return value != null ? value : "";
    }

    public Peternak findById(String idPeternak) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tablePeternak = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idPeternak", "idPeternak");
        columnMapping.put("nikPeternak", "nikPeternak");
        columnMapping.put("namaPeternak", "namaPeternak");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("tanggalPendaftaran", "tanggalPendaftaran");

        columnMapping.put("noTelepon", "noTelepon");
        columnMapping.put("email", "email");
        columnMapping.put("jenisKelamin", "jenisKelamin");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("idIsikhnas", "idIsikhnas");

        columnMapping.put("dusun", "dusun");
        columnMapping.put("desa", "desa");
        columnMapping.put("kecamatan", "kecamatan");
        columnMapping.put("kabupaten", "kabupaten");
        columnMapping.put("alamat", "alamat");
        columnMapping.put("latitude", "latitude");
        columnMapping.put("longitude", "longitude");

        Peternak peternak = client.getDataByColumn(tablePeternak.toString(), columnMapping, "main", "idPeternak",
                idPeternak, Peternak.class);

        System.out.println("Data Peternak ditemukan: by id" + peternak);

        return peternak.getIdPeternak() != null ? peternak : null;
    }

    public Peternak update(String peternakId, Peternak peternak) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePeternak = TableName.valueOf(tableName);
        client.insertRecord(tablePeternak, peternakId, "main", "nikPeternak", peternak.getNikPeternak());
        client.insertRecord(tablePeternak, peternakId, "main", "namaPeternak", peternak.getNamaPeternak());
        client.insertRecord(tablePeternak, peternakId, "main", "lokasi", peternak.getLokasi());
        client.insertRecord(tablePeternak, peternakId, "main", "tanggalPendaftaran", peternak.getTanggalPendaftaran());

        client.insertRecord(tablePeternak, peternakId, "main", "noTelepon", peternak.getNoTelepon());
        client.insertRecord(tablePeternak, peternakId, "main", "email", peternak.getEmail());
        client.insertRecord(tablePeternak, peternakId, "main", "jenisKelamin", peternak.getJenisKelamin());
        client.insertRecord(tablePeternak, peternakId, "main", "tanggalLahir", peternak.getTanggalLahir());
        client.insertRecord(tablePeternak, peternakId, "main", "idIsikhnas", peternak.getIdIsikhnas());

        client.insertRecord(tablePeternak, peternakId, "main", "dusun", peternak.getIdIsikhnas());
        client.insertRecord(tablePeternak, peternakId, "main", "desa", peternak.getDesa());
        client.insertRecord(tablePeternak, peternakId, "main", "kecamatan", peternak.getKecamatan());
        client.insertRecord(tablePeternak, peternakId, "main", "kabupaten", peternak.getKabupaten());
        client.insertRecord(tablePeternak, peternakId, "main", "alamat", peternak.getAlamat());
        client.insertRecord(tablePeternak, peternakId, "main", "latitude", peternak.getLatitude());
        client.insertRecord(tablePeternak, peternakId, "main", "longitude", peternak.getLongitude());

        client.insertRecord(tablePeternak, peternakId, "petugas", "nikPetugas", peternak.getPetugas().getNikPetugas());
        client.insertRecord(tablePeternak, peternakId, "petugas", "namaPetugas",
                peternak.getPetugas().getNamaPetugas());
        client.insertRecord(tablePeternak, peternakId, "petugas", "noTelp", peternak.getPetugas().getNoTelp());
        client.insertRecord(tablePeternak, peternakId, "petugas", "email", peternak.getPetugas().getEmail());
        client.insertRecord(tablePeternak, peternakId, "detail", "created_by", "Polinema");
        return peternak;
    }

    // public boolean existsByUserID(String UID) throws IOException {
    // HBaseCustomClient client = new HBaseCustomClient(conf);
    //
    // TableName tableUsers = TableName.valueOf(tableName);
    // Map<String, String> columnMapping = new HashMap<>();
    //
    // // Add the mappings to the HashMap
    // columnMapping.put("id", "id");
    //
    // Peternak peternak = client.getDataByColumn(tableUsers.toString(),
    // columnMapping, "user", "id", UID, Peternak.class);
    // if(peternak.getIdPeternak()!= null){
    // return true;
    // }else{
    // return false;
    // }
    // }

    public boolean deleteById(String peternakId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, peternakId);
        return true;
    }

    public boolean existsByNikPeternak(String nikPeternak) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePeternak = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("nikPeternak", "nikPeternak");

        Peternak peternak = client.getDataByColumn(tablePeternak.toString(), columnMapping, "main", "nikPeternak",
                nikPeternak, Peternak.class);
        return peternak.getNikPeternak() != null;
    }

    public List<String> findExistingNikPetugas(List<String> nikPetugasList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePetugas = TableName.valueOf("petugasdev"); // Nama tabel petugas di HBase
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("nikPetugas", "nikPetugas");

        // Implementasi pencarian batch untuk nikPetugas
        return client.getExistingByColumn(tablePetugas.toString(), columnMapping, "main", "nikPetugas", nikPetugasList);
    }

    public Peternak findByNikPeternak(String nikPeternak) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePeternak = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("nikPeternak", "nikPeternak");
        columnMapping.put("namaPeternak", "namaPeternak");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("tanggalPendaftaran", "tanggalPendaftaran");

        columnMapping.put("noTelepon", "noTelepon");
        columnMapping.put("email", "email");
        columnMapping.put("jenisKelamin", "jenisKelamin");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("idIsikhnas", "idIsikhnas");

        columnMapping.put("dusun", "dusun");
        columnMapping.put("desa", "desa");
        columnMapping.put("kecamatan", "kecamatan");
        columnMapping.put("kabupaten", "kabupaten");
        columnMapping.put("alamat", "alamat");
        columnMapping.put("latitude", "latitude");
        columnMapping.put("longitude", "longitude");

        Peternak peternak = client.getDataByColumn(tablePeternak.toString(), columnMapping, "main", "nikPeternak",
                nikPeternak, Peternak.class);

        System.out.println("Data Peternak ditemukan: by nik" + peternak);

        return peternak.getNikPeternak() != null ? peternak : null;
    }

    public List<String> findExistingNamaPetugas(List<String> namaPetugasList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePetugas = TableName.valueOf("petugasdev"); // Nama tabel petugas di HBase
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("namaPetugas", "namaPetugas");

        // Implementasi pencarian batch untuk namaPetugas
        return client.getExistingByColumn(tablePetugas.toString(), columnMapping, "main", "namaPetugas",
                namaPetugasList);
    }

    public boolean existsByEmail(String email) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePeternak = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("email", "email");

        Peternak peternak = client.getDataByColumn(tablePeternak.toString(), columnMapping, "main", "email", email,
                Peternak.class);
        return peternak.getEmail() != null;
    }

    public boolean existsByNoTelepon(String noTelepon) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePeternak = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("noTelepon", "noTelepon");

        Peternak peternak = client.getDataByColumn(tablePeternak.toString(), columnMapping, "main", "noTelepon",
                noTelepon, Peternak.class);
        return peternak.getNoTelepon() != null;
    }

    public List<String> findExistingNik(List<String> nikList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePeternak = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("nikPeternak", "nikPeternak");

        // Implementasi pencarian batch untuk NIK
        return client.getExistingByColumn(tablePeternak.toString(), columnMapping, "main", "nikPeternak", nikList);
    }

    public List<String> findExistingEmail(List<String> emailList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePeternak = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("email", "email");

        // Implementasi pencarian batch untuk Email
        return client.getExistingByColumn(tablePeternak.toString(), columnMapping, "main", "email", emailList);
    }

    public List<String> findExistingNoTelp(List<String> noTelpList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePeternak = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("noTelepon", "noTelepon");

        // Implementasi pencarian batch untuk NoTelp
        return client.getExistingByColumn(tablePeternak.toString(), columnMapping, "main", "noTelepon", noTelpList);
    }
}

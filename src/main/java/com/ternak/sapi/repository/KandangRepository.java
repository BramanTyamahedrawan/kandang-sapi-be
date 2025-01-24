
package com.ternak.sapi.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ternak.sapi.model.Hewan;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Peternak;

public class KandangRepository {
        Configuration conf = HBaseConfiguration.create();
        String tableName = "kandangdev";
        // HewanController departmentController = new HewanController();

        public List<Kandang> findAll(int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableUsers = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idKandang", "idKandang");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("luas", "luas");
                columnMapping.put("kapasitas", "kapasitas");
                columnMapping.put("jenisKandang", "jenisKandang");
                columnMapping.put("nilaiBangunan", "nilaiBangunan");
                columnMapping.put("alamat", "alamat");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");
                columnMapping.put("namaKandang", "namaKandang");
                return client.showListTable(tableUsers.toString(), columnMapping, Kandang.class, size);
        }

        public Kandang save(Kandang kandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                String rowKey = kandang.getIdKandang();
                TableName tableKandang = TableName.valueOf(tableName);
                client.insertRecord(tableKandang, rowKey, "main", "idKandang", rowKey);
                client.insertRecord(tableKandang, rowKey, "main", "luas", kandang.getLuas());
                client.insertRecord(tableKandang, rowKey, "main", "kapasitas", kandang.getKapasitas());
                client.insertRecord(tableKandang, rowKey, "main", "jenisKandang", kandang.getJenisKandang());
                client.insertRecord(tableKandang, rowKey, "main", "nilaiBangunan", kandang.getNilaiBangunan());
                client.insertRecord(tableKandang, rowKey, "main", "alamat", kandang.getAlamat());
                client.insertRecord(tableKandang, rowKey, "main", "latitude", kandang.getLatitude());
                client.insertRecord(tableKandang, rowKey, "main", "longitude", kandang.getLongitude());
                client.insertRecord(tableKandang, rowKey, "main", "file_path", kandang.getFile_path());
                client.insertRecord(tableKandang, rowKey, "main", "namaKandang", kandang.getNamaKandang());
                // Insert Peternak
                client.insertRecord(tableKandang, rowKey, "peternak", "idPeternak",
                                kandang.getPeternak().getIdPeternak());
                client.insertRecord(tableKandang, rowKey, "peternak", "nikPeternak",
                                kandang.getPeternak().getNikPeternak());
                client.insertRecord(tableKandang, rowKey, "peternak", "namaPeternak",
                                kandang.getPeternak().getNamaPeternak());
                client.insertRecord(tableKandang, rowKey, "peternak", "email",
                                kandang.getPeternak().getEmail());
                client.insertRecord(tableKandang, rowKey, "peternak", "lokasi",
                                kandang.getPeternak().getLokasi());
                client.insertRecord(tableKandang, rowKey, "peternak", "noTelepon",
                                kandang.getPeternak().getNoTelepon());
                client.insertRecord(tableKandang, rowKey, "peternak", "alamat",
                                kandang.getPeternak().getAlamat());
                client.insertRecord(tableKandang, rowKey, "peternak", "dusun",
                                kandang.getPeternak().getDusun());
                client.insertRecord(tableKandang, rowKey, "peternak", "desa",
                                kandang.getPeternak().getDesa());
                client.insertRecord(tableKandang, rowKey, "peternak", "kecamatan",
                                kandang.getPeternak().getKecamatan());
                client.insertRecord(tableKandang, rowKey, "peternak", "kabupaten",
                                kandang.getPeternak().getKabupaten());
                client.insertRecord(tableKandang, rowKey, "peternak", "latitude",
                                kandang.getPeternak().getLatitude());
                client.insertRecord(tableKandang, rowKey, "peternak", "longitude",
                                kandang.getPeternak().getLongitude());
                client.insertRecord(tableKandang, rowKey, "peternak", "jenisKelamin",
                                kandang.getPeternak().getJenisKelamin());
                client.insertRecord(tableKandang, rowKey, "peternak", "tanggalPendaftaran",
                                kandang.getPeternak().getTanggalPendaftaran());
                client.insertRecord(tableKandang, rowKey, "peternak", "tanggalLahir",
                                kandang.getPeternak().getTanggalLahir());
                client.insertRecord(tableKandang, rowKey, "peternak", "idIsikhnas",
                                kandang.getPeternak().getIdIsikhnas());
                // Insert jenis hewan
                client.insertRecord(tableKandang, rowKey, "jenisHewan", "idJenisHewan",
                                kandang.getJenisHewan().getIdJenisHewan());
                client.insertRecord(tableKandang, rowKey, "jenisHewan", "jenis", kandang.getJenisHewan().getJenis());
                client.insertRecord(tableKandang, rowKey, "jenisHewan", "deskripsi",
                                kandang.getJenisHewan().getDeskripsi());
                client.insertRecord(tableKandang, rowKey, "detail", "created_by", "Polinema");
                return kandang;
        }

        public List<Kandang> saveAll(List<Kandang> kandangList) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKandang = TableName.valueOf(tableName);

                System.out.println("Memulai penyimpanan data ke HBase...");
                List<String> failedRows = new ArrayList<>();

                for (Kandang kandang : kandangList) {
                        try {
                                // Validasi jenis kandang jika null
                                if (kandang.getJenisKandang() == null || kandang.getJenisKandang().isEmpty()) {
                                        kandang.setJenisKandang("Permanen");
                                }

                                // validasi nama kandang jika null
                                if (kandang.getNamaKandang() == null || kandang.getNamaKandang().isEmpty()) {
                                        kandang.setNamaKandang("Kandang " + kandang.getPeternak().getNamaPeternak());
                                }

                                String rowKey = safeString(kandang.getIdKandang());

                                if (kandang.getJenisHewan() != null) {
                                        JenisHewan jenisHewan = new JenisHewan();
                                        client.insertRecord(tableKandang, rowKey, "jenisHewan", "idJenisHewan",
                                                        safeString(jenisHewan.getIdJenisHewan()));
                                        client.insertRecord(tableKandang, rowKey, "jenisHewan", "jenis",
                                                        safeString(jenisHewan.getJenis()));
                                        client.insertRecord(tableKandang, rowKey, "jenisHewan", "deskripsi",
                                                        safeString(jenisHewan.getDeskripsi()));
                                        client.insertRecord(tableKandang, rowKey, "detail", "created_by", "Polinema");
                                }

                                // Jika peternak ada, masukkan informasi peternak
                                if (kandang.getPeternak() != null) {
                                        Peternak peternak = kandang.getPeternak();
                                        client.insertRecord(tableKandang, rowKey, "peternak", "idPeternak",
                                                        safeString(peternak.getIdPeternak()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "nikPeternak",
                                                        safeString(peternak.getNikPeternak()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "namaPeternak",
                                                        safeString(peternak.getNamaPeternak()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "email",
                                                        safeString(peternak.getEmail()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "lokasi",
                                                        safeString(peternak.getLokasi()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "noTelepon",
                                                        safeString(peternak.getNoTelepon()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "alamat",
                                                        safeString(peternak.getAlamat()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "dusun",
                                                        safeString(peternak.getDusun()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "desa",
                                                        safeString(peternak.getDesa()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "kecamatan",
                                                        safeString(peternak.getKecamatan()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "kabupaten",
                                                        safeString(peternak.getKabupaten()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "latitude",
                                                        safeString(peternak.getLatitude()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "longitude",
                                                        safeString(peternak.getLongitude()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "jenisKelamin",
                                                        safeString(peternak.getJenisKelamin()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "tanggalPendaftaran",
                                                        safeString(peternak.getTanggalPendaftaran()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "tanggalLahir",
                                                        safeString(peternak.getTanggalLahir()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "idIsikhnas",
                                                        safeString(peternak.getIdIsikhnas()));
                                        client.insertRecord(tableKandang, rowKey, "detail", "created_by", "Polinema");

                                }

                                // Insert data kandang ke HBase
                                client.insertRecord(tableKandang, rowKey, "main", "idKandang",
                                                safeString(kandang.getIdKandang()));
                                client.insertRecord(tableKandang, rowKey, "main", "namaKandang",
                                                safeString(kandang.getNamaKandang()));
                                client.insertRecord(tableKandang, rowKey, "main", "alamat",
                                                safeString(kandang.getAlamat()));
                                client.insertRecord(tableKandang, rowKey, "main", "luas",
                                                safeString(kandang.getLuas()));
                                client.insertRecord(tableKandang, rowKey, "main", "nilaiBangunan",
                                                safeString(kandang.getNilaiBangunan()));
                                client.insertRecord(tableKandang, rowKey, "main", "jenisKandang",
                                                safeString(kandang.getJenisKandang()));
                                client.insertRecord(tableKandang, rowKey, "main", "latitude",
                                                safeString(kandang.getLatitude()));
                                client.insertRecord(tableKandang, rowKey, "main", "longitude",
                                                safeString(kandang.getLongitude()));
                                client.insertRecord(tableKandang, rowKey, "main", "kapasitas",
                                                safeString(kandang.getKapasitas()));
                                System.out.println("Berhasil menyimpan data Kandang ID: " + kandang.getIdKandang());

                                System.out.println("data id jenis hewan" + kandang.getIdJenisHewan());
                                System.out.println("Berhasil menyimpan data Kandang ID: " + kandang.getIdKandang());
                        } catch (Exception e) {
                                failedRows.add(kandang.getIdKandang());
                                System.err
                                                .println("Gagal menyimpan Kandang ID: " + kandang.getIdKandang()
                                                                + " Error: " + e.getMessage());
                        }
                }

                if (!failedRows.isEmpty()) {
                        throw new IOException(
                                        "Failed to save records for Kandang IDs: " + String.join(", ", failedRows));
                }

                return kandangList;
        }

        public List<Kandang> saveID(List<Kandang> kandangList) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKandang = TableName.valueOf(tableName);

                System.out.println("Memulai penyimpanan data ke HBase...");
                List<String> failedRows = new ArrayList<>();

                for (Kandang kandang : kandangList) {
                        try {
                                // Validasi jenis kandang jika null
                                if (kandang.getJenisKandang() == null || kandang.getJenisKandang().isEmpty()) {
                                        kandang.setJenisKandang("Permanen");
                                }

                                // validasi nama kandang jika null
                                if (kandang.getNamaKandang() == null || kandang.getNamaKandang().isEmpty()) {
                                        kandang.setNamaKandang("Kandang " + kandang.getPeternak().getNamaPeternak());
                                }

                                String rowKey = safeString(kandang.getIdKandang());

                                // Jika peternak ada, masukkan informasi peternak
                                if (kandang.getPeternak() != null) {
                                        Peternak peternak = kandang.getPeternak();
                                        client.insertRecord(tableKandang, rowKey, "peternak", "idPeternak",
                                                        safeString(peternak.getIdPeternak()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "nikPeternak",
                                                        safeString(peternak.getNikPeternak()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "namaPeternak",
                                                        safeString(peternak.getNamaPeternak()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "email",
                                                        safeString(peternak.getEmail()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "lokasi",
                                                        safeString(peternak.getLokasi()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "noTelepon",
                                                        safeString(peternak.getNoTelepon()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "alamat",
                                                        safeString(peternak.getAlamat()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "dusun",
                                                        safeString(peternak.getDusun()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "desa",
                                                        safeString(peternak.getDesa()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "kecamatan",
                                                        safeString(peternak.getKecamatan()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "kabupaten",
                                                        safeString(peternak.getKabupaten()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "latitude",
                                                        safeString(peternak.getLatitude()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "longitude",
                                                        safeString(peternak.getLongitude()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "jenisKelamin",
                                                        safeString(peternak.getJenisKelamin()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "tanggalPendaftaran",
                                                        safeString(peternak.getTanggalPendaftaran()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "tanggalLahir",
                                                        safeString(peternak.getTanggalLahir()));
                                        client.insertRecord(tableKandang, rowKey, "peternak", "idIsikhnas",
                                                        safeString(peternak.getIdIsikhnas()));
                                        client.insertRecord(tableKandang, rowKey, "detail", "created_by", "Polinema");

                                }

                                if (kandang.getJenisHewan() != null) {
                                        client.insertRecord(tableKandang, rowKey, "jenisHewan", "idJenisHewan",
                                                        kandang.getJenisHewan().getIdJenisHewan());
                                        client.insertRecord(tableKandang, rowKey, "jenisHewan", "jenis",
                                                        kandang.getJenisHewan().getJenis());
                                        client.insertRecord(tableKandang, rowKey, "jenisHewan", "deskripsi",
                                                        kandang.getJenisHewan().getDeskripsi());
                                }

                                // Insert data kandang ke HBase
                                client.insertRecord(tableKandang, rowKey, "main", "idKandang",
                                                safeString(kandang.getIdKandang()));
                                client.insertRecord(tableKandang, rowKey, "main", "namaKandang",
                                                safeString(kandang.getNamaKandang()));
                                client.insertRecord(tableKandang, rowKey, "main", "alamat",
                                                safeString(kandang.getAlamat()));
                                client.insertRecord(tableKandang, rowKey, "main", "luas",
                                                safeString(kandang.getLuas()));
                                client.insertRecord(tableKandang, rowKey, "main", "nilaiBangunan",
                                                safeString(kandang.getNilaiBangunan()));
                                client.insertRecord(tableKandang, rowKey, "main", "jenisKandang",
                                                safeString(kandang.getJenisKandang()));
                                client.insertRecord(tableKandang, rowKey, "main", "latitude",
                                                safeString(kandang.getLatitude()));
                                client.insertRecord(tableKandang, rowKey, "main", "longitude",
                                                safeString(kandang.getLongitude()));
                                client.insertRecord(tableKandang, rowKey, "main", "kapasitas",
                                                safeString(kandang.getKapasitas()));
                                System.out.println("Berhasil menyimpan data Kandang Nama: " + kandang.getNamaKandang());

                                System.out.println("Berhasil menyimpan data Kandang ID: " + kandang.getIdKandang());
                        } catch (Exception e) {
                                failedRows.add(kandang.getIdKandang());
                                System.err
                                                .println("Gagal menyimpan Kandang ID: " + kandang.getIdKandang()
                                                                + " Error: " + e.getMessage());
                        }
                }

                if (!failedRows.isEmpty()) {
                        throw new IOException(
                                        "Failed to save records for Kandang IDs: " + String.join(", ", failedRows));
                }

                return kandangList;
        }

        public Kandang saveImportByHewan(Kandang kandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                String rowKey = kandang.getIdKandang();

                TableName tableKandang = TableName.valueOf(tableName);
                client.insertRecord(tableKandang, rowKey, "main", "idKandang",
                                kandang.getIdKandang());
                client.insertRecord(tableKandang, rowKey, "main", "namaKandang",
                                kandang.getNamaKandang());
                client.insertRecord(tableKandang, rowKey, "main", "alamat",
                                kandang.getAlamat());
                client.insertRecord(tableKandang, rowKey, "main", "luas",
                                kandang.getLuas());
                client.insertRecord(tableKandang, rowKey, "main", "kapasitas", kandang.getKapasitas());
                client.insertRecord(tableKandang, rowKey, "main", "nilaiBangunan",
                                kandang.getNilaiBangunan());
                client.insertRecord(tableKandang, rowKey, "main", "jenisKandang",
                                kandang.getJenisKandang());
                client.insertRecord(tableKandang, rowKey, "main", "latitude",
                                kandang.getLatitude());
                client.insertRecord(tableKandang, rowKey, "main", "longitude",
                                kandang.getLongitude());
                client.insertRecord(tableKandang, rowKey, "detail", "created_by", "Polinema");

                if (kandang.getPeternak() != null) {
                        Peternak peternak = kandang.getPeternak();
                        if (peternak != null && peternak.getNamaPeternak() != null) {
                                client.insertRecord(tableKandang, rowKey, "peternak", "idPeternak",
                                                safeString(peternak.getIdPeternak()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "nikPeternak",
                                                safeString(peternak.getNikPeternak()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "namaPeternak",
                                                safeString(peternak.getNamaPeternak()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "email",
                                                safeString(peternak.getEmail()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "lokasi",
                                                safeString(peternak.getLokasi()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "noTelepon",
                                                safeString(peternak.getNoTelepon()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "alamat",
                                                safeString(peternak.getAlamat()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "dusun",
                                                safeString(peternak.getDusun()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "desa",
                                                safeString(peternak.getDesa()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "kecamatan",
                                                safeString(peternak.getKecamatan()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "kabupaten",
                                                safeString(peternak.getKabupaten()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "latitude",
                                                safeString(peternak.getLatitude()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "longitude",
                                                safeString(peternak.getLongitude()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "jenisKelamin",
                                                safeString(peternak.getJenisKelamin()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "tanggalPendaftaran",
                                                safeString(peternak.getTanggalPendaftaran()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "tanggalLahir",
                                                safeString(peternak.getTanggalLahir()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "idIsikhnas",
                                                safeString(peternak.getIdIsikhnas()));
                                client.insertRecord(tableKandang, rowKey, "detail", "created_by", "Polinema");
                        }
                        if (kandang.getJenisHewan() != null) {
                                client.insertRecord(tableKandang, rowKey, "jenisHewan", "idJenisHewan",
                                                kandang.getJenisHewan().getIdJenisHewan());
                                client.insertRecord(tableKandang, rowKey, "jenisHewan", "jenis",
                                                kandang.getJenisHewan().getJenis());
                                client.insertRecord(tableKandang, rowKey, "jenisHewan", "deskripsi",
                                                kandang.getJenisHewan().getDeskripsi());
                        }
                }

                return kandang;
        }

        public Kandang saveKandangById(Kandang kandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableKandang = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKandang", "idKandang");

                String rowKey = kandang.getIdKandang();

                client.insertRecord(tableKandang, rowKey, "main", "idKandang",
                                kandang.getIdKandang());
                client.insertRecord(tableKandang, rowKey, "main", "namaKandang",
                                kandang.getNamaKandang() != null ? kandang.getNamaKandang()
                                                : "Nama kandang tidak valid");
                client.insertRecord(tableKandang, rowKey, "main", "alamat",
                                kandang.getAlamat() != null ? kandang.getAlamat() : "-");
                client.insertRecord(tableKandang, rowKey, "main", "luas",
                                kandang.getLuas() != null ? kandang.getLuas() : "-");
                client.insertRecord(tableKandang, rowKey, "main", "nilaiBangunan",
                                kandang.getNilaiBangunan() != null ? kandang.getNilaiBangunan() : "-");
                client.insertRecord(tableKandang, rowKey, "main", "jenisKandang",
                                kandang.getJenisKandang() != null ? kandang.getJenisKandang() : "-");
                client.insertRecord(tableKandang, rowKey, "main", "latitude",
                                kandang.getLatitude() != null ? kandang.getLatitude() : "-");
                client.insertRecord(tableKandang, rowKey, "main", "longitude",
                                kandang.getLongitude() != null ? kandang.getLongitude() : "-");

                if (kandang.getPeternak() != null) {
                        Peternak peternak = kandang.getPeternak();
                        if (peternak != null && peternak.getNamaPeternak() != null) {
                                client.insertRecord(tableKandang, rowKey, "peternak", "idPeternak",
                                                safeString(peternak.getIdPeternak()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "nikPeternak",
                                                safeString(peternak.getNikPeternak()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "namaPeternak",
                                                safeString(peternak.getNamaPeternak()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "email",
                                                safeString(peternak.getEmail()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "lokasi",
                                                safeString(peternak.getLokasi()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "noTelepon",
                                                safeString(peternak.getNoTelepon()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "alamat",
                                                safeString(peternak.getAlamat()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "dusun",
                                                safeString(peternak.getDusun()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "desa",
                                                safeString(peternak.getDesa()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "kecamatan",
                                                safeString(peternak.getKecamatan()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "kabupaten",
                                                safeString(peternak.getKabupaten()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "provinsi",
                                                safeString(peternak.getProvinsi()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "latitude",
                                                safeString(peternak.getLatitude()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "longitude",
                                                safeString(peternak.getLongitude()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "jenisKelamin",
                                                safeString(peternak.getJenisKelamin()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "tanggalPendaftaran",
                                                safeString(peternak.getTanggalPendaftaran()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "tanggalLahir",
                                                safeString(peternak.getTanggalLahir()));
                                client.insertRecord(tableKandang, rowKey, "peternak", "idIsikhnas",
                                                safeString(peternak.getIdIsikhnas()));
                        }

                        if (kandang.getJenisHewan() != null) {
                                client.insertRecord(tableKandang, rowKey, "jenisHewan", "idJenisHewan",
                                                kandang.getJenisHewan().getIdJenisHewan());
                                client.insertRecord(tableKandang, rowKey, "jenisHewan", "jenis",
                                                kandang.getJenisHewan().getJenis());
                                client.insertRecord(tableKandang, rowKey, "jenisHewan", "deskripsi",
                                                kandang.getJenisHewan().getDeskripsi());
                        }
                }

                client.insertRecord(tableKandang, rowKey, "detail", "created_by", "Polinema");

                return kandang;
        }

        private String safeString(String value) {
                return value != null ? value : "";
        }

        public Kandang findById(String idKandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableKandang = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idKandang", "idKandang");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("luas", "luas");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("kapasitas", "kapasitas");
                columnMapping.put("jenisKandang", "jenisKandang");
                columnMapping.put("nilaiBangunan", "nilaiBangunan");
                columnMapping.put("alamat", "alamat");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");
                columnMapping.put("namaKandang", "namaKandang");

                Kandang kandang = client.getDataByColumn(tableKandang.toString(), columnMapping, "main", "idKandang",
                                idKandang, Kandang.class);
                System.out.println("data kandang ditemukan by id kandang" + kandang);
                return kandang.getIdKandang() != null ? kandang : null;
        }

        public Kandang findByIdKandang(String idKandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKandang = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKandang", "idKandang");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("luas", "luas");
                columnMapping.put("kapasitas", "kapasitas");
                columnMapping.put("jenisKandang", "jenisKandang");
                columnMapping.put("nilaiBangunan", "nilaiBangunan");
                columnMapping.put("alamat", "alamat");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");
                columnMapping.put("namaKandang", "namaKandang");

                Kandang kandang = client.getDataByColumn(tableKandang.toString(), columnMapping, "main", "idKandang",
                                idKandang, Kandang.class);

                System.out.println("data kandang ditemukan by id kandang" + kandang);

                return kandang.getIdKandang() != null ? kandang : null;
        }

        public Kandang findByFotoKandang(String idKandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKandang = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKandang", "idKandang");
                columnMapping.put("file_path", "file_path");
                Kandang kandang = client.getDataByColumn(tableKandang.toString(), columnMapping, "main", "idKandang",
                                idKandang, Kandang.class);

                System.out.println("data kandang ditemukan by id kandang" + kandang);

                return kandang.getIdKandang() != null ? kandang : null;
        }

        public Kandang findByNamaKandang(String namaKandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKandang = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                columnMapping.put("idKandang", "idKandang");
                columnMapping.put("namaKandang", "namaKandang");
                Kandang kandang = client.getDataByColumn(tableKandang.toString(), columnMapping, "main", "namaKandang",
                                namaKandang, Kandang.class);
                return kandang.getNamaKandang() != null ? kandang : null;
        }

        public List<Kandang> findKandangByPeternak(String peternakID, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableUsers = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idKandang", "idKandang");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("luas", "luas");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("kapasitas", "kapasitas");
                columnMapping.put("jenisKandang", "jenisKandang");
                columnMapping.put("nilaiBangunan", "nilaiBangunan");
                columnMapping.put("alamat", "alamat");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");
                columnMapping.put("namaKandang", "namaKandang");
                List<Kandang> kandang = client.getDataListByColumn(tableUsers.toString(), columnMapping, "peternak",
                                "nikPeternak", peternakID, Kandang.class, size);

                return kandang;
        }

        public List<Kandang> findByPeternakId(String idPeternak) throws IOException {

                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKandang = TableName.valueOf(tableName); // Sesuaikan nama tabel HBase Anda
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKandang", "idKandang");
                columnMapping.put("idPeternak", "idPeternak");
                columnMapping.put("peternak", "peternak");

                return client.getDataListByColumn(tableKandang.toString(), columnMapping,
                                "peternak", "idPeternak", idPeternak, Kandang.class, -1);
        }

        public List<Kandang> findByJenisHewanId(String idJenisHewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKandang = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKandang", "idKandang");
                columnMapping.put("jenisHewan", "jenisHewan");

                return client.getDataListByColumn(tableKandang.toString(), columnMapping,
                                "jenisHewan", "idJenisHewan", idJenisHewan, Kandang.class, -1);
        }

        public List<Kandang> findAllById(List<String> kandangIds) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idKandang", "idKandang");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("luas", "luas");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("kapasitas", "kapasitas");
                columnMapping.put("jenisKandang", "jenisKandang");
                columnMapping.put("nilaiBangunan", "nilaiBangunan");
                columnMapping.put("alamat", "alamat");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");
                columnMapping.put("namaKandang", "namaKandang");

                List<Kandang> kandangs = new ArrayList<>();
                for (String kandangId : kandangIds) {
                        Kandang kandang = client.showDataTable(table.toString(), columnMapping, kandangId,
                                        Kandang.class);
                        if (kandang != null) {
                                kandangs.add(kandang);
                        }
                }

                return kandangs;
        }

        public Kandang update(String kandangId, Kandang kandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableKandang = TableName.valueOf(tableName);
                client.insertRecord(tableKandang, kandangId, "main", "luas", kandang.getLuas());
                client.insertRecord(tableKandang, kandangId, "main", "kapasitas", kandang.getKapasitas());
                client.insertRecord(tableKandang, kandangId, "main", "jenisKandang", kandang.getJenisKandang());
                client.insertRecord(tableKandang, kandangId, "main", "nilaiBangunan", kandang.getNilaiBangunan());
                client.insertRecord(tableKandang, kandangId, "main", "alamat", kandang.getAlamat());
                client.insertRecord(tableKandang, kandangId, "main", "latitude", kandang.getLatitude());
                client.insertRecord(tableKandang, kandangId, "main", "longitude", kandang.getLongitude());
                System.out.println("File yang diterima " + kandang.getFile_path());
                client.insertRecord(tableKandang, kandangId, "main", "file_path", kandang.getFile_path());
                client.insertRecord(tableKandang, kandangId, "main", "namaKandang", kandang.getNamaKandang());
                client.insertRecord(tableKandang, kandangId, "peternak", "nikPeternak",
                                kandang.getPeternak().getNikPeternak());
                client.insertRecord(tableKandang, kandangId, "peternak", "namaPeternak",
                                kandang.getPeternak().getNamaPeternak());
                client.insertRecord(tableKandang, kandangId, "peternak", "lokasi", kandang.getPeternak().getLokasi());
                client.insertRecord(tableKandang, kandangId, "peternak", "tanggalPendaftaran",
                                kandang.getPeternak().getTanggalPendaftaran());

                client.insertRecord(tableKandang, kandangId, "peternak", "noTelepon",
                                kandang.getPeternak().getNoTelepon());
                client.insertRecord(tableKandang, kandangId, "peternak", "email", kandang.getPeternak().getEmail());
                client.insertRecord(tableKandang, kandangId, "peternak", "jenisKelamin",
                                kandang.getPeternak().getJenisKelamin());
                client.insertRecord(tableKandang, kandangId, "peternak", "tanggalLahir",
                                kandang.getPeternak().getTanggalLahir());
                client.insertRecord(tableKandang, kandangId, "peternak", "idIsikhnas",
                                kandang.getPeternak().getIdIsikhnas());

                client.insertRecord(tableKandang, kandangId, "peternak", "dusun", kandang.getPeternak().getDusun());
                client.insertRecord(tableKandang, kandangId, "peternak", "desa", kandang.getPeternak().getDesa());
                client.insertRecord(tableKandang, kandangId, "peternak", "kecamatan",
                                kandang.getPeternak().getKecamatan());
                client.insertRecord(tableKandang, kandangId, "peternak", "kabupaten",
                                kandang.getPeternak().getKabupaten());
                client.insertRecord(tableKandang, kandangId, "peternak", "provinsi",
                                kandang.getPeternak().getProvinsi());
                client.insertRecord(tableKandang, kandangId, "peternak", "alamat", kandang.getPeternak().getAlamat());
                client.insertRecord(tableKandang, kandangId, "peternak", "latitude",
                                kandang.getPeternak().getLatitude());
                client.insertRecord(tableKandang, kandangId, "peternak", "longitude",
                                kandang.getPeternak().getLongitude());

                client.insertRecord(tableKandang, kandangId, "jenisHewan", "idJenisHewan",
                                kandang.getJenisHewan().getIdJenisHewan());
                client.insertRecord(tableKandang, kandangId, "jenisHewan", "jenis", kandang.getJenisHewan().getJenis());
                client.insertRecord(tableKandang, kandangId, "jenisHewan", "deskripsi",
                                kandang.getJenisHewan().getDeskripsi());
                client.insertRecord(tableKandang, kandangId, "detail", "created_by", "Polinema");
                return kandang;
        }

        public Kandang updatePeternakByKandang(String kandangId, Kandang kandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableKandang = TableName.valueOf(tableName);

                client.insertRecord(tableKandang, kandangId, "main", "namaKandang",
                                "Kandang " + kandang.getPeternak().getNamaPeternak());
                client.insertRecord(tableKandang, kandangId, "peternak", "nikPeternak",
                                kandang.getPeternak().getNikPeternak());
                client.insertRecord(tableKandang, kandangId, "peternak", "namaPeternak",
                                kandang.getPeternak().getNamaPeternak());
                client.insertRecord(tableKandang, kandangId, "peternak", "lokasi", kandang.getPeternak().getLokasi());
                client.insertRecord(tableKandang, kandangId, "peternak", "tanggalPendaftaran",
                                kandang.getPeternak().getTanggalPendaftaran());

                client.insertRecord(tableKandang, kandangId, "peternak", "noTelepon",
                                kandang.getPeternak().getNoTelepon());
                client.insertRecord(tableKandang, kandangId, "peternak", "email", kandang.getPeternak().getEmail());
                client.insertRecord(tableKandang, kandangId, "peternak", "jenisKelamin",
                                kandang.getPeternak().getJenisKelamin());
                client.insertRecord(tableKandang, kandangId, "peternak", "tanggalLahir",
                                kandang.getPeternak().getTanggalLahir());
                client.insertRecord(tableKandang, kandangId, "peternak", "idIsikhnas",
                                kandang.getPeternak().getIdIsikhnas());

                client.insertRecord(tableKandang, kandangId, "peternak", "dusun", kandang.getPeternak().getDusun());
                client.insertRecord(tableKandang, kandangId, "peternak", "desa", kandang.getPeternak().getDesa());
                client.insertRecord(tableKandang, kandangId, "peternak", "kecamatan",
                                kandang.getPeternak().getKecamatan());
                client.insertRecord(tableKandang, kandangId, "peternak", "kabupaten",
                                kandang.getPeternak().getKabupaten());
                client.insertRecord(tableKandang, kandangId, "peternak", "provinsi",
                                kandang.getPeternak().getProvinsi());
                client.insertRecord(tableKandang, kandangId, "peternak", "alamat", kandang.getPeternak().getAlamat());
                client.insertRecord(tableKandang, kandangId, "peternak", "latitude",
                                kandang.getPeternak().getLatitude());
                client.insertRecord(tableKandang, kandangId, "peternak", "longitude",
                                kandang.getPeternak().getLongitude());
                client.insertRecord(tableKandang, kandangId, "detail", "created_by", "Polinema");
                return kandang;
        }

        public Kandang updateJenisHewanByKandang(String idHewan, Kandang kandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKandang = TableName.valueOf(tableName);
                client.insertRecord(tableKandang, idHewan, "jenisHewan", "jenis", kandang.getJenisHewan().getJenis());
                client.insertRecord(tableKandang, idHewan, "jenisHewan", "deskripsi",
                                kandang.getJenisHewan().getDeskripsi());
                return kandang;
        }

        public boolean deleteById(String kandangId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                client.deleteRecord(tableName, kandangId);
                return true;
        }

        public boolean existsByIdKandang(String idKandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKandang = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKandang", "idKandang");

                Kandang kandang = client.getDataByColumn(tableKandang.toString(), columnMapping, "main", "idKandang",
                                idKandang,
                                Kandang.class);
                return kandang.getIdKandang() != null; // True jika ID Kandang sudah ada
        }

        public boolean existsByAlamat(String alamat) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKandang = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("alamat", "alamat");

                Kandang kandang = client.getDataByColumn(tableKandang.toString(), columnMapping, "main", "alamat",
                                alamat,
                                Kandang.class);
                return kandang.getAlamat() != null; // True jika alamat sudah ada
        }
}

package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Vaksin;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.model.NamaVaksin;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Inseminasi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.*;

public class VaksinRepository {
        Configuration conf = HBaseConfiguration.create();
        String tableName = "vaksindev";

        public List<Vaksin> findAll(int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableVaksin = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("namaVaksin", "namaVaksin");
                columnMapping.put("jenisVaksin", "jenisVaksin");
                columnMapping.put("tglVaksin", "tglVaksin");
                columnMapping.put("batchVaksin", "batchVaksin");
                columnMapping.put("vaksinKe", "vaksinKe");

                return client.showListTable(tableVaksin.toString(), columnMapping, Vaksin.class, size);
        }

        public Vaksin save(Vaksin vaksin) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                String rowKey = vaksin.getIdVaksin();

                TableName tableVaksin = TableName.valueOf(tableName);
                client.insertRecord(tableVaksin, rowKey, "main", "idVaksin", rowKey);
                if (vaksin.getTglVaksin() != null) {
                        client.insertRecord(tableVaksin, rowKey, "main", "tglVaksin", vaksin.getTglVaksin());
                }
                if (vaksin.getBatchVaksin() != null) {
                        client.insertRecord(tableVaksin, rowKey, "main", "batchVaksin", vaksin.getBatchVaksin());
                }
                if (vaksin.getVaksinKe() != null) {
                        client.insertRecord(tableVaksin, rowKey, "main", "vaksinKe", vaksin.getVaksinKe());
                }

                // Peternak
                if (vaksin.getPeternak() != null) {
                        client.insertRecord(tableVaksin, rowKey, "peternak", "idPeternak",
                                        vaksin.getPeternak().getIdPeternak());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "nikPeternak",
                                        vaksin.getPeternak().getNikPeternak());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "namaPeternak",
                                        vaksin.getPeternak().getNamaPeternak());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "email",
                                        vaksin.getPeternak().getEmail());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "lokasi",
                                        vaksin.getPeternak().getLokasi());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "noTelepon",
                                        vaksin.getPeternak().getNoTelepon());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "alamat",
                                        vaksin.getPeternak().getAlamat());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "dusun",
                                        vaksin.getPeternak().getDusun());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "desa",
                                        vaksin.getPeternak().getDesa());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "kecamatan",
                                        vaksin.getPeternak().getKecamatan());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "kabupaten",
                                        vaksin.getPeternak().getKabupaten());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "latitude",
                                        vaksin.getPeternak().getLatitude());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "longitude",
                                        vaksin.getPeternak().getLongitude());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "jenisKelamin",
                                        vaksin.getPeternak().getJenisKelamin());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "tanggalPendaftaran",
                                        vaksin.getPeternak().getTanggalPendaftaran());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "tanggalLahir",
                                        vaksin.getPeternak().getTanggalLahir());
                        client.insertRecord(tableVaksin, rowKey, "peternak", "idIsikhnas",
                                        vaksin.getPeternak().getIdIsikhnas());
                }

                // Petugas
                if (vaksin.getPetugas() != null) {
                        client.insertRecord(tableVaksin, rowKey, "petugas", "petugasId",
                                        vaksin.getPetugas().getPetugasId());
                        client.insertRecord(tableVaksin, rowKey, "petugas", "nikPetugas",
                                        vaksin.getPetugas().getNikPetugas());
                        client.insertRecord(tableVaksin, rowKey, "petugas", "namaPetugas",
                                        vaksin.getPetugas().getNamaPetugas());
                        client.insertRecord(tableVaksin, rowKey, "petugas", "noTelp",
                                        vaksin.getPetugas().getNoTelp());
                        client.insertRecord(tableVaksin, rowKey, "petugas", "email",
                                        vaksin.getPetugas().getEmail());
                        client.insertRecord(tableVaksin, rowKey, "petugas", "job",
                                        vaksin.getPetugas().getJob());
                        client.insertRecord(tableVaksin, rowKey, "petugas", "wilayah",
                                        vaksin.getPetugas().getWilayah());
                }

                // Hewan
                if (vaksin.getHewan() != null) {
                        client.insertRecord(tableVaksin, rowKey, "hewan", "idHewan", vaksin.getHewan().getIdHewan());
                        client.insertRecord(tableVaksin, rowKey, "hewan", "kodeEartagNasional",
                                        vaksin.getHewan().getKodeEartagNasional());
                        client.insertRecord(tableVaksin, rowKey, "hewan", "noKartuTernak",
                                        vaksin.getHewan().getNoKartuTernak());
                        client.insertRecord(tableVaksin, rowKey, "hewan", "idIsikhnasTernak",
                                        vaksin.getHewan().getIdIsikhnasTernak());
                        client.insertRecord(tableVaksin, rowKey, "hewan", "sex", vaksin.getHewan().getSex());
                        client.insertRecord(tableVaksin, rowKey, "hewan", "umur", vaksin.getHewan().getUmur());
                        client.insertRecord(tableVaksin, rowKey, "hewan", "identifikasiHewan",
                                        vaksin.getHewan().getIdentifikasiHewan());
                        client.insertRecord(tableVaksin, rowKey, "hewan", "tanggalLahir",
                                        vaksin.getHewan().getTanggalLahir());
                        client.insertRecord(tableVaksin, rowKey, "hewan", "tempatLahir",
                                        vaksin.getHewan().getTempatLahir());
                        client.insertRecord(tableVaksin, rowKey, "hewan", "tanggalTerdaftar",
                                        vaksin.getHewan().getTanggalTerdaftar());
                }

                // Jenis Vaksin
                if (vaksin.getJenisVaksin() != null) {
                        client.insertRecord(tableVaksin, rowKey, "jenisVaksin", "idJenisVaksin",
                                        vaksin.getJenisVaksin().getIdJenisVaksin());
                        client.insertRecord(tableVaksin, rowKey, "jenisVaksin", "jenis",
                                        vaksin.getJenisVaksin().getJenis());
                        client.insertRecord(tableVaksin, rowKey, "jenisVaksin", "deskripsi",
                                        vaksin.getJenisVaksin().getDeskripsi());
                }

                // Nama Vaksin
                if (vaksin.getNamaVaksin() != null) {
                        client.insertRecord(tableVaksin, rowKey, "namaVaksin", "idNamaVaksin",
                                        vaksin.getNamaVaksin().getIdNamaVaksin());
                        client.insertRecord(tableVaksin, rowKey, "namaVaksin", "nama",
                                        vaksin.getNamaVaksin().getNama());
                        client.insertRecord(tableVaksin, rowKey, "namaVaksin", "deskripsi",
                                        vaksin.getNamaVaksin().getDeskripsi());
                }
                client.insertRecord(tableVaksin, rowKey, "detail", "created_by", "Polinema");
                return vaksin;
        }

        public Vaksin findVaksinById(String vaksinId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableVaksin = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("namaVaksin", "namaVaksin");
                columnMapping.put("jenisVaksin", "jenisVaksin");
                columnMapping.put("tglVaksin", "tglVaksin");
                columnMapping.put("batchVaksin", "batchVaksin");
                columnMapping.put("vaksinKe", "vaksinKe");

                return client.showDataTable(tableVaksin.toString(), columnMapping, vaksinId, Vaksin.class);
        }

        public List<Vaksin> findVaksinByJenisVaksin(String jenisVaksinId, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableVaksin = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("namaVaksin", "namaVaksin");
                columnMapping.put("jenisVaksin", "jenisVaksin");
                columnMapping.put("tglVaksin", "tglVaksin");
                columnMapping.put("batchVaksin", "batchVaksin");
                columnMapping.put("vaksinKe", "vaksinKe");

                List<Vaksin> vaksin = client.getDataListByColumn(tableVaksin.toString(), columnMapping, "jenisVaksin",
                                "idJenisVaksin", jenisVaksinId, Vaksin.class, size);
                return vaksin;
        }

        public List<Vaksin> findVaksinByPeternak(String peternakId, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableVaksin = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("namaVaksin", "namaVaksin");
                columnMapping.put("jenisVaksin", "jenisVaksin");
                columnMapping.put("tglVaksin", "tglVaksin");
                columnMapping.put("batchVaksin", "batchVaksin");
                columnMapping.put("vaksinKe", "vaksinKe");

                List<Vaksin> vaksin = client.getDataListByColumn(tableVaksin.toString(), columnMapping, "peternak",
                                "nikPeternak", peternakId, Vaksin.class, size);

                return vaksin;
        }

        public List<Vaksin> findVaksinByPetugas(String petugasId, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("namaVaksin", "namaVaksin");
                columnMapping.put("jenisVaksin", "jenisVaksin");
                columnMapping.put("tglVaksin", "tglVaksin");
                columnMapping.put("batchVaksin", "batchVaksin");
                columnMapping.put("vaksinKe", "vaksinKe");

                return client.getDataListByColumn(table.toString(), columnMapping, "petugas", "nikPetugas", petugasId,
                                Vaksin.class, size);
        }

        public List<Vaksin> findVaksinByHewan(String hewanId, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("namaVaksin", "namaVaksin");
                columnMapping.put("jenisVaksin", "jenisVaksin");
                columnMapping.put("tglVaksin", "tglVaksin");
                columnMapping.put("batchVaksin", "batchVaksin");
                columnMapping.put("vaksinKe", "vaksinKe");

                return client.getDataListByColumn(table.toString(), columnMapping, "hewan", "kodeEartagNasional",
                                hewanId,
                                Vaksin.class, size);
        }

        public List<Vaksin> findAllById(List<String> vaksinIds) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("namaVaksin", "namaVaksin");
                columnMapping.put("jenisVaksin", "jenisVaksin");
                columnMapping.put("tglVaksin", "tglVaksin");
                columnMapping.put("batchVaksin", "batchVaksin");
                columnMapping.put("vaksinKe", "vaksinKe");

                List<Vaksin> vaksins = new ArrayList<>();
                for (String vaksinId : vaksinIds) {
                        Vaksin vaksin = client.showDataTable(table.toString(), columnMapping, vaksinId, Vaksin.class);
                        if (vaksin != null) {
                                vaksins.add(vaksin);
                        }
                }

                return vaksins;
        }

        public Vaksin update(String vaksinId, Vaksin vaksin) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableVaksin = TableName.valueOf(tableName);
                if (vaksin.getTglVaksin() != null) {
                        client.insertRecord(tableVaksin, vaksinId, "main", "tglVaksin", vaksin.getTglVaksin());
                }
                if (vaksin.getBatchVaksin() != null) {
                        client.insertRecord(tableVaksin, vaksinId, "main", "batchVaksin", vaksin.getBatchVaksin());
                }
                if (vaksin.getVaksinKe() != null) {
                        client.insertRecord(tableVaksin, vaksinId, "main", "vaksinKe", vaksin.getVaksinKe());
                }

                // Peternak
                if (vaksin.getPeternak() != null) {
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "idPeternak",
                                        vaksin.getPeternak().getIdPeternak());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "nikPeternak",
                                        vaksin.getPeternak().getNikPeternak());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "namaPeternak",
                                        vaksin.getPeternak().getNamaPeternak());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "lokasi",
                                        vaksin.getPeternak().getLokasi());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "tanggalPendaftaran",
                                        vaksin.getPeternak().getTanggalPendaftaran());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "noTelepon",
                                        vaksin.getPeternak().getNoTelepon());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "email",
                                        vaksin.getPeternak().getEmail());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "jenisKelamin",
                                        vaksin.getPeternak().getJenisKelamin());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "tanggalLahir",
                                        vaksin.getPeternak().getTanggalLahir());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "idIsikhnas",
                                        vaksin.getPeternak().getIdIsikhnas());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "dusun",
                                        vaksin.getPeternak().getDusun());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "desa",
                                        vaksin.getPeternak().getDesa());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "kecamatan",
                                        vaksin.getPeternak().getKecamatan());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "kabupaten",
                                        vaksin.getPeternak().getKabupaten());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "provinsi",
                                        vaksin.getPeternak().getProvinsi());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "alamat",
                                        vaksin.getPeternak().getAlamat());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "latitude",
                                        vaksin.getPeternak().getLatitude());
                        client.insertRecord(tableVaksin, vaksinId, "peternak", "longitude",
                                        vaksin.getPeternak().getLongitude());
                }

                // Petugas
                if (vaksin.getPetugas() != null) {
                        client.insertRecord(tableVaksin, vaksinId, "petugas", "petugasId",
                                        vaksin.getPetugas().getPetugasId());
                        client.insertRecord(tableVaksin, vaksinId, "petugas", "nikPetugas",
                                        vaksin.getPetugas().getNikPetugas());
                        client.insertRecord(tableVaksin, vaksinId, "petugas", "namaPetugas",
                                        vaksin.getPetugas().getNamaPetugas());
                        client.insertRecord(tableVaksin, vaksinId, "petugas", "noTelp",
                                        vaksin.getPetugas().getNoTelp());
                        client.insertRecord(tableVaksin, vaksinId, "petugas", "email",
                                        vaksin.getPetugas().getEmail());
                        client.insertRecord(tableVaksin, vaksinId, "main", "job", vaksin.getPetugas().getJob());
                        client.insertRecord(tableVaksin, vaksinId, "main", "wilayah",
                                        vaksin.getPetugas().getWilayah());
                }

                // Hewan
                if (vaksin.getHewan() != null) {
                        client.insertRecord(tableVaksin, vaksinId, "hewan", "idHewan", vaksin.getHewan().getIdHewan());
                        client.insertRecord(tableVaksin, vaksinId, "hewan", "kodeEartagNasional",
                                        vaksin.getHewan().getKodeEartagNasional());
                        client.insertRecord(tableVaksin, vaksinId, "hewan", "noKartuTernak",
                                        vaksin.getHewan().getNoKartuTernak());
                        client.insertRecord(tableVaksin, vaksinId, "hewan", "idIsikhnasTernak",
                                        vaksin.getHewan().getIdIsikhnasTernak());
                        client.insertRecord(tableVaksin, vaksinId, "hewan", "sex", vaksin.getHewan().getSex());
                        client.insertRecord(tableVaksin, vaksinId, "hewan", "umur", vaksin.getHewan().getUmur());
                        client.insertRecord(tableVaksin, vaksinId, "hewan", "identifikasiHewan",
                                        vaksin.getHewan().getIdentifikasiHewan());
                        client.insertRecord(tableVaksin, vaksinId, "hewan", "tanggalLahir",
                                        vaksin.getHewan().getTanggalLahir());
                        client.insertRecord(tableVaksin, vaksinId, "hewan", "tempatLahir",
                                        vaksin.getHewan().getTempatLahir());
                        client.insertRecord(tableVaksin, vaksinId, "hewan", "tanggalTerdaftar",
                                        vaksin.getHewan().getTanggalTerdaftar());
                }

                // Jenis Vaksin
                if (vaksin.getJenisVaksin() != null) {
                        client.insertRecord(tableVaksin, vaksinId, "jenisVaksin", "idJenisVaksin",
                                        vaksin.getJenisVaksin().getIdJenisVaksin());
                        client.insertRecord(tableVaksin, vaksinId, "jenisVaksin", "jenis",
                                        vaksin.getJenisVaksin().getJenis());
                        client.insertRecord(tableVaksin, vaksinId, "jenisVaksin", "deskripsi",
                                        vaksin.getJenisVaksin().getDeskripsi());
                }

                // Nama Vaksin
                if (vaksin.getNamaVaksin() != null) {
                        client.insertRecord(tableVaksin, vaksinId, "namaVaksin", "idNamaVaksin",
                                        vaksin.getNamaVaksin().getIdNamaVaksin());
                        client.insertRecord(tableVaksin, vaksinId, "namaVaksin", "nama",
                                        vaksin.getNamaVaksin().getNama());
                        client.insertRecord(tableVaksin, vaksinId, "namaVaksin", "deskripsi",
                                        vaksin.getNamaVaksin().getDeskripsi());
                }

                client.insertRecord(tableVaksin, vaksinId, "detail", "created_by", "Polinema");
                return vaksin;
        }

        public Vaksin updateJenisVaksin(String idVaksin, Vaksin vaksin) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tbl = TableName.valueOf(tableName);

                client.insertRecord(tbl, idVaksin, "jenisVaksin", "jenis", vaksin.getJenisVaksin().getJenis());
                client.insertRecord(tbl, idVaksin, "jenisVaksin", "deskripsi", vaksin.getJenisVaksin().getDeskripsi());

                return vaksin;
        }

        public Vaksin updateNamaVaksin(String idVaksin, Vaksin vaksin) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tbl = TableName.valueOf(tableName);

                client.insertRecord(tbl, idVaksin, "namaVaksin", "nama", vaksin.getNamaVaksin().getNama());
                client.insertRecord(tbl, idVaksin, "namaVaksin", "deskripsi", vaksin.getNamaVaksin().getDeskripsi());
                client.insertRecord(tbl, idVaksin, "jenisVaksin", "idJenisVaksin",
                                vaksin.getNamaVaksin().getJenisVaksin().getIdJenisVaksin());
                client.insertRecord(tbl, idVaksin, "jenisVaksin", "jenis",
                                vaksin.getNamaVaksin().getJenisVaksin().getJenis());
                client.insertRecord(tbl, idVaksin, "jenisVaksin", "deskripsi",
                                vaksin.getNamaVaksin().getJenisVaksin().getDeskripsi());

                return vaksin;
        }

        public Vaksin updatePetugasByVaksin(String vaksinId, Vaksin vaksin) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableVaksin = TableName.valueOf(tableName);

                client.insertRecord(tableVaksin, vaksinId, "petugas", "nikPetugas",
                                vaksin.getPetugas().getNikPetugas());
                client.insertRecord(tableVaksin, vaksinId, "petugas", "namaPetugas",
                                vaksin.getPetugas().getNamaPetugas());
                client.insertRecord(tableVaksin, vaksinId, "petugas", "noTelp",
                                vaksin.getPetugas().getNoTelp());
                client.insertRecord(tableVaksin, vaksinId, "petugas", "email",
                                vaksin.getPetugas().getEmail());
                client.insertRecord(tableVaksin, vaksinId, "main", "job", vaksin.getPetugas().getJob());
                client.insertRecord(tableVaksin, vaksinId, "main", "wilayah",
                                vaksin.getPetugas().getWilayah());
                client.insertRecord(tableVaksin, vaksinId, "detail", "created_by", "Polinema");
                return vaksin;
        }

        public Vaksin updatePeternakByVaksin(String vaksinId, Vaksin vaksin) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableVaksin = TableName.valueOf(tableName);

                client.insertRecord(tableVaksin, vaksinId, "kandang", "namaKandang",
                                "Kandang " + vaksin.getPeternak().getNamaPeternak());

                client.insertRecord(tableVaksin, vaksinId, "peternak", "nikPeternak",
                                vaksin.getPeternak().getNikPeternak());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "namaPeternak",
                                vaksin.getPeternak().getNamaPeternak());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "lokasi", vaksin.getPeternak().getLokasi());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "tanggalPendaftaran",
                                vaksin.getPeternak().getTanggalPendaftaran());

                client.insertRecord(tableVaksin, vaksinId, "peternak", "noTelepon",
                                vaksin.getPeternak().getNoTelepon());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "email", vaksin.getPeternak().getEmail());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "jenisKelamin",
                                vaksin.getPeternak().getJenisKelamin());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "tanggalLahir",
                                vaksin.getPeternak().getTanggalLahir());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "idIsikhnas",
                                vaksin.getPeternak().getIdIsikhnas());

                client.insertRecord(tableVaksin, vaksinId, "peternak", "dusun", vaksin.getPeternak().getDusun());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "desa", vaksin.getPeternak().getDesa());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "kecamatan",
                                vaksin.getPeternak().getKecamatan());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "kabupaten",
                                vaksin.getPeternak().getKabupaten());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "provinsi",
                                vaksin.getPeternak().getProvinsi());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "alamat", vaksin.getPeternak().getAlamat());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "latitude",
                                vaksin.getPeternak().getLatitude());
                client.insertRecord(tableVaksin, vaksinId, "peternak", "longitude",
                                vaksin.getPeternak().getLongitude());
                return vaksin;
        }

        public Vaksin updateHewanByVaksin(String vaksinId, Vaksin vaksin) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableVaksin = TableName.valueOf(tableName);
                client.insertRecord(tableVaksin, vaksinId, "hewan", "kodeEartagNasional",
                                vaksin.getHewan().getKodeEartagNasional());
                client.insertRecord(tableVaksin, vaksinId, "hewan", "noKartuTernak",
                                vaksin.getHewan().getNoKartuTernak());
                client.insertRecord(tableVaksin, vaksinId, "hewan", "idIsikhnasTernak",
                                vaksin.getHewan().getIdIsikhnasTernak());
                client.insertRecord(tableVaksin, vaksinId, "hewan", "sex", vaksin.getHewan().getSex());
                client.insertRecord(tableVaksin, vaksinId, "hewan", "umur", vaksin.getHewan().getUmur());
                client.insertRecord(tableVaksin, vaksinId, "hewan", "identifikasiHewan",
                                vaksin.getHewan().getIdentifikasiHewan());
                client.insertRecord(tableVaksin, vaksinId, "hewan", "tanggalLahir",
                                vaksin.getHewan().getTanggalLahir());
                client.insertRecord(tableVaksin, vaksinId, "hewan", "tempatLahir",
                                vaksin.getHewan().getTempatLahir());
                client.insertRecord(tableVaksin, vaksinId, "hewan", "tanggalTerdaftar",
                                vaksin.getHewan().getTanggalTerdaftar());
                return vaksin;
        }

        public List<Vaksin> findByPetugasId(String petugasId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableVaksin = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("petugasId", "petugasId");
                columnMapping.put("petugas", "petugas");

                return client.getDataListByColumn(tableVaksin.toString(), columnMapping,
                                "petugas", "petugasId", petugasId, Vaksin.class, -1);
        }

        public List<Vaksin> findByPeternakId(String peternakId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableVaksin = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("peternakId", "peternakId");
                columnMapping.put("peternak", "peternak");

                return client.getDataListByColumn(tableVaksin.toString(), columnMapping,
                                "peternak", "peternakId", peternakId, Vaksin.class, -1);
        }

        public List<Vaksin> findByHewanId(String idHewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableVaksin = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("hewan", "hewan");

                return client.getDataListByColumn(tableVaksin.toString(), columnMapping,
                                "hewan", "idHewan", idHewan, Vaksin.class, -1);
        }

        public boolean deleteById(String vaksinId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                client.deleteRecord(tableName, vaksinId);
                return true;
        }

        public boolean existsByIdVaksin(String idVaksin) throws IOException {
                // Logika untuk memeriksa apakah ID vaksin sudah ada
                // Menggunakan HBaseCustomClient untuk validasi pada HBase
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableVaksin = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idVaksin", "idVaksin");

                Vaksin vaksin = client.getDataByColumn(tableVaksin.toString(), columnMapping, "main", "idVaksin",
                                idVaksin,
                                Vaksin.class);
                return vaksin.getIdVaksin() != null; // True jika vaksin ID sudah ada
        }

        public boolean existsByHewanId(String hewanId) throws IOException {
                // Logika untuk memeriksa apakah hewan sudah divaksin atau memiliki vaksin
                // dengan ID yang sama
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableVaksin = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("hewan_id", "hewan_id");

                Vaksin vaksin = client.getDataByColumn(tableVaksin.toString(), columnMapping, "main", "hewan_id",
                                hewanId,
                                Vaksin.class);
                return vaksin != null; // True jika hewan sudah memiliki vaksin yang terdaftar
        }

        // public boolean existsByHewanIdAndNamaVaksinAndVaksinKe(String hewanId, String
        // namaVaksin, String vaksinKe)
        // throws IOException {
        // // Logika untuk memeriksa apakah hewan dengan hewan_id tertentu sudah
        // divaksin
        // // dengan nama vaksin dan vaksin ke yang sama
        // HBaseCustomClient client = new HBaseCustomClient(conf);
        // TableName tableVaksin = TableName.valueOf(tableName);
        // Map<String, String> columnMapping = new HashMap<>();
        // columnMapping.put("hewan_id", "hewan_id");
        // columnMapping.put("namaVaksin", "namaVaksin");
        // columnMapping.put("vaksinKe", "vaksinKe");
        //
        // // Mencari vaksin dengan kondisi yang sudah disebutkan
        // Vaksin vaksin = client.getDataByColumn(tableVaksin.toString(), columnMapping,
        // "main", "hewan_id", hewanId,
        // Vaksin.class);
        //
        // // Cek apakah vaksin dengan kombinasi tersebut sudah ada
        // return vaksin != null && vaksin.getNamaVaksin().equals(namaVaksin) &&
        // vaksin.getVaksinKe().equals(vaksinKe);
        // }

        private String safeString(String input) {
                return input == null ? "" : input;
        }

        public List<Vaksin> saveAll(List<Vaksin> vaksinList) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableVaksin = TableName.valueOf(tableName);

                System.out.println("Memulai penyimpanan data ke HBase...");
                List<String> failedRows = new ArrayList<>();

                for (Vaksin vaksin : vaksinList) {
                        try {
                                String rowKey = safeString(vaksin.getIdVaksin());
                                client.insertRecord(tableVaksin, rowKey, "main", "idVaksin",
                                                safeString(vaksin.getIdVaksin()));
                                client.insertRecord(tableVaksin, rowKey, "main", "tglVaksin",
                                                safeString(vaksin.getTglVaksin()));
                                client.insertRecord(tableVaksin, rowKey, "main", "batchVaksin",
                                                safeString(vaksin.getBatchVaksin()));
                                client.insertRecord(tableVaksin, rowKey, "main", "vaksinKe",
                                                safeString(vaksin.getVaksinKe()));

                                if (vaksin.getJenisVaksin() != null) {
                                        JenisVaksin jenisVaksin = vaksin.getJenisVaksin();
                                        if (jenisVaksin.getJenis() != null) {
                                                client.insertRecord(tableVaksin, safeString(vaksin.getIdVaksin()),
                                                                "jenisVaksin", "idJenisVaksin",
                                                                safeString(jenisVaksin.getIdJenisVaksin()));
                                                client.insertRecord(tableVaksin, safeString(vaksin.getIdVaksin()),
                                                                "jenisVaksin", "jenis",
                                                                safeString(jenisVaksin.getJenis()));
                                                client.insertRecord(tableVaksin, safeString(vaksin.getIdVaksin()),
                                                                "jenisVaksin", "deskripsi",
                                                                safeString(jenisVaksin.getDeskripsi()));
                                        }
                                }
                                if (vaksin.getNamaVaksin() != null) {
                                        NamaVaksin namaVaksin = vaksin.getNamaVaksin();
                                        if (namaVaksin.getNama() != null) {
                                                client.insertRecord(tableVaksin, safeString(vaksin.getIdVaksin()),
                                                                "namaVaksin", "idNamaVaksin",
                                                                safeString(namaVaksin.getIdNamaVaksin()));
                                                client.insertRecord(tableVaksin, safeString(vaksin.getIdVaksin()),
                                                                "namaVaksin", "nama",
                                                                safeString(namaVaksin.getNama()));
                                                client.insertRecord(tableVaksin, safeString(vaksin.getIdVaksin()),
                                                                "namaVaksin",
                                                                "deskripsi", safeString(namaVaksin.getDeskripsi()));
                                        }
                                }

                                if (vaksin.getPeternak() != null) {
                                        Peternak peternak = vaksin.getPeternak();
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "idPeternak",
                                                        safeString(peternak.getIdPeternak()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "nikPeternak",
                                                        safeString(peternak.getNikPeternak()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "namaPeternak",
                                                        safeString(peternak.getNamaPeternak()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "email",
                                                        safeString(peternak.getEmail()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "lokasi",
                                                        safeString(peternak.getLokasi()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "noTelepon",
                                                        safeString(peternak.getNoTelepon()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "alamat",
                                                        safeString(peternak.getAlamat()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "dusun",
                                                        safeString(peternak.getDusun()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "desa",
                                                        safeString(peternak.getDesa()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "kecamatan",
                                                        safeString(peternak.getKecamatan()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "kabupaten",
                                                        safeString(peternak.getKabupaten()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "latitude",
                                                        safeString(peternak.getLatitude()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "longitude",
                                                        safeString(peternak.getLongitude()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "jenisKelamin",
                                                        safeString(peternak.getJenisKelamin()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "tanggalPendaftaran",
                                                        safeString(peternak.getTanggalPendaftaran()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "tanggalLahir",
                                                        safeString(peternak.getTanggalLahir()));
                                        client.insertRecord(tableVaksin, rowKey, "peternak", "idIsikhnas",
                                                        safeString(peternak.getIdIsikhnas()));
                                        client.insertRecord(tableVaksin, rowKey, "detail", "created_by", "Polinema");

                                }

                                if (vaksin.getPetugas() != null) {
                                        Petugas petugas = vaksin.getPetugas();
                                        client.insertRecord(tableVaksin, rowKey, "petugas", "nikPetugas",
                                                        safeString(petugas.getNikPetugas()));
                                        client.insertRecord(tableVaksin, rowKey, "petugas", "namaPetugas",
                                                        safeString(petugas.getNamaPetugas()));
                                        client.insertRecord(tableVaksin, rowKey, "petugas", "email",
                                                        safeString(petugas.getEmail()));
                                        client.insertRecord(tableVaksin, rowKey, "petugas", "noTelp",
                                                        safeString(petugas.getNoTelp()));
                                }

                                if (vaksin.getHewan() != null) {
                                        Hewan hewan = vaksin.getHewan();
                                        client.insertRecord(tableVaksin, rowKey, "hewan", "idHewan",
                                                        safeString(hewan.getIdHewan()));
                                        client.insertRecord(tableVaksin, rowKey, "hewan", "kodeEartagNasional",
                                                        safeString(hewan.getKodeEartagNasional()));
                                        client.insertRecord(tableVaksin, rowKey, "hewan", "sex",
                                                        safeString(hewan.getSex()));
                                        client.insertRecord(tableVaksin, rowKey, "hewan", "tanggalLahir",
                                                        safeString(hewan.getTanggalLahir()));
                                        client.insertRecord(tableVaksin, rowKey, "hewan", "tempatLahir",
                                                        safeString(hewan.getTempatLahir()));
                                        client.insertRecord(tableVaksin, rowKey, "hewan", "tanggalTerdaftar",
                                                        safeString(hewan.getTanggalTerdaftar()));
                                        client.insertRecord(tableVaksin, rowKey, "hewan", "umur",
                                                        safeString(hewan.getUmur()));
                                        client.insertRecord(tableVaksin, rowKey, "hewan", "identifikasiHewan",
                                                        safeString(hewan.getIdentifikasiHewan()));
                                }

                                client.insertRecord(tableVaksin, rowKey, "detail", "created_by", "Polinema");

                                System.out.println("Berhasil menyimpan Nama Vaksin: " + vaksin.getIdVaksin());

                        } catch (Exception e) {
                                failedRows.add(vaksin.getIdVaksin());
                                System.err.println(
                                                "Failed to insert record for NIK: " + vaksin.getIdVaksin() + ", Error: "
                                                                + e.getMessage());
                        }
                }

                if (!failedRows.isEmpty()) {
                        throw new IOException("Failed to save records for NIKs: " + String.join(", ", failedRows));
                }

                return vaksinList;
        }

        public List<Vaksin> findIdJenisVaksin(String idJenisVaksin) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableVaksin = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("jenisVaksin", "jenisVaksin");

                return client.getDataListByColumn(tableVaksin.toString(), columnMapping, "jenisVaksin", "idJenisVaksin",
                                idJenisVaksin, Vaksin.class, -1);
        }

        public List<Vaksin> findIdNamaVaksin(String idNamaVaksin) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableVaksin = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                columnMapping.put("idVaksin", "idVaksin");
                columnMapping.put("namaVaksin", "namaVaksin");

                return client.getDataListByColumn(tableVaksin.toString(), columnMapping, "namaVaksin", "idNamaVaksin",
                                idNamaVaksin, Vaksin.class, -1);
        }
}

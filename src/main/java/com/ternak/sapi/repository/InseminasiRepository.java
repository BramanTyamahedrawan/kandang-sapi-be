package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.*;
import com.ternak.sapi.model.*;
import org.checkerframework.checker.units.qual.K;

public class InseminasiRepository {
        Configuration conf = HBaseConfiguration.create();
        String tableName = "inseminasidev";

        public List<Inseminasi> findAll(int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableInseminasi = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idInseminasi", "idInseminasi");
                columnMapping.put("tanggalIB", "tanggalIB");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("ib1", "ib1");
                columnMapping.put("ib2", "ib2");
                columnMapping.put("ib3", "ib3");
                columnMapping.put("ibLain", "ibLain");
                columnMapping.put("idPejantan", "idPejantan");
                columnMapping.put("idPembuatan", "idPembuatan");
                columnMapping.put("bangsaPejantan", "bangsaPejantan");
                columnMapping.put("produsen", "produsen");
                return client.showListTable(tableInseminasi.toString(), columnMapping, Inseminasi.class, size);
        }

        private String safeString(String value) {
                System.out.println("Value before safeString: " + value);
                return (value != null && !value.trim().isEmpty()) ? value : "";
        }

        public Inseminasi save(Inseminasi inseminasi) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                String rowKey = inseminasi.getIdInseminasi();

                TableName tableInseminasi = TableName.valueOf(tableName);
                client.insertRecord(tableInseminasi, rowKey, "main", "idInseminasi", rowKey);
                if (inseminasi.getTanggalIB() != null) {
                        client.insertRecord(tableInseminasi, rowKey, "main", "tanggalIB", inseminasi.getTanggalIB());
                }
                if (inseminasi.getIb1() != null) {
                        client.insertRecord(tableInseminasi, rowKey, "main", "ib1", inseminasi.getIb1());
                }
                if (inseminasi.getIb2() != null) {
                        client.insertRecord(tableInseminasi, rowKey, "main", "ib2", inseminasi.getIb2());
                }
                if (inseminasi.getIb3() != null) {
                        client.insertRecord(tableInseminasi, rowKey, "main", "ib3", inseminasi.getIb3());
                }
                if (inseminasi.getIbLain() != null) {
                        client.insertRecord(tableInseminasi, rowKey, "main", "ibLain", inseminasi.getIbLain());
                }
                if (inseminasi.getIdPejantan() != null) {
                        client.insertRecord(tableInseminasi, rowKey, "main", "idPejantan", inseminasi.getIdPejantan());
                }
                if (inseminasi.getIdPembuatan() != null) {
                        client.insertRecord(tableInseminasi, rowKey, "main", "idPembuatan",
                                        inseminasi.getIdPembuatan());
                }
                if (inseminasi.getBangsaPejantan() != null) {
                        client.insertRecord(tableInseminasi, rowKey, "main", "bangsaPejantan",
                                        inseminasi.getBangsaPejantan());
                }
                if (inseminasi.getProdusen() != null) {
                        client.insertRecord(tableInseminasi, rowKey, "main", "produsen", inseminasi.getProdusen());
                }

                // Peternak
                client.insertRecord(tableInseminasi, rowKey, "peternak", "idPeternak",
                                inseminasi.getPeternak().getIdPeternak());
                client.insertRecord(tableInseminasi, rowKey, "peternak", "nikPeternak",
                                inseminasi.getPeternak().getNikPeternak());
                client.insertRecord(tableInseminasi, rowKey, "peternak", "namaPeternak",
                                inseminasi.getPeternak().getNamaPeternak());
                client.insertRecord(tableInseminasi, rowKey, "peternak", "alamat",
                                inseminasi.getPeternak().getAlamat());

                // Petugas
                client.insertRecord(tableInseminasi, rowKey, "petugas", "petugasId",
                                inseminasi.getPetugas().getPetugasId());
                client.insertRecord(tableInseminasi, rowKey, "petugas", "nikPetugas",
                                inseminasi.getPetugas().getNikPetugas());
                client.insertRecord(tableInseminasi, rowKey, "petugas", "namaPetugas",
                                inseminasi.getPetugas().getNamaPetugas());
                client.insertRecord(tableInseminasi, rowKey, "petugas", "job",
                                inseminasi.getPetugas().getJob());

                // Hewan
                client.insertRecord(tableInseminasi, rowKey, "hewan", "idHewan", inseminasi.getHewan().getIdHewan());
                client.insertRecord(tableInseminasi, rowKey, "hewan", "kodeEartagNasional",
                                inseminasi.getHewan().getKodeEartagNasional());
                client.insertRecord(tableInseminasi, rowKey, "hewan", "noKartuTernak",
                                inseminasi.getHewan().getNoKartuTernak());

                // Rumpun Hewan
                client.insertRecord(tableInseminasi, rowKey, "rumpunHewan", "idRumpunHewan",
                                inseminasi.getRumpunHewan().getIdRumpunHewan());
                client.insertRecord(tableInseminasi, rowKey, "rumpunHewan", "rumpun",
                                inseminasi.getRumpunHewan().getRumpun());
                client.insertRecord(tableInseminasi, rowKey, "rumpunHewan", "deskripsi",
                                inseminasi.getRumpunHewan().getDeskripsi());

                client.insertRecord(tableInseminasi, rowKey, "detail", "created_by", "Polinema");

                return inseminasi;
        }

        public List<Inseminasi> saveImport(List<Inseminasi> inseminasiList) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableInseminasi = TableName.valueOf(tableName);

                System.out.println("Memulai penyimpanan data ke HBase...");
                List<String> failedRows = new ArrayList<>();

                for (Inseminasi inseminasi : inseminasiList) {
                        try {

                                if (inseminasi.getPeternak() != null) {
                                        Peternak peternak = inseminasi.getPeternak();
                                        if (peternak.getIdPeternak() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "idPeternak",
                                                                safeString(peternak.getIdPeternak()));
                                        }
                                        if (peternak.getNikPeternak() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "nikPeternak",
                                                                safeString(peternak.getNikPeternak()));
                                        }
                                        if (peternak.getNamaPeternak() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "namaPeternak",
                                                                safeString(peternak.getNamaPeternak()));
                                        }
                                        if (peternak.getAlamat() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "alamat",
                                                                safeString(peternak.getAlamat()));
                                        }
                                        if (peternak.getLokasi() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "lokasi",
                                                                safeString(peternak.getLokasi()));
                                        }
                                        if (peternak.getEmail() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "email",
                                                                safeString(peternak.getEmail()));
                                        }
                                        if (peternak.getNoTelepon() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "noTelepon",
                                                                safeString(peternak.getNoTelepon()));
                                        }
                                        if (peternak.getJenisKelamin() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "jenisKelamin",
                                                                safeString(peternak.getJenisKelamin()));
                                        }
                                        if (peternak.getTanggalLahir() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "tanggalLahir",
                                                                safeString(peternak.getTanggalLahir()));
                                        }
                                        if (peternak.getIdIsikhnas() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "idIsikhnas",
                                                                safeString(peternak.getIdIsikhnas()));
                                        }
                                        // if (peternak.getDusun() != null) {
                                        // client.insertRecord(tableInseminasi,
                                        // safeString(inseminasi.getIdInseminasi()), "peternak",
                                        // "dusun",
                                        // safeString(peternak.getDusun()));
                                        // }
                                        if (peternak.getDesa() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "desa",
                                                                safeString(peternak.getDesa()));
                                        }
                                        if (peternak.getKecamatan() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "kecamatan",
                                                                safeString(peternak.getKecamatan()));
                                        }
                                        if (peternak.getKabupaten() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "kabupaten",
                                                                safeString(peternak.getKabupaten()));
                                        }
                                        if (peternak.getProvinsi() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "peternak",
                                                                "provinsi",
                                                                safeString(peternak.getProvinsi()));
                                        }
                                }

                                if (inseminasi.getPetugas() != null) {
                                        Petugas petugas = inseminasi.getPetugas();
                                        if (petugas.getNamaPetugas() != null) {
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "petugas",
                                                                "nikPetugas",
                                                                safeString(petugas.getNikPetugas()));
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "petugas",
                                                                "namaPetugas",
                                                                safeString(petugas.getNamaPetugas()));
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "petugas",
                                                                "email",
                                                                safeString(petugas.getEmail()));
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "petugas",
                                                                "noTelp",
                                                                safeString(petugas.getNoTelp()));
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "petugas",
                                                                "job",
                                                                safeString(petugas.getJob()));
                                                client.insertRecord(tableInseminasi,
                                                                safeString(inseminasi.getIdInseminasi()), "petugas",
                                                                "wilayah",
                                                                safeString(petugas.getWilayah()));

                                        }
                                }

                                if (inseminasi.getHewan() != null) {
                                        Hewan hewan = inseminasi.getHewan();
                                        client.insertRecord(tableInseminasi, safeString(inseminasi.getIdInseminasi()),
                                                        "hewan", "idHewan",
                                                        safeString(hewan.getIdHewan()));
                                        client.insertRecord(tableInseminasi, safeString(inseminasi.getIdInseminasi()),
                                                        "hewan",
                                                        "kodeEartagNasional",
                                                        safeString(hewan.getKodeEartagNasional()));
                                        client.insertRecord(tableInseminasi, safeString(inseminasi.getIdInseminasi()),
                                                        "hewan",
                                                        "noKartuTernak",
                                                        safeString(hewan.getNoKartuTernak()));
                                        client.insertRecord(tableInseminasi, safeString(inseminasi.getIdInseminasi()),
                                                        "hewan",
                                                        "identifikasiHewan", safeString(hewan.getIdentifikasiHewan()));
                                        client.insertRecord(tableInseminasi, safeString(inseminasi.getIdInseminasi()),
                                                        "hewan", "sex",
                                                        safeString(hewan.getSex()));
                                        client.insertRecord(tableInseminasi, safeString(inseminasi.getIdInseminasi()),
                                                        "hewan", "umur",
                                                        safeString(hewan.getUmur()));
                                        client.insertRecord(tableInseminasi, safeString(inseminasi.getIdInseminasi()),
                                                        "hewan",
                                                        "tanggalTerdaftar", safeString(hewan.getTanggalTerdaftar()));
                                        client.insertRecord(tableInseminasi, safeString(inseminasi.getIdInseminasi()),
                                                        "hewan",
                                                        "tanggalLahir",
                                                        safeString(hewan.getTanggalLahir()));
                                        client.insertRecord(tableInseminasi, safeString(inseminasi.getIdInseminasi()),
                                                        "hewan",
                                                        "tempatLahir", safeString(hewan.getTempatLahir()));
                                }

                                String rowKey = safeString(inseminasi.getIdInseminasi());

                                client.insertRecord(tableInseminasi, rowKey, "main", "idInseminasi",
                                                safeString(inseminasi.getIdInseminasi()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "tanggalIB",
                                                safeString(inseminasi.getTanggalIB()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "ib1",
                                                safeString(inseminasi.getIb1()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "ib2",
                                                safeString(inseminasi.getIb2()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "ib3",
                                                safeString(inseminasi.getIb3()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "ibLain",
                                                safeString(inseminasi.getIbLain()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "idPejantan",
                                                safeString(inseminasi.getIdPejantan()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "idPembuatan",
                                                safeString(inseminasi.getIdPembuatan()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "bangsaPejantan",
                                                safeString(inseminasi.getBangsaPejantan()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "produsen",
                                                safeString(inseminasi.getProdusen()));

                                System.out.println(
                                                "Data berhasil disimpan ke HBase dengan ID Inseminasi: "
                                                                + inseminasi.getIdInseminasi());

                        } catch (Exception e) {
                                failedRows.add(inseminasi.getIdInseminasi());
                                System.err.println(
                                                "Failed to insert record for ID: " + inseminasi.getIdInseminasi()
                                                                + ", Error: "
                                                                + e.getMessage());
                        }
                }
                if (!failedRows.isEmpty()) {
                        throw new IOException(
                                        "Failed to save records for ID Inseminasi: " + String.join(", ", failedRows));
                }
                return inseminasiList;

        }

        public List<Inseminasi> saveBulkImport(List<Inseminasi> inseminasiList) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableInseminasi = TableName.valueOf(tableName);

                System.out.println("Memulai penyimpanan data ke HBase...");
                List<String> failedRows = new ArrayList<>();

                for (Inseminasi inseminasi : inseminasiList) {
                        try {

                                String rowKey = safeString(inseminasi.getIdInseminasi());

                                if (inseminasi.getPetugas() != null) {
                                        Petugas petugas = inseminasi.getPetugas();
                                        client.insertRecord(tableInseminasi, rowKey, "petugas", "petugasId",
                                                        safeString(petugas.getPetugasId()));
                                        client.insertRecord(tableInseminasi, rowKey, "petugas", "nikPetugas",
                                                        safeString(petugas.getNikPetugas()));
                                        client.insertRecord(tableInseminasi, rowKey, "petugas", "namaPetugas",
                                                        safeString(petugas.getNamaPetugas()));
                                        client.insertRecord(tableInseminasi, rowKey, "petugas", "email",
                                                        safeString(petugas.getEmail()));
                                        client.insertRecord(tableInseminasi, rowKey, "petugas", "job",
                                                        safeString(petugas.getJob()));
                                        client.insertRecord(tableInseminasi, rowKey, "petugas", "noTelp",
                                                        safeString(petugas.getNoTelp()));
                                }

                                if (inseminasi.getPeternak() != null) {
                                        Peternak peternak = inseminasi.getPeternak();
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "idPeternak",
                                                        safeString(peternak.getIdPeternak()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "nikPeternak",
                                                        safeString(peternak.getNikPeternak()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "namaPeternak",
                                                        safeString(peternak.getNamaPeternak()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "email",
                                                        safeString(peternak.getEmail()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "lokasi",
                                                        safeString(peternak.getLokasi()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "noTelepon",
                                                        safeString(peternak.getNoTelepon()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "alamat",
                                                        safeString(peternak.getAlamat()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "dusun",
                                                        safeString(peternak.getDusun()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "desa",
                                                        safeString(peternak.getDesa()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "kecamatan",
                                                        safeString(peternak.getKecamatan()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "kabupaten",
                                                        safeString(peternak.getKabupaten()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "jenisKelamin",
                                                        safeString(peternak.getJenisKelamin()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "tanggalPendaftaran",
                                                        safeString(peternak.getTanggalPendaftaran()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "tanggalLahir",
                                                        safeString(peternak.getTanggalLahir()));
                                        client.insertRecord(tableInseminasi, rowKey, "peternak", "idIsikhnas",
                                                        safeString(peternak.getIdIsikhnas()));
                                        client.insertRecord(tableInseminasi, rowKey, "detail", "created_by",
                                                        "Polinema");
                                }

                                if (inseminasi.getKandang() != null) {
                                        Kandang kandang = inseminasi.getKandang();
                                        if (kandang.getNamaKandang() != null) {
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "kandang", "idKandang",
                                                                safeString(kandang.getIdKandang()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "kandang", "namaKandang",
                                                                safeString(kandang.getNamaKandang()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "kandang", "alamat",
                                                                safeString(kandang.getAlamat()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "kandang", "luas",
                                                                safeString(kandang.getLuas()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "kandang", "jenisKandang",
                                                                safeString(kandang.getJenisKandang()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "kandang", "kapasitas",
                                                                safeString(kandang.getKapasitas()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "kandang", "nilaiBangunan",
                                                                safeString(kandang.getNilaiBangunan()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "kandang", "latitude",
                                                                safeString(kandang.getLatitude()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "kandang", "longitude",
                                                                safeString(kandang.getLongitude()));
                                        }
                                }

                                if (inseminasi.getJenisHewan() != null) {
                                        JenisHewan jenisHewan = inseminasi.getJenisHewan();
                                        if (jenisHewan.getJenis() != null) {
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "jenisHewan", "idJenisHewan",
                                                                safeString(jenisHewan.getIdJenisHewan()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "jenisHewan", "jenis",
                                                                safeString(jenisHewan.getJenis()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "jenisHewan", "deskripsi",
                                                                safeString(jenisHewan.getDeskripsi()));
                                        }
                                }

                                if (inseminasi.getRumpunHewan() != null) {
                                        RumpunHewan rumpunHewan = inseminasi.getRumpunHewan();
                                        if (rumpunHewan.getRumpun() != null) {
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "rumpunHewan", "idRumpunHewan",
                                                                safeString(rumpunHewan.getIdRumpunHewan()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "rumpunHewan", "rumpun",
                                                                safeString(rumpunHewan.getRumpun()));
                                                client.insertRecord(tableInseminasi, rowKey,
                                                                "rumpunHewan", "deskripsi",
                                                                safeString(rumpunHewan.getDeskripsi()));
                                        }
                                }

                                if (inseminasi.getHewan() != null) {
                                        Hewan hewan = inseminasi.getHewan();
                                        client.insertRecord(tableInseminasi, rowKey, "hewan", "idHewan",
                                                        safeString(hewan.getIdHewan()));
                                        client.insertRecord(tableInseminasi, rowKey, "hewan", "kodeEartagNasional",
                                                        safeString(hewan.getKodeEartagNasional()));
                                        client.insertRecord(tableInseminasi, rowKey, "hewan", "noKartuTernak",
                                                        safeString(hewan.getNoKartuTernak()));
                                        client.insertRecord(tableInseminasi, rowKey, "hewan", "sex",
                                                        safeString(hewan.getSex()));
                                        client.insertRecord(tableInseminasi, rowKey, "hewan", "umur",
                                                        safeString(hewan.getUmur()));
                                        client.insertRecord(tableInseminasi, rowKey, "hewan", "identifikasiHewan",
                                                        safeString(hewan.getIdentifikasiHewan()));
                                        client.insertRecord(tableInseminasi, rowKey, "hewan", "tanggalLahir",
                                                        safeString(hewan.getTanggalLahir()));
                                        client.insertRecord(tableInseminasi, rowKey, "hewan", "tempatLahir",
                                                        safeString(hewan.getTempatLahir()));
                                        client.insertRecord(tableInseminasi, rowKey, "hewan", "tanggalTerdaftar",
                                                        safeString(hewan.getTanggalTerdaftar()));
                                }

                                client.insertRecord(tableInseminasi, rowKey, "main", "idInseminasi",
                                                safeString(inseminasi.getIdInseminasi()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "tanggalIB",
                                                safeString(inseminasi.getTanggalIB()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "ib1",
                                                safeString(inseminasi.getIb1()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "ib2",
                                                safeString(inseminasi.getIb2()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "ib3",
                                                safeString(inseminasi.getIb3()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "ibLain",
                                                safeString(inseminasi.getIbLain()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "idPejantan",
                                                safeString(inseminasi.getIdPejantan()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "idPembuatan",
                                                safeString(inseminasi.getIdPembuatan()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "bangsaPejantan",
                                                safeString(inseminasi.getBangsaPejantan()));
                                client.insertRecord(tableInseminasi, rowKey, "main", "produsen",
                                                safeString(inseminasi.getProdusen()));

                        } catch (Exception e) {
                                failedRows.add(inseminasi.getIdInseminasi());
                                System.err.println(
                                                "Failed to insert record for ID: " + inseminasi.getIdInseminasi()
                                                                + ", Error: "
                                                                + e.getMessage());
                        }
                }

                return inseminasiList;
        }

        public Inseminasi saveByIdPejantan(Inseminasi inseminasi) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableInseminasi = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idPejantan", "idPejantan");

                String rowKey = inseminasi.getIdPejantan();

                client.insertRecord(tableInseminasi, rowKey, "main", "idInseminasi", inseminasi.getIdInseminasi());
                client.insertRecord(tableInseminasi, rowKey, "main", "idPejantan", inseminasi.getIdPejantan());
                client.insertRecord(tableInseminasi, rowKey, "main", "idPembuatan", inseminasi.getIdPembuatan());
                client.insertRecord(tableInseminasi, rowKey, "main", "tanggalIB", inseminasi.getTanggalIB());
                client.insertRecord(tableInseminasi, rowKey, "main", "bangsaPejantan", inseminasi.getBangsaPejantan());
                client.insertRecord(tableInseminasi, rowKey, "main", "produsen", inseminasi.getProdusen());
                client.insertRecord(tableInseminasi, rowKey, "main", "ib1", inseminasi.getIb1());
                client.insertRecord(tableInseminasi, rowKey, "main", "ib2", inseminasi.getIb2());
                client.insertRecord(tableInseminasi, rowKey, "main", "ib3", inseminasi.getIb3());
                client.insertRecord(tableInseminasi, rowKey, "main", "ibLain", inseminasi.getIbLain());

                return inseminasi;
        }

        public Inseminasi update(String inseminasiId, Inseminasi inseminasi) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableInseminasi = TableName.valueOf(tableName);
                if (inseminasi.getTanggalIB() != null) {
                        client.insertRecord(tableInseminasi, inseminasiId, "main", "tanggalIB",
                                        inseminasi.getTanggalIB());
                }
                if (inseminasi.getIb1() != null) {
                        client.insertRecord(tableInseminasi, inseminasiId, "main", "ib1", inseminasi.getIb1());
                }
                if (inseminasi.getIb2() != null) {
                        client.insertRecord(tableInseminasi, inseminasiId, "main", "ib2", inseminasi.getIb2());
                }
                if (inseminasi.getIb3() != null) {
                        client.insertRecord(tableInseminasi, inseminasiId, "main", "ib3", inseminasi.getIb3());
                }
                if (inseminasi.getIbLain() != null) {
                        client.insertRecord(tableInseminasi, inseminasiId, "main", "ibLain", inseminasi.getIbLain());
                }
                if (inseminasi.getIdPejantan() != null) {
                        client.insertRecord(tableInseminasi, inseminasiId, "main", "idPejantan",
                                        inseminasi.getIdPejantan());
                }
                if (inseminasi.getIdPembuatan() != null) {
                        client.insertRecord(tableInseminasi, inseminasiId, "main", "idPembuatan",
                                        inseminasi.getIdPembuatan());
                }
                if (inseminasi.getBangsaPejantan() != null) {
                        client.insertRecord(tableInseminasi, inseminasiId, "main", "bangsaPejantan",
                                        inseminasi.getBangsaPejantan());
                }
                if (inseminasi.getProdusen() != null) {
                        client.insertRecord(tableInseminasi, inseminasiId, "main", "produsen",
                                        inseminasi.getProdusen());
                }

                // Peternak
                client.insertRecord(tableInseminasi, inseminasiId, "peternak", "idPeternak",
                                inseminasi.getPeternak().getIdPeternak());
                client.insertRecord(tableInseminasi, inseminasiId, "peternak", "nikPeternak",
                                inseminasi.getPeternak().getNikPeternak());
                client.insertRecord(tableInseminasi, inseminasiId, "peternak", "namaPeternak",
                                inseminasi.getPeternak().getNamaPeternak());
                client.insertRecord(tableInseminasi, inseminasiId, "peternak", "alamat",
                                inseminasi.getPeternak().getAlamat());

                // Petugas
                client.insertRecord(tableInseminasi, inseminasiId, "petugas", "petugasId",
                                inseminasi.getPetugas().getPetugasId());
                client.insertRecord(tableInseminasi, inseminasiId, "petugas", "nikPetugas",
                                inseminasi.getPetugas().getNikPetugas());
                client.insertRecord(tableInseminasi, inseminasiId, "petugas", "namaPetugas",
                                inseminasi.getPetugas().getNamaPetugas());
                client.insertRecord(tableInseminasi, inseminasiId, "petugas", "job",
                                inseminasi.getPetugas().getJob());

                // Hewan
                client.insertRecord(tableInseminasi, inseminasiId, "hewan", "idHewan",
                                inseminasi.getHewan().getIdHewan());
                client.insertRecord(tableInseminasi, inseminasiId, "hewan", "kodeEartagNasional",
                                inseminasi.getHewan().getKodeEartagNasional());
                client.insertRecord(tableInseminasi, inseminasiId, "hewan", "noKartuTernak",
                                inseminasi.getHewan().getNoKartuTernak());

                // Rumpun Hewan
                client.insertRecord(tableInseminasi, inseminasiId, "rumpunHewan", "idRumpunHewan",
                                inseminasi.getRumpunHewan().getIdRumpunHewan());
                client.insertRecord(tableInseminasi, inseminasiId, "rumpunHewan", "rumpun",
                                inseminasi.getRumpunHewan().getRumpun());
                client.insertRecord(tableInseminasi, inseminasiId, "rumpunHewan", "deskripsi",
                                inseminasi.getRumpunHewan().getDeskripsi());

                client.insertRecord(tableInseminasi, inseminasiId, "detail", "created_by", "Polinema");

                return inseminasi;
        }

        public Inseminasi updatePetugasByInseminasi(String inseminasiId, Inseminasi inseminasi) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableInseminasi = TableName.valueOf(tableName);

                client.insertRecord(tableInseminasi, inseminasiId, "petugas", "nikPetugas",
                                inseminasi.getPetugas().getNikPetugas());
                client.insertRecord(tableInseminasi, inseminasiId, "petugas", "namaPetugas",
                                inseminasi.getPetugas().getNamaPetugas());
                client.insertRecord(tableInseminasi, inseminasiId, "petugas", "noTelp",
                                inseminasi.getPetugas().getNoTelp());
                client.insertRecord(tableInseminasi, inseminasiId, "petugas", "email",
                                inseminasi.getPetugas().getEmail());
                client.insertRecord(tableInseminasi, inseminasiId, "main", "job", inseminasi.getPetugas().getJob());
                client.insertRecord(tableInseminasi, inseminasiId, "main", "wilayah",
                                inseminasi.getPetugas().getWilayah());
                client.insertRecord(tableInseminasi, inseminasiId, "detail", "created_by", "Polinema");
                return inseminasi;
        }

        public Inseminasi findInseminasiById(String inseminasiId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableInseminasi = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idInseminasi", "idInseminasi");
                columnMapping.put("tanggalIB", "tanggalIB");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("ib1", "ib1");
                columnMapping.put("ib2", "ib2");
                columnMapping.put("ib3", "ib3");
                columnMapping.put("ibLain", "ibLain");
                columnMapping.put("idPejantan", "idPejantan");
                columnMapping.put("idPembuatan", "idPembuatan");
                columnMapping.put("bangsaPejantan", "bangsaPejantan");
                columnMapping.put("produsen", "produsen");

                return client.showDataTable(tableInseminasi.toString(), columnMapping, inseminasiId, Inseminasi.class);
        }

        public Inseminasi findById(String idInseminasi) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableInseminasi = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                columnMapping.put("idInseminasi", "idInseminasi");
                columnMapping.put("tanggalIB", "tanggalIB");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("ib1", "ib1");
                columnMapping.put("ib2", "ib2");
                columnMapping.put("ib3", "ib3");
                columnMapping.put("ibLain", "ibLain");
                columnMapping.put("idPejantan", "idPejantan");
                columnMapping.put("idPembuatan", "idPembuatan");
                columnMapping.put("bangsaPejantan", "bangsaPejantan");
                columnMapping.put("produsen", "produsen");

                Inseminasi inseminasi = client.getDataByColumn(tableInseminasi.toString(), columnMapping, "main",
                                "idInseminasi", idInseminasi, Inseminasi.class);

                System.out.println("Data Inseminasi ditemukan by ID: " + idInseminasi);

                return inseminasi.getIdInseminasi() != null ? inseminasi : null;
        }

        public Inseminasi findInseminasiByIdPejantan(String idPejantan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableInseminasi = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idInseminasi", "idInseminasi");
                columnMapping.put("tanggalIB", "tanggalIB");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("ib1", "ib1");
                columnMapping.put("ib2", "ib2");
                columnMapping.put("ib3", "ib3");
                columnMapping.put("ibLain", "ibLain");
                columnMapping.put("idPejantan", "idPejantan");
                columnMapping.put("idPembuatan", "idPembuatan");
                columnMapping.put("bangsaPejantan", "bangsaPejantan");
                columnMapping.put("produsen", "produsen");

                Inseminasi inseminasi = client.getDataByColumn(tableInseminasi.toString(), columnMapping, "main",
                                "idPejantan",
                                idPejantan, Inseminasi.class);

                System.out.println("Data Inseminasi ditemukan by ID Pejantan: " + idPejantan);

                return inseminasi.getIdInseminasi() != null ? inseminasi : null;
        }

        public List<Inseminasi> findInseminasiByPeternak(String peternakId, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idInseminasi", "idInseminasi");
                columnMapping.put("tanggalIB", "tanggalIB");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("ib1", "ib1");
                columnMapping.put("ib2", "ib2");
                columnMapping.put("ib3", "ib3");
                columnMapping.put("ibLain", "ibLain");
                columnMapping.put("idPejantan", "idPejantan");
                columnMapping.put("idPembuatan", "idPembuatan");
                columnMapping.put("bangsaPejantan", "bangsaPejantan");
                columnMapping.put("produsen", "produsen");

                List<Inseminasi> inseminasi = client.getDataListByColumn(table.toString(), columnMapping, "peternak",
                                "namaPeternak", peternakId, Inseminasi.class, size);
                return inseminasi;
        }

        public List<Inseminasi> findInseminasiByPetugas(String namaPetugas, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idInseminasi", "idInseminasi");
                columnMapping.put("tanggalIB", "tanggalIB");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("ib1", "ib1");
                columnMapping.put("ib2", "ib2");
                columnMapping.put("ib3", "ib3");
                columnMapping.put("ibLain", "ibLain");
                columnMapping.put("idPejantan", "idPejantan");
                columnMapping.put("idPembuatan", "idPembuatan");
                columnMapping.put("bangsaPejantan", "bangsaPejantan");
                columnMapping.put("produsen", "produsen");

                return client.getDataListByColumn(table.toString(), columnMapping, "petugas", "namaPetugas",
                                namaPetugas,
                                Inseminasi.class, size);
        }

        public List<Inseminasi> findInseminasiByHewan(String hewanId, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idInseminasi", "idInseminasi");
                columnMapping.put("tanggalIB", "tanggalIB");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("ib1", "ib1");
                columnMapping.put("ib2", "ib2");
                columnMapping.put("ib3", "ib3");
                columnMapping.put("ibLain", "ibLain");
                columnMapping.put("idPejantan", "idPejantan");
                columnMapping.put("idPembuatan", "idPembuatan");
                columnMapping.put("bangsaPejantan", "bangsaPejantan");
                columnMapping.put("produsen", "produsen");
                return client.getDataListByColumn(table.toString(), columnMapping, "hewan", "idHewan", hewanId,
                                Inseminasi.class, size);
        }

        public List<Inseminasi> findAllById(List<String> inseminasiIds) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idInseminasi", "idInseminasi");
                columnMapping.put("tanggalIB", "tanggalIB");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("ib1", "ib1");
                columnMapping.put("ib2", "ib2");
                columnMapping.put("ib3", "ib3");
                columnMapping.put("ibLain", "ibLain");
                columnMapping.put("idPejantan", "idPejantan");
                columnMapping.put("idPembuatan", "idPembuatan");
                columnMapping.put("bangsaPejantan", "bangsaPejantan");
                columnMapping.put("produsen", "produsen");

                List<Inseminasi> inseminasis = new ArrayList<>();
                for (String inseminasiId : inseminasiIds) {
                        Inseminasi inseminasi = client.showDataTable(table.toString(), columnMapping, inseminasiId,
                                        Inseminasi.class);
                        if (inseminasi != null) {
                                inseminasis.add(inseminasi);
                        }
                }

                return inseminasis;
        }

        public List<Inseminasi> findByPetugasId(String petugasId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableInseminasi = TableName.valueOf(tableName); // Sesuaikan nama tabel HBase Anda
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idInseminasi", "idInseminasi");
                columnMapping.put("petugasId", "petugasId");
                columnMapping.put("petugas", "petugas");

                return client.getDataListByColumn(tableInseminasi.toString(), columnMapping,
                                "petugas", "petugasId", petugasId, Inseminasi.class, 100);
        }

        public boolean deleteById(String inseminasiId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                client.deleteRecord(tableName, inseminasiId);
                return true;
        }

        public boolean existsById(String inseminasiId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableInseminasi = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idInseminasi", "idInseminasi");

                Inseminasi inseminasi = client.getDataByColumn(tableInseminasi.toString(), columnMapping, "main",
                                "idInseminasi", inseminasiId,
                                Inseminasi.class);
                return inseminasi.getIdInseminasi() != null; // True jika ID Hewan sudah ada
        }
}

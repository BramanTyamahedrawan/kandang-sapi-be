
package com.ternak.sapi.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.model.TujuanPemeliharaan;

public class HewanRepository {
        Configuration conf = HBaseConfiguration.create();
        String tableName = "hewandev";
        // HewanController departmentController = new HewanController();
        private Iterable<String> idHewans;

        public List<Hewan> findAll(int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableUsers = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
                columnMapping.put("noKartuTernak", "noKartuTernak");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("tujuanPemeliharaan", "tujuanPemeliharaan");
                columnMapping.put("sex", "sex");
                columnMapping.put("umur", "umur");
                columnMapping.put("tanggalLahir", "tanggalLahir");
                columnMapping.put("tempatLahir", "tempatLahir");

                columnMapping.put("identifikasiHewan", "identifikasiHewan");
                columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
                columnMapping.put("petugasId","petugasId");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");
                return client.showListTable(tableUsers.toString(), columnMapping, Hewan.class, size);
        }

        public Hewan save(Hewan hewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                String rowKey = hewan.getIdHewan();
                TableName tableHewan = TableName.valueOf(tableName);
                client.insertRecord(tableHewan, rowKey, "main", "idHewan", rowKey);
                if (hewan.getKodeEartagNasional() != null) {
                        client.insertRecord(tableHewan, rowKey, "main", "kodeEartagNasional",
                                        hewan.getKodeEartagNasional());
                }
                if (hewan.getSex() != null) {
                        client.insertRecord(tableHewan, rowKey, "main", "sex", hewan.getSex());
                }
                if (hewan.getUmur() != null) {
                        client.insertRecord(tableHewan, rowKey, "main", "umur", hewan.getUmur());
                }
                if (hewan.getTanggalLahir() != null) {
                        client.insertRecord(tableHewan, rowKey, "main", "tanggalLahir", hewan.getTanggalLahir());
                }
                if (hewan.getTempatLahir() != null) {
                        client.insertRecord(tableHewan, rowKey, "main", "tempatLahir", hewan.getTempatLahir());
                }
                // if (hewan.getTujuanPemeliharaan() != null) {
                // client.insertRecord(tableHewan, rowKey, "main", "tujuanPemeliharaan",
                // hewan.getTujuanPemeliharaan());
                // }
                if (hewan.getIdentifikasiHewan() != null) {
                        client.insertRecord(tableHewan, rowKey, "main", "identifikasiHewan",
                                        hewan.getIdentifikasiHewan());
                }
                if (hewan.getTanggalTerdaftar() != null) {
                        client.insertRecord(tableHewan, rowKey, "main", "tanggalTerdaftar",
                                        hewan.getTanggalTerdaftar());
                }
                if (hewan.getLatitude() != null) {
                        client.insertRecord(tableHewan, rowKey, "main", "latitude", hewan.getLatitude());
                }
                if (hewan.getLongitude() != null) {
                        client.insertRecord(tableHewan, rowKey, "main", "longitude", hewan.getLongitude());
                }
                if (hewan.getFile_path() != null) {
                        client.insertRecord(tableHewan, rowKey, "main", "file_path", hewan.getFile_path());
                }
                if (hewan.getPeternak().getIdPeternak() != null) {
                        client.insertRecord(tableHewan, rowKey, "peternak", "idPeternak",
                                        hewan.getPeternak().getIdPeternak());
                }
                if (hewan.getPeternak().getNikPeternak() != null) {
                        client.insertRecord(tableHewan, rowKey, "peternak", "nikPeternak",
                                        hewan.getPeternak().getNikPeternak());
                }
                if (hewan.getPeternak().getNamaPeternak() != null) {
                        client.insertRecord(tableHewan, rowKey, "peternak", "namaPeternak",
                                        hewan.getPeternak().getNamaPeternak());
                }
                if (hewan.getJenisHewan().getIdJenisHewan() != null) {
                        client.insertRecord(tableHewan, rowKey, "jenisHewan", "idJenisHewan",
                                        hewan.getJenisHewan().getIdJenisHewan());
                }
                if (hewan.getJenisHewan().getJenis() != null) {
                        client.insertRecord(tableHewan, rowKey, "jenisHewan", "jenis",
                                        hewan.getJenisHewan().getJenis());
                }
                if (hewan.getJenisHewan().getDeskripsi() != null) {
                        client.insertRecord(tableHewan, rowKey, "jenisHewan", "deskripsi",
                                        hewan.getJenisHewan().getDeskripsi());
                }
                if (hewan.getRumpunHewan().getIdRumpunHewan() != null) {
                        client.insertRecord(tableHewan, rowKey, "rumpunHewan", "idRumpunHewan",
                                        hewan.getRumpunHewan().getIdRumpunHewan());
                }
                if (hewan.getRumpunHewan().getRumpun() != null) {
                        client.insertRecord(tableHewan, rowKey, "rumpunHewan", "rumpun",
                                        hewan.getRumpunHewan().getRumpun());
                }
                if (hewan.getRumpunHewan().getDeskripsi() != null) {
                        client.insertRecord(tableHewan, rowKey, "rumpunHewan", "deskripsi",
                                        hewan.getRumpunHewan().getDeskripsi());
                }
                if (hewan.getPetugas().getNikPetugas() != null) {
                        client.insertRecord(tableHewan, rowKey, "petugas", "nikPetugas",
                                        hewan.getPetugas().getNikPetugas());
                }
                if (hewan.getPetugas().getNamaPetugas() != null) {
                        client.insertRecord(tableHewan, rowKey, "petugas", "namaPetugas",
                                        hewan.getPetugas().getNamaPetugas());
                }
                if (hewan.getKandang().getIdKandang() != null) {
                        client.insertRecord(tableHewan, rowKey, "kandang", "idKandang",
                                        hewan.getKandang().getIdKandang());
                }
                client.insertRecord(tableHewan, rowKey, "detail", "created_by", "Polinema");
                return hewan;
        }

        public List<Hewan> saveAll(List<Hewan> hewanList) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableHewan = TableName.valueOf(tableName);

                System.out.println("Memulai penyimpanan data ke HBase...");
                List<String> failedRows = new ArrayList<>();

                for (Hewan hewan : hewanList) {
                        try {
                                String rowKey = safeString(hewan.getIdHewan());

                                if (hewan.getPeternak() != null) {
                                        Peternak peternak = hewan.getPeternak();
                                        client.insertRecord(tableHewan, rowKey, "peternak", "idPeternak",
                                                        safeString(peternak.getIdPeternak()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "nikPeternak",
                                                        safeString(peternak.getNikPeternak()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "namaPeternak",
                                                        safeString(peternak.getNamaPeternak()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "email",
                                                        safeString(peternak.getEmail()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "lokasi",
                                                        safeString(peternak.getLokasi()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "noTelepon",
                                                        safeString(peternak.getNoTelepon()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "alamat",
                                                        safeString(peternak.getAlamat()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "dusun",
                                                        safeString(peternak.getDusun()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "desa",
                                                        safeString(peternak.getDesa()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "kecamatan",
                                                        safeString(peternak.getKecamatan()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "kabupaten",
                                                        safeString(peternak.getKabupaten()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "latitude",
                                                        safeString(peternak.getLatitude()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "longitude",
                                                        safeString(peternak.getLongitude()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "jenisKelamin",
                                                        safeString(peternak.getJenisKelamin()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "tanggalPendaftaran",
                                                        safeString(peternak.getTanggalPendaftaran()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "tanggalLahir",
                                                        safeString(peternak.getTanggalLahir()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "idIsikhnas",
                                                        safeString(peternak.getIdIsikhnas()));
                                        client.insertRecord(tableHewan, rowKey, "detail", "created_by", "Polinema");
                                }

                                if (hewan.getPetugas() != null) {
                                        Petugas petugas = hewan.getPetugas();
                                        client.insertRecord(tableHewan, rowKey, "petugas", "petugasId",
                                                safeString(petugas.getPetugasId()));
                                        client.insertRecord(tableHewan, rowKey, "petugas", "nikPetugas",
                                                        safeString(petugas.getNikPetugas()));
                                        client.insertRecord(tableHewan, rowKey, "petugas", "namaPetugas",
                                                        safeString(petugas.getNamaPetugas()));
                                        client.insertRecord(tableHewan, rowKey, "petugas", "email",
                                                        safeString(petugas.getEmail()));
                                        client.insertRecord(tableHewan, rowKey, "petugas", "job",
                                                        safeString(petugas.getJob()));
                                        client.insertRecord(tableHewan, rowKey, "petugas", "noTelp",
                                                        safeString(petugas.getNoTelp()));
                                }

                                if (hewan.getKandang() != null) {
                                        Kandang kandang = hewan.getKandang();
                                        if (kandang.getNamaKandang() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "kandang", "idKandang",
                                                                safeString(kandang.getIdKandang()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "kandang", "namaKandang",
                                                                safeString(kandang.getNamaKandang()));
                                        }
                                }

                                if (hewan.getJenisHewan() != null) {
                                        JenisHewan jenisHewan = hewan.getJenisHewan();
                                        if (jenisHewan.getJenis() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "jenisHewan", "idJenisHewan",
                                                                safeString(jenisHewan.getIdJenisHewan()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "jenisHewan", "jenis",
                                                                safeString(jenisHewan.getJenis()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "jenisHewan", "deskripsi",
                                                                safeString(jenisHewan.getDeskripsi()));
                                        }
                                }

                                if (hewan.getRumpunHewan() != null) {
                                        RumpunHewan rumpunHewan = hewan.getRumpunHewan();
                                        if (rumpunHewan.getRumpun() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "rumpunHewan", "idRumpunHewan",
                                                                safeString(rumpunHewan.getIdRumpunHewan()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "rumpunHewan", "rumpun",
                                                                safeString(rumpunHewan.getRumpun()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "rumpunHewan", "deskripsi",
                                                                safeString(rumpunHewan.getDeskripsi()));
                                        }
                                }

                                if (hewan.getTujuanPemeliharaan() != null) {
                                        TujuanPemeliharaan tujuanPemeliharaan = hewan.getTujuanPemeliharaan();
                                        if (tujuanPemeliharaan.getTujuanPemeliharaan() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "tujuanPemeliharaan",
                                                                "idTujuanPemeliharaan", safeString(tujuanPemeliharaan
                                                                                .getIdTujuanPemeliharaan()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "tujuanPemeliharaan",
                                                                "tujuanPemeliharaan",
                                                                safeString(tujuanPemeliharaan.getTujuanPemeliharaan()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "tujuanPemeliharaan",
                                                                "deskripsi",
                                                                safeString(tujuanPemeliharaan.getDeskripsi()));

                                        }
                                }

                                client.insertRecord(tableHewan, rowKey, "main", "idHewan",
                                                safeString(hewan.getIdHewan()));
                                client.insertRecord(tableHewan, rowKey, "main", "kodeEartagNasional",
                                                safeString(hewan.getKodeEartagNasional()));
                                client.insertRecord(tableHewan, rowKey, "main", "sex", safeString(hewan.getSex()));
                                client.insertRecord(tableHewan, rowKey, "main", "umur", safeString(hewan.getUmur()));
                                client.insertRecord(tableHewan, rowKey, "main", "identifikasiHewan",
                                                safeString(hewan.getIdentifikasiHewan()));
                                client.insertRecord(tableHewan, rowKey, "main", "tanggalLahir",
                                                safeString(hewan.getTanggalLahir()));
                                client.insertRecord(tableHewan, rowKey, "main", "tempatLahir",
                                                safeString(hewan.getTempatLahir()));
                                client.insertRecord(tableHewan, rowKey, "main", "tanggalTerdaftar",
                                                safeString(hewan.getTanggalTerdaftar()));

                                client.insertRecord(tableHewan, rowKey, "detail", "created_by", "Polinema");
                                System.out.println("Data berhasil disimpan ke HBase: " + hewan.getIdHewan());
                        } catch (Exception e) {
                                failedRows.add(hewan.getIdHewan());
                                System.err.println(
                                                "Failed to insert record for ID: " + hewan.getIdHewan() + ", Error: "
                                                                + e.getMessage());
                        }
                }

                if (!failedRows.isEmpty()) {
                        throw new IOException("Failed to save records for Hewan: " + String.join(", ", failedRows));
                }

                return hewanList;
        }

        public List<Hewan> saveImport(List<Hewan> hewanList) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableHewan = TableName.valueOf(tableName);

                System.out.println("Memulai penyimpanan data ke HBase...");
                List<String> failedRows = new ArrayList<>();

                for (Hewan hewan : hewanList) {
                        try {

                                if (hewan.getPeternak() != null) {
                                        Peternak peternak = hewan.getPeternak();
                                        if (peternak.getIdPeternak() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "peternak", "idPeternak",
                                                                safeString(peternak.getIdPeternak()));
                                        }
                                        if (peternak.getNikPeternak() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "peternak", "nikPeternak",
                                                                safeString(peternak.getNikPeternak()));
                                        }
                                        if (peternak.getNamaPeternak() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "peternak", "namaPeternak",
                                                                safeString(peternak.getNamaPeternak()));
                                        }
                                        if (peternak.getAlamat() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "peternak",
                                                                "alamat",
                                                                safeString(peternak.getAlamat()));
                                        }
                                        if (peternak.getDesa() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "peternak",
                                                                "desa",
                                                                safeString(peternak.getDesa()));
                                        }
                                        if (peternak.getKecamatan() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "peternak",
                                                                "kecamatan",
                                                                safeString(peternak.getKecamatan()));
                                        }
                                        if (peternak.getKabupaten() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "peternak",
                                                                "kabupaten",
                                                                safeString(peternak.getKabupaten()));
                                        }
                                        if (peternak.getProvinsi() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "peternak",
                                                                "provinsi",
                                                                safeString(peternak.getProvinsi()));
                                        }
                                        if (peternak.getEmail() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "peternak",
                                                                "email",
                                                                safeString(peternak.getEmail()));
                                        }
                                        if (peternak.getNoTelepon() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "peternak",
                                                                "noTelepon",
                                                                safeString(peternak.getNoTelepon()));
                                        }
                                }

                                if (hewan.getJenisHewan() != null) {
                                        JenisHewan jenisHewan = hewan.getJenisHewan();
                                        if (jenisHewan.getJenis() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "jenisHewan", "idJenisHewan",
                                                                safeString(jenisHewan.getIdJenisHewan()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "jenisHewan", "jenis",
                                                                safeString(jenisHewan.getJenis()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "jenisHewan", "deskripsi",
                                                                safeString(jenisHewan.getDeskripsi()));
                                        }
                                }

                                if (hewan.getPetugas() != null) {
                                        Petugas petugas = hewan.getPetugas();
                                        if (petugas.getNamaPetugas() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()), "petugas", "petugasId",
                                                        safeString(petugas.getPetugasId()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "petugas", "nikPetugas",
                                                                safeString(petugas.getNikPetugas()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "petugas", "namaPetugas",
                                                                safeString(petugas.getNamaPetugas()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "petugas", "email",
                                                                safeString(petugas.getEmail()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "petugas", "noTelp",
                                                                safeString(petugas.getNoTelp()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "petugas", "job",
                                                                safeString(petugas.getJob()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "petugas", "wilayah",
                                                                safeString(petugas.getWilayah()));

                                        }

                                }

                                if (hewan.getKandang() != null) {
                                        Kandang kandang = hewan.getKandang();
                                        if (kandang.getNamaKandang() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "kandang", "idKandang",
                                                                kandang.getIdKandang() != null ? kandang.getIdKandang()
                                                                                : UUID.randomUUID().toString());
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "kandang", "namaKandang",
                                                                kandang.getNamaKandang() != null
                                                                                ? kandang.getNamaKandang()
                                                                                : "-");

                                        } else {
                                                System.out.println("Nama Kandang Kosong" + kandang.getNamaKandang());
                                        }
                                }

                                if (hewan.getRumpunHewan() != null) {
                                        RumpunHewan rumpunHewan = hewan.getRumpunHewan();
                                        if (rumpunHewan.getRumpun() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "rumpunHewan", "idRumpunHewan",
                                                                safeString(rumpunHewan.getIdRumpunHewan()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "rumpunHewan", "rumpun",
                                                                safeString(rumpunHewan.getRumpun()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "rumpunHewan", "deskripsi",
                                                                safeString(rumpunHewan.getDeskripsi()));
                                        }
                                }

                                if (hewan.getTujuanPemeliharaan() != null) {
                                        TujuanPemeliharaan tujuanPemeliharaan = hewan.getTujuanPemeliharaan();
                                        if (tujuanPemeliharaan.getTujuanPemeliharaan() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "tujuanPemeliharaan",
                                                                "idTujuanPemeliharaan", safeString(tujuanPemeliharaan
                                                                                .getIdTujuanPemeliharaan()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "tujuanPemeliharaan",
                                                                "tujuanPemeliharaan",
                                                                safeString(tujuanPemeliharaan.getTujuanPemeliharaan()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "tujuanPemeliharaan",
                                                                "deskripsi",
                                                                safeString(tujuanPemeliharaan.getDeskripsi()));

                                        }
                                }

                                String rowKey = safeString(hewan.getIdHewan());

                                client.insertRecord(tableHewan, rowKey, "main", "idHewan",
                                                safeString(hewan.getIdHewan()));
                                client.insertRecord(tableHewan, rowKey, "main", "noKartuTernak",
                                                safeString(hewan.getNoKartuTernak()));
                                client.insertRecord(tableHewan, rowKey, "main", "kodeEartagNasional",
                                                safeString(hewan.getKodeEartagNasional()));
                                client.insertRecord(tableHewan, rowKey, "main", "identifikasiHewan",
                                                safeString(hewan.getIdentifikasiHewan()));
                                client.insertRecord(tableHewan, rowKey, "main", "sex", safeString(hewan.getSex()));
                                client.insertRecord(tableHewan, rowKey, "main", "umur", safeString(hewan.getUmur()));
                                client.insertRecord(tableHewan, rowKey, "main", "tanggalTerdaftar",
                                                safeString(hewan.getTanggalTerdaftar()));
                                client.insertRecord(tableHewan, rowKey, "main", "tempatLahir",
                                                safeString(hewan.getTempatLahir()));
                                client.insertRecord(tableHewan, rowKey, "main", "tanggalLahir",
                                                safeString(hewan.getTanggalLahir()));
                                client.insertRecord(tableHewan, rowKey, "main", "petugasId",
                                        safeString(hewan.getPetugasId()));

                                System.out.println("Data Kandang : " + hewan.getKandang());
                                System.out.println("Berhasil menyimpan Hewan: " + hewan.getIdHewan());
                        } catch (Exception e) {
                                failedRows.add(hewan.getIdHewan());
                                System.err.println(
                                                "Failed to insert record for ID: " + hewan.getIdHewan() + ", Error: "
                                                                + e.getMessage());
                        }
                }
                if (!failedRows.isEmpty()) {
                        throw new IOException("Failed to save records for ID Hewan: " + String.join(", ", failedRows));
                }

                return hewanList;
        }

        public List<Hewan> saveBulkImport(List<Hewan> hewanList) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableHewan = TableName.valueOf(tableName);

                System.out.println("Memulai penyimpanan data ke HBase...");
                List<String> failedRows = new ArrayList<>();

                for (Hewan hewan : hewanList) {
                        try {
                                String rowKey = safeString(hewan.getIdHewan());

                                if (hewan.getPetugas() != null) {
                                        Petugas petugas = hewan.getPetugas();
                                        client.insertRecord(tableHewan, rowKey, "petugas", "petugasId",
                                                safeString(petugas.getPetugasId()));
                                        client.insertRecord(tableHewan, rowKey, "petugas", "nikPetugas",
                                                        safeString(petugas.getNikPetugas()));
                                        client.insertRecord(tableHewan, rowKey, "petugas", "namaPetugas",
                                                        safeString(petugas.getNamaPetugas()));
                                        client.insertRecord(tableHewan, rowKey, "petugas", "email",
                                                        safeString(petugas.getEmail()));
                                        client.insertRecord(tableHewan, rowKey, "petugas", "job",
                                                        safeString(petugas.getJob()));
                                        client.insertRecord(tableHewan, rowKey, "petugas", "noTelp",
                                                        safeString(petugas.getNoTelp()));
                                }

                                if (hewan.getPeternak() != null) {
                                        Peternak peternak = hewan.getPeternak();
                                        client.insertRecord(tableHewan, rowKey, "peternak", "idPeternak",
                                                        safeString(peternak.getIdPeternak()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "nikPeternak",
                                                        safeString(peternak.getNikPeternak()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "namaPeternak",
                                                        safeString(peternak.getNamaPeternak()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "email",
                                                        safeString(peternak.getEmail()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "lokasi",
                                                        safeString(peternak.getLokasi()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "noTelepon",
                                                        safeString(peternak.getNoTelepon()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "alamat",
                                                        safeString(peternak.getAlamat()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "dusun",
                                                        safeString(peternak.getDusun()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "desa",
                                                        safeString(peternak.getDesa()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "kecamatan",
                                                        safeString(peternak.getKecamatan()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "kabupaten",
                                                        safeString(peternak.getKabupaten()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "jenisKelamin",
                                                        safeString(peternak.getJenisKelamin()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "tanggalPendaftaran",
                                                        safeString(peternak.getTanggalPendaftaran()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "tanggalLahir",
                                                        safeString(peternak.getTanggalLahir()));
                                        client.insertRecord(tableHewan, rowKey, "peternak", "idIsikhnas",
                                                        safeString(peternak.getIdIsikhnas()));
                                        client.insertRecord(tableHewan, rowKey, "detail", "created_by", "Polinema");
                                }

                                if (hewan.getKandang() != null) {
                                        Kandang kandang = hewan.getKandang();
                                        if (kandang.getNamaKandang() != null) {
                                                client.insertRecord(tableHewan, rowKey,
                                                                "kandang", "idKandang",
                                                                safeString(kandang.getIdKandang()));
                                                client.insertRecord(tableHewan, rowKey,
                                                                "kandang", "namaKandang",
                                                                safeString(kandang.getNamaKandang()));
                                                client.insertRecord(tableHewan, rowKey,
                                                                "kandang", "alamat",
                                                                safeString(kandang.getAlamat()));
                                                client.insertRecord(tableHewan, rowKey,
                                                                "kandang", "luas",
                                                                safeString(kandang.getLuas()));
                                                client.insertRecord(tableHewan, rowKey,
                                                                "kandang", "jenisKandang",
                                                                safeString(kandang.getJenisKandang()));
                                                client.insertRecord(tableHewan, rowKey,
                                                                "kandang", "kapasitas",
                                                                safeString(kandang.getKapasitas()));
                                                client.insertRecord(tableHewan, rowKey,
                                                                "kandang", "nilaiBangunan",
                                                                safeString(kandang.getNilaiBangunan()));
                                                client.insertRecord(tableHewan, rowKey,
                                                                "kandang", "latitude",
                                                                safeString(kandang.getLatitude()));
                                                client.insertRecord(tableHewan, rowKey,
                                                                "kandang", "longitude",
                                                                safeString(kandang.getLongitude()));
                                        }
                                }

                                if (hewan.getJenisHewan() != null) {
                                        JenisHewan jenisHewan = hewan.getJenisHewan();
                                        if (jenisHewan.getJenis() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "jenisHewan", "idJenisHewan",
                                                                safeString(jenisHewan.getIdJenisHewan()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "jenisHewan", "jenis",
                                                                safeString(jenisHewan.getJenis()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "jenisHewan", "deskripsi",
                                                                safeString(jenisHewan.getDeskripsi()));
                                        }
                                }

                                if (hewan.getRumpunHewan() != null) {
                                        RumpunHewan rumpunHewan = hewan.getRumpunHewan();
                                        if (rumpunHewan.getRumpun() != null) {
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "rumpunHewan", "idRumpunHewan",
                                                                safeString(rumpunHewan.getIdRumpunHewan()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "rumpunHewan", "rumpun",
                                                                safeString(rumpunHewan.getRumpun()));
                                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()),
                                                                "rumpunHewan", "deskripsi",
                                                                safeString(rumpunHewan.getDeskripsi()));
                                        }
                                }

                                client.insertRecord(tableHewan, rowKey, "main", "idHewan",
                                                safeString(hewan.getIdHewan()));
                                client.insertRecord(tableHewan, rowKey, "main", "kodeEartagNasional",
                                                safeString(hewan.getKodeEartagNasional()));
                                client.insertRecord(tableHewan, rowKey, "main", "noKartuTernak",
                                                safeString(hewan.getNoKartuTernak()));
                                client.insertRecord(tableHewan, rowKey, "main", "sex", safeString(hewan.getSex()));
                                client.insertRecord(tableHewan, rowKey, "main", "umur", safeString(hewan.getUmur()));
                                client.insertRecord(tableHewan, rowKey, "main", "identifikasiHewan",
                                                safeString(hewan.getIdentifikasiHewan()));
                                client.insertRecord(tableHewan, rowKey, "main", "tanggalLahir",
                                                safeString(hewan.getTanggalLahir()));
                                client.insertRecord(tableHewan, rowKey, "main", "tempatLahir",
                                                safeString(hewan.getTempatLahir()));
                                client.insertRecord(tableHewan, rowKey, "main", "tanggalTerdaftar",
                                                safeString(hewan.getTanggalTerdaftar()));

                                client.insertRecord(tableHewan, rowKey, "detail", "created_by", "Polinema");
                                System.out.println("Data berhasil disimpan ke HBase: " + hewan.getIdHewan());
                        } catch (Exception e) {
                                failedRows.add(hewan.getIdHewan());
                                System.err.println(
                                                "Failed to insert record for ID: " + hewan.getIdHewan() + ", Error: "
                                                                + e.getMessage());
                        }
                }

                if (!failedRows.isEmpty()) {
                        throw new IOException("Failed to save records for Hewan: " + String.join(", ", failedRows));
                }

                return hewanList;
        }

        public Hewan saveByIDHewan(Hewan hewan) throws IOException {

                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableHewan = TableName.valueOf(tableName);

                String rowKey = hewan.getIdHewan();

                client.insertRecord(tableHewan, rowKey, "main", "idHewan",
                                safeString(hewan.getIdHewan()));
                client.insertRecord(tableHewan, rowKey, "main", "noKartuTernak", safeString(hewan.getNoKartuTernak()));
                client.insertRecord(tableHewan, rowKey, "main", "kodeEartagNasional",
                                safeString(hewan.getKodeEartagNasional()));
                client.insertRecord(tableHewan, rowKey, "main", "identifikasiHewan",
                                safeString(hewan.getIdentifikasiHewan()));
                client.insertRecord(tableHewan, rowKey, "main", "sex", safeString(hewan.getSex()));
                client.insertRecord(tableHewan, rowKey, "main", "umur", safeString(hewan.getUmur()));
                client.insertRecord(tableHewan, rowKey, "main", "tanggalTerdaftar",
                                safeString(hewan.getTanggalTerdaftar()));
                client.insertRecord(tableHewan, rowKey, "main", "tanggalLahir", safeString(hewan.getTanggalLahir()));
                client.insertRecord(tableHewan, rowKey, "main", "tempatLahir", safeString(hewan.getTempatLahir()));

                if (hewan.getJenisHewan() != null) {
                        JenisHewan jenisHewan = hewan.getJenisHewan();
                        if (jenisHewan.getJenis() != null) {
                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()), "jenisHewan",
                                                "idJenisHewan",
                                                safeString(jenisHewan.getIdJenisHewan()));
                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()), "jenisHewan", "jenis",
                                                safeString(jenisHewan.getJenis()));
                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()), "jenisHewan",
                                                "deskripsi",
                                                safeString(jenisHewan.getDeskripsi()));
                        }
                }

                if (hewan.getRumpunHewan() != null) {
                        RumpunHewan rumpunHewan = hewan.getRumpunHewan();
                        if (rumpunHewan.getRumpun() != null) {
                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()), "rumpunHewan",
                                                "idRumpunHewan",
                                                safeString(rumpunHewan.getIdRumpunHewan()));
                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()), "rumpunHewan", "rumpun",
                                                safeString(rumpunHewan.getRumpun()));
                                client.insertRecord(tableHewan, safeString(hewan.getIdHewan()), "rumpunHewan",
                                                "deskripsi",
                                                safeString(rumpunHewan.getDeskripsi()));
                        }
                }

                if (hewan.getPeternak() != null) {
                        Peternak peternak = hewan.getPeternak();
                        client.insertRecord(tableHewan, rowKey, "peternak", "idPeternak",
                                        safeString(peternak.getIdPeternak()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "nikPeternak",
                                        safeString(peternak.getNikPeternak()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "namaPeternak",
                                        safeString(peternak.getNamaPeternak()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "email",
                                        safeString(peternak.getEmail()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "lokasi",
                                        safeString(peternak.getLokasi()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "noTelepon",
                                        safeString(peternak.getNoTelepon()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "alamat",
                                        safeString(peternak.getAlamat()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "dusun",
                                        safeString(peternak.getDusun()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "desa",
                                        safeString(peternak.getDesa()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "kecamatan",
                                        safeString(peternak.getKecamatan()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "kabupaten",
                                        safeString(peternak.getKabupaten()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "jenisKelamin",
                                        safeString(peternak.getJenisKelamin()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "tanggalPendaftaran",
                                        safeString(peternak.getTanggalPendaftaran()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "tanggalLahir",
                                        safeString(peternak.getTanggalLahir()));
                        client.insertRecord(tableHewan, rowKey, "peternak", "idIsikhnas",
                                        safeString(peternak.getIdIsikhnas()));
                        client.insertRecord(tableHewan, rowKey, "detail", "created_by", "Polinema");
                }

                if (hewan.getPetugas() != null) {
                        Petugas petugas = hewan.getPetugas();
                        client.insertRecord(tableHewan, rowKey, "petugas", "petugasId",
                                safeString(petugas.getPetugasId()));
                        client.insertRecord(tableHewan, rowKey, "petugas", "nikPetugas",
                                        safeString(petugas.getNikPetugas()));
                        client.insertRecord(tableHewan, rowKey, "petugas", "namaPetugas",
                                        safeString(petugas.getNamaPetugas()));
                        client.insertRecord(tableHewan, rowKey, "petugas", "email",
                                        safeString(petugas.getEmail()));
                        client.insertRecord(tableHewan, rowKey, "petugas", "job", safeString(petugas.getJob()));
                        client.insertRecord(tableHewan, rowKey, "petugas", "noTelp", safeString(petugas.getNoTelp()));
                }

                return hewan;
        }

        private String safeString(String value) {
                return value != null ? value : "";
        }

        public Hewan findById(String idHewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableHewan = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
                columnMapping.put("noKartuTernak", "noKartuTernak");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");

                columnMapping.put("sex", "sex");
                columnMapping.put("umur", "umur");
                columnMapping.put("tanggalLahir", "tanggalLahir");
                columnMapping.put("tempatLahir", "tempatLahir");
                columnMapping.put("tujuanPemeliharaan", "tujuanPemeliharaan");

                columnMapping.put("identifikasiHewan", "identifikasiHewan");
                columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");

                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");

                Hewan hewan = client.getDataByColumn(tableHewan.toString(), columnMapping, "main", "idHewan", idHewan,
                                Hewan.class);

                return hewan.getIdHewan() != null ? hewan : null;
        }

        public Hewan findByIdNoKandang(String idHewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableHewan = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
                columnMapping.put("noKartuTernak", "noKartuTernak");

                columnMapping.put("peternak", "peternak");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");

                columnMapping.put("sex", "sex");
                columnMapping.put("umur", "umur");
                columnMapping.put("tanggalLahir", "tanggalLahir");
                columnMapping.put("tempatLahir", "tempatLahir");
                columnMapping.put("tujuanPemeliharaan", "tujuanPemeliharaan");

                columnMapping.put("identifikasiHewan", "identifikasiHewan");
                columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");

                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");

                Hewan hewan = client.getDataByColumn(tableHewan.toString(), columnMapping, "main", "idHewan", idHewan,
                                Hewan.class);

                return hewan.getIdHewan() != null ? hewan : null;
        }

        public Hewan findByNoEartag(String kodeEartagNasional) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableUsers = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
                columnMapping.put("noKartuTernak", "noKartuTernak");

                columnMapping.put("peternak", "peternak");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");

                columnMapping.put("sex", "sex");
                columnMapping.put("umur", "umur");
                columnMapping.put("tanggalLahir", "tanggalLahir");
                columnMapping.put("tempatLahir", "tempatLahir");
                columnMapping.put("tujuanPemeliharaan", "tujuanPemeliharaan");

                columnMapping.put("identifikasiHewan", "identifikasiHewan");
                columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");

                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");

                Hewan hewan = client.getDataByColumn(tableUsers.toString(), columnMapping, "main", "kodeEartagNasional",
                                kodeEartagNasional, Hewan.class);
                return hewan.getKodeEartagNasional() != null ? hewan : null;
        }

        public List<Hewan> findHewanByPeternak(String peternakID, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableUsers = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
                columnMapping.put("noKartuTernak", "noKartuTernak");

                columnMapping.put("peternak", "peternak");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("sex", "sex");
                columnMapping.put("umur", "umur");
                columnMapping.put("identifikasiHewan", "identifikasiHewan");
                columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");

                List<Hewan> hewan = client.getDataListByColumn(tableUsers.toString(), columnMapping, "peternak",
                                "nikPeternak",
                                peternakID, Hewan.class, size);

                return hewan;
        }

        public List<Hewan> findHewanByKandang(String kandangId, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableUsers = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
                columnMapping.put("noKartuTernak", "noKartuTernak");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("sex", "sex");
                columnMapping.put("umur", "umur");
                columnMapping.put("identifikasiHewan", "identifikasiHewan");
                columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");

                List<Hewan> hewan = client.getDataListByColumn(tableUsers.toString(), columnMapping, "kandang",
                                "idKandang",
                                kandangId, Hewan.class, size);

                return hewan;
        }

        public List<Hewan> findHewanByPetugas(String petugasId, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableUsers = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
                columnMapping.put("noKartuTernak", "noKartuTernak");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("sex", "sex");
                columnMapping.put("umur", "umur");
                columnMapping.put("identifikasiHewan", "identifikasiHewan");
                columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");

                List<Hewan> hewan = client.getDataListByColumn(tableUsers.toString(), columnMapping, "petugas",
                                "nikPetugas",
                                petugasId, Hewan.class, size);

                return hewan;
        }

        public List<Hewan> findHewanByJenisHewan(String jenis, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableUsers = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
                columnMapping.put("noKartuTernak", "noKartuTernak");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("sex", "sex");
                columnMapping.put("umur", "umur");
                columnMapping.put("identifikasiHewan", "identifikasiHewan");
                columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");

                List<Hewan> hewan = client.getDataListByColumn(tableUsers.toString(), columnMapping, "jenisHewan",
                                "jenis",
                                jenis, Hewan.class, size);

                return hewan;
        }

        public List<Hewan> findHewanByRumpunHewan(String rumpun, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableUsers = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
                columnMapping.put("noKartuTernak", "noKartuTernak");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("sex", "sex");
                columnMapping.put("umur", "umur");
                columnMapping.put("identifikasiHewan", "identifikasiHewan");
                columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");

                List<Hewan> hewan = client.getDataListByColumn(tableUsers.toString(), columnMapping, "rumpunHewan",
                                "rumpun",
                                rumpun, Hewan.class, size);

                return hewan;
        }

        public List<Hewan> findHewanByTujuanPemeliharaan(String tujuan, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableUsers = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
                columnMapping.put("noKartuTernak", "noKartuTernak");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("sex", "sex");
                columnMapping.put("umur", "umur");
                columnMapping.put("identifikasiHewan", "identifikasiHewan");
                columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");

                List<Hewan> hewan = client.getDataListByColumn(tableUsers.toString(), columnMapping,
                                "tujuanPemeliharaan",
                                "tujuan",
                                tujuan, Hewan.class, size);

                return hewan;
        }

        public List<Hewan> findByPetugasId(String petugasId) throws IOException {
                // Get list of Hewan based on petugasId
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableHewan = TableName.valueOf(tableName); // Sesuaikan nama tabel HBase Anda
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("petugasId", "petugasId");
                columnMapping.put("petugas", "petugas");

                return client.getDataListByColumn(tableHewan.toString(), columnMapping,
                        "petugas", "petugasId", petugasId, Hewan.class, 100);
        }

        public List<Hewan> findAllById(List<String> ideHewans) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
                columnMapping.put("noKartuTernak", "noKartuTernak");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("sex", "sex");
                columnMapping.put("umur", "umur");
                columnMapping.put("identifikasiHewan", "identifikasiHewan");
                columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
                columnMapping.put("latitude", "latitude");
                columnMapping.put("longitude", "longitude");
                columnMapping.put("file_path", "file_path");

                List<Hewan> hewans = new ArrayList<>();
                for (String idHewan : idHewans) {
                        Hewan hewan = client.showDataTable(table.toString(), columnMapping, idHewan, Hewan.class);
                        if (hewan != null) {
                                hewans.add(hewan);
                        }
                }

                return hewans;
        }

        public Hewan update(String idHewan, Hewan hewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableHewan = TableName.valueOf(tableName);
                if (hewan.getSex() != null) {
                        client.insertRecord(tableHewan, idHewan, "main", "sex", hewan.getSex());
                }
                if (hewan.getUmur() != null) {
                        client.insertRecord(tableHewan, idHewan, "main", "umur", hewan.getUmur());
                }
                if (hewan.getTanggalLahir() != null) {
                        client.insertRecord(tableHewan, idHewan, "main", "tanggalLahir", hewan.getTanggalLahir());
                }
                if (hewan.getTempatLahir() != null) {
                        client.insertRecord(tableHewan, idHewan, "main", "tempatLahir", hewan.getTempatLahir());
                }
                // if (hewan.getTujuanPemeliharaan() != null) {
                // client.insertRecord(tableHewan, idHewan, "main", "tujuanPemeliharaan",
                // hewan.getTujuanPemeliharaan());
                // }
                if (hewan.getIdentifikasiHewan() != null) {
                        client.insertRecord(tableHewan, idHewan, "main", "identifikasiHewan",
                                        hewan.getIdentifikasiHewan());
                }
                if (hewan.getTanggalTerdaftar() != null) {
                        client.insertRecord(tableHewan, idHewan, "main", "tanggalTerdaftar",
                                        hewan.getTanggalTerdaftar());
                }
                if (hewan.getLatitude() != null) {
                        client.insertRecord(tableHewan, idHewan, "main", "latitude", hewan.getLatitude());
                }
                if (hewan.getLongitude() != null) {
                        client.insertRecord(tableHewan, idHewan, "main", "longitude", hewan.getLongitude());
                }
                if (hewan.getFile_path() != null) {
                        client.insertRecord(tableHewan, idHewan, "main", "file_path", hewan.getFile_path());
                }
                if (hewan.getPeternak().getIdPeternak() != null) {
                        client.insertRecord(tableHewan, idHewan, "peternak", "idPeternak",
                                        hewan.getPeternak().getIdPeternak());
                }
                if (hewan.getPeternak().getNikPeternak() != null) {
                        client.insertRecord(tableHewan, idHewan, "peternak", "nikPeternak",
                                        hewan.getPeternak().getNikPeternak());
                }
                if (hewan.getPeternak().getNamaPeternak() != null) {
                        client.insertRecord(tableHewan, idHewan, "peternak", "namaPeternak",
                                        hewan.getPeternak().getNamaPeternak());
                }
                if (hewan.getJenisHewan().getIdJenisHewan() != null) {
                        client.insertRecord(tableHewan, idHewan, "jenisHewan", "idJenisHewan",
                                        hewan.getJenisHewan().getIdJenisHewan());
                }
                if (hewan.getJenisHewan().getJenis() != null) {
                        client.insertRecord(tableHewan, idHewan, "jenisHewan", "jenis",
                                        hewan.getJenisHewan().getJenis());
                }
                if (hewan.getJenisHewan().getDeskripsi() != null) {
                        client.insertRecord(tableHewan, idHewan, "jenisHewan", "deskripsi",
                                        hewan.getJenisHewan().getDeskripsi());
                }
                if (hewan.getRumpunHewan().getIdRumpunHewan() != null) {
                        client.insertRecord(tableHewan, idHewan, "rumpunHewan", "idRumpunHewan",
                                        hewan.getRumpunHewan().getIdRumpunHewan());
                }
                if (hewan.getRumpunHewan().getRumpun() != null) {
                        client.insertRecord(tableHewan, idHewan, "rumpunHewan", "rumpun",
                                        hewan.getRumpunHewan().getRumpun());
                }
                if (hewan.getRumpunHewan().getDeskripsi() != null) {
                        client.insertRecord(tableHewan, idHewan, "rumpunHewan", "deskripsi",
                                        hewan.getRumpunHewan().getDeskripsi());
                }
                if (hewan.getPetugas().getNikPetugas() != null) {
                        client.insertRecord(tableHewan, idHewan, "petugas", "nikPetugas",
                                        hewan.getPetugas().getNikPetugas());
                }
                if (hewan.getPetugas().getNamaPetugas() != null) {
                        client.insertRecord(tableHewan, idHewan, "petugas", "namaPetugas",
                                        hewan.getPetugas().getNamaPetugas());
                }
                if (hewan.getKandang().getIdKandang() != null) {
                        client.insertRecord(tableHewan, idHewan, "kandang", "idKandang",
                                        hewan.getKandang().getIdKandang());
                }
                client.insertRecord(tableHewan, idHewan, "detail", "created_by", "Polinema");
                return hewan;
        }

        public Hewan updatePetugasByHewan(String IdHewan, Hewan hewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableHewan = TableName.valueOf(tableName);
                System.out.println("Nik Petugas " + hewan.getPetugas().getNikPetugas());
                if(hewan.getPetugas().getNikPetugas() != null ){
                        client.insertRecord(tableHewan, IdHewan, "petugas", "nikPetugas", hewan.getPetugas().getNikPetugas());
                }
                if(hewan.getPetugas().getNamaPetugas() != null ){
                        client.insertRecord(tableHewan, IdHewan, "petugas", "namaPetugas", hewan.getPetugas().getNamaPetugas());
                }
                if(hewan.getPetugas().getNoTelp() != null ){
                        client.insertRecord(tableHewan, IdHewan, "petugas", "noTelp", hewan.getPetugas().getNoTelp());
                }
                if(hewan.getPetugas().getEmail() != null ){
                        client.insertRecord(tableHewan, IdHewan, "petugas", "email", hewan.getPetugas().getEmail());
                }
                if(hewan.getPetugas().getJob() != null ){
                        client.insertRecord(tableHewan, IdHewan, "petugas", "job", hewan.getPetugas().getJob());
                }
                if(hewan.getPetugas().getWilayah() != null ){
                        client.insertRecord(tableHewan, IdHewan, "petugas", "wilayah", hewan.getPetugas().getWilayah());
                }
                return hewan;
        }

        public Hewan findPetugasIdByHewan(String petugasId) throws  IOException{
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableHewan = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idHewan", "idHewan");
                columnMapping.put("petugasId", "petugasId");
                columnMapping.put("petugas", "petugas");

                Hewan hewan = client.getDataByColumn( tableHewan.toString(),columnMapping,"main", "petugasId",petugasId,Hewan.class);
                return  hewan.getPetugasId() != null ? hewan : null;
        }

        public boolean deleteById(String idHewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                client.deleteRecord(tableName, idHewan);
                return true;
        }

        public boolean existsByIdHewan(String idHewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableHewan = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idHewan", "idHewan");

                Hewan hewan = client.getDataByColumn(tableHewan.toString(), columnMapping, "main", "idHewan", idHewan,
                                Hewan.class);
                return hewan.getIdHewan() != null; // True jika ID Hewan sudah ada
        }

        public boolean existsByKodeEartagNasional(String kodeEartagNasional) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableHewan = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("kodeEartagNasional", "kodeEartagNasional");

                Hewan hewan = client.getDataByColumn(tableHewan.toString(), columnMapping, "main", "kodeEartagNasional",
                                kodeEartagNasional, Hewan.class);
                return hewan.getKodeEartagNasional() != null; // True jika kode eartag sudah ada
        }

}

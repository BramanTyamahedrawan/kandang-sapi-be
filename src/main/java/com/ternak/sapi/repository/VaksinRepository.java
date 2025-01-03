package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Vaksin;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.model.NamaVaksin;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.Peternak;
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
        client.insertRecord(tableVaksin, rowKey, "main", "idVaksin", vaksin.getIdVaksin());
        if (vaksin.getNamaVaksin() != null) {
            client.insertRecord(tableVaksin, rowKey, "main", "namaVaksin", vaksin.getNamaVaksin());
        }
        if (vaksin.getJenisVaksin() != null) {
            client.insertRecord(tableVaksin, rowKey, "main", "jenisVaksin", vaksin.getJenisVaksin());
        }
        if (vaksin.getTglVaksin() != null) {
            client.insertRecord(tableVaksin, rowKey, "main", "tglVaksin", vaksin.getTglVaksin());
        }
        if (vaksin.getBatchVaksin() != null) {
            client.insertRecord(tableVaksin, rowKey, "main", "batchVaksin", vaksin.getBatchVaksin());
        }
        if (vaksin.getVaksinKe() != null) {
            client.insertRecord(tableVaksin, rowKey, "main", "vaksinKe", vaksin.getVaksinKe());
        }
        client.insertRecord(tableVaksin, rowKey, "peternak", "idPeternak", vaksin.getPeternak().getIdPeternak());
        client.insertRecord(tableVaksin, rowKey, "peternak", "nikPeternak", vaksin.getPeternak().getNikPeternak());
        client.insertRecord(tableVaksin, rowKey, "peternak", "namaPeternak", vaksin.getPeternak().getNamaPeternak());
        client.insertRecord(tableVaksin, rowKey, "peternak", "lokasi", vaksin.getPeternak().getLokasi());
        client.insertRecord(tableVaksin, rowKey, "petugas", "nikPetugas", vaksin.getPetugas().getNikPetugas());
        client.insertRecord(tableVaksin, rowKey, "petugas", "namaPetugas", vaksin.getPetugas().getNamaPetugas());
        client.insertRecord(tableVaksin, rowKey, "hewan", "kodeEartagNasional",
                vaksin.getHewan().getKodeEartagNasional());
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

        return client.getDataListByColumn(table.toString(), columnMapping, "hewan", "kodeEartagNasional", hewanId,
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
        if (vaksin.getNamaVaksin() != null) {
            client.insertRecord(tableVaksin, vaksinId, "main", "namaVaksin", vaksin.getNamaVaksin());
        }
        if (vaksin.getJenisVaksin() != null) {
            client.insertRecord(tableVaksin, vaksinId, "main", "jenisVaksin", vaksin.getJenisVaksin());
        }
        if (vaksin.getTglVaksin() != null) {
            client.insertRecord(tableVaksin, vaksinId, "main", "tglVaksin", vaksin.getTglVaksin());
        }
        if (vaksin.getBatchVaksin() != null) {
            client.insertRecord(tableVaksin, vaksinId, "main", "batchVaksin", vaksin.getBatchVaksin());
        }
        if (vaksin.getVaksinKe() != null) {
            client.insertRecord(tableVaksin, vaksinId, "main", "vaksinKe", vaksin.getVaksinKe());
        }
        client.insertRecord(tableVaksin, vaksinId, "peternak", "idPeternak", vaksin.getPeternak().getIdPeternak());
        client.insertRecord(tableVaksin, vaksinId, "peternak", "nikPeternak", vaksin.getPeternak().getNikPeternak());
        client.insertRecord(tableVaksin, vaksinId, "peternak", "namaPeternak", vaksin.getPeternak().getNamaPeternak());
        client.insertRecord(tableVaksin, vaksinId, "peternak", "lokasi", vaksin.getPeternak().getLokasi());
        client.insertRecord(tableVaksin, vaksinId, "petugas", "nikPetugas", vaksin.getPetugas().getNikPetugas());
        client.insertRecord(tableVaksin, vaksinId, "petugas", "namaPetugas", vaksin.getPetugas().getNamaPetugas());
        client.insertRecord(tableVaksin, vaksinId, "hewan", "kodeEartagNasional",
                vaksin.getHewan().getKodeEartagNasional());
        return vaksin;
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

        Vaksin vaksin = client.getDataByColumn(tableVaksin.toString(), columnMapping, "main", "idVaksin", idVaksin,
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

        Vaksin vaksin = client.getDataByColumn(tableVaksin.toString(), columnMapping, "main", "hewan_id", hewanId,
                Vaksin.class);
        return vaksin != null; // True jika hewan sudah memiliki vaksin yang terdaftar
    }

    public boolean existsByHewanIdAndNamaVaksinAndVaksinKe(String hewanId, String namaVaksin, String vaksinKe)
            throws IOException {
        // Logika untuk memeriksa apakah hewan dengan hewan_id tertentu sudah divaksin
        // dengan nama vaksin dan vaksin ke yang sama
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableVaksin = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("hewan_id", "hewan_id");
        columnMapping.put("namaVaksin", "namaVaksin");
        columnMapping.put("vaksinKe", "vaksinKe");

        // Mencari vaksin dengan kondisi yang sudah disebutkan
        Vaksin vaksin = client.getDataByColumn(tableVaksin.toString(), columnMapping, "main", "hewan_id", hewanId,
                Vaksin.class);

        // Cek apakah vaksin dengan kombinasi tersebut sudah ada
        return vaksin != null && vaksin.getNamaVaksin().equals(namaVaksin) && vaksin.getVaksinKe().equals(vaksinKe);
    }

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

                client.insertRecord(tableVaksin, rowKey, "main", "tglVaksin",
                        safeString(vaksin.getTglVaksin()));
                client.insertRecord(tableVaksin, rowKey, "main", "batchVaksin", safeString(vaksin.getBatchVaksin()));
                client.insertRecord(tableVaksin, rowKey, "main", "vaksinKe", safeString(vaksin.getVaksinKe()));
                client.insertRecord(tableVaksin, rowKey, "main", "tglPendataan", safeString(vaksin.getTglPendataan()));

                // if (namaVaksin.getJenisVaksin() != null) {
                // JenisVaksin jenisVaksin = namaVaksin.getJenisVaksin();
                // client.insertRecord(tableNamaVaksin, rowKey, "main", "idJenisVaksin",
                // safeString(jenisVaksin.getIdJenisVaksin()));
                // client.insertRecord(tableNamaVaksin, rowKey, "main", "namaJenisVaksin",
                // safeString(jenisVaksin.getNamaVaksin()));
                // }

                if (vaksin.getNamaVaksin() != null) {
                    NamaVaksin namaVaksin = vaksin.getNamaVaksinTable();
                    client.insertRecord(tableVaksin, rowKey, "main", "idNamaVaksin",
                            safeString(namaVaksin.getIdNamaVaksin()));
                    client.insertRecord(tableVaksin, rowKey, "main", "namaNamaVaksin",
                            safeString(namaVaksin.getNamaVaksin()));
                }

                if (vaksin.getJenisVaksin() != null) {
                    JenisVaksin jenisVaksin = vaksin.getJenisVaksinTable();
                    client.insertRecord(tableVaksin, rowKey, "main", "idJenisVaksin",
                            safeString(jenisVaksin.getIdJenisVaksin()));
                    client.insertRecord(tableVaksin, rowKey, "main", "namaJenisVaksin",
                            safeString(jenisVaksin.getNamaVaksin()));
                }

                if (vaksin.getPeternak() != null) {
                    Peternak peternak = vaksin.getPeternak();
                    client.insertRecord(tableVaksin, rowKey, "main", "idPeternak",
                            safeString(peternak.getIdPeternak()));
                    client.insertRecord(tableVaksin, rowKey, "main", "nikPeternak",
                            safeString(peternak.getNikPeternak()));
                    client.insertRecord(tableVaksin, rowKey, "main", "namaPeternak",
                            safeString(peternak.getNamaPeternak()));
                }

                if (vaksin.getPetugas() != null) {
                    Petugas petugas = vaksin.getPetugas();
                    client.insertRecord(tableVaksin, rowKey, "main", "nikPetugas", safeString(petugas.getNikPetugas()));
                    client.insertRecord(tableVaksin, rowKey, "main", "namaPetugas",
                            safeString(petugas.getNamaPetugas()));
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
}

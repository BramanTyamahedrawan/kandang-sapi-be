package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;

import com.ternak.sapi.model.RumpunHewan;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.*;

public class JenisVaksinRepository {
    private String safeString(String input) {
        return input == null ? "" : input;
    }

    Configuration conf = HBaseConfiguration.create();
    String tableName = "jenisvaksindev";

    public List<JenisVaksin> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idJenisVaksin", "idJenisVaksin");
        columnMapping.put("jenis", "jenis");
        columnMapping.put("deskripsi", "deskripsi");

        return client.showListTable(tableUsers.toString(), columnMapping, JenisVaksin.class, size);
    }

    public List<JenisVaksin> findAllByUserID(String userID, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idJenisVaksin", "idJenisVaksin");
        columnMapping.put("jenis", "jenis");
        columnMapping.put("deskripsi", "deskripsi");

        return client.getDataListByColumn(tableUsers.toString(), columnMapping, "user", "id", userID, JenisVaksin.class,
                size);
    }

    public JenisVaksin save(JenisVaksin jenisVaksin) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = jenisVaksin.getIdJenisVaksin();
        TableName tableJenisVaksin = TableName.valueOf(tableName);
        client.insertRecord(tableJenisVaksin, rowKey, "main", "idJenisVaksin", rowKey);
        if (jenisVaksin.getJenis() != null) {
            client.insertRecord(tableJenisVaksin, rowKey, "main", "jenis", jenisVaksin.getJenis());
        }
        if (jenisVaksin.getDeskripsi() != null) {
            client.insertRecord(tableJenisVaksin, rowKey, "main", "deskripsi", jenisVaksin.getDeskripsi());
        }
        client.insertRecord(tableJenisVaksin, rowKey, "detail", "created_by", "Polinema");
        return jenisVaksin;
    }

    public List<JenisVaksin> saveAll(List<JenisVaksin> jenisVaksinList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableJenisVaksin = TableName.valueOf(tableName);

        System.out.println("Memulai penyimpanan data ke HBase...");
        List<String> failedRows = new ArrayList<>();

        for (JenisVaksin jenisVaksin : jenisVaksinList) {
            try {
                String rowKey = safeString(jenisVaksin.getIdJenisVaksin());

                client.insertRecord(tableJenisVaksin, rowKey, "main", "idJenisVaksin",
                        safeString(jenisVaksin.getIdJenisVaksin()));
                client.insertRecord(tableJenisVaksin, rowKey, "main", "jenis",
                        safeString(jenisVaksin.getJenis()));
                client.insertRecord(tableJenisVaksin, rowKey, "main", "deskripsi",
                        safeString(jenisVaksin.getDeskripsi()));
                client.insertRecord(tableJenisVaksin, rowKey, "detail", "created_by", "Polinema");

                System.out.println(
                        "Berhasil menyimpan Jenis Vaksin: " + jenisVaksin.getIdJenisVaksin());
            } catch (Exception e) {
                failedRows.add(jenisVaksin.getIdJenisVaksin());
                System.err.println(
                        "Failed to insert record for ID: " + jenisVaksin.getIdJenisVaksin() + ", Error: "
                                + e.getMessage());
            }
        }

        if (!failedRows.isEmpty()) {
            throw new IOException("Failed to save records for Jenis Vaksin: " + String.join(", ", failedRows));
        }

        return jenisVaksinList;
    }

    public JenisVaksin update(String jenisvaksinId, JenisVaksin jenisVaksin) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableJenisVaksin = TableName.valueOf(tableName);
        if (jenisVaksin.getJenis() != null) {
            client.insertRecord(tableJenisVaksin, jenisvaksinId, "main", "jenis", jenisVaksin.getJenis());
        }
        if (jenisVaksin.getDeskripsi() != null) {
            client.insertRecord(tableJenisVaksin, jenisvaksinId, "main", "deskripsi", jenisVaksin.getDeskripsi());
        }
        client.insertRecord(tableJenisVaksin, jenisvaksinId, "detail", "updated_by", "Polinema");
        return jenisVaksin;
    }

    public boolean deleteById(String jenisvaksinId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, jenisvaksinId);
        return true;
    }

    public JenisVaksin findById(String idJenisVaksin) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableJenisVaksin = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idJenisVaksin", "idJenisVaksin");
        columnMapping.put("jenis", "jenis");
        columnMapping.put("deskripsi", "deskripsi");

        JenisVaksin jenisVaksin = client.getDataByColumn(tableJenisVaksin.toString(), columnMapping, "main",
                "idJenisVaksin", idJenisVaksin, JenisVaksin.class);
        System.out.println("Jenis vaksi ditemukan " + jenisVaksin);
        return jenisVaksin.getIdJenisVaksin() != null ? jenisVaksin : null;
    }

    public JenisVaksin findByJenisVaksin(String jenis) throws IOException {

        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableJenisVaksin = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idJenisVaksin", "idJenisVaksin");

        columnMapping.put("jenis", "jenis");
        columnMapping.put("deskripsi", "deskripsi");

        JenisVaksin jenisVaksin = client.getDataByColumn(tableJenisVaksin.toString(), columnMapping, "main", "jenis",
                jenis,
                JenisVaksin.class);
        return jenisVaksin;

    }

    public boolean existsByJenis(String jenis) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableJenisVaksin = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("jenis", "jenis");

        JenisVaksin jenisVaksin = client.getDataByColumn(tableJenisVaksin.toString(), columnMapping, "main", "jenis",
                jenis, JenisVaksin.class);
        return jenisVaksin.getJenis() != null;
    }

}

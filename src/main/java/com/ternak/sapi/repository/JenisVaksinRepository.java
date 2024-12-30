package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.model.Petugas;

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
                client.insertRecord(tableJenisVaksin, rowKey, "main", "namaVaksin",
                        safeString(jenisVaksin.getNamaVaksin()));
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

    public JenisVaksin findById(String idJenisVaksin) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableJenisVaksin = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idJenisVaksin", "idJenisVaksin");
        columnMapping.put("namaVaksin", "namaVaksin");
        columnMapping.put("deskripsi", "deskripsi");

        return client.showDataTable(tableJenisVaksin.toString(), columnMapping, idJenisVaksin, JenisVaksin.class);
    }

}

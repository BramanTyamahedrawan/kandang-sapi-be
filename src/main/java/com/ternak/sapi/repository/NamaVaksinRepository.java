package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.JenisVaksin;
import com.ternak.sapi.model.NamaVaksin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.*;

public class NamaVaksinRepository {
    private String safeString(String input) {
        return input == null ? "" : input;
    }

    Configuration conf = HBaseConfiguration.create();
    String tableName = "namavaksindev";

    public List<NamaVaksin> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idNamaVaksin", "idNamaVaksin");
        columnMapping.put("jenisVaksin", "jenisVaksin");
        columnMapping.put("nama", "nama");
        columnMapping.put("deskripsi", "deskripsi");

        return client.showListTable(tableUsers.toString(), columnMapping, NamaVaksin.class, size);
    }

    public List<NamaVaksin> findAllByUserID(String userID, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idNamaVaksin", "idNamaVaksin");
        columnMapping.put("jenisVaksin", "jenisVaksin");
        columnMapping.put("nama", "nama");
        columnMapping.put("deskripsi", "deskripsi");

        return client.getDataListByColumn(tableUsers.toString(), columnMapping, "user", "id", userID, NamaVaksin.class,
                size);
    }

    public List<NamaVaksin> saveAll(List<NamaVaksin> namaVaksinList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableNamaVaksin = TableName.valueOf(tableName);

        System.out.println("Memulai penyimpanan data ke HBase...");
        List<String> failedRows = new ArrayList<>();

        for (NamaVaksin namaVaksin : namaVaksinList) {
            try {

                if (namaVaksin.getJenisVaksin() != null) {
                    JenisVaksin jenisVaksin = namaVaksin.getJenisVaksin();
                    client.insertRecord(tableNamaVaksin, safeString(namaVaksin.getIdNamaVaksin()), "jenisVaksin",
                            "idJenisVaksin",
                            safeString(jenisVaksin.getIdJenisVaksin()));
                    client.insertRecord(tableNamaVaksin, safeString(namaVaksin.getIdNamaVaksin()), "jenisVaksin",
                            "jenis",
                            safeString(jenisVaksin.getJenis()));
                    client.insertRecord(tableNamaVaksin, safeString(namaVaksin.getIdNamaVaksin()), "jenisVaksin",
                            "deskripsi",
                            safeString(jenisVaksin.getDeskripsi()));
                }

                String rowKey = safeString(namaVaksin.getIdNamaVaksin());

                client.insertRecord(tableNamaVaksin, rowKey, "main", "idNamaVaksin",
                        safeString(namaVaksin.getIdNamaVaksin()));
                client.insertRecord(tableNamaVaksin, rowKey, "main", "nama",
                        safeString(namaVaksin.getNama()));
                client.insertRecord(tableNamaVaksin, rowKey, "main", "deskripsi",
                        safeString(namaVaksin.getDeskripsi()));

                client.insertRecord(tableNamaVaksin, rowKey, "detail", "created_by", "Polinema");

                System.out.println("Berhasil menyimpan Nama Vaksin: " + namaVaksin.getIdNamaVaksin());

            } catch (Exception e) {
                failedRows.add(namaVaksin.getIdNamaVaksin());
                System.err.println(
                        "Failed to insert record for nama vaksin: " + namaVaksin.getIdNamaVaksin() + ", Error: "
                                + e.getMessage());
            }
        }

        if (!failedRows.isEmpty()) {
            throw new IOException("Failed to save records for NIKs: " + String.join(", ", failedRows));
        }

        return namaVaksinList;
    }

    public NamaVaksin findById(String idNamaVaksin) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableNamaVaksin = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idNamaVaksin", "idNamaVaksin");
        columnMapping.put("jenisVaksin", "jenisVaksin");
        columnMapping.put("nama", "nama");
        columnMapping.put("deskripsi", "deskripsi");

        return client.showDataTable(tableNamaVaksin.toString(), columnMapping, idNamaVaksin, NamaVaksin.class);
    }

    public NamaVaksin findByNamaVaksin(String namaVaksin) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableNamaVaksin = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idNamaVaksin", "idNamaVaksin");
        columnMapping.put("jenisVaksin", "jenisVaksin");
        columnMapping.put("nama", "nama");
        columnMapping.put("deskripsi", "deskripsi");

        NamaVaksin nama = client.getDataByColumn(tableNamaVaksin.toString(), columnMapping, "main", "nama",
                namaVaksin,
                NamaVaksin.class);
        return nama.getNama() != null ? nama : null;
    }

    public NamaVaksin findNamaVaksinByJenisVaksin(String namaVaksinId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idNamaVaksin", "idNamaVaksin");
        columnMapping.put("jenisVaksin", "jenisVaksin");
        columnMapping.put("nama", "nama");
        columnMapping.put("deskripsi", "deskripsi");

        NamaVaksin namaVaksin = client.getDataByColumn(tableUsers.toString(), columnMapping, "main", "nama",
                namaVaksinId, NamaVaksin.class);

        return namaVaksin;
    }

}

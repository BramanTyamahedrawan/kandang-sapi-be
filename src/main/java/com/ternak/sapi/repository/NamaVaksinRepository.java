package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
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

    public List<NamaVaksin> saveAll(List<NamaVaksin> namaVaksinList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableNamaVaksin = TableName.valueOf(tableName);

        System.out.println("Memulai penyimpanan data ke HBase...");
        List<String> failedRows = new ArrayList<>();

        for (NamaVaksin namaVaksin : namaVaksinList) {
            try {
                String rowKey = safeString(namaVaksin.getIdNamaVaksin());

                client.insertRecord(tableNamaVaksin, rowKey, "main", "namaVaksin",
                        safeString(namaVaksin.getNamaVaksin()));
                client.insertRecord(tableNamaVaksin, rowKey, "main", "deskripsi",
                        safeString(namaVaksin.getDeskripsi()));

                if (namaVaksin.getJenisVaksin() != null) {
                    JenisVaksin jenisVaksin = namaVaksin.getJenisVaksin();
                    client.insertRecord(tableNamaVaksin, rowKey, "main", "idJenisVaksin",
                            safeString(jenisVaksin.getIdJenisVaksin()));
                    client.insertRecord(tableNamaVaksin, rowKey, "main", "namaJenisVaksin",
                            safeString(jenisVaksin.getNamaVaksin()));
                }

                client.insertRecord(tableNamaVaksin, rowKey, "detail", "created_by", "Polinema");

                System.out.println("Berhasil menyimpan Nama Vaksin: " + namaVaksin.getIdNamaVaksin());

            } catch (Exception e) {
                failedRows.add(namaVaksin.getIdNamaVaksin());
                System.err.println(
                        "Failed to insert record for NIK: " + namaVaksin.getIdNamaVaksin() + ", Error: "
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
        columnMapping.put("nama", "nama");
        columnMapping.put("deskripsi", "deskripsi");

        return client.showDataTable(tableNamaVaksin.toString(), columnMapping, idNamaVaksin, NamaVaksin.class);
    }

}

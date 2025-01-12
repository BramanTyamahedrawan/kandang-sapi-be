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
import com.ternak.sapi.model.TujuanPemeliharaan;

public class TujuanPemeliharaanRepository {

    private String safeString(String input) {
        return input == null ? "" : input;
    }

    Configuration conf = HBaseConfiguration.create();
    String tableName = "tujuanpemeliharaandev";

    public List<TujuanPemeliharaan> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idTujuanPemeliharaan", "idTujuanPemeliharaan");
        columnMapping.put("tujuanPemeliharaan", "tujuanPemeliharaan");
        columnMapping.put("deskripsi", "deskripsi");
        return client.showListTable(tableUsers.toString(), columnMapping, TujuanPemeliharaan.class, size);
    }

    public TujuanPemeliharaan findByTujuan(String tujuan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableTujuan = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("idTujuanPemeliharaan", "idTujuanPemeliharaan");
        columnMapping.put("tujuanPemeliharaan", "tujuanPemeliharaan");
        columnMapping.put("deskrisi", "deskripsi");
        TujuanPemeliharaan tujuanPemeliharaan = client.getDataByColumn(tableTujuan.toString(), columnMapping, "main",
                "tujuanPemeliharaan", tujuan, TujuanPemeliharaan.class);
        return tujuanPemeliharaan.getTujuanPemeliharaan() != null ? tujuanPemeliharaan : null;
    }

    public List<TujuanPemeliharaan> saveAll(List<TujuanPemeliharaan> tujuanPemeliharaanList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableTujuanPemeliharaan = TableName.valueOf(tableName);

        System.out.println("Memulai penyimpanan data ke HBase...");
        List<String> failedRows = new ArrayList<>();

        for (TujuanPemeliharaan tujuanPemeliharaan : tujuanPemeliharaanList) {
            try {
                String rowKey = safeString(tujuanPemeliharaan.getIdTujuanPemeliharaan());

                client.insertRecord(tableTujuanPemeliharaan, rowKey, "main", "idTujuanPemeliharaan",
                        safeString(tujuanPemeliharaan.getIdTujuanPemeliharaan()));
                client.insertRecord(tableTujuanPemeliharaan, rowKey, "main", "tujuanPemeliharaan",
                        safeString(tujuanPemeliharaan.getTujuanPemeliharaan()));
                client.insertRecord(tableTujuanPemeliharaan, rowKey, "main", "deskripsi",
                        safeString(tujuanPemeliharaan.getDeskripsi()));
                client.insertRecord(tableTujuanPemeliharaan, rowKey, "detail", "created_by", "Polinema");

                System.out.println(
                        "Berhasil menyimpan Tujuan Pemeliharaan: " + tujuanPemeliharaan.getIdTujuanPemeliharaan());
            } catch (Exception e) {
                failedRows.add(tujuanPemeliharaan.getIdTujuanPemeliharaan());
                System.err.println(
                        "Failed to insert record for ID: " + tujuanPemeliharaan.getIdTujuanPemeliharaan() + ", Error: "
                                + e.getMessage());
            }
        }

        if (!failedRows.isEmpty()) {
            throw new IOException("Failed to save records for Jenis Hewan: " + String.join(", ", failedRows));
        }

        return tujuanPemeliharaanList;
    }

    public TujuanPemeliharaan saveImport(TujuanPemeliharaan tujuanPemeliharaan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = tujuanPemeliharaan.getIdTujuanPemeliharaan();
        TableName tableTujuanPemeliharaan = TableName.valueOf(tableName);
        client.insertRecord(tableTujuanPemeliharaan, rowKey, "main", "idTujuanPemeliharaan",
                safeString(tujuanPemeliharaan.getIdTujuanPemeliharaan()));
        client.insertRecord(tableTujuanPemeliharaan, rowKey, "main", "tujuanPemeliharaan",
                safeString(tujuanPemeliharaan.getTujuanPemeliharaan()));
        client.insertRecord(tableTujuanPemeliharaan, rowKey, "main", "deskripsi",
                safeString(tujuanPemeliharaan.getDeskripsi()));
        client.insertRecord(tableTujuanPemeliharaan, rowKey, "detail", "created_by", "Polinema");
        return tujuanPemeliharaan;
    }
}

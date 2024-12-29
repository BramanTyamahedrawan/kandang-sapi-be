package com.ternak.sapi.repository;

import com.ternak.sapi.controller.JenisHewanController;
import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.TujuanPemeliharaan;
import com.ternak.sapi.model.JenisHewan;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.*;

public class TujuanPemeliharaanRepository {

    private String safeString(String input) {
        return input == null ? "" : input;
    }

    Configuration conf = HBaseConfiguration.create();
    String tableName = "tujuanpemeliharaandev";

    public List<TujuanPemeliharaan> saveAll(List<TujuanPemeliharaan> tujuanPemeliharaanList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableTujuanPemeliharaan = TableName.valueOf(tableName);

        System.out.println("Memulai penyimpanan data ke HBase...");
        List<String> failedRows = new ArrayList<>();

        for (TujuanPemeliharaan tujuanPemeliharaan : tujuanPemeliharaanList) {
            try {
                String rowKey = safeString(tujuanPemeliharaan.getIdTujuanPemeliharaan());

                client.insertRecord(tableTujuanPemeliharaan, rowKey, "main", "idJenisHewan",
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
}

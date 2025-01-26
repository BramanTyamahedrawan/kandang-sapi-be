
package com.ternak.sapi.repository;

import com.ternak.sapi.controller.JenisHewanController;
import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.*;
import com.ternak.sapi.model.JenisHewan;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.*;

public class JenisHewanRepository {
    Configuration conf = HBaseConfiguration.create();
    String tableName = "jenishewandev";
    // JenisHewanController departmentController = new JenisHewanController();

    public List<JenisHewan> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idJenisHewan", "idJenisHewan");
        columnMapping.put("jenis", "jenis");
        columnMapping.put("deskripsi", "deskripsi");
        return client.showListTable(tableUsers.toString(), columnMapping, JenisHewan.class, size);
    }

    public JenisHewan save(JenisHewan jenishewan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = jenishewan.getIdJenisHewan();
        TableName tableJenisHewan = TableName.valueOf(tableName);
        client.insertRecord(tableJenisHewan, rowKey, "main", "idJenisHewan", rowKey);
        if (jenishewan.getJenis() != null) {
            client.insertRecord(tableJenisHewan, rowKey, "main", "jenis", jenishewan.getJenis());
        }
        if (jenishewan.getDeskripsi() != null) {
            client.insertRecord(tableJenisHewan, rowKey, "main", "deskripsi", jenishewan.getDeskripsi());
        }
        client.insertRecord(tableJenisHewan, rowKey, "detail", "created_by", "Polinema");
        return jenishewan;
    }

    public List<JenisHewan> saveAll(List<JenisHewan> jenishewanList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableJenisHewan = TableName.valueOf(tableName);

        System.out.println("Memulai penyimpanan data ke HBase...");
        List<String> failedRows = new ArrayList<>();

        for (JenisHewan jenisHewan : jenishewanList) {
            try {
                String rowKey = safeString(jenisHewan.getIdJenisHewan());

                client.insertRecord(tableJenisHewan, rowKey, "main", "idJenisHewan",
                        safeString(jenisHewan.getIdJenisHewan()));
                if (jenisHewan.getJenis() != null) {
                    client.insertRecord(tableJenisHewan, rowKey, "main", "jenis", safeString(jenisHewan.getJenis()));
                }
                client.insertRecord(tableJenisHewan, rowKey, "main", "deskripsi",
                        safeString(jenisHewan.getDeskripsi()));
                client.insertRecord(tableJenisHewan, rowKey, "detail", "created_by", "Polinema");

                System.out.println("Berhasil menyimpan Jenis Hewan: " + jenisHewan.getIdJenisHewan());
            } catch (Exception e) {
                failedRows.add(jenisHewan.getIdJenisHewan());
                System.err.println(
                        "Failed to insert record for ID: " + jenisHewan.getIdJenisHewan() + ", Error: "
                                + e.getMessage());
            }
        }

        if (!failedRows.isEmpty()) {
            throw new IOException("Failed to save records for Jenis Hewan: " + String.join(", ", failedRows));
        }

        return jenishewanList;
    }

    public JenisHewan saveByJenis(JenisHewan jenis) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = jenis.getIdJenisHewan();
        TableName tableJenisHewan = TableName.valueOf(tableName);

        client.insertRecord(tableJenisHewan, rowKey, "main", "idJenisHewan", rowKey);
        if (jenis.getJenis() != null) {
            client.insertRecord(tableJenisHewan, rowKey, "main", "jenis", jenis.getJenis());
        }
        if (jenis.getDeskripsi() != null) {
            client.insertRecord(tableJenisHewan, rowKey, "main", "deskripsi", jenis.getDeskripsi());
        }
        client.insertRecord(tableJenisHewan, rowKey, "detail", "created_by", "Polinema");
        return jenis;
    }

    // Utility untuk memastikan nilai string tidak null
    private String safeString(String value) {
        return value != null ? value : "";
    }

    public JenisHewan findById(String idJenisHewan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableJenisHewan = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idJenisHewan", "idJenisHewan");
        columnMapping.put("jenis", "jenis");
        columnMapping.put("deskripsi", "deskripsi");

        JenisHewan jenisHewan = client.getDataByColumn(tableJenisHewan.toString(), columnMapping, "main",
                "idJenisHewan",
                idJenisHewan, JenisHewan.class);

        System.out.println("Jenis Hewan ditemukan " + jenisHewan);
        return jenisHewan.getIdJenisHewan() != null ? jenisHewan : null;
    }

    public JenisHewan findByJenis(String jenis) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableJenisHewan = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("idJenisHewan", "idJenisHewan");
        columnMapping.put("jenis", "jenis");
        columnMapping.put("deskripsi", "deskripsi");

        JenisHewan jenisHewan = client.getDataByColumn(tableJenisHewan.toString(), columnMapping, "main", "jenis",
                jenis, JenisHewan.class);
        return jenisHewan.getJenis() != null ? jenisHewan : null;
    }

    public JenisHewan update(String jenishewanId, JenisHewan jenishewan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableJenisHewan = TableName.valueOf(tableName);
        if (jenishewan.getJenis() != null) {
            client.insertRecord(tableJenisHewan, jenishewanId, "main", "jenis", jenishewan.getJenis());
        }
        if (jenishewan.getDeskripsi() != null) {
            client.insertRecord(tableJenisHewan, jenishewanId, "main", "deskripsi", jenishewan.getDeskripsi());
        }

        client.insertRecord(tableJenisHewan, jenishewanId, "detail", "created_by", "Polinema");
        return jenishewan;
    }

    public boolean deleteById(String jenishewanId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, jenishewanId);
        return true;
    }

    public boolean existsByJenis(String jenis) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableJenisHewan = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("jenis", "jenis");

        JenisHewan jenisHewan = client.getDataByColumn(tableJenisHewan.toString(), columnMapping, "main", "jenis",
                jenis, JenisHewan.class);
        return jenisHewan.getJenis() != null; // True jika sudah ada jenis yang sama
    }
}

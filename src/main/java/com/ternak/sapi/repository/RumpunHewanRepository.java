
package com.ternak.sapi.repository;

import com.ternak.sapi.controller.RumpunHewanController;
import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.model.RumpunHewan;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.*;

public class RumpunHewanRepository {
    Configuration conf = HBaseConfiguration.create();
    String tableName = "rumpunhewandev";
    // RumpunHewanController departmentController = new RumpunHewanController();

    public List<RumpunHewan> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idRumpunHewan", "idRumpunHewan");
        columnMapping.put("rumpun", "rumpun");
        columnMapping.put("deskripsi", "deskripsi");
        return client.showListTable(tableUsers.toString(), columnMapping, RumpunHewan.class, size);
    }

    public RumpunHewan save(RumpunHewan rumpunhewan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = rumpunhewan.getIdRumpunHewan();
        TableName tableRumpunHewan = TableName.valueOf(tableName);

        if (!existsByRumpun(rumpunhewan.getRumpun())) {
            client.insertRecord(tableRumpunHewan, rowKey, "main", "idRumpunHewan", rowKey);
            if (rumpunhewan.getRumpun() != null) {
                client.insertRecord(tableRumpunHewan, rowKey, "main", "rumpun", rumpunhewan.getRumpun());
            }
            if (rumpunhewan.getDeskripsi() != null) {
                client.insertRecord(tableRumpunHewan, rowKey, "main", "deskripsi", rumpunhewan.getDeskripsi());
            }
            client.insertRecord(tableRumpunHewan, rowKey, "detail", "created_by", "Polinema");
        }
        return rumpunhewan;
    }

    public List<RumpunHewan> saveAll(List<RumpunHewan> rumpunhewanList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableRumpunHewan = TableName.valueOf(tableName);

        System.out.println("Memulai penyimpanan data ke HBase...");
        List<String> failedRows = new ArrayList<>();

        for (RumpunHewan rumpunhewan : rumpunhewanList) {
            try {
                String rowKey = safeString(rumpunhewan.getIdRumpunHewan());

                client.insertRecord(tableRumpunHewan, rowKey, "main", "idRumpunHewan",
                        safeString(rumpunhewan.getIdRumpunHewan()));
                client.insertRecord(tableRumpunHewan, rowKey, "main", "rumpun",
                        safeString(rumpunhewan.getRumpun()));
                client.insertRecord(tableRumpunHewan, rowKey, "main", "deskripsi",
                        safeString(rumpunhewan.getDeskripsi()));
                client.insertRecord(tableRumpunHewan, rowKey, "detail", "created_by", "Polinema");

                System.out.println(
                        "Berhasil menyimpan Rumpun Hewan: " + rumpunhewan.getIdRumpunHewan());
            } catch (Exception e) {
                failedRows.add(rumpunhewan.getIdRumpunHewan());
                System.err.println(
                        "Failed to insert record for ID: " + rumpunhewan.getIdRumpunHewan() + ", Error: "
                                + e.getMessage());
            }
        }

        if (!failedRows.isEmpty()) {
            throw new IOException("Failed to save records for Rumpun Hewan: " + String.join(", ", failedRows));
        }

        return rumpunhewanList;
    }

    public RumpunHewan saveByRumpun(RumpunHewan rumpun) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = rumpun.getRumpun();

        TableName tableRumpun = TableName.valueOf(tableName);
        client.insertRecord(tableRumpun, rowKey, "main", "idRumpunHewan", rumpun.getIdRumpunHewan());
        client.insertRecord(tableRumpun, rowKey, "main", "rumpun", rumpun.getRumpun());
        client.insertRecord(tableRumpun, rowKey, "main", "deskripsi", rumpun.getDeskripsi());
        client.insertRecord(tableRumpun, rowKey, "detail", "created_by", "Polinema");
        return rumpun;
    }

    private String safeString(String value) {
        return value != null ? value : "";
    }

    public RumpunHewan findById(String idHewan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idRumpunHewan", "idRumpunHewan");
        columnMapping.put("rumpun", "rumpun");
        columnMapping.put("deskripsi", "deskripsi");

        return client.showDataTable(tableUsers.toString(), columnMapping, idHewan, RumpunHewan.class);
    }

    public RumpunHewan update(String rumpunhewanId, RumpunHewan rumpunhewan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableRumpunHewan = TableName.valueOf(tableName);
        if (rumpunhewan.getRumpun() != null) {
            client.insertRecord(tableRumpunHewan, rumpunhewanId, "main", "rumpun", rumpunhewan.getRumpun());
        }
        if (rumpunhewan.getDeskripsi() != null) {
            client.insertRecord(tableRumpunHewan, rumpunhewanId, "main", "deskripsi",rumpunhewan.getDeskripsi());
        }

        client.insertRecord(tableRumpunHewan, rumpunhewanId, "detail", "created_by", "Polinema");
        return rumpunhewan;
    }

    public RumpunHewan findByRumpun(String rumpun) throws IOException {
        if (rumpun == null || rumpun.isEmpty()) {
            System.err.println("Parameter rumpun tidak boleh null atau kosong.");
            return null;
        }

        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableRumpun = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        columnMapping.put("idRumpunHewan", "idRumpunHewan");
        columnMapping.put("rumpun", "rumpun");
        columnMapping.put("deskripsi", "deskripsi");

        RumpunHewan rumpunHewan = client.getDataByColumn(tableRumpun.toString(), columnMapping, "main", "rumpun",
                rumpun, RumpunHewan.class);
        System.out.println("Mencari data Rumpun Hewan dengan rumpun: " + rumpun);

        if (rumpunHewan == null || rumpunHewan.getRumpun() == null) {
            System.out.println("Rumpun Hewan tidak ditemukan untuk rumpun: " + rumpun);
            return null;
        }

        System.out.println("Rumpun Hewan ditemukan by rumpun: " + rumpunHewan);

        return rumpunHewan;
    }

    public boolean deleteById(String rumpunhewanId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, rumpunhewanId);
        return true;
    }

    public boolean existsByRumpun(String rumpun) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableRumpunHewan = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("rumpun", "rumpun");

        RumpunHewan rumpunHewan = client.getDataByColumn(tableRumpunHewan.toString(), columnMapping, "main", "rumpun",
                rumpun, RumpunHewan.class);
        return rumpunHewan.getRumpun() != null; // True jika rumpun sudah ada
    }
}

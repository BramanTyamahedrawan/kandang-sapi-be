package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Pkb;
import com.ternak.sapi.model.User;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.*;

public class PkbRepository {
    Configuration conf = HBaseConfiguration.create();
    String tableName = "pkbdev";

    public List<Pkb> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tablePkb = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalPkb", "tanggalPkb");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("umurKebuntingan", "umurKebuntingan");

        return client.showListTable(tablePkb.toString(), columnMapping, Pkb.class, size);
    }

    public Pkb save(Pkb pkb) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = pkb.getIdKejadian();

        TableName tablePkb = TableName.valueOf(tableName);
        client.insertRecord(tablePkb, rowKey, "main", "idKejadian", rowKey);
        if (pkb.getTanggalPkb() != null) {
        client.insertRecord(tablePkb, rowKey, "main", "tanggalPkb", pkb.getTanggalPkb());
        }
        if (pkb.getSpesies() != null) {
        client.insertRecord(tablePkb, rowKey, "main", "spesies", pkb.getSpesies());
        }
        if (pkb.getUmurKebuntingan() != null) {
        client.insertRecord(tablePkb, rowKey, "main", "umurKebuntingan", pkb.getUmurKebuntingan());
        }
        client.insertRecord(tablePkb, rowKey, "peternak", "idPeternak", pkb.getPeternak().getIdPeternak());
        client.insertRecord(tablePkb, rowKey, "peternak", "nikPeternak", pkb.getPeternak().getNikPeternak());
        client.insertRecord(tablePkb, rowKey, "peternak", "namaPeternak", pkb.getPeternak().getNamaPeternak());
        client.insertRecord(tablePkb, rowKey, "peternak", "lokasi", pkb.getPeternak().getLokasi());
        client.insertRecord(tablePkb, rowKey, "petugas", "nikPetugas", pkb.getPetugas().getNikPetugas());
        client.insertRecord(tablePkb, rowKey, "petugas", "namaPetugas", pkb.getPetugas().getNamaPetugas());
        client.insertRecord(tablePkb, rowKey, "hewan", "kodeEartagNasional", pkb.getHewan().getKodeEartagNasional());
        client.insertRecord(tablePkb, rowKey, "detail", "created_by", "Polinema");
        return pkb;
    }

    public Pkb findPkbById(String pkbId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tablePkb = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalPkb", "tanggalPkb");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("umurKebuntingan", "umurKebuntingan");

        return client.showDataTable(tablePkb.toString(), columnMapping, pkbId, Pkb.class);
    }

    public List<Pkb> findPkbByPeternak(String peternakId, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalPkb", "tanggalPkb");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("umurKebuntingan", "umurKebuntingan");

        List<Pkb> pkb = client.getDataListByColumn(table.toString(), columnMapping, "peternak", "nikPeternak", peternakId, Pkb.class, size);
        return pkb;
    }
    
    public List<Pkb> findPkbByPetugas(String petugasId, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalPkb", "tanggalPkb");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("umurKebuntingan", "umurKebuntingan");
        
        return client.getDataListByColumn(table.toString(), columnMapping, "petugas", "nikPetugas", petugasId, Pkb.class, size);
    }
    
    public List<Pkb> findPkbByHewan(String hewanId, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalPkb", "tanggalPkb");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("umurKebuntingan", "umurKebuntingan");

        return client.getDataListByColumn(table.toString(), columnMapping, "hewan", "kodeEartagNasional", hewanId, Pkb.class, size);
    }

    public List<Pkb> findAllById(List<String> pkbIds) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalPkb", "tanggalPkb");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("umurKebuntingan", "umurKebuntingan");

        List<Pkb> pkbs = new ArrayList<>();
        for (String pkbId : pkbIds) {
            Pkb pkb = client.showDataTable(table.toString(), columnMapping, pkbId, Pkb.class);
            if (pkb != null) {
                pkbs.add(pkb);
            }
        }

        return pkbs;
    }

    public Pkb update(String pkbId, Pkb pkb) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tablePkb = TableName.valueOf(tableName);
        if (pkb.getTanggalPkb() != null) {
        client.insertRecord(tablePkb, pkbId, "main", "tanggalPkb", pkb.getTanggalPkb());
        }
        if (pkb.getSpesies() != null) {
        client.insertRecord(tablePkb, pkbId, "main", "spesies", pkb.getSpesies());
        }
        if (pkb.getUmurKebuntingan() != null) {
        client.insertRecord(tablePkb, pkbId, "main", "umurKebuntingan", pkb.getUmurKebuntingan());
        }
        client.insertRecord(tablePkb, pkbId, "peternak", "idPeternak", pkb.getPeternak().getIdPeternak());
        client.insertRecord(tablePkb, pkbId, "peternak", "nikPeternak", pkb.getPeternak().getNikPeternak());
        client.insertRecord(tablePkb, pkbId, "peternak", "namaPeternak", pkb.getPeternak().getNamaPeternak());
        client.insertRecord(tablePkb, pkbId, "peternak", "lokasi", pkb.getPeternak().getLokasi());
        client.insertRecord(tablePkb, pkbId, "petugas", "nikPetugas", pkb.getPetugas().getNikPetugas());
        client.insertRecord(tablePkb, pkbId, "petugas", "namaPetugas", pkb.getPetugas().getNamaPetugas());
        client.insertRecord(tablePkb, pkbId, "hewan", "kodeEartagNasional", pkb.getHewan().getKodeEartagNasional());
        client.insertRecord(tablePkb, pkbId, "detail", "created_by", "Polinema");
        return pkb;
    }

    public boolean deleteById(String pkbId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, pkbId);
        return true;
    }
}

package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Pengobatan;
import com.ternak.sapi.model.User;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.*;

public class PengobatanRepository {
    Configuration conf = HBaseConfiguration.create();
    String tableName = "pengobatandev";

    public List<Pengobatan> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tablePengobatan = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idKasus", "idKasus");
        columnMapping.put("tanggalPengobatan", "tanggalPengobatan");
        columnMapping.put("tanggalKasus", "tanggalKasus");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("namaInfrastruktur", "namaInfrastruktur");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("dosis", "dosis");
        columnMapping.put("sindrom", "sindrom");
        columnMapping.put("diagnosaBanding", "diagnosaBanding");

        return client.showListTable(tablePengobatan.toString(), columnMapping, Pengobatan.class, size);
    }

    public Pengobatan save(Pengobatan pengobatan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = pengobatan.getIdKasus();

        TableName tablePengobatan = TableName.valueOf(tableName);
        client.insertRecord(tablePengobatan, rowKey, "main", "idKasus", rowKey);
        if (pengobatan.getTanggalPengobatan() != null) {
        client.insertRecord(tablePengobatan, rowKey, "main", "tanggalPengobatan", pengobatan.getTanggalPengobatan());
        }
        if (pengobatan.getTanggalKasus() != null) {
        client.insertRecord(tablePengobatan, rowKey, "main", "tanggalKasus", pengobatan.getTanggalKasus());
        }
        if (pengobatan.getNamaInfrastruktur() != null) {
        client.insertRecord(tablePengobatan, rowKey, "main", "namaInfrastruktur", pengobatan.getNamaInfrastruktur());
        }
        if (pengobatan.getLokasi() != null) {
        client.insertRecord(tablePengobatan, rowKey, "main", "lokasi", pengobatan.getLokasi());
        }
        if (pengobatan.getDosis() != null) {
        client.insertRecord(tablePengobatan, rowKey, "main", "dosis", pengobatan.getDosis());
        }
        if (pengobatan.getSindrom() != null) {
        client.insertRecord(tablePengobatan, rowKey, "main", "sindrom", pengobatan.getSindrom());
        }
        if (pengobatan.getDiagnosaBanding() != null) {
        client.insertRecord(tablePengobatan, rowKey, "main", "diagnosaBanding", pengobatan.getDiagnosaBanding());
        }
        client.insertRecord(tablePengobatan, rowKey, "petugas", "nikPetugas", pengobatan.getPetugas().getNikPetugas());
        client.insertRecord(tablePengobatan, rowKey, "petugas", "namaPetugas", pengobatan.getPetugas().getNamaPetugas());
        client.insertRecord(tablePengobatan, rowKey, "detail", "created_by", "Polinema");
        return pengobatan;
    }

    public Pengobatan findPengobatanById(String pengobatanId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tablePengobatan = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idKasus", "idKasus");
        columnMapping.put("tanggalPengobatan", "tanggalPengobatan");
        columnMapping.put("tanggalKasus", "tanggalKasus");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("namaInfrastruktur", "namaInfrastruktur");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("dosis", "dosis");
        columnMapping.put("sindrom", "sindrom");
        columnMapping.put("diagnosaBanding", "diagnosaBanding");

        return client.showDataTable(tablePengobatan.toString(), columnMapping, pengobatanId, Pengobatan.class);
    }
    
    public List<Pengobatan> findPengobatanByPetugas(String petugasId, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idKasus", "idKasus");
        columnMapping.put("tanggalPengobatan", "tanggalPengobatan");
        columnMapping.put("tanggalKasus", "tanggalKasus");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("namaInfrastruktur", "namaInfrastruktur");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("dosis", "dosis");
        columnMapping.put("sindrom", "sindrom");
        columnMapping.put("diagnosaBanding", "diagnosaBanding");

        return client.getDataListByColumn(table.toString(), columnMapping, "main", "petugas", petugasId, Pengobatan.class, size);
    }

    public List<Pengobatan> findAllById(List<String> pengobatanIds) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idKasus", "idKasus");
        columnMapping.put("tanggalPengobatan", "tanggalPengobatan");
        columnMapping.put("tanggalKasus", "tanggalKasus");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("namaInfrastruktur", "namaInfrastruktur");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("dosis", "dosis");
        columnMapping.put("sindrom", "sindrom");
        columnMapping.put("diagnosaBanding", "diagnosaBanding");

        List<Pengobatan> pengobatans = new ArrayList<>();
        for (String pengobatanId : pengobatanIds) {
            Pengobatan pengobatan = client.showDataTable(table.toString(), columnMapping, pengobatanId, Pengobatan.class);
            if (pengobatan != null) {
                pengobatans.add(pengobatan);
            }
        }

        return pengobatans;
    }

    public Pengobatan update(String pengobatanId, Pengobatan pengobatan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tablePengobatan = TableName.valueOf(tableName);
        if (pengobatan.getTanggalPengobatan() != null) {
        client.insertRecord(tablePengobatan, pengobatanId, "main", "tanggalPengobatan", pengobatan.getTanggalPengobatan());
        }
        if (pengobatan.getTanggalKasus() != null) {
        client.insertRecord(tablePengobatan, pengobatanId, "main", "tanggalKasus", pengobatan.getTanggalKasus());
        }
        if (pengobatan.getNamaInfrastruktur() != null) {
        client.insertRecord(tablePengobatan, pengobatanId, "main", "namaInfrastruktur", pengobatan.getNamaInfrastruktur());
        }
        if (pengobatan.getLokasi() != null) {
        client.insertRecord(tablePengobatan, pengobatanId, "main", "lokasi", pengobatan.getLokasi());
        }
        if (pengobatan.getDosis() != null) {
        client.insertRecord(tablePengobatan, pengobatanId, "main", "dosis", pengobatan.getDosis());
        }
        if (pengobatan.getSindrom() != null) {
        client.insertRecord(tablePengobatan, pengobatanId, "main", "sindrom", pengobatan.getSindrom());
        }
        if (pengobatan.getDiagnosaBanding() != null) {
        client.insertRecord(tablePengobatan, pengobatanId, "main", "diagnosaBanding", pengobatan.getDiagnosaBanding());
        }
        client.insertRecord(tablePengobatan, pengobatanId, "petugas", "nikPetugas", pengobatan.getPetugas().getNikPetugas());
        client.insertRecord(tablePengobatan, pengobatanId, "petugas", "namaPetugas", pengobatan.getPetugas().getNamaPetugas());
        client.insertRecord(tablePengobatan, pengobatanId, "detail", "created_by", "Polinema");
        return pengobatan;
    }

    public boolean deleteById(String pengobatanId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, pengobatanId);
        return true;
    }
}

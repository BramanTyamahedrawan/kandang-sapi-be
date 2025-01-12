package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.model.User;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.*;

public class KelahiranRepository {
    Configuration conf = HBaseConfiguration.create();
    String tableName = "kelahirandev";

    public List<Kelahiran> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableKelahiran = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalLaporan", "tanggalLaporan");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("inseminasi", "inseminasi");
        columnMapping.put("eartagAnak", "eartagAnak");
        columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
        columnMapping.put("spesies", "spesies");

        return client.showListTable(tableKelahiran.toString(), columnMapping, Kelahiran.class, size);
    }

    public Kelahiran save(Kelahiran kelahiran) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = kelahiran.getIdKejadian();

        TableName tableKelahiran = TableName.valueOf(tableName);
        client.insertRecord(tableKelahiran, rowKey, "main", "idKejadian", rowKey);
        if (kelahiran.getTanggalLaporan() != null) {
            client.insertRecord(tableKelahiran, rowKey, "main", "tanggalLaporan", kelahiran.getTanggalLaporan());
        }
        if (kelahiran.getTanggalLahir() != null) {
            client.insertRecord(tableKelahiran, rowKey, "main", "tanggalLahir", kelahiran.getTanggalLahir());
        }
        if (kelahiran.getEartagAnak() != null) {
            client.insertRecord(tableKelahiran, rowKey, "main", "eartagAnak", kelahiran.getEartagAnak());
        }
        if (kelahiran.getJenisKelaminAnak() != null) {
            client.insertRecord(tableKelahiran, rowKey, "main", "jenisKelaminAnak", kelahiran.getJenisKelaminAnak());
        }
        if (kelahiran.getSpesies() != null) {
            client.insertRecord(tableKelahiran, rowKey, "main", "spesies", kelahiran.getSpesies());
        }
        client.insertRecord(tableKelahiran, rowKey, "peternak", "idPeternak", kelahiran.getPeternak().getIdPeternak());
        client.insertRecord(tableKelahiran, rowKey, "peternak", "nikPeternak",
                kelahiran.getPeternak().getNikPeternak());
        client.insertRecord(tableKelahiran, rowKey, "peternak", "namaPeternak",
                kelahiran.getPeternak().getNamaPeternak());
        client.insertRecord(tableKelahiran, rowKey, "peternak", "lokasi", kelahiran.getPeternak().getLokasi());
        client.insertRecord(tableKelahiran, rowKey, "petugas", "nikPetugas", kelahiran.getPetugas().getNikPetugas());
        client.insertRecord(tableKelahiran, rowKey, "petugas", "namaPetugas", kelahiran.getPetugas().getNamaPetugas());
        client.insertRecord(tableKelahiran, rowKey, "hewan", "kodeEartagNasional",
                kelahiran.getHewan().getKodeEartagNasional());
        client.insertRecord(tableKelahiran, rowKey, "kandang", "idKandang", kelahiran.getKandang().getIdKandang());
        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "idInseminasi",
                kelahiran.getInseminasi().getIdInseminasi());
        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "idPejantan",
                kelahiran.getInseminasi().getIdPejantan());
        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "idPembuatan",
                kelahiran.getInseminasi().getIdPembuatan());
        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "produsen", kelahiran.getInseminasi().getProdusen());
        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "bangsaPejantan",
                kelahiran.getInseminasi().getBangsaPejantan());
        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "ib", kelahiran.getInseminasi().getIb1());
        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "tanggalIB",
                kelahiran.getInseminasi().getTanggalIB());

        client.insertRecord(tableKelahiran, rowKey, "detail", "created_by", "Polinema");
        return kelahiran;
    }

    public Kelahiran findKelahiranById(String kelahiranId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableKelahiran = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalLaporan", "tanggalLaporan");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("inseminasi", "inseminasi");
        columnMapping.put("eartagAnak", "eartagAnak");
        columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
        columnMapping.put("spesies", "spesies");

        return client.showDataTable(tableKelahiran.toString(), columnMapping, kelahiranId, Kelahiran.class);
    }

    public List<Kelahiran> findKelahiranByPeternak(String peternakId, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalLaporan", "tanggalLaporan");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("inseminasi", "inseminasi");
        columnMapping.put("eartagAnak", "eartagAnak");
        columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
        columnMapping.put("spesies", "spesies");

        List<Kelahiran> kelahiran = client.getDataListByColumn(table.toString(), columnMapping, "peternak",
                "nikPeternak", peternakId, Kelahiran.class, size);
        return kelahiran;
    }

    public List<Kelahiran> findKelahiranByPetugas(String petugasId, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalLaporan", "tanggalLaporan");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("inseminasi", "inseminasi");
        columnMapping.put("eartagAnak", "eartagAnak");
        columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
        columnMapping.put("spesies", "spesies");

        return client.getDataListByColumn(table.toString(), columnMapping, "petugas", "nikPetugas", petugasId,
                Kelahiran.class, size);
    }

    public List<Kelahiran> findKelahiranByHewan(String hewanId, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalLaporan", "tanggalLaporan");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("inseminasi", "inseminasi");
        columnMapping.put("eartagAnak", "eartagAnak");
        columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
        columnMapping.put("spesies", "spesies");

        return client.getDataListByColumn(table.toString(), columnMapping, "hewan", "kodeEartagNasional", hewanId,
                Kelahiran.class, size);
    }

    public List<Kelahiran> findAllById(List<String> kelahiranIds) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalLaporan", "tanggalLaporan");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("inseminasi", "inseminasi");
        columnMapping.put("eartagAnak", "eartagAnak");
        columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
        columnMapping.put("spesies", "spesies");
        List<Kelahiran> kelahirans = new ArrayList<>();
        for (String kelahiranId : kelahiranIds) {
            Kelahiran kelahiran = client.showDataTable(table.toString(), columnMapping, kelahiranId, Kelahiran.class);
            if (kelahiran != null) {
                kelahirans.add(kelahiran);
            }
        }

        return kelahirans;
    }

    public Kelahiran update(String kelahiranId, Kelahiran kelahiran) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableKelahiran = TableName.valueOf(tableName);
        if (kelahiran.getTanggalLaporan() != null) {
            client.insertRecord(tableKelahiran, kelahiranId, "main", "tanggalLaporan", kelahiran.getTanggalLaporan());
        }
        if (kelahiran.getTanggalLahir() != null) {
            client.insertRecord(tableKelahiran, kelahiranId, "main", "tanggalLahir", kelahiran.getTanggalLahir());
        }
        if (kelahiran.getEartagAnak() != null) {
            client.insertRecord(tableKelahiran, kelahiranId, "main", "eartagAnak", kelahiran.getEartagAnak());
        }
        if (kelahiran.getJenisKelaminAnak() != null) {
            client.insertRecord(tableKelahiran, kelahiranId, "main", "jenisKelaminAnak",
                    kelahiran.getJenisKelaminAnak());
        }
        if (kelahiran.getSpesies() != null) {
            client.insertRecord(tableKelahiran, kelahiranId, "main", "spesies", kelahiran.getSpesies());
        }
        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "idPeternak",
                kelahiran.getPeternak().getIdPeternak());
        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "nikPeternak",
                kelahiran.getPeternak().getNikPeternak());
        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "namaPeternak",
                kelahiran.getPeternak().getNamaPeternak());
        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "lokasi", kelahiran.getPeternak().getLokasi());
        client.insertRecord(tableKelahiran, kelahiranId, "petugas", "nikPetugas",
                kelahiran.getPetugas().getNikPetugas());
        client.insertRecord(tableKelahiran, kelahiranId, "petugas", "namaPetugas",
                kelahiran.getPetugas().getNamaPetugas());
        client.insertRecord(tableKelahiran, kelahiranId, "hewan", "kodeEartagNasional",
                kelahiran.getHewan().getKodeEartagNasional());
        client.insertRecord(tableKelahiran, kelahiranId, "kandang", "idKandang", kelahiran.getKandang().getIdKandang());
        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "idInseminasi",
                kelahiran.getInseminasi().getIdInseminasi());
        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "idPejantan",
                kelahiran.getInseminasi().getIdPejantan());
        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "idPembuatan",
                kelahiran.getInseminasi().getIdPembuatan());
        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "bangsaPejantan",
                kelahiran.getInseminasi().getBangsaPejantan());
        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "produsen",
                kelahiran.getInseminasi().getProdusen());
        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "ib", kelahiran.getInseminasi().getIb1());
        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "tanggalIB",
                kelahiran.getInseminasi().getTanggalIB());
        client.insertRecord(tableKelahiran, kelahiranId, "detail", "created_by", "Polinema");
        return kelahiran;
    }

    public boolean deleteById(String kelahiranId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, kelahiranId);
        return true;
    }
}

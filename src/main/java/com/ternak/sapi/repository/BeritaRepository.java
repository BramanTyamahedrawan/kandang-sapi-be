package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Berita;
import com.ternak.sapi.model.User;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BeritaRepository {
    Configuration conf = HBaseConfiguration.create();
    String tableName = "beritadev";

    public List<Berita> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableBerita = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idBerita", "idBerita");
        columnMapping.put("judul", "judul");
        columnMapping.put("tglPembuatan", "tglPembuatan");
        columnMapping.put("isiBerita", "isiBerita");
        columnMapping.put("pembuat", "pembuat");
        columnMapping.put("file_path", "file_path");

        return client.showListTable(tableBerita.toString(), columnMapping, Berita.class, size);
    }

    public Berita save(Berita berita) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = UUID.randomUUID().toString();

        TableName tableBerita = TableName.valueOf(tableName);
        client.insertRecord(tableBerita, rowKey, "main", "idBerita", rowKey);
        client.insertRecord(tableBerita, rowKey, "main", "judul", berita.getJudul());
        client.insertRecord(tableBerita, rowKey, "main", "tglPembuatan", berita.getTglPembuatan());
        client.insertRecord(tableBerita, rowKey, "main", "isiBerita", berita.getIsiBerita());
        client.insertRecord(tableBerita, rowKey, "main", "pembuat", berita.getPembuat());
        client.insertRecord(tableBerita, rowKey, "main", "file_path", berita.getFile_path());
        client.insertRecord(tableBerita, rowKey, "detail", "created_by", "Polinema");
        return berita;
    }

    public Berita findById(String beritaId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableBerita = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idBerita", "idBerita");
        columnMapping.put("judul", "judul");
        columnMapping.put("tglPembuatan", "tglPembuatan");
        columnMapping.put("isiBerita", "isiBerita");
        columnMapping.put("pembuat", "pembuat");
        columnMapping.put("file_path", "file_path");

        return client.showDataTable(tableBerita.toString(), columnMapping, beritaId, Berita.class);
    }

    public Berita update(String beritaId, Berita berita) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableBerita = TableName.valueOf(tableName);
        client.insertRecord(tableBerita, beritaId, "main", "judul", berita.getJudul());
        client.insertRecord(tableBerita, beritaId, "main", "tglPembuatan", berita.getTglPembuatan());
        client.insertRecord(tableBerita, beritaId, "main", "isiBerita", berita.getIsiBerita());
        client.insertRecord(tableBerita, beritaId, "main", "pembuat", berita.getPembuat());
        client.insertRecord(tableBerita, beritaId, "main", "file_path", berita.getFile_path());
        return berita;
    }

    public boolean deleteById(String beritaId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, beritaId);
        return true;
    }
}

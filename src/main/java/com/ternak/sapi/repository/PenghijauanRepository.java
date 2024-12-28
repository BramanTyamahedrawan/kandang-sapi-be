/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Penghijauan;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;


public class PenghijauanRepository {
     Configuration conf = HBaseConfiguration.create();
    String tableName = "penghijauandev";
    
    public List<Penghijauan> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idPenghijauan", "idPenghijauan");
        columnMapping.put("namaTempat", "namaTempat");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("keterangan", "keterangan");
        columnMapping.put("file_path", "file_path");
        return client.showListTable(tableUsers.toString(), columnMapping, Penghijauan.class, size);
    }
    public Penghijauan save(Penghijauan penghijauan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = penghijauan.getIdPenghijauan();
        TableName tablePenghijauan = TableName.valueOf(tableName);
        client.insertRecord(tablePenghijauan, rowKey, "main", "idPenghijauan", rowKey);
        client.insertRecord(tablePenghijauan, rowKey, "main", "namaTempat", penghijauan.getNamaTempat());
        client.insertRecord(tablePenghijauan, rowKey, "main", "lokasi", penghijauan.getLokasi());
        client.insertRecord(tablePenghijauan, rowKey, "main", "keterangan", penghijauan.getKeterangan());
        client.insertRecord(tablePenghijauan, rowKey, "main", "file_path", penghijauan.getFile_path());
        client.insertRecord(tablePenghijauan, rowKey, "detail", "created_by", "Polinema");
        return penghijauan;
    }
    
    public Penghijauan findById(String penghijauanId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idPenghijauan", "idPenghijauan");
        columnMapping.put("namaTempat", "namaTempat");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("keterangan", "keterangan");
        columnMapping.put("file_path", "file_path");

        return client.showDataTable(tableUsers.toString(), columnMapping, penghijauanId, Penghijauan.class);
    }
    
      public List<Penghijauan> findAllById(List<String> penghijauanIds) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
       columnMapping.put("idPenghijauan", "idPenghijauan");
        columnMapping.put("namaTempat", "namaTempat");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("keterangan", "keterangan");
        columnMapping.put("file_path", "file_path");

        List<Penghijauan> penghijauans = new ArrayList<>();
        for (String penghijauanId : penghijauanIds) {
            Penghijauan penghijauan = client.showDataTable(table.toString(), columnMapping, penghijauanId, Penghijauan.class);
            if (penghijauan != null) {
                penghijauans.add(penghijauan);
            }
        }

        return penghijauans;
    }
      
       public Penghijauan update(String penghijauanId, Penghijauan penghijauan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tablePenghijauan = TableName.valueOf(tableName);
        client.insertRecord(tablePenghijauan, penghijauanId, "main", "namaTempat", penghijauan.getNamaTempat());
        client.insertRecord(tablePenghijauan, penghijauanId, "main", "lokasi", penghijauan.getLokasi());
        client.insertRecord(tablePenghijauan, penghijauanId, "main", "keterangan", penghijauan.getKeterangan());
        client.insertRecord(tablePenghijauan, penghijauanId, "main", "file_path", penghijauan.getFile_path());
        client.insertRecord(tablePenghijauan, penghijauanId, "detail", "created_by", "Polinema");
        return penghijauan;
    }
       
       public boolean deleteById(String penghijauanId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, penghijauanId);
        return true;
    }
}

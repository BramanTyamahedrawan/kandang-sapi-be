
package com.ternak.sapi.repository;

import com.ternak.sapi.controller.RumpunHewanController;
import com.ternak.sapi.helper.HBaseCustomClient;
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
    //RumpunHewanController departmentController = new RumpunHewanController();

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
        client.insertRecord(tableRumpunHewan, rowKey, "main", "idRumpunHewan", rowKey);
        if (rumpunhewan.getRumpun() != null) {
            client.insertRecord(tableRumpunHewan, rowKey, "main", "rumpun", rumpunhewan.getRumpun());
        }
        if (rumpunhewan.getDeskripsi() != null) {
            client.insertRecord(tableRumpunHewan, rowKey, "main", "deskripsi", rumpunhewan.getDeskripsi());
        }
        client.insertRecord(tableRumpunHewan, rowKey, "detail", "created_by", "Polinema");
        return rumpunhewan;
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
            client.insertRecord(tableRumpunHewan, rumpunhewanId, "main", "deskripsi", rumpunhewan.getDeskripsi());
        }

        client.insertRecord(tableRumpunHewan, rumpunhewanId, "detail", "created_by", "Polinema");
        return rumpunhewan;
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

        RumpunHewan rumpunHewan = client.getDataByColumn(tableRumpunHewan.toString(), columnMapping, "main", "rumpun", rumpun, RumpunHewan.class);
        return rumpunHewan.getRumpun() != null; // True jika rumpun sudah ada
    }
}

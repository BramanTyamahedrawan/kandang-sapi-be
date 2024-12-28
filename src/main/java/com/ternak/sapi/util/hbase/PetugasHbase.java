package com.ternak.sapi.util.hbase;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Petugas;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetugasHbase {
    Configuration conf = HBaseConfiguration.create();
    HBaseCustomClient client = new HBaseCustomClient(conf);
    String tableName = "petugasdev";

    public PetugasHbase() throws IOException {
    }

    public List<String> getBulkDataByColumn(String tableName, String columnFamily, String column, List<String> values) throws IOException {
        List<String> existingValues = new ArrayList<>();
        for (String value : values) {
            Map<String, String> columnMapping = new HashMap<>();
            columnMapping.put(column, column);

            Petugas petugas = client.getDataByColumn(tableName, columnMapping, columnFamily, column, value, Petugas.class);
            if (petugas != null && petugas.getNikPetugas() != null) {
                existingValues.add(petugas.getNikPetugas());
            }
        }
        return existingValues;
    }
}

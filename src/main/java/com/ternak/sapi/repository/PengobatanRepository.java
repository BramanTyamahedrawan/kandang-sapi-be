package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Inseminasi;
import com.ternak.sapi.model.Pengobatan;
import com.ternak.sapi.model.Petugas;
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
        columnMapping.put("provinsiPengobatan", "provinsiPengobatan");
        columnMapping.put("kabupatenPengobatan", "kabupatenPengobatan");
        columnMapping.put("kecamatanPengobatan", "kecamatanPengobatan");
        columnMapping.put("desaPengobatan", "desaPengobatan");

        return client.showListTable(tablePengobatan.toString(), columnMapping, Pengobatan.class, size);
    }

    public Pengobatan save(Pengobatan pengobatan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = pengobatan.getIdKasus();

        TableName tablePengobatan = TableName.valueOf(tableName);
        client.insertRecord(tablePengobatan, rowKey, "main", "idKasus", rowKey);
        if (pengobatan.getTanggalPengobatan() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "tanggalPengobatan",
                    pengobatan.getTanggalPengobatan());
        }
        if (pengobatan.getTanggalKasus() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "tanggalKasus", pengobatan.getTanggalKasus());
        }
        if (pengobatan.getNamaInfrastruktur() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "namaInfrastruktur",
                    pengobatan.getNamaInfrastruktur());
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
        client.insertRecord(tablePengobatan, rowKey, "petugas", "namaPetugas",
                pengobatan.getPetugas().getNamaPetugas());
        client.insertRecord(tablePengobatan, rowKey, "detail", "created_by", "Polinema");
        return pengobatan;
    }

    public List<Pengobatan> saveImport(List<Pengobatan> pengobatanList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePengobatan = TableName.valueOf(tableName);

        System.out.println("Memulai penyimpanan data ke HBase...");
        List<String> failedRows = new ArrayList<>();

        for (Pengobatan pengobatan : pengobatanList) {
            try {
                if (pengobatan.getPetugas() != null) {
                    Petugas petugas = pengobatan.getPetugas();
                    if (petugas.getNamaPetugas() != null) {
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdKasus()), "petugas",
                                "nikPetugas",
                                safeString(petugas.getNikPetugas()));
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdKasus()), "petugas",
                                "namaPetugas",
                                safeString(petugas.getNamaPetugas()));
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdKasus()), "petugas",
                                "email",
                                safeString(petugas.getEmail()));
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdKasus()), "petugas",
                                "noTelp",
                                safeString(petugas.getNoTelp()));
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdKasus()), "petugas", "job",
                                safeString(petugas.getJob()));
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdKasus()), "petugas",
                                "wilayah",
                                safeString(petugas.getWilayah()));

                    }
                }

                String rowKey = pengobatan.getIdKasus();

                if (pengobatan.getIdKasus() != null) {
                    client.insertRecord(tablePengobatan, rowKey, "main", "idKasus", rowKey);
                }
                if (pengobatan.getTanggalPengobatan() != null) {
                    client.insertRecord(tablePengobatan, rowKey, "main", "tanggalPengobatan",
                            pengobatan.getTanggalPengobatan());
                }
                if (pengobatan.getTanggalKasus() != null) {
                    client.insertRecord(tablePengobatan, rowKey, "main", "tanggalKasus", pengobatan.getTanggalKasus());
                }
                if (pengobatan.getNamaInfrastruktur() != null) {
                    client.insertRecord(tablePengobatan, rowKey, "main", "namaInfrastruktur",
                            pengobatan.getNamaInfrastruktur());
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
                    client.insertRecord(tablePengobatan, rowKey, "main", "diagnosaBanding",
                            pengobatan.getDiagnosaBanding());
                }
                if (pengobatan.getProvinsiPengobatan() != null) {
                    client.insertRecord(tablePengobatan, rowKey, "main", "provinsiPengobatan",
                            pengobatan.getProvinsiPengobatan());
                }
                if (pengobatan.getKabupatenPengobatan() != null) {
                    client.insertRecord(tablePengobatan, rowKey, "main", "kabupatenPengobatan",
                            pengobatan.getKabupatenPengobatan());
                }
                if (pengobatan.getKecamatanPengobatan() != null) {
                    client.insertRecord(tablePengobatan, rowKey, "main", "kecamatanPengobatan",
                            pengobatan.getKecamatanPengobatan());
                }
                if (pengobatan.getDesaPengobatan() != null) {
                    client.insertRecord(tablePengobatan, rowKey, "main", "desaPengobatan",
                            pengobatan.getDesaPengobatan());
                }

                client.insertRecord(tablePengobatan, rowKey, "detail", "created_by", "Polinema");

                System.out.println(
                        "Data berhasil disimpan ke HBase dengan ID Inseminasi: " + pengobatan.getIdKasus());

            } catch (Exception e) {
                failedRows.add(pengobatan.getIdKasus());
                System.err.println(
                        "Failed to insert record for ID: " + pengobatan.getIdKasus() + ", Error: "
                                + e.getMessage());
            }
        }
        if (!failedRows.isEmpty()) {
            throw new IOException("Failed to save records for ID Kasus: " + String.join(", ", failedRows));
        }

        return pengobatanList;
    }

    private String safeString(String value) {
        System.out.println("Value before safeString: " + value);
        return (value != null && !value.trim().isEmpty()) ? value : "";
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
        columnMapping.put("provinsiPengobatan", "provinsiPengobatan");
        columnMapping.put("kabupatenPengobatan", "kabupatenPengobatan");
        columnMapping.put("kecamatanPengobatan", "kecamatanPengobatan");
        columnMapping.put("desaPengobatan", "desaPengobatan");

        return client.showDataTable(tablePengobatan.toString(), columnMapping, pengobatanId, Pengobatan.class);
    }

    public List<Pengobatan> findPengobatanByPetugas(String namaPetugas, int size) throws IOException {
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
        columnMapping.put("provinsiPengobatan", "provinsiPengobatan");
        columnMapping.put("kabupatenPengobatan", "kabupatenPengobatan");
        columnMapping.put("kecamatanPengobatan", "kecamatanPengobatan");
        columnMapping.put("desaPengobatan", "desaPengobatan");

        return client.getDataListByColumn(table.toString(), columnMapping, "petugas", "namaPetugas", namaPetugas,
                Pengobatan.class, size);
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
        columnMapping.put("provinsiPengobatan", "provinsiPengobatan");
        columnMapping.put("kabupatenPengobatan", "kabupatenPengobatan");
        columnMapping.put("kecamatanPengobatan", "kecamatanPengobatan");
        columnMapping.put("desaPengobatan", "desaPengobatan");

        List<Pengobatan> pengobatans = new ArrayList<>();
        for (String pengobatanId : pengobatanIds) {
            Pengobatan pengobatan = client.showDataTable(table.toString(), columnMapping, pengobatanId,
                    Pengobatan.class);
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
            client.insertRecord(tablePengobatan, pengobatanId, "main", "tanggalPengobatan",
                    pengobatan.getTanggalPengobatan());
        }
        if (pengobatan.getTanggalKasus() != null) {
            client.insertRecord(tablePengobatan, pengobatanId, "main", "tanggalKasus", pengobatan.getTanggalKasus());
        }
        if (pengobatan.getNamaInfrastruktur() != null) {
            client.insertRecord(tablePengobatan, pengobatanId, "main", "namaInfrastruktur",
                    pengobatan.getNamaInfrastruktur());
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
            client.insertRecord(tablePengobatan, pengobatanId, "main", "diagnosaBanding",
                    pengobatan.getDiagnosaBanding());
        }
        client.insertRecord(tablePengobatan, pengobatanId, "petugas", "nikPetugas",
                pengobatan.getPetugas().getNikPetugas());
        client.insertRecord(tablePengobatan, pengobatanId, "petugas", "namaPetugas",
                pengobatan.getPetugas().getNamaPetugas());
        client.insertRecord(tablePengobatan, pengobatanId, "detail", "created_by", "Polinema");
        return pengobatan;
    }

    public boolean deleteById(String pengobatanId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, pengobatanId);
        return true;
    }
}

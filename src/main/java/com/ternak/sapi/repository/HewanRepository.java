
package com.ternak.sapi.repository;

import com.ternak.sapi.controller.HewanController;
import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Hewan;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.thirdparty.org.checkerframework.checker.units.qual.s;

import java.io.IOException;
import java.util.*;

public class HewanRepository {
    Configuration conf = HBaseConfiguration.create();
    String tableName = "hewandev";
    // HewanController departmentController = new HewanController();
    private Iterable<String> idHewans;

    public List<Hewan> findAll(int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idHewan", "idHewan");
        columnMapping.put("kodeEartagNasional", "kodeEartagNasional");

        columnMapping.put("peternak", "peternak");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("jenisHewan", "jenisHewan");
        columnMapping.put("rumpunHewan", "rumpunHewan");

        columnMapping.put("sex", "sex");
        columnMapping.put("umur", "umur");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("tempatLahir", "tempatLahir");
        columnMapping.put("tujuanPemeliharaan", "tujuanPemeliharaan");

        columnMapping.put("identifikasiHewan", "identifikasiHewan");
        columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");

        columnMapping.put("latitude", "latitude");
        columnMapping.put("longitude", "longitude");
        columnMapping.put("file_path", "file_path");
        return client.showListTable(tableUsers.toString(), columnMapping, Hewan.class, size);
    }

    public Hewan save(Hewan hewan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        String rowKey = hewan.getIdHewan();
        TableName tableHewan = TableName.valueOf(tableName);
        client.insertRecord(tableHewan, rowKey, "main", "idHewan", rowKey);
        if (hewan.getKodeEartagNasional() != null) {
            client.insertRecord(tableHewan, rowKey, "main", "kodeEartagNasional", hewan.getKodeEartagNasional());
        }
        if (hewan.getSex() != null) {
            client.insertRecord(tableHewan, rowKey, "main", "sex", hewan.getSex());
        }
        if (hewan.getUmur() != null) {
            client.insertRecord(tableHewan, rowKey, "main", "umur", hewan.getUmur());
        }
        if (hewan.getTanggalLahir() != null) {
            client.insertRecord(tableHewan, rowKey, "main", "tanggalLahir", hewan.getTanggalLahir());
        }
        if (hewan.getTempatLahir() != null) {
            client.insertRecord(tableHewan, rowKey, "main", "tempatLahir", hewan.getTempatLahir());
        }
        if (hewan.getTujuanPemeliharaan() != null) {
            client.insertRecord(tableHewan, rowKey, "main", "tujuanPemeliharaan", hewan.getTujuanPemeliharaan());
        }
        if (hewan.getIdentifikasiHewan() != null) {
            client.insertRecord(tableHewan, rowKey, "main", "identifikasiHewan", hewan.getIdentifikasiHewan());
        }
        if (hewan.getTanggalTerdaftar() != null) {
            client.insertRecord(tableHewan, rowKey, "main", "tanggalTerdaftar", hewan.getTanggalTerdaftar());
        }
        if (hewan.getLatitude() != null) {
            client.insertRecord(tableHewan, rowKey, "main", "latitude", hewan.getLatitude());
        }
        if (hewan.getLongitude() != null) {
            client.insertRecord(tableHewan, rowKey, "main", "longitude", hewan.getLongitude());
        }
        if (hewan.getFile_path() != null) {
            client.insertRecord(tableHewan, rowKey, "main", "file_path", hewan.getFile_path());
        }
        if (hewan.getPeternak().getIdPeternak() != null) {
            client.insertRecord(tableHewan, rowKey, "peternak", "idPeternak", hewan.getPeternak().getIdPeternak());
        }
        if (hewan.getPeternak().getNikPeternak() != null) {
            client.insertRecord(tableHewan, rowKey, "peternak", "nikPeternak", hewan.getPeternak().getNikPeternak());
        }
        if (hewan.getPeternak().getNamaPeternak() != null) {
            client.insertRecord(tableHewan, rowKey, "peternak", "namaPeternak", hewan.getPeternak().getNamaPeternak());
        }
        if (hewan.getJenisHewan().getIdJenisHewan() != null) {
            client.insertRecord(tableHewan, rowKey, "jenisHewan", "idJenisHewan",
                    hewan.getJenisHewan().getIdJenisHewan());
        }
        if (hewan.getJenisHewan().getJenis() != null) {
            client.insertRecord(tableHewan, rowKey, "jenisHewan", "jenis", hewan.getJenisHewan().getJenis());
        }
        if (hewan.getJenisHewan().getDeskripsi() != null) {
            client.insertRecord(tableHewan, rowKey, "jenisHewan", "deskripsi", hewan.getJenisHewan().getDeskripsi());
        }
        if (hewan.getRumpunHewan().getIdRumpunHewan() != null) {
            client.insertRecord(tableHewan, rowKey, "rumpunHewan", "idRumpunHewan",
                    hewan.getRumpunHewan().getIdRumpunHewan());
        }
        if (hewan.getRumpunHewan().getRumpun() != null) {
            client.insertRecord(tableHewan, rowKey, "rumpunHewan", "rumpun", hewan.getRumpunHewan().getRumpun());
        }
        if (hewan.getRumpunHewan().getDeskripsi() != null) {
            client.insertRecord(tableHewan, rowKey, "rumpunHewan", "deskripsi", hewan.getRumpunHewan().getDeskripsi());
        }
        if (hewan.getPetugas().getNikPetugas() != null) {
            client.insertRecord(tableHewan, rowKey, "petugas", "nikPetugas", hewan.getPetugas().getNikPetugas());
        }
        if (hewan.getPetugas().getNamaPetugas() != null) {
            client.insertRecord(tableHewan, rowKey, "petugas", "namaPetugas", hewan.getPetugas().getNamaPetugas());
        }
        if (hewan.getKandang().getIdKandang() != null) {
            client.insertRecord(tableHewan, rowKey, "kandang", "idKandang", hewan.getKandang().getIdKandang());
        }
        client.insertRecord(tableHewan, rowKey, "detail", "created_by", "Polinema");
        return hewan;
    }

    public List<Hewan> saveAll(List<Hewan> hewanList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableHewan = TableName.valueOf(tableName);

        System.out.println("Memulai penyimpanan data ke HBase...");
        List<String> failedRows = new ArrayList<>();

        for (Hewan hewan : hewanList) {
            try {
                String rowKey = safeString(hewan.getIdHewan());

                client.insertRecord(tableHewan, rowKey, "main", "idHewan",
                        safeString(hewan.getIdHewan()));
                client.insertRecord(tableHewan, rowKey, "main", "kodeEartagNasional",
                        safeString(hewan.getKodeEartagNasional()));

                if (hewan.getPeternak() != null) {
                    client.insertRecord(tableHewan, rowKey, "peternak", "idPeternak",
                            safeString(hewan.getPeternak().getIdPeternak()));
                    client.insertRecord(tableHewan, rowKey, "peternak", "nikPeternak",
                            safeString(hewan.getPeternak().getNikPeternak()));
                    client.insertRecord(tableHewan, rowKey, "peternak", "namaPeternak",
                            safeString(hewan.getPeternak().getNamaPeternak()));
                }

                if (hewan.getPetugas() != null) {
                    client.insertRecord(tableHewan, rowKey, "petugas", "nikPetugas",
                            safeString(hewan.getPetugas().getNikPetugas()));
                    client.insertRecord(tableHewan, rowKey, "petugas", "namaPetugas",
                            safeString(hewan.getPetugas().getNamaPetugas()));
                }

                if (hewan.getKandang() != null) {
                    client.insertRecord(tableHewan, rowKey, "kandang", "idKandang",
                            safeString(hewan.getKandang().getIdKandang()));
                }

                if (hewan.getJenisHewan() != null) {
                    client.insertRecord(tableHewan, rowKey, "jenisHewan", "idJenisHewan",
                            safeString(hewan.getJenisHewan().getIdJenisHewan()));
                    client.insertRecord(tableHewan, rowKey, "jenisHewan", "jenis",
                            safeString(hewan.getJenisHewan().getJenis()));
                    client.insertRecord(tableHewan, rowKey, "jenisHewan", "deskripsi",
                            safeString(hewan.getJenisHewan().getDeskripsi()));
                }

                if (hewan.getRumpunHewan() != null) {
                    client.insertRecord(tableHewan, rowKey, "rumpunHewan", "idRumpunHewan",
                            safeString(hewan.getRumpunHewan().getIdRumpunHewan()));
                    client.insertRecord(tableHewan, rowKey, "rumpunHewan", "rumpun",
                            safeString(hewan.getRumpunHewan().getRumpun()));
                    client.insertRecord(tableHewan, rowKey, "rumpunHewan", "deskripsi",
                            safeString(hewan.getRumpunHewan().getDeskripsi()));
                }

                client.insertRecord(tableHewan, rowKey, "detail", "created_by", "Polinema");
                System.out.println("Data berhasil disimpan ke HBase: " + hewan.getIdHewan());
            } catch (Exception e) {
                failedRows.add(hewan.getIdHewan());
                System.err.println(
                        "Failed to insert record for ID: " + hewan.getIdHewan() + ", Error: "
                                + e.getMessage());
            }
        }

        if (!failedRows.isEmpty()) {
            throw new IOException("Failed to save records for Hewan: " + String.join(", ", failedRows));
        }

        return hewanList;
    }

    private String safeString(String value) {
        return value != null ? value : "";
    }

    public Hewan findById(String idHewan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idHewan", "idHewan");
        columnMapping.put("kodeEartagNasional", "kodeEartagNasional");

        columnMapping.put("peternak", "peternak");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("jenisHewan", "jenisHewan");
        columnMapping.put("rumpunHewan", "rumpunHewan");

        columnMapping.put("sex", "sex");
        columnMapping.put("umur", "umur");
        columnMapping.put("tanggalLahir", "tanggalLahir");
        columnMapping.put("tempatLahir", "tempatLahir");
        columnMapping.put("tujuanPemeliharaan", "tujuanPemeliharaan");

        columnMapping.put("identifikasiHewan", "identifikasiHewan");
        columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");

        columnMapping.put("latitude", "latitude");
        columnMapping.put("longitude", "longitude");
        columnMapping.put("file_path", "file_path");

        return client.showDataTable(tableUsers.toString(), columnMapping, idHewan, Hewan.class);
    }

    public List<Hewan> findHewanByPeternak(String peternakId, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idHewan", "idHewan");
        columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("sex", "sex");
        columnMapping.put("umur", "umur");
        columnMapping.put("identifikasiHewan", "identifikasiHewan");
        columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
        columnMapping.put("latitude", "latitude");
        columnMapping.put("longitude", "longitude");
        columnMapping.put("file_path", "file_path");

        List<Hewan> hewan = client.getDataListByColumn(tableUsers.toString(), columnMapping, "peternak", "nikPeternak",
                peternakId, Hewan.class, size);

        return hewan;
    }

    public List<Hewan> findHewanByKandang(String kandangId, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idHewan", "idHewan");
        columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("sex", "sex");
        columnMapping.put("umur", "umur");
        columnMapping.put("identifikasiHewan", "identifikasiHewan");
        columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
        columnMapping.put("latitude", "latitude");
        columnMapping.put("longitude", "longitude");
        columnMapping.put("file_path", "file_path");

        List<Hewan> hewan = client.getDataListByColumn(tableUsers.toString(), columnMapping, "kandang", "idKandang",
                kandangId, Hewan.class, size);

        return hewan;
    }

    public List<Hewan> findHewanByPetugas(String petugasId, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableUsers = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();

        // Add the mappings to the HashMap
        columnMapping.put("idHewan", "idHewan");
        columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("sex", "sex");
        columnMapping.put("umur", "umur");
        columnMapping.put("identifikasiHewan", "identifikasiHewan");
        columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
        columnMapping.put("latitude", "latitude");
        columnMapping.put("longitude", "longitude");
        columnMapping.put("file_path", "file_path");

        List<Hewan> hewan = client.getDataListByColumn(tableUsers.toString(), columnMapping, "petugas", "nikPetugas",
                petugasId, Hewan.class, size);

        return hewan;
    }

    public List<Hewan> findAllById(List<String> ideHewans) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idHewan", "idHewan");
        columnMapping.put("kodeEartagNasional", "kodeEartagNasional");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("sex", "sex");
        columnMapping.put("umur", "umur");
        columnMapping.put("identifikasiHewan", "identifikasiHewan");
        columnMapping.put("tanggalTerdaftar", "tanggalTerdaftar");
        columnMapping.put("latitude", "latitude");
        columnMapping.put("longitude", "longitude");
        columnMapping.put("file_path", "file_path");

        List<Hewan> hewans = new ArrayList<>();
        for (String idHewan : idHewans) {
            Hewan hewan = client.showDataTable(table.toString(), columnMapping, idHewan, Hewan.class);
            if (hewan != null) {
                hewans.add(hewan);
            }
        }

        return hewans;
    }

    public Hewan update(String idHewan, Hewan hewan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName tableHewan = TableName.valueOf(tableName);
        if (hewan.getSex() != null) {
            client.insertRecord(tableHewan, idHewan, "main", "sex", hewan.getSex());
        }
        if (hewan.getUmur() != null) {
            client.insertRecord(tableHewan, idHewan, "main", "umur", hewan.getUmur());
        }
        if (hewan.getTanggalLahir() != null) {
            client.insertRecord(tableHewan, idHewan, "main", "tanggalLahir", hewan.getTanggalLahir());
        }
        if (hewan.getTempatLahir() != null) {
            client.insertRecord(tableHewan, idHewan, "main", "tempatLahir", hewan.getTempatLahir());
        }
        if (hewan.getTujuanPemeliharaan() != null) {
            client.insertRecord(tableHewan, idHewan, "main", "tujuanPemeliharaan", hewan.getTujuanPemeliharaan());
        }
        if (hewan.getIdentifikasiHewan() != null) {
            client.insertRecord(tableHewan, idHewan, "main", "identifikasiHewan", hewan.getIdentifikasiHewan());
        }
        if (hewan.getTanggalTerdaftar() != null) {
            client.insertRecord(tableHewan, idHewan, "main", "tanggalTerdaftar", hewan.getTanggalTerdaftar());
        }
        if (hewan.getLatitude() != null) {
            client.insertRecord(tableHewan, idHewan, "main", "latitude", hewan.getLatitude());
        }
        if (hewan.getLongitude() != null) {
            client.insertRecord(tableHewan, idHewan, "main", "longitude", hewan.getLongitude());
        }
        if (hewan.getFile_path() != null) {
            client.insertRecord(tableHewan, idHewan, "main", "file_path", hewan.getFile_path());
        }
        if (hewan.getPeternak().getIdPeternak() != null) {
            client.insertRecord(tableHewan, idHewan, "peternak", "idPeternak", hewan.getPeternak().getIdPeternak());
        }
        if (hewan.getPeternak().getNikPeternak() != null) {
            client.insertRecord(tableHewan, idHewan, "peternak", "nikPeternak", hewan.getPeternak().getNikPeternak());
        }
        if (hewan.getPeternak().getNamaPeternak() != null) {
            client.insertRecord(tableHewan, idHewan, "peternak", "namaPeternak", hewan.getPeternak().getNamaPeternak());
        }
        if (hewan.getJenisHewan().getIdJenisHewan() != null) {
            client.insertRecord(tableHewan, idHewan, "jenisHewan", "idJenisHewan",
                    hewan.getJenisHewan().getIdJenisHewan());
        }
        if (hewan.getJenisHewan().getJenis() != null) {
            client.insertRecord(tableHewan, idHewan, "jenisHewan", "jenis", hewan.getJenisHewan().getJenis());
        }
        if (hewan.getJenisHewan().getDeskripsi() != null) {
            client.insertRecord(tableHewan, idHewan, "jenisHewan", "deskripsi", hewan.getJenisHewan().getDeskripsi());
        }
        if (hewan.getRumpunHewan().getIdRumpunHewan() != null) {
            client.insertRecord(tableHewan, idHewan, "rumpunHewan", "idRumpunHewan",
                    hewan.getRumpunHewan().getIdRumpunHewan());
        }
        if (hewan.getRumpunHewan().getRumpun() != null) {
            client.insertRecord(tableHewan, idHewan, "rumpunHewan", "rumpun", hewan.getRumpunHewan().getRumpun());
        }
        if (hewan.getRumpunHewan().getDeskripsi() != null) {
            client.insertRecord(tableHewan, idHewan, "rumpunHewan", "deskripsi", hewan.getRumpunHewan().getDeskripsi());
        }
        if (hewan.getPetugas().getNikPetugas() != null) {
            client.insertRecord(tableHewan, idHewan, "petugas", "nikPetugas", hewan.getPetugas().getNikPetugas());
        }
        if (hewan.getPetugas().getNamaPetugas() != null) {
            client.insertRecord(tableHewan, idHewan, "petugas", "namaPetugas", hewan.getPetugas().getNamaPetugas());
        }
        if (hewan.getKandang().getIdKandang() != null) {
            client.insertRecord(tableHewan, idHewan, "kandang", "idKandang", hewan.getKandang().getIdKandang());
        }
        client.insertRecord(tableHewan, idHewan, "detail", "created_by", "Polinema");
        return hewan;
    }

    public boolean deleteById(String idHewan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, idHewan);
        return true;
    }

    public boolean existsByIdHewan(String idHewan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableHewan = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("idHewan", "idHewan");

        Hewan hewan = client.getDataByColumn(tableHewan.toString(), columnMapping, "main", "idHewan", idHewan,
                Hewan.class);
        return hewan.getIdHewan() != null; // True jika ID Hewan sudah ada
    }

    public boolean existsByKodeEartagNasional(String kodeEartagNasional) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tableHewan = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("kodeEartagNasional", "kodeEartagNasional");

        Hewan hewan = client.getDataByColumn(tableHewan.toString(), columnMapping, "main", "kodeEartagNasional",
                kodeEartagNasional, Hewan.class);
        return hewan.getKodeEartagNasional() != null; // True jika kode eartag sudah ada
    }
}

package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.model.Pengobatan;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.Pkb;

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
        columnMapping.put("idPengobatan", "idPengobatan");
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

        String rowKey = pengobatan.getIdPengobatan();

        TableName tablePengobatan = TableName.valueOf(tableName);
        client.insertRecord(tablePengobatan, rowKey, "main", "idPengobatan", safeString(pengobatan.getIdPengobatan()));
        if (pengobatan.getIdKasus() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "idKasus", safeString(pengobatan.getIdKasus()));
        }
        if (pengobatan.getTanggalPengobatan() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "tanggalPengobatan",
                    safeString(pengobatan.getTanggalPengobatan()));
        }
        if (pengobatan.getTanggalKasus() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "tanggalKasus",
                    safeString(pengobatan.getTanggalKasus()));
        }
        if (pengobatan.getNamaInfrastruktur() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "namaInfrastruktur",
                    safeString(pengobatan.getNamaInfrastruktur()));
        }
        if (pengobatan.getSindrom() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "sindrom", safeString(pengobatan.getSindrom()));
        }
        if (pengobatan.getDiagnosaBanding() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "diagnosaBanding",
                    safeString(pengobatan.getDiagnosaBanding()));
        }
        if (pengobatan.getDosis() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "dosis", safeString(pengobatan.getDosis()));
        }
        if (pengobatan.getLokasi() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "lokasi", safeString(pengobatan.getLokasi()));
        }
        if (pengobatan.getProvinsiPengobatan() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "provinsiPengobatan",
                    safeString(pengobatan.getProvinsiPengobatan()));
        }
        if (pengobatan.getKabupatenPengobatan() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "kabupatenPengobatan",
                    safeString(pengobatan.getKabupatenPengobatan()));
        }
        if (pengobatan.getKecamatanPengobatan() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "kecamatanPengobatan",
                    safeString(pengobatan.getKecamatanPengobatan()));
        }
        if (pengobatan.getDesaPengobatan() != null) {
            client.insertRecord(tablePengobatan, rowKey, "main", "desaPengobatan",
                    safeString(pengobatan.getDesaPengobatan()));
        }

        // Petugas
        if (pengobatan.getPetugas() != null) {
            client.insertRecord(tablePengobatan, rowKey, "petugas", "petugasId",
                    pengobatan.getPetugas().getPetugasId());
            client.insertRecord(tablePengobatan, rowKey, "petugas", "nikPetugas",
                    pengobatan.getPetugas().getNikPetugas());
            client.insertRecord(tablePengobatan, rowKey, "petugas", "namaPetugas",
                    pengobatan.getPetugas().getNamaPetugas());
            client.insertRecord(tablePengobatan, rowKey, "petugas", "email",
                    pengobatan.getPetugas().getEmail());
            client.insertRecord(tablePengobatan, rowKey, "petugas", "noTelp",
                    pengobatan.getPetugas().getNoTelp());
            client.insertRecord(tablePengobatan, rowKey, "petugas", "job",
                    pengobatan.getPetugas().getJob());
        }

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
                    if (petugas.getPetugasId() != null) {
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdPengobatan()),
                                "petugas",
                                "petugasId",
                                safeString(petugas.getPetugasId()));
                    }
                    if (petugas.getNikPetugas() != null) {
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdPengobatan()),
                                "petugas",
                                "nikPetugas",
                                safeString(petugas.getNikPetugas()));
                    }
                    if (petugas.getNamaPetugas() != null) {
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdPengobatan()),
                                "petugas",
                                "namaPetugas",
                                safeString(petugas.getNamaPetugas()));
                    }
                    if (petugas.getEmail() != null) {
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdPengobatan()),
                                "petugas",
                                "email",
                                safeString(petugas.getEmail()));
                    }
                    if (petugas.getNoTelp() != null) {
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdPengobatan()),
                                "petugas",
                                "noTelp",
                                safeString(petugas.getNoTelp()));
                    }
                    if (petugas.getJob() != null) {
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdPengobatan()),
                                "petugas",
                                "job",
                                safeString(petugas.getJob()));
                    }
                    if (petugas.getWilayah() != null) {
                        client.insertRecord(tablePengobatan, safeString(pengobatan.getIdPengobatan()),
                                "petugas",
                                "wilayah",
                                safeString(petugas.getWilayah()));
                    }
                }

                String rowKey = pengobatan.getIdPengobatan();

                client.insertRecord(tablePengobatan, rowKey, "main", "idPengobatan",
                        safeString(pengobatan.getIdPengobatan()));
                if (pengobatan.getIdKasus() != null) {
                    client.insertRecord(tablePengobatan, rowKey, "main", "idKasus",
                            safeString(pengobatan.getIdKasus()));
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
                        "Data berhasil disimpan ke HBase dengan ID Pengobatan: " + pengobatan.getIdPengobatan());

            } catch (Exception e) {
                failedRows.add(pengobatan.getIdPengobatan());
                System.err.println(
                        "Failed to insert record for ID: " + pengobatan.getIdPengobatan() + ", Error: "
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
        columnMapping.put("idPengobatan", "idPengobatan");
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
        columnMapping.put("idPengobatan", "idPengobatan");
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
        columnMapping.put("idPengobatan", "idPengobatan");
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

    public Pengobatan updatePetugasByPengobatan(String pengobatanId, Pengobatan pengobatan) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePengobatan = TableName.valueOf(tableName);

        client.insertRecord(tablePengobatan, pengobatanId, "petugas", "nikPetugas",
                pengobatan.getPetugas().getNikPetugas());
        client.insertRecord(tablePengobatan, pengobatanId, "petugas", "namaPetugas",
                pengobatan.getPetugas().getNamaPetugas());
        client.insertRecord(tablePengobatan, pengobatanId, "petugas", "noTelp",
                pengobatan.getPetugas().getNoTelp());
        client.insertRecord(tablePengobatan, pengobatanId, "petugas", "email",
                pengobatan.getPetugas().getEmail());
        client.insertRecord(tablePengobatan, pengobatanId, "main", "job", pengobatan.getPetugas().getJob());
        client.insertRecord(tablePengobatan, pengobatanId, "main", "wilayah",
                pengobatan.getPetugas().getWilayah());
        client.insertRecord(tablePengobatan, pengobatanId, "detail", "created_by", "Polinema");
        return pengobatan;
    }

    public List<Pengobatan> findByPetugasId(String petugasId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePengobatan = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("idPengobatan", "idPengobatan");
        columnMapping.put("petugasId", "petugasId");
        columnMapping.put("petugas", "petugas");

        return client.getDataListByColumn(tablePengobatan.toString(), columnMapping,
                "petugas", "petugasId", petugasId, Pengobatan.class, -1);
    }

    public boolean deleteById(String pengobatanId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        client.deleteRecord(tableName, pengobatanId);
        return true;
    }

    public boolean existsById(String pengobatanId) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePengobatan = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("idPengobatan", "idPengobatan");

        Pengobatan pengobatan = client.getDataByColumn(tablePengobatan.toString(), columnMapping, "main",
                "idPengobatan", pengobatanId,
                Pengobatan.class);
        return pengobatan.getIdPengobatan() != null;
    }
}

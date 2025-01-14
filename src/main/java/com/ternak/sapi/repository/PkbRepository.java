package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Pkb;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.model.User;
import com.ternak.sapi.model.Petugas;
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
        columnMapping.put("rumpunHewan", "rumpunHewan");
        columnMapping.put("jenisHewan", "jenisHewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("jumlah", "jumlah");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("lokasi", "lokasi");
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

    private String safeString(String value) {
        return (value != null && !value.trim().isEmpty()) ? value : "";
    }

    public List<Pkb> saveImport(List<Pkb> pkbList) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);
        TableName tablePkb = TableName.valueOf(tableName);

        System.out.println("Memulai penyimpanan data ke HBase...");
        List<String> failedRows = new ArrayList<>();

        for (Pkb pkb : pkbList) {
            try {

                if (pkb.getPetugas() != null) {
                    Petugas petugas = pkb.getPetugas();
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "petugas",
                            "nikPetugas",
                            safeString(petugas.getNikPetugas()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "petugas",
                            "namaPetugas",
                            safeString(petugas.getNamaPetugas()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "petugas",
                            "email",
                            safeString(petugas.getEmail()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "petugas",
                            "noTelp",
                            safeString(petugas.getNoTelp()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "petugas", "job",
                            safeString(petugas.getJob()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "petugas",
                            "wilayah",
                            safeString(petugas.getWilayah()));
                }

                if (pkb.getPeternak() != null) {
                    Peternak peternak = pkb.getPeternak();
                    if (peternak.getIdPeternak() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "idPeternak",
                                safeString(peternak.getIdPeternak()));
                    }
                    if (peternak.getNikPeternak() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "nikPeternak",
                                safeString(peternak.getNikPeternak()));
                    }
                    if (peternak.getNamaPeternak() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "namaPeternak",
                                safeString(peternak.getNamaPeternak()));
                    }
                    if (peternak.getAlamat() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "alamat",
                                safeString(peternak.getAlamat()));
                    }
                    if (peternak.getLokasi() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "lokasi",
                                safeString(peternak.getLokasi()));
                    }
                    if (peternak.getEmail() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "email",
                                safeString(peternak.getEmail()));
                    }
                    if (peternak.getNoTelepon() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "noTelepon",
                                safeString(peternak.getNoTelepon()));
                    }
                    if (peternak.getJenisKelamin() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "jenisKelamin",
                                safeString(peternak.getJenisKelamin()));
                    }
                    if (peternak.getTanggalLahir() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "tanggalLahir",
                                safeString(peternak.getTanggalLahir()));
                    }
                    if (peternak.getTanggalPendaftaran() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "tanggalPendaftaran",
                                safeString(peternak.getTanggalPendaftaran()));

                    }
                    if (peternak.getIdIsikhnas() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "idIsikhnas",
                                safeString(peternak.getIdIsikhnas()));
                    }
                    if (peternak.getDusun() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak", "dusun",
                                safeString(peternak.getDusun()));
                    }
                    if (peternak.getDesa() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "desa",
                                safeString(peternak.getDesa()));
                    }
                    if (peternak.getKecamatan() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "kecamatan",
                                safeString(peternak.getKecamatan()));
                    }
                    if (peternak.getKabupaten() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "kabupaten",
                                safeString(peternak.getKabupaten()));
                    }
                    if (peternak.getProvinsi() != null) {
                        client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "peternak",
                                "provinsi",
                                safeString(peternak.getProvinsi()));
                    }
                }

                if (pkb.getKandang() != null) {
                    Kandang kandang = pkb.getKandang();
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "kandang", "idKandang",
                            safeString(kandang.getIdKandang()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "kandang", "namaKandang",
                            safeString(kandang.getNamaKandang()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "kandang", "alamat",
                            safeString(kandang.getAlamat()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "kandang", "luas",
                            safeString(kandang.getLuas()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "kandang", "jenisKandang",
                            safeString(kandang.getJenisKandang()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "kandang", "kapasitas",
                            safeString(kandang.getKapasitas()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "kandang", "nilaiBangunan",
                            safeString(kandang.getNilaiBangunan()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "kandang", "latitude",
                            safeString(kandang.getLatitude()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "kandang", "longitude",
                            safeString(kandang.getLongitude()));
                }

                if (pkb.getJenisHewan() != null) {
                    JenisHewan jenisHewan = pkb.getJenisHewan();
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "jenisHewan",
                            "idJenisHewan", safeString(jenisHewan.getIdJenisHewan()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "jenisHewan", "jenis",
                            safeString(jenisHewan.getJenis()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "jenisHewan", "deskripsi",
                            safeString(jenisHewan.getDeskripsi()));
                }

                if (pkb.getRumpunHewan() != null) {
                    RumpunHewan rumpunHewan = pkb.getRumpunHewan();
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "rumpunHewan",
                            "idRumpunHewan", safeString(rumpunHewan.getIdRumpunHewan()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "rumpunHewan", "rumpun",
                            safeString(rumpunHewan.getRumpun()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "rumpunHewan", "deskripsi",
                            safeString(rumpunHewan.getDeskripsi()));
                }

                if (pkb.getHewan() != null) {
                    Hewan hewan = pkb.getHewan();
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "hewan", "idHewan",
                            safeString(hewan.getIdHewan()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "hewan",
                            "kodeEartagNasional", safeString(hewan.getKodeEartagNasional()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "hewan",
                            "noKartuTernak",
                            safeString(hewan.getNoKartuTernak()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "hewan",
                            "identifikasiHewan", safeString(hewan.getIdentifikasiHewan()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "hewan", "sex",
                            safeString(hewan.getSex()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "hewan", "umur",
                            safeString(hewan.getUmur()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "hewan",
                            "tanggalTerdaftar", safeString(hewan.getTanggalTerdaftar()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "hewan",
                            "tanggalLahir",
                            safeString(hewan.getTanggalLahir()));
                    client.insertRecord(tablePkb, safeString(pkb.getIdKejadian()), "hewan",
                            "tempatLahir", safeString(hewan.getTempatLahir()));
                }

                String rowKey = pkb.getIdKejadian();

                client.insertRecord(tablePkb, rowKey, "main", "idKejadian", rowKey);
                if (pkb.getTanggalPkb() != null) {
                    client.insertRecord(tablePkb, rowKey, "main", "tanggalPkb", pkb.getTanggalPkb());
                }
                if (pkb.getSpesies() != null) {
                    client.insertRecord(tablePkb, rowKey, "main", "spesies", pkb.getSpesies());
                }
                if (pkb.getLokasi() != null) {
                    client.insertRecord(tablePkb, rowKey, "main", "lokasi", pkb.getLokasi());
                }
                if (pkb.getUmurKebuntingan() != null) {
                    client.insertRecord(tablePkb, rowKey, "main", "umurKebuntingan", pkb.getUmurKebuntingan());
                }
                if (pkb.getJumlah() != null) {
                    client.insertRecord(tablePkb, rowKey, "main", "jumlah", pkb.getJumlah());
                }

                client.insertRecord(tablePkb, rowKey, "detail", "created_by", "Polinema");

                System.out.println(
                        "Data berhasil disimpan ke HBase dengan ID Kejadian: " + pkb.getIdKejadian());

            } catch (Exception e) {
                failedRows.add(pkb.getIdKejadian());
                System.err.println(
                        "Failed to insert record for ID: " + pkb.getIdKejadian() + ", Error: "
                                + e.getMessage());
            }
        }

        if (!failedRows.isEmpty()) {
            throw new IOException("Failed to save records for ID Kejadian: " + String.join(", ", failedRows));
        }
        return pkbList;
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
        columnMapping.put("rumpunHewan", "rumpunHewan");
        columnMapping.put("jenisHewan", "jenisHewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("jumlah", "jumlah");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("lokasi", "lokasi");
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
        columnMapping.put("rumpunHewan", "rumpunHewan");
        columnMapping.put("jenisHewan", "jenisHewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("jumlah", "jumlah");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("umurKebuntingan", "umurKebuntingan");

        List<Pkb> pkb = client.getDataListByColumn(table.toString(), columnMapping, "peternak", "nikPeternak",
                peternakId, Pkb.class, size);
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
        columnMapping.put("rumpunHewan", "rumpunHewan");
        columnMapping.put("jenisHewan", "jenisHewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("jumlah", "jumlah");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("umurKebuntingan", "umurKebuntingan");

        return client.getDataListByColumn(table.toString(), columnMapping, "petugas", "nikPetugas", petugasId,
                Pkb.class, size);
    }

    public List<Pkb> findPkbByHewan(String idHewan, int size) throws IOException {
        HBaseCustomClient client = new HBaseCustomClient(conf);

        TableName table = TableName.valueOf(tableName);
        Map<String, String> columnMapping = new HashMap<>();
        // Add the mappings to the HashMap
        columnMapping.put("idKejadian", "idKejadian");
        columnMapping.put("tanggalPkb", "tanggalPkb");
        columnMapping.put("peternak", "peternak");
        columnMapping.put("hewan", "hewan");
        columnMapping.put("petugas", "petugas");
        columnMapping.put("rumpunHewan", "rumpunHewan");
        columnMapping.put("jenisHewan", "jenisHewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("jumlah", "jumlah");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("lokasi", "lokasi");
        columnMapping.put("umurKebuntingan", "umurKebuntingan");

        return client.getDataListByColumn(table.toString(), columnMapping, "hewan", "idHewan", idHewan,
                Pkb.class, size);
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
        columnMapping.put("rumpunHewan", "rumpunHewan");
        columnMapping.put("jenisHewan", "jenisHewan");
        columnMapping.put("kandang", "kandang");
        columnMapping.put("jumlah", "jumlah");
        columnMapping.put("spesies", "spesies");
        columnMapping.put("lokasi", "lokasi");
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

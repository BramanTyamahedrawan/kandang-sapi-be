package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Pkb;
import com.ternak.sapi.model.RumpunHewan;
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
                columnMapping.put("idPkb", "idPkb");
                columnMapping.put("idKejadian", "idKejadian");
                columnMapping.put("tanggalPkb", "tanggalPkb");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("umurKebuntingan", "umurKebuntingan");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("kandang", "kandang");

                return client.showListTable(tablePkb.toString(), columnMapping, Pkb.class, size);
        }

        public Pkb save(Pkb pkb) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                String rowKey = pkb.getIdPkb();

                TableName tablePkb = TableName.valueOf(tableName);
                client.insertRecord(tablePkb, rowKey, "main", "idPkb", pkb.getIdPkb());
                if (pkb.getIdKejadian() != null) {
                        client.insertRecord(tablePkb, rowKey, "main", "idKejadian", pkb.getIdKejadian());
                }
                if (pkb.getTanggalPkb() != null) {
                        client.insertRecord(tablePkb, rowKey, "main", "tanggalPkb", pkb.getTanggalPkb());
                }
                if (pkb.getJumlah() != null) {
                        client.insertRecord(tablePkb, rowKey, "main", "jumlah", pkb.getJumlah());
                }
                if (pkb.getUmurKebuntingan() != null) {
                        client.insertRecord(tablePkb, rowKey, "main", "umurKebuntingan", pkb.getUmurKebuntingan());
                }

                // Peternak
                if (pkb.getPeternak() != null) {
                        client.insertRecord(tablePkb, rowKey, "peternak", "idPeternak",
                                        pkb.getPeternak().getIdPeternak());
                        client.insertRecord(tablePkb, rowKey, "peternak", "nikPeternak",
                                        pkb.getPeternak().getNikPeternak());
                        client.insertRecord(tablePkb, rowKey, "peternak", "namaPeternak",
                                        pkb.getPeternak().getNamaPeternak());
                        client.insertRecord(tablePkb, rowKey, "peternak", "idIsikhnas",
                                        pkb.getPeternak().getIdIsikhnas());
                        client.insertRecord(tablePkb, rowKey, "peternak", "email",
                                        pkb.getPeternak().getEmail());
                        client.insertRecord(tablePkb, rowKey, "peternak", "noTelepon",
                                        pkb.getPeternak().getNoTelepon());
                        client.insertRecord(tablePkb, rowKey, "peternak", "alamat",
                                        pkb.getPeternak().getAlamat());
                        client.insertRecord(tablePkb, rowKey, "peternak", "provinsi",
                                        pkb.getPeternak().getProvinsi());
                        client.insertRecord(tablePkb, rowKey, "peternak", "kabupaten",
                                        pkb.getPeternak().getKabupaten());
                        client.insertRecord(tablePkb, rowKey, "peternak", "kecamatan",
                                        pkb.getPeternak().getKecamatan());
                        client.insertRecord(tablePkb, rowKey, "peternak", "desa",
                                        pkb.getPeternak().getDesa());
                }

                // Petugas
                if (pkb.getPetugas() != null) {
                        client.insertRecord(tablePkb, rowKey, "petugas", "petugasId",
                                        pkb.getPetugas().getPetugasId());
                        client.insertRecord(tablePkb, rowKey, "petugas", "nikPetugas",
                                        pkb.getPetugas().getNikPetugas());
                        client.insertRecord(tablePkb, rowKey, "petugas", "namaPetugas",
                                        pkb.getPetugas().getNamaPetugas());
                        client.insertRecord(tablePkb, rowKey, "petugas", "email",
                                        pkb.getPetugas().getEmail());
                        client.insertRecord(tablePkb, rowKey, "petugas", "noTelp",
                                        pkb.getPetugas().getNoTelp());
                        client.insertRecord(tablePkb, rowKey, "petugas", "job",
                                        pkb.getPetugas().getJob());
                }

                // Hewan
                if (pkb.getHewan() != null) {
                        client.insertRecord(tablePkb, rowKey, "hewan", "idHewan",
                                        pkb.getHewan().getIdHewan());
                        client.insertRecord(tablePkb, rowKey, "hewan", "kodeEartagNasional",
                                        pkb.getHewan().getKodeEartagNasional());
                        client.insertRecord(tablePkb, rowKey, "hewan", "noKartuTernak",
                                        pkb.getHewan().getNoKartuTernak());
                        client.insertRecord(tablePkb, rowKey, "hewan", "idIsikhnasTernak",
                                        pkb.getHewan().getIdIsikhnasTernak());
                        client.insertRecord(tablePkb, rowKey, "hewan", "identifikasiHewan",
                                        pkb.getHewan().getIdentifikasiHewan());
                        client.insertRecord(tablePkb, rowKey, "hewan", "umur",
                                        pkb.getHewan().getUmur());
                }

                // Kandang
                if (pkb.getKandang() != null) {
                        client.insertRecord(tablePkb, rowKey, "kandang", "idKandang",
                                        pkb.getKandang().getIdKandang());
                        client.insertRecord(tablePkb, rowKey, "kandang", "namaKandang",
                                        pkb.getKandang().getNamaKandang());
                        client.insertRecord(tablePkb, rowKey, "kandang", "alamat",
                                        pkb.getKandang().getAlamat());
                        client.insertRecord(tablePkb, rowKey, "kandang", "jenisKandang",
                                        pkb.getKandang().getJenisKandang());
                        client.insertRecord(tablePkb, rowKey, "kandang", "luas",
                                        pkb.getKandang().getLuas());
                        client.insertRecord(tablePkb, rowKey, "kandang", "kapasitas",
                                        pkb.getKandang().getKapasitas());
                        client.insertRecord(tablePkb, rowKey, "kandang", "nilaiBangunan",
                                        pkb.getKandang().getNilaiBangunan());
                        client.insertRecord(tablePkb, rowKey, "kandang", "latitude",
                                        pkb.getKandang().getLatitude());
                        client.insertRecord(tablePkb, rowKey, "kandang", "longitude",
                                        pkb.getKandang().getLongitude());
                }

                // Rumpun Hewan
                if (pkb.getRumpunHewan() != null) {
                        client.insertRecord(tablePkb, rowKey, "rumpunHewan", "idRumpunHewan",
                                        pkb.getRumpunHewan().getIdRumpunHewan());
                        client.insertRecord(tablePkb, rowKey, "rumpunHewan", "rumpun",
                                        pkb.getRumpunHewan().getRumpun());
                        client.insertRecord(tablePkb, rowKey, "rumpunHewan", "deskripsi",
                                        pkb.getRumpunHewan().getDeskripsi());
                }

                // Jenis Hewan
                if (pkb.getJenisHewan() != null) {
                        client.insertRecord(tablePkb, rowKey, "jenisHewan", "idJenisHewan",
                                        pkb.getJenisHewan().getIdJenisHewan());
                        client.insertRecord(tablePkb, rowKey, "jenisHewan", "jenis",
                                        pkb.getJenisHewan().getJenis());
                        client.insertRecord(tablePkb, rowKey, "jenisHewan", "deskripsi",
                                        pkb.getJenisHewan().getDeskripsi());
                }
                client.insertRecord(tablePkb, rowKey, "detail", "created_by", "Polinema");
                return pkb;
        }

        public Pkb update(String pkbId, Pkb pkb) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tablePkb = TableName.valueOf(tableName);
                if (pkb.getIdKejadian() != null) {
                        client.insertRecord(tablePkb, pkbId, "main", "idKejadian", pkb.getIdKejadian());
                }
                if (pkb.getTanggalPkb() != null) {
                        client.insertRecord(tablePkb, pkbId, "main", "tanggalPkb", pkb.getTanggalPkb());
                }
                if (pkb.getJumlah() != null) {
                        client.insertRecord(tablePkb, pkbId, "main", "jumlah", pkb.getJumlah());
                }
                if (pkb.getUmurKebuntingan() != null) {
                        client.insertRecord(tablePkb, pkbId, "main", "umurKebuntingan", pkb.getUmurKebuntingan());
                }

                // Peternak
                if (pkb.getPeternak() != null) {
                        client.insertRecord(tablePkb, pkbId, "peternak", "idPeternak",
                                        pkb.getPeternak().getIdPeternak());
                        client.insertRecord(tablePkb, pkbId, "peternak", "nikPeternak",
                                        pkb.getPeternak().getNikPeternak());
                        client.insertRecord(tablePkb, pkbId, "peternak", "namaPeternak",
                                        pkb.getPeternak().getNamaPeternak());
                        client.insertRecord(tablePkb, pkbId, "peternak", "idIsikhnas",
                                        pkb.getPeternak().getIdIsikhnas());
                        client.insertRecord(tablePkb, pkbId, "peternak", "email",
                                        pkb.getPeternak().getEmail());
                        client.insertRecord(tablePkb, pkbId, "peternak", "noTelepon",
                                        pkb.getPeternak().getNoTelepon());
                        client.insertRecord(tablePkb, pkbId, "peternak", "alamat",
                                        pkb.getPeternak().getAlamat());
                        client.insertRecord(tablePkb, pkbId, "peternak", "provinsi",
                                        pkb.getPeternak().getProvinsi());
                        client.insertRecord(tablePkb, pkbId, "peternak", "kabupaten",
                                        pkb.getPeternak().getKabupaten());
                        client.insertRecord(tablePkb, pkbId, "peternak", "kecamatan",
                                        pkb.getPeternak().getKecamatan());
                        client.insertRecord(tablePkb, pkbId, "peternak", "desa",
                                        pkb.getPeternak().getDesa());
                }

                // Petugas
                if (pkb.getPetugas() != null) {
                        client.insertRecord(tablePkb, pkbId, "petugas", "petugasId",
                                        pkb.getPetugas().getPetugasId());
                        client.insertRecord(tablePkb, pkbId, "petugas", "nikPetugas",
                                        pkb.getPetugas().getNikPetugas());
                        client.insertRecord(tablePkb, pkbId, "petugas", "namaPetugas",
                                        pkb.getPetugas().getNamaPetugas());
                        client.insertRecord(tablePkb, pkbId, "petugas", "email",
                                        pkb.getPetugas().getEmail());
                        client.insertRecord(tablePkb, pkbId, "petugas", "noTelp",
                                        pkb.getPetugas().getNoTelp());
                        client.insertRecord(tablePkb, pkbId, "petugas", "job",
                                        pkb.getPetugas().getJob());
                }

                // Hewan
                if (pkb.getHewan() != null) {
                        client.insertRecord(tablePkb, pkbId, "hewan", "idHewan",
                                        pkb.getHewan().getIdHewan());
                        client.insertRecord(tablePkb, pkbId, "hewan", "kodeEartagNasional",
                                        pkb.getHewan().getKodeEartagNasional());
                        client.insertRecord(tablePkb, pkbId, "hewan", "noKartuTernak",
                                        pkb.getHewan().getNoKartuTernak());
                        client.insertRecord(tablePkb, pkbId, "hewan", "idIsikhnasTernak",
                                        pkb.getHewan().getIdIsikhnasTernak());
                        client.insertRecord(tablePkb, pkbId, "hewan", "identifikasiHewan",
                                        pkb.getHewan().getIdentifikasiHewan());
                        client.insertRecord(tablePkb, pkbId, "hewan", "umur",
                                        pkb.getHewan().getUmur());
                }

                // Kandang
                if (pkb.getKandang() != null) {
                        client.insertRecord(tablePkb, pkbId, "kandang", "idKandang",
                                        pkb.getKandang().getIdKandang());
                        client.insertRecord(tablePkb, pkbId, "kandang", "namaKandang",
                                        pkb.getKandang().getNamaKandang());
                        client.insertRecord(tablePkb, pkbId, "kandang", "alamat",
                                        pkb.getKandang().getAlamat());
                        client.insertRecord(tablePkb, pkbId, "kandang", "jenisKandang",
                                        pkb.getKandang().getJenisKandang());
                        client.insertRecord(tablePkb, pkbId, "kandang", "luas",
                                        pkb.getKandang().getLuas());
                        client.insertRecord(tablePkb, pkbId, "kandang", "kapasitas",
                                        pkb.getKandang().getKapasitas());
                        client.insertRecord(tablePkb, pkbId, "kandang", "nilaiBangunan",
                                        pkb.getKandang().getNilaiBangunan());
                        client.insertRecord(tablePkb, pkbId, "kandang", "latitude",
                                        pkb.getKandang().getLatitude());
                        client.insertRecord(tablePkb, pkbId, "kandang", "longitude",
                                        pkb.getKandang().getLongitude());
                }

                // Rumpun Hewan
                if (pkb.getRumpunHewan() != null) {
                        client.insertRecord(tablePkb, pkbId, "rumpunHewan", "idRumpunHewan",
                                        pkb.getRumpunHewan().getIdRumpunHewan());
                        client.insertRecord(tablePkb, pkbId, "rumpunHewan", "rumpun",
                                        pkb.getRumpunHewan().getRumpun());
                        client.insertRecord(tablePkb, pkbId, "rumpunHewan", "deskripsi",
                                        pkb.getRumpunHewan().getDeskripsi());
                }

                // Jenis Hewan
                if (pkb.getJenisHewan() != null) {
                        client.insertRecord(tablePkb, pkbId, "jenisHewan", "idJenisHewan",
                                        pkb.getJenisHewan().getIdJenisHewan());
                        client.insertRecord(tablePkb, pkbId, "jenisHewan", "jenis",
                                        pkb.getJenisHewan().getJenis());
                        client.insertRecord(tablePkb, pkbId, "jenisHewan", "deskripsi",
                                        pkb.getJenisHewan().getDeskripsi());
                }

                client.insertRecord(tablePkb, pkbId, "detail", "created_by", "Polinema");
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
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "petugas",
                                                        "petugasId",
                                                        safeString(petugas.getPetugasId()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "petugas",
                                                        "nikPetugas",
                                                        safeString(petugas.getNikPetugas()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "petugas",
                                                        "namaPetugas",
                                                        safeString(petugas.getNamaPetugas()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "petugas",
                                                        "email",
                                                        safeString(petugas.getEmail()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "petugas",
                                                        "noTelp",
                                                        safeString(petugas.getNoTelp()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "petugas", "job",
                                                        safeString(petugas.getJob()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "petugas",
                                                        "wilayah",
                                                        safeString(petugas.getWilayah()));
                                }

                                if (pkb.getPeternak() != null) {
                                        Peternak peternak = pkb.getPeternak();
                                        if (peternak.getIdPeternak() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "idPeternak",
                                                                safeString(peternak.getIdPeternak()));
                                        }
                                        if (peternak.getNikPeternak() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "nikPeternak",
                                                                safeString(peternak.getNikPeternak()));
                                        }
                                        if (peternak.getNamaPeternak() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "namaPeternak",
                                                                safeString(peternak.getNamaPeternak()));
                                        }
                                        if (peternak.getAlamat() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "alamat",
                                                                safeString(peternak.getAlamat()));
                                        }
                                        if (peternak.getLokasi() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "lokasi",
                                                                safeString(peternak.getLokasi()));
                                        }
                                        if (peternak.getEmail() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "email",
                                                                safeString(peternak.getEmail()));
                                        }
                                        if (peternak.getNoTelepon() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "noTelepon",
                                                                safeString(peternak.getNoTelepon()));
                                        }
                                        if (peternak.getJenisKelamin() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "jenisKelamin",
                                                                safeString(peternak.getJenisKelamin()));
                                        }
                                        if (peternak.getTanggalLahir() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "tanggalLahir",
                                                                safeString(peternak.getTanggalLahir()));
                                        }
                                        if (peternak.getTanggalPendaftaran() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "tanggalPendaftaran",
                                                                safeString(peternak.getTanggalPendaftaran()));

                                        }
                                        if (peternak.getIdIsikhnas() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "idIsikhnas",
                                                                safeString(peternak.getIdIsikhnas()));
                                        }
                                        if (peternak.getDusun() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak", "dusun",
                                                                safeString(peternak.getDusun()));
                                        }
                                        if (peternak.getDesa() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "desa",
                                                                safeString(peternak.getDesa()));
                                        }
                                        if (peternak.getKecamatan() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "kecamatan",
                                                                safeString(peternak.getKecamatan()));
                                        }
                                        if (peternak.getKabupaten() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "kabupaten",
                                                                safeString(peternak.getKabupaten()));
                                        }
                                        if (peternak.getProvinsi() != null) {
                                                client.insertRecord(tablePkb, safeString(pkb.getIdPkb()),
                                                                "peternak",
                                                                "provinsi",
                                                                safeString(peternak.getProvinsi()));
                                        }
                                }

                                if (pkb.getKandang() != null) {
                                        Kandang kandang = pkb.getKandang();
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "kandang",
                                                        "idKandang",
                                                        safeString(kandang.getIdKandang()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "kandang",
                                                        "namaKandang",
                                                        safeString(kandang.getNamaKandang()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "kandang",
                                                        "alamat",
                                                        safeString(kandang.getAlamat()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "kandang",
                                                        "luas",
                                                        safeString(kandang.getLuas()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "kandang",
                                                        "jenisKandang",
                                                        safeString(kandang.getJenisKandang()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "kandang",
                                                        "kapasitas",
                                                        safeString(kandang.getKapasitas()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "kandang",
                                                        "nilaiBangunan",
                                                        safeString(kandang.getNilaiBangunan()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "kandang",
                                                        "latitude",
                                                        safeString(kandang.getLatitude()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "kandang",
                                                        "longitude",
                                                        safeString(kandang.getLongitude()));
                                }

                                if (pkb.getJenisHewan() != null) {
                                        JenisHewan jenisHewan = pkb.getJenisHewan();
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "jenisHewan",
                                                        "idJenisHewan", safeString(jenisHewan.getIdJenisHewan()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "jenisHewan",
                                                        "jenis",
                                                        safeString(jenisHewan.getJenis()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "jenisHewan",
                                                        "deskripsi",
                                                        safeString(jenisHewan.getDeskripsi()));
                                }

                                if (pkb.getRumpunHewan() != null) {
                                        RumpunHewan rumpunHewan = pkb.getRumpunHewan();
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "rumpunHewan",
                                                        "idRumpunHewan", safeString(rumpunHewan.getIdRumpunHewan()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "rumpunHewan",
                                                        "rumpun",
                                                        safeString(rumpunHewan.getRumpun()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "rumpunHewan",
                                                        "deskripsi",
                                                        safeString(rumpunHewan.getDeskripsi()));
                                }

                                if (pkb.getHewan() != null) {
                                        Hewan hewan = pkb.getHewan();
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "hewan",
                                                        "idHewan",
                                                        safeString(hewan.getIdHewan()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "hewan",
                                                        "kodeEartagNasional",
                                                        safeString(hewan.getKodeEartagNasional()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "hewan",
                                                        "noKartuTernak",
                                                        safeString(hewan.getNoKartuTernak()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "hewan",
                                                        "identifikasiHewan", safeString(hewan.getIdentifikasiHewan()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "hewan", "sex",
                                                        safeString(hewan.getSex()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "hewan", "umur",
                                                        safeString(hewan.getUmur()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "hewan",
                                                        "tanggalTerdaftar", safeString(hewan.getTanggalTerdaftar()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "hewan",
                                                        "tanggalLahir",
                                                        safeString(hewan.getTanggalLahir()));
                                        client.insertRecord(tablePkb, safeString(pkb.getIdPkb()), "hewan",
                                                        "tempatLahir", safeString(hewan.getTempatLahir()));
                                }

                                String rowKey = pkb.getIdPkb();

                                client.insertRecord(tablePkb, rowKey, "main", "idPkb", pkb.getIdPkb());
                                if (pkb.getIdKejadian() != null) {
                                        client.insertRecord(tablePkb, rowKey, "main", "idKejadian",
                                                        pkb.getIdKejadian());

                                }
                                if (pkb.getTanggalPkb() != null) {
                                        client.insertRecord(tablePkb, rowKey, "main", "tanggalPkb",
                                                        pkb.getTanggalPkb());
                                }
                                if (pkb.getUmurKebuntingan() != null) {
                                        client.insertRecord(tablePkb, rowKey, "main", "umurKebuntingan",
                                                        pkb.getUmurKebuntingan());
                                }
                                if (pkb.getJumlah() != null) {
                                        client.insertRecord(tablePkb, rowKey, "main", "jumlah", pkb.getJumlah());
                                }

                                client.insertRecord(tablePkb, rowKey, "detail", "created_by", "Polinema");

                                System.out.println(
                                                "Data berhasil disimpan ke HBase dengan ID PKB: "
                                                                + pkb.getIdPkb());

                        } catch (Exception e) {
                                failedRows.add(pkb.getIdPkb());
                                System.err.println(
                                                "Failed to insert record for ID: " + pkb.getIdPkb() + ", Error: "
                                                                + e.getMessage());
                        }
                }

                if (!failedRows.isEmpty()) {
                        throw new IOException(
                                        "Failed to save records for ID PKB: " + String.join(", ", failedRows));
                }
                return pkbList;
        }

        public Pkb findPkbById(String pkbId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tablePkb = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idPkb", "idPkb");
                columnMapping.put("idKejadian", "idKejadian");
                columnMapping.put("tanggalPkb", "tanggalPkb");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("umurKebuntingan", "umurKebuntingan");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("kandang", "kandang");

                return client.showDataTable(tablePkb.toString(), columnMapping, pkbId, Pkb.class);
        }

        public List<Pkb> findPkbByPeternak(String peternakId, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idPkb", "idPkb");
                columnMapping.put("idKejadian", "idKejadian");
                columnMapping.put("tanggalPkb", "tanggalPkb");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("umurKebuntingan", "umurKebuntingan");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("kandang", "kandang");

                List<Pkb> pkb = client.getDataListByColumn(table.toString(), columnMapping, "peternak", "nikPeternak",
                                peternakId, Pkb.class, size);
                return pkb;
        }

        public List<Pkb> findPkbByPetugas(String petugasId, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idPkb", "idPkb");
                columnMapping.put("idKejadian", "idKejadian");
                columnMapping.put("tanggalPkb", "tanggalPkb");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("umurKebuntingan", "umurKebuntingan");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("kandang", "kandang");

                return client.getDataListByColumn(table.toString(), columnMapping, "petugas", "nikPetugas", petugasId,
                                Pkb.class, size);
        }

        public List<Pkb> findPkbByHewan(String idHewan, int size) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idPkb", "idPkb");
                columnMapping.put("idKejadian", "idKejadian");
                columnMapping.put("tanggalPkb", "tanggalPkb");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("umurKebuntingan", "umurKebuntingan");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("kandang", "kandang");

                return client.getDataListByColumn(table.toString(), columnMapping, "hewan", "idHewan", idHewan,
                                Pkb.class, size);
        }

        public List<Pkb> findAllById(List<String> pkbIds) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName table = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                // Add the mappings to the HashMap
                columnMapping.put("idPkb", "idPkb");
                columnMapping.put("idKejadian", "idKejadian");
                columnMapping.put("tanggalPkb", "tanggalPkb");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("umurKebuntingan", "umurKebuntingan");
                columnMapping.put("peternak", "peternak");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("petugas", "petugas");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("kandang", "kandang");

                List<Pkb> pkbs = new ArrayList<>();
                for (String pkbId : pkbIds) {
                        Pkb pkb = client.showDataTable(table.toString(), columnMapping, pkbId, Pkb.class);
                        if (pkb != null) {
                                pkbs.add(pkb);
                        }
                }

                return pkbs;
        }

        public boolean deleteById(String pkbId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                client.deleteRecord(tableName, pkbId);
                return true;
        }

        public boolean existsById(String pkbId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tablePkb = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idPkb", "idPkb");

                Pkb pkb = client.getDataByColumn(tablePkb.toString(), columnMapping, "main",
                                "idPkb", pkbId,
                                Pkb.class);
                return pkb.getIdPkb() != null; // True jika ID Hewan sudah ada
        }
}

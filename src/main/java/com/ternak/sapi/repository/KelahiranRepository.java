package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.model.Inseminasi;

// import com.ternak.sapi.model.User;
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
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("inseminasi", "inseminasi");
                columnMapping.put("eartagAnak", "eartagAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
                columnMapping.put("lokasi", "lokasi");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("idHewanAnak", "idHewanAnak");
                columnMapping.put("urutanIB", "urutanIB");

                return client.showListTable(tableKelahiran.toString(), columnMapping, Kelahiran.class, size);
        }

        public Kelahiran save(Kelahiran kelahiran) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                String rowKey = kelahiran.getIdKejadian();

                TableName tableKelahiran = TableName.valueOf(tableName);
                client.insertRecord(tableKelahiran, rowKey, "main", "idKejadian", rowKey);
                if (kelahiran.getTanggalLaporan() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "tanggalLaporan",
                                        kelahiran.getTanggalLaporan());
                }
                if (kelahiran.getTanggalLahir() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "tanggalLahir",
                                        kelahiran.getTanggalLahir());
                }
                if (kelahiran.getEartagAnak() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "eartagAnak", kelahiran.getEartagAnak());
                }
                if (kelahiran.getJenisKelaminAnak() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "jenisKelaminAnak",
                                        kelahiran.getJenisKelaminAnak());
                }
                if (kelahiran.getSpesies() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "spesies", kelahiran.getSpesies());
                }
                client.insertRecord(tableKelahiran, rowKey, "peternak", "idPeternak",
                                kelahiran.getPeternak().getIdPeternak());
                client.insertRecord(tableKelahiran, rowKey, "peternak", "nikPeternak",
                                kelahiran.getPeternak().getNikPeternak());
                client.insertRecord(tableKelahiran, rowKey, "peternak", "namaPeternak",
                                kelahiran.getPeternak().getNamaPeternak());
                client.insertRecord(tableKelahiran, rowKey, "peternak", "lokasi", kelahiran.getPeternak().getLokasi());
                client.insertRecord(tableKelahiran, rowKey, "petugas", "nikPetugas",
                                kelahiran.getPetugas().getNikPetugas());
                client.insertRecord(tableKelahiran, rowKey, "petugas", "namaPetugas",
                                kelahiran.getPetugas().getNamaPetugas());
                client.insertRecord(tableKelahiran, rowKey, "hewan", "kodeEartagNasional",
                                kelahiran.getHewan().getKodeEartagNasional());
                client.insertRecord(tableKelahiran, rowKey, "kandang", "idKandang",
                                kelahiran.getKandang().getIdKandang());
                client.insertRecord(tableKelahiran, rowKey, "inseminasi", "idInseminasi",
                                kelahiran.getInseminasi().getIdInseminasi());
                client.insertRecord(tableKelahiran, rowKey, "inseminasi", "idPejantan",
                                kelahiran.getInseminasi().getIdPejantan());
                client.insertRecord(tableKelahiran, rowKey, "inseminasi", "idPembuatan",
                                kelahiran.getInseminasi().getIdPembuatan());
                client.insertRecord(tableKelahiran, rowKey, "inseminasi", "produsen",
                                kelahiran.getInseminasi().getProdusen());
                client.insertRecord(tableKelahiran, rowKey, "inseminasi", "bangsaPejantan",
                                kelahiran.getInseminasi().getBangsaPejantan());
                client.insertRecord(tableKelahiran, rowKey, "inseminasi", "ib", kelahiran.getInseminasi().getIb1());
                client.insertRecord(tableKelahiran, rowKey, "inseminasi", "tanggalIB",
                                kelahiran.getInseminasi().getTanggalIB());

                client.insertRecord(tableKelahiran, rowKey, "detail", "created_by", "Polinema");
                return kelahiran;
        }

        private String safeString(String value) {
                return (value != null && !value.trim().isEmpty()) ? value : "";
        }

        public List<Kelahiran> saveBulkImport(List<Kelahiran> kelahiranList) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);

                System.out.println("Memulai penyimpanan data ke HBase...");
                List<String> failedRows = new ArrayList<>();

                for (Kelahiran kelahiran : kelahiranList) {
                        try {
                                if (kelahiran.getPetugas() != null) {
                                        Petugas petugas = kelahiran.getPetugas();
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "petugas",
                                                        "nikPetugas",
                                                        safeString(petugas.getNikPetugas()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "petugas",
                                                        "namaPetugas",
                                                        safeString(petugas.getNamaPetugas()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "petugas",
                                                        "email",
                                                        safeString(petugas.getEmail()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "petugas",
                                                        "noTelp",
                                                        safeString(petugas.getNoTelp()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "petugas", "job",
                                                        safeString(petugas.getJob()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "petugas",
                                                        "wilayah",
                                                        safeString(petugas.getWilayah()));
                                }

                                if (kelahiran.getPeternak() != null) {
                                        Peternak peternak = kelahiran.getPeternak();
                                        if (peternak.getIdPeternak() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "idPeternak",
                                                                safeString(peternak.getIdPeternak()));
                                        }
                                        if (peternak.getNikPeternak() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "nikPeternak",
                                                                safeString(peternak.getNikPeternak()));
                                        }
                                        if (peternak.getNamaPeternak() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "namaPeternak",
                                                                safeString(peternak.getNamaPeternak()));
                                        }
                                        if (peternak.getAlamat() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "alamat",
                                                                safeString(peternak.getAlamat()));
                                        }
                                        if (peternak.getLokasi() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "lokasi",
                                                                safeString(peternak.getLokasi()));
                                        }
                                        if (peternak.getEmail() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "email",
                                                                safeString(peternak.getEmail()));
                                        }
                                        if (peternak.getNoTelepon() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "noTelepon",
                                                                safeString(peternak.getNoTelepon()));
                                        }
                                        if (peternak.getJenisKelamin() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "jenisKelamin",
                                                                safeString(peternak.getJenisKelamin()));
                                        }
                                        if (peternak.getTanggalLahir() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "tanggalLahir",
                                                                safeString(peternak.getTanggalLahir()));
                                        }
                                        if (peternak.getTanggalPendaftaran() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "tanggalPendaftaran",
                                                                safeString(peternak.getTanggalPendaftaran()));

                                        }
                                        if (peternak.getIdIsikhnas() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "idIsikhnas",
                                                                safeString(peternak.getIdIsikhnas()));
                                        }
                                        if (peternak.getDusun() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak", "dusun",
                                                                safeString(peternak.getDusun()));
                                        }
                                        if (peternak.getDesa() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "desa",
                                                                safeString(peternak.getDesa()));
                                        }
                                        if (peternak.getKecamatan() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "kecamatan",
                                                                safeString(peternak.getKecamatan()));
                                        }
                                        if (peternak.getKabupaten() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "kabupaten",
                                                                safeString(peternak.getKabupaten()));
                                        }
                                        if (peternak.getProvinsi() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKejadian()),
                                                                "peternak",
                                                                "provinsi",
                                                                safeString(peternak.getProvinsi()));
                                        }
                                }

                                if (kelahiran.getKandang() != null) {
                                        Kandang kandang = kelahiran.getKandang();
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "kandang",
                                                        "idKandang",
                                                        safeString(kandang.getIdKandang()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "kandang",
                                                        "namaKandang",
                                                        safeString(kandang.getNamaKandang()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "kandang",
                                                        "alamat",
                                                        safeString(kandang.getAlamat()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "kandang",
                                                        "luas",
                                                        safeString(kandang.getLuas()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "kandang",
                                                        "jenisKandang",
                                                        safeString(kandang.getJenisKandang()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "kandang",
                                                        "kapasitas",
                                                        safeString(kandang.getKapasitas()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "kandang",
                                                        "nilaiBangunan",
                                                        safeString(kandang.getNilaiBangunan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "kandang",
                                                        "latitude",
                                                        safeString(kandang.getLatitude()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "kandang",
                                                        "longitude",
                                                        safeString(kandang.getLongitude()));
                                }

                                if (kelahiran.getJenisHewan() != null) {
                                        JenisHewan jenisHewan = kelahiran.getJenisHewan();
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "jenisHewan",
                                                        "idJenisHewan", safeString(jenisHewan.getIdJenisHewan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "jenisHewan",
                                                        "jenis",
                                                        safeString(jenisHewan.getJenis()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "jenisHewan",
                                                        "deskripsi",
                                                        safeString(jenisHewan.getDeskripsi()));
                                }

                                if (kelahiran.getRumpunHewan() != null) {
                                        RumpunHewan rumpunHewan = kelahiran.getRumpunHewan();
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "rumpunHewan",
                                                        "idRumpunHewan", safeString(rumpunHewan.getIdRumpunHewan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "rumpunHewan",
                                                        "rumpun",
                                                        safeString(rumpunHewan.getRumpun()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "rumpunHewan",
                                                        "deskripsi",
                                                        safeString(rumpunHewan.getDeskripsi()));
                                }

                                if (kelahiran.getHewan() != null) {
                                        Hewan hewan = kelahiran.getHewan();
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "hewan",
                                                        "idHewan",
                                                        safeString(hewan.getIdHewan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "hewan",
                                                        "kodeEartagNasional",
                                                        safeString(hewan.getKodeEartagNasional()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "hewan",
                                                        "noKartuTernak",
                                                        safeString(hewan.getNoKartuTernak()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "hewan",
                                                        "identifikasiHewan", safeString(hewan.getIdentifikasiHewan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "hewan",
                                                        "sex",
                                                        safeString(hewan.getSex()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "hewan",
                                                        "umur",
                                                        safeString(hewan.getUmur()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "hewan",
                                                        "tanggalTerdaftar", safeString(hewan.getTanggalTerdaftar()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "hewan",
                                                        "tanggalLahir",
                                                        safeString(hewan.getTanggalLahir()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "hewan",
                                                        "tempatLahir", safeString(hewan.getTempatLahir()));
                                }

                                if (kelahiran.getInseminasi() != null) {
                                        Inseminasi inseminasi = kelahiran.getInseminasi();
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "inseminasi",
                                                        "idInseminasi",
                                                        safeString(inseminasi.getIdInseminasi()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "inseminasi",
                                                        "idPejantan",
                                                        safeString(inseminasi.getIdPejantan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "inseminasi", "idPembuatan",
                                                        safeString(inseminasi.getIdPembuatan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "inseminasi", "tanggalIB",
                                                        safeString(inseminasi.getTanggalIB()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "inseminasi",
                                                        "produsen",
                                                        safeString(inseminasi.getProdusen()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "inseminasi",
                                                        "bangsaPejantan",
                                                        safeString(inseminasi.getBangsaPejantan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "inseminasi", "ib1",
                                                        safeString(inseminasi.getIb1()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "inseminasi", "ib2", safeString(inseminasi.getIb2()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "inseminasi", "ib3", safeString(inseminasi.getIb3()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKejadian()),
                                                        "inseminasi", "ibLain", safeString(inseminasi.getIbLain()));
                                }

                                String rowKey = kelahiran.getIdKejadian();

                                client.insertRecord(tableKelahiran, rowKey, "main", "idKejadian", rowKey);
                                if (kelahiran.getTanggalLaporan() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "tanggalLaporan",
                                                        kelahiran.getTanggalLaporan());
                                }
                                if (kelahiran.getTanggalLahir() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "tanggalLahir",
                                                        kelahiran.getTanggalLahir());
                                }
                                if (kelahiran.getEartagAnak() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "eartagAnak",
                                                        kelahiran.getEartagAnak());
                                }
                                if (kelahiran.getJenisKelaminAnak() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "jenisKelaminAnak",
                                                        kelahiran.getJenisKelaminAnak());
                                }
                                if (kelahiran.getSpesies() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "spesies",
                                                        kelahiran.getSpesies());
                                }
                                if (kelahiran.getKategori() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "kategori",
                                                        kelahiran.getKategori());
                                }
                                if (kelahiran.getLokasi() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "lokasi",
                                                        kelahiran.getLokasi());
                                }
                                if (kelahiran.getJumlah() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "jumlah",
                                                        kelahiran.getJumlah());
                                }
                                if (kelahiran.getIdHewanAnak() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "idHewanAnak",
                                                        kelahiran.getIdHewanAnak());
                                }
                                if (kelahiran.getUrutanIB() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "urutanIB",
                                                        kelahiran.getUrutanIB());
                                }
                                client.insertRecord(tableKelahiran, rowKey, "detail", "created_by", "Polinema");

                                System.out.println(
                                                "Data berhasil disimpan ke HBase dengan ID Kejadian (kelahiran): "
                                                                + kelahiran.getIdKejadian());

                        } catch (Exception e) {
                                failedRows.add(kelahiran.getIdKejadian());
                                System.err.println(
                                                "Failed to insert record for ID: " + kelahiran.getIdKejadian()
                                                                + ", Error: " + e.getMessage());
                        }
                }

                if (!failedRows.isEmpty()) {
                        throw new IOException(
                                        "Failed to save records for ID Kejadian: " + String.join(", ", failedRows));
                }

                return kelahiranList;
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
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("inseminasi", "inseminasi");
                columnMapping.put("eartagAnak", "eartagAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
                columnMapping.put("lokasi", "lokasi");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("idHewanAnak", "idHewanAnak");
                columnMapping.put("urutanIB", "urutanIB");

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
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("inseminasi", "inseminasi");
                columnMapping.put("eartagAnak", "eartagAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
                columnMapping.put("lokasi", "lokasi");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("idHewanAnak", "idHewanAnak");
                columnMapping.put("urutanIB", "urutanIB");

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
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("inseminasi", "inseminasi");
                columnMapping.put("eartagAnak", "eartagAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
                columnMapping.put("lokasi", "lokasi");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("idHewanAnak", "idHewanAnak");
                columnMapping.put("urutanIB", "urutanIB");

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
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("inseminasi", "inseminasi");
                columnMapping.put("eartagAnak", "eartagAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
                columnMapping.put("lokasi", "lokasi");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("idHewanAnak", "idHewanAnak");
                columnMapping.put("urutanIB", "urutanIB");

                return client.getDataListByColumn(table.toString(), columnMapping, "hewan", "kodeEartagNasional",
                                hewanId,
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
                columnMapping.put("jenisHewan", "jenisHewan");
                columnMapping.put("rumpunHewan", "rumpunHewan");
                columnMapping.put("hewan", "hewan");
                columnMapping.put("kandang", "kandang");
                columnMapping.put("inseminasi", "inseminasi");
                columnMapping.put("eartagAnak", "eartagAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
                columnMapping.put("lokasi", "lokasi");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("idHewanAnak", "idHewanAnak");
                columnMapping.put("urutanIB", "urutanIB");

                List<Kelahiran> kelahirans = new ArrayList<>();
                for (String kelahiranId : kelahiranIds) {
                        Kelahiran kelahiran = client.showDataTable(table.toString(), columnMapping, kelahiranId,
                                        Kelahiran.class);
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
                        client.insertRecord(tableKelahiran, kelahiranId, "main", "tanggalLaporan",
                                        kelahiran.getTanggalLaporan());
                }
                if (kelahiran.getTanggalLahir() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "main", "tanggalLahir",
                                        kelahiran.getTanggalLahir());
                }
                if (kelahiran.getEartagAnak() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "main", "eartagAnak",
                                        kelahiran.getEartagAnak());
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
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "lokasi",
                                kelahiran.getPeternak().getLokasi());
                client.insertRecord(tableKelahiran, kelahiranId, "petugas", "nikPetugas",
                                kelahiran.getPetugas().getNikPetugas());
                client.insertRecord(tableKelahiran, kelahiranId, "petugas", "namaPetugas",
                                kelahiran.getPetugas().getNamaPetugas());
                client.insertRecord(tableKelahiran, kelahiranId, "hewan", "kodeEartagNasional",
                                kelahiran.getHewan().getKodeEartagNasional());
                client.insertRecord(tableKelahiran, kelahiranId, "kandang", "idKandang",
                                kelahiran.getKandang().getIdKandang());
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
                client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "ib",
                                kelahiran.getInseminasi().getIb1());
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

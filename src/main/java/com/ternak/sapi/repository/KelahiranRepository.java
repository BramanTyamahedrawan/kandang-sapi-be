package com.ternak.sapi.repository;

import com.ternak.sapi.helper.HBaseCustomClient;
import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.RumpunHewan;
import com.ternak.sapi.model.Vaksin;
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
                columnMapping.put("idKelahiran", "idKelahiran");
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
                columnMapping.put("noKartuTernakAnak", "noKartuTernakAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("idHewanAnak", "idHewanAnak");
                columnMapping.put("urutanIB", "urutanIB");

                return client.showListTable(tableKelahiran.toString(), columnMapping, Kelahiran.class, size);
        }

        public Kelahiran save(Kelahiran kelahiran) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                String rowKey = kelahiran.getIdKelahiran();

                TableName tableKelahiran = TableName.valueOf(tableName);
                client.insertRecord(tableKelahiran, rowKey, "main", "idKelahiran", rowKey);
                if (kelahiran.getIdKejadian() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "idKejadian", kelahiran.getIdKejadian());

                }
                if (kelahiran.getTanggalLaporan() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "tanggalLaporan",
                                        kelahiran.getTanggalLaporan());
                }
                if (kelahiran.getTanggalLahir() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "tanggalLahir",
                                        kelahiran.getTanggalLahir());
                }
                if (kelahiran.getIdHewanAnak() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "idHewanAnak", kelahiran.getIdHewanAnak());
                }
                if (kelahiran.getNoKartuTernakAnak() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "noKartuTernakAnak",
                                        kelahiran.getNoKartuTernakAnak());
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
                if (kelahiran.getKategori() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "kategori", kelahiran.getKategori());
                }
                if (kelahiran.getJumlah() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "jumlah", kelahiran.getJumlah());
                }
                if (kelahiran.getUrutanIB() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "main", "urutanIB", kelahiran.getUrutanIB());
                }

                // Peternak
                if (kelahiran.getPeternak() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "idPeternak",
                                        kelahiran.getPeternak().getIdPeternak());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "nikPeternak",
                                        kelahiran.getPeternak().getNikPeternak());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "namaPeternak",
                                        kelahiran.getPeternak().getNamaPeternak());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "alamat",
                                        kelahiran.getPeternak().getAlamat());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "email",
                                        kelahiran.getPeternak().getEmail());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "noTelepon",
                                        kelahiran.getPeternak().getNoTelepon());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "tanggalLahir",
                                        kelahiran.getPeternak().getTanggalLahir());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "tanggalPendaftaran",
                                        kelahiran.getPeternak().getTanggalPendaftaran());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "idIsikhnas",
                                        kelahiran.getPeternak().getIdIsikhnas());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "dusun",
                                        kelahiran.getPeternak().getDusun());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "desa",
                                        kelahiran.getPeternak().getDesa());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "kecamatan",
                                        kelahiran.getPeternak().getKecamatan());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "kabupaten",
                                        kelahiran.getPeternak().getKabupaten());
                        client.insertRecord(tableKelahiran, rowKey, "peternak", "provinsi",
                                        kelahiran.getPeternak().getProvinsi());

                }

                // Petugas
                if (kelahiran.getPetugas() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "petugas", "petugasId",
                                        kelahiran.getPetugas().getPetugasId());
                        client.insertRecord(tableKelahiran, rowKey, "petugas", "nikPetugas",
                                        kelahiran.getPetugas().getNikPetugas());
                        client.insertRecord(tableKelahiran, rowKey, "petugas", "namaPetugas",
                                        kelahiran.getPetugas().getNamaPetugas());
                        client.insertRecord(tableKelahiran, rowKey, "petugas", "job",
                                        kelahiran.getPetugas().getJob());
                        client.insertRecord(tableKelahiran, rowKey, "petugas", "email",
                                        kelahiran.getPetugas().getEmail());
                        client.insertRecord(tableKelahiran, rowKey, "petugas", "noTelp",
                                        kelahiran.getPetugas().getNoTelp());
                        client.insertRecord(tableKelahiran, rowKey, "petugas", "wilayah",
                                        kelahiran.getPetugas().getWilayah());
                }

                // Hewan
                if (kelahiran.getHewan() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "hewan", "idHewan",
                                        kelahiran.getHewan().getIdHewan());
                        client.insertRecord(tableKelahiran, rowKey, "hewan", "kodeEartagNasional",
                                        kelahiran.getHewan().getKodeEartagNasional());
                        client.insertRecord(tableKelahiran, rowKey, "hewan", "noKartuTernak",
                                        kelahiran.getHewan().getNoKartuTernak());
                        client.insertRecord(tableKelahiran, rowKey, "hewan", "idIsikhnasTernak",
                                        kelahiran.getHewan().getIdIsikhnasTernak());
                        client.insertRecord(tableKelahiran, rowKey, "hewan", "identifikasiHewan",
                                        kelahiran.getHewan().getIdentifikasiHewan());
                        client.insertRecord(tableKelahiran, rowKey, "hewan", "sex", kelahiran.getHewan().getSex());
                        client.insertRecord(tableKelahiran, rowKey, "hewan", "umur", kelahiran.getHewan().getUmur());
                        client.insertRecord(tableKelahiran, rowKey, "hewan", "tanggalTerdaftar",
                                        kelahiran.getHewan().getTanggalTerdaftar());
                        client.insertRecord(tableKelahiran, rowKey, "hewan", "tanggalLahir",
                                        kelahiran.getHewan().getTanggalLahir());
                        client.insertRecord(tableKelahiran, rowKey, "hewan", "tempatLahir",
                                        kelahiran.getHewan().getTempatLahir());
                }

                // Kandang
                if (kelahiran.getKandang() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "kandang", "idKandang",
                                        kelahiran.getKandang().getIdKandang());
                        client.insertRecord(tableKelahiran, rowKey, "kandang", "namaKandang",
                                        kelahiran.getKandang().getNamaKandang());
                        client.insertRecord(tableKelahiran, rowKey, "kandang", "alamat",
                                        kelahiran.getKandang().getAlamat());
                        client.insertRecord(tableKelahiran, rowKey, "kandang", "luas",
                                        kelahiran.getKandang().getLuas());
                        client.insertRecord(tableKelahiran, rowKey, "kandang", "jenisKandang",
                                        kelahiran.getKandang().getJenisKandang());
                        client.insertRecord(tableKelahiran, rowKey, "kandang", "kapasitas",
                                        kelahiran.getKandang().getKapasitas());
                        client.insertRecord(tableKelahiran, rowKey, "kandang", "nilaiBangunan",
                                        kelahiran.getKandang().getNilaiBangunan());
                        client.insertRecord(tableKelahiran, rowKey, "kandang", "latitude",
                                        kelahiran.getKandang().getLatitude());
                        client.insertRecord(tableKelahiran, rowKey, "kandang", "longitude",
                                        kelahiran.getKandang().getLongitude());
                }

                // Rumpun Hewan
                if (kelahiran.getRumpunHewan() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "rumpunHewan", "idRumpunHewan",
                                        kelahiran.getRumpunHewan().getIdRumpunHewan());
                        client.insertRecord(tableKelahiran, rowKey, "rumpunHewan", "rumpun",
                                        kelahiran.getRumpunHewan().getRumpun());
                        client.insertRecord(tableKelahiran, rowKey, "rumpunHewan", "deskripsi",
                                        kelahiran.getRumpunHewan().getDeskripsi());
                }

                // Jenis Hewan
                if (kelahiran.getJenisHewan() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "jenisHewan", "idJenisHewan",
                                        kelahiran.getJenisHewan().getIdJenisHewan());
                        client.insertRecord(tableKelahiran, rowKey, "jenisHewan", "jenis",
                                        kelahiran.getJenisHewan().getJenis());
                        client.insertRecord(tableKelahiran, rowKey, "jenisHewan", "deskripsi",
                                        kelahiran.getJenisHewan().getDeskripsi());
                }

                // Inseminasi
                if (kelahiran.getInseminasi() != null) {
                        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "idInseminasi",
                                        kelahiran.getInseminasi().getIdInseminasi());
                        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "idPejantan",
                                        kelahiran.getInseminasi().getIdPejantan());
                        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "idPembuatan",
                                        kelahiran.getInseminasi().getIdPembuatan());
                        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "tanggalIB",
                                        kelahiran.getInseminasi().getTanggalIB());
                        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "produsen",
                                        kelahiran.getInseminasi().getProdusen());
                        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "bangsaPejantan",
                                        kelahiran.getInseminasi().getBangsaPejantan());
                        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "ib1",
                                        kelahiran.getInseminasi().getIb1());
                        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "ib2",
                                        kelahiran.getInseminasi().getIb2());
                        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "ib3",
                                        kelahiran.getInseminasi().getIb3());
                        client.insertRecord(tableKelahiran, rowKey, "inseminasi", "ibLain",
                                        kelahiran.getInseminasi().getIbLain());
                }

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
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "petugas", "petugasId",
                                                        safeString(petugas.getPetugasId()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "petugas",
                                                        "nikPetugas",
                                                        safeString(petugas.getNikPetugas()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "petugas",
                                                        "namaPetugas",
                                                        safeString(petugas.getNamaPetugas()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "petugas",
                                                        "email",
                                                        safeString(petugas.getEmail()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "petugas",
                                                        "noTelp",
                                                        safeString(petugas.getNoTelp()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "petugas", "job",
                                                        safeString(petugas.getJob()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "petugas",
                                                        "wilayah",
                                                        safeString(petugas.getWilayah()));
                                }

                                if (kelahiran.getPeternak() != null) {
                                        Peternak peternak = kelahiran.getPeternak();
                                        if (peternak.getIdPeternak() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "idPeternak",
                                                                safeString(peternak.getIdPeternak()));
                                        }
                                        if (peternak.getNikPeternak() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "nikPeternak",
                                                                safeString(peternak.getNikPeternak()));
                                        }
                                        if (peternak.getNamaPeternak() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "namaPeternak",
                                                                safeString(peternak.getNamaPeternak()));
                                        }
                                        if (peternak.getAlamat() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "alamat",
                                                                safeString(peternak.getAlamat()));
                                        }
                                        if (peternak.getLokasi() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "lokasi",
                                                                safeString(peternak.getLokasi()));
                                        }
                                        if (peternak.getEmail() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "email",
                                                                safeString(peternak.getEmail()));
                                        }
                                        if (peternak.getNoTelepon() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "noTelepon",
                                                                safeString(peternak.getNoTelepon()));
                                        }
                                        if (peternak.getJenisKelamin() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "jenisKelamin",
                                                                safeString(peternak.getJenisKelamin()));
                                        }
                                        if (peternak.getTanggalLahir() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "tanggalLahir",
                                                                safeString(peternak.getTanggalLahir()));
                                        }
                                        if (peternak.getTanggalPendaftaran() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "tanggalPendaftaran",
                                                                safeString(peternak.getTanggalPendaftaran()));

                                        }
                                        if (peternak.getIdIsikhnas() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "idIsikhnas",
                                                                safeString(peternak.getIdIsikhnas()));
                                        }
                                        if (peternak.getDusun() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak", "dusun",
                                                                safeString(peternak.getDusun()));
                                        }
                                        if (peternak.getDesa() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "desa",
                                                                safeString(peternak.getDesa()));
                                        }
                                        if (peternak.getKecamatan() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "kecamatan",
                                                                safeString(peternak.getKecamatan()));
                                        }
                                        if (peternak.getKabupaten() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "kabupaten",
                                                                safeString(peternak.getKabupaten()));
                                        }
                                        if (peternak.getProvinsi() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "provinsi",
                                                                safeString(peternak.getProvinsi()));
                                        }
                                }

                                if (kelahiran.getKandang() != null) {
                                        Kandang kandang = kelahiran.getKandang();
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "kandang",
                                                        "idKandang",
                                                        safeString(kandang.getIdKandang()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "kandang",
                                                        "namaKandang",
                                                        safeString(kandang.getNamaKandang()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "kandang",
                                                        "alamat",
                                                        safeString(kandang.getAlamat()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "kandang",
                                                        "luas",
                                                        safeString(kandang.getLuas()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "kandang",
                                                        "jenisKandang",
                                                        safeString(kandang.getJenisKandang()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "kandang",
                                                        "kapasitas",
                                                        safeString(kandang.getKapasitas()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "kandang",
                                                        "nilaiBangunan",
                                                        safeString(kandang.getNilaiBangunan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "kandang",
                                                        "latitude",
                                                        safeString(kandang.getLatitude()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "kandang",
                                                        "longitude",
                                                        safeString(kandang.getLongitude()));
                                }

                                if (kelahiran.getJenisHewan() != null) {
                                        JenisHewan jenisHewan = kelahiran.getJenisHewan();
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "jenisHewan",
                                                        "idJenisHewan", safeString(jenisHewan.getIdJenisHewan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "jenisHewan",
                                                        "jenis",
                                                        safeString(jenisHewan.getJenis()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "jenisHewan",
                                                        "deskripsi",
                                                        safeString(jenisHewan.getDeskripsi()));
                                }

                                if (kelahiran.getRumpunHewan() != null) {
                                        RumpunHewan rumpunHewan = kelahiran.getRumpunHewan();
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "rumpunHewan",
                                                        "idRumpunHewan", safeString(rumpunHewan.getIdRumpunHewan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "rumpunHewan",
                                                        "rumpun",
                                                        safeString(rumpunHewan.getRumpun()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "rumpunHewan",
                                                        "deskripsi",
                                                        safeString(rumpunHewan.getDeskripsi()));
                                }

                                if (kelahiran.getHewan() != null) {
                                        Hewan hewan = kelahiran.getHewan();
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "hewan",
                                                        "idHewan",
                                                        safeString(hewan.getIdHewan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "hewan",
                                                        "kodeEartagNasional",
                                                        safeString(hewan.getKodeEartagNasional()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "hewan",
                                                        "noKartuTernak",
                                                        safeString(hewan.getNoKartuTernak()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "hewan", "idIsikhnasTernak",
                                                        safeString(hewan.getIdIsikhnasTernak()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "hewan",
                                                        "identifikasiHewan", safeString(hewan.getIdentifikasiHewan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "hewan",
                                                        "sex",
                                                        safeString(hewan.getSex()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "hewan",
                                                        "umur",
                                                        safeString(hewan.getUmur()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "hewan",
                                                        "tanggalTerdaftar", safeString(hewan.getTanggalTerdaftar()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "hewan",
                                                        "tanggalLahir",
                                                        safeString(hewan.getTanggalLahir()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "hewan",
                                                        "tempatLahir", safeString(hewan.getTempatLahir()));
                                }

                                if (kelahiran.getInseminasi() != null) {
                                        Inseminasi inseminasi = kelahiran.getInseminasi();
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "inseminasi",
                                                        "idInseminasi",
                                                        safeString(inseminasi.getIdInseminasi()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "inseminasi",
                                                        "idPejantan",
                                                        safeString(inseminasi.getIdPejantan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "inseminasi", "idPembuatan",
                                                        safeString(inseminasi.getIdPembuatan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "inseminasi", "tanggalIB",
                                                        safeString(inseminasi.getTanggalIB()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "inseminasi",
                                                        "produsen",
                                                        safeString(inseminasi.getProdusen()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "inseminasi",
                                                        "bangsaPejantan",
                                                        safeString(inseminasi.getBangsaPejantan()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "inseminasi", "ib1",
                                                        safeString(inseminasi.getIb1()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "inseminasi", "ib2", safeString(inseminasi.getIb2()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "inseminasi", "ib3", safeString(inseminasi.getIb3()));
                                        client.insertRecord(tableKelahiran, safeString(kelahiran.getIdKelahiran()),
                                                        "inseminasi", "ibLain", safeString(inseminasi.getIbLain()));
                                }

                                String rowKey = kelahiran.getIdKelahiran();
                                client.insertRecord(tableKelahiran, rowKey, "main", "idKelahiran",
                                                kelahiran.getIdKelahiran());
                                if (kelahiran.getIdKejadian() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "idKejadian",
                                                        kelahiran.getIdKejadian());
                                }
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
                                if (kelahiran.getNoKartuTernakAnak() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "noKartuTernakAnak",
                                                        kelahiran.getNoKartuTernakAnak());
                                }
                                if (kelahiran.getSpesies() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "spesies",
                                                        kelahiran.getSpesies());
                                }
                                if (kelahiran.getKategori() != null) {
                                        client.insertRecord(tableKelahiran, rowKey, "main", "kategori",
                                                        kelahiran.getKategori());
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
                                                "Data berhasil disimpan ke HBase dengan ID Kelahiran (kelahiran): "
                                                                + kelahiran.getIdKelahiran());

                        } catch (Exception e) {
                                failedRows.add(kelahiran.getIdKelahiran());
                                System.err.println(
                                                "Failed to insert record for ID: " + kelahiran.getIdKelahiran()
                                                                + ", Error: " + e.getMessage());
                        }
                }

                if (!failedRows.isEmpty()) {
                        throw new IOException(
                                        "Failed to save records for ID Kelahiran: " + String.join(", ", failedRows));
                }

                return kelahiranList;
        }

        public List<Kelahiran> saveImport(List<Kelahiran> kelahiranList) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);

                System.out.println("Memulai penyimpanan data kealhiran ke HBase...");
                List<String> failedRows = new ArrayList<>();

                for (Kelahiran kelahiran : kelahiranList) {
                        try {
                                if (kelahiran.getJenisHewan() != null) {
                                        JenisHewan jenisHewan = kelahiran.getJenisHewan();
                                        if (jenisHewan.getIdJenisHewan() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "jenisHewan",
                                                                "idJenisHewan",
                                                                safeString(jenisHewan.getIdJenisHewan()));
                                        }
                                        if (jenisHewan.getJenis() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "jenisHewan",
                                                                "jenis",
                                                                safeString(jenisHewan.getJenis()));
                                        }
                                        if (jenisHewan.getDeskripsi() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "jenisHewan",
                                                                "deskripsi",
                                                                safeString(jenisHewan.getDeskripsi()));
                                        }

                                }

                                if (kelahiran.getRumpunHewan() != null) {
                                        RumpunHewan rumpunHewan = kelahiran.getRumpunHewan();
                                        if (rumpunHewan.getIdRumpunHewan() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "rumpunHewan",
                                                                "idRumpunHewan",
                                                                safeString(rumpunHewan.getIdRumpunHewan()));
                                        }
                                        if (rumpunHewan.getRumpun() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "rumpunHewan",
                                                                "rumpun",
                                                                safeString(rumpunHewan.getRumpun()));
                                        }
                                        if (rumpunHewan.getDeskripsi() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "rumpunHewan",
                                                                "deskripsi",
                                                                safeString(rumpunHewan.getDeskripsi()));
                                        }

                                }

                                if (kelahiran.getPeternak() != null) {
                                        Peternak peternak = kelahiran.getPeternak();
                                        if (peternak.getIdPeternak() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "idPeternak",
                                                                safeString(peternak.getIdPeternak()));
                                        }
                                        if (peternak.getNikPeternak() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "nikPeternak",
                                                                safeString(peternak.getNikPeternak()));
                                        }
                                        if (peternak.getNamaPeternak() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "namaPeternak",
                                                                safeString(peternak.getNamaPeternak()));
                                        }
                                        if (peternak.getAlamat() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "alamat",
                                                                safeString(peternak.getAlamat()));
                                        }
                                        if (peternak.getLokasi() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "lokasi",
                                                                safeString(peternak.getLokasi()));
                                        }
                                        if (peternak.getEmail() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "email",
                                                                safeString(peternak.getEmail()));
                                        }
                                        if (peternak.getNoTelepon() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "noTelepon",
                                                                safeString(peternak.getNoTelepon()));
                                        }
                                        if (peternak.getJenisKelamin() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "jenisKelamin",
                                                                safeString(peternak.getJenisKelamin()));
                                        }
                                        if (peternak.getTanggalLahir() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "tanggalLahir",
                                                                safeString(peternak.getTanggalLahir()));
                                        }
                                        if (peternak.getTanggalPendaftaran() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "tanggalPendaftaran",
                                                                safeString(peternak.getTanggalPendaftaran()));

                                        }
                                        if (peternak.getIdIsikhnas() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "idIsikhnas",
                                                                safeString(peternak.getIdIsikhnas()));
                                        }
                                        if (peternak.getDusun() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak", "dusun",
                                                                safeString(peternak.getDusun()));
                                        }
                                        if (peternak.getDesa() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "desa",
                                                                safeString(peternak.getDesa()));
                                        }
                                        if (peternak.getKecamatan() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "kecamatan",
                                                                safeString(peternak.getKecamatan()));
                                        }
                                        if (peternak.getKabupaten() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "kabupaten",
                                                                safeString(peternak.getKabupaten()));
                                        }
                                        if (peternak.getProvinsi() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "peternak",
                                                                "provinsi",
                                                                safeString(peternak.getProvinsi()));
                                        }
                                }

                                if (kelahiran.getKandang() != null) {
                                        Kandang kandang = kelahiran.getKandang();
                                        if (kandang.getIdKandang() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "kandang",
                                                                "idKandang",
                                                                safeString(kandang.getIdKandang()));
                                        }
                                        if (kandang.getNamaKandang() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "kandang",
                                                                "namaKandang",
                                                                safeString(kandang.getNamaKandang()));
                                        }
                                        if (kandang.getAlamat() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "kandang",
                                                                "alamat",
                                                                safeString(kandang.getAlamat()));
                                        }
                                        if (kandang.getLuas() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "kandang",
                                                                "luas",
                                                                safeString(kandang.getLuas()));
                                        }
                                        if (kandang.getJenisKandang() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "kandang",
                                                                "jenisKandang",
                                                                safeString(kandang.getJenisKandang()));
                                        }
                                        if (kandang.getKapasitas() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "kandang",
                                                                "kapasitas",
                                                                safeString(kandang.getKapasitas()));
                                        }
                                        if (kandang.getNilaiBangunan() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "kandang",
                                                                "nilaiBangunan",
                                                                safeString(kandang.getNilaiBangunan()));
                                        }
                                        if (kandang.getLatitude() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "kandang",
                                                                "latitude",
                                                                safeString(kandang.getLatitude()));
                                        }
                                        if (kandang.getLongitude() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "kandang",
                                                                "longitude",
                                                                safeString(kandang.getLongitude()));
                                        }
                                }

                                if (kelahiran.getHewan() != null) {
                                        Hewan hewan = kelahiran.getHewan();
                                        if (hewan.getIdHewan() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "hewan",
                                                                "idHewan",
                                                                safeString(hewan.getIdHewan()));
                                        }
                                        if (hewan.getKodeEartagNasional() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "hewan",
                                                                "kodeEartagNasional",
                                                                safeString(hewan.getKodeEartagNasional()));
                                        }
                                        if (hewan.getNoKartuTernak() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "hewan",
                                                                "noKartuTernak",
                                                                safeString(hewan.getNoKartuTernak()));
                                        }
                                        if (hewan.getIdIsikhnasTernak() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "hewan",
                                                                "idIsikhnasTernak",
                                                                safeString(hewan.getIdIsikhnasTernak()));
                                        }
                                        if (hewan.getIdentifikasiHewan() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "hewan",
                                                                "identifikasiHewan",
                                                                safeString(hewan.getIdentifikasiHewan()));
                                        }
                                        if (hewan.getSex() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "hewan",
                                                                "sex",
                                                                safeString(hewan.getSex()));
                                        }
                                        if (hewan.getUmur() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "hewan",
                                                                "umur",
                                                                safeString(hewan.getUmur()));
                                        }
                                        if (hewan.getTanggalTerdaftar() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "hewan",
                                                                "tanggalTerdaftar",
                                                                safeString(hewan.getTanggalTerdaftar()));
                                        }
                                        if (hewan.getTanggalLahir() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "hewan",
                                                                "tanggalLahir",
                                                                safeString(hewan.getTanggalLahir()));
                                        }
                                        if (hewan.getTempatLahir() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "hewan",
                                                                "tempatLahir",
                                                                safeString(hewan.getTempatLahir()));
                                        }
                                }

                                if (kelahiran.getInseminasi() != null) {
                                        Inseminasi inseminasi = kelahiran.getInseminasi();
                                        if (inseminasi.getIdInseminasi() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "inseminasi",
                                                                "idInseminasi",
                                                                safeString(inseminasi.getIdInseminasi()));
                                        }
                                        if (inseminasi.getIdPejantan() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "inseminasi",
                                                                "idPejantan",
                                                                safeString(inseminasi.getIdPejantan()));
                                        }
                                        if (inseminasi.getIdPembuatan() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "inseminasi",
                                                                "idPembuatan",
                                                                safeString(inseminasi.getIdPembuatan()));
                                        }
                                        if (inseminasi.getTanggalIB() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "inseminasi",
                                                                "tanggalIB",
                                                                safeString(inseminasi.getTanggalIB()));
                                        }
                                        if (inseminasi.getProdusen() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "inseminasi",
                                                                "produsen",
                                                                safeString(inseminasi.getProdusen()));
                                        }
                                        if (inseminasi.getBangsaPejantan() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "inseminasi",
                                                                "bangsaPejantan",
                                                                safeString(inseminasi.getBangsaPejantan()));
                                        }
                                        if (inseminasi.getIb1() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "inseminasi",
                                                                "ib1",
                                                                safeString(inseminasi.getIb1()));
                                        }
                                        if (inseminasi.getIb2() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "inseminasi",
                                                                "ib2",
                                                                safeString(inseminasi.getIb2()));
                                        }
                                        if (inseminasi.getIb3() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "inseminasi",
                                                                "ib3",
                                                                safeString(inseminasi.getIb3()));
                                        }
                                        if (inseminasi.getIbLain() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "inseminasi",
                                                                "ibLain",
                                                                safeString(inseminasi.getIbLain()));
                                        }
                                }

                                if (kelahiran.getPetugas() != null) {
                                        Petugas petugas = kelahiran.getPetugas();
                                        if (petugas.getPetugasId() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "petugas",
                                                                "idPetugas",
                                                                safeString(petugas.getPetugasId()));
                                        }
                                        if (petugas.getNikPetugas() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "petugas",
                                                                "nikPetugas",
                                                                safeString(petugas.getNikPetugas()));
                                        }
                                        if (petugas.getNamaPetugas() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "petugas",
                                                                "namaPetugas",
                                                                safeString(petugas.getNamaPetugas()));
                                        }
                                        if (petugas.getEmail() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "petugas",
                                                                "email",
                                                                safeString(petugas.getEmail()));
                                        }
                                        if (petugas.getNoTelp() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "petugas",
                                                                "noTelepon",
                                                                safeString(petugas.getNoTelp()));
                                        }
                                        if (petugas.getJob() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "petugas",
                                                                "job",
                                                                safeString(petugas.getJob()));
                                        }
                                        if (petugas.getWilayah() != null) {
                                                client.insertRecord(tableKelahiran,
                                                                safeString(kelahiran.getIdKelahiran()),
                                                                "petugas",
                                                                "wilayahKerja",
                                                                safeString(petugas.getWilayah()));

                                        }
                                }

                        } catch (Exception e) {
                                failedRows.add(kelahiran.getIdKelahiran());
                                System.err.println(
                                                "Failed to insert record for ID: " + kelahiran.getIdKelahiran()
                                                                + ", Error: "
                                                                + e.getMessage());
                        }
                }

                if (!failedRows.isEmpty()) {
                        throw new IOException(
                                        "Failed to save records for ID Kelahiran: " + String.join(", ", failedRows));
                }

                return kelahiranList;
        }

        public Kelahiran findById(String idKelahiran) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableKelahiran = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idKelahiran", "idKelahiran");
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
                columnMapping.put("noKartuTernakAnak", "noKartuTernakAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
                columnMapping.put("jumlah", "jumlah");
                columnMapping.put("idHewanAnak", "idHewanAnak");
                columnMapping.put("urutanIB", "urutanIB");

                Kelahiran kelahiran = client.getDataByColumn(tableKelahiran.toString(), columnMapping, "main",
                                "idKelahiran", idKelahiran, Kelahiran.class);

                System.out.println("Data Kelahiran ditemukan by ID: " + idKelahiran);

                return kelahiran.getIdKelahiran() != null ? kelahiran : null;
        }

        public Kelahiran findKelahiranById(String kelahiranId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);

                TableName tableKelahiran = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();

                // Add the mappings to the HashMap
                columnMapping.put("idKelahiran", "idKelahiran");
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
                columnMapping.put("noKartuTernakAnak", "noKartuTernakAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
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
                columnMapping.put("idKelahiran", "idKelahiran");
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
                columnMapping.put("noKartuTernakAnak", "noKartuTernakAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
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
                columnMapping.put("idKelahiran", "idKelahiran");
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
                columnMapping.put("noKartuTernakAnak", "noKartuTernakAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
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
                columnMapping.put("idKelahiran", "idKelahiran");
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
                columnMapping.put("noKartuTernakAnak", "noKartuTernakAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
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
                columnMapping.put("idKelahiran", "idKelahiran");
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
                columnMapping.put("noKartuTernakAnak", "noKartuTernakAnak");
                columnMapping.put("jenisKelaminAnak", "jenisKelaminAnak");
                columnMapping.put("spesies", "spesies");
                columnMapping.put("kategori", "kategori");
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
                if (kelahiran.getIdKejadian() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "main", "idKejadian",
                                        kelahiran.getIdKejadian());

                }
                if (kelahiran.getTanggalLaporan() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "main", "tanggalLaporan",
                                        kelahiran.getTanggalLaporan());
                }
                if (kelahiran.getTanggalLahir() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "main", "tanggalLahir",
                                        kelahiran.getTanggalLahir());
                }
                if (kelahiran.getIdHewanAnak() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "main", "idHewanAnak",
                                        kelahiran.getIdHewanAnak());
                }
                if (kelahiran.getNoKartuTernakAnak() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "main", "noKartuTernakAnak",
                                        kelahiran.getNoKartuTernakAnak());
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
                if (kelahiran.getKategori() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "main", "kategori", kelahiran.getKategori());
                }
                if (kelahiran.getJumlah() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "main", "jumlah", kelahiran.getJumlah());
                }
                if (kelahiran.getUrutanIB() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "main", "urutanIB", kelahiran.getUrutanIB());
                }

                // Peternak
                if (kelahiran.getPeternak() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "idPeternak",
                                        kelahiran.getPeternak().getIdPeternak());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "nikPeternak",
                                        kelahiran.getPeternak().getNikPeternak());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "namaPeternak",
                                        kelahiran.getPeternak().getNamaPeternak());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "alamat",
                                        kelahiran.getPeternak().getAlamat());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "email",
                                        kelahiran.getPeternak().getEmail());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "noTelepon",
                                        kelahiran.getPeternak().getNoTelepon());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "tanggalLahir",
                                        kelahiran.getPeternak().getTanggalLahir());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "tanggalPendaftaran",
                                        kelahiran.getPeternak().getTanggalPendaftaran());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "idIsikhnas",
                                        kelahiran.getPeternak().getIdIsikhnas());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "dusun",
                                        kelahiran.getPeternak().getDusun());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "desa",
                                        kelahiran.getPeternak().getDesa());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "kecamatan",
                                        kelahiran.getPeternak().getKecamatan());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "kabupaten",
                                        kelahiran.getPeternak().getKabupaten());
                        client.insertRecord(tableKelahiran, kelahiranId, "peternak", "provinsi",
                                        kelahiran.getPeternak().getProvinsi());
                }

                // Petugas
                if (kelahiran.getPetugas() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "petugas", "petugasId",
                                        kelahiran.getPetugas().getPetugasId());
                        client.insertRecord(tableKelahiran, kelahiranId, "petugas", "nikPetugas",
                                        kelahiran.getPetugas().getNikPetugas());
                        client.insertRecord(tableKelahiran, kelahiranId, "petugas", "namaPetugas",
                                        kelahiran.getPetugas().getNamaPetugas());
                        client.insertRecord(tableKelahiran, kelahiranId, "petugas", "job",
                                        kelahiran.getPetugas().getJob());
                        client.insertRecord(tableKelahiran, kelahiranId, "petugas", "wilayah",
                                        kelahiran.getPetugas().getWilayah());
                        client.insertRecord(tableKelahiran, kelahiranId, "petugas", "noTelp",
                                        kelahiran.getPetugas().getNoTelp());
                        client.insertRecord(tableKelahiran, kelahiranId, "petugas", "email",
                                        kelahiran.getPetugas().getEmail());
                }

                // Hewan
                if (kelahiran.getHewan() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "hewan", "idHewan",
                                        kelahiran.getHewan().getIdHewan());
                        client.insertRecord(tableKelahiran, kelahiranId, "hewan", "kodeEartagNasional",
                                        kelahiran.getHewan().getKodeEartagNasional());
                        client.insertRecord(tableKelahiran, kelahiranId, "hewan", "noKartuTernak",
                                        kelahiran.getHewan().getNoKartuTernak());
                        client.insertRecord(tableKelahiran, kelahiranId, "hewan", "idIsikhnasTernak",
                                        kelahiran.getHewan().getIdIsikhnasTernak());
                        client.insertRecord(tableKelahiran, kelahiranId, "hewan", "identifikasiHewan",
                                        kelahiran.getHewan().getIdentifikasiHewan());
                        client.insertRecord(tableKelahiran, kelahiranId, "hewan", "sex",
                                        kelahiran.getHewan().getSex());
                        client.insertRecord(tableKelahiran, kelahiranId, "hewan", "umur",
                                        kelahiran.getHewan().getUmur());
                        client.insertRecord(tableKelahiran, kelahiranId, "hewan", "tanggalTerdaftar",
                                        kelahiran.getHewan().getTanggalTerdaftar());
                        client.insertRecord(tableKelahiran, kelahiranId, "hewan", "tanggalLahir",
                                        kelahiran.getHewan().getTanggalLahir());
                        client.insertRecord(tableKelahiran, kelahiranId, "hewan", "tempatLahir",
                                        kelahiran.getHewan().getTempatLahir());
                }

                // Kandang
                if (kelahiran.getKandang() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "kandang", "idKandang",
                                        kelahiran.getKandang().getIdKandang());
                        client.insertRecord(tableKelahiran, kelahiranId, "kandang", "namaKandang",
                                        kelahiran.getKandang().getNamaKandang());
                        client.insertRecord(tableKelahiran, kelahiranId, "kandang", "nilaiBangunan",
                                        kelahiran.getKandang().getNilaiBangunan());
                        client.insertRecord(tableKelahiran, kelahiranId, "kandang", "longitude",
                                        kelahiran.getKandang().getLongitude());
                        client.insertRecord(tableKelahiran, kelahiranId, "kandang", "latitude",
                                        kelahiran.getKandang().getLatitude());
                        client.insertRecord(tableKelahiran, kelahiranId, "kandang", "luas",
                                        kelahiran.getKandang().getLuas());
                        client.insertRecord(tableKelahiran, kelahiranId, "kandang", "kapasitas",
                                        kelahiran.getKandang().getKapasitas());
                        client.insertRecord(tableKelahiran, kelahiranId, "kandang", "jenisKandang",
                                        kelahiran.getKandang().getJenisKandang());
                        client.insertRecord(tableKelahiran, kelahiranId, "kandang", "alamat",
                                        kelahiran.getKandang().getAlamat());
                }

                // Rumpun Hewan
                if (kelahiran.getRumpunHewan() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "rumpunHewan", "idRumpunHewan",
                                        kelahiran.getRumpunHewan().getIdRumpunHewan());
                        client.insertRecord(tableKelahiran, kelahiranId, "rumpunHewan", "rumpun",
                                        kelahiran.getRumpunHewan().getRumpun());
                        client.insertRecord(tableKelahiran, kelahiranId, "rumpunHewan", "deskripsi",
                                        kelahiran.getRumpunHewan().getDeskripsi());
                }

                // Jenis Hewan
                if (kelahiran.getJenisHewan() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "jenisHewan", "idJenisHewan",
                                        kelahiran.getJenisHewan().getIdJenisHewan());
                        client.insertRecord(tableKelahiran, kelahiranId, "jenisHewan", "jenis",
                                        kelahiran.getJenisHewan().getJenis());
                        client.insertRecord(tableKelahiran, kelahiranId, "jenisHewan", "deskripsi",
                                        kelahiran.getJenisHewan().getDeskripsi());
                }

                // Inseminasi
                if (kelahiran.getInseminasi() != null) {
                        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "idInseminasi",
                                        kelahiran.getInseminasi().getIdInseminasi());
                        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "idPejantan",
                                        kelahiran.getInseminasi().getIdPejantan());
                        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "idPembuatan",
                                        kelahiran.getInseminasi().getIdPembuatan());
                        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "tanggalIB",
                                        kelahiran.getInseminasi().getTanggalIB());
                        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "produsen",
                                        kelahiran.getInseminasi().getProdusen());
                        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "bangsaPejantan",
                                        kelahiran.getInseminasi().getBangsaPejantan());
                        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "ib1",
                                        kelahiran.getInseminasi().getIb1());
                        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "ib2",
                                        kelahiran.getInseminasi().getIb2());
                        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "ib3",
                                        kelahiran.getInseminasi().getIb3());
                        client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "ibLain",
                                        kelahiran.getInseminasi().getIbLain());
                }
                client.insertRecord(tableKelahiran, kelahiranId, "detail", "created_by", "Polinema");

                return kelahiran;
        }

        public Kelahiran updatePetugasByKelahiran(String kelahiranId, Kelahiran kelahiran) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);

                client.insertRecord(tableKelahiran, kelahiranId, "petugas", "nikPetugas",
                                kelahiran.getPetugas().getNikPetugas());
                client.insertRecord(tableKelahiran, kelahiranId, "petugas", "namaPetugas",
                                kelahiran.getPetugas().getNamaPetugas());
                client.insertRecord(tableKelahiran, kelahiranId, "petugas", "noTelp",
                                kelahiran.getPetugas().getNoTelp());
                client.insertRecord(tableKelahiran, kelahiranId, "petugas", "email",
                                kelahiran.getPetugas().getEmail());
                client.insertRecord(tableKelahiran, kelahiranId, "main", "job", kelahiran.getPetugas().getJob());
                client.insertRecord(tableKelahiran, kelahiranId, "main", "wilayah",
                                kelahiran.getPetugas().getWilayah());
                client.insertRecord(tableKelahiran, kelahiranId, "detail", "created_by", "Polinema");
                return kelahiran;
        }

        public Kelahiran updatePeternakByKelahiran(String kelahiranId, Kelahiran kelahiran) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);

                client.insertRecord(tableKelahiran, kelahiranId, "kandang", "namaKandang",
                                "Kandang " + kelahiran.getPeternak().getNamaPeternak());

                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "nikPeternak",
                                kelahiran.getPeternak().getNikPeternak());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "namaPeternak",
                                kelahiran.getPeternak().getNamaPeternak());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "lokasi",
                                kelahiran.getPeternak().getLokasi());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "tanggalPendaftaran",
                                kelahiran.getPeternak().getTanggalPendaftaran());

                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "noTelepon",
                                kelahiran.getPeternak().getNoTelepon());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "email",
                                kelahiran.getPeternak().getEmail());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "jenisKelamin",
                                kelahiran.getPeternak().getJenisKelamin());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "tanggalLahir",
                                kelahiran.getPeternak().getTanggalLahir());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "idIsikhnas",
                                kelahiran.getPeternak().getIdIsikhnas());

                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "dusun",
                                kelahiran.getPeternak().getDusun());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "desa", kelahiran.getPeternak().getDesa());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "kecamatan",
                                kelahiran.getPeternak().getKecamatan());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "kabupaten",
                                kelahiran.getPeternak().getKabupaten());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "provinsi",
                                kelahiran.getPeternak().getProvinsi());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "alamat",
                                kelahiran.getPeternak().getAlamat());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "latitude",
                                kelahiran.getPeternak().getLatitude());
                client.insertRecord(tableKelahiran, kelahiranId, "peternak", "longitude",
                                kelahiran.getPeternak().getLongitude());
                return kelahiran;
        }

        public Kelahiran updateKandangByKelahiran(String kelahiranId, Kelahiran kelahiran) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                client.insertRecord(tableKelahiran, kelahiranId,
                                "kandang", "namaKandang",
                                kelahiran.getKandang().getNamaKandang());
                client.insertRecord(tableKelahiran, kelahiranId,
                                "kandang", "alamat",
                                kelahiran.getKandang().getAlamat());
                client.insertRecord(tableKelahiran, kelahiranId,
                                "kandang", "luas",
                                kelahiran.getKandang().getLuas());
                client.insertRecord(tableKelahiran, kelahiranId,
                                "kandang", "jenisKandang",
                                kelahiran.getKandang().getJenisKandang());
                client.insertRecord(tableKelahiran, kelahiranId,
                                "kandang", "kapasitas",
                                kelahiran.getKandang().getKapasitas());
                client.insertRecord(tableKelahiran, kelahiranId,
                                "kandang", "nilaiBangunan",
                                kelahiran.getKandang().getNilaiBangunan());
                client.insertRecord(tableKelahiran, kelahiranId,
                                "kandang", "latitude",
                                kelahiran.getKandang().getLatitude());
                client.insertRecord(tableKelahiran, kelahiranId,
                                "kandang", "longitude",
                                kelahiran.getKandang().getLongitude());
                return kelahiran;
        }

        public Kelahiran updateJenisHewanByKelahiran(String kelahiranId, Kelahiran kelahiran) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                client.insertRecord(tableKelahiran, kelahiranId, "jenisHewan", "jenis",
                                kelahiran.getJenisHewan().getJenis());
                client.insertRecord(tableKelahiran, kelahiranId, "jenisHewan", "deskripsi",
                                kelahiran.getJenisHewan().getDeskripsi());
                return kelahiran;
        }

        public Kelahiran updateRumpunHewanByKelahiran(String kelahiranId, Kelahiran kelahiran) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                client.insertRecord(tableKelahiran, kelahiranId, "rumpunHewan", "rumpun",
                                kelahiran.getRumpunHewan().getRumpun());
                client.insertRecord(tableKelahiran, kelahiranId, "rumpunHewan", "deskripsi",
                                kelahiran.getRumpunHewan().getDeskripsi());
                return kelahiran;
        }

        public Kelahiran updateHewanByKelahiran(String kelahiranId, Kelahiran kelahiran) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                client.insertRecord(tableKelahiran, kelahiranId, "hewan", "kodeEartagNasional",
                                kelahiran.getHewan().getKodeEartagNasional());
                client.insertRecord(tableKelahiran, kelahiranId, "hewan", "noKartuTernak",
                                kelahiran.getHewan().getNoKartuTernak());
                client.insertRecord(tableKelahiran, kelahiranId, "hewan", "idIsikhnasTernak",
                                kelahiran.getHewan().getIdIsikhnasTernak());
                client.insertRecord(tableKelahiran, kelahiranId, "hewan", "sex", kelahiran.getHewan().getSex());
                client.insertRecord(tableKelahiran, kelahiranId, "hewan", "umur", kelahiran.getHewan().getUmur());
                client.insertRecord(tableKelahiran, kelahiranId, "hewan", "identifikasiHewan",
                                kelahiran.getHewan().getIdentifikasiHewan());
                client.insertRecord(tableKelahiran, kelahiranId, "hewan", "tanggalLahir",
                                kelahiran.getHewan().getTanggalLahir());
                client.insertRecord(tableKelahiran, kelahiranId, "hewan", "tempatLahir",
                                kelahiran.getHewan().getTempatLahir());
                client.insertRecord(tableKelahiran, kelahiranId, "hewan", "tanggalTerdaftar",
                                kelahiran.getHewan().getTanggalTerdaftar());
                return kelahiran;
        }

        public Kelahiran updateInseminasiByKelahiran(String kelahiranId, Kelahiran kelahiran) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "idPejantan",
                                kelahiran.getInseminasi().getIdPejantan());
                client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "idPembuatan",
                                kelahiran.getInseminasi().getIdPembuatan());
                client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "tanggalIB",
                                kelahiran.getInseminasi().getTanggalIB());
                client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "produsen",
                                kelahiran.getInseminasi().getProdusen());
                client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "bangsaPejantan",
                                kelahiran.getInseminasi().getBangsaPejantan());
                client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "ib1",
                                kelahiran.getInseminasi().getIb1());
                client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "ib2",
                                kelahiran.getInseminasi().getIb2());
                client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "ib3",
                                kelahiran.getInseminasi().getIb3());
                client.insertRecord(tableKelahiran, kelahiranId, "inseminasi", "ibLain",
                                kelahiran.getInseminasi().getIbLain());
                return kelahiran;
        }

        public List<Kelahiran> findByPetugasId(String petugasId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKelahiran", "idKelahiran");
                columnMapping.put("petugasId", "petugasId");
                columnMapping.put("petugas", "petugas");

                return client.getDataListByColumn(tableKelahiran.toString(), columnMapping,
                                "petugas", "petugasId", petugasId, Kelahiran.class, -1);
        }

        public List<Kelahiran> findByPeternakId(String peternakId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKelahiran", "idKelahiran");
                columnMapping.put("peternakId", "peternakId");
                columnMapping.put("peternak", "peternak");

                return client.getDataListByColumn(tableKelahiran.toString(), columnMapping,
                                "peternak", "peternakId", peternakId, Kelahiran.class, -1);
        }

        public List<Kelahiran> findByKandangId(String idKandang) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKelahiran", "idKelahiran");
                columnMapping.put("kandang", "kandang");

                return client.getDataListByColumn(tableKelahiran.toString(), columnMapping,
                                "kandang", "idKandang", idKandang, Kelahiran.class, -1);
        }

        public List<Kelahiran> findByJenisHewanId(String idJenisHewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKelahiran", "idKelahiran");
                columnMapping.put("jenisHewan", "jenisHewan");

                return client.getDataListByColumn(tableKelahiran.toString(), columnMapping,
                                "jenisHewan", "idJenisHewan", idJenisHewan, Kelahiran.class, -1);
        }

        public List<Kelahiran> findByRumpunId(String idRumpunHewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKelahiran", "idKelahiran");
                columnMapping.put("rumpunHewan", "rumpunHewan");

                return client.getDataListByColumn(tableKelahiran.toString(), columnMapping,
                                "rumpunHewan", "idRumpunHewan", idRumpunHewan, Kelahiran.class, -1);
        }

        public List<Kelahiran> findByHewanId(String idHewan) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKelahiran", "idKelahiran");
                columnMapping.put("hewan", "hewan");

                return client.getDataListByColumn(tableKelahiran.toString(), columnMapping,
                                "hewan", "idHewan", idHewan, Kelahiran.class, -1);
        }

        public List<Kelahiran> findByInseminasiId(String idInseminasi) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKelahiran", "idKelahiran");
                columnMapping.put("inseminasi", "inseminasi");

                return client.getDataListByColumn(tableKelahiran.toString(), columnMapping,
                                "inseminasi", "idInseminasi", idInseminasi, Kelahiran.class, -1);
        }

        public boolean deleteById(String kelahiranId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                client.deleteRecord(tableName, kelahiranId);
                return true;
        }

        public boolean existsById(String kelahiranId) throws IOException {
                HBaseCustomClient client = new HBaseCustomClient(conf);
                TableName tableKelahiran = TableName.valueOf(tableName);
                Map<String, String> columnMapping = new HashMap<>();
                columnMapping.put("idKelahiran", "idKelahiran");

                Kelahiran kelahiran = client.getDataByColumn(tableKelahiran.toString(), columnMapping, "main",
                                "idKelahiran", kelahiranId,
                                Kelahiran.class);
                return kelahiran.getIdKelahiran() != null; // True jika ID Hewan sudah ada
        }
}

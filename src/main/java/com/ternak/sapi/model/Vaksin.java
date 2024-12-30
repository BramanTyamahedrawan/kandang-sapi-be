package com.ternak.sapi.model;

public class Vaksin {
    private String idVaksin;
    private Peternak peternak;
    private Hewan hewan;
    private Petugas petugas;
    private String namaVaksin;
    private String jenisVaksin;
    private String tglVaksin;
    private String tglPendataan;
    private String batchVaksin;
    private String vaksinKe;
    private NamaVaksin namaVaksinTable;
    private JenisVaksin jenisVaksinTable;

    public Vaksin() {
    }

    public Vaksin(String idVaksin, Peternak peternak, Hewan hewan, Petugas petugas, String namaVaksin,
            String jenisVaksin, String tglVaksin, String batchVaksin, String vaksinKe, NamaVaksin namaVaksinTable,
            JenisVaksin jenisVaksinTable, String tglPendataan) {
        this.idVaksin = idVaksin;
        this.peternak = peternak;
        this.hewan = hewan;
        this.petugas = petugas;
        this.namaVaksin = namaVaksin;
        this.jenisVaksin = jenisVaksin;
        this.tglVaksin = tglVaksin;
        this.batchVaksin = batchVaksin;
        this.vaksinKe = vaksinKe;
        this.namaVaksinTable = namaVaksinTable;
        this.jenisVaksinTable = jenisVaksinTable;
        this.tglPendataan = tglPendataan;
    }

    public String getIdVaksin() {
        return idVaksin;
    }

    public void setIdVaksin(String idVaksin) {
        this.idVaksin = idVaksin;
    }

    public Peternak getPeternak() {
        return peternak;
    }

    public void setPeternak(Peternak peternak) {
        this.peternak = peternak;
    }

    public Hewan getHewan() {
        return hewan;
    }

    public void setHewan(Hewan hewan) {
        this.hewan = hewan;
    }

    public Petugas getPetugas() {
        return petugas;
    }

    public void setPetugas(Petugas petugas) {
        this.petugas = petugas;
    }

    public String getNamaVaksin() {
        return namaVaksin;
    }

    public void setNamaVaksin(String namaVaksin) {
        this.namaVaksin = namaVaksin;
    }

    public String getJenisVaksin() {
        return jenisVaksin;
    }

    public void setJenisVaksin(String jenisVaksin) {
        this.jenisVaksin = jenisVaksin;
    }

    public String getTglVaksin() {
        return tglVaksin;
    }

    public void setTglVaksin(String tglVaksin) {
        this.tglVaksin = tglVaksin;
    }

    public String getBatchVaksin() {
        return batchVaksin;
    }

    public void setBatchVaksin(String batchVaksin) {
        this.batchVaksin = batchVaksin;
    }

    public String getVaksinKe() {
        return vaksinKe;
    }

    public void setVaksinKe(String vaksinKe) {
        this.vaksinKe = vaksinKe;
    }

    public NamaVaksin getNamaVaksinTable() {
        return namaVaksinTable;
    }

    public void setNamaVaksinTable(NamaVaksin namaVaksinTable) {
        this.namaVaksinTable = namaVaksinTable;
    }

    public JenisVaksin getJenisVaksinTable() {
        return jenisVaksinTable;
    }

    public void setJenisVaksinTable(JenisVaksin jenisVaksinTable) {
        this.jenisVaksinTable = jenisVaksinTable;
    }

    public String getTglPendataan() {
        return tglPendataan;
    }

    public void setTglPendataan(String tglPendataan) {
        this.tglPendataan = tglPendataan;
    }

    public boolean isValid() {
        return this.idVaksin != null &&
                this.peternak != null &&
                this.hewan != null &&
                this.petugas != null &&
                this.batchVaksin != null &&
                this.vaksinKe != null;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "this.idVaksin":
                this.idVaksin = value;
                break;
            case "this.namaVaksin":
                this.namaVaksin = value;
                break;
            case "this.jenisVaksin":
                this.jenisVaksin = value;
                break;
            case "this.tglVaksin":
                this.tglVaksin = value;
                break;
            case "this.batchVaksin":
                this.batchVaksin = value;
                break;
            case "this.vaksinKe":
                this.vaksinKe = value;
                break;
            case "this.tglPendataan":
                this.tglPendataan = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
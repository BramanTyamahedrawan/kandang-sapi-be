package com.ternak.sapi.model;

public class Vaksin {
    private String idVaksin;
    private Peternak peternak;
    private Hewan hewan;
    private Petugas petugas;
    private String tglVaksin;
    private String tglPendataan;
    private String batchVaksin;
    private String vaksinKe;
    private NamaVaksin namaVaksin;
    private JenisVaksin jenisVaksin;

    public Vaksin() {
    }

    public Vaksin(String idVaksin, Peternak peternak, Hewan hewan, Petugas petugas,
          String tglVaksin, String batchVaksin, String vaksinKe, NamaVaksin namaVaksin,
            JenisVaksin jenisVaksin, String tglPendataan) {
        this.idVaksin = idVaksin;
        this.peternak = peternak;
        this.hewan = hewan;
        this.petugas = petugas;
        this.tglVaksin = tglVaksin;
        this.batchVaksin = batchVaksin;
        this.vaksinKe = vaksinKe;
        this.namaVaksin = namaVaksin;
        this.jenisVaksin = jenisVaksin;
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

    public JenisVaksin getJenisVaksin() {
        return jenisVaksin;
    }

    public void setJenisVaksin(JenisVaksin jenisVaksin) {
        this.jenisVaksin = jenisVaksin;
    }

    public NamaVaksin getNamaVaksin() {
        return namaVaksin;
    }

    public void setNamaVaksin(NamaVaksin namaVaksin) {
        this.namaVaksin = namaVaksin;
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
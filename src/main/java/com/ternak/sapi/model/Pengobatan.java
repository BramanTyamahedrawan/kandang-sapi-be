package com.ternak.sapi.model;


public class Pengobatan {
    private String idKasus;
    private String tanggalPengobatan;
    private String tanggalKasus;
    private Petugas petugas;
    private String namaInfrastruktur;
    private String lokasi;
    private String dosis;
    private String sindrom;
    private String diagnosaBanding;

    public Pengobatan() {
    }

    public Pengobatan(String idKasus, String tanggalPengobatan, String tanggalKasus, Petugas petugas,
            String namaInfrastruktur, String lokasi, String dosis, String sindrom, String diagnosaBanding) {
        this.idKasus = idKasus;
        this.tanggalPengobatan = tanggalPengobatan;
        this.tanggalKasus = tanggalKasus;
        this.petugas = petugas;
        this.namaInfrastruktur = namaInfrastruktur;
        this.lokasi = lokasi;
        this.dosis = dosis;
        this.sindrom = sindrom;
        this.diagnosaBanding = diagnosaBanding;
    }

    public String getIdKasus() {
        return idKasus;
    }

    public void setIdKasus(String idKasus) {
        this.idKasus = idKasus;
    }

    public String getTanggalPengobatan() {
        return tanggalPengobatan;
    }

    public void setTanggalPengobatan(String tanggalPengobatan) {
        this.tanggalPengobatan = tanggalPengobatan;
    }

    public String getTanggalKasus() {
        return tanggalKasus;
    }

    public void setTanggalKasus(String tanggalKasus) {
        this.tanggalKasus = tanggalKasus;
    }

    public Petugas getPetugas() {
        return petugas;
    }

    public void setPetugas(Petugas petugas) {
        this.petugas = petugas;
    }

    public String getNamaInfrastruktur() {
        return namaInfrastruktur;
    }

    public void setNamaInfrastruktur(String namaInfrastruktur) {
        this.namaInfrastruktur = namaInfrastruktur;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getSindrom() {
        return sindrom;
    }

    public void setSindrom(String sindrom) {
        this.sindrom = sindrom;
    }

    public String getDiagnosaBanding() {
        return diagnosaBanding;
    }

    public void setDiagnosaBanding(String diagnosaBanding) {
        this.diagnosaBanding = diagnosaBanding;
    }

    public boolean isValid() {
        return this.idKasus != null &&
                this.petugas != null;
    }
    
    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idKasus":
                this.idKasus = value;
                break;
            case "tanggalPengobatan":
                this.tanggalPengobatan = value;
                break;
            case "tanggalKasus":
                this.tanggalKasus = value;
                break;
            case "namaInfrastruktur":
                this.namaInfrastruktur = value;
                break;
            case "lokasi":
                this.lokasi = value;
                break;
            case "dosis":
                this.dosis = value;
                break;
            case "sindrom":
                this.sindrom = value;
                break;
            case "diagnosaBanding":
                this.diagnosaBanding = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}

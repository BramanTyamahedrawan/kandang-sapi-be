package com.ternak.sapi.model;

public class Pengobatan {
    private String idPengobatan;
    private String idKasus;
    private String tanggalPengobatan;
    private String tanggalKasus;
    private Petugas petugas;
    private String namaInfrastruktur;
    private String lokasi;
    private String dosis;
    private String sindrom;
    private String diagnosaBanding;
    private String provinsiPengobatan;
    private String kabupatenPengobatan;
    private String kecamatanPengobatan;
    private String desaPengobatan;

    public Pengobatan() {
    }

    public Pengobatan(String idPengobatan, String idKasus, String tanggalPengobatan, String tanggalKasus,
            Petugas petugas,
            String namaInfrastruktur, String lokasi, String dosis, String sindrom, String diagnosaBanding,
            String provinsiPengobatan, String kabupatenPengobatan, String kecamatanPengobatan, String desaPengobatan) {
        this.idPengobatan = idPengobatan;
        this.idKasus = idKasus;
        this.tanggalPengobatan = tanggalPengobatan;
        this.tanggalKasus = tanggalKasus;
        this.petugas = petugas;
        this.namaInfrastruktur = namaInfrastruktur;
        this.lokasi = lokasi;
        this.dosis = dosis;
        this.sindrom = sindrom;
        this.diagnosaBanding = diagnosaBanding;
        this.provinsiPengobatan = provinsiPengobatan;
        this.kabupatenPengobatan = kabupatenPengobatan;
        this.kecamatanPengobatan = kecamatanPengobatan;
        this.desaPengobatan = desaPengobatan;
    }

    public String getIdPengobatan() {
        return idPengobatan;
    }

    public void setIdPengobatan(String idPengobatan) {
        this.idPengobatan = idPengobatan;
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

    public String getProvinsiPengobatan() {
        return provinsiPengobatan;
    }

    public void setProvinsiPengobatan(String provinsiPengobatan) {
        this.provinsiPengobatan = provinsiPengobatan;
    }

    public String getKabupatenPengobatan() {
        return kabupatenPengobatan;
    }

    public void setKabupatenPengobatan(String kabupatenPengobatan) {
        this.kabupatenPengobatan = kabupatenPengobatan;
    }

    public String getKecamatanPengobatan() {
        return kecamatanPengobatan;
    }

    public void setKecamatanPengobatan(String kecamatanPengobatan) {
        this.kecamatanPengobatan = kecamatanPengobatan;
    }

    public String getDesaPengobatan() {
        return desaPengobatan;
    }

    public void setDesaPengobatan(String desaPengobatan) {
        this.desaPengobatan = desaPengobatan;
    }

    public boolean isValid() {
        return this.idPengobatan != null &&
                this.petugas != null;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idPengobatan":
                this.idPengobatan = value;
                break;
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
            case "provinsiPengobatan":
                this.provinsiPengobatan = value;
                break;
            case "kabupatenPengobatan":
                this.kabupatenPengobatan = value;
                break;
            case "kecamatanPengobatan":
                this.kecamatanPengobatan = value;
                break;
            case "desaPengobatan":
                this.desaPengobatan = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}

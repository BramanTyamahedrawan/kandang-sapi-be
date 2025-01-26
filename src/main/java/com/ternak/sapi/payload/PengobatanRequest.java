package com.ternak.sapi.payload;

public class PengobatanRequest {
    private String idPengobatan;
    private String idKasus;
    private String tanggalPengobatan;
    private String tanggalKasus;
    private String namaInfrastruktur;
    private String lokasi;
    private String dosis;
    private String sindrom;
    private String diagnosaBanding;
    private String provinsiPengobatan;
    private String kabupatenPengobatan;
    private String kecamatanPengobatan;
    private String desaPengobatan;

    // petugas
    private String petugasId;
    private String nikPetugas;
    private String namaPetugas;
    private String email;
    private String noTelp;
    private String wilayah;
    private String job;

    public PengobatanRequest() {
    }

    public PengobatanRequest(String idPengobatan, String idKasus, String tanggalPengobatan, String tanggalKasus,
            String petugasId,
            String namaInfrastruktur, String lokasi, String dosis, String sindrom, String diagnosaBanding,
            String provinsiPengobatan, String kabupatenPengobatan, String kecamatanPengobatan, String desaPengobatan,
            String nikPetugas, String namaPetugas, String email, String noTelp, String wilayah, String job) {
        this.idPengobatan = idPengobatan;
        this.idKasus = idKasus;
        this.tanggalPengobatan = tanggalPengobatan;
        this.tanggalKasus = tanggalKasus;
        this.petugasId = petugasId;
        this.namaInfrastruktur = namaInfrastruktur;
        this.lokasi = lokasi;
        this.dosis = dosis;
        this.sindrom = sindrom;
        this.diagnosaBanding = diagnosaBanding;
        this.provinsiPengobatan = provinsiPengobatan;
        this.kabupatenPengobatan = kabupatenPengobatan;
        this.kecamatanPengobatan = kecamatanPengobatan;
        this.desaPengobatan = desaPengobatan;
        this.nikPetugas = nikPetugas;
        this.namaPetugas = namaPetugas;
        this.email = email;
        this.noTelp = noTelp;
        this.wilayah = wilayah;
        this.job = job;
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

    public String getPetugasId() {
        return petugasId;
    }

    public void setPetugasId(String petugasId) {
        this.petugasId = petugasId;
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

    // Petugas
    public String getNikPetugas() {
        return nikPetugas;
    }

    public void setNikPetugas(String nikPetugas) {
        this.nikPetugas = nikPetugas;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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
            case "petugasId":
                this.petugasId = value;
                break;
            case "namaPetugas":
                this.namaPetugas = value;
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

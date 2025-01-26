package com.ternak.sapi.model;

public class Pkb {
    private String idKejadian;
    private String tanggalPkb;
    private Peternak peternak;
    private Hewan hewan;
    private Petugas petugas;
    private RumpunHewan rumpunHewan;
    private JenisHewan jenisHewan;
    private Kandang kandang;
    private String umurKebuntingan;
    private String jumlah;

    public Pkb() {
    }

    public Pkb(String idKejadian, String tanggalPkb, Peternak peternak, Hewan hewan, Petugas petugas,
            String umurKebuntingan, RumpunHewan rumpunHewan, JenisHewan jenisHewan, String jumlah,
            Kandang kandang) {
        this.idKejadian = idKejadian;
        this.tanggalPkb = tanggalPkb;
        this.peternak = peternak;
        this.hewan = hewan;
        this.petugas = petugas;
        this.umurKebuntingan = umurKebuntingan;
        this.rumpunHewan = rumpunHewan;
        this.jenisHewan = jenisHewan;
        this.jumlah = jumlah;
        this.kandang = kandang;
    }

    public String getIdKejadian() {
        return idKejadian;
    }

    public void setIdKejadian(String idKejadian) {
        this.idKejadian = idKejadian;
    }

    public String getTanggalPkb() {
        return tanggalPkb;
    }

    public void setTanggalPkb(String tanggalPkb) {
        this.tanggalPkb = tanggalPkb;
    }

    public Peternak getPeternak() {
        return peternak;
    }

    public void setPeternak(Peternak peternak) {
        this.peternak = peternak;
    }

    public Kandang getKandang() {
        return kandang;
    }

    public void setKandang(Kandang kandang) {
        this.kandang = kandang;
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

    public String getUmurKebuntingan() {
        return umurKebuntingan;
    }

    public void setUmurKebuntingan(String umurKebuntingan) {
        this.umurKebuntingan = umurKebuntingan;
    }

    public RumpunHewan getRumpunHewan() {
        return rumpunHewan;
    }

    public void setRumpunHewan(RumpunHewan rumpunHewan) {
        this.rumpunHewan = rumpunHewan;
    }

    public JenisHewan getJenisHewan() {
        return jenisHewan;
    }

    public void setJenisHewan(JenisHewan jenisHewan) {
        this.jenisHewan = jenisHewan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public boolean isValid() {
        return this.idKejadian != null &&
                this.peternak != null &&
                this.hewan != null &&
                this.petugas != null &&
                this.rumpunHewan != null &&
                this.jenisHewan != null &&
                this.kandang != null;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idKejadian":
                this.idKejadian = value;
                break;
            case "tanggalPkb":
                this.tanggalPkb = value;
                break;
            case "umurKebuntingan":
                this.umurKebuntingan = value;
                break;
            case "jumlah":
                this.jumlah = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}

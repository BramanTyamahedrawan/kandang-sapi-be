package com.ternak.sapi.model;

public class Inseminasi {
    private String idInseminasi;
    private String tanggalIB;
    private Peternak peternak;
    private Hewan hewan;
    private Petugas petugas;
    private String ib;
    private String idPejantan;
    private String idPembuatan;
    private String bangsaPejantan;
    private String produsen;

    public Inseminasi() {
    }

    public Inseminasi(String idInseminasi, String tanggalIB, Peternak peternak, Hewan hewan, Petugas petugas, String ib, String idPejantan, String idPembuatan, String bangsaPejantan, String produsen) {
        this.idInseminasi = idInseminasi;
        this.tanggalIB = tanggalIB;
        this.peternak = peternak;
        this.hewan = hewan;
        this.petugas = petugas;
        this.ib = ib;
        this.idPejantan = idPejantan;
        this.idPembuatan = idPembuatan;
        this.bangsaPejantan = bangsaPejantan;
        this.produsen = produsen;
    }

    public String getIdInseminasi() {
        return idInseminasi;
    }

    public void setIdInseminasi(String idInseminasi) {
        this.idInseminasi = idInseminasi;
    }

    public String getTanggalIB() {
        return tanggalIB;
    }

    public void setTanggalIB(String tanggalIB) {
        this.tanggalIB = tanggalIB;
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

    public String getIb() {
        return ib;
    }

    public void setIb(String ib) {
        this.ib = ib;
    }

    public String getIdPejantan() {
        return idPejantan;
    }

    public void setIdPejantan(String idPejantan) {
        this.idPejantan = idPejantan;
    }

    public String getIdPembuatan() {
        return idPembuatan;
    }

    public void setIdPembuatan(String idPembuatan) {
        this.idPembuatan = idPembuatan;
    }

    public String getBangsaPejantan() {
        return bangsaPejantan;
    }

    public void setBangsaPejantan(String bangsaPejantan) {
        this.bangsaPejantan = bangsaPejantan;
    }

    public String getProdusen() {
        return produsen;
    }

    public void setProdusen(String produsen) {
        this.produsen = produsen;
    }

    
   
    public boolean isValid() {
        return this.idInseminasi != null;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idInseminasi":
                this.idInseminasi = value;
                break;
            case "tanggalIB":
                this.tanggalIB = value;
                break;
            case "ib":
                this.ib = value;
                break;
            case "idPejantan":
                this.idPejantan = value;
                break;
            case "idPembuatan":
                this.idPembuatan = value;
                break;
            case "bangsaPejantan":
                this.bangsaPejantan = value;
                break;
            case "produsen":
                this.produsen = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}

package com.ternak.sapi.payload;

public class InseminasiRequest {
    private String idInseminasi;
    private String tanggalIB;
    private String peternak_id;
    private String hewan_id;
    private String petugas_id;
    private String ib;
    private String idPejantan;
    private String idPembuatan;
    private String bangsaPejantan;
    private String produsen;

    public InseminasiRequest() {
    }

    public InseminasiRequest(String idInseminasi, String tanggalIB, String peternak_id, 
            String hewan_id, String petugas_id, String ib, String idPejantan, String idPembuatan, 
            String bangsaPejantan, String produsen) {
        this.idInseminasi = idInseminasi;
        this.tanggalIB = tanggalIB;
        this.peternak_id = peternak_id;
        this.hewan_id = hewan_id;
        this.petugas_id = petugas_id;
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

    public String getPeternak_id() {
        return peternak_id;
    }

    public void setPeternak_id(String peternak_id) {
        this.peternak_id = peternak_id;
    }

    public String getHewan_id() {
        return hewan_id;
    }

    public void setHewan_id(String hewan_id) {
        this.hewan_id = hewan_id;
    }

    public String getPetugas_id() {
        return petugas_id;
    }

    public void setPetugas_id(String petugas_id) {
        this.petugas_id = petugas_id;
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

    

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idInseminasi":
                this.idInseminasi = value;
                break;
            case "tanggalIB":
                this.tanggalIB = value;
                break;
            case "peternak_id":
                this.peternak_id = value;
                break;
            case "hewan_id":
                this.hewan_id = value;
                break;
            case "petugas_id":
                this.petugas_id = value;
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

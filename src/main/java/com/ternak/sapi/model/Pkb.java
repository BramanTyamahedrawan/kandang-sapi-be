package com.ternak.sapi.model;

public class Pkb {
    private String idKejadian;
    private String tanggalPkb;
    private Peternak peternak;
    private Hewan hewan;
    private Petugas petugas;
    private String spesies;
    private String umurKebuntingan;
    
    public Pkb() {
    }

    public Pkb(String idKejadian, String tanggalPkb, Peternak peternak, Hewan hewan, Petugas petugas, String spesies, String umurKebuntingan) {
        this.idKejadian = idKejadian;
        this.tanggalPkb = tanggalPkb;
        this.peternak = peternak;
        this.hewan = hewan;
        this.petugas = petugas;
        this.spesies = spesies;
        this.umurKebuntingan = umurKebuntingan;
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

    public String getSpesies() {
        return spesies;
    }

    public void setSpesies(String spesies) {
        this.spesies = spesies;
    }

    public String getUmurKebuntingan() {
        return umurKebuntingan;
    }

    public void setUmurKebuntingan(String umurKebuntingan) {
        this.umurKebuntingan = umurKebuntingan;
    }

    public boolean isValid() {
        return this.idKejadian != null &&  
               this.peternak != null && 
               this.hewan != null && 
               this.petugas != null;
    }
    
    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idKejadian":
                this.idKejadian = value;
                break;
            case "tanggalPkb":
                this.tanggalPkb = value;
                break;
            case "spesies":
                this.spesies = value;
                break;
            case "umurKebuntingan":
                this.umurKebuntingan = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}

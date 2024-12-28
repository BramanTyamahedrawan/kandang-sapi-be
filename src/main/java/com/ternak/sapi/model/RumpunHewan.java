package com.ternak.sapi.model;

public class RumpunHewan {
    private String idRumpunHewan;
    private String rumpun;
    private String deskripsi;

    public RumpunHewan() {
    }

    public String getIdRumpunHewan() {
        return idRumpunHewan;
    }

    public void setIdRumpunHewan(String idRumpunHewan) {
        this.idRumpunHewan = idRumpunHewan;
    }

    public String getRumpun() {
        return rumpun;
    }

    public void setRumpun(String rumpun) {
        this.rumpun = rumpun;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public boolean isValid(){
        return this.rumpun != null &&
                this.deskripsi != null;
    }

    public void set(String fieldName, String value){
        switch (fieldName){
            case "idRumpunHewan":
                this.idRumpunHewan = value;
                break;
            case "rumpun":
                this.rumpun = value;
                break;
            case "deskripsi":
                this.deskripsi = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}

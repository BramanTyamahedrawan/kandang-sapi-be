package com.ternak.sapi.payload;

public class PetugasRequest {
    private String petugasId;
    private String nikPetugas;
    private String namaPetugas;
    private String noTelp;
    private String email;
    private String job;
    private String wilayah;

    public PetugasRequest() {
    }

    public PetugasRequest(String petugasId,String nikPetugas, String namaPetugas, String noTelp, String email) {
        this.petugasId = petugasId;
        this.nikPetugas = nikPetugas;
        this.namaPetugas = namaPetugas;
        this.noTelp = noTelp;
        this.email = email;
        this.job = "";
    }

    public PetugasRequest(String petugasId,String nikPetugas, String namaPetugas, String noTelp, String email, String job, String wilayah) {
        this.petugasId = petugasId;
        this.nikPetugas = nikPetugas;
        this.namaPetugas = namaPetugas;
        this.noTelp = noTelp;
        this.email = email;
        this.job = job;
        this.wilayah = wilayah;
    }

    public void setPetugasId(String petugasId) {
        this.petugasId = petugasId;
    }

    public String getPetugasId() {
        return petugasId;
    }

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

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "nikPetugas":
                this.nikPetugas = value;
                break;
            case "namaPetugas":
                this.namaPetugas = value;
                break;
            case "noTelp":
                this.noTelp = value;
                break;
            case "email":
                this.email = value;
                break;
            case "job":
                this.job = value;
                break;
            case "wilayah":
                this.wilayah = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}

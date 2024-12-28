package com.ternak.sapi.payload;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class KelahiranRequest {
    private String idKejadian;
    private String tanggalLaporan;
    private String tanggalLahir;
    private String peternak_id;
    private String hewan_id;
    private String kandang_id;
    private String petugas_id;
    private String inseminasi_id;
    private String eartagAnak;
    private String jenisKelaminAnak;
    private String spesies;
    
    public KelahiranRequest() {
    }

    public KelahiranRequest(String idKejadian, String tanggalLaporan, String tanggalLahir, String peternak_id, 
            String hewan_id, String kandang_id, String petugas_id, String inseminasi_id, String eartagAnak, String jenisKelaminAnak, 
            String spesies) {
        this.idKejadian = idKejadian;
        this.tanggalLaporan = tanggalLaporan;
        this.tanggalLahir = tanggalLahir;
        this.peternak_id = peternak_id;
        this.hewan_id = hewan_id;
        this.kandang_id = kandang_id;
        this.petugas_id = petugas_id;
        this.inseminasi_id = inseminasi_id;
        this.eartagAnak = eartagAnak;
        this.jenisKelaminAnak = jenisKelaminAnak;
        this.spesies = spesies;
    }

    public String getIdKejadian() {
        return idKejadian;
    }

    public void setIdKejadian(String idKejadian) {
        this.idKejadian = idKejadian;
    }

    public String getTanggalLaporan() {
        return tanggalLaporan;
    }

    public void setTanggalLaporan(String tanggalLaporan) {
        this.tanggalLaporan = tanggalLaporan;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
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

    public String getKandang_id() {
        return kandang_id;
    }

    public void setKandang_id(String kandang_id) {
        this.kandang_id = kandang_id;
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

    public String getInseminasi_id() {
        return inseminasi_id;
    }

    public void setInseminasi_id(String inseminasi_id) {
        this.inseminasi_id = inseminasi_id;
    }

    public String getEartagAnak() {
        return eartagAnak;
    }

    public void setEartagAnak(String eartagAnak) {
        this.eartagAnak = eartagAnak;
    }

    public String getJenisKelaminAnak() {
        return jenisKelaminAnak;
    }

    public void setJenisKelaminAnak(String jenisKelaminAnak) {
        this.jenisKelaminAnak = jenisKelaminAnak;
    }

    public String getSpesies() {
        return spesies;
    }

    public void setSpesies(String spesies) {
        this.spesies = spesies;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idKejadian":
                this.idKejadian = value;
                break;
            case "tanggalLaporan":
                this.tanggalLaporan = value;
                break;
            case "tanggalLahir":
                this.tanggalLahir = value;
                break;
            case "peternak_id":
                this.peternak_id = value;
                break;
            case "hewan_id":
                this.hewan_id = value;
                break;
            case "kandang_id":
                this.kandang_id = value;
                break;
            case "petugas_id":
                this.petugas_id = value;
                break;
            case "inseminasi_id":
                this.inseminasi_id = value;
                break;
            case "eartagAnak":
                this.eartagAnak = value;
                break;
            case "jenisKelaminAnak":
                this.jenisKelaminAnak = value;
                break;
            case "spesies":
                this.spesies = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}

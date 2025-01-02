/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ternak.sapi.model;

public class JenisHewan {
    private String idJenisHewan;
    private String jenis;
    private String deskripsi;

    public JenisHewan() {
    }

    public JenisHewan(String idJenisHewan, String jenis, String deskripsi) {
        this.idJenisHewan = idJenisHewan;
        this.jenis = jenis;
        this.deskripsi = deskripsi;
    }

    public String getIdJenisHewan() {
        return idJenisHewan;
    }

    public void setIdJenisHewan(String idJenisHewan) {
        this.idJenisHewan = idJenisHewan;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public boolean isValid() {
        return this.jenis != null &&
                this.deskripsi != null;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idJenisHewan":
                this.idJenisHewan = value;
                break;
            case "jenis":
                this.jenis = value;
                break;
            case "deskripsi":
                this.deskripsi = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }

}

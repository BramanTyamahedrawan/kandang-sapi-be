/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ternak.sapi.payload;

import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.Peternak;

public class RumpunHewanRequest {

    private String idRumpunHewan;
    private String rumpun;
    private String deskripsi;

    public RumpunHewanRequest() {
    }

    public RumpunHewanRequest(String idRumpunHewan, String rumpun, String deskripsi) {
        this.idRumpunHewan = idRumpunHewan;
        this.rumpun = rumpun;
        this.deskripsi = deskripsi;
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

    public void set(String fieldName, String value) {
        switch (fieldName) {
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

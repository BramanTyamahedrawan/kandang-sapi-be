/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ternak.sapi.model;

/**
 *
 * @author reyza1
 */
public class Penghijauan {
    private String idPenghijauan;
    private String namaTempat;
    private String lokasi;
    private String keterangan;
    private String file_path;

    public enum AnswerType {
        IMAGE,
        AUDIO,
        VIDEO,
        NORMAL,
    }

    public Penghijauan(){
    }

    public Penghijauan(String idPenghijauan, String namaTempat, String lokasi, String file_path){
        this.idPenghijauan = idPenghijauan;
        this.namaTempat = namaTempat;
        this.lokasi = lokasi;
        this.keterangan = keterangan;
        this.file_path = file_path;
    }

    public String getIdPenghijauan(){
        return idPenghijauan;
    }
    public void setIdPenghijauan(String idPenghijauan){
        this.idPenghijauan = idPenghijauan;
    }

    public String getNamaTempat(){
        return namaTempat;
    }
    public void setNamaTempat(String namaTempat){
        this.namaTempat = namaTempat;
    }

    public String getLokasi(){
        return lokasi;
    }
    public void setLokasi(String lokasi){
        this.lokasi = lokasi;
    }

    public String getKeterangan(){
        return keterangan;
    }
    public void setKeterangan(String lokasi){
        this.keterangan = keterangan;
    }

    public String getFile_path(){
        return file_path;
    }
    public void setFile_path(String file_path){
        this.file_path = file_path;
    }

    public boolean isValid(){
        return this.idPenghijauan != null &&
                this.namaTempat != null &&
                this.lokasi != null &&
                this.lokasi != null &&
                this.keterangan != null &&
                this.file_path != null;
    }

    public void set(String fieldName, String value){
        switch (fieldName){
            case "idPenghijauan":
                this.idPenghijauan = value;
                break;
            case "namaTempat":
                this.namaTempat = value;
                break;
            case "lokasi":
                this.lokasi = value;
                break;
            case "keterangan":
                this.keterangan = value;
                break;
            case "file_path":
                this.file_path = value;
                break;
             default:
                    throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}

package com.ternak.sapi.model;

import java.time.Instant;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class User {
    private String id;
    private String nik;
    private String name;
    private String username;
    private String alamat;
    @Email
    private String email;
    private String password;
    @NotBlank
    private String role;

    private Instant createdAt;

    public User() {

    }

    public User(String id,String nik, String name, String username, String email, String alamat, String password, String role,
            Instant createdAt) {
        this.name = name;
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.nik = nik;
        this.alamat = alamat;
        this.createdAt = createdAt;
    }

    public User(String name, String username, String email, String password, String role, Instant createdAt) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    public boolean isValid(){
        return this.id != null &&
                this.name != null &&
                this.nik != null &&
                this.email != null &&
                this.password != null &&
                this.alamat != null &&
                this.role != null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "id":
                this.id = value;
                break;
            case "name":
                this.name = value;
                break;
            case "nik":
                this.nik = value;
                break;
            case "username":
                this.username = value;
                break;
            case "email":
                this.email = value;
                break;
            case "alamat":
                this.alamat = value;
                break;
            case "password":
                this.password = value;
                break;
            case "role":
                this.role = value;
                break;
            case "createdAt":
                this.createdAt = Instant.parse(value);
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
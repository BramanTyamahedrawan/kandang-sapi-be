package com.ternak.sapi.payload;

import java.time.Instant;

public class UserRequest {
    private String id;
    private String nik;
    private String name;
    private String username;
    private String email;
    private String password;
    private String alamat;
    private String role;
    private Instant createdAt;
    public UserRequest() {
    }

    public UserRequest(String id, String nik, String name, String username, String email, String password,
            String alamat, String role, Instant createdAt) {
        this.id = id;
        this.nik = nik;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.alamat = alamat;
        this.role = role;
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNik() {
        return nik;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "id":
                this.id = value;
                break;
            case "nik":
                this.nik = value;
                break;
            case "name":
                this.name = value;
                break;
            case "username":
                this.username = value;
                break;
            case "email":
                this.email = value;
                break;
            case "password":
                this.password = value;
                break;
            case "alamat":
                this.alamat = value;
                break;
            case "role":
                this.role = value;
                break;

            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}

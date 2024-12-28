package com.ternak.sapi.payload;

public class UserSummary {
    private String id;
    private String username;
    private String name;
    private String role;
    private String description;

    private String avatar;

    public UserSummary(String id, String username, String name, String role, String description, String avatar) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
        this.description = description;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

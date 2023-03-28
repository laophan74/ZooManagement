package com.example.zoomanagement.Model;

public class User {
    private String email;
    private String userName;
    private String birth;
    private String address;
    private Boolean isAdmin;

    public void ChangeDataUser(String email,String userName, String birth, String address, Boolean isAdmin) {
        this.email = email;
        this.userName = userName;
        this.birth = birth;
        this.address = address;
        this.isAdmin = isAdmin;
    }

    public User() {
        this.email = email;
        this.userName = userName;
        this.birth = birth;
        this.address = address;
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}

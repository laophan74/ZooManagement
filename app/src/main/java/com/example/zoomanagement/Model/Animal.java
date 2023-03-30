package com.example.zoomanagement.Model;

public class Animal {
    private String name;
    private String picture;
    private String origin;
    private Float size;
    private Float weight;
    private String status;
    private String document;
    private String Staff;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void SetDataAnimal(String name, String picture, String origin, Float size, Float weight, String status) {
        this.name = name;
        this.picture = picture;
        this.origin = origin;
        this.size = size;
        this.weight = weight;
        this.status = status;
    }

    public Animal() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStaff() {
        return Staff;
    }

    public void setStaff(String staff) {
        Staff = staff;
    }
}

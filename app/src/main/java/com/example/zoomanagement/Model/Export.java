package com.example.zoomanagement.Model;

public class Export {
    private String exportDate;
    private String materialName;
    private Integer quantity;
    private String unit;
    private String staff;
    private String document;

    public Export(String exportDate, String materialName, Integer quantity, String unit, String staff) {
        this.exportDate = exportDate;
        this.materialName = materialName;
        this.quantity = quantity;
        this.unit = unit;
        this.staff = staff;
    }
    public Export(){}
    public String getExportDate() {
        return exportDate;
    }

    public void setExportDate(String exportDate) {
        this.exportDate = exportDate;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}

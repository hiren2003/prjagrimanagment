package com.project.agriculturemanagmentapp;

public class clsOrderModel {
    clsEcommModel clsEcommModel;
    String key,mo,address,qty,date;

    public clsOrderModel(com.project.agriculturemanagmentapp.clsEcommModel clsEcommModel, String key, String mo, String address, String qty, String date) {
        this.clsEcommModel = clsEcommModel;
        this.key = key;
        this.mo = mo;
        this.address = address;
        this.qty = qty;
        this.date = date;
    }

    public clsOrderModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public com.project.agriculturemanagmentapp.clsEcommModel getClsEcommModel() {
        return clsEcommModel;
    }

    public void setClsEcommModel(com.project.agriculturemanagmentapp.clsEcommModel clsEcommModel) {
        this.clsEcommModel = clsEcommModel;
    }

    public String getMo() {
        return mo;
    }

    public void setMo(String mo) {
        this.mo = mo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

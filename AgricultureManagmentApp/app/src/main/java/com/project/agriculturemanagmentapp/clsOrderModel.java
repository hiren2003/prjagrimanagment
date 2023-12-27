package com.project.agriculturemanagmentapp;

public class clsOrderModel {
    clsEcommModel clsEcommModel;
    String key,name,mo,address,qty,date,time,PaymentMode,PaymentId,PaymentStatus;


    public clsOrderModel(com.project.agriculturemanagmentapp.clsEcommModel clsEcommModel, String key, String name, String mo, String address, String qty, String date, String time, String paymentMode, String paymentId, String paymentStatus) {
        this.clsEcommModel = clsEcommModel;
        this.key = key;
        this.name = name;
        this.mo = mo;
        this.address = address;
        this.qty = qty;
        this.date = date;
        this.time = time;
        PaymentMode = paymentMode;
        PaymentId = paymentId;
        PaymentStatus = paymentStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(String paymentId) {
        PaymentId = paymentId;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }
}

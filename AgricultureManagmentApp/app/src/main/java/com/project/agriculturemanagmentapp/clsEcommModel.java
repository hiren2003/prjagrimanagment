package com.project.agriculturemanagmentapp;

public class clsEcommModel {
    String key,pname,img,price,dpec,des,recomm,qty;
    Float cgst,sgst,discount;
    String type;

    public clsEcommModel(String key, String pname, String img, String price, String dpec, String des, String recomm, String qty, Float cgst, Float sgst, Float discount, String type) {
        this.key = key;
        this.pname = pname;
        this.img = img;
        this.price = price;
        this.dpec = dpec;
        this.des = des;
        this.recomm = recomm;
        this.qty = qty;
        this.cgst = cgst;
        this.sgst = sgst;
        this.discount = discount;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public clsEcommModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDpec() {
        return dpec;
    }

    public void setDpec(String dpec) {
        this.dpec = dpec;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getRecomm() {
        return recomm;
    }

    public void setRecomm(String recomm) {
        this.recomm = recomm;
    }

    public Float getCgst() {
        return cgst;
    }

    public void setCgst(Float cgst) {
        this.cgst = cgst;
    }

    public Float getSgst() {
        return sgst;
    }

    public void setSgst(Float sgst) {
        this.sgst = sgst;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }
}

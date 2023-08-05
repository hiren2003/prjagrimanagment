package com.project.agriculturemanagmentapp;

public class clsEcommModel {
    String key,pname,img,price,dpec,des,recomm,qty;

    public clsEcommModel(String key, String pname, String img, String price, String dpec, String des, String recomm, String qty) {
        this.key = key;
        this.pname = pname;
        this.img = img;
        this.price = price;
        this.dpec = dpec;
        this.des = des;
        this.recomm = recomm;
        this.qty = qty;
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
}

package com.project.agriculturemanagmentapp;

public class ClsCultivationProductModel {
    String category,pname,specie,qty,price,payment,state,district,tehsil,village,des,img,prfpc,uname,mo,date,key;

    public ClsCultivationProductModel(String category, String pname, String specie, String qty, String price, String payment, String state, String district, String tehsil, String village, String des, String img, String prfpc, String uname, String mo, String date, String key) {
        this.category = category;
        this.pname = pname;
        this.specie = specie;
        this.qty = qty;
        this.price = price;
        this.payment = payment;
        this.state = state;
        this.district = district;
        this.tehsil = tehsil;
        this.village = village;
        this.des = des;
        this.img = img;
        this.prfpc = prfpc;
        this.uname = uname;
        this.mo = mo;
        this.date = date;
        this.key = key;
    }
    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public String getMo() {
        return mo;
    }

    public void setMo(String mo) {
        this.mo = mo;
    }

    public ClsCultivationProductModel() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrfpc() {
        return prfpc;
    }

    public void setPrfpc(String prfpc) {
        this.prfpc = prfpc;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

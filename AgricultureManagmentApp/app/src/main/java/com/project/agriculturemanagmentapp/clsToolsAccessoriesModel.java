package com.project.agriculturemanagmentapp;

public class clsToolsAccessoriesModel {
    String key,pname,sname,mo,price,state,district,tehsil,village,desc,img,date,month,category,umo;

    public clsToolsAccessoriesModel(String key, String pname, String sname, String mo, String price, String state, String district, String tehsil, String village, String desc, String img, String date, String month, String category, String umo) {
        this.key = key;
        this.pname = pname;
        this.sname = sname;
        this.mo = mo;
        this.price = price;
        this.state = state;
        this.district = district;
        this.tehsil = tehsil;
        this.village = village;
        this.desc = desc;
        this.img = img;
        this.date = date;
        this.month = month;
        this.category = category;
        this.umo = umo;
    }

    public String getUmo() {
        return umo;
    }

    public void setUmo(String umo) {
        this.umo = umo;
    }

    public clsToolsAccessoriesModel() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getMo() {
        return mo;
    }

    public void setMo(String mo) {
        this.mo = mo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

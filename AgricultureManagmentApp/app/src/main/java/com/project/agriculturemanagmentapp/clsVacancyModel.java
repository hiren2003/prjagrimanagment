package com.project.agriculturemanagmentapp;

public class clsVacancyModel {
    String key,oname,ocan,twork,wtype,eamt,wdur,state,district,tehsil,village,des,date,umo;

    public clsVacancyModel(String key, String oname, String ocan, String twork, String wtype, String eamt, String wdur, String state, String district, String tehsil, String village, String des, String date, String umo) {
        this.key = key;
        this.oname = oname;
        this.ocan = ocan;
        this.twork = twork;
        this.wtype = wtype;
        this.eamt = eamt;
        this.wdur = wdur;
        this.state = state;
        this.district = district;
        this.tehsil = tehsil;
        this.village = village;
        this.des = des;
        this.date = date;
        this.umo = umo;
    }

    public String getOcan() {
        return ocan;
    }

    public void setOcan(String ocan) {
        this.ocan = ocan;
    }

    public clsVacancyModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getTwork() {
        return twork;
    }

    public void setTwork(String twork) {
        this.twork = twork;
    }

    public String getWtype() {
        return wtype;
    }

    public void setWtype(String wtype) {
        this.wtype = wtype;
    }

    public String getEamt() {
        return eamt;
    }

    public void setEamt(String eamt) {
        this.eamt = eamt;
    }

    public String getWdur() {
        return wdur;
    }

    public void setWdur(String wdur) {
        this.wdur = wdur;
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


    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getUmo() {
        return umo;
    }

    public void setUmo(String umo) {
        this.umo = umo;
    }
}

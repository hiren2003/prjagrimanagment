package com.project.agriculturemanagmentapp;

public class clsChatModel {
    String msg,smo,rmo,date,time,key,img;

    public clsChatModel(String msg, String smo, String rmo, String date, String time, String key, String img) {
        this.msg = msg;
        this.smo = smo;
        this.rmo = rmo;
        this.date = date;
        this.time = time;
        this.key = key;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public clsChatModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSmo() {
        return smo;
    }

    public void setSmo(String smo) {
        this.smo = smo;
    }

    public String getRmo() {
        return rmo;
    }

    public void setRmo(String rmo) {
        this.rmo = rmo;
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
}

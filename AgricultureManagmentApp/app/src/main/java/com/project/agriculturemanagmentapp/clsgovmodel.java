package com.project.agriculturemanagmentapp;

public class clsgovmodel {
    String key,name,url,state,des,lnk;

    public clsgovmodel(String key, String name, String url, String state, String des, String lnk) {
        this.key = key;
        this.name = name;
        this.url = url;
        this.state = state;
        this.des = des;
        this.lnk = lnk;
    }

    public clsgovmodel() {
    }

    public String getDes() {
        return des;
    }

    public String getLnk() {
        return lnk;
    }

    public void setLnk(String lnk) {
        this.lnk = lnk;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

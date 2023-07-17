package com.project.agriculturemanagmentapp;

public class clsFeedModel {
    String prfpc,uname,date,post,des,key;

    public clsFeedModel(String prfpc, String uname, String date, String post, String des, String key) {
        this.prfpc = prfpc;
        this.uname = uname;
        this.date = date;
        this.post = post;
        this.des = des;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public clsFeedModel() {
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}

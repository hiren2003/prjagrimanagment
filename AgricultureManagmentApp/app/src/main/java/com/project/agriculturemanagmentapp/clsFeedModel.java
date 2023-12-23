package com.project.agriculturemanagmentapp;

public class clsFeedModel {
    String date, post, des, key, key2, mediatype,umo;

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public clsFeedModel(String date, String post, String des, String key, String key2, String mediatype, String umo) {
        this.date = date;
        this.post = post;
        this.des = des;
        this.key = key;
        this.key2 = key2;
        this.mediatype = mediatype;
        this.umo = umo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public clsFeedModel() {
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
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

    public String getPost() {
        return post;
    }

    public String getUmo() {
        return umo;
    }

    public void setUmo(String umo) {
        this.umo = umo;
    }

    public void setPost(String post) {
        this.post = post;
    }
}

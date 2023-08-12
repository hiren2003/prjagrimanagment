package com.project.agriculturemanagmentapp;

public class clsCommentModel {
    String key,uname,prfpc,comment;

    public clsCommentModel(String key, String uname, String prfpc, String comment) {
        this.key = key;
        this.uname = uname;
        this.prfpc = prfpc;
        this.comment = comment;
    }

    public clsCommentModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPrfpc() {
        return prfpc;
    }

    public void setPrfpc(String prfpc) {
        this.prfpc = prfpc;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

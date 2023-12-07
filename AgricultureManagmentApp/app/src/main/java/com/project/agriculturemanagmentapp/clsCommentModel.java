package com.project.agriculturemanagmentapp;

public class clsCommentModel {
    String key,uname,prfpc,comment;
    String mo;

    public clsCommentModel(String key, String comment, String mo) {
        this.key = key;
        this.comment = comment;
        this.mo = mo;
    }

    public String getMo() {
        return mo;
    }

    public void setMo(String mo) {
        this.mo = mo;
    }

    public clsCommentModel() {
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

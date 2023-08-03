package com.project.agriculturemanagmentapp;

public class clsUserModel {
    String uname;
    String mo;
    String url,email,dob,gender,address,state;

    public clsUserModel(String uname, String mo, String url, String email, String dob, String gender, String address, String state) {
        this.uname = uname;
        this.mo = mo;
        this.url = url;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public clsUserModel(String uname, String mo) {
        this.uname = uname;
        this.mo = mo;
    }

    public clsUserModel() {
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getMo() {
        return mo;
    }

    public void setMo(String mo) {
        this.mo = mo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

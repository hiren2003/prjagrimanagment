package com.project.agriculturemanagmentapp;

public class clsNewsModel {
    String key,headline,link,description,img;

    public clsNewsModel(String key, String headline, String link, String description, String img) {
        this.key = key;
        this.headline = headline;
        this.link = link;
        this.description = description;
        this.img = img;
    }

    public clsNewsModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}


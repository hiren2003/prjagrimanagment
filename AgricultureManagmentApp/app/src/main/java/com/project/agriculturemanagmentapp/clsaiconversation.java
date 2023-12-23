package com.project.agriculturemanagmentapp;

import android.graphics.Bitmap;
import android.net.Uri;

public class clsaiconversation {
    Uri imguri;
    String query;
    boolean isai;

    public clsaiconversation(Uri imguri, String query, boolean isai) {
        this.imguri = imguri;
        this.query = query;
        this.isai = isai;
    }

    public Uri getImguri() {
        return imguri;
    }

    public void setImguri(Uri imguri) {
        this.imguri = imguri;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

package com.qyai.watch_app.utils;

import java.io.Serializable;

public class LactionInfo implements Serializable {
    private String locX;
    private String locY;

    public String getLocX() {
        return locX;
    }


    public void setLocX(String locX) {
        this.locX = locX;
    }

    public String getLocY() {
        return locY;
    }

    public void setLocY(String locY) {
        this.locY = locY;
    }

    @Override
    public String toString() {
        return "LactionInfo{" +
                "locX='" + locX + '\'' +
                ", locY='" + locY + '\'' +
                '}';
    }
}

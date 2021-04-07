package com.qyai.watch_app.utils;

public class GpsReq {


    /**
     * floorId : string
     * mapId : string
     * posX : 0
     * posY : 0
     * posZ : 0
     * userId : string
     */

//    private String floorId;
//    private String mapId;
    private String posX;
    private String posY;
    private String posZ;
    private String userId;


    public String getPosX() {
        return posX;
    }

    public void setPosX(String posX) {
        this.posX = posX;
    }

    public String getPosY() {
        return posY;
    }

    public void setPosY(String posY) {
        this.posY = posY;
    }

    public String getPosZ() {
        return posZ;
    }

    public void setPosZ(String posZ) {
        this.posZ = posZ;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

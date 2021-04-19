package com.qyai.watch_app.message.bean;

import java.io.Serializable;

public class AlarmPushBean implements Serializable {


    /**
     * id : 6161aedbba6842c994594f8155a2643d
     * authorId : 145
     * authorName : 京-GH256F
     * authorType : 2
     * areaId : null
     * areaVersion : null
     * deviceId : 39678
     * posX : -10.1456983511
     * posY : 6.0607456425
     * posZ : 0.0
     * type : 1
     * dealStatus : 0
     * content : 「京-GH256F」发起了求救，请立即处理！
     * dealContent : null
     * source : 2
     * createTime : 2021-02-24 11:52:10
     * dealTime : null
     * dealUser : null
     * dealOpinion : null
     * fenceId : null
     * fenceVersion : null
     * fenceName : null
     * areaShape : null
     * areaPath : null
     * areaCircle : null
     * areaZLoc : null
     * fenceColor : null
     * areaName : null
     * healthType : null
     * address : null
     * smcContent : null
     * dealUserName : null
     * dealStatusName : 未处理
     * dealOpinionName : null
     * typeName : SOS
     */

    public String id;
    public String authorId;
    public String authorName;
    public String authorType;
    public String areaId;
    public String areaVersion;
    public String deviceId;
    public String posX;
    public String posY;
    public String posZ;
    public int type;
    public int dealStatus;
    public String content;
    public String dealContent;
    public int source;
    public String createTime;
    public String dealTime;
    public String dealUser;
    public String dealOpinion;
    public String fenceId;
    public String fenceVersion;
    public String fenceName;
    public String areaShape;
    public String areaPath;
    public String areaCircle;
    public String areaZLoc;
    public String fenceColor;
    public String areaName;
    public String healthType;
    public String address;
    public String smcContent;
    public String dealUserName;
    public String dealStatusName;
    public String dealOpinionName;
    public String typeName;
    public  String temperature;
    public  String heartRate;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorType() {
        return authorType;
    }

    public void setAuthorType(String authorType) {
        this.authorType = authorType;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaVersion() {
        return areaVersion;
    }

    public void setAreaVersion(String areaVersion) {
        this.areaVersion = areaVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDealContent() {
        return dealContent;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealUser() {
        return dealUser;
    }

    public void setDealUser(String dealUser) {
        this.dealUser = dealUser;
    }

    public String getDealOpinion() {
        return dealOpinion;
    }

    public void setDealOpinion(String dealOpinion) {
        this.dealOpinion = dealOpinion;
    }

    public String getFenceId() {
        return fenceId;
    }

    public void setFenceId(String fenceId) {
        this.fenceId = fenceId;
    }

    public String getFenceVersion() {
        return fenceVersion;
    }

    public void setFenceVersion(String fenceVersion) {
        this.fenceVersion = fenceVersion;
    }

    public String getFenceName() {
        return fenceName;
    }

    public void setFenceName(String fenceName) {
        this.fenceName = fenceName;
    }

    public String getAreaShape() {
        return areaShape;
    }

    public void setAreaShape(String areaShape) {
        this.areaShape = areaShape;
    }

    public String getAreaPath() {
        return areaPath;
    }

    public void setAreaPath(String areaPath) {
        this.areaPath = areaPath;
    }

    public String getAreaCircle() {
        return areaCircle;
    }

    public void setAreaCircle(String areaCircle) {
        this.areaCircle = areaCircle;
    }

    public String getAreaZLoc() {
        return areaZLoc;
    }

    public void setAreaZLoc(String areaZLoc) {
        this.areaZLoc = areaZLoc;
    }

    public String getFenceColor() {
        return fenceColor;
    }

    public void setFenceColor(String fenceColor) {
        this.fenceColor = fenceColor;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getHealthType() {
        return healthType;
    }

    public void setHealthType(String healthType) {
        this.healthType = healthType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSmcContent() {
        return smcContent;
    }

    public void setSmcContent(String smcContent) {
        this.smcContent = smcContent;
    }

    public String getDealUserName() {
        return dealUserName;
    }

    public void setDealUserName(String dealUserName) {
        this.dealUserName = dealUserName;
    }

    public String getDealStatusName() {
        return dealStatusName;
    }

    public void setDealStatusName(String dealStatusName) {
        this.dealStatusName = dealStatusName;
    }

    public String getDealOpinionName() {
        return dealOpinionName;
    }

    public void setDealOpinionName(String dealOpinionName) {
        this.dealOpinionName = dealOpinionName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

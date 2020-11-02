package com.qyai.beaconlib.bean;

import java.io.Serializable;

public class QYPositionBean implements Serializable {
    public double x;
    public double y;
    public double z;
    public int region;
    public String buildId;
    public long timestamp;
    public String userId;
    public int direction;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public QYPositionBean() {

    }

    public QYPositionBean(double x, double y, double z, long timestamp) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;
    }

    public QYPositionBean(double x, double y, double z, long timestamp, int direction) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;
        this.direction=direction;
    }
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public int getRegion() {
        return region;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getBuildId() {
        return buildId;
    }

    public String getUserId() {
        return userId;
    }
    /**
     * 获取我们的楼层信息
     *
     * @param region2 高博的楼层
     * @return
     */
    public String getRange() {
        if (region == 0) {
            return "b3";
        } else if (region == 1) {
            return "b2";
        } else if (region == 2) {
            return "b1";
        }
        return "";
    }

    @Override
    public String toString() {
        return "QYPositionBean{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", region=" + region +
                ", buildId='" + buildId + '\'' +
                ", timestamp=" + timestamp +
                ", userId='" + userId + '\'' +
                ", direction=" + direction +
                '}';
    }
}

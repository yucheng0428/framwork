package com.qyai.watch_app.message.bean;

import java.io.Serializable;
import java.util.List;

public class AlarmInfo implements Serializable {

    /**
     * areaShape : 1
     * typeName : 越界
     * source : 1
     * type : 2
     * deviceId : 39678
     * content : 「健康守护测试人员txr」越界了，请及时关注处理！
     * areaVersion : 1
     * areaName : 区域0
     * id : ba2bdf635bd748bf83458ad5d918dbb7
     * dealStatusName : 未处理
     * areaZLoc : 0.1,0.1,0.1,0.1,0.1
     * authorType : 1
     * dealStatus : 0
     * authorId : 361
     * posX : -2.531
     * fenceName : bug测试
     * posY : 6.6553
     * posZ : 0.0
     * areaId : 3022
     * createTime : 2021-02-04 11:30:05
     * authorName : 健康守护测试人员txr
     * fenceColor : #ff0000
     * areaPath : {"closed":true,"type":"path","value":"((-28.69,-38.52),(-28.69,7.67),(28.0,7.67),(28.0,-38.52),(-28.69,-38.52))","open":false,"points":[{"x":-28.69,"y":-38.52,"type":"point","value":"(-28.69,-38.52)"},{"x":-28.69,"y":7.67,"type":"point","value":"(-28.69,7.67)"},{"x":28,"y":7.67,"type":"point","value":"(28.0,7.67)"},{"x":28,"y":-38.52,"type":"point","value":"(28.0,-38.52)"},{"x":-28.69,"y":-38.52,"type":"point","value":"(-28.69,-38.52)"}]}
     * fenceId : 436
     */

    private int areaShape;
    private String typeName;
    private int source;
    private int type;
    private String deviceId;
    private String content;
    private int areaVersion;
    private String areaName;
    private String dealContent;
    private String id;
    private String dealStatusName;
    private String areaZLoc;
    private String authorType;
    private int dealStatus;
    private String authorId;
    private double posX;
    private String fenceName;
    private double posY;
    private double posZ;
    private int areaId;
    private String createTime;
    private String authorName;
    private String fenceColor;
    private AreaPathBean areaPath;
    private int fenceId;

    public String getDealContent() {
        return dealContent;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
    }

    public int getAreaShape() {
        return areaShape;
    }

    public void setAreaShape(int areaShape) {
        this.areaShape = areaShape;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAreaVersion() {
        return areaVersion;
    }

    public void setAreaVersion(int areaVersion) {
        this.areaVersion = areaVersion;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDealStatusName() {
        return dealStatusName;
    }

    public void setDealStatusName(String dealStatusName) {
        this.dealStatusName = dealStatusName;
    }

    public String getAreaZLoc() {
        return areaZLoc;
    }

    public void setAreaZLoc(String areaZLoc) {
        this.areaZLoc = areaZLoc;
    }

    public String getAuthorType() {
        return authorType;
    }

    public void setAuthorType(String authorType) {
        this.authorType = authorType;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public String getFenceName() {
        return fenceName;
    }

    public void setFenceName(String fenceName) {
        this.fenceName = fenceName;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getFenceColor() {
        return fenceColor;
    }

    public void setFenceColor(String fenceColor) {
        this.fenceColor = fenceColor;
    }

    public AreaPathBean getAreaPath() {
        return areaPath;
    }

    public void setAreaPath(AreaPathBean areaPath) {
        this.areaPath = areaPath;
    }

    public int getFenceId() {
        return fenceId;
    }

    public void setFenceId(int fenceId) {
        this.fenceId = fenceId;
    }

    public static class AreaPathBean implements Serializable {
        /**
         * closed : true
         * type : path
         * value : ((-28.69,-38.52),(-28.69,7.67),(28.0,7.67),(28.0,-38.52),(-28.69,-38.52))
         * open : false
         * points : [{"x":-28.69,"y":-38.52,"type":"point","value":"(-28.69,-38.52)"},{"x":-28.69,"y":7.67,"type":"point","value":"(-28.69,7.67)"},{"x":28,"y":7.67,"type":"point","value":"(28.0,7.67)"},{"x":28,"y":-38.52,"type":"point","value":"(28.0,-38.52)"},{"x":-28.69,"y":-38.52,"type":"point","value":"(-28.69,-38.52)"}]
         */

        private boolean closed;
        private String type;
        private String value;
        private boolean open;
        private List<PointsBean> points;

        public boolean isClosed() {
            return closed;
        }

        public void setClosed(boolean closed) {
            this.closed = closed;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public List<PointsBean> getPoints() {
            return points;
        }

        public void setPoints(List<PointsBean> points) {
            this.points = points;
        }

        public static class PointsBean implements Serializable {
            /**
             * x : -28.69
             * y : -38.52
             * type : point
             * value : (-28.69,-38.52)
             */

            private double x;
            private double y;
            private String type;
            private String value;

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}

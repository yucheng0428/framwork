package com.qyai.baidumap.postion.bean;

import com.lib.common.baseUtils.baseModle.BaseResult;

public class LoctionBean  extends BaseResult {


    /**
     * msg : 查询成功
     * code : 000000
     * data : {"page":null,"limit":null,"sort":null,"order":null,"keyQuery":null,"id":null,"deviceId":"359202540567140","deviceType":"1","authorId":null,"watchType":null,"authorType":null,"authorName":null,"locX":114.595768,"locY":35.0543726,"locZ":0,"direction":null,"speed":0,"createTime":null,"createUser":null,"areaId":null,"onlineStatus":1,"admin":false,"adminDo":null,"sceneId":null,"buildId":null,"floorId":null,"person":null,"car":null,"classType":null,"carNum":null,"time":1617952758979,"cameras":null,"lineLimit":null}
     */

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * page : null
         * limit : null
         * sort : null
         * order : null
         * keyQuery : null
         * id : null
         * deviceId : 359202540567140
         * deviceType : 1
         * authorId : null
         * watchType : null
         * authorType : null
         * authorName : null
         * locX : 114.595768
         * locY : 35.0543726
         * locZ : 0
         * direction : null
         * speed : 0
         * createTime : null
         * createUser : null
         * areaId : null
         * onlineStatus : 1
         * admin : false
         * adminDo : null
         * sceneId : null
         * buildId : null
         * floorId : null
         * person : null
         * car : null
         * classType : null
         * carNum : null
         * time : 1617952758979
         * cameras : null
         * lineLimit : null
         */

        private Object page;
        private Object limit;
        private Object sort;
        private Object order;
        private Object keyQuery;
        private Object id;
        private String deviceId;
        private String deviceType;
        private Object authorId;
        private Object watchType;
        private Object authorType;
        private Object authorName;
        private double locX;
        private double locY;
        private int locZ;
        private Object direction;
        private int speed;
        private Object createTime;
        private Object createUser;
        private Object areaId;
        private int onlineStatus;
        private boolean admin;
        private Object adminDo;
        private Object sceneId;
        private Object buildId;
        private Object floorId;
        private Object person;
        private Object car;
        private Object classType;
        private Object carNum;
        private long time;
        private Object cameras;
        private Object lineLimit;
        private String positionAddress;

        public String getPositionAddress() {
            return positionAddress;
        }

        public void setPositionAddress(String positionAddress) {
            this.positionAddress = positionAddress;
        }

        public Object getPage() {
            return page;
        }

        public void setPage(Object page) {
            this.page = page;
        }

        public Object getLimit() {
            return limit;
        }

        public void setLimit(Object limit) {
            this.limit = limit;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public Object getOrder() {
            return order;
        }

        public void setOrder(Object order) {
            this.order = order;
        }

        public Object getKeyQuery() {
            return keyQuery;
        }

        public void setKeyQuery(Object keyQuery) {
            this.keyQuery = keyQuery;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public Object getAuthorId() {
            return authorId;
        }

        public void setAuthorId(Object authorId) {
            this.authorId = authorId;
        }

        public Object getWatchType() {
            return watchType;
        }

        public void setWatchType(Object watchType) {
            this.watchType = watchType;
        }

        public Object getAuthorType() {
            return authorType;
        }

        public void setAuthorType(Object authorType) {
            this.authorType = authorType;
        }

        public Object getAuthorName() {
            return authorName;
        }

        public void setAuthorName(Object authorName) {
            this.authorName = authorName;
        }

        public double getLocX() {
            return locX;
        }

        public void setLocX(double locX) {
            this.locX = locX;
        }

        public double getLocY() {
            return locY;
        }

        public void setLocY(double locY) {
            this.locY = locY;
        }

        public int getLocZ() {
            return locZ;
        }

        public void setLocZ(int locZ) {
            this.locZ = locZ;
        }

        public Object getDirection() {
            return direction;
        }

        public void setDirection(Object direction) {
            this.direction = direction;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getCreateUser() {
            return createUser;
        }

        public void setCreateUser(Object createUser) {
            this.createUser = createUser;
        }

        public Object getAreaId() {
            return areaId;
        }

        public void setAreaId(Object areaId) {
            this.areaId = areaId;
        }

        public int getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(int onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public Object getAdminDo() {
            return adminDo;
        }

        public void setAdminDo(Object adminDo) {
            this.adminDo = adminDo;
        }

        public Object getSceneId() {
            return sceneId;
        }

        public void setSceneId(Object sceneId) {
            this.sceneId = sceneId;
        }

        public Object getBuildId() {
            return buildId;
        }

        public void setBuildId(Object buildId) {
            this.buildId = buildId;
        }

        public Object getFloorId() {
            return floorId;
        }

        public void setFloorId(Object floorId) {
            this.floorId = floorId;
        }

        public Object getPerson() {
            return person;
        }

        public void setPerson(Object person) {
            this.person = person;
        }

        public Object getCar() {
            return car;
        }

        public void setCar(Object car) {
            this.car = car;
        }

        public Object getClassType() {
            return classType;
        }

        public void setClassType(Object classType) {
            this.classType = classType;
        }

        public Object getCarNum() {
            return carNum;
        }

        public void setCarNum(Object carNum) {
            this.carNum = carNum;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public Object getCameras() {
            return cameras;
        }

        public void setCameras(Object cameras) {
            this.cameras = cameras;
        }

        public Object getLineLimit() {
            return lineLimit;
        }

        public void setLineLimit(Object lineLimit) {
            this.lineLimit = lineLimit;
        }
    }
}

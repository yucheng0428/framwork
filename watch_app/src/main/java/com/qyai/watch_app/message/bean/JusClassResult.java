package com.qyai.watch_app.message.bean;

import com.lib.common.baseUtils.baseModle.BaseResult;

import java.io.Serializable;
import java.util.List;

public class JusClassResult extends BaseResult implements Serializable{


    /**
     * msg : 查询成功!
     * code : 000000
     * data : [{"id":12,"name":"医疗","operateId":null,"operateTime":1611654590000,"type":"1","userId":null,"modelNum":null,"appCode":null,"mapId":null,"x":null,"y":null,"z":null,"userName":"","userPhone":null,"pid":0},{"id":1,"name":"镇江石化","operateId":"1","operateTime":1606242389179,"type":"1","userId":"6","modelNum":null,"appCode":null,"mapId":null,"x":null,"y":null,"z":null,"userName":"sdfsd\\","userPhone":null,"pid":0},{"id":8,"name":"仪征化纤","operateId":"1","operateTime":1609315906000,"type":"1","userId":null,"modelNum":null,"appCode":null,"mapId":null,"x":null,"y":null,"z":null,"userName":"","userPhone":null,"pid":0}]
     */

    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 12
         * name : 医疗
         * operateId : null
         * operateTime : 1611654590000
         * type : 1
         * userId : null
         * modelNum : null
         * appCode : null
         * mapId : null
         * x : null
         * y : null
         * z : null
         * userName :
         * userPhone : null
         * pid : 0
         */
        private String id;
        private String name;
        private Object operateId;
        private long operateTime;
        private String type;
        private Object userId;
        private Object modelNum;
        private Object appCode;
        private Object mapId;
        private Object x;
        private Object y;
        private Object z;
        private String userName;
        private Object userPhone;
        private String pid;
        private List<DataBean> childList;
        public boolean openList=false;//是否展开
        public boolean checkItem=false;//是否选中
        public int leve=0;



        public List<DataBean> getChildList() {
            return childList;
        }

        public void setChildList(List<DataBean> childList) {
            this.childList = childList;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getOperateId() {
            return operateId;
        }

        public void setOperateId(Object operateId) {
            this.operateId = operateId;
        }

        public long getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(long operateTime) {
            this.operateTime = operateTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getModelNum() {
            return modelNum;
        }

        public void setModelNum(Object modelNum) {
            this.modelNum = modelNum;
        }

        public Object getAppCode() {
            return appCode;
        }

        public void setAppCode(Object appCode) {
            this.appCode = appCode;
        }

        public Object getMapId() {
            return mapId;
        }

        public void setMapId(Object mapId) {
            this.mapId = mapId;
        }

        public Object getX() {
            return x;
        }

        public void setX(Object x) {
            this.x = x;
        }

        public Object getY() {
            return y;
        }

        public void setY(Object y) {
            this.y = y;
        }

        public Object getZ() {
            return z;
        }

        public void setZ(Object z) {
            this.z = z;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(Object userPhone) {
            this.userPhone = userPhone;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", operateId=" + operateId +
                    ", operateTime=" + operateTime +
                    ", type='" + type + '\'' +
                    ", userId=" + userId +
                    ", modelNum=" + modelNum +
                    ", appCode=" + appCode +
                    ", mapId=" + mapId +
                    ", x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", userName='" + userName + '\'' +
                    ", userPhone=" + userPhone +
                    ", pid='" + pid + '\'' +
                    ", childList=" + childList +
                    ", openList=" + openList +
                    ", checkItem=" + checkItem +
                    ", leve=" + leve +
                    '}';
        }
    }


}

package com.qyai.main.bracelet.bean;

import java.io.Serializable;
import java.util.List;

public class SyncHeartBean implements Serializable {

    /**
     * code : 0
     * data : [{"heartValue":68,"heartStartTime":1603442227000},{"heartValue":80,"heartStartTime":1603442285000}]
     * dataType : 1286
     */

    private int code;
    private int dataType;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * heartValue : 68
         * heartStartTime : 1603442227000
         */

        private int heartValue;
        private long heartStartTime;

        public int getHeartValue() {
            return heartValue;
        }

        public void setHeartValue(int heartValue) {
            this.heartValue = heartValue;
        }

        public long getHeartStartTime() {
            return heartStartTime;
        }

        public void setHeartStartTime(long heartStartTime) {
            this.heartStartTime = heartStartTime;
        }
    }
}

package com.qyai.bracelet_lib.bean;

import java.io.Serializable;
import java.util.List;

public class SyncBloodBean implements Serializable {

    /**
     * code : 0
     * data : [{"bloodStartTime":1603442227000,"bloodDBP":117,"bloodSBP":76},{"bloodStartTime":1603442285000,"bloodDBP":120,"bloodSBP":77}]
     * dataType : 1288
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
         * bloodStartTime : 1603442227000
         * bloodDBP : 117
         * bloodSBP : 76
         */

        private long bloodStartTime;
        private int bloodDBP;
        private int bloodSBP;

        public long getBloodStartTime() {
            return bloodStartTime;
        }

        public void setBloodStartTime(long bloodStartTime) {
            this.bloodStartTime = bloodStartTime;
        }

        public int getBloodDBP() {
            return bloodDBP;
        }

        public void setBloodDBP(int bloodDBP) {
            this.bloodDBP = bloodDBP;
        }

        public int getBloodSBP() {
            return bloodSBP;
        }

        public void setBloodSBP(int bloodSBP) {
            this.bloodSBP = bloodSBP;
        }
    }
}

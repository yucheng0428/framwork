package com.qyai.bracelet_lib.bean;

import java.io.Serializable;
import java.util.List;

public class SyncHistiryBean implements Serializable {


    /**
     * code : 0
     * data : [{"heartValue":0,"hrvValue":23,"cvrrValue":6,"OOValue":97,"stepValue":0,"DBPValue":115,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422246000,"SBPValue":75,"respiratoryRateValue":16},{"heartValue":0,"hrvValue":34,"cvrrValue":5,"OOValue":93,"stepValue":0,"DBPValue":111,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422311000,"SBPValue":72,"respiratoryRateValue":13},{"heartValue":0,"hrvValue":35,"cvrrValue":2,"OOValue":95,"stepValue":0,"DBPValue":113,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422367000,"SBPValue":72,"respiratoryRateValue":14},{"heartValue":0,"hrvValue":31,"cvrrValue":2,"OOValue":95,"stepValue":0,"DBPValue":113,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422427000,"SBPValue":72,"respiratoryRateValue":14},{"heartValue":0,"hrvValue":19,"cvrrValue":2,"OOValue":97,"stepValue":0,"DBPValue":115,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422487000,"SBPValue":74,"respiratoryRateValue":15},{"heartValue":0,"hrvValue":18,"cvrrValue":6,"OOValue":98,"stepValue":0,"DBPValue":115,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422548000,"SBPValue":74,"respiratoryRateValue":16},{"heartValue":0,"hrvValue":23,"cvrrValue":4,"OOValue":97,"stepValue":0,"DBPValue":115,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422606000,"SBPValue":73,"respiratoryRateValue":16},{"heartValue":0,"hrvValue":33,"cvrrValue":2,"OOValue":97,"stepValue":0,"DBPValue":113,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422669000,"SBPValue":74,"respiratoryRateValue":15},{"heartValue":0,"hrvValue":26,"cvrrValue":2,"OOValue":93,"stepValue":0,"DBPValue":111,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422727000,"SBPValue":71,"respiratoryRateValue":13},{"heartValue":0,"hrvValue":24,"cvrrValue":3,"OOValue":97,"stepValue":0,"DBPValue":112,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422786000,"SBPValue":74,"respiratoryRateValue":15},{"heartValue":0,"hrvValue":20,"cvrrValue":3,"OOValue":98,"stepValue":0,"DBPValue":116,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422846000,"SBPValue":76,"respiratoryRateValue":19},{"heartValue":0,"hrvValue":23,"cvrrValue":4,"OOValue":96,"stepValue":0,"DBPValue":113,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422909000,"SBPValue":73,"respiratoryRateValue":15},{"heartValue":0,"hrvValue":22,"cvrrValue":3,"OOValue":81,"stepValue":0,"DBPValue":106,"tempIntValue":36,"tempFloatValue":1,"startTime":1603422966000,"SBPValue":69,"respiratoryRateValue":10}]
     * dataType : 1289
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
         * heartValue : 0
         * hrvValue : 23
         * cvrrValue : 6
         * OOValue : 97
         * stepValue : 0
         * DBPValue : 115
         * tempIntValue : 36
         * tempFloatValue : 1
         * startTime : 1603422246000
         * SBPValue : 75
         * respiratoryRateValue : 16
         */

        private int heartValue;
        private int hrvValue;
        private int cvrrValue;
        private int OOValue;
        private int stepValue;
        private int DBPValue;
        private int tempIntValue;
        private int tempFloatValue;
        private long startTime;
        private int SBPValue;
        private int respiratoryRateValue;

        public int getHeartValue() {
            return heartValue;
        }

        public void setHeartValue(int heartValue) {
            this.heartValue = heartValue;
        }

        public int getHrvValue() {
            return hrvValue;
        }

        public void setHrvValue(int hrvValue) {
            this.hrvValue = hrvValue;
        }

        public int getCvrrValue() {
            return cvrrValue;
        }

        public void setCvrrValue(int cvrrValue) {
            this.cvrrValue = cvrrValue;
        }

        public int getOOValue() {
            return OOValue;
        }

        public void setOOValue(int OOValue) {
            this.OOValue = OOValue;
        }

        public int getStepValue() {
            return stepValue;
        }

        public void setStepValue(int stepValue) {
            this.stepValue = stepValue;
        }

        public int getDBPValue() {
            return DBPValue;
        }

        public void setDBPValue(int DBPValue) {
            this.DBPValue = DBPValue;
        }

        public int getTempIntValue() {
            return tempIntValue;
        }

        public void setTempIntValue(int tempIntValue) {
            this.tempIntValue = tempIntValue;
        }

        public int getTempFloatValue() {
            return tempFloatValue;
        }

        public void setTempFloatValue(int tempFloatValue) {
            this.tempFloatValue = tempFloatValue;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public int getSBPValue() {
            return SBPValue;
        }

        public void setSBPValue(int SBPValue) {
            this.SBPValue = SBPValue;
        }

        public int getRespiratoryRateValue() {
            return respiratoryRateValue;
        }

        public void setRespiratoryRateValue(int respiratoryRateValue) {
            this.respiratoryRateValue = respiratoryRateValue;
        }
    }
}

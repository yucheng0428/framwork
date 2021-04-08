package com.qyai.watch_app.message.bean;

import com.lib.common.baseUtils.baseModle.BaseResult;

import java.io.Serializable;

public class AlarmCountResult extends BaseResult  {


    /**
     * msg : 操作成功
     * code : 000000
     * data : {"personCount":15,"personOnLineCount":0,"alarmCount":23,"pendingAlarmCount":7,"toDayAlarmCount":0,"toDayPendingAlarmCount":0}
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
         * personCount : 15
         * personOnLineCount : 0
         * alarmCount : 23
         * pendingAlarmCount : 7
         * toDayAlarmCount : 0
         * toDayPendingAlarmCount : 0
         */

        private int personCount;
        private int personOnLineCount;
        private int alarmCount;
        private int pendingAlarmCount;
        private int toDayAlarmCount;
        private int toDayPendingAlarmCount;

        public int getPersonCount() {
            return personCount;
        }

        public void setPersonCount(int personCount) {
            this.personCount = personCount;
        }

        public int getPersonOnLineCount() {
            return personOnLineCount;
        }

        public void setPersonOnLineCount(int personOnLineCount) {
            this.personOnLineCount = personOnLineCount;
        }

        public int getAlarmCount() {
            return alarmCount;
        }

        public void setAlarmCount(int alarmCount) {
            this.alarmCount = alarmCount;
        }

        public int getPendingAlarmCount() {
            return pendingAlarmCount;
        }

        public void setPendingAlarmCount(int pendingAlarmCount) {
            this.pendingAlarmCount = pendingAlarmCount;
        }

        public int getToDayAlarmCount() {
            return toDayAlarmCount;
        }

        public void setToDayAlarmCount(int toDayAlarmCount) {
            this.toDayAlarmCount = toDayAlarmCount;
        }

        public int getToDayPendingAlarmCount() {
            return toDayPendingAlarmCount;
        }

        public void setToDayPendingAlarmCount(int toDayPendingAlarmCount) {
            this.toDayPendingAlarmCount = toDayPendingAlarmCount;
        }
    }
}

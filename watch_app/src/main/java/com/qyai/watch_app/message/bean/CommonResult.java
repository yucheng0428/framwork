package com.qyai.watch_app.message.bean;

import java.io.Serializable;
import java.util.List;

public class CommonResult implements Serializable {


    /**
     * msg : 操作成功
     * code : 000000
     * data : [{"id":53,"content":"45654645","date":"2021-01-28 15:30:29"},{"id":54,"content":"明航","date":"2021-01-28 15:30:29"},{"id":55,"content":"明航2","date":"2021-01-28 15:30:29"}]
     */

    private String msg;
    private String code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 53
         * content : 45654645
         * date : 2021-01-28 15:30:29
         */

        private int id;
        private String content;
        private String date;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}

package com.qyai.watch_app.message.bean;

import java.io.Serializable;
import java.util.List;

public class AlarmResult implements Serializable {


    /**
     * msg : 操作成功
     * code : 000000
     * data : [{"id":"51db3955c24d40cb9778b69e9b4c1c5d","authorId":"1851","authorName":"0x073b","authorType":"4","areaId":null,"areaVersion":null,"deviceId":"1851","posX":17.72,"posY":-13.12,"posZ":2.58,"type":14,"dealStatus":0,"content":"基站0x073b掉线报警!","dealContent":null,"source":1,"createTime":"2021-02-04 11:17:45","dealTime":null,"dealUser":null,"dealOpinion":null,"fenceId":null,"fenceVersion":null,"fenceName":null,"areaShape":null,"areaPath":null,"areaCircle":null,"areaZLoc":null,"fenceColor":null,"areaName":null,"healthType":null,"address":null,"smcContent":null,"dealUserName":null,"dealStatusName":"未处理","dealOpinionName":null,"typeName":"基站离线"}]
     */

    private String msg;
    private String code;
    private List<AlarmPushBean> data;

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

    public List<AlarmPushBean> getData() {
        return data;
    }

    public void setData(List<AlarmPushBean> data) {
        this.data = data;
    }


}

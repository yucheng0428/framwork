package com.lib.common.baseUtils.baseModle;

import java.io.Serializable;

/**
 * Created by yucheng on 2017-06-07.
 */

public class BaseResult implements Serializable {

    /**
     * msg : 5
     * code : 502
     */

    private String msg;
    private String code;


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
}

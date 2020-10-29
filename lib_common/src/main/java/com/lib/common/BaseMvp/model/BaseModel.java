package com.lib.common.BaseMvp.model;


import com.lib.common.netHttp.HttpServiec;

/**
 * 作者：yucheng 2018/9/18 0018
 * 类描述：
 */
public class BaseModel {
    public HttpServiec httpServiec = HttpServiec.getInstance();

    public void cancel(int id) {
        httpServiec.cancel(id);
    }

}

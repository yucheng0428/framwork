package com.qyai.beaconlib.login;

import com.lib.common.BaseMvp.view.BaseView;
import com.qyai.beaconlib.login.bean.UserEvent;

public interface LoginView extends BaseView {

    /**
     * 获取用户名
     * @return
     */
    String getUserName();

    /**
     * 获取密码
     * @return
     */
    String getPassWord();

    /**
     * 登录成功后下一步
     */
    void loginNext(UserEvent event);

}

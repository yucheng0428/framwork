package com.qyai.main.login;

import com.lib.common.BaseMvp.view.BaseView;
import com.qyai.main.login.bean.UserEvent;

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

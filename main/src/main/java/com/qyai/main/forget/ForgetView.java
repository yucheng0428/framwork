package com.qyai.main.forget;

import com.lib.common.BaseMvp.view.BaseView;

public interface ForgetView extends BaseView {

    String userName();
    String passWord();
    String sendCode();
    void nextForget(String s);
}

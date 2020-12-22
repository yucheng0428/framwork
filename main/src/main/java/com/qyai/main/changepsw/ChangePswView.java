package com.qyai.main.changepsw;

import com.lib.common.BaseMvp.view.BaseView;

public interface ChangePswView extends BaseView {

    String userName();
    String passWord();
    String sendCode();
    void nextForget(String s);
}

package com.qyai.main.register;

import com.lib.common.BaseMvp.view.BaseView;

public interface RegiserView extends BaseView {
    String userName();
    String passWord();
    String sendCode();
    void nextRegiser(String s);
}

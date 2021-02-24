package com.qyai.main.changepsw;

import com.lib.common.BaseMvp.view.BaseView;
import com.lib.common.baseUtils.baseModle.BaseResult;

public interface ChangePswView extends BaseView {

    String oldPsw();
    String newPsw();
    String newAgainPsw();
    String sendCode();
    void nextForget(BaseResult s);
}

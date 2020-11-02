package com.qyai.beaconlib.html;


import com.lib.common.BaseMvp.view.BaseView;

import java.util.Map;

public interface HtmlView extends BaseView {
    /**
     * 获取蓝牙信标信息
     */
    void getBlueToothInfo(Map<Object, Object> map);

    void showInfoMessage(BeaCcontBean bean);
}

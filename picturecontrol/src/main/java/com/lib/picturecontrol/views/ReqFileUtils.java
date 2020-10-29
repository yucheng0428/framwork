package com.lib.picturecontrol.views;

import android.content.Context;

import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.IntentKey;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.lib.picturecontrol.bean.ApprovalAttachBean;

/**
 * 上传附件工具类
 * 仅处理图片上传
 */
public class ReqFileUtils {

    private OnHttpCallBack<String> callBack;
    private Context mContext;



    public ReqFileUtils(Context context) {
        this.mContext = context;
    }

    public void setOnHttpCallBack(OnHttpCallBack<String> onHttpCallBack) {
        this.callBack = onHttpCallBack;
    }

    /**
     * 上传图片请求
     * @param bean
     */
    public void uploadFile(ApprovalAttachBean bean) {
        HttpServiec.getInstance().postFlowableData(IntentKey.REQ_UPLAOD, Constants.fileUploadUrl, bean,callBack , String.class);

    }

    /**
     * 删除图片请求
     *
     * @param bean
     */
    public void delectFile(ApprovalAttachBean bean) {
        // TODO: 2020/10/28  这里需要修改一个删除的接口 Constants.fileUploadUrl
        HttpServiec.getInstance().postFlowableData(IntentKey.REQ_DELECT, Constants.fileUploadUrl,bean, callBack, String.class);

    }
}

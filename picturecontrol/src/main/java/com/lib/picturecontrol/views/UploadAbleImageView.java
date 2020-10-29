package com.lib.picturecontrol.views;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.netHttp.OnHttpCallBack;
import com.lib.common.netHttp.UploadProgressListener;
import com.lib.picturecontrol.R;
import com.lib.picturecontrol.bean.ApprovalAttachBean;

/**
 * 图片上传控件
 * Created by GYH on 2017/6/8.
 */

public class UploadAbleImageView extends LinearLayout implements UploadProgressListener, View.OnClickListener, OnHttpCallBack<String> {
    protected static LayoutParams LAYOUT_PARAMS = new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);

    private Context mContext;
    private LayoutInflater mInflater;
    private FrameLayout mainView;
    private FrameLayout layout_loading;
    private RoundProgressBar progressBar;
    private ImageView imageView;
    private ImageDeleteView del;
    private MaskView maskView;
    private int mPosition;
    private ApprovalAttachBean mRequest;
    private ReqFileUtils fileUtils;
    private MediaDelectInterface delectInterface;
    private MediaUploadInterface uploadInterface;


    public UploadAbleImageView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public UploadAbleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public UploadAbleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public UploadAbleImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        init();
    }

    public void setDelectInterface(MediaDelectInterface delectInterface) {
        this.delectInterface = delectInterface;
    }

    public void setUploadInterface(MediaUploadInterface uploadInterface) {
        this.uploadInterface = uploadInterface;
    }

    public void setAttachBean(ApprovalAttachBean attachBean) {
        this.mRequest = attachBean;
    }

    public void startUpload(ApprovalAttachBean req, int position) {
        this.mPosition = position;
        setAttachBean(req);
//        fileUtils.uploadFile(mRequest);
    }

    private void init() {
        this.setOrientation(LinearLayout.VERTICAL);
        fileUtils = new ReqFileUtils(mContext);
        mInflater = LayoutInflater.from(mContext);
        mainView = (FrameLayout) mInflater.inflate(
                R.layout.upload_image_view, null);
        layout_loading = (FrameLayout) mainView.findViewById(R.id.layout_loading);
        progressBar = (RoundProgressBar) mainView.findViewById(R.id.rp_progres);
        imageView = (ImageView) mainView.findViewById(R.id.iv_pic);
        del = (ImageDeleteView) mainView.findViewById(R.id.iv_del);
        maskView = (MaskView) mainView.findViewById(R.id.mask_view);

        progressBar.setOnClickListener(this);
        del.setOnClickListener(this);

        this.addView(mainView, LAYOUT_PARAMS);
    }

    /**
     * 显示加载进度
     */
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        maskView.setVisibility(View.VISIBLE);
    }

    /**
     * 加载结束  隐藏加载进度
     */
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        maskView.startAnimation(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showDelBtn(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        progressBar.setProgress(0);
        realRound = false;
    }

    /**
     * 显示删除按钮
     *
     * @param canDelt 是否能删除
     */
    public void showDelBtn(boolean canDelt) {
        boolean isAnimatior = del.getVisibility() == View.VISIBLE;
        del.setVisibility(canDelt ? View.VISIBLE : View.INVISIBLE, !isAnimatior);
    }


    /**
     * 获取内部imageView控件
     *
     * @return
     */
    public ImageView getImageModel() {
        return imageView;
    }

    /**
     * 获取内部RoundProgressBar控件
     *
     * @return
     */
    public RoundProgressBar getProgressBar() {
        return progressBar;
    }


    boolean realRound = false;

    @Override
    public void onProgress(long currentBytesCount, long totalBytesCount) {
        float per = (float) (currentBytesCount) / (float) (totalBytesCount);
        if (realRound)
            progressBar.setProgress((int) (per * 100) == 100 ? 99 : (int) (per * 100));
        if ((int) (per * 100) == 100 && !realRound)
            realRound = true;
    }

    @Override
    public void onSuccessful(int id, String s) {
        switch (id) {
            case IntentKey.REQ_UPLAOD:
                progressBar.setProgress(100);
                hideLoading();
                uploadInterface.uploadListener(mPosition, s);
                break;
            case IntentKey.REQ_DELECT:
                delectInterface.fileDelectListener(mPosition, s);
                break;
        }
    }

    @Override
    public void onFaild(int id, String s, String err) {
        switch (id) {
            case IntentKey.REQ_UPLAOD:
                progressBar.progressFailed();
                UIHelper.ToastMessage(mContext, err);
                break;
            case IntentKey.REQ_DELECT:
                UIHelper.ToastMessage(mContext, err);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_del) {
            if (!mRequest.hasUploaded) {
                fileUtils.delectFile(mRequest);
            }else {
                delectInterface.fileDelectListener(mPosition, "");
            }
        } else if (id == R.id.rp_progres) {
            if (progressBar.getProgressStatus() == RoundProgressBar.PROGRESS_FAILED) {
                startUpload(mRequest, mPosition);
                progressBar.setProgressStatus(RoundProgressBar.PROGRESS_UPLOADING);
            }
        }
    }


}

package com.lib.picturecontrol.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.Utils;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.lib.picturecontrol.R;
import com.lib.picturecontrol.adapter.ReclecyAdapter;
import com.lib.picturecontrol.bean.ApprovalAttachBean;
import com.lib.picturecontrol.cameralibary.LookPictureAct;
import com.lib.picturecontrol.tools.PickImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AttachView extends LinearLayout
        implements
        PickImageUtils.InotifyBitmap,
        ReclecyAdapter.DelListener {

    protected static LayoutParams LAYOUT_PARAMS = new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);

    private Context mContext;
    private LayoutInflater mInflater;
    /**
     * 附件布局
     */
    private LinearLayout attachLayout;
    private LinearLayout attachTopLayout;
    /**
     * 标题
     */
    private TextView title;
    /**
     * 附件数量
     */
    private TextView num;
    /**
     * 标题布局
     */
    private LinearLayout titleLayout;
    /**
     * 标题分割线
     */
    private View viewLine;
    /**
     * 图片附件
     */
    private RecyclerView recyclerView;
    /**
     * 附件最大数量
     */
    private int maxNum = 9;
    /**
     * 附件工具类
     */
    private PickImageUtils utils;
    /**
     * 附件适配器
     */
    private ReclecyAdapter gAdapter;
    /**
     * 能否编辑
     */
    private boolean editAble = true;
    /**
     * 拍照图片处理监听
     */
    private AttachCameraListener acListener;
    private AttachClickPosition aClickPosition;
    private int aNum;

    private int backgroundColor = -1;

    public AttachView(Context context, AttributeSet attrs, int defStyleAttr,
                      int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        TypedArray mTypedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AttachView);
        backgroundColor = mTypedArray.getColor(R.styleable.AttachView_backgroundColor, Color.WHITE);
        initUI();
    }

    public AttachView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray mTypedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AttachView);
        backgroundColor = mTypedArray.getColor(R.styleable.AttachView_backgroundColor, Color.WHITE);
        initUI();
    }

    public AttachView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray mTypedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AttachView);
        backgroundColor = mTypedArray.getColor(R.styleable.AttachView_backgroundColor, Color.WHITE);
        mTypedArray.recycle();
        initUI();
    }

    public AttachView(Context context) {
        super(context);
        this.mContext = context;
        initUI();
    }

    private void initUI() {
        this.setOrientation(LinearLayout.VERTICAL);
        mInflater = LayoutInflater.from(mContext);
        attachLayout = (LinearLayout) mInflater.inflate(R.layout.approval_attach_add_layout, null);
        title = (TextView) attachLayout.findViewById(R.id.tv_add_attch);
        num = (TextView) attachLayout.findViewById(R.id.attach_num);
        titleLayout = (LinearLayout) attachLayout.findViewById(R.id.attacn_title);
        viewLine = attachLayout.findViewById(R.id.view_line);
        attachTopLayout = (LinearLayout) attachLayout.findViewById(R.id.attach_layout);
        recyclerView = attachLayout.findViewById(R.id.recyclerView);
        utils = new PickImageUtils(mContext, this);
        utils.setIsSingle(false);
        utils.setMaxPics(maxNum);
        gAdapter = new ReclecyAdapter(mContext, editAble);
        gAdapter.setDelListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(gAdapter);
        gAdapter.setRecItemClick(new RecyclerItemCallback<ApprovalAttachBean, ReclecyAdapter.ViewHodler>() {
            @Override
            public void onItemClick(int position, ApprovalAttachBean model, int tag, ReclecyAdapter.ViewHodler holder) {
                super.onItemClick(position, model, tag, holder);
                ApprovalAttachBean item = gAdapter.getDataSource().get(position);
                if (item.imgRes != 0) {
                    if (aClickPosition != null)
                        aClickPosition.noticClickPosition();
                    if (Utils.hasPermission(mContext, Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {

                        utils.showPickDialog();
                    } else {
                        Utils.grantedPermissions(mContext, new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.ACCESS_COARSE_LOCATION},
                                "获取拍照、定位获取本地位置权限");
                    }

                } else {
                    ArrayList<String> pathList = new ArrayList<String>();
                    for (ApprovalAttachBean image : gAdapter.getDataSource()) {
                        if (image.imgRes == 0)
                            pathList.add(image.url);
                    }
                    Intent intent = new Intent(mContext, LookPictureAct.class);
                    intent.putStringArrayListExtra(IntentKey.CHECK_FILE_PATH, pathList);
                    mContext.startActivity(intent);
                }
            }
        });
        if (backgroundColor != -1) {
            titleLayout.setBackgroundColor(backgroundColor);
            recyclerView.setBackgroundColor(backgroundColor);
        }
        this.addView(attachLayout, LAYOUT_PARAMS);
    }


    public void setImageList(List<ApprovalAttachBean> imageList){
        if(gAdapter!=null&&imageList!=null&&imageList.size()>0){
            gAdapter.setData(imageList);
            gAdapter.notifyDataSetChanged();
        }
        aNum = (gAdapter.getDataSource().size() - 1);
        num.setText(String.format("(%1$d/%2$d)", aNum, maxNum));
    }
    /**
     * 设置是否能够编辑
     *
     * @param editAble
     */
    public void setEditAble(boolean editAble) {
        this.editAble = editAble;
        if (editAble)
            title.setText("上传附件");
        else
            title.setText("附件");
        gAdapter.isEditAble(editAble);
    }

    /**
     * 设置图片单选
     *
     * @param isSingle
     */
    public void setIsSingle(boolean isSingle) {
        utils.setIsSingle(isSingle);
        setMaxNum(1);
    }


    /**
     * 拍照水印上显示项目名称
     */
    public void setProjName(String projName) {
        utils.setProjName(projName);
    }

    /**
     * 设置标题
     */

    public void setTitle(String titleStr) {
        title.setText(titleStr == null ? "" : titleStr);
    }

    /**
     * 设置附件最大数量
     *
     * @param max
     */
    public void setMaxNum(int max) {
        maxNum = max;
        num.setText(String.format("(0/%d)", maxNum));
    }

    /**
     * 隐藏标题
     */
    public void hidTitle() {
        titleLayout.setVisibility(View.GONE);
    }

    /**
     * 设置标题颜色
     */
    public void setTitleColor(int colorRes) {
        try {
            title.setTextColor(getContext().getResources().getColor(colorRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调整标题位置（左边距）
     *
     * @Para padding 离左边的位置(dp)
     */
    public void setTitleLeftPadding(int padding) {
        title.setPadding(Utils.dip2px(getContext(), padding), 0, 0, 0);
    }


    /**
     * 隐藏右边的0/9
     */
    public void hideRightText() {
        num.setVisibility(View.GONE);
    }

    /**
     * 隐藏全部标题（标题及分割线）
     */
    public void hidAllTitle() {
        titleLayout.setVisibility(View.GONE);
        viewLine.setVisibility(View.GONE);
    }

    /**
     * 获取头部布局
     *
     * @return
     */
    public LinearLayout getTitleLayout() {
        return titleLayout;
    }

    /**
     * 设置附件模块背景
     *
     * @param attachBg
     */
    public void setAttachBg(int attachBg) {
        LayoutParams lp = (LayoutParams) titleLayout.getLayoutParams();
        lp.setMargins(0, 10, 0, 0);
        titleLayout.setLayoutParams(lp);
        if (attachBg > -1) {
            attachTopLayout.setBackground(getResources().getDrawable(attachBg));
        }
    }

    /**
     * 拍照图片处理监听
     */
    public void setAcListener(AttachCameraListener acListener) {
        this.acListener = acListener;
    }

    public void setAttachClickPosition(AttachClickPosition aClickPosition) {
        this.aClickPosition = aClickPosition;
    }

    /**
     * 获取已选图片的路径集合
     *
     * @return
     */
    public List<String> getPics() {
        List<String> result = new ArrayList<>();
        if (gAdapter.getDataSource() == null || gAdapter.getDataSource().size() < 1)
            return result;
        else {
            for (ApprovalAttachBean bean : gAdapter.getDataSource()) {
                if (bean.imgRes != R.mipmap.add_attach_icon && bean.url.startsWith("/"))
                    result.add(bean.url);
            }
            return result;
        }
    }



    /**
     * 获取图片数量（以上传和未上传）
     *
     * @return
     */
    public int getPicNum() {
        if (gAdapter.getDataSource() == null)
            return 0;
        else {
            if (gAdapter.getDataSource().contains(ReclecyAdapter.ADDPIC))
                return gAdapter.getDataSource().size() - 1;
            return gAdapter.getDataSource().size();
        }
    }



    /**
     * 附件选择通知，在onactivityResult方法中调用（必须调用）
     *
     * @param request
     * @param response
     * @param intent
     */
    public void notifyAttachResult(int request, int response, Intent intent) {
        utils.notifyActivityResult(request, response, intent);
    }

    @Override
    public void notifyBitmap(int id, Bitmap bitmap, String path) {
        aNum = (gAdapter.getDataSource().size() - 1);
        if (!TextUtils.isEmpty(path)) {
            aNum++;
            if (aNum > maxNum) {
                UIHelper.ToastMessage(mContext,
                        String.format("最多只能添加%d张附件", maxNum));
                return;
            }
            if (aNum == maxNum) {
                gAdapter.getDataSource().remove(gAdapter.getDataSource().size() - 1);
                gAdapter.getDataSource().add(gAdapter.getDataSource().size(), new ApprovalAttachBean(path, bitmap));
            } else if (gAdapter.getDataSource().size() > 0)
                gAdapter.getDataSource().add(gAdapter.getDataSource().size() - 1, new ApprovalAttachBean(path, bitmap));
            else
                gAdapter.getDataSource().add(new ApprovalAttachBean(path, bitmap));
            gAdapter.notifyDataSetChanged();
            num.setText(String.format("(%1$d/%2$d)", aNum, maxNum));

            if (acListener != null)
                acListener.onTakePhotoFinished();
        }
    }

    @Override
    public void notifyBitmap(int id, List<String> paths) {
        aNum = (gAdapter.getDataSource().size() - 1);
        if (paths != null && paths.size() > 0) {
            aNum += paths.size();
            if (aNum > maxNum) {
                UIHelper.ToastMessage(mContext,
                        String.format("最多只能添加%d张附件", maxNum));
                return;
            }
            for (String path : paths) {
                if (gAdapter.getDataSource().size()== maxNum) {
                    gAdapter.getDataSource().remove(gAdapter.getDataSource().size() - 1);
                    gAdapter.getDataSource().add(gAdapter.getDataSource().size(), new ApprovalAttachBean(path, null));
                } else if (gAdapter.getDataSource().size() > 0)
                    gAdapter.getDataSource().add(gAdapter.getDataSource().size() - 1, new ApprovalAttachBean(path, null));
                else
                    gAdapter.getDataSource().add(new ApprovalAttachBean(path, null));
            }
            gAdapter.notifyDataSetChanged();
            num.setText(String.format("(%1$d/%2$d)", aNum, maxNum));
        }
    }



    @Override
    public void delPic(ApprovalAttachBean bean) {
        aNum = (gAdapter.getDataSource().size() - 1);
        num.setText(String.format("(%1$d/%2$d)", aNum, maxNum));
    }



    /**
     * 拍照结束 图片异步处理结束
     *
     * @author gyh
     */
    public interface AttachCameraListener {
        public void onTakePhotoFinished();
    }

    public interface AttachClickPosition {
        public void noticClickPosition();
    }


    public void setTitleLayout(int marginTop) {
        LinearLayout.LayoutParams para = (LayoutParams) titleLayout.getLayoutParams();
        if (marginTop < 0)
            return;
        para.setMargins(0, marginTop, 0, 0);
        titleLayout.setLayoutParams(para);
    }
}

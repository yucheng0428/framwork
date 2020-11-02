package com.lib.picturecontrol.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lib.common.recyclerView.SimpleRecAdapter;
import com.lib.picturecontrol.R;
import com.lib.picturecontrol.R2;
import com.lib.picturecontrol.views.MediaDelectInterface;
import com.lib.picturecontrol.views.MediaUploadInterface;
import com.lib.picturecontrol.views.UploadAbleImageView;
import com.lib.picturecontrol.bean.ApprovalAttachBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReclecyAdapter extends SimpleRecAdapter<ApprovalAttachBean, ReclecyAdapter.ViewHodler> {
    public static final ApprovalAttachBean ADDPIC = new ApprovalAttachBean(R.mipmap.add_attach_icon);

    public float imgSize;// item默认宽度（正方形）
    private Context mContext;
    private DelListener delListener;
    private boolean editAble;// 是否有编辑权限
    private int layoutIdView = 0;// 子项布局
    private List<String> upLoadResults = new ArrayList<>();//已上传图片集合

    public ReclecyAdapter(Context context, boolean editAble) {
        super(context);
        this.mContext = context;
        this.editAble = editAble;

        imgSize = mContext.getResources().getDimension(R.dimen.attach_item_width);
        if (editAble) {
            data.add(ADDPIC);
        }
    }

    public void isEditAble(boolean editAble) {
        this.editAble = editAble;
        if (!editAble && data.contains(ADDPIC))
            data.remove(ADDPIC);
        if (editAble && !data.contains(ADDPIC))
            data.add(ADDPIC);
    }


    public void setDelListener(DelListener listener) {
        this.delListener = listener;
    }

    public void setLayoutId(int viewId) {
        layoutIdView = viewId;
    }

    @Override
    public ReclecyAdapter.ViewHodler newViewHolder(View itemView) {
        return new ViewHodler(itemView);
    }

    @Override
    public int getLayoutId() {
        if (layoutIdView == 0) {
            layoutIdView = R.layout.item_attend_pics;
        }
        return layoutIdView;
    }

    @Override
    public void onBindViewHolder(ReclecyAdapter.ViewHodler holder, int position) {
        final ApprovalAttachBean item = data.get(position);

        if (item.imgRes != 0) {
            holder.pic.showDelBtn(false);
            holder.pic.getImageModel().setImageResource(item.imgRes);
        } else {
            if (item.bm != null) {
                holder.pic.getImageModel().setImageBitmap(item.bm);
            } else {
                String url = null;
                if (item.url.contains("/")) {
                    url = item.url;
                    if (!item.url.startsWith("content://"))
                        url = "file://" + item.url;
                } else {
                    url = item.url;
                }
                Glide.with(mContext).load(url).placeholder(R.drawable.icon_loadings).skipMemoryCache(true).into(holder.pic.getImageModel());
            }
            holder.pic.showDelBtn(editAble);
            if (position == 0)
                Log.d("attachView", "listSize " + item.id);
            holder.pic.setAttachBean(item);
            /**
             * 未上传图片处理
             */
            if (item.url.startsWith("/") && !item.hasUploaded) {
                Log.d("imageUpload", item.url);
                holder.pic.setUploadInterface(new MediaUploadInterface() {
                    @Override
                    public void uploadListener(int pos, String bean) {
                        upLoadResults.add(bean);
                    }
                });
                holder.pic.startUpload(item, position);
                item.hasUploaded = true;
            }
        }
        /**
         * 删除图片处理
         */
        holder.pic.setDelectInterface(new MediaDelectInterface() {
            @Override
            public void fileDelectListener(int pos, String s) {
                String sd = data.get(position).url;
                if (upLoadResults.contains(sd)) {
                    upLoadResults.remove(sd);
                }
                if (!data.contains(ADDPIC)) {
                    data.add(data.size(), ADDPIC);
                }
                data.remove(position);
                notifyDataSetChanged();
                delListener.delPic(item);
            }
        });
        //图片点击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, data.get(position), 1, holder);
                }
            }
        });
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        @BindView(R2.id.iv_pic)
        UploadAbleImageView pic;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface DelListener {
        /**
         * 附件删除监听
         */
        public void delPic(ApprovalAttachBean bean);
    }

    public void addPicFlag() {
        data.add(data.size(), ADDPIC);
    }

    public void clearCache() {
        upLoadResults.clear();
    }
}

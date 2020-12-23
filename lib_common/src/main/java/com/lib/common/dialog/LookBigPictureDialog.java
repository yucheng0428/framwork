package com.lib.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.lib.common.R;

/**
 * 查看大图
 */
public class LookBigPictureDialog extends Dialog {
    protected boolean isneedFinish = false;
    public Context context;
    ImageView imageView;


    public void setImgUrl(String imgUrl) {
        if (imgUrl != null) {
            Glide.with(context).load(imgUrl).placeholder(R.drawable.icon_loadings).skipMemoryCache(true).into(imageView);
        }
    }

    public LookBigPictureDialog(@NonNull Context context, String url) {
        super(context, R.style.DialogTheme);
        this.context = context;
        setWindow();
        setContentView(R.layout.dialog_lookbig_picture);
        imageView = findViewById(R.id.image);
        setImgUrl(url);
    }


    public void setWindow() {
        // TODO Auto-generated method stub
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置

        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(p);
        window.setWindowAnimations(android.R.style.Animation);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isneedFinish && context instanceof Activity) {
            ((Activity) context).finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void show() {
        try {
            if (context instanceof Activity && ((Activity) context).isFinishing())
                return;
            super.show();
        } catch (Exception e) {
        }
    }
}

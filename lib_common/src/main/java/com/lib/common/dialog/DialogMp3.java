package com.lib.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.lib.common.R;

public class DialogMp3 extends Dialog {
    public DialogMp3(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        setContentView(R.layout.dlg_mp3);
    }

    private void setWindow() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = d.getWidth();
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(p);
    }
}

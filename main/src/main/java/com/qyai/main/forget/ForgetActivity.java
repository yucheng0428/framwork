package com.qyai.main.forget;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.common.BaseMvp.BaseMvpHeadAct;
import com.lib.common.BaseMvp.factory.CreateMvpPresenter;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.qyai.main.R;
import com.qyai.main.R2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

@CreateMvpPresenter(ForgetPersenter.class)
@Route(path = Common.FORGET_VIEW)
public class ForgetActivity extends BaseMvpHeadAct<ForgetView, ForgetPersenter> implements ForgetView {
    @BindView(R2.id.et_user)
    EditText et_user;
    @BindView(R2.id.et_code)
    EditText et_code;
    @BindView(R2.id.tv_sendcode)
    TextView tv_sendcode;
    @BindView(R2.id.et_password)
    EditText et_password;
    @BindView(R2.id.btn_ok)
    Button btn_ok;
    Disposable disposable;
    boolean isCode = false;
    boolean isShow=false;
    @Override
    public int layoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initUIData() {
        setTvTitle(getResources().getString(R.string.title_forget));
    }

    @OnClick({R2.id.tv_sendcode, R2.id.btn_ok,R2.id.iv_open,})
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ok) {
            if (!TextUtils.isEmpty(userName()) && !TextUtils.isEmpty(passWord())) {
                Map<Object,Object> para=new HashMap<>();
                para.put("userName",userName());
                para.put("password",passWord());
                para.put("code",sendCode());
                getMvpPresenter().reqForget(para);
            }
        } else if (v.getId() == R.id.tv_sendcode) {
            if (isCode) {
                return;
            }
            if(!SPValueUtil.isEmpty(et_user.getText().toString())){
                showErrMsg("请填写手机号码");
                return;
            }
            disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .map(new Function<Long, Long>() {
                        @Override
                        public Long apply(Long aLong) throws Exception {
                            return 60 - (aLong + 1);
                        }
                    })
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(final Long count) throws Exception {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    isCode = true;
                                    tv_sendcode.setText(count + "秒后重发");
                                }
                            });
                            if (count == 0) {
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        isCode = false;
                                        tv_sendcode.setText("获取验证码");
                                    }
                                });

                                if (disposable != null) {
                                    disposable.dispose();
                                }
                            }
                        }
                    });
        }else if(v.getId()==R.id.iv_open){
            if(isShow){
                isShow=false;
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }else {
                isShow=true;
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //密码可见
            }
        }
    }

    @Override
    public void showLodingDialog() {
        showProgress();
    }

    @Override
    public void hidLodingDialog() {
        hindProgress();
    }

    @Override
    public void showErrMsg(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public String userName() {
        String userName = et_user.getText().toString().trim();
        if (!TextUtils.isEmpty(userName)) {
            return userName;
        } else {
            UIHelper.ToastMessage(mActivity, "请输入密码");
            return "";
        }
    }

    @Override
    public String passWord() {
        String password = et_password.getText().toString().trim();
        if (!TextUtils.isEmpty(password)) {
            return password;
        } else {
            UIHelper.ToastMessage(mActivity, "请输入密码");
            return "";
        }
    }

    @Override
    public String sendCode() {
        String code= et_code.getText().toString().trim();
        if (!TextUtils.isEmpty(code)) {
            return code;
        } else {
            UIHelper.ToastMessage(mActivity, "请输入验证码");
            return "";
        }
    }

    @Override
    public void nextForget(String s) {
        showErrMsg(s);
    }
}
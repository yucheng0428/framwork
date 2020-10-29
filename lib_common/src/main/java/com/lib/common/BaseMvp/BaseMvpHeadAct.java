package com.lib.common.BaseMvp;

import android.os.Bundle;
import android.util.Log;

import com.lib.common.BaseMvp.factory.PresenterMvpFactory;
import com.lib.common.BaseMvp.factory.PresenterMvpFactoryImpl;
import com.lib.common.BaseMvp.presenter.BasePresenter;
import com.lib.common.BaseMvp.proxy.BaseMvpProxy;
import com.lib.common.BaseMvp.proxy.PresenterProxyInterface;
import com.lib.common.BaseMvp.view.BaseView;
import com.lib.common.base.BaseHeadActivity;

/**
 * 作者：yucheng 2018/9/18 0018
 * 类描述： mvp中Acitivity抽象类
 * @description 继承自Activity的基类MvpActivity
 * 使用代理模式来代理Presenter的创建、销毁、绑定、解绑以及Presenter的状态保存,
 * 其实就是管理Presenter的生命周期
 */
public abstract class BaseMvpHeadAct<V extends BaseView,P extends BasePresenter<V>> extends BaseHeadActivity
   implements PresenterProxyInterface<V,P> {
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V,P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V,P>createFactory(getClass()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("perfect-mvp","V onCreate");
        Log.d("perfect-mvp","V onCreate mProxy = " + mProxy);
        Log.d("perfect-mvp","V onCreate this = " + this.hashCode());
        if(savedInstanceState != null){
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("perfect-mvp","V onResume");
        mProxy.onResume((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("perfect-mvp","V onDestroy = " );
        mProxy.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("perfect-mvp","V onSaveInstanceState");
        outState.putBundle(PRESENTER_SAVE_KEY,mProxy.onSaveInstanceState());
    }

    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        Log.d("perfect-mvp","V setPresenterFactory");
        mProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        Log.d("perfect-mvp","V getPresenterFactory");
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getMvpPresenter() {
        Log.d("perfect-mvp","V getMvpPresenter");
        return mProxy.getMvpPresenter();
    }

}

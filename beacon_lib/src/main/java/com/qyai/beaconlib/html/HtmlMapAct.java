package com.qyai.beaconlib.html;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.lib.common.BaseMvp.BaseMvpHeadAct;
import com.lib.common.BaseMvp.factory.CreateMvpPresenter;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.qyai.beaconlib.R;
import com.qyai.beaconlib.R2;
import com.qyai.beaconlib.bean.QYPositionBean;
import com.qyai.beaconlib.location.SensorManageService;
import com.qyai.beaconlib.utlis.Constants;
import com.qyai.beaconlib.utlis.JSUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;

@CreateMvpPresenter(HtmlPersener.class)
public class HtmlMapAct extends BaseMvpHeadAct<HtmlView, HtmlPersener> implements HtmlView {
    @BindView(R2.id.mWebContainer)
    LinearLayout mWebContainer;
    @BindView(R2.id.view_map)
    WebView webView;
    boolean isMapReady = false;
    private static String currentBuildId;
    private String mapUrl = "";
    boolean isCheack=true;
    protected QYPositionBean currentPosition = null;
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
        UIHelper.ToastMessage(mActivity, msg);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_html_map;
    }

    @Override
    protected void initUIData() {

        EventBus.getDefault().register(this);
        hideIvRight(View.VISIBLE);
        setIvRightSrc(R.mipmap.add_attention_bg);
        setTvTitle("地图");
        SensorManageService.initService(mActivity);
        initWebView();
    }

    @Override
    public void setOnClickIvRight() {
        super.setOnClickIvRight();
    }


    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);

        WebView.setWebContentsDebuggingEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //不展示缩放工具
        webSettings.setDisplayZoomControls(false);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webSettings.setDatabaseEnabled(true);   // 开启 database storage API 功能
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setSaveFormData(true);
        webSettings.setGeolocationEnabled(true);
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        webSettings.setAllowFileAccessFromFileURLs(true);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        webSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true

        JSUtils jsUtils = new JSUtils(mActivity, tvTitle, new JSUtils.WebViewCallBack() {
            @Override
            public void isReady() {
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            webView.loadUrl("javascript:changeMapById('" + currentBuildId + "')");
                        } catch (Exception e) {
                            Log.e("WEBVIEW", e.toString());
                        }
                    }
                });
            }

            @Override
            public void isMapReady(boolean b) {
                isMapReady = b;
            }
        });
        webView.addJavascriptInterface(jsUtils, "android_map");
            webView.loadUrl(Constants.MAP_URL);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(QYPositionBean position) {
        try {
            Log.d("WEBVIEW:拿到位置信息了", position.toString());
            currentPosition = position;
            if(isCheack&& SPValueUtil.isEmpty(position.getBuildId())){
                if (currentBuildId == null || !currentBuildId.equals(position.getBuildId())) {
                    currentBuildId = position.getBuildId();
                    isMapReady = false;
                    webView.loadUrl("javascript:changeMapById('" + currentBuildId + "')");
                    return;
                }
            }
            if (isMapReady && position != null && currentBuildId.equals(position.getBuildId())) {

                webView.loadUrl("javascript:updateMyPosition(" + position.x + "," + position.y + "," + position.z + "," + position.getDirection() + ",'" + position.getRange() + "')");
//                setTitle(LocationUtils.getMetroName(position.getBuildId()));
            }
        } catch (Exception e) {
            Log.e("WEBVIEW+even", e.toString());
        }
    }

    @Override
    public void onDestroy() {
        destroyWebView();
        super.onDestroy();
    }

    public void destroyWebView() {
        SensorManageService.stopService(mActivity);
        mWebContainer.removeAllViews();

        if (webView != null) {
            webView.clearHistory();
            webView.clearCache(true);
            webView.removeJavascriptInterface("android_map");
            webView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            webView.freeMemory();
            webView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getBlueToothInfo(Map<Object, Object> map) {
        getMvpPresenter().setReqQuery(map);
    }


    @Override
    public void showInfoMessage(BeaCcontBean bean) {
    }
}

package com.qyai.baidumap;

import android.util.Log;

import com.mob.MobSDK;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.whatsapp.WhatsApp;

public class ShareUtils {

    public static void shareMessage(String title, String content, String imageUrl, String url, String phone) {
        OnekeyShare oks = new OnekeyShare();
        oks.addHiddenPlatform(WechatMoments.NAME);
        oks.addHiddenPlatform(WhatsApp.NAME);
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                //微博分享链接和图文
                if ("SinaWeibo".equals(platform.getName())) {
                    paramsToShare.setText(content);
                    paramsToShare.setImageUrl(imageUrl);
                }
                //微信好友分享网页
                if ("Wechat".equals(platform.getName())) {
                    paramsToShare.setTitle(title);
                    paramsToShare.setText(content);
                    paramsToShare.setImageUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20180111%2F00%2F1515603525-qwkFGlSYdP.jpg&refer=http%3A%2F%2Fimage.biaobaiju.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1617157877&t=d67d7df351c3aafc756ae0e21c0e7ea3");
                    paramsToShare.setUrl("http://bbs.mob.com/thread-24431-1-6.html");
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    Log.d("ShareSDK", paramsToShare.toMap().toString());
                }
                //微信朋友圈分享图片
                if ("WechatMoments".equals(platform.getName())) {
                    paramsToShare.setTitle(title);
                    paramsToShare.setText(content);
            /*Bitmap imageData = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
            paramsToShare.setImageData(imageData);*/
                    paramsToShare.setImageUrl(imageUrl);
                    paramsToShare.setShareType(Platform.SHARE_IMAGE);
                    Log.d("ShareSDK", paramsToShare.toMap().toString());
                }
                //QQ分享链接
                if ("QQ".equals(platform.getName())) {
                    paramsToShare.setTitle(title);
                    paramsToShare.setTitleUrl(url);
                    paramsToShare.setText(content);
                    paramsToShare.setImageUrl(imageUrl);
                    Log.d("ShareSDK", paramsToShare.toMap().toString());
                }
                //支付宝好友分享网页
                if ("Alipay".equals(platform.getName())) {
                    paramsToShare.setTitle(title);
                    paramsToShare.setText(content);
                    paramsToShare.setImageUrl(imageUrl);
                    paramsToShare.setUrl(url);
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    Log.d("ShareSDK", paramsToShare.toMap().toString());
                }
                //WhatsApp分享图片
                if ("WhatsApp".equals(platform.getName())) {
                    paramsToShare.setImageUrl(imageUrl);
                }
                //短信分享文本
                if ("ShortMessage".equals(platform.getName())) {
                    paramsToShare.setText(content);
                    paramsToShare.setTitle(title);
                    paramsToShare.setAddress(phone);
                }
            }
        });
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.d("ShareLogin", "onComplete ---->  分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d("ShareLogin", "onError ---->  失败" + throwable.getStackTrace());
                Log.d("ShareLogin", "onError ---->  失败" + throwable.getMessage());
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.d("ShareLogin", "onCancel ---->  分享取消");
            }
        });

// 启动分享GUI
        oks.show(MobSDK.getContext());
    }
}

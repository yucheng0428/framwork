package com.lib.common.netHttp;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.FlowableSubscriber;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class FlowabBaseSubscribe<T> implements FlowableSubscriber<ResponseBody> {
    Subscription subscription;
    public OnHttpCallBack<T> callBack;
    public int reqId;
    Class<T> clazz;


    public FlowabBaseSubscribe(int id,OnHttpCallBack<T> callBack,Class<T> clazz){
        this.callBack = callBack;
        this.clazz=clazz;
        this.reqId=id;
    }
    @Override
    public void onSubscribe(Subscription s) {
        subscription=s;
        s.request(10);
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        if (responseBody != null) {
            try {
                T event= JSONObject.parseObject(responseBody.string(),clazz);
                callBack.onSuccessful(reqId,event);
            }catch (Exception e){
                Log.e("解析出错了",""+e);
            }
        } else {
            onException(HttpExceptionReason.LODING_ERROR);
        }
    }


    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            Log.d("HTTPERR","code = "+code);
            if (code == 500 || code == 404) {
                onException(HttpExceptionReason.SERVE_ERROR);
            } else if (code == 405) {
                onException(HttpExceptionReason.CONNECT_ERROR);
            }
        } else if (e instanceof ConnectException) {
            onException(HttpExceptionReason.BAD_NETWORK);
        } else if (e instanceof SocketTimeoutException) {
            onException(HttpExceptionReason.CONNECT_TIMEOUT);
        } else {
            onException(HttpExceptionReason.UNKNOWN_ERROR);
        }
        callBack.onFaild(reqId, null, e.getMessage());
    }

    @Override
    public void onComplete() {
    }

    //取消
    public void cancel(){
          subscription.cancel();
    }

    public void onException(HttpExceptionReason reason) {
        String errMsg = "服务器异常";
        switch (reason) {
            case BAD_NETWORK:
                errMsg = "网络异常!!";
                break;
            case PARSE_ERROR:
                errMsg = "数据解析失败!!";
                break;
            case CONNECT_ERROR:
                errMsg = "请求配置出错!!";
                break;
            case CONNECT_TIMEOUT:
                errMsg = "网络连接超时!!";
                break;
            case UNKNOWN_ERROR:
                errMsg = "数据获取失败";
                break;
            case LODING_ERROR:
                errMsg = "访问错误";
                break;
        }
        callBack.onFaild(reqId, null, errMsg);

    }
}

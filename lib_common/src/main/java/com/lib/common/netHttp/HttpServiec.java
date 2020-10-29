package com.lib.common.netHttp;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述：
 * Created by szh
 */
public class HttpServiec {
    public BaseApi service;
    private static HttpServiec instance;
    public static OkHttpClient okHttpClient;
    public static Retrofit retrofit;
    public static String base_Url = "http://47.92.125.30:18200/";
    public static Map<Object,Object> httpManger=new HashMap<>();

    public static HttpServiec getInstance() {
        if (instance == null) {
            synchronized (HttpServiec.class) {
                if (instance == null) {
                    instance = new HttpServiec();
                    httpManger.clear();
                }
            }
        }
        return instance;
    }

    public static void  cleanInstance(){
        instance=null;
    }

    //构造方法私有
    public HttpServiec() {
            service = getRetrofit().create(BaseApi.class);
    }



    public Retrofit getRetrofit() {
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient(10))
                    .baseUrl(base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .build();
        return retrofit;
    }

    //构造方法私有
    private OkHttpClient getOkHttpClient(int time) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(time, TimeUnit.SECONDS)
                    .connectTimeout(time, TimeUnit.SECONDS)
                    .addInterceptor(NetHeaderInterceptor.getInterceptor())
                    .addInterceptor(MySSLSocketFactory.loggingInterceptor)
                    .build();
        return okHttpClient;
    }


    /**
     * 统一线程处理
     *
     *
     * @return
     */
    FlowableTransformer getScheduler() {    //compose简化线程
        return new FlowableTransformer() {
            @Override
            public Flowable apply(Flowable observable) {
                return  observable
                        .subscribeOn(Schedulers.io())
                        .onBackpressureDrop()
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    //发起请求
    public void postHttpData(int id, String url, RequestBody req, ResponseObserver observer) {
        observer.setReqId(id);
        HttpServiec.getInstance().service.executePostData(url, req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
    //发起请求
    public void postFlowableMap(int id, String url, Map<Object,Object> req, OnHttpCallBack onHttpCallBack, Class clazz) {
        FlowabBaseSubscribe baseSubscribe=new FlowabBaseSubscribe(id,onHttpCallBack,clazz);
        RequestBody requestBody= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(req));
        HttpServiec.getInstance().service.flowablePostData(url, requestBody)
                .compose(getScheduler())
                .subscribeWith(baseSubscribe);
        httpManger.put(id,baseSubscribe);
    }
    //发起请求
    public void postFlowableData(int id, String url, RequestBody req, OnHttpCallBack onHttpCallBack, Class clazz) {
        FlowabBaseSubscribe baseSubscribe=new FlowabBaseSubscribe(id,onHttpCallBack,clazz);
        HttpServiec.getInstance().service.flowablePostData(url, req)
               .compose(getScheduler())
                .subscribeWith(baseSubscribe);
        httpManger.put(id,baseSubscribe);
    }

    //发起请求
    public void postFlowableData(int id, String url, Object req, OnHttpCallBack onHttpCallBack, Class clazz) {
        FlowabBaseSubscribe baseSubscribe=new FlowabBaseSubscribe(id,onHttpCallBack,clazz);
        HttpServiec.getInstance().service.flowablePostData(url, req)
                .compose(getScheduler())
                .subscribeWith(baseSubscribe);
        httpManger.put(id,baseSubscribe);
    }


    public void cancel(int id){
        if(httpManger.size()>0&&httpManger.get(id)!=null){
            FlowabBaseSubscribe subscribe= (FlowabBaseSubscribe) httpManger.get(id);
            subscribe.cancel();
            httpManger.remove(id);
        }
    }

}
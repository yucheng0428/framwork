package com.lib.common.netHttp;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 设置请求头
 * Created by yucheng on 2017-03-22.
 */

public class NetHeaderInterceptor implements Interceptor {
    private Map<String, String> headers;
   static NetHeaderInterceptor interceptor;
    public static NetHeaderInterceptor getInterceptor(){
        if(interceptor==null){
            interceptor=new NetHeaderInterceptor();
        }
        return interceptor;
    }

    /**
     * 添加请求头
     * 根据需要配置
     */
    public NetHeaderInterceptor() {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }

        return chain.proceed(builder.build());
    }

}

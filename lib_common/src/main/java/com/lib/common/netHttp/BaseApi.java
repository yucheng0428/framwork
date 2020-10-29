package com.lib.common.netHttp;


import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface BaseApi {
    /**
     * @param url  请求地址
     * @param maps 请求参数
     * @return
     */
    @GET
    Observable<ResponseBody> executeGet(@Url String url, @QueryMap Map<String, String> maps);


    /**
     * @param url  请求地址
     * @param maps 请求参数
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> execPost(@Url String url, @FieldMap Map<String, String> maps);


    /**
     * 实体请求
     *
     * @param url
     * @return
     */
    @POST
    Observable<ResponseBody> executePostData(@Url String url, @Body RequestBody requestBody);

    /**
     * 实体请求
     *
     * @param url
     * @return
     */
    @POST
    Flowable<ResponseBody>flowablePostMap(@Url String url, @QueryMap Map<String, String> maps);
    @POST
    Flowable<ResponseBody> flowablePostData(@Url String url, @Body RequestBody requestBody);
    @POST
    Flowable<ResponseBody> flowablePostData(@Url String url, @Body Object requestBody);
    @Streaming
    @POST("/systemStorage/download")
//文件下载
//    Observable<ResponseBody> download(@Body RequestBody requestBody,@Path("url") String url);
    Observable<ResponseBody> download(@Url String url, @Query("path") String path);


    @POST
        //文件上传BASE64
    Observable<FileEvent> uploadFileBase(@Url String url, @Body Object object);
}

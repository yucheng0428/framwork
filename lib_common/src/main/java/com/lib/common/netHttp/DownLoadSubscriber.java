package com.lib.common.netHttp;



import android.os.AsyncTask;

import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * <p>类说明</p>
 *
 * @author yucheng
 * @date 2018/12/20 16:02
 * @Description
 */
public abstract class DownLoadSubscriber implements Observer<ResponseBody> {
    String fileName;
    protected Disposable disposable;

    public DownLoadSubscriber(int id,String fileName){
        this.fileName=fileName;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onComplete(){
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
    @Override
    public void onNext(ResponseBody t) {
        fileName = "android.apk";
        File dir = new File(Constants.STORAGE_FILE);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        if (file.exists()) {
            file.delete();
        }
        new WriteFile(file,t,this).execute();
        LogUtil.e("DownLoadSubscriber", "onNext");
    }

    @Override
    public void onError(Throwable e) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        onFail("下载失败");
        LogUtil.e("DownLoadSubscriber", "onError"+e.getMessage());
    }

    private class WriteFile extends AsyncTask<String, Long, Boolean> {
        private File file;
        private ResponseBody body;
        private DownLoadSubscriber subscriber;

        public WriteFile(File file, ResponseBody body, DownLoadSubscriber subscriber) {
            this.file = file;
            this.body = body;
            this.subscriber = subscriber;
        }

        @Override
        protected void onPreExecute() {
            subscriber.onStart();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            InputStream input = null;
            byte[] buf = new byte[2048];
            int len = 0;
            FileOutputStream fos = null;
            try{
                input = body.byteStream();
                final long total = body.contentLength();
                long sum = 0;

                File dir = file.getParentFile();
                if (!dir.exists()){
                    if (!dir.mkdirs()) return false;
                }
                fos = new FileOutputStream(file);
                while ((len = input.read(buf))!=-1){
                    sum += len;
                    fos.write(buf,0,len);
                    final long finalSum = sum;
                    publishProgress(finalSum, total);
                }
                fos.flush();
                return true;
            } catch (Exception ex){
                return false;
            }finally {
                try{
                    if (input != null) input.close();
                    if (fos != null) fos.close();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            subscriber.onProgress(values[1], values[2],values[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result){
                subscriber.onFinish(file.getAbsolutePath());
            } else {
                subscriber.onFail("下载失败");
            }
        }
    }


    public void onFail(String msg){}

    public void onStart(){};//下载开始

   public  void onProgress(long progress,long downSize,long total){};//下载进度

   public  void onFinish(String path){};//下载完成

}


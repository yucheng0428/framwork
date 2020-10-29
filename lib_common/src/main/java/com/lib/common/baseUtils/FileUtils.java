package com.lib.common.baseUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils
{
	public static String readFile(InputStream in)
	{
		InputStreamReader is = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(is);
		String line = null;
		StringBuilder sBuilder = new StringBuilder();
		try
		{
			while ((line = br.readLine()) != null)
			{
				sBuilder.append(line);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return sBuilder.toString();
	}

	public static String readFile(Context mContext, int id)
	{
		InputStream is = mContext.getResources().openRawResource(id);
		int size;
		String text = null;
		try
		{
			size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			text = new String(buffer, "utf8");
			return text.replace("\r\n", "\n");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static String readMETAINFO(Context ctx)
	{
		ClassLoader classLoader = ctx.getClassLoader();
		URL url = classLoader.getResource("META-INF/channel");
		if (url == null)
		{
			return null;
		}
		InputStream is = classLoader.getResourceAsStream("META-INF/channel");
		return readFile(is);
	}
	
	

	private static final String IMG_NAME = "TicketSA_FlashPic";

	/**
	 * 从本地文件中加载图片
	 * 
	 * @return
	 */
	public static Bitmap loadPic(Activity activity) {
		String fileDir = activity.getFilesDir().getAbsolutePath();
		try {
			String SavePath = fileDir + "/TicketSA/" + IMG_NAME;
			Bitmap bm = BitmapFactory.decodeFile(SavePath);
			if (bm != null) {
				if (bm.getRowBytes() != 0) {
					return bm;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static String getExternalCacheDir(Context ctx) {
		final String dir;
//		if (Build.VERSION.SDK_INT >= 11) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			Context context=ctx;
			File fi=ctx.getExternalCacheDir();
			if(fi==null){
				final String cacheDir = "/Android/data/" + ctx.getPackageName() + "/cache/";
				dir = Environment.getExternalStorageDirectory().getPath() + cacheDir;
			}else{
				dir = ctx.getExternalCacheDir().getAbsolutePath();
			}
		} else {
			final String cacheDir = "/Android/data/" + ctx.getPackageName() + "/cache/";
			dir = Environment.getExternalStorageDirectory().getPath() + cacheDir;
		}
		return dir;
	}
	
	
	
	 /** 
     * 获得指定文件的byte数组 
     */  
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {
            e.printStackTrace();  
        } catch (IOException e) {
            e.printStackTrace();  
        }  
        return buffer;  
    }  
  
    /** 
     * 根据byte数组，生成文件 
     */  
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {  
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath+"\\"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);  
        } catch (Exception e) {
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {
                    e1.printStackTrace();  
                }  
            }  
        }  
    } 
    
    
    
    /**
	 * 删除文件
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!TextUtils.isEmpty(path)) {

			File newPath = new File(path);
			checker.checkDelete(newPath.toString());
			if (newPath.isFile()) {
				try {
					status = newPath.delete();

				} catch (SecurityException se) {
					se.printStackTrace();
					status = false;
				}
			} else
				status = false;
		} else
			status = false;
		return status;
	}
	
	
	/**
	 * 删除文件
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (null != file && file.exists()) {

			checker.checkDelete(file.toString());
			if (file.isFile()) {
				try {
					status = file.delete();
					
				} catch (SecurityException se) {
					se.printStackTrace();
					status = false;
				}
			} else
				status = false;
		} else
			status = false;
		return status;
	}
    
	/**获取文件大小**/
	public static long getCacheSize(File file){
		
		long size = 0;
		
		if(file.exists() && file.isDirectory()){
			String[] fileArr = file.list();
			if(null != fileArr){
				File file2 = null;
				for(int index = 0, len = fileArr.length; index < len; index++){
					file2 = new File(file, fileArr[index]);
					if(null != file2 && file2.exists()){
						if(!file2.isFile()){
							 size += getCacheSize(file2);
						}else{
							size += file2.length();
						}
						
					}
				}
			}
		}
		return size;
	}
	
	/**根据字节转换相应的单位B,k,m等等**/
    public static String FormetFileSize(long fileS){// 转换文件大小
	    DecimalFormat df = new DecimalFormat("#.00");
	    String fileSizeString = "";
	    if(fileS <= 0)
	    	fileSizeString = "0B";
	    else if (fileS < 1024){
	        fileSizeString = df.format((double) fileS) + "B";
	    }else if (fileS < 1048576){
	        fileSizeString = df.format((double) fileS / 1024) + "K";
	        
	    }else if (fileS < 1073741824){
	    	fileSizeString = df.format((double) fileS / 1048576) + "M";
	    }else{
	        fileSizeString = df.format((double) fileS / 1073741824) + "G";
	    }
	    return fileSizeString;
	}
    
    /**删除指定文件夹**/
    public static int clearCacheFolder(File dir, long numDays) {
    	int deletedFiles = 0;                  
    	if (dir!= null && dir.isDirectory()) {
    		try {                                 
    			for (File child:dir.listFiles()) {
    				if (child.isDirectory()) { 
    					deletedFiles += clearCacheFolder(child, numDays);
    					}                         
    				if (child.lastModified() < numDays) {
    					if (child.delete()) {
    						deletedFiles++;
    						}
    					}
    				}
    			} catch(Exception e) {
    				e.printStackTrace();
    				} 
    			}
    	return deletedFiles;
    	}  
    
    /**根据文件后缀得到文件类型:1图片;2文本;3多媒体**/
    public static int getFileType(String filePostfix){
    	String postfix=filePostfix.replace(".", "");
    	//图片后缀
    	String postfixs1[]=new String[]{"jpg","png","bmp","jpge"};
    	//文本后缀
    	String postfixs2[]=new String[]{"txt","text","doc","docx","rtf"};
    	//多媒体后缀
    	String postfixs3[]=new String[]{"mp3","mp4","rm","wav","ram","mpg","avi"};
    	//网页后缀
    	String postfixs4[]=new String[]{"html","htm","jsp"};
    	//压缩文件后缀
    	String postfixs5[]=new String[]{"zip"};
    	//默认文件
    	String postfixs6[]=new String[]{""};
    	return -1;}
    
    /** 二进制数据保存到文件*/
    public static void saveBinaryCodeToFile(byte date[],String path){
    	FileOutputStream ops=null;
    	try {
			ops=new FileOutputStream(new File(path));
			int length=0;
			ops.write(date);
			ops.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(ops!=null){
				try {
					ops.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
    /**创建文件*/
    public static File createFile(String filePath, String fileName){
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			try{
				File dir = new File(filePath);
				if(!dir.exists())
					dir.mkdirs();
				
				File file = new File(dir,fileName);
				if(!file.exists())
					file.createNewFile();
				return file;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 创建图片文件
	 * @param context
	 * @return
	 */
	public static  File createFilePng(Context context){
		//用时间戳来命名
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		String time = formatter.format(new Date());
		String fileName =  time + ".jpg";
		String path = FileUtils.getFilePath(Constants.STORAGE_PICTURE,context);
		File file = new File(path+"/"+fileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 创建图片文件
	 * @param context
	 * @return
	 */
	public static  File createFileMp3(Context context){
		//用时间戳来命名
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		String time = formatter.format(new Date());
		String fileName =  time + ".mp3";
		String path = FileUtils.getFilePath(Constants.STORAGE_PICTURE,context);
		File file = new File(path+"/"+fileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	public static String getFilePath(String fileName,Context context){

		File dirFile;
		//获取外部存储路径  api 29以下可以用这种方式在根目录 创建文件夹

		if(android.os.Build.VERSION.SDK_INT >= 29){

			dirFile = new File(context.getExternalFilesDir(null).getAbsolutePath()+fileName);
		}
		else {
			// 该方式由于谷歌在android 10 开始不允许在根目录下创建路径 所以
			//只能在/storage/emulated/0/Android/data/包名/files 此目录下创建
			dirFile = new File(Environment.getExternalStorageDirectory().getPath() + fileName);
		}
		if (!dirFile.exists()) {
			boolean mkdirs = dirFile.mkdirs();
			if (!mkdirs) {
				Log.e("TAG", "文件夹创建失败");
			}
		}

		return dirFile.getAbsolutePath();
	}

	/**根据文件路径获取文件名**/
	public static String getFileName(String filePostfix){
		String fileName = filePostfix.substring(filePostfix.lastIndexOf("/")+1);
		return fileName;
	}

	/**文件名获取文件后缀**/
	public static String getFileNameype(String filePostfix){
		String fileName = filePostfix.substring(filePostfix.lastIndexOf(".")+1);
		return fileName;
	}

	/**
	 * <图片缓存目录> <功能详细描述>
	 *
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String getCachePath() {
		String sdcard = Environment.getExternalStorageDirectory() + "/";
		StringBuilder sb = new StringBuilder(sdcard).append("YunOsCache");
		return sb.toString();
	}

	/**
	 * 取得文件大小
	 * @param f
	 * @return
	 */
	public static long getFileSizes(File f) {
		long s = 0;
		FileInputStream fis = null;
		try {
			if (f.exists()) {

				fis = new FileInputStream(f);
				s = fis.available();
			} else {
				f.createNewFile();
				System.out.println("文件不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return s;
	}

	public synchronized static void openFileStr(Activity context, File file){
		if (file == null)
			return;
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		//获取文件file的MIME类型
		String type = getMIMEType(file);
		//设置intent的data和Type属性。
		intent.setDataAndType(Uri.fromFile(file), type);
		//跳转
		try{context.startActivity(intent);
		}catch (Exception e){
			UIHelper.ToastMessage(context,"本地暂不支持查看此文件，请下载相关应用后重新查看！");
		}

	}

	/***根据文件后缀回去MIME类型****/

	private static String getMIMEType(File file) {
		String type="*/*";
		String fName = file.getName();
		//获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if(dotIndex < 0){
			return type;
		}
		/* 获取文件的后缀名*/
		String end=fName.substring(dotIndex,fName.length()).toLowerCase();
		if(end=="")return type;
		//在MIME和文件类型的匹配表中找到对应的MIME类型。
		for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
			if(end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}
	private static final String[][] MIME_MapTable = {
			// {后缀名，MIME类型}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{".dwg","application/acad"},
			{".dxf","application/dxf"},
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" },
			{ ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
			{ ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" }, { "", "*/*" }
	};
}

package com.lib.picturecontrol.album;

import android.database.Cursor;
import android.provider.MediaStore;

import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.FileUtils;
import com.lib.picturecontrol.tools.ImageUtilsEx;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地相册打开的helper Created by linjizong on 15/6/11.
 */
public class LocalImageHelper {
    private static LocalImageHelper instance;

    private static int maxNum = 3;

    public static void setMaxNum(int num) {
        maxNum = num;
    }

    public static int getMax() {
        return maxNum;
    }

    private final BaseApp context;

    final List<LocalFile> checkedItems = new ArrayList<LocalFile>();

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    // 当前选中得图片个数
    private int currentSize;
    // 拍照时指定保存图片的路径
    private String cameraImgPath;

    public void setCameraImgPath(String cameraImgPath) {
        this.cameraImgPath = cameraImgPath;
    }

    public String getCameraImgPath() {
        if (null == cameraImgPath) {
            return "";
        }
        return cameraImgPath;
    }

    // 大图遍历字段
    private static final String[] STORE_IMAGES = {MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION};

    // 小图遍历字段
    private static final String[] THUMBNAIL_STORE_IMAGE = {
            MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.DATA};

    List<LocalFile> paths = new ArrayList<LocalFile>();

    Map<String, List<LocalFile>> folders = new HashMap<String, List<LocalFile>>();

    private LocalImageHelper(BaseApp context) {
        this.context = context;
    }

    public Map<String, List<LocalFile>> getFolderMap() {
        return folders;
    }

    public static LocalImageHelper getInstance() {
        // 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
        if (instance == null) {
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (LocalImageHelper.class) {
                //未初始化，则初始instance变量
                if (instance == null) {
                    instance = new LocalImageHelper(BaseApp.getInstance());
                }
            }
        }
        return instance;
    }

    public static void init(BaseApp context) {
        instance = new LocalImageHelper(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.initImage();
            }
        }).start();
    }

    public boolean isInited() {
        return paths.size() > 0;
    }

    public List<LocalFile> getCheckedItems() {
        return checkedItems;
    }

    private boolean resultOk;

    public boolean isResultOk() {
        return resultOk;
    }

    public void setResultOk(boolean ok) {
        resultOk = ok;
    }

    private boolean isRunning = false;

    public void initImage() {
        try {

//		if (isRunning)
//			return;
//		isRunning = true;
//		if (isInited())
//			return;
            // 获取大图的游标
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // 大图URI
                    STORE_IMAGES, // 字段
                    null, // No where clause
                    null, // No where clause
                    MediaStore.Images.Media.DATE_TAKEN + " DESC"); // 根据时间升序
            if (cursor == null)
                return;
            folders.clear();
            paths.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);// 大图ID
                String path = cursor.getString(1);// 大图路径
                File file = new File(path);
                // 判断大图是否存在
                if (file.exists()) {
                    // 小图URI
                    //String thumbUri = getThumbnail(id, path);
                    // 获取大图URI
                    String uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            .buildUpon().appendPath(Integer.toString(id)).build()
                            .toString();
                /*if (StringUtils.isEmpty(uri))
					continue;*/
				/*if (StringUtils.isEmpty(thumbUri))
					thumbUri = uri;*/
                    String thumbUri = uri;
                    // 获取目录名
                    String folder = file.getParentFile().getName();

                    LocalFile localFile = new LocalFile();
                    localFile.setOriginalUri(uri);
                    localFile.setThumbnailUri(thumbUri);
                    localFile.setFileSize(FileUtils.getFileSizes(file));
                    int degree = cursor.getInt(2);
                    if (degree != 0) {
                        degree = degree + 180;
                    }
                    localFile.setOrientation(360 - degree);

                    paths.add(localFile);
                    // 判断文件夹是否已经存在
                    if (folders.containsKey(folder)) {
                        folders.get(folder).add(localFile);
                    } else {
                        List<LocalFile> files = new ArrayList<LocalFile>();
                        files.add(localFile);
                        folders.put(folder, files);
                    }
                }
            }
            folders.put("所有图片", paths);
            cursor.close();
            isRunning = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //就段代码太耗时，对每张图片都进行查找缩略图，每张图片都再次遍历手机存储
    private String getThumbnail(int id, String path) {
        // 获取大图的缩略图
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                THUMBNAIL_STORE_IMAGE,
                MediaStore.Images.Thumbnails.IMAGE_ID + " = ?",
                new String[]{id + ""}, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int thumId = cursor.getInt(0);
            String uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI
                    .buildUpon().appendPath(Integer.toString(thumId)).build()
                    .toString();
            cursor.close();
            return uri;
        }
        cursor.close();
        return null;
    }

    public String getCompressedGraphPath(int id, String path) throws Exception {
        // 获取大图URI
        String uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                .buildUpon().appendPath(Integer.toString(id)).build()
                .toString();
        File file = new File(uri + "CompressedGraphPath");
        ImageUtilsEx.compressUploadPic(uri.toString(), file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    public List<LocalFile> getFolder(String folder) {
        return folders.get(folder);
    }

    public void clearList() {
        checkedItems.clear();
    }

    public void clear() {
        checkedItems.clear();
        currentSize = (0);
        String foloder = FileUtils.getCachePath() + "/PostPicture/";
        File savedir = new File(foloder);
        if (savedir.exists()) {
            deleteFile(savedir);
        }
    }

    public void deleteFile(File file) {

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        deleteFile(files[i]);
                    }
                }
            }
        } else {
        }
    }

    public static class LocalFile {
        private String originalUri;// 原图URI

        private String thumbnailUri;// 缩略图URI


        private int orientation;// 图片旋转角度

        private long fileSize;// 图片大小

        public long getFileSize() {
            return fileSize;
        }

        public void setFileSize(long fileSize) {
            this.fileSize = fileSize;
        }

        public String getThumbnailUri() {
            return thumbnailUri;
        }

        public void setThumbnailUri(String thumbnailUri) {
            this.thumbnailUri = thumbnailUri;
        }

        public String getOriginalUri() {
            return originalUri;
        }

        public void setOriginalUri(String originalUri) {
            this.originalUri = originalUri;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int exifOrientation) {
            orientation = exifOrientation;
        }


    }

    public void refreshData() {
        isRunning = false;
        paths.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.initImage();
            }
        }).start();
    }

}

/**
 *
 */
package com.lib.common.baseUtils;


/**
 * 定义Intent传参中的Key
 */
public interface IntentKey {




    int REQ_UPLAOD = 101;// 拍照
    int REQ_DELECT = 103;// 裁剪


    /**
     * 点击小图看大图的本地路径或者网络地址
     **/
    String CHECK_FILE_PATH = "check_file_path";



    String LOCAL_FOLDER_NAME = "local_folder_name";//跳转到相册页的文件夹名称


    int IM_SHARE = 10086;

    //上一次打开app的时间
    int REQUEST_CODE_GETIMAGE_BYCAMERA =1234 ;
    int REQUEST_CODE_GETIMAGE_BYLIST = 12345;
    int REQUEST_CODE_GETIMAGE_BYCROP =12333 ;
    int REQ_CODE_GRAFFITI = 1223;
}

package com.lib.common.baseUtils.permssion;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class PermissionCheckUtils {

    public static Boolean requestPermissions(Activity activity, int requestCode, String... permissions) {
        if (!isOverMarshmallow()) {
            // 版本低，不需要动态请求权限
            return true;
        }
        // 得到还没申请过的权限
        String[] deniedPermissions = findDeniedPermissions(activity, permissions);
        if (deniedPermissions.length > 0) {
            LogUtil.w(Constants.TAG,"需要获取的权限是:"+deniedPermissions[0]);
            ActivityCompat.requestPermissions(activity,
                    deniedPermissions,
                    Constants.REQUEST_CODE);
            return false;
        }else{
            return true;
        }
    }

    // 判断权限集合
    private static String[] findDeniedPermissions(Activity activity, String... permissions) {
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (lacksPermission(activity, permission)) {
                permissionList.add(permission);
            }
        }

        String[] strArr = new String[permissionList.size()];
        return permissionList.toArray(strArr);
    }


    // 判断是否缺少权限
    public static boolean lacksPermission(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    private static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}

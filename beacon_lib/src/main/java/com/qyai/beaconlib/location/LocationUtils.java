package com.qyai.beaconlib.location;


import com.lib.common.base.BaseApp;
import com.qyai.beaconlib.debug.MyApplication;
import com.qyai.beaconlib.R;

/**
 * @author hf
 * @time 2020/5/27 16:31
 * @des 位置转换
 */
public class LocationUtils {
    public static final boolean isQYAlgorithm = true;
    /**
     * 转变高博坐标z为我们自己的地图坐标z
     *
     * @param buildId 高博的buildId
     * @param range   我们的楼层（b1,b2,b3）
     * @return
     */
    public static double changeZByRange(String buildId, String range) {
        if (BuildingIdConstants.PSOP_WHGJ_BGS.equals(buildId)) {
            if ("b3".equals(range)) {
                return -20;
            } else if ("b2".equals(range)) {
                return -10;
            } else if ("b1".equals(range)) {
                return 0;
            }
        } else if (BuildingIdConstants.PSOP_WHGJ_WJDD.equals(buildId)) {
            if ("b3".equals(range)) {
                return -20;
            } else if ("b2".equals(range)) {
                return -10;
            } else if ("b1".equals(range)) {
                return 0;
            }
        } else if (BuildingIdConstants.PSOP_WHGJ_CMT.equals(buildId)) {
            if ("b3".equals(range)) {
                return -20;
            } else if ("b2".equals(range)) {
                return -10;
            } else if ("b1".equals(range)) {
                return 0;
            }
        } else if (BuildingIdConstants.PSOP_WHGJ_QSL.equals(buildId)) {
            if ("b3".equals(range)) {
                return -20;
            } else if ("b2".equals(range)) {
                return -10;
            } else if ("b1".equals(range)) {
                return 0;
            }
        } else if (BuildingIdConstants.PSOP_WHGJ_SYL.equals(buildId)) {
            if ("b3".equals(range)) {
                return -20;
            } else if ("b2".equals(range)) {
                return -10;
            } else if ("b1".equals(range)) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * 根据高博的buildId获取地铁站名称
     *
     * @param buildId 高博的buildId
     * @return
     */
    public static String getMetroName(String buildId) {
        if (BuildingIdConstants.PSOP_WHGJ_YBY.equals(buildId)) {
            return BaseApp.getIns().getString(R.string.text_metro_yby);
        } else if (BuildingIdConstants.PSOP_WHGJ_CMT.equals(buildId)) {
            return BaseApp.getIns().getString(R.string.text_metro_cmt);
        } else if (BuildingIdConstants.PSOP_WHGJ_WJDD.equals(buildId)) {
            return BaseApp.getIns().getString(R.string.text_metro_wjdd);
        } else if (BuildingIdConstants.PSOP_WHGJ_QSL.equals(buildId)) {
            return BaseApp.getIns().getString(R.string.text_metro_qsl);
        } else if (BuildingIdConstants.PSOP_WHGJ_SYL.equals(buildId)) {
            return BaseApp.getIns().getString(R.string.text_metro_syl);
        } else if (BuildingIdConstants.PSOP_WHGJ_BGS.equals(buildId)) {
            return BaseApp.getIns().getString(R.string.text_metro_office);
        } else {
            return BaseApp.getIns().getString(R.string.text_metro_wjdd);
        }
    }

}

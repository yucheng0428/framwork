package com.qyai.watch_app.message.bean;

import com.lib.common.baseUtils.SPValueUtil;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeUtil {

    public static MenuTreeUtil instance;
    public List<JusClassResult.DataBean> list = new ArrayList<JusClassResult.DataBean>();

    public static MenuTreeUtil getInstance(){
        if(instance==null){
            instance=new MenuTreeUtil();
        }
        return instance;
    }

    /**
     * 列表树状结构 排列
     * @param menu 数据源
     * @param id  父id
     * @param level  层级
     * @return
     */
    public List<JusClassResult.DataBean> changeTreeList(List<JusClassResult.DataBean> menu, String id,int level) {
        List<JusClassResult.DataBean> lists = new ArrayList<JusClassResult.DataBean>();
        if (!SPValueUtil.isEmpty(id)) {
            id = "0";
        }
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getPid().equals(id)) {
                menu.get(i).leve=level;
                menu.get(i).setChildList(changeTreeList(menu, menu.get(i).getId(),menu.get(i).leve+1));
                lists.add(menu.get(i));
            }
        }
        return lists;
    }

    /**
     * 展开树状列表
     * @param dataBeans
     * @return
     */
    public List<JusClassResult.DataBean> openTreeList(List<JusClassResult.DataBean> dataBeans){
        List<JusClassResult.DataBean> lists = new ArrayList<JusClassResult.DataBean>();
        for (JusClassResult.DataBean bean : dataBeans) {
            if (bean.getChildList() != null && bean.getChildList().size() > 0) {
                if (!bean.openList) {
                    bean.openList = true;
                    lists.add(bean);
                    lists.addAll(openTreeList(bean.getChildList()));
                }
            }else {
                lists.add(bean);
            }
        }
        return lists;
    }

//    public List<JusClassResult.DataBean> geDatatList(List<JusClassResult.DataBean> dataBeans){
//        List<JusClassResult.DataBean> lists = new ArrayList<JusClassResult.DataBean>();
//        for (JusClassResult.DataBean bean : dataBeans) {
//            if (bean.getChildList() != null && bean.getChildList().size() > 0) {
//                if (!bean.openList) {
//                    bean.openList = true;
//                    lists.addAll(geDatatList(bean.getChildList()));
//                }
//            }
//        }
//        return lists;
//    }
}


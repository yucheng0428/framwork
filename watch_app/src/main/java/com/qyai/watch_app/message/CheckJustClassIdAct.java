package com.qyai.watch_app.message;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.message.bean.JusClassResult;
import com.qyai.watch_app.message.bean.MenuTreeUtil;
import com.qyai.watch_app.utils.OnlyUserUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class CheckJustClassIdAct extends BaseHeadActivity {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    CheckJustClassIdAdapter adapter;
    JusClassResult.DataBean dataBean;
    @Override
    public int layoutId() {
        return R.layout.act_check_classid_view;
    }

    @Override
    protected void initUIData() {
        setTvTitle("请选择辖区");
        hideTvRight(View.VISIBLE);
        setTvRightMsg("确定");
        adapter=new CheckJustClassIdAdapter(mActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        if (SPValueUtil.isEmpty(SPValueUtil.getStringValue(mActivity, Common.JUSCLASSRESULT))) {
            dataBean= JSONObject.parseObject(SPValueUtil.getStringValue(mActivity, Common.JUSCLASSRESULT), JusClassResult.DataBean.class);
        }
        adapter.setRecItemClick(new RecyclerItemCallback<JusClassResult.DataBean, CheckJustClassIdAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, JusClassResult.DataBean model, int tag, CheckJustClassIdAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                adapter.resetcheck();
                model.checkItem=true;
                SPValueUtil.saveStringValue(mActivity, Common.JUSCLASSRESULT, JSONObject.toJSONString(model));
                adapter.notifyDataSetChanged();
            }
        });
        getAllClass();
    }

    //获取辖区分类
    public void getAllClass() {
        HashMap req = new HashMap();
        HttpServiec.getInstance().getFlowbleData(100, HttpReq.getInstence().getIp() + "jurisdiction/queryAllClass/userClass", req, new OnHttpCallBack<JusClassResult>() {
            @Override
            public void onSuccessful(int id, JusClassResult result) {
                if (result != null && result.getData() != null && result.getCode().equals("000000")) {
                    if (result.getData().size() > 0) {
                        if (SPValueUtil.isEmpty(SPValueUtil.getStringValue(mActivity, Common.JUSCLASSRESULT))) {
                            JusClassResult.DataBean dataBean = JSONObject.parseObject(SPValueUtil.getStringValue(mActivity, Common.JUSCLASSRESULT), JusClassResult.DataBean.class);
                            for(JusClassResult.DataBean bean:result.getData()){
                                if(dataBean.getId().equals(bean.getId())){
                                    bean.checkItem=true;
                                }
                            }
                        }
                        List<JusClassResult.DataBean> treeList =MenuTreeUtil.getInstance().changeTreeList(result.getData(),result.getData().get(0).getPid(),1);
                        adapter.setData(MenuTreeUtil.getInstance().openTreeList(treeList));
                    }
                } else if (result != null && result.getCode().equals(Common.CATCH_CODE)) {
                    OnlyUserUtils.catchOut(mActivity, result.getMsg());
                }
            }

            @Override
            public void onFaild(int id, JusClassResult o, String err) {

            }
        }, JusClassResult.class);
    }

    @Override
    public void setOnClickTvRight() {
       setResult(Common.CHECK_SUCCESSFUL);
       mActivity.finish();
    }
}

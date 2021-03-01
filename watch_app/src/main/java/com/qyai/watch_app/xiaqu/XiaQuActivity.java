package com.qyai.watch_app.xiaqu;

import android.content.Intent;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.PhoneUtils;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.dialog.LookBigPictureDialog;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.contacts.ContactsDialog;
import com.qyai.watch_app.contacts.bean.ContactsInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 辖区守护
 */
public class XiaQuActivity extends BaseHeadActivity {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    XiaQuAdapter adapter;
    static int classId;


    @Override
    public int layoutId() {
        return R.layout.activity_xiaqu;
    }

    @Override
    protected void initUIData() {
        setTvTitle("辖区守护");
        classId = getIntent().getIntExtra("classId",0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new XiaQuAdapter(mActivity);
        recyclerView.setAdapter(adapter);
//        adapter.setData(XiaQuInfo.getContactsInfoList());
        adapter.setRecItemClick(new RecyclerItemCallback<XiaQuResult.DataBean.ListBean, XiaQuAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, XiaQuResult.DataBean.ListBean model, int tag, XiaQuAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    case 1:
                        //1是点击整item;
                        Intent intent = new Intent(mActivity, PersonDetailActivity.class);
                        intent.putExtra("personId", model.getPersonId());
                        startActivity(intent);
                        break;
                    case 2:
                        //2是点击打电话;
                        ContactsDialog dialog=new ContactsDialog(mActivity, changeList(model));
                        dialog.show();
//                        PhoneUtils.dialPhone(mActivity, model.getPhone());
                        break;
                    case 3:
                        ARouter.getInstance().build("/maplib/GMapActivity").navigation();
                        //2是点击打电话;
                        break;
                    case 4:
                        String imageUrl="";
                        if(model.getSex().equals("1")){
                            imageUrl="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1289666865,1142307765&fm=26&gp=0.jpg";
                        }else {
                            imageUrl= Constants.imageUrl;
                        }
                        new LookBigPictureDialog(mActivity, imageUrl).show();
                        break;
                }
            }
        });
        getUserList();

    }

    public List<ContactsInfo> changeList(XiaQuResult.DataBean.ListBean dtoBean){
        List<ContactsInfo> infos=new ArrayList<>();
        ContactsInfo contactsInfo=new ContactsInfo("当前联系人",dtoBean.getPhone(),dtoBean.getName());
        infos.add(contactsInfo);
        if(dtoBean!=null){
            String arr[]=dtoBean.getEmergencyMan().split(",");
            String phone[]=dtoBean.getEmergencyPhone().split(",");
            if(arr.length>1&&phone.length>1&&arr.length==phone.length){
                for(int i=0;i<arr.length;i++){
                    switch (i){
                        case 0:
                            infos.add(new ContactsInfo("第一紧急联系人",phone[i],arr[i]));
                            break;
                        case 1:
                            infos.add(new ContactsInfo("第二紧急联系人",phone[i],arr[i]));
                            break;
                        case 2:
                            infos.add(new ContactsInfo("第三紧急联系人",phone[i],arr[i]));
                            break;
                        case 3:
                            infos.add(new ContactsInfo("第四紧急联系人",phone[i],arr[i]));
                            break;
                        default:
                            infos.add(new ContactsInfo("第"+(i+1)+"紧急联系人",phone[i],arr[i]));
                            break;
                    }
                }
            }
        }
        return infos;
    }
    /**
     * 获取辖区人员信息
     * /jurisdiction/selectUserJurisdictionList
     * 查询辖区人员列表
     */
    public void getUserList() {
        HashMap req = new HashMap();
        req.put("limit", 10);
        req.put("page", 1);
        List<String> count=new ArrayList<>();
        count.add(classId+"");
        req.put("personClassId",count);
        HttpServiec.getInstance().postFlowableData(100, HttpReq.getInstence().getIp() + "jurisdiction/selectUserJurisdictionList", req, new OnHttpCallBack<XiaQuResult>() {
            @Override
            public void onSuccessful(int id, XiaQuResult result) {
                if(result!=null&&result.getCode().equals("000000")){
                    if(result.getData().getList()!=null&&result.getData().getList().size()>0){
                        adapter.setData(result.getData().getList());
                    }
                }
            }

            @Override
            public void onFaild(int id, XiaQuResult o, String err) {

            }
        }, XiaQuResult.class);
    }
}
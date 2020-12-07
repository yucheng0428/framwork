package com.qyai.watch_app.postion;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.SPValueUtil;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.postion.bean.EnclosureInfo;


import butterknife.BindView;
import butterknife.OnClick;

public class PostionActivity extends BaseHeadActivity {

    @BindView(R2.id.mapView)
    TextView mapView;
    @BindView(R2.id.iv_postion)
    ImageView iv_postion;
    @BindView(R2.id.layout_enclosure)
    View layout_enclosure;
    boolean isEdit = false;
    boolean isShowing = false;
    @BindView(R2.id.et_enclosuer)
    EditText et_enclosuer;
    @BindView(R2.id.tv_adress)
    TextView tv_adress;
    @BindView(R2.id.tv_seekBar_value)
    TextView tv_seekBar_value;
    @BindView(R2.id.seekBar)
    SeekBar seekBar;
    @BindView(R2.id.layout_Search)
    LinearLayout layout_Search;
    @BindView(R2.id.ed_search)
    EditText ed_search;
    @BindView(R2.id.ryl_search)
    RecyclerView ryl_search;
    AddEnclosureAdapter addEnclosureAdapter;
    int number = 0;
    @Override
    public int layoutId() {
        return R.layout.activity_postion;
    }


    @Override
    protected void initUIData() {
        setTvTitle(getIntent().getStringExtra("title"));
        setTvRightMsg("编辑");
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        ryl_search.setLayoutManager(layoutManager);
        addEnclosureAdapter = new AddEnclosureAdapter(mActivity);
        addEnclosureAdapter.setData(EnclosureInfo.getEnclosureInfoList());
        ryl_search.setAdapter(addEnclosureAdapter);
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (SPValueUtil.isEmpty(s.toString())) {
                    ryl_search.setVisibility(View.VISIBLE);
                } else {
                    ryl_search.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //改变数值
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                number = progress;
//                tv_seekBar_value.setText(progress + "");
                if(number==10){
                    tv_seekBar_value.setText(10 + "米");
                }else if(number==20){
                    tv_seekBar_value.setText(50 + "米");
                }else if(number==30){
                    tv_seekBar_value.setText(100 + "米");
                }else if(number==40){
                    tv_seekBar_value.setText(200 + "米");
                }else if(number==50){
                    tv_seekBar_value.setText(500 + "米");
                }else if(number==60){
                    tv_seekBar_value.setText(800 + "米");
                }else if(number==70){
                    tv_seekBar_value.setText(1000 + "米");
                }else if(number==80){
                    tv_seekBar_value.setText(1500 + "米");
                }else if(number==90){
                    tv_seekBar_value.setText(1800 + "米");
                }else if(number==100){
                    tv_seekBar_value.setText(2000 + "米");
                }
                //如果需要设置最小值，如下 (注：上面设置最大值现对应要减10：seekBar.setMax(70-10);)
//                progress += 10;
//                textView.setText(progress + "");
            }

            //开始拖动
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            //停止拖动
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void setOnClickTvRight() {
        super.setOnClickTvRight();
        if (!isEdit) {
            setTvRightMsg("保存");
            layout_Search.setVisibility(View.VISIBLE);
            et_enclosuer.setEnabled(true);
            et_enclosuer.setHint("请输入围栏名称");
            et_enclosuer.setText("");
            isEdit = true;
        } else {
            if (ryl_search.getVisibility() == View.VISIBLE) {
                tv_adress.setText(addEnclosureAdapter.getDataSource().get(0).getAdress());
                ryl_search.setVisibility(View.GONE);
                ed_search.setText("");
            } else {
                setTvRightMsg("编辑");
                layout_Search.setVisibility(View.GONE);
                isEdit = false;
                layout_enclosure.setVisibility(View.GONE);
                hideTvRight(View.GONE);
                isShowing = false;
            }
        }
    }

    @OnClick({R2.id.iv_postion})
    public void onClick(View view) {
        if (view.getId() == R.id.iv_postion) {
            if (!isShowing) {
                layout_enclosure.setVisibility(View.VISIBLE);
                et_enclosuer.setEnabled(false);
                hideTvRight(View.VISIBLE);
                isShowing = true;
            }
        }
    }
}
package com.lib.picturecontrol.album;


import android.os.Bundle;
import android.telephony.mbms.FileInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.base.BaseActivity;
import com.lib.picturecontrol.R;
import com.lib.picturecontrol.R2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 附件选择
 *
 * @author yucheng
 */
public class LocalFileAct extends BaseActivity implements OnClickListener, OnItemClickListener {

    @BindView(R2.id.ivRight)
    ImageView ivRight;
    @BindView(R2.id.tvTitle)
    TextView tvTitle;
    @BindView(R2.id.ivLeft)
    ImageView ivLeft;
    @BindView(R2.id.rlv_doc)
    RecyclerView rlv_doc;
    @BindView(R2.id.tv_all_size)
    TextView tv_all_size;
    @BindView(R2.id.tv_send)
    TextView tv_send;
    @BindView(R2.id.main_layout)
    LinearLayout main_layout;
    @BindView(R2.id.anim_layout)
    LinearLayout anim_layout;
    @BindView(R2.id.progress_bar)
    ImageView progress;
    public File[] files;
    public static List<FileInfo> fileInfos ;





    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivLeft) {
            onBackPressed();
        } else if (id == R.id.tv_send) {// if (FileDao.queryAll().size()>)
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    }




    public void updateSizAndCount() {
//        List<FileInfo> mList = FileDao.queryAll();
//        if (mList.size() == 0) {
//            tv_send.setBackgroundResource(R.drawable.shape_attach_file_bt_send);
//            tv_send.setTextColor(getResources().getColor(R.color.labor_text_low));
//            tv_all_size.setText(getString(R.string.tx_file_size, "0B"));
//        } else {
//            tv_send.setBackgroundResource(R.drawable.shape_attach_file_bt_send_blue);
//            tv_send.setTextColor(getResources().getColor(R.color.white));
//            long count = 0L;
//            for (int i = 0; i < mList.size(); i++) {
//                count = count + mList.get(i).getFileSize();
//            }
//            tv_all_size.setText(getString(R.string.tx_file_size, FileUtil.FormetFileSize(count)));
//        }
//        tv_send.setText(getString(R.string.tx_file_send, "" + mList.size()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.localfile_view;
    }

    @Override
    protected void initUIData(Bundle bundle) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_loading);
        progress.startAnimation(animation);
        tvTitle.setText("选择文件");
        ivLeft.setOnClickListener(this);
        tv_send.setOnClickListener(this);

        rlv_doc.setLayoutManager(new LinearLayoutManager(mActivity));
//        mExpandableItemAdapter = new ExpandableItemAdapter(mEntityArrayList, false);
//        rlv_doc.setAdapter(mExpandableItemAdapter);
//        FileDao.deleteAll1();
        if (fileInfos==null){
            fileInfos = new ArrayList<>();
//            ReadDOCFile();
        }else {
           /* String className=getIntent().getStringExtra("ClassName");
            if (className.equals(staticClassName)){

            }else {

            }*/
            for (FileInfo i:fileInfos){
//                i.setIsCheck(false);
            }
            complete();
        }
        updateSizAndCount();
    }

    private void complete(){
//        if (fileInfos.size() > 0) {
//            SubItem wordItem = new SubItem("WORD");
//            SubItem excelItem = new SubItem("EXCEL");
//            SubItem pdfItem = new SubItem("PDF");
//            SubItem PPTItem = new SubItem("PPT");
//            SubItem textItem = new SubItem("TXT");
//            for (int j = 0; j < fileInfos.size(); j++) {
//                if (FileUtils.checkSuffix(fileInfos.get(j).getFilePath(), new String[]{"doc","docx","dot","dotx","docm","dotm","xml"})) {
//                    wordItem.addSubItem(fileInfos.get(j));
//                } else if (FileUtils.checkSuffix(fileInfos.get(j).getFilePath(), new String[]{"xls","xlsx","xlsm","xltx","xltm","xlsb","xlam"})) {
//                    excelItem.addSubItem(fileInfos.get(j));
//                } else if (FileUtils.checkSuffix(fileInfos.get(j).getFilePath(), new String[]{"pdf"})) {
//                    pdfItem.addSubItem(fileInfos.get(j));
//                } else if (FileUtils.checkSuffix(fileInfos.get(j).getFilePath(), new String[]{"ppt","pptx","pps","ppsx"})) {
//                    PPTItem.addSubItem(fileInfos.get(j));
//                } else if (FileUtils.checkSuffix(fileInfos.get(j).getFilePath(), new String[]{"txt"})) {
//                    textItem.addSubItem(fileInfos.get(j));
//                }
//            }
//
//            mEntityArrayList.add(wordItem);
//            mEntityArrayList.add(excelItem);
//            mEntityArrayList.add(pdfItem);
//            mEntityArrayList.add(PPTItem);
//            mEntityArrayList.add(textItem);
//            mExpandableItemAdapter.setNewData(mEntityArrayList);
//            mExpandableItemAdapter.notifyDataSetChanged();
//
//            progress.clearAnimation();
//            anim_layout.setVisibility(View.GONE);
//            main_layout.setVisibility(View.VISIBLE);
//        } else {
//            Toast.makeText(mActivity, "sorry,没有读取到文件!", Toast.LENGTH_LONG).show();
//        }
    }
}

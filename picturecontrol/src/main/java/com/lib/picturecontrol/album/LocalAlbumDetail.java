package com.lib.picturecontrol.album;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.lib.common.base.BaseActivity;
import com.lib.common.baseUtils.IntentKey;
import com.lib.picturecontrol.R;
import com.lib.picturecontrol.R2;
import com.lib.picturecontrol.views.MatrixImageView;
import com.lib.picturecontrol.cameralibary.LookPictureAct;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author linjizong
 * @Description:欢迎页测试
 * @date 2015-4-11
 */
public class LocalAlbumDetail extends BaseActivity implements MatrixImageView.OnSingleTapListener, OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ImageSize imageSize;
    @BindView(R2.id.gridview)
    GridView gridView;

    @BindView(R2.id.album_title)
    TextView title;// 标题

    @BindView(R2.id.album_title_bar)
    View titleBar;// 标题栏

    @BindView(R2.id.pagerview)
    View pagerContainer;// 图片显示部分

    @BindView(R2.id.album_finish)
    TextView finish;

    @BindView(R2.id.header_finish)
    TextView headerFinish;

    @BindView(R2.id.albumviewpager)
    AlbumViewPager viewpager;// 大图显示pager

    @BindView(R2.id.header_bar_photo_count)
    TextView mCountView;

    @BindView(R2.id.header_bar_photo_back)
    ImageView mBackView;

    @BindView(R2.id.album_item_header_bar)
    View headerBar;

    @BindView(R2.id.checkbox)
    CheckBox checkBox;

    String folder;

    List<LocalImageHelper.LocalFile> currentFolder = null;

    LocalImageHelper helper = LocalImageHelper.getInstance();

    List<LocalImageHelper.LocalFile> checkedItems;

    private void showViewPager(int index) {
        pagerContainer.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
        findViewById(R.id.album_title_bar).setVisibility(View.GONE);
        viewpager.setAdapter(viewpager.new LocalViewPagerAdapter(currentFolder));
        viewpager.setCurrentItem(index);
        mCountView.setText((index + 1) + "/" + currentFolder.size());
        // 第一次载入第一张图时，需要手动修改
        if (index == 0) {
            checkBox.setTag(currentFolder.get(index));
            checkBox.setChecked(checkedItems.contains(currentFolder.get(index)));
        }
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation((float) 0.9, 1, (float) 0.9, 1, pagerContainer.getWidth() / 2, pagerContainer.getHeight() / 2);
        scaleAnimation.setDuration(300);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0.1, 1);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        pagerContainer.startAnimation(set);
    }

    private void hideViewPager() {
        pagerContainer.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        findViewById(R.id.album_title_bar).setVisibility(View.VISIBLE);
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, (float) 0.9, 1, (float) 0.9, pagerContainer.getWidth() / 2, pagerContainer.getHeight() / 2);
        scaleAnimation.setDuration(200);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        pagerContainer.startAnimation(set);
        ((BaseAdapter) gridView.getAdapter()).notifyDataSetChanged();
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (viewpager.getAdapter() != null) {
                String text = (position + 1) + "/" + viewpager.getAdapter().getCount();
                mCountView.setText(text);
                checkBox.setTag(currentFolder.get(position));
                checkBox.setChecked(checkedItems.contains(currentFolder.get(position)));
            } else {
                mCountView.setText("0/0");
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    @Override
    public void onSingleTap() {
        if (headerBar.getVisibility() == View.VISIBLE) {
            AlphaAnimation animation = new AlphaAnimation(1, 0);
            animation.setDuration(300);
            headerBar.startAnimation(animation);
            headerBar.setVisibility(View.GONE);
        } else {
            headerBar.setVisibility(View.VISIBLE);
            AlphaAnimation animation = new AlphaAnimation(0, 1);
            animation.setDuration(300);
            headerBar.startAnimation(animation);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.header_bar_photo_back) {
            hideViewPager();
        } else if (id == R.id.album_finish || id == R.id.header_finish) {
            LocalImageHelper.getInstance().setResultOk(true);
            setResult(RESULT_OK);
            finish();
        } else if (id == R.id.album_back) {
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        if (pagerContainer.getVisibility() == View.VISIBLE) {
            hideViewPager();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!b) {
            if (checkedItems.contains(compoundButton.getTag())) {
                checkedItems.remove(compoundButton.getTag());
            }
        } else {
            if (!checkedItems.contains(compoundButton.getTag())) {
                if (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize() >= LocalImageHelper.getMax()) {
                    Toast.makeText(this, "最多选择" + LocalImageHelper.getMax() + "张图片", Toast.LENGTH_SHORT).show();
                    compoundButton.setChecked(false);
                    return;
                }
                checkedItems.add((LocalImageHelper.LocalFile) compoundButton.getTag());
            }
        }
        if (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize() > 0) {
            finish.setText("完成(" + (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize()) + "/" + LocalImageHelper.getMax() + ")");
            finish.setEnabled(true);
            headerFinish.setText("完成(" + (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize()) + "/" + LocalImageHelper.getMax() + ")");
            headerFinish.setEnabled(true);
        } else {
            finish.setText("完成");
            finish.setEnabled(false);
            headerFinish.setText("完成");
            headerFinish.setEnabled(false);
        }
    }

    public class MyAdapter extends BaseAdapter {
        private Context m_context;

        private LayoutInflater miInflater;

        List<LocalImageHelper.LocalFile> paths;

        DisplayImageOptions options;

        public MyAdapter(Context context, List<LocalImageHelper.LocalFile> paths) {
            m_context = context;
            this.paths = paths;
            imageSize = new ImageSize(160, 160);
            options =
                    new DisplayImageOptions.Builder().cacheInMemory(true)
                            .cacheOnDisk(false)
                            .showImageForEmptyUri(R.drawable.icon_loadings)
                            .showImageOnFail(R.drawable.icon_loadings)
                            .showImageOnLoading(R.drawable.icon_loadings)
                            .bitmapConfig(Bitmap.Config.RGB_565)
//                    .setImageSize(new ImageSize(((YunApp)context.getApplicationContext()).getQuarterWidth(), 0))
                            .displayer(new SimpleBitmapDisplayer())
                            .build();
        }

        @Override
        public int getCount() {
            return paths.size();
        }

        @Override
        public LocalImageHelper.LocalFile getItem(int i) {
            return paths.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            Log.d("takePhoto", "LocalAlbumDetail position--" + i);
            if (convertView == null || convertView.getTag() == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.simple_list_item, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                viewHolder.checkBox.setOnCheckedChangeListener(LocalAlbumDetail.this);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            LocalImageHelper.LocalFile localFile = paths.get(i);
            // FrescoLoader.getInstance().localDisplay(localFile.getThumbnailUri(), imageView, options);
            ImageLoader.getInstance().displayImage(localFile.getThumbnailUri(), new ImageViewAware(viewHolder.imageView), options, imageSize, null, null);
            viewHolder.imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    ArrayList<String> path = new ArrayList<>();
                    path.add(paths.get(i).getOriginalUri());
                    Intent intent = new Intent(LocalAlbumDetail.this, LookPictureAct.class);
                    intent.putExtra(IntentKey.CHECK_FILE_PATH, path);
                    startActivity(intent);
                }
            });
            viewHolder.checkBox.setTag(localFile);
            viewHolder.checkBox.setChecked(checkedItems.contains(localFile));
            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;

            CheckBox checkBox;
        }
    }


    @Override
    protected int layoutId() {
        return R.layout.local_album_detail;
    }

    @Override
    protected void initUIData(Bundle bundle) {
        {
            if (!LocalImageHelper.getInstance().isInited()) {
                finish();
                return;
            }

            viewpager.setOnPageChangeListener(pageChangeListener);
            viewpager.setOnSingleTapListener(this);
            checkBox.setOnCheckedChangeListener(this);
            mBackView.setOnClickListener(this);
            finish.setOnClickListener(this);
            headerFinish.setOnClickListener(this);
            findViewById(R.id.album_back).setOnClickListener(this);

            folder = getIntent().getExtras().getString(IntentKey.LOCAL_FOLDER_NAME);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 防止停留在本界面时切换到桌面，导致应用被回收，图片数组被清空，在此处做一个初始化处理
                    helper.initImage();
                    // 获取该文件夹下地所有文件
                    final List<LocalImageHelper.LocalFile> folders = helper.getFolder(folder);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (folders != null) {
                                currentFolder = folders;
                                MyAdapter adapter = new MyAdapter(LocalAlbumDetail.this, folders);
                                title.setText(folder);
                                gridView.setAdapter(adapter);
                                // 设置当前选中数量
                                if (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize() > 0) {
                                    finish.setText("完成(" + (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize()) + "/" + LocalImageHelper.getMax() + ")");
                                    finish.setEnabled(true);
                                    headerFinish.setText("完成(" + (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize()) + "/" + LocalImageHelper.getMax() + ")");
                                    headerFinish.setEnabled(true);
                                } else {
                                    finish.setText("完成");
                                    // finish.setEnabled(false);
                                    headerFinish.setText("完成");
                                    // headerFinish.setEnabled(false);
                                }
                            }
                        }
                    });
                }
            }).start();
            checkedItems = helper.getCheckedItems();
            Log.d("picNum", checkedItems.size() + "");
            LocalImageHelper.getInstance().setResultOk(false);
        }
    }
}
package com.lib.picturecontrol.cameralibary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.IntentKey;
import com.lib.picturecontrol.R;
import com.lib.picturecontrol.R2;

import java.util.List;

import butterknife.BindView;

public class LookPictureAct extends BaseHeadActivity {
    @BindView(R2.id.viewpager)
    ViewPager pager;
    List<String> picPath;

    @Override
    public int layoutId() {
        return R.layout.activity_look_picture;
    }

    @Override
    protected void initUIData() {
        setTvTitle("查看图片");
        picPath = getIntent().getStringArrayListExtra(IntentKey.CHECK_FILE_PATH);
        if (picPath != null && picPath.size() > 0) {
            pager.setAdapter(new MyPagerAdapter(this, picPath));
        }
    }


    public class MyPagerAdapter extends PagerAdapter {
        private Context mContext;
        private List<String> mData;

        public MyPagerAdapter(Context context, List<String> list) {
            mContext = context;
            mData = list;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.item_page, null);
            ImageView tv = (ImageView) view.findViewById(R.id.imageView);
            Glide.with(mContext).load(mData.get(position)).into(tv);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container,position,object); 这一句要删除，否则报错
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}

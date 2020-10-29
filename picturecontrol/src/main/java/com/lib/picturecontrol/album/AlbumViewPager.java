package com.lib.picturecontrol.album;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lib.picturecontrol.R;
import com.lib.picturecontrol.views.MatrixImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.List;

/**
 * @ClassName: AlbumViewPager
 * @Description: 自定义viewpager 优化了事件拦截
 * @author LinJ
 * @date 2015-1-9 下午5:33:33
 */
public class AlbumViewPager extends ViewPager implements MatrixImageView.OnMovingListener
{
    public final static String TAG = "AlbumViewPager";
    
    /** 当前子控件是否处理拖动状态 */
    private boolean mChildIsBeingDragged = false;
    
    private static final DisplayImageOptions options =
            new DisplayImageOptions.Builder().cacheInMemory(true)
            .cacheOnDisc(true)
                .displayer(new SimpleBitmapDisplayer())
                .build();
    
    /** 界面单击事件 用以显示和隐藏菜单栏 */
    private MatrixImageView.OnSingleTapListener onSingleTapListener;
    

    /** 播放按钮点击事件 */
    public AlbumViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0)
    {
        if (mChildIsBeingDragged)
            return false;
        return super.onInterceptTouchEvent(arg0);
    }
    
    @Override
    public void startDrag()
    {
        mChildIsBeingDragged = true;
    }
    
    @Override
    public void stopDrag()
    {
        mChildIsBeingDragged = false;
    }
    
    public void setOnSingleTapListener(MatrixImageView.OnSingleTapListener onSingleTapListener)
    {
        this.onSingleTapListener = onSingleTapListener;
    }
    
    public class ViewPagerAdapter extends PagerAdapter
    {
        private List<String> paths;// 大图地址 如果为网络图片 则为大图url
        
        public ViewPagerAdapter(List<String> paths)
        {
            this.paths = paths;
        }
        
        @Override
        public int getCount()
        {
            return paths.size();
        }
        
        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position)
        {
            // 注意，这里不可以加inflate的时候直接添加到viewGroup下，而需要用addView重新添加
            // 因为直接加到viewGroup下会导致返回的view为viewGroup
            View imageLayout = inflate(getContext(), R.layout.item_album_pager, null);
            viewGroup.addView(imageLayout);
            assert imageLayout != null;
            MatrixImageView imageView = (MatrixImageView)imageLayout.findViewById(R.id.image);
            imageView.setOnMovingListener(AlbumViewPager.this);
            imageView.setOnSingleTapListener(onSingleTapListener);
            String path = paths.get(position);
            ImageLoader.getInstance().displayImage(path, imageView, options);
            return imageLayout;
        }
        
        @Override
        public int getItemPosition(Object object)
        {
            // 在notifyDataSetChanged时返回None，重新绘制
            return POSITION_NONE;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int arg1, Object object)
        {
            ((ViewPager)container).removeView((View)object);
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }
        
    }
    
    public class LocalViewPagerAdapter extends PagerAdapter
    {
        private List<LocalImageHelper.LocalFile> paths;// 大图地址 如果为网络图片 则为大图url
        
        public LocalViewPagerAdapter(List<LocalImageHelper.LocalFile> paths)
        {
            this.paths = paths;
        }
        
        @Override
        public int getCount()
        {
            return paths.size();
        }
        
        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position)
        {
            // 注意，这里不可以加inflate的时候直接添加到viewGroup下，而需要用addView重新添加
            // 因为直接加到viewGroup下会导致返回的view为viewGroup
            View imageLayout = inflate(getContext(), R.layout.item_album_pager, null);
            viewGroup.addView(imageLayout);
            assert imageLayout != null;
            MatrixImageView imageView = (MatrixImageView)imageLayout.findViewById(R.id.image);
            imageView.setOnMovingListener(AlbumViewPager.this);
            imageView.setOnSingleTapListener(onSingleTapListener);
            LocalImageHelper.LocalFile path = paths.get(position);
            ImageLoader.getInstance().displayImage(path.getOriginalUri(), imageView);
            
            return imageLayout;
        }
        
        @Override
        public int getItemPosition(Object object)
        {
            // 在notifyDataSetChanged时返回None，重新绘制
            return POSITION_NONE;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int arg1, Object object)
        {
            ((ViewPager)container).removeView((View)object);
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }
    }
    
}
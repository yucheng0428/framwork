package com.lib.common.widgt;
/**
 * 
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lib.common.R;


@SuppressLint({ "ResourceAsColor", "DrawAllocation" })
public class IndexView extends View {

	private OnTouchIndexListener onTouchIndexListener;

	private String[] mIndex = null;/** 索引*/

	private int mSelect = -1;/** 当前选中项 */

	private boolean mIndexBg = true;/** 索引背景 */

//	private Bitmap mBitmap;
	
	public IndexView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public IndexView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public IndexView(Context context) {
		super(context);
		
		this.setBackgroundResource(R.color.white);
	}

	public void setIndex(String[] index) {
		mIndex = index;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (mIndex == null) {
//			throw new NullPointerException("索引为空");
			return;
		}

		if (mIndexBg) {
			canvas.drawColor(Color.WHITE);
		}

		final int indexSize = mIndex.length;
		final int height = getHeight();
		final int width = getWidth();
		final int singleHeight = height / indexSize;
		for (int i = 0; i < indexSize; i++) {
			Paint paint = new Paint();
			paint.setColor(getResources().getColor(R.color.color_333333));
//			paint.setColor((R.color.bg_blue));
			paint.setTypeface(Typeface.DEFAULT);//索引字母字体
			paint.setAntiAlias(true);
			paint.setTextSize(this.getResources().getDimensionPixelSize(
					R.dimen.sp_10));
			if (i == mSelect) {
				paint.setColor(getResources().getColor(R.color.color_0093dd));
				paint.setFakeBoldText(true);
			}

			float xPos = width / 2 - paint.measureText(mIndex[i]) / 2;
			float yPos = 10 + singleHeight * i + singleHeight / 2;
			canvas.drawText(mIndex[i], xPos, yPos, paint);
			paint.reset();
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (mIndex == null) {
//			throw new NullPointerException("索引为空");
			return true;
		}
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = mSelect;
		final OnTouchIndexListener listener = onTouchIndexListener;
		int indexSize = mIndex.length;
		final int c = (int) (y / getHeight() * indexSize);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mIndexBg = true;
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < indexSize) {
					listener.onTouchingIndexChanged(TouchStatus.ACTION_DOWN, mIndex[c]);
					mSelect = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < indexSize) {
					listener.onTouchingIndexChanged(TouchStatus.ACTION_MOVE, mIndex[c]);
					mSelect = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			mIndexBg = false;
			listener.onTouchingIndexChanged(TouchStatus.ACTION_UP, "");
			mSelect = -1;
			invalidate();
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchIndexChangedListener(
			OnTouchIndexListener onTouchingLetterChangedListener) {
		this.onTouchIndexListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchIndexListener {
		public void onTouchingIndexChanged(TouchStatus touchStatus, String s);
	}
	
	public enum TouchStatus {
		ACTION_DOWN, ACTION_MOVE, ACTION_UP
	}
}

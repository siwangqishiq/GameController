package com.xinlan.controller.component;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.xinlan.controller.MainView;
import com.xinlan.controller.R;

public class Controller {
	private MainView context;
	private int padLeft = 30;
	private int padTop = 30;

	private Bitmap mBottomBmp, mBarBmp;
	private float bottom_x, bottom_y, bottom_width, bottom_height;
	private float bar_x, bar_y, bar_width, bar_height;
	private Rect srcBottomRect, srcBarRect;
	private RectF dstBottomRect, dstBarRect;

	public Controller(MainView context) {
		this.context = context;
		mBottomBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.controller_bottom);// 载入摇杆底座图片
		mBarBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.controller_top);// 载入摇杆图片

		bottom_width = bottom_height = MainView.screenW / 3;// 设定底座高度与宽度
		bar_width = bar_height = bottom_width / 2;// 摇杆宽高 与底座按1:2的比例

		// 源图像矩形
		srcBottomRect = new Rect(0, 0, mBottomBmp.getWidth(),
				mBottomBmp.getHeight());
		srcBarRect = new Rect(0, 0, mBarBmp.getWidth(), mBarBmp.getHeight());
		// 目标矩形
		dstBottomRect = new RectF(padLeft, padTop, padLeft + bottom_width,
				padTop + bottom_height);
		bar_x = bottom_x = dstBottomRect.centerX();
		bar_y = bottom_y = dstBottomRect.centerY();
		dstBarRect = new RectF(bar_x - bar_width / 2, bar_y - bar_height / 2,
				bar_x + bar_width / 2, bar_y + bar_height / 2);
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(mBottomBmp, srcBottomRect, dstBottomRect, null);
		canvas.drawBitmap(mBarBmp, srcBarRect, dstBarRect, null);
	}
	
}// end class

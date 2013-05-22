package com.xinlan.controller.component;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.xinlan.controller.MainView;
import com.xinlan.controller.R;

public class Controller {
	private MainView context;
	private Box mBox;

	private int padLeft = 30;
	private int padTop = 60;
	private boolean isBarFocus = false;
	private float down_x, down_y;

	private Bitmap mBottomBmp, mBarBmp;
	private float bottom_x, bottom_y, bottom_width, bottom_height;
	private float bar_x, bar_y, bar_width, bar_height;
	private Rect srcBottomRect, srcBarRect;
	private RectF dstBottomRect, dstBarRect;
	private RectF barBoundRect;// bar活动范围
	private float barRadius, bottomRadius;

	public Controller(MainView context) {
		this.context = context;
		mBox = context.mBox;
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
		float bar_width_pad = bar_width - 10;
		barBoundRect = new RectF(bar_x - bar_width_pad / 2, bar_y
				- bar_width_pad / 2, bar_x + bar_width_pad / 2, bar_y
				+ bar_width_pad / 2);
		barRadius = dstBarRect.width() / 2;
		bottomRadius = dstBarRect.width() / 2;

	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(mBottomBmp, srcBottomRect, dstBottomRect, null);
		dstBarRect.set(bar_x - bar_width / 2, bar_y - bar_height / 2, bar_x
				+ bar_width / 2, bar_y + bar_height / 2);
		canvas.drawBitmap(mBarBmp, srcBarRect, dstBarRect, null);
	}

	public void logic() {
		if (isBarFocus) {
			float delta_y = bar_y - bottom_y;
			float delta_x = bar_x - bottom_x;
			if (mBox == null) {
				mBox = context.mBox;
			}
			if (delta_y > -delta_x && delta_y > delta_x) {//下方向
				mBox.box_y += mBox.dy;
			}
			if (delta_y < -(delta_x) && delta_y > delta_x) {//左方向
				mBox.box_x -= mBox.dx;
			}
			if (delta_y < -delta_x && delta_y < delta_x) {//上方向
				mBox.box_y -= mBox.dy;
			}
			if (delta_y > -delta_x && delta_y < delta_x) {//	右
				mBox.box_x += mBox.dx;
			}
			//斜边情况
			if (delta_y == delta_x) {
				if (delta_x > 0) {
					mBox.box_x += mBox.dx;
					mBox.box_y += mBox.dy;
				} else if (delta_x < 0){
					mBox.box_x -= mBox.dx;
					mBox.box_y -= mBox.dy;
				}
			}
			
			if (delta_y == -delta_x) {
				if (delta_x > 0) {
					mBox.box_x += mBox.dx;
					mBox.box_y -= mBox.dy;
				} else if (delta_x < 0) {
					mBox.box_x -= mBox.dx;
					mBox.box_y += mBox.dy;
				}
			}
		}
	}

	/**
	 * 触摸事件处理
	 * 
	 * @param event
	 */
	public void onTouch(MotionEvent event) {
		float x1 = event.getX(0);
		float y1 = event.getY(0);

		switch (MotionEventCompat.getActionMasked(event)) {
		case MotionEvent.ACTION_DOWN:
			if (MathUtils.isInCircle(x1, y1, dstBarRect.centerX(),
					dstBarRect.centerY(), barRadius)) {
				isBarFocus = true;
				down_x = x1;
				down_y = y1;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isBarFocus) {
				bar_x = bottom_x + (x1 - down_x);
				bar_y = bottom_y + (y1 - down_y);
				if (bar_x > barBoundRect.right)
					bar_x = barBoundRect.right;
				if (bar_x < barBoundRect.left)
					bar_x = barBoundRect.left;
				if (bar_y < barBoundRect.top)
					bar_y = barBoundRect.top;
				if (bar_y > barBoundRect.bottom)
					bar_y = barBoundRect.bottom;
			}
			break;
		case MotionEvent.ACTION_UP:
			isBarFocus = false;
			bar_x = bottom_x;
			bar_y = bottom_y;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:// 第二只手指按下
			break;
		case MotionEvent.ACTION_POINTER_UP:// 第二只手指抬起
			break;
		}// end switch
	}
}// end class

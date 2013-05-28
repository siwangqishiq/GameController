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
	public static final int TOUCH_NONE = 0;// 没有接触
	public static final int ONLY_DIRECTION = 1;// 仅操作方向键
	public static final int ONLY_BUTTON = 2;// 仅按钮键
	public static final int BOTH_DIRECTION_BOTTON = 3;// 方向及按钮键

	public static int status = TOUCH_NONE;

	private MainView context;
	private Box mBox;

	private int padLeft = 20;
	private int padTop = 30;

	private boolean isBarFocus = false;
	private float down_x, down_y;

	private Bitmap mBottomBmp, mBarBmp;
	private float bottom_x, bottom_y, bottom_width, bottom_height;
	private float bar_x, bar_y, bar_width, bar_height;
	private Rect srcBottomRect, srcBarRect;
	private RectF dstBottomRect, dstBarRect;
	private RectF barBoundRect;// bar活动范围
	private float barRadius, bottomRadius;
	private float middleScreen;

	private boolean isAPressed = false;
	private boolean isBPressed = false;
	private int button_pad_right = 20;
	private int button_pad_bottom = 30;
	private Bitmap mButtonBmp;
	private float buttonWidth;
	private float buttonA_x, buttonA_y, buttonB_x, buttonB_y;
	private float buttonA_left, buttonA_top, buttonB_left, buttonB_top;
	private Rect srcButtonA, srcButtonB;
	private RectF dstButtonA, dstButtonB;
	private Rect srcButtonPressedA, srcButtonPressedB;
	private float buttonRadius;

	public Controller(MainView context) {
		this.context = context;
		mBox = context.mBox;
		middleScreen = MainView.screenW / 2;
		mBottomBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.controller_bottom);// 载入摇杆底座图片
		mBarBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.controller_top);// 载入摇杆图片
		mButtonBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.cotroller_button);// 按钮
		int cube_len = MainView.screenW > MainView.screenH ? MainView.screenH
				: MainView.screenW;
		bottom_width = bottom_height = cube_len / 3;// 设定底座高度与宽度
		buttonWidth = bar_width = bar_height = bottom_width / 2;// 摇杆宽高
																// 与底座按1:2的比例

		// 源图像矩形
		srcBottomRect = new Rect(0, 0, mBottomBmp.getWidth(),
				mBottomBmp.getHeight());
		srcBarRect = new Rect(0, 0, mBarBmp.getWidth(), mBarBmp.getHeight());
		padTop += MainView.screenH / 2;
		// 目标矩形
		dstBottomRect = new RectF(padLeft, padTop, padLeft + bottom_width,
				padTop + bottom_height);
		bar_x = bottom_x = dstBottomRect.centerX();
		bar_y = bottom_y = dstBottomRect.centerY();
		dstBarRect = new RectF(bar_x - bar_width / 2, bar_y - bar_height / 2,
				bar_x + bar_width / 2, bar_y + bar_height / 2);
		float bar_width_pad = bar_width - 20;
		barBoundRect = new RectF(bar_x - bar_width_pad / 2, bar_y
				- bar_width_pad / 2, bar_x + bar_width_pad / 2, bar_y
				+ bar_width_pad / 2);
		barRadius = dstBarRect.width() / 2;
		bottomRadius = dstBarRect.width() / 2;

		srcButtonA = new Rect(0, 0, mButtonBmp.getWidth() / 2,
				mButtonBmp.getHeight());
		srcButtonB = new Rect(0, 0, mButtonBmp.getWidth() / 2,
				mButtonBmp.getHeight());
		srcButtonPressedA = new Rect(mButtonBmp.getWidth() / 2, 0,
				mButtonBmp.getWidth(), mButtonBmp.getHeight());
		srcButtonPressedB = new Rect(mButtonBmp.getWidth() / 2, 0,
				mButtonBmp.getWidth(), mButtonBmp.getHeight());
		buttonRadius = buttonWidth / 2;
		buttonB_x = MainView.screenW - (buttonRadius + button_pad_right);
		buttonB_y = MainView.screenH - (buttonRadius + button_pad_bottom);
		buttonB_left = buttonB_x - buttonRadius;
		buttonB_top = buttonB_y - buttonRadius;
		dstButtonB = new RectF(buttonB_left, buttonB_top, buttonB_left
				+ buttonWidth, buttonB_top + buttonWidth);
		buttonA_x = buttonB_left - buttonRadius;
		buttonA_y = buttonB_top - buttonRadius;
		buttonA_left = buttonA_x - buttonRadius;
		buttonA_top = buttonA_y - buttonRadius;
		dstButtonA = new RectF(buttonA_left, buttonA_top, buttonA_left
				+ buttonWidth, buttonA_top + buttonWidth);
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(mBottomBmp, srcBottomRect, dstBottomRect, null);
		dstBarRect.set(bar_x - bar_width / 2, bar_y - bar_height / 2, bar_x
				+ bar_width / 2, bar_y + bar_height / 2);
		canvas.drawBitmap(mBarBmp, srcBarRect, dstBarRect, null);

		if (isAPressed) {
			canvas.drawBitmap(mButtonBmp, srcButtonPressedA, dstButtonA, null);
		} else {
			canvas.drawBitmap(mButtonBmp, srcButtonA, dstButtonA, null);
		}
		if (isBPressed) {
			canvas.drawBitmap(mButtonBmp, srcButtonPressedB, dstButtonB, null);
		} else {
			canvas.drawBitmap(mButtonBmp, srcButtonB, dstButtonB, null);
		}
	}

	public void logic() {
		if (mBox == null) {
			mBox = context.mBox;
		}
		if (isBarFocus) {
			float delta_y = bar_y - bottom_y;
			float delta_x = bar_x - bottom_x;
			if (delta_y > -delta_x && delta_y > delta_x) {// 下方向
				mBox.box_y += mBox.dy;
			}
			if (delta_y < -(delta_x) && delta_y > delta_x) {// 左方向
				mBox.box_x -= mBox.dx;
			}
			if (delta_y < -delta_x && delta_y < delta_x) {// 上方向
				mBox.box_y -= mBox.dy;
			}
			if (delta_y > -delta_x && delta_y < delta_x) {// 右
				mBox.box_x += mBox.dx;
			}
			// 斜边情况
			if (delta_y == delta_x) {
				if (delta_x > 0) {
					mBox.box_x += mBox.dx;
					mBox.box_y += mBox.dy;
				} else if (delta_x < 0) {
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
		if (isAPressed) {
			mBox.isAlpha = true;
		}

		if (isBPressed) {
			mBox.isAlpha = false;
		}
	}

	/**
	 * 
	 * @param event
	 */
	public void onTouch(MotionEvent event) {
		switch (status) {
		case TOUCH_NONE:
			noneTouch(event);
			break;
		case ONLY_DIRECTION:
			onlyDirectionTouch(event);
			break;
		case ONLY_BUTTON:
			onlyButtonTouch(event);
			break;
		case BOTH_DIRECTION_BOTTON:
			bothDirectionAndButtonTouch(event);
			break;
		}
	}

	private void bothDirectionAndButtonTouch(MotionEvent event) {
		float x1 = event.getX(0);
		float y1 = event.getY(0);

	}

	private void onlyDirectionTouch(MotionEvent event) {
		float x1 = event.getX(0);
		float y1 = event.getY(0);

	}

	private void onlyButtonTouch(MotionEvent event) {
		float x1 = event.getX(0);
		float y1 = event.getY(0);

	}

	private void noneTouch(MotionEvent event) {
		int count= event.getPointerCount();
		float x1 = event.getX(0);
		float y1 = event.getY(0);
		
//		System.out.println("x1="+x1+",y1="+y1+"   x2="+x2+",y2="+y2);
		switch (MotionEventCompat.getActionMasked(event)) {
		case MotionEvent.ACTION_DOWN:
//			if (x1 < middleScreen) {
//				status = ONLY_DIRECTION;
//			} else {
//				status = ONLY_BUTTON;
//			}
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_POINTER_DOWN:// 第二只手指按下
			break;
		case MotionEvent.ACTION_POINTER_UP:// 第二只手指抬起
			break;
		}// end switch
	}

	/**
	 * 触摸事件处理
	 * 
	 * @param event
	 */
	public void onTouchs(MotionEvent event) {
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
			if (MathUtils
					.isInCircle(x1, y1, buttonA_x, buttonA_y, buttonRadius)) {
				isAPressed = true;
				break;
			}

			if (MathUtils
					.isInCircle(x1, y1, buttonB_x, buttonB_y, buttonRadius)) {
				isBPressed = true;
				break;
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

			isAPressed = false;
			isBPressed = false;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:// 第二只手指按下

			break;
		case MotionEvent.ACTION_POINTER_UP:// 第二只手指抬起

			break;
		}// end switch
	}
}// end class

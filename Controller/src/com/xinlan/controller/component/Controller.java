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
				R.drawable.controller_bottom);// ����ҡ�˵���ͼƬ
		mBarBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.controller_top);// ����ҡ��ͼƬ

		bottom_width = bottom_height = MainView.screenW / 3;// �趨�����߶�����
		bar_width = bar_height = bottom_width / 2;// ҡ�˿�� �������1:2�ı���

		// Դͼ�����
		srcBottomRect = new Rect(0, 0, mBottomBmp.getWidth(),
				mBottomBmp.getHeight());
		srcBarRect = new Rect(0, 0, mBarBmp.getWidth(), mBarBmp.getHeight());
		// Ŀ�����
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

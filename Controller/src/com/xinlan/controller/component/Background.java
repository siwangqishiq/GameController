package com.xinlan.controller.component;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import com.xinlan.controller.MainView;
import com.xinlan.controller.R;

public class Background {
	private MainView context;
	private Bitmap mBgBmp;

	private float speed = 1f;
	private Rect srcRect1, srcRect2;
	private RectF dstRect1, dstRect2;
	private float x1, y1, x2, y2;

	public Background(MainView context) {
		this.context = context;
		mBgBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.stars_bg);
		x1 = 0;
		srcRect1 = new Rect(0, 0, mBgBmp.getWidth(), mBgBmp.getHeight());
		dstRect1 = new RectF(x1, 0, x1 + MainView.screenW, MainView.screenH);
		x2 = MainView.screenW - 30;
		srcRect2 = srcRect1;
		dstRect2 = new RectF(x2, 0, x2 + MainView.screenW, MainView.screenH);
	}

	public void draw(Canvas canvas) {
		dstRect1.set(x1, 0, x1 + MainView.screenW, MainView.screenH);
		dstRect2.set(x2, 0, x2 + MainView.screenW, MainView.screenH);
		canvas.drawBitmap(mBgBmp, srcRect1, dstRect1, null);
		canvas.drawBitmap(mBgBmp, srcRect2, dstRect2, null);
	}

	public void logic() {
		x1 -= speed;
		if (x1 < -MainView.screenW + 30) {
			x1 = MainView.screenW - 30;
		}
		x2 -= speed;
		if (x2 < -MainView.screenW + 30) {
			x2 = MainView.screenW - 30;
		}
	}
}// end class

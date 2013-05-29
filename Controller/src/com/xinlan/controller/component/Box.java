package com.xinlan.controller.component;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.xinlan.controller.MainView;
import com.xinlan.controller.R;

public class Box extends BaseSprite {
	private MainView context;
	private Bitmap mBoxBmp;
	private Rect srcRect;
	private RectF dstRect;
	private Paint paint;
	public float speed = 3f;

	public Box(MainView context) {
		super(context);
		this.context = context;
		mBoxBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher);
		x = 100;
		y = 100;
		width = 50;
		height = 50;
		srcRect = new Rect(0, 0, mBoxBmp.getWidth(), mBoxBmp.getHeight());
		dstRect = new RectF(x, y, x + width, y + height);
	}

	public void draw(Canvas canvas) {
		dstRect.set(x, y, x + width, y + height);
		canvas.drawBitmap(mBoxBmp, srcRect, dstRect, paint);
	}
	
	public void logic() {
		super.logic();
		x += dx;
		y += dy;
		//Æ«ÒÆÁ¿¹éÁã
		dx = 0;
		dy = 0;
	}
}// end class

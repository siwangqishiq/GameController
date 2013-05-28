package com.xinlan.controller.component;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.xinlan.controller.MainView;
import com.xinlan.controller.R;

public class Box {
	private MainView context;
	public float dx, dy;
	public float width, height;

	private Bitmap mBoxBmp;
	public float box_x, box_y;
	
	private Rect srcRect;
	private RectF dstRect;
	
	public boolean isAlpha=false;
	
	private int alpha=255;
	private Paint paint;
	
	public Box(MainView context) {
		this.context = context;
		mBoxBmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher);
		box_x = MainView.screenW / 2;
		box_y = MainView.screenH / 2;

		width = mBoxBmp.getWidth();
		height = mBoxBmp.getHeight();

		srcRect = new Rect(0, 0, mBoxBmp.getWidth(), mBoxBmp.getHeight());
		dstRect = new RectF(box_x, box_y, box_x + width, box_y + height);

		dx = dy = 2f;
		paint =new Paint();
		paint.setAlpha(alpha);
	}

	public void draw(Canvas canvas) {
		canvas.save();
		dstRect.set(box_x, box_y, box_x + width, box_y + height);
		canvas.drawBitmap(mBoxBmp, srcRect, dstRect, paint);
		canvas.restore();
	}
}// end class

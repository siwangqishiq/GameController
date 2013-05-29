package com.xinlan.controller;

import com.xinlan.controller.component.Background;
import com.xinlan.controller.component.Box;
import com.xinlan.controller.component.Controller;
import com.xinlan.controller.component.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements Callback, Runnable {
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	private Context context;
	public static int screenW, screenH;
	private Resources res = this.getResources();

	public static int GAME_STATE = 1;

	// public Background mBackground;
	public static final int X_MAX = 40;
	public static final int Y_MAX = 24;
	
	public Map mMap;
	public Box mBox;
	public Controller mController;

	public MainView(Context context) {
		super(context);
		this.context = context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		this.setKeepScreenOn(true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		init();
		flag = true;
		th = new Thread(this);
		th.start();
	}

	/**
	 */
	public void init() {
		mMap = new Map(this);
		mBox = new Box(this);
		// mBackground = new Background(this);
		mController = new Controller(this);
	}

	public void draw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				switch (GAME_STATE) {
				case 1:
					canvas.drawColor(Color.WHITE);
					// mBackground.draw(canvas);
					mMap.draw(canvas);
					mBox.draw(canvas);
					mController.draw(canvas);
					break;
				}
			}// end if
		} catch (Exception e) {
		} finally {
			if (canvas != null) {
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	public void logic() {
		// mBackground.logic();
		mBox.logic();
		mController.logic();
	}

	public void run() {
		while (flag) {
			long start = System.currentTimeMillis();
			logic();
			draw();
			long end = System.currentTimeMillis();
//			 System.out.println(end - start);
			try {
				if (end - start < 5) {
					Thread.sleep(5 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}// end while
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mController.onTouch(event);
		return true;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
}// end class

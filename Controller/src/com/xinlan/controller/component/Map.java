package com.xinlan.controller.component;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.graphics.RectF;

import com.xinlan.controller.MainView;
import com.xinlan.controller.R;
import com.xinlan.controller.data.MapConstants;

public class Map {
	public static final int MAP_CUBE1 = 1;
	public static final int MAP_CUBE2 = 2;

	private MainView context;
	private byte[][] data = MapConstants.map1;

	private Bitmap mapBitmap;

	private Bitmap mCube1Bitmap, mCube2Bitmap;
	private Rect srcCube1, srcCube2;
	private RectF dstCube;
	private final int X_MAX = MainView.X_MAX;
	private final int Y_MAX = MainView.Y_MAX;

	public float cube_width;
	public float cube_height;
	private Rect srcMap;
	private RectF dstMap;

	public Map(MainView context) {
		this.context = context;
		cube_width = MainView.screenW / X_MAX;
		cube_height = MainView.screenH / Y_MAX;

		mCube1Bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.map_cube1);
		mCube2Bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.map_cube2);
		srcCube1 = new Rect(0, 0, mCube1Bitmap.getWidth(),
				mCube1Bitmap.getHeight());
		srcCube2 = new Rect(0, 0, mCube2Bitmap.getWidth(),
				mCube2Bitmap.getHeight());
		dstCube = new RectF();
		mapBitmap = initMapBitmap();
		srcMap = new Rect(0, 0, mapBitmap.getWidth(), mapBitmap.getHeight());
		dstMap = new RectF(0, 0, MainView.screenW, MainView.screenH);
	}

	private Bitmap initMapBitmap() {
		Bitmap retBitmap = Bitmap.createBitmap(MainView.screenW,
				MainView.screenH, Config.ARGB_8888);
		Canvas canvas = new Canvas(retBitmap);
		float x = 0;
		float y = 0;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				switch (data[i][j]) {
				case MAP_CUBE1:
					dstCube.set(x, y, x + cube_width, y + cube_height);
					canvas.drawBitmap(mCube1Bitmap, srcCube1, dstCube, null);
					break;
				case MAP_CUBE2:
					dstCube.set(x, y, x + cube_width, y + cube_height);
					canvas.drawBitmap(mCube2Bitmap, srcCube2, dstCube, null);
					break;
				}// end switch
				x += cube_width;
			}// end for j
			y += cube_height;
			x = 0.0f;
		}// end for i
		return retBitmap;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(mapBitmap, srcMap, dstMap, null);
	}
	
	public void logic(){
		
	}
}// end class

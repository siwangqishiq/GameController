package com.xinlan.controller.component;

import com.xinlan.controller.MainView;
import com.xinlan.controller.data.MapConstants;

public class BaseSprite {
	public static final float gravityDx = 5.0f;

	public final int SCREENW = MainView.screenW;
	public final int SCREENH = MainView.screenH;
	public final int X_MAX = MainView.X_MAX;
	public final int Y_MAX = MainView.Y_MAX;
	public byte mapData[][] = MapConstants.map1;
	protected float x, y;
	protected float dx, dy;
	protected float width, height;
	public int cube_width;
	public int cube_height;

	private int index_x, index_y;
	private MainView context;

	public BaseSprite(MainView context) {
		this.context = context;
		cube_width = MainView.screenW / X_MAX;
		cube_height = MainView.screenH / Y_MAX;
	}

	public void logic() {
		index_x = (int) (x) / cube_width;
		index_y = (int) (y) / cube_height;
		if (index_x < 0)
			index_x = 0;
		if (index_x >= Y_MAX)
			index_x = Y_MAX - 1;
		if (index_y < 0)
			index_y = 0;
		if (index_y > X_MAX)
			index_y = X_MAX - 1;
//		System.out.println(index_x + "," + index_y + "-->"
//				+ mapData[index_y][index_x]);
		doGravity();
	}

	private void doGravity() {

	}
}// end class

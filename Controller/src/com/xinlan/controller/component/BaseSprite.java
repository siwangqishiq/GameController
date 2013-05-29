package com.xinlan.controller.component;

import com.xinlan.controller.MainView;

public class BaseSprite {
	public static final float gravityDx = 5.0f;
	
	public final int SCREENW=MainView.screenW;
	public final int SCREENH = MainView.screenH;
	public final int X_MAX = MainView.X_MAX;
	public final int Y_MAX = MainView.Y_MAX;
	protected float x, y;
	protected float dx, dy;
	protected float width, height;

	private int index_x, index_y;

	public void logic() {
		index_x = SCREENW/X_MAX;
		index_y = SCREENH/Y_MAX;
		doGravity();
	}

	private void doGravity(){
		
	}
}// end class

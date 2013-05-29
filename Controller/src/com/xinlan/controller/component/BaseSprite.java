package com.xinlan.controller.component;

public class BaseSprite {
	public static final float gravityDx = 5.0f;
	
	protected float x,y;
	protected float dx,dy;
	protected float width,height;

	private int index_x,index_y;
	
	public void logic(){
		doGravity();
	}
	
	private void doGravity(){
		
	}
}//end class

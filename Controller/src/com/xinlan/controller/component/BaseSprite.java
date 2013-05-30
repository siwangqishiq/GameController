package com.xinlan.controller.component;

import com.xinlan.controller.MainView;
import com.xinlan.controller.data.MapConstants;

public class BaseSprite {
	public static final float gravityDx = 6.0f;

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
	private int bound_x, bound_y;
	private float cube_width_div2, cube_height_div2;

	private int left_index_x, left_index_y;
	private int right_index_x, right_index_y;
	private int left_bottom_index_x, left_bottom_index_y;
	private int right_bottom_index_x, right_bottom_index_y;

	protected boolean isRightCanGo, isLeftCanGo, isUpCanGo, isDownCanGo;

	public BaseSprite(MainView context) {
		this.context = context;
		cube_width = MainView.screenW / X_MAX;
		cube_height = MainView.screenH / Y_MAX;
		cube_width_div2 = cube_width / 2;
		cube_height_div2 = cube_height / 2;
		bound_x = X_MAX - 1;
		bound_y = Y_MAX - 1;
	}

	public void logic() {
		isRightCanGo = isLeftCanGo = isUpCanGo = isDownCanGo = true;
		// 左上索引点
		left_index_x = (int) x / cube_width;
		left_index_y = (int) y / cube_height;
		// 右上索引点
		right_index_x = (int) (x + width) / cube_width;
		right_index_y = left_index_y;
		// 左下索引点
		left_bottom_index_x = left_index_x;
		left_bottom_index_y = (int) (y + height) / cube_height;
		// 右下索引点
		right_bottom_index_x = right_index_x;
		right_bottom_index_y = left_bottom_index_y;

		if (mapData[right_index_y][right_index_x] != 0) {
			// 校准
			isRightCanGo = false;
		}
		if (mapData[right_bottom_index_y][right_bottom_index_x] != 0) {
			isRightCanGo = false;
		}
		if (mapData[left_index_y][left_index_x] != 0) {
			isRightCanGo = false;
		}
		if (mapData[left_bottom_index_y][left_bottom_index_x] != 0) {
			isRightCanGo = false;
		}
		if(!isRightCanGo){
			x-=dx;
			y-=dy;
		}

		// index_x = (int) (x) / cube_width;
		// index_y = (int) (y) / cube_height;
		// if (index_x < 0)
		// index_x = 0;
		// if (index_x >= Y_MAX)
		// index_x = bound_y;
		// if (index_y < 0)
		// index_y = 0;
		// if (index_y >= X_MAX)
		// index_y = bound_x;
		// System.out.println(index_x + "," + index_y + "-->"
		// + mapData[index_y][index_x]);
		// doGravity();

	}

	private void doGravity() {
		if (index_y + 1 >= X_MAX) {

		}
		if (mapData[index_y + 1][index_x] == 0) {
			y += gravityDx;
		}
	}
}// end class

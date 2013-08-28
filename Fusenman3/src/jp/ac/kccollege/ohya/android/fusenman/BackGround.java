package jp.ac.kccollege.ohya.android.fusenman;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class BackGround {

	/** スクロール方向 */
/*	public static enum SCROLL {
		UP, DOWN, LEFT, RIGHT
	};*/

	/** 画面移動ビット数 */
	private int hSpeed = 0;
	
	// right to left scroll tracker for  BG
	private int move_x = 0;

	/** The drawable to use as the far background of the animation canvas */
	private Bitmap bgImage;

	/** コンストラクタ */
	public BackGround(Bitmap myImage, RectF drawRect, int hSpeed) {

		bgImage = Bitmap.createScaledBitmap(myImage,
				(int) drawRect.width(), (int) drawRect.height(), true);
		this.hSpeed = hSpeed;
	}

	/**
	 * 描画
	 * 
	 * @param c
	 */
	public void draw(Canvas canvas) {
		// decrement the far background
		move_x -= hSpeed;

		// calculate the wrap factor for matching image draw
		int new_x = bgImage.getWidth() - (-move_x);

		// if we have scrolled all the way, reset to start
		if (new_x <= 0) {
			move_x = 0;
			// only need one draw
			canvas.drawBitmap(bgImage, move_x, 0, null);

		} else {
			// need to draw original and wrap
			canvas.drawBitmap(bgImage, move_x, 0, null);
			canvas.drawBitmap(bgImage, new_x, 0, null);
		}
	}

}

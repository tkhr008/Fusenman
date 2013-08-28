package jp.ac.kccollege.ohya.android.framework.game2D;

import android.graphics.Canvas;

/**シーンのインターフェイス*/
public interface Scene {
	/**初期化*/
	public void init(GameView view);
	/**開始処理*/
	public void start(GameView view);
	/**プロセス*/
	public void process(GameView view);
	/**描画*/
	public void draw(Canvas canvas);
}
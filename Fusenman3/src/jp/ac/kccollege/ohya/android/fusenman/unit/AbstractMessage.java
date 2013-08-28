package jp.ac.kccollege.ohya.android.fusenman.unit;

import android.graphics.Canvas;
import android.graphics.Paint;
import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman;

/** 画面にメッセージを表示するクラス */
public abstract class AbstractMessage extends AbstractUnit {

	// インスタンス変数
	/** 弾を発射する確率 */
	protected float chanceOfShot = 0.0f;
	protected String message;
	protected Paint paint;
	/**
	 * コンストラクタ
	 * 
	 * @param type
	 *            キャラタイプ
	 * @param _x
	 *            左上のx座標
	 * @param _y
	 *            左上のy座標
	 * @param _w
	 *            キャラの幅
	 * @param _h
	 *            キャラの高さ
	 */
	AbstractMessage(float _w, float _h) {
		super(Fusenman.MESSAGE1,0,0, _w, _h);
		init();// 初期化へ
	}

	/** 初期化 */
	public abstract void init();

	/** 開始処理 */
	public void start(GameView view) {
		status = Status.START;
		// 出現位置の決定
		//char_y = view.getDrawRect().centerY();
		//char_x = view.getDrawRect().right;
		//myImage.setVisible(true, true);// 可視
	}

	/** 物理状態の更新 */
	@Override
	public void process(GameView view) {
		switch (status) {

		case INIT:// 初期化
			start(view);
			break;
			
		case START:// 開始
		case READY:// 準備
			if (waitTime-- <= 0) {
				live();//生存へ
			}
			break;

		case LIVE:// 生存
			if (isOutside(view.getDrawRect())) {
				dead();// 消滅
			}else{
				//現在の位置更新
				char_x += hSpeed;
				offset(char_x,char_y);
			}
			break;

		default:
			break;
		}
	}

	/** 消滅した時 */
	public void dead() {
		status = Status.DEAD;
	}
	
	/** 生存処理 */
	protected void live() {
		status = Status.LIVE;
	}
	
	/**描画処理*/
	public void draw(Canvas c) {
		if (status == Status.DEAD || status == Status.INIT) {
			return;
		}

		c.drawText(message, char_x, char_y, paint);
		// 描画
		//myImage.setBounds((int) left, (int) top, (int) right, (int) bottom);
		//myImage.draw(c);
	}
	
	/** ダメージを受けた時 */
	public void damage() {

	}
}

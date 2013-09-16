package jp.ac.kccollege.ohya.android.fusenman.unit;

import android.graphics.Canvas;
import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;

/** 敵共通設定用の抽象クラス */
public abstract class AbstractEnemy extends AbstractUnit {

	// static変数
	/** 現在の敵数 */
	private static int currentNum = 0;

	// インスタンス変数
	/** 弾を発射する確率 */
	protected float chanceOfShot = 0.0f;

	// staticメソッド
	/** 現在の敵数を返す */
	public static int getCurrentNum() {
		return currentNum;
	}

	/** 確率に従って攻撃のタイミングを決定する */
	protected static boolean shotReady(float chanseOfShot) {
		if (chanseOfShot > Math.random() * 100) {
			return true;
		}
		return false;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param _x
	 *            左上のx座標
	 * @param _y
	 *            左上のy座標
	 * @param _w
	 *            キャラの幅
	 * @param _h
	 *            キャラの高さ
	 */
	protected AbstractEnemy( float _x, float _y, float _w, float _h) {
		super(_x, _y, _w, _h);
	}
	
	/** 初期化 */
	public void init() {
		status = Status.INIT;//初期化
		currentNum++;// 敵チーム数増加
		myImage = images2.get(myType);// 画像の設定
		myImage.setVisible(false, true);// 不可視
		size = Size.M;// サイズリセット
		resize(size);
		alpha = 255;
	}

	/** 開始処理 */
	public void start(GameView view) {
		status = Status.START;
		// 出現位置の決定
		float bottom = view.getDrawRect().bottom;
		float top = view.getDrawRect().top;
		char_y = (int) (Math.random() * (bottom - char_h + top));// ランダム
		char_x = view.getDrawRect().right;

		// 出現のタイミングをずらす(縦位置の割合によって計算）
		waitTime = (int) (Math.random() *(char_y % 5 * 100));
		myImage.setVisible(true, true);// 可視

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
				start(view);// 再スタート
			}
			break;

		case DAMAGE:// ダメージ

			if (life > 0) {// lifeが残っていたら
				live();// 生存処理へ
			} else {
				damage();// ダメージ処理継続
			}
			break;

		default:
			break;
		}
	}

	/** 準備処理 */
	public void ready() {
		status = Status.READY;
		waitTime = 10;
	}

	/** 生存処理 */
	protected void live() {
		if (status == Status.DAMAGE) {
			myImage = images2.get(myType);// 画像のリセット
		}
		status = Status.LIVE;
	}

	/** ダメージを受けた時 */
	public void damage() {
		if (status != Status.DAMAGE) {
			status = Status.DAMAGE;
			myImage = images2.get(CharType.BOMB);// 爆発画像に変更
		}

		if (life-- <= 0) {// ライフが0になったとき
			// 爆発演出ができなくなったとき
			if (!bomb()) {
				dead();// 消滅
			}
		}
	}

	/**
	 * 爆発処理
	 * 
	 * @return true 爆発処理中　false 爆発終了
	 */
	protected boolean bomb() {
		alpha -= 10;
		// 完全に透明になったら爆発終了
		if (alpha <= 0) {
			return false;
		}

		myImage.mutate().setAlpha(alpha);// アルファ値の変更

		return true;
	}

	/** 消滅した時 */
	public void dead() {
		status = Status.DEAD;
		currentNum--;// 敵数減少
	}

	public void draw(Canvas c) {
		if (status == Status.DEAD || status == Status.INIT) {
			return;
		}
		// 描画
		myImage.setBounds((int) left, (int) top, (int) right, (int) bottom);
		myImage.draw(c);
	}
}

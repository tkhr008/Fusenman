package jp.ac.kccollege.ohya.android.fusenman.unit;

import android.graphics.RectF;

/** オブジェクトの領域を管理する抽象クラス */
public abstract class AbstractRect extends RectF {

	//インスタンス変数
	/**オブジェクト x座標*/
	protected float char_x = 0;
	/**オブジェクト y座標*/
	protected float char_y = 0;
	/**オブジェクト 幅*/
	protected float char_w=0;
	/**オブジェクト 高さ*/
	protected float char_h=0;

	/**
	 * コンストラクタ
	 * @param char_x　初期左上x座標
	 * @param char_y　初期左上y座標
	 * @param char_w　オブジェクトの幅
	 * @param char_h　 オブジェクトの高さ           
	 */
	protected AbstractRect(float char_x, float char_y, float char_w, float char_h) {
		//スーパークラス呼び出し
		super(char_x, char_y, char_x + char_w,char_y+ char_h);
		
		//オブジェクトエリアを初期設定
		this.char_x = char_x;
		this.char_y = char_y;
		this.char_w = char_w;
		this.char_h = char_h;
	}

	/**
	 * 当たり判定(円)
	 * @param enemyRect 敵キャラ範囲
	 * @return true:当たり,false:はずれ
	 */
	public boolean judge(RectF enemyRect) {

		// 自分と敵の中心間距離を計算
		float dx = centerX() - enemyRect.centerX();
		float dy = centerY() - enemyRect.centerY();
		float distance = (float) Math.sqrt(dx * dx + dy * dy);

		// 中心間距離が半径の合計より小さかったら当たり。
		if (distance <= ((enemyRect.width() + width()) * 0.5f)) {
			return true;
		}
		return false;
	}

	/**
	 * 当たりエリアを返す
	 * @return RectF 当たりエリア
	 */
/*	public RectF getArea() {
		// 当たりエリアとキャラの位置を統一
		//myRect.offsetTo(char_x, char_y);
		return this;
	}*/

	/**
	 * オブジェクトの左上座標の設定
	 * @param x
	 * @param y
	 */
	public void offset(float x, float y) {
		char_x = x;
		char_y = y;

		// 当たりエリアとキャラの位置を統一
		set(char_x,char_y,char_x+char_w,char_y+char_h);
	}
	
	/**
	 * キャンバスの外にでたか
	 * 
	 * @return true:エリア外　false:エリア内
	 */
	public boolean isOutside(final RectF viewRect) {
		//エリア外か？
		if ( ! intersect(viewRect) ){
			return true;
		}
		return false;
	}

}

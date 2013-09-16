package jp.ac.kccollege.ohya.android.fusenman.unit;

import java.util.HashMap;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;

/** ゲームユニットクラス */
public abstract class AbstractUnit extends AbstractRect {

	// static変数----------------------------------------------------------------
	/**サイズの種類*/
	protected static enum Size {
		SS,S, M, L,LL,XL
	}; 
	/**キャライメージ配列*/
	protected static Drawable[] images;
	protected static HashMap<Enum<CharType>,Drawable> images2;
	
	// インスタンス変数----------------------------------------------------------------
	protected CharType myType=null;
	
	/**自分自身の画像*/
	protected Drawable myImage; 
	 /**サイズ*/
	protected Size size = Size.M;
	/**状態*/
	protected Status status = Status.INIT;
	/**横方向の速度*/
	protected float hSpeed = 0; 
	/**縦方向の速度*/
	protected float vSpeed=0;
	/**ｚ座標 */
	protected float char_z = 0;
	/**キャラのデフォルトの幅*/
	protected float charDef_w=0;
	/**キャラのデフォルトの高さ*/
	protected float charDef_h=0;
	
	 /**キャラタイプ*/
	protected int type=0;
	/**アルファ値*/
	protected int alpha = 255; 
	/**残機*/
	protected int life=1; 
	/**待ち時間*/
	protected int waitTime=0;	
	/**上昇するかどうか*/	
	protected boolean up = true;	
	
	//コンストラクタ----------------------------------------------------------------
	/**
	 * コンストラクタ
	 * @param type キャラタイプ
	 * @param char_x
	 *            　 初期左上x座標
	 * @param char_y
	 *            　初期左上y座標
	 * @param char_w
	 *            　オブジェクトの幅
	 * @param char_h
	 *            オブジェクトの高さ
	 */
	AbstractUnit(int type,float _x, float _y, float _w, float _h){
		super(_x, _y, _w, _h);
		this.type = type;//キャラタイプの設定
		charDef_w = _w;
		charDef_h = _h;
	}
	AbstractUnit(float _x, float _y, float _w, float _h){
		super(_x, _y, _w, _h);
		charDef_w = _w;
		charDef_h = _h;
	}
	//static メソッド----------------------------------------------------------------
	/**ゲームユニットの画像を保持*/
	/*
	public static void setUnitImages(Drawable[] images){
		AbstractUnit.images = images;
	}
	*/
	public static void setUnitImages(HashMap<Enum<CharType>,Drawable> images){
		AbstractUnit.images2 = images;
	}	
	// 抽象メソッド----------------------------------------------------------------
	/** 初期化 */
	public abstract void init();
	/** 開始 */
	public abstract void start(GameView view);	
	/** 物理状態の更新 */
	public abstract void process(GameView view);
	/** 描画 * @param canvas キャンバス */
	public abstract void draw(Canvas canvas);
	/**ダメージ*/
	public abstract void damage();

	// 具象メソッド----------------------------------------------------------------
	/**サイズ変更*/
	protected void resize(Size newSize) {
		if (size == newSize) {return;}//変更なし
		
		float value = 0;
		switch (newSize) {	
		case SS:	value = 0.25f;		break;		
		case S:		value = 0.5f;		break;
		case M:		value = 1.0f;		break;
		case L:		value = 1.5f;		break;
		case LL:	value = 2.0f;		break;
		case XL:	value = 2.5f;		break;
		}
		//新しいサイズで矩形の値を設定
		char_w = charDef_w * value;
		char_h = charDef_h * value;
		size = newSize;// 現在のサイズの更新

	}
	/**サイズによる倍率を取得*/
/*	protected float  getSize() {
		float value=0;
		switch (size) {	
		case SS:	value = 0.25f;		break;		
		case S:		value = 0.5f;		break;
		case M:		value = 1.0f;		break;
		case L:		value = 1.5f;		break;
		case LL:	value = 2.0f;		break;
		case XL:	value = 2.5f;		break;
		}
		return value;

	}*/
	/**サイズアップ*/
	protected void sizeUp(){
		switch(size){
		case SS:	resize(Size.S);break;
		case S:	resize( Size.M);break;
		case M:	resize( Size.L);break;
		case L:	resize( Size.LL);break;
		case LL:	resize( Size.XL);break;
		case XL:	break;
		}
	}
	
	/**サイズダウン*/
	protected void sizeDown(){
		switch(size){
		case XL:	resize( Size.LL);break;
		case LL:	resize( Size.L);break;
		case L:	resize( Size.M);break;
		case M:	resize( Size.S);break;
		case S:	resize( Size.SS);break;
		case SS:	break;
		}
	}
	
	/**ゆらぎを作る
	 * @param speed 縦方向の速度
	 * @param range 縦方向の振れ幅
	 */
	protected void yuragi(float speed,float range){

		if (up){
			vSpeed -= speed;// 減速
		}else{
			vSpeed += speed;// 加速
		}
		char_y += (int) vSpeed;// y方向移動
	
		if (vSpeed > range || vSpeed <= -range){// 上昇の切り替え
			up = !up;
		}
	}
	
	/**自分自身が消滅したか
	 * @return dead 消滅フラグ
	 */
	public boolean isDead(){
		return status.isDead();
	}
	
	/**自分自身がダメージ中かどうか
	 * @return boolean ダメージフラグ
	 */
	public boolean isDamage(){
		return status.isDamage();
	}	
	
	/**自分のタイプを返す*/
	public int getType(){
		return type;
	}
	
	/**自身の状態を返す*/
	public Status getStatus(){
		return status;
	}
	
	/** 攻撃処理 */
	public void attack() {
		if(status.isLive()){
			status = Status.ATTACK;//攻撃
		}
	}
	
	/**方向決定*/
	public void fixDirection(float x, float y){
		double radian = Math.atan2(x - char_x, y- char_y);
		vSpeed = (float)(Math.cos(radian)*vSpeed);
		hSpeed = (float)(Math.sin(radian)*hSpeed);

	}
	
	/**キャラタイプの一致*/
	public boolean equals(CharType type){
		if(myType == null){
			return false;
		}else{
			return myType.equals(type);
		}
	}

}

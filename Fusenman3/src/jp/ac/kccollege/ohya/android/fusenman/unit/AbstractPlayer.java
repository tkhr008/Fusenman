package jp.ac.kccollege.ohya.android.fusenman.unit;

import android.graphics.Canvas;

/**Playerクラス
 * @author Takahiro Ohya
 */
public abstract class AbstractPlayer extends AbstractUnit {

	//static変数
	/**現在の味方数*/
	static int currentNum=0;
	
	//staticメソッド
	/**現在の味方数を返す*/
	public static int getCurrentNum(){
		return currentNum;
	}
	
	/**コンストラクタ
	 * @param type キャラタイプ
	 * @param _x 左上のx座標
	 * @param _y 左上のy座標
	 * @param _w キャラの幅
	 * @param _h キャラの高さ
	 */
	public AbstractPlayer(final int type, float _x, float _y,float _w, float _h){
		super(/*type,*/_x,_y,_w, _h);
		//init();//初期化へ
	}
	public AbstractPlayer(float _x, float _y,float _w, float _h){
		super(_x,_y,_w, _h);
		//init();//初期化へ
	}	
	/**初期化*/
	public void init(){
		status = Status.INIT;//初期化
		currentNum++;//プレイヤーチーム増加
		//myImage = images[type];//画像の設定
		myImage = images2.get(myType);//画像の設定
	}

	/**準備処理*/
	public void ready(){
		status = Status.READY;//準備
	}

	/**生存処理*/
	public void live(){
		status = Status.LIVE;//生存
	}
	
	/**ダメージ処理*/
	public void damage(){
		status = Status.DAMAGE;//ダメージ中
		life--;//ライフ減
	}

	/**消滅処理*/
	public void dead(){
		status = Status.DEAD;//消滅
		currentNum--;//プレイヤーチーム減少
	}

	/** 描画
	 * @paramキャンバス　画面描画用
	 */
	public void draw(Canvas c){

		if(status == Status.DEAD || status == Status.INIT){
			return;
		}
		
		//描画
		myImage.setBounds((int)left,(int)top,(int)right,(int)bottom);
		myImage.draw(c);
	}
}

package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;

/**弾クラス*/
public class ShotOfPlayer extends AbstractPlayer{

	/**コンストラクタ
	 * @param type　キャラのタイプ
	 * @param size サイズ
	 * @param char_x 初期X座標
	 * @param char_y　初期Y座標
	 * */
	ShotOfPlayer(int type){
		//super(キャラタイプ,x,y,w,h)
		super(type,0,0,50,50);
	}	
	ShotOfPlayer(){
		//super(キャラタイプ,x,y,w,h)
		super(0,0,50,50);
		myType = CharType.PSHOT;
		init();//初期化へ
	}		
	//staticメソッド
	/**スピード変更*/
	private  static float changeSpeed(Size size){
		float speed=0;
		switch(size){
		case SS:	speed =6;	break;
		case S:	speed =6;	break;
		case M:	speed =4;	break;
		case L:	speed =4;	break;
		case LL:	speed =2;	break;
		case XL:	speed =2;	break;
		}
		return speed;
	}

	//インスタンスメソッド
	/**初期化*/
	public void init(){
		super.init();
		life=1;
		resize(size);//サイズ変更
		hSpeed = changeSpeed(size);//スピード変更
		//myType = CharType.PSHOT;
		myImage = images2.get(myType);
	}
	
	/**開始処理*/
	@Override
	public void start(GameView view) {
		myImage.setVisible(true, true);//可視
	}

	/**物理状態の更新	 */
	@Override
	public void process(GameView view){
		//状態によって分岐
		switch(status){
		
		case INIT://初期化
			start(view);
			live();//生存へ
			break;
						
		case LIVE://生存
			
			//画面右端まで移動
			if (! isOutside(view.getDrawRect())) {
				char_x += hSpeed;
				char_y += vSpeed;
				yuragi(0.15f,2.0f);//ゆらぎ
		
			}else{
				dead();//消滅
			}
			break;
		
		case DAMAGE://ダメージ
			if(life <= 0){
				dead();
			}
			break;
			
		default:
			break;
		}
		
		//現在の位置更新
		offset(char_x,char_y);
	}

	/**消滅した時の処理*/
	public void dead(){
		super.dead();
		char_x = 0;
		char_y = 0;
		myImage.setVisible(false, true);//不可視
		
	}

}

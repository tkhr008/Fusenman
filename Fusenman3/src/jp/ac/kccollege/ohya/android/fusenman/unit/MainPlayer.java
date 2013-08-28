package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;



/**MainPlayerクラス
 * @author Takahiro Ohya
 */
public class MainPlayer
	extends AbstractPlayer implements InterfaceShooter{

	//インスタンス変数
	/**残機*/
	private final int LIFE = 3;
	
	/** コンストラクタ */
	public MainPlayer(int type){
		//super(キャラタイプ,x,y,w,h)
		super(type,0, 0, 50,50);
	}

	/**初期化*/
	public void init(){
		super.init();
		life=LIFE;//残機のリセット
		size= Size.M;//サイズ変更
		//myImage = images2.get(CharType.AHIRU);
	}
	
	/**準備処理(無敵時間)*/
	public void ready(){
		super.ready();
		waitTime = 50;//無敵タイムの設定
		alpha = 150;//アルファ値の変更
		myImage.mutate().setAlpha(alpha);
	}

	/**開始処理*/	
	@Override
	public void start(GameView view) {
		//中央に配置	
		offset(view.getDrawRect().centerX(),view.getDrawRect().centerY() );
	}

	/**
	 * 物理状態の更新
	 * @param view ゲームビュー
	 **/
	public void process(GameView view) {
		
		//状態によって分岐
		switch(status){
		
		case INIT://初期化
			ready();
			start(view);
			break;
			
		case READY://準備
			if(waitTime-- <= 0){
				live();
			}
			break;		
			
		case DAMAGE://ダメージ
			if(life > 0){
				ready();
			}else{
				dead();
			}
			break;
		
		case LIVE://生存
			// 加速度センサーの値による移動
			//move(view.gravity_x, view.gravity_y,view.gravity_z);
			//ゆらぎ
			yuragi(0.25f,1.0f);
			
			break;
			
		default:
			break;
		}
		
		
		//現在の位置更新
		offset(char_x,char_y);
	}
	
	/** 移動 */
	public void move(int diffX, int diffY) {
		char_x += diffX;
		char_y += diffY;
		//lotate((int)char_x);
	}
 
	/** 加速度センサーによる移動 */
	private void move(float gravity_x, float gravity_y,float gravity_z) {

		// 加速度 横向きアプリなので、gravity_x,gravity_yが反対
		char_x += (int) ((gravity_y * 100) / 100) - 4;// 加速度を追加( 4は調整値）
		char_y += (int) ((gravity_x * 100) / 100) - 4;// 加速度を追加( 4は調整値）
			
		//z方向の加速度によってサイズ変更
		if(char_z - gravity_z  < -4 && gravity_z > 12){
			sizeUp();
			
		}else if(char_z - gravity_z  > 8 && gravity_z < 0){
			sizeDown();
		}
		char_z = gravity_z;
	}
	
	/**生存処理*/
	public void live(){
		myImage = images[type];//画像のリセット
		alpha = 255;//透明度0%
		myImage.setAlpha(alpha);//アルファ値の変更		
		myImage.clearColorFilter();//カラーフィルタのリセット
		super.live();
	}

	/**ダメージ処理*/
	public void damage(){
		super.damage();
		//カラーフィルタ設定
		myImage.setColorFilter(Color.RED, Mode.MULTIPLY);
	}

	/**消滅処理*/
	public void dead(){
		super.dead();
		myImage=images[BOMBIMAGE];
		myImage.clearColorFilter();//カラーフィルタのリセット
	}
	
	/**弾発射*/
	@Override
	public int shoot() {
		super.live();//生存へステータス変更
		return 1;
	}
}

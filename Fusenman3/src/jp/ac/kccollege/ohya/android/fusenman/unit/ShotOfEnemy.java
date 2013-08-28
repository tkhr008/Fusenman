package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;

/**敵の弾クラス*/
public class ShotOfEnemy extends AbstractEnemy{
	
	private float shotSpeed=2.0f;//弾の速度
	
	/**コンストラクタ
	 * @param type　キャラのタイプ
	 * @param char_x 初期X座標
	 * @param char_y　初期Y座標
	 * */
	ShotOfEnemy(int type){
		//super(キャラタイプ,x,y,w,h)	
		super(type,0,0,25,25);
	}
	
	@Override
	public void init(){
		shotSpeed=2.0f;
		status = Status.LIVE;//ステータス変更	
		myImage = images[type];		//画像のリセット
		alpha=255;
	}
	/**開始処理*/
	//@Override
	public void start(){
		//super.start();
		shotSpeed=3.0f;
		status = Status.LIVE;//ステータス変更	
		myImage = images[type];		//画像のリセット
		alpha=255;
	}
	//インスタンスメソッド
	/**物理状態の更新	 */
	@Override
	public void process(GameView view){

		switch(status){
		
		case LIVE:	//生存
			//画面の外にでたら消滅
			if (isOutside(view.getDrawRect() ) ) {
				dead();//消滅
				break;
			}
			char_x += hSpeed;	//X移動
			char_y += vSpeed;	//Y移動
			break;
			
		default:
			break;
		}
		
		//現在の位置更新
		offset(char_x,char_y);
	}
	
	/**方向決定*/
	public void fixDirection(float x, float y){
		double radian = Math.atan2(x - char_x, y- char_y);
		vSpeed = (float)(Math.cos(radian)*shotSpeed);
		hSpeed = (float)(Math.sin(radian)*shotSpeed);

	}
	
	/**ダメージを受けた時*/
	@Override
	public void damage(){
		dead();//衝突で消滅
	}
	
	/**消滅した時 */
	@Override
	public void dead(){
		status = Status.DEAD;
		//弾数は敵の数としてカウントしない
	}
}

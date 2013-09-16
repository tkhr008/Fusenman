package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;
import jp.ac.kccollege.ohya.android.fusenman.unit.InterfaceShooter;

/**ステージボス管理クラス*/
public class StageBoss2 extends AbstractEnemy
implements InterfaceShooter {

	/**加速*/
	private float accel=3;
	
	/** コンストラクタ */
	public StageBoss2() {
		super(0, 0, 100, 100);// super(x,y,w,h)
		myType = CharType.BOSS2;
		init();
	}
	/**初期化*/
	@Override
	public void init(){
		super.init();
		hSpeed = accel;//加速値の設定
		life=70;//ライフの設定
		chanceOfShot = 3.0f;//弾を発射する確率
	}

	/**開始*/
	@Override
	public void start(GameView view) {
		super.start(view);
		accel += 3;//加速値の増加
		hSpeed = (float)(Math.random()*accel);//x軸スピードの変化
	}
	
	/**物理状態の更新 */
	@Override
	public void process(GameView view) {
		super.process(view);

		switch(status){
	
		case LIVE://生存
			if(shotReady(chanceOfShot)){
				attack();//chanceOfShotの確率で攻撃
			}
			//break;//意図的
			
		case DAMAGE://ダメージ
			char_x = (char_x - hSpeed)*1.01f;//加速する
			break;
			
		default:
			break;
		}
		
		//現在の位置更新
		offset(char_x,char_y);
	}

	/**ダメージを受けた時*/
	public void damage(){
		super.damage();
		if(life%10==0){
			hSpeed = -8;//10ダメージごとに後方移動
		}
	}
	
	/**弾発射*/
	@Override
	public CharType shoot() {
		live();
		return CharType.ESHOT;
	}
}

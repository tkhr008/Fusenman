package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;

public class Anpanman extends AbstractEnemy{

	/**コンストラクタ*/
	public Anpanman(){
		super(0,0,65,55);//super(x,y,w,h)
		myType = CharType.ANPANMAN;
		init();
	}	
	/**初期化*/
	@Override
	public void init(){
		super.init();
		hSpeed = 5;//x方向スピード値
		chanceOfShot = 1.0f;//弾を発射する確率
	}	

	/* (非 Javadoc)物理状態の更新	 */
	@Override 
	public void process(GameView view) {
		super.process(view);
		
		switch(status){

		case DAMAGE://ダメージ
		case LIVE://生存			
			char_x -= hSpeed; // 移動
			char_y -= (int)(Math.random()*5);//0～4でランダム上昇

			if(shotReady(chanceOfShot)){
				attack();//chanceOfShotの確率で攻撃
			}
			break;
			
		default:
			break;
		}
		
		//現在の位置更新
		offset(char_x,char_y);
	}
	
}

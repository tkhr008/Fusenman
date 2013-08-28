package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;
/**敵キャラ　キラー*/
public class Killer extends AbstractEnemy {

	/** コンストラクタ */
	public Killer(int type){
		//super(キャラタイプ,x,y,w,h)		
		super(type,0,0,50,30);
	}

	@Override
	public void init(){
		super.init();
		hSpeed = 5;//水平スピード設定
		life=2;//ライフ設定
	}
	
	/* (非 Javadoc)物理状態の更新 */
	@Override
	public void process(GameView view) {
		super.process(view);

		switch(status){
	
		case LIVE://生存
		case DAMAGE://ダメージ
			char_x -= hSpeed; // 移動
			break;
		default:
			break;
		}
		
		//現在の位置更新
		offset(char_x,char_y);
	}

}

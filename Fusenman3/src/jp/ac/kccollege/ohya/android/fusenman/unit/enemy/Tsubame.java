package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;

/**敵キャラ　キラー*/
public class Tsubame extends AbstractEnemy {

	/** コンストラクタ */
	public Tsubame(){
		super(0,0,50,50);//super(x,y,w,h)
		myType = CharType.TSUBAME;
		init();
	}	
	@Override
	public void start(GameView view) {
		super.start(view);
		hSpeed=5;//横方向の速度
	}

	/* 物理状態の更新 */
	@Override
	public void process(GameView view) {
		super.process(view);

		switch(status){
		
		case LIVE://生存
		case DAMAGE://ダメージ
			char_x -= hSpeed; // x方向移動
			yuragi(0.15f,2.0f);//ゆらぎ
			break;
		default:
			break;
		}
		
		//現在の位置更新
		offset(char_x,char_y);
	}
}

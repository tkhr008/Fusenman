package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;

/**敵キャラ　カラス*/
public class Karasu extends AbstractEnemy{
	
	/**コンストラクタ*/
	public Karasu(int type){
		//super(キャラタイプ,x,y,w,h)
		super(type,0,0,50,50);
	}
	
	/**初期化*/
	@Override	
	public void init(){
		super.init();
		hSpeed=4;//水平スピード設定
		life=1;//ライフ設定
	}

	/* (非 Javadoc)物理状態の更新	 */
	@Override
	public void process(GameView view) {
		super.process(view);
		
		switch(status){
		
		case LIVE:
		case DAMAGE:
			char_x -= hSpeed;	//x方向移動
			yuragi(0.15f,4.0f);	//ゆらぎ			
			break;
			
		default:
			break;
		}
		//現在の位置更新
		offset(char_x,char_y);
	}
}

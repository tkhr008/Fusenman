package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;

public class Ahiru extends AbstractEnemy{

	private float gravity=0;//重力
	
	/**コンストラクタ*/
	public Ahiru(){
		super(0,0,50,50);
		myType = CharType.AHIRU;
		init();
	}
	
	/**初期化	 */
	@Override
	public void init(){
		super.init();
		life=3;
	}
	
	/**開始処理	 */	
	@Override
	public void start(GameView view) {
		super.start(view);	
		vSpeed = (int) (char_x%20+10);//10～24の縦方向速度
		hSpeed = char_y%20+3;//3～22の速度
		gravity=1;//重力
	}

	/**物理状態の更新	
	 * @param GameView view
	 */
	@Override
	public void process(GameView view) {
		super.process(view);

		switch(status){
			
		case LIVE:
		case DAMAGE:
			char_x -= hSpeed;	//X移動
			char_y -= vSpeed;	//Y移動
			vSpeed -= gravity;
			break;
			
		default:
			break;
		}
		
		//座標変換テスト（OpenGLの場合）
		float x = GameView.toGlPoint((int)char_x,true,true);
		char_x = GameView.toCanvasPoint(x,true,true); 
		float y = GameView.toGlPoint((int)char_y,false,true);
		char_y = GameView.toCanvasPoint(y,false,true); 
		
		//現在の位置更新
		offset(char_x,char_y);
	}
	
	/**ダメージ処理*/
	public void damage(){
		super.damage();
	}

}

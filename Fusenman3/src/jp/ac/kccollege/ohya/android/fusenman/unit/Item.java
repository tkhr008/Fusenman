package jp.ac.kccollege.ohya.android.fusenman.unit;

import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;


/** 画面にメッセージを表示するクラス */
public class Item extends AbstractUnit {

	// インスタンス変数
	private int numberOfFrames=0;
	AnimationDrawable  anim;
	private int index=1;
	
	/**コンストラクタ*/
	public Item(){
		super(0,0,50,50);
		myType = CharType.ITEM1;
		anim =(AnimationDrawable)images2.get(myType);
		myImage=anim.getFrame(0);
		//numberOfFrames = ((AnimationDrawable)(myImage)).getNumberOfFrames();
		numberOfFrames = anim.getNumberOfFrames();
		init();
	}	
	/**初期化	 */
	@Override
	public void init(){
		status = Status.INIT;
		life=3;

	}
	
	/**開始処理	 */	
	@Override
	public void start(GameView view) {
		status = Status.START;
		hSpeed=4;//水平スピード設定
		hSpeed = char_y%20+3;//3～22の速度
		// 出現位置の決定
		float bottom = view.getDrawRect().bottom;
		float top = view.getDrawRect().top;
		char_y = (int) (Math.random() * (bottom - char_h + top));// ランダム
		char_x = view.getDrawRect().right;
	}

	/**物理状態の更新	
	 * @param GameView view
	 */
	@Override
	public void process(GameView view) {

		switch(status){
		case INIT:// 初期化
			start(view);
			break;
			
		case START:// 開始
		case READY:// 準備
			if (waitTime-- <= 0) {
				live();//生存へ
			}
			break;			
		case LIVE:
		case DAMAGE:
			char_x -= hSpeed;	//X移動
			char_y -= vSpeed;	//Y移動
			yuragi(0.15f,4.0f);	//ゆらぎ		
			break;
			
		default:
			break;
		}
		
		//現在の位置更新
		offset(char_x,char_y);
	}
	
	/**ダメージ処理*/
	public void damage(){

	}
	protected void live() {
		status = Status.LIVE;
	}
	
	@Override
	public void draw(Canvas canvas) {
		if (status == Status.DEAD || status == Status.INIT) {
			return;
		}
		// 描画
		//anim.setBounds((int) left, (int) top, (int) right, (int) bottom);
		//anim.draw(canvas);
		
		if(numberOfFrames-1 > index){
			index++;
		}else{
			index=1;
		}

		//(((AnimationDrawable)myImage).getFrame((int)index)).draw(canvas);
		//myImage=((AnimationDrawable)myImage).getFrame((int)index);
		//myImage= anim.getFrame(index);
		
		myImage.setBounds((int) left, (int) top, (int) right, (int) bottom);
		myImage=anim.getFrame(index);
		myImage.draw(canvas);
	}
}

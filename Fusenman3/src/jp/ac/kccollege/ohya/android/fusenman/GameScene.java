package jp.ac.kccollege.ohya.android.fusenman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractPlayer;


public class GameScene extends AnimationScene {
	/**モーションイベント*/
	private MotionEvent event;
	/**現在のx座標*/
	private int currentX =0;
	/**現在のy座標*/
	private int currentY = 0;
	/**ムーブ開始時のx座標*/
	private int savedX=0;
	/**ムーブ開始時のy座標*/
	private int savedY=0;
	/**ムーブ中かどうか*/
	private boolean moving = false;
	
	/** ゲーム開始時刻 */
	private long gameStarted;	
	/** プレイ残時間 */
	private final static long TIME = 300;
	/** 残時間 */
	private long remainedTime;

	/** ペイント */
	private Paint paint;

	@Override
	public void init(GameView view) {
		super.init(view);

		paint = new Paint();
		paint.setTextAlign(Paint.Align.RIGHT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setTextSize(32);
	
	}

	
	@Override
	public void start(GameView view) {
		super.start(view);	

		gameStarted = System.currentTimeMillis();//開始時間を保存
		//座標の初期化
		savedX=0;		savedY=0;	
		currentX=0;	currentY=0;
		//移動描画のOFF
		moving=false;
		
		//////////////

		//ゲームオーバー後の再開だったら初期化
		if(mainPlayer.isDead()){
			mainPlayer.init();
		}
		//敵の生成
		enemyFactory.start(stageCount);
		//item test
		itemFactory.create(CharType.ITEM1);
	}

	@Override
	public void process(GameView view) {
		//スーパークラスのメソッド呼び出し
		super.process(view);
		//残り時間の設定
		remainedTime = TIME - (System.currentTimeMillis() - gameStarted) / 1000;
		
		if(AbstractPlayer.getCurrentNum() <= 0) {			
			view.startScene(FusenmanScenes.OVER);
			return;
		}
		//敵の追加
		if(AbstractEnemy.getCurrentNum() <= 0) {
			
			if( ! enemyFactory.createUnits()){
				view.startScene(FusenmanScenes.STAGECLEAR);
				return;
			}
			
		}		
		
		//タッチイベント取得
		while ( (event = view.event()) != null) {
			
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.d("Motion", "ActionDown");

				/*うまくダウンが反応しないため
				savedX = (int)event.getX();//座標の保存
				savedY = (int)event.getY(); 
				moving = true;
				*/
				break;
				
			case MotionEvent.ACTION_UP://画面から指を離した時
				Log.d("Motion", "ActionUp");
				if(moving){
					moving=false;//ドラッグ終了
				}
				mainPlayer.attack();//攻撃
				break;
				
			case MotionEvent.ACTION_MOVE:// 画面をドラッグした時
				Log.d("Motion", "ActionMove");
				currentX = (int)event.getX() ;	//現在のX,Y座標
				currentY = (int)event.getY() ;
				
				if(moving){//移動描画中だったら
					mainPlayer.move(currentX - savedX,  currentY - savedY);//差分の移動
				}else{	//初回だったら
					moving = true;//移動描画の処理開始
				}
				
				savedX = currentX;	//１つ前のX座標更新
				savedY  = currentY;	//１つ前のY座標更新				
				
				break;
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
//debug
		canvas.drawText("残り敵数："+AbstractEnemy.getCurrentNum(), canvas.getWidth()/3*1, 48,paint);
		canvas.drawText("Players:"+AbstractPlayer.getCurrentNum(), canvas.getWidth()/3, 48*2,paint);
		canvas.drawText("Items:"+itemUnits.size(), canvas.getWidth()/3, 48*3,paint);
		
		canvas.drawText(remainedTime + "秒", canvas.getWidth()/3*2, 48*3,paint);
		canvas.drawText("Enemies:"+enemyUnits.size(), canvas.getWidth()/3*2, 48*2,paint);
		canvas.drawText("Images:"+unitImages2.size(), canvas.getWidth()/3*2, 48,paint);
	}

}

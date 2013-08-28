package jp.ac.kccollege.ohya.android.fusenman.unit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman;

/** 画面にメッセージを表示するクラス */
public class Message extends AbstractUnit {

	// インスタンス変数
	private String message;
	protected Paint paint;
	/**
	 * コンストラクタ
	 * 
	 * @param type
	 *            キャラタイプ
	 * @param _x
	 *            左上のx座標
	 * @param _y
	 *            左上のy座標
	 * @param textScaleX
	 *            文字の水平サイズ
	 * @param textSize
	 *            文字サイズ
	 */
	public Message(String message, float textScaleX, float textSize) {
		super(Fusenman.MESSAGE1,0,0, textScaleX,textSize);
		this.message=message;
	}

	/** 初期化 */
	public void init() {
		status = Status.INIT;
		hSpeed= -3*(int)Math.random()*6;
		paint = new Paint();
		paint.setTextAlign(Paint.Align.RIGHT);
		paint.setAntiAlias(true);
		paint.setColor(Color.MAGENTA);
		
		paint.setTextSize(char_h);
		paint.setTextScaleX(char_w);
		//myImage = images[type];// 画像の設定
		//myImage.setVisible(false, true);// 不可視

		//alpha = 255;
	}

	/** 開始処理 */
	public void start(GameView view) {
		status = Status.START;
		// 出現位置の決定
		hSpeed= -8;
		char_y = (int)(Math.random()*view.getDrawRect().bottom);
		char_x = view.getDrawRect().right;
		//myImage.setVisible(true, true);// 可視
		paint = new Paint();
		paint.setTextAlign(Paint.Align.RIGHT);
		paint.setAntiAlias(true);
		paint.setColor(Color.MAGENTA);
		
		paint.setTextSize(char_h);
		paint.setTextScaleX(char_w);
	}

	/** 物理状態の更新 */
	@Override
	public void process(GameView view) {
		switch (status) {

		case INIT:// 初期化
			start(view);
			break;
			
		case START:// 開始
		case READY:// 準備
			if (waitTime-- <= 0) {
				live();//生存へ
			}
			break;

		case LIVE:// 生存
			//if (isOutside(view.getDrawRect())) {
			if (char_x + char_w < 0) {
				dead();// 消滅
			}else{
				//現在の位置更新
				char_x += hSpeed;
				offset(char_x,char_y);
			}
			break;

		default:
			break;
		}
	}

	/** 消滅した時 */
	public void dead() {
		status = Status.DEAD;
	}
	
	/** 生存処理 */
	protected void live() {
		status = Status.LIVE;
	}
	
	/**描画処理*/
	public void draw(Canvas c) {
		if (status == Status.DEAD || status == Status.INIT) {
			return;
		}
		
		//koko
/*		paint = new Paint();
		paint.setTextAlign(Paint.Align.RIGHT);
		paint.setAntiAlias(true);
		paint.setColor(Color.GREEN);
		paint.setTextSize(char_h);
		paint.setTextScaleX(char_w);*/
		if(paint != null){
			c.drawText(message, char_x,char_y, paint);
		}
		// 描画
		//myImage.setBounds((int) left, (int) top, (int) right, (int) bottom);
		//myImage.draw(c);
	}
	
	/** ダメージを受けた時 */
	public void damage() {

	}
}

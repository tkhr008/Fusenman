package jp.ac.kccollege.ohya.android.framework.game2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ゲーム内オブジェクトの処理、タッチの処理、画面の表示等のゲームの基本処理を行うクラス
 */
public class GameView extends SurfaceView implements Runnable,
		SensorEventListener, 
		SurfaceHolder.Callback {
	
	/** FPS 1秒あたりのフレーム数。ゲームの処理単位 */
	public static final int FRAMES_PER_SECOND = 50;
	/** フレームあたりのミリ秒数 */
	public static final int MILLIS_PER_FRAME = 1000 / FRAMES_PER_SECOND;
	/** スレッド */
	private static Thread thread;
	/** ゲーム内のシーン */
	private Scenes scenes;
	/** シーン */
	private static Scene current;
	/** イベントキュー */
	private final Queue<MotionEvent> events = new LinkedList<MotionEvent>();

	/** サーフィス（表面）管理変数 */
	private final SurfaceHolder holder;
    
	/** キャンバス */
	public static int cHeight = 1; // キャンバスの高さ（動的に変更）
	public static int cWidth = 1; // キャンバスの幅（動的に変更）
	/** 描画矩形 */
	protected RectF drawRect; // 表示エリア範囲

	/** 画面の縦横比 */
	private static final float RATIO_HEIGHT = 2.0f;// 縦比率
	private static final float RATIO_WIDTH = 3.0f;// 横比率
	/** センサー用 */
	private Activity context;
	private boolean mRegisteredSensor;
	private SensorManager mSensorManager;
	public float gravity_y = 0.0f;// 縦方向（y軸）の加速度
	public float gravity_x = 0.0f;// 横方向（x軸）の加速度
	public float gravity_z = 0.0f;// 前後方向（z軸）の加速度
	private long preTime=0;
	
	/** コンストラクタ */
	public GameView(final Context context, final Scenes scenes) {
		super(context);
		this.context = (Activity) context;// センサー取得のためコンテキストを登録

		this.scenes = scenes;
		setFocusable(true);
		// getHolder().addCallback(this);

		holder = getHolder();// サーフィスを取得
		holder.addCallback(this);// コールバックインスタンスの登録

	       // GestureDetector設定
        // このクラス(TestGesture)をリスナーとして設定
      // this.gestureDetector = new GestureDetector((Activity)this, (Activity)this);
        
		// センサー管理インスタンスを取得
		mSensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);

		// 加速度センサーの取得
		List<Sensor> sensors = mSensorManager
				.getSensorList(Sensor.TYPE_ACCELEROMETER);

		// センサーを取得
		// http://www.atmarkit.co.jp/fsmart/articles/android13/android13_2.html
		if (sensors.size() > 0) {
			Sensor sensor = sensors.get(0);
			mSensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_NORMAL);
			mRegisteredSensor = true;
		}

	}

	/** 表示状態(画面サイズや回転状態)が変更された際にAPIからコールバックされる */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d("GameView", "surfaceChanged");
		
	}

	/** 実際にSurfaceViewが生成完了された際にAPIからコールバックされる */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("GameView", "surfaceCreated");

		cWidth = getWidth();// キャンバスの幅を取得
		cHeight = getHeight();// キャンバスの高さを取得
		setDrawRect();// 比率にあった描画エリアの設定

		
		// センサー管理インスタンスを取得
		// mSensorManager
		// = (SensorManager)context.getSystemService(Activity.SENSOR_SERVICE);

		// スレッドの開始
		thread = new Thread(GameView.this);
		thread.start();
	}

	/** SurfaceViewが破棄された際に、APIからコールバックされる */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("GameView", "surfaceDestroyed");
		// リスナーの解除
		if (mRegisteredSensor) {
			mSensorManager.unregisterListener(this);
			mRegisteredSensor = false;
		}
		// スレッドの終了
		thread.interrupt();
	}

	/** ゲームループ中の１フレームの処理 */
	@Override
	public void run() {
		Log.d("GameView", "#run start");

		for (final Scene scene : scenes.scenes()) {
			scene.init(this);
		}

		startScene(scenes.bootScene());

		while (!thread.isInterrupted()) {
			final long start = System.currentTimeMillis();
			final Scene scene = current;
			scene.process(this);

			// (1) ダブルバッファリング開始
			// final SurfaceHolder holder = getHolder();
			final Canvas canvas = holder.lockCanvas();
			if (canvas != null) {
				try {
					canvas.clipRect(drawRect);// 描画エリアのクリップ
					scene.draw(canvas);
				} finally {
					// (2) ダブルバッファリング完了
					holder.unlockCanvasAndPost(canvas);
				}
			}

			final long elapsed = System.currentTimeMillis() - start;
			final long towait = MILLIS_PER_FRAME - elapsed;
			if (0 < towait) {
				try {
					Thread.sleep(towait);
				} catch (final InterruptedException e) {
					Log.d("GameView", "Thread#sleep", e);
					break;
				}
			}
		}
		Log.d("GameView", "#run end");
		thread = null;
	}

	/**
	 * イベントを返す
	 * 
	 * @return
	 */
	public MotionEvent event() {
		MotionEvent event = null; 
		try{
			event = events.poll();
		} catch (java.util.NoSuchElementException e) {
			e.printStackTrace();
		}
		return event;
	}

	/**
	 * タッチされた際にAPIからコールバックされるゲームループ内で処理できるように、 ユーザのアクションを記録する
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		
		try{
			events.add(event);
		} catch (java.util.NoSuchElementException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * シーンを変更する
	 * 
	 * @param scene
	 */
	public void startScene(final Scene scene) {
		current = scene;
		events.clear();
		current.start(this);
	}

	/** 画面比に合わせて描画エリアの設定 */
	private void setDrawRect() {
		float newWidth = 0, newHeight = 0;

		while (newWidth < cWidth && newHeight < cHeight) {
			newWidth += RATIO_WIDTH;
			newHeight += RATIO_HEIGHT;
		}
		float left = (int) ((cWidth - newWidth) / 2);// 左座標の設定
		float top = (int) ((cHeight - newHeight) / 2);// 上座標の設定
		// 描画エリア（矩形）
		drawRect = new RectF(left, top, left + newWidth, top + newHeight);
	}

	/** 描画エリアを返す */
	public RectF getDrawRect() {
		return drawRect;
	}

	/**
	 * OpenGL用の座標に変換
	 * 
	 * @param p
	 *            　座標
	 * @param isX
	 *            　　xの値 =ture, yの値=false
	 * @param isCenter0
	 *            中央を0とするかどうか
	 * @return　あたらしい座標
	 */
	public static float toGlPoint(int p, boolean isX, boolean isCenter0) {
		if (isCenter0) {
			if (isX) {
				return (float) p / cWidth * RATIO_WIDTH - (RATIO_WIDTH / 2);
			} else {
				return (p / (float) cHeight * RATIO_HEIGHT - (RATIO_HEIGHT / 2))
						* -1;// y軸逆転
			}
		} else {
			if (isX) {
				return (p / cWidth * RATIO_WIDTH);
			} else {
				return (p / cHeight * RATIO_HEIGHT);
			}
		}
	}

	/**
	 * キャンバス用の座標に変換
	 * 
	 * @param p
	 *            　座標
	 * @param isX
	 *            　　xの値 =ture, yの値=false
	 * @param isCenter0
	 *            中央を0とするかどうか
	 * @return　あたらしい座標
	 */
	public static float toCanvasPoint(float p, boolean isX, boolean isCenter0) {
		if (isCenter0) {
			if (isX) {
				return cWidth / (RATIO_WIDTH / (p + (RATIO_WIDTH / 2)));
			} else {
				return cHeight / (RATIO_HEIGHT / (-1 * p + (RATIO_HEIGHT / 2)));// y軸逆転
			}
		} else {
			if (isX) {
				return cWidth / (RATIO_WIDTH / p);
			} else {
				return cHeight / (RATIO_HEIGHT / p);
			}
		}
	}

	/**
	 * センサーの値に変更があった際に呼び出される
	 * 
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		// 加速度センサーではなかったら
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
			return;
		}			

		//200msに一回だけ計算する
		long currentTime = System.currentTimeMillis();
		if( currentTime - preTime > 200){
			//http://oshiete.goo.ne.jp/qa/6404282.html
			gravity_x = event.values[0];// 左右方向
			gravity_y = event.values[1];// 上下方向
			gravity_z = event.values[2];// 奥行き方向
	
			Log.d("sensor", "gravity_x:" + gravity_x);
			Log.d("sensor", "gravity_y:" + gravity_y);
			Log.d("sensor", "gravity_z:" + gravity_z);
		}
		//前回値を保存
		preTime = currentTime;
	}

	/**
	 * センサーの精度が変更された際に呼び出される
	 * 
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 */
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
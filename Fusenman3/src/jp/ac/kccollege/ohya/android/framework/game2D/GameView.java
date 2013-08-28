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
 * �Q�[�����I�u�W�F�N�g�̏����A�^�b�`�̏����A��ʂ̕\�����̃Q�[���̊�{�������s���N���X
 */
public class GameView extends SurfaceView implements Runnable,
		SensorEventListener, 
		SurfaceHolder.Callback {
	
	/** FPS 1�b������̃t���[�����B�Q�[���̏����P�� */
	public static final int FRAMES_PER_SECOND = 50;
	/** �t���[��������̃~���b�� */
	public static final int MILLIS_PER_FRAME = 1000 / FRAMES_PER_SECOND;
	/** �X���b�h */
	private static Thread thread;
	/** �Q�[�����̃V�[�� */
	private Scenes scenes;
	/** �V�[�� */
	private static Scene current;
	/** �C�x���g�L���[ */
	private final Queue<MotionEvent> events = new LinkedList<MotionEvent>();

	/** �T�[�t�B�X�i�\�ʁj�Ǘ��ϐ� */
	private final SurfaceHolder holder;
    
	/** �L�����o�X */
	public static int cHeight = 1; // �L�����o�X�̍����i���I�ɕύX�j
	public static int cWidth = 1; // �L�����o�X�̕��i���I�ɕύX�j
	/** �`���` */
	protected RectF drawRect; // �\���G���A�͈�

	/** ��ʂ̏c���� */
	private static final float RATIO_HEIGHT = 2.0f;// �c�䗦
	private static final float RATIO_WIDTH = 3.0f;// ���䗦
	/** �Z���T�[�p */
	private Activity context;
	private boolean mRegisteredSensor;
	private SensorManager mSensorManager;
	public float gravity_y = 0.0f;// �c�����iy���j�̉����x
	public float gravity_x = 0.0f;// �������ix���j�̉����x
	public float gravity_z = 0.0f;// �O������iz���j�̉����x
	private long preTime=0;
	
	/** �R���X�g���N�^ */
	public GameView(final Context context, final Scenes scenes) {
		super(context);
		this.context = (Activity) context;// �Z���T�[�擾�̂��߃R���e�L�X�g��o�^

		this.scenes = scenes;
		setFocusable(true);
		// getHolder().addCallback(this);

		holder = getHolder();// �T�[�t�B�X���擾
		holder.addCallback(this);// �R�[���o�b�N�C���X�^���X�̓o�^

	       // GestureDetector�ݒ�
        // ���̃N���X(TestGesture)�����X�i�[�Ƃ��Đݒ�
      // this.gestureDetector = new GestureDetector((Activity)this, (Activity)this);
        
		// �Z���T�[�Ǘ��C���X�^���X���擾
		mSensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);

		// �����x�Z���T�[�̎擾
		List<Sensor> sensors = mSensorManager
				.getSensorList(Sensor.TYPE_ACCELEROMETER);

		// �Z���T�[���擾
		// http://www.atmarkit.co.jp/fsmart/articles/android13/android13_2.html
		if (sensors.size() > 0) {
			Sensor sensor = sensors.get(0);
			mSensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_NORMAL);
			mRegisteredSensor = true;
		}

	}

	/** �\�����(��ʃT�C�Y���]���)���ύX���ꂽ�ۂ�API����R�[���o�b�N����� */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d("GameView", "surfaceChanged");
		
	}

	/** ���ۂ�SurfaceView�������������ꂽ�ۂ�API����R�[���o�b�N����� */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("GameView", "surfaceCreated");

		cWidth = getWidth();// �L�����o�X�̕����擾
		cHeight = getHeight();// �L�����o�X�̍������擾
		setDrawRect();// �䗦�ɂ������`��G���A�̐ݒ�

		
		// �Z���T�[�Ǘ��C���X�^���X���擾
		// mSensorManager
		// = (SensorManager)context.getSystemService(Activity.SENSOR_SERVICE);

		// �X���b�h�̊J�n
		thread = new Thread(GameView.this);
		thread.start();
	}

	/** SurfaceView���j�����ꂽ�ۂɁAAPI����R�[���o�b�N����� */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("GameView", "surfaceDestroyed");
		// ���X�i�[�̉���
		if (mRegisteredSensor) {
			mSensorManager.unregisterListener(this);
			mRegisteredSensor = false;
		}
		// �X���b�h�̏I��
		thread.interrupt();
	}

	/** �Q�[�����[�v���̂P�t���[���̏��� */
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

			// (1) �_�u���o�b�t�@�����O�J�n
			// final SurfaceHolder holder = getHolder();
			final Canvas canvas = holder.lockCanvas();
			if (canvas != null) {
				try {
					canvas.clipRect(drawRect);// �`��G���A�̃N���b�v
					scene.draw(canvas);
				} finally {
					// (2) �_�u���o�b�t�@�����O����
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
	 * �C�x���g��Ԃ�
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
	 * �^�b�`���ꂽ�ۂ�API����R�[���o�b�N�����Q�[�����[�v���ŏ����ł���悤�ɁA ���[�U�̃A�N�V�������L�^����
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
	 * �V�[����ύX����
	 * 
	 * @param scene
	 */
	public void startScene(final Scene scene) {
		current = scene;
		events.clear();
		current.start(this);
	}

	/** ��ʔ�ɍ��킹�ĕ`��G���A�̐ݒ� */
	private void setDrawRect() {
		float newWidth = 0, newHeight = 0;

		while (newWidth < cWidth && newHeight < cHeight) {
			newWidth += RATIO_WIDTH;
			newHeight += RATIO_HEIGHT;
		}
		float left = (int) ((cWidth - newWidth) / 2);// �����W�̐ݒ�
		float top = (int) ((cHeight - newHeight) / 2);// ����W�̐ݒ�
		// �`��G���A�i��`�j
		drawRect = new RectF(left, top, left + newWidth, top + newHeight);
	}

	/** �`��G���A��Ԃ� */
	public RectF getDrawRect() {
		return drawRect;
	}

	/**
	 * OpenGL�p�̍��W�ɕϊ�
	 * 
	 * @param p
	 *            �@���W
	 * @param isX
	 *            �@�@x�̒l =ture, y�̒l=false
	 * @param isCenter0
	 *            ������0�Ƃ��邩�ǂ���
	 * @return�@�����炵�����W
	 */
	public static float toGlPoint(int p, boolean isX, boolean isCenter0) {
		if (isCenter0) {
			if (isX) {
				return (float) p / cWidth * RATIO_WIDTH - (RATIO_WIDTH / 2);
			} else {
				return (p / (float) cHeight * RATIO_HEIGHT - (RATIO_HEIGHT / 2))
						* -1;// y���t�]
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
	 * �L�����o�X�p�̍��W�ɕϊ�
	 * 
	 * @param p
	 *            �@���W
	 * @param isX
	 *            �@�@x�̒l =ture, y�̒l=false
	 * @param isCenter0
	 *            ������0�Ƃ��邩�ǂ���
	 * @return�@�����炵�����W
	 */
	public static float toCanvasPoint(float p, boolean isX, boolean isCenter0) {
		if (isCenter0) {
			if (isX) {
				return cWidth / (RATIO_WIDTH / (p + (RATIO_WIDTH / 2)));
			} else {
				return cHeight / (RATIO_HEIGHT / (-1 * p + (RATIO_HEIGHT / 2)));// y���t�]
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
	 * �Z���T�[�̒l�ɕύX���������ۂɌĂяo�����
	 * 
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		// �����x�Z���T�[�ł͂Ȃ�������
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
			return;
		}			

		//200ms�Ɉ�񂾂��v�Z����
		long currentTime = System.currentTimeMillis();
		if( currentTime - preTime > 200){
			//http://oshiete.goo.ne.jp/qa/6404282.html
			gravity_x = event.values[0];// ���E����
			gravity_y = event.values[1];// �㉺����
			gravity_z = event.values[2];// ���s������
	
			Log.d("sensor", "gravity_x:" + gravity_x);
			Log.d("sensor", "gravity_y:" + gravity_y);
			Log.d("sensor", "gravity_z:" + gravity_z);
		}
		//�O��l��ۑ�
		preTime = currentTime;
	}

	/**
	 * �Z���T�[�̐��x���ύX���ꂽ�ۂɌĂяo�����
	 * 
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 */
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}
package jp.ac.kccollege.ohya.android.fusenman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;

public class TitleScene extends AnimationScene {
	/** 1秒で点滅する */
	private static final long BLINK_DURATION = 2000;
	/** 点滅アニメーションの開始時刻 */
	private long blinkStarted;
	private Paint titlePaint;
	private Paint msgPaint;

	@Override
	public void init(GameView view) {
		super.init(view);

		titlePaint = new Paint();
		titlePaint.setTextSize(48);
		titlePaint.setAntiAlias(true);
		titlePaint.setColor(Color.BLACK);
		titlePaint.setTextAlign(Paint.Align.CENTER);
		msgPaint = new Paint();
		msgPaint.setTextSize(24);
		msgPaint.setAntiAlias(true);
		msgPaint.setColor(Color.BLACK);
		msgPaint.setTextAlign(Paint.Align.CENTER);
	}

	@Override
	public void start(GameView view) {
		super.start(view);
		blinkStarted = System.currentTimeMillis();
	}

	@Override
	public void process(GameView view) {
		final MotionEvent e = view.event();
		if (e != null && e.getAction() == MotionEvent.ACTION_UP) {
			view.startScene(FusenmanScenes.STAGESTART);
		}
		super.process(view);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		final int w = canvas.getWidth();
		final int h = canvas.getHeight();

		canvas.drawText("Fusenman Game", w / 2, h / 2, titlePaint);

		final long elapsed = (System.currentTimeMillis() - blinkStarted)
				% BLINK_DURATION;
		final int g;
		if (elapsed <= BLINK_DURATION / 2) {
			g = (int) (255 * elapsed / (BLINK_DURATION / 2));
		} else {
			g = (int) (255 * (BLINK_DURATION - elapsed) / (BLINK_DURATION / 2));
		}
		msgPaint.setColor(Color.rgb(g, g, g));
		canvas.drawText("Touch to Start!", w / 2, h / 2 + 48, msgPaint);
	}

}

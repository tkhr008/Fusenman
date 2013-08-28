package jp.ac.kccollege.ohya.android.fusenman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;

public class OverScene extends AnimationScene {
	private final static long WAIT = 2000;
	private final static long AUTO = 10000;
	private Paint paintScore;
	private long start;

	@Override
	public void init(GameView view) {
		super.init(view);

		paintScore = new Paint();
		paintScore.setTextAlign(Paint.Align.CENTER);
		paintScore.setAntiAlias(true);
		paintScore.setColor(Color.BLACK);
		paintScore.setTextSize(48);

	}

	@Override
	public void start(GameView view) {
		start = System.currentTimeMillis();
	}

	@Override
	public void process(GameView view) {
		final long elapsed = System.currentTimeMillis() - start;
		final MotionEvent e = view.event();
		if (WAIT < elapsed && e != null
				&& e.getAction() == MotionEvent.ACTION_UP || AUTO <= elapsed) {
			view.startScene(FusenmanScenes.TITLE);
		}
		super.process(view);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawText("Game Over",
				canvas.getWidth() / 2, canvas.getHeight() / 2, paintScore);
	}
}

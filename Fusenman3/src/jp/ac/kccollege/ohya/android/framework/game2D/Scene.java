package jp.ac.kccollege.ohya.android.framework.game2D;

import android.graphics.Canvas;

/**�V�[���̃C���^�[�t�F�C�X*/
public interface Scene {
	/**������*/
	public void init(GameView view);
	/**�J�n����*/
	public void start(GameView view);
	/**�v���Z�X*/
	public void process(GameView view);
	/**�`��*/
	public void draw(Canvas canvas);
}
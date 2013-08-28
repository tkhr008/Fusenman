package jp.ac.kccollege.ohya.android.framework.game2D;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public abstract class GameActivity extends Activity {
	protected abstract Scenes scenes();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // �^�C�g���o�[���\���ɂ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// ���j���[�o�[���\���ɂ���
       Window window = getWindow();
       window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(new GameView(this, scenes()));
	}

}
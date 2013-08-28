package jp.ac.kccollege.ohya.android.fusenman;

import jp.ac.kccollege.ohya.android.framework.game2D.GameActivity;
import jp.ac.kccollege.ohya.android.framework.game2D.Scenes;

public class FusenmanActivity extends GameActivity {
	@Override
	protected Scenes scenes() {
		return new FusenmanScenes();
	}
}

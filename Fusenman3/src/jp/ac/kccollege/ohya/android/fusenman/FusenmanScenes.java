package jp.ac.kccollege.ohya.android.fusenman;

import java.util.List;

import jp.ac.kccollege.ohya.android.framework.game2D.Scene;
import jp.ac.kccollege.ohya.android.framework.game2D.Scenes;

class FusenmanScenes extends Scenes {
	public static final Scene TITLE = new TitleScene();
	public static final Scene STAGESTART = new StageStartScene();
	public static final Scene GAME = new GameScene();
	public static final Scene STAGECLEAR = new StageClearScene();
	public static final Scene OVER = new OverScene();
	
	@Override
	public Scene bootScene() {
		return TITLE;
	}

	@Override
	protected void appendAll(List<Scene> container) {
		container.add(TITLE);
		container.add(STAGESTART);
		container.add(GAME);
		container.add(STAGECLEAR);
		container.add(OVER);
	}
}

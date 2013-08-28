package jp.ac.kccollege.ohya.android.framework.game2D;

import java.util.ArrayList;
import java.util.List;

/**抽象クラス*/
public abstract class Scenes {
	/**シーンリスト*/
	private final List<Scene> scenes;
	/**コンストラクタ*/
	protected Scenes() {
		scenes = new ArrayList<Scene>();
		appendAll(scenes);
	}
	//抽象メソッド
	/**シーンの起動*/
	public abstract Scene bootScene();
	/***/
	protected abstract void appendAll(List<Scene> container);
	/***/
	public Iterable<Scene> scenes() {
		return scenes;
	}
}

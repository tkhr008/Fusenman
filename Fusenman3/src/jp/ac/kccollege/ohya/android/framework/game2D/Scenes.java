package jp.ac.kccollege.ohya.android.framework.game2D;

import java.util.ArrayList;
import java.util.List;

/**���ۃN���X*/
public abstract class Scenes {
	/**�V�[�����X�g*/
	private final List<Scene> scenes;
	/**�R���X�g���N�^*/
	protected Scenes() {
		scenes = new ArrayList<Scene>();
		appendAll(scenes);
	}
	//���ۃ��\�b�h
	/**�V�[���̋N��*/
	public abstract Scene bootScene();
	/***/
	protected abstract void appendAll(List<Scene> container);
	/***/
	public Iterable<Scene> scenes() {
		return scenes;
	}
}

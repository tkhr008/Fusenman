package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;
import jp.ac.kccollege.ohya.android.fusenman.unit.InterfaceShooter;

/** �X�e�[�W�{�X�Ǘ��N���X */
public class StageBoss1 extends AbstractEnemy
implements InterfaceShooter {

	/** ���� */
	private float accel = 3;

	/** �R���X�g���N�^ */
	public StageBoss1(int type) {
		// super(�L�����^�C�v,x,y,w,h)
		super(type, 0, 0, 100, 100);
	}

	/** ������ */
	@Override
	public void init() {
		super.init();
		hSpeed = accel;
		life = 30;
		chanceOfShot = 2.0f;// �e�𔭎˂���m��
	}

	/** �J�n */
	@Override
	public void start(GameView view) {
		super.start(view);
		accel += 3;// �����l�̑���
		hSpeed = (float) (Math.random() * accel);// x���X�s�[�h�̕ω�
	}

	/** ������Ԃ̍X�V */
	@Override
	public void process(GameView view) {
		super.process(view);

		switch (status) {

		case LIVE:// ����
		case DAMAGE:// �_���[�W

			if (shotReady(chanceOfShot)) {// �������ł�����U��
				attack();
			}
			char_x -= hSpeed; // �ړ�
			yuragi(0.3f, 8.0f); // ��炬
			break;

		default:
			break;
		}

		// ���݂̈ʒu�X�V
		offset(char_x, char_y);
	}

	/** �_���[�W���󂯂��� */
	public void damage() {
		super.damage();
		if (life % 8 == 0) {
			hSpeed = -8;// ���̃_���[�W���ƂɌ���ړ�
			sizeUp();
		}
	}

	/** �U������ */
/*	private boolean shotReady() {

		//�U���̃^�C�~���O�𗐐��Ŕ��f
		if(char_y == (int)(Math.random() * char_y) + 1){
			return true;
		}
		return false;
	}*/

	/**�e����*/
	@Override
	public int shoot() {
		live();
		return Fusenman.SHOT_OF_ENEMY ;
	}
}

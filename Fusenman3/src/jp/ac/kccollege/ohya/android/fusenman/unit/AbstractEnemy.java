package jp.ac.kccollege.ohya.android.fusenman.unit;

import android.graphics.Canvas;
import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;

/** �G���ʐݒ�p�̒��ۃN���X */
public abstract class AbstractEnemy extends AbstractUnit {

	// static�ϐ�
	/** ���݂̓G�� */
	private static int currentNum = 0;

	// �C���X�^���X�ϐ�
	/** �e�𔭎˂���m�� */
	protected float chanceOfShot = 0.0f;

	// static���\�b�h
	/** ���݂̓G����Ԃ� */
	public static int getCurrentNum() {
		return currentNum;
	}

	/** �m���ɏ]���čU���̃^�C�~���O�����肷�� */
	protected static boolean shotReady(float chanseOfShot) {
		if (chanseOfShot > Math.random() * 100) {
			return true;
		}
		return false;
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param _x
	 *            �����x���W
	 * @param _y
	 *            �����y���W
	 * @param _w
	 *            �L�����̕�
	 * @param _h
	 *            �L�����̍���
	 */
	protected AbstractEnemy( float _x, float _y, float _w, float _h) {
		super(_x, _y, _w, _h);
	}
	
	/** ������ */
	public void init() {
		status = Status.INIT;//������
		currentNum++;// �G�`�[��������
		myImage = images2.get(myType);// �摜�̐ݒ�
		myImage.setVisible(false, true);// �s��
		size = Size.M;// �T�C�Y���Z�b�g
		resize(size);
		alpha = 255;
	}

	/** �J�n���� */
	public void start(GameView view) {
		status = Status.START;
		// �o���ʒu�̌���
		float bottom = view.getDrawRect().bottom;
		float top = view.getDrawRect().top;
		char_y = (int) (Math.random() * (bottom - char_h + top));// �����_��
		char_x = view.getDrawRect().right;

		// �o���̃^�C�~���O�����炷(�c�ʒu�̊����ɂ���Čv�Z�j
		waitTime = (int) (Math.random() *(char_y % 5 * 100));
		myImage.setVisible(true, true);// ��

	}

	/** ������Ԃ̍X�V */
	@Override
	public void process(GameView view) {
		switch (status) {

		case INIT:// ������
			start(view);
			break;
			
		case START:// �J�n
		case READY:// ����
			if (waitTime-- <= 0) {
				live();//������
			}
			break;

		case LIVE:// ����
			if (isOutside(view.getDrawRect())) {
				start(view);// �ăX�^�[�g
			}
			break;

		case DAMAGE:// �_���[�W

			if (life > 0) {// life���c���Ă�����
				live();// ����������
			} else {
				damage();// �_���[�W�����p��
			}
			break;

		default:
			break;
		}
	}

	/** �������� */
	public void ready() {
		status = Status.READY;
		waitTime = 10;
	}

	/** �������� */
	protected void live() {
		if (status == Status.DAMAGE) {
			myImage = images2.get(myType);// �摜�̃��Z�b�g
		}
		status = Status.LIVE;
	}

	/** �_���[�W���󂯂��� */
	public void damage() {
		if (status != Status.DAMAGE) {
			status = Status.DAMAGE;
			myImage = images2.get(CharType.BOMB);// �����摜�ɕύX
		}

		if (life-- <= 0) {// ���C�t��0�ɂȂ����Ƃ�
			// �������o���ł��Ȃ��Ȃ����Ƃ�
			if (!bomb()) {
				dead();// ����
			}
		}
	}

	/**
	 * ��������
	 * 
	 * @return true �����������@false �����I��
	 */
	protected boolean bomb() {
		alpha -= 10;
		// ���S�ɓ����ɂȂ����甚���I��
		if (alpha <= 0) {
			return false;
		}

		myImage.mutate().setAlpha(alpha);// �A���t�@�l�̕ύX

		return true;
	}

	/** ���ł����� */
	public void dead() {
		status = Status.DEAD;
		currentNum--;// �G������
	}

	public void draw(Canvas c) {
		if (status == Status.DEAD || status == Status.INIT) {
			return;
		}
		// �`��
		myImage.setBounds((int) left, (int) top, (int) right, (int) bottom);
		myImage.draw(c);
	}
}

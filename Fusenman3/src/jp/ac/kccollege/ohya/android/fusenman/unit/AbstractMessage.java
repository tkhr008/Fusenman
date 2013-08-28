package jp.ac.kccollege.ohya.android.fusenman.unit;

import android.graphics.Canvas;
import android.graphics.Paint;
import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman;

/** ��ʂɃ��b�Z�[�W��\������N���X */
public abstract class AbstractMessage extends AbstractUnit {

	// �C���X�^���X�ϐ�
	/** �e�𔭎˂���m�� */
	protected float chanceOfShot = 0.0f;
	protected String message;
	protected Paint paint;
	/**
	 * �R���X�g���N�^
	 * 
	 * @param type
	 *            �L�����^�C�v
	 * @param _x
	 *            �����x���W
	 * @param _y
	 *            �����y���W
	 * @param _w
	 *            �L�����̕�
	 * @param _h
	 *            �L�����̍���
	 */
	AbstractMessage(float _w, float _h) {
		super(Fusenman.MESSAGE1,0,0, _w, _h);
		init();// ��������
	}

	/** ������ */
	public abstract void init();

	/** �J�n���� */
	public void start(GameView view) {
		status = Status.START;
		// �o���ʒu�̌���
		//char_y = view.getDrawRect().centerY();
		//char_x = view.getDrawRect().right;
		//myImage.setVisible(true, true);// ��
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
				dead();// ����
			}else{
				//���݂̈ʒu�X�V
				char_x += hSpeed;
				offset(char_x,char_y);
			}
			break;

		default:
			break;
		}
	}

	/** ���ł����� */
	public void dead() {
		status = Status.DEAD;
	}
	
	/** �������� */
	protected void live() {
		status = Status.LIVE;
	}
	
	/**�`�揈��*/
	public void draw(Canvas c) {
		if (status == Status.DEAD || status == Status.INIT) {
			return;
		}

		c.drawText(message, char_x, char_y, paint);
		// �`��
		//myImage.setBounds((int) left, (int) top, (int) right, (int) bottom);
		//myImage.draw(c);
	}
	
	/** �_���[�W���󂯂��� */
	public void damage() {

	}
}

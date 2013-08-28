package jp.ac.kccollege.ohya.android.fusenman.unit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman;

/** ��ʂɃ��b�Z�[�W��\������N���X */
public class Message extends AbstractUnit {

	// �C���X�^���X�ϐ�
	private String message;
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
	 * @param textScaleX
	 *            �����̐����T�C�Y
	 * @param textSize
	 *            �����T�C�Y
	 */
	public Message(String message, float textScaleX, float textSize) {
		super(Fusenman.MESSAGE1,0,0, textScaleX,textSize);
		this.message=message;
	}

	/** ������ */
	public void init() {
		status = Status.INIT;
		hSpeed= -3*(int)Math.random()*6;
		paint = new Paint();
		paint.setTextAlign(Paint.Align.RIGHT);
		paint.setAntiAlias(true);
		paint.setColor(Color.MAGENTA);
		
		paint.setTextSize(char_h);
		paint.setTextScaleX(char_w);
		//myImage = images[type];// �摜�̐ݒ�
		//myImage.setVisible(false, true);// �s��

		//alpha = 255;
	}

	/** �J�n���� */
	public void start(GameView view) {
		status = Status.START;
		// �o���ʒu�̌���
		hSpeed= -8;
		char_y = (int)(Math.random()*view.getDrawRect().bottom);
		char_x = view.getDrawRect().right;
		//myImage.setVisible(true, true);// ��
		paint = new Paint();
		paint.setTextAlign(Paint.Align.RIGHT);
		paint.setAntiAlias(true);
		paint.setColor(Color.MAGENTA);
		
		paint.setTextSize(char_h);
		paint.setTextScaleX(char_w);
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
			//if (isOutside(view.getDrawRect())) {
			if (char_x + char_w < 0) {
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
		
		//koko
/*		paint = new Paint();
		paint.setTextAlign(Paint.Align.RIGHT);
		paint.setAntiAlias(true);
		paint.setColor(Color.GREEN);
		paint.setTextSize(char_h);
		paint.setTextScaleX(char_w);*/
		if(paint != null){
			c.drawText(message, char_x,char_y, paint);
		}
		// �`��
		//myImage.setBounds((int) left, (int) top, (int) right, (int) bottom);
		//myImage.draw(c);
	}
	
	/** �_���[�W���󂯂��� */
	public void damage() {

	}
}

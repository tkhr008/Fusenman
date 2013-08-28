package jp.ac.kccollege.ohya.android.fusenman.unit;

import android.graphics.RectF;

/** �I�u�W�F�N�g�̗̈���Ǘ����钊�ۃN���X */
public abstract class AbstractRect extends RectF {

	//�C���X�^���X�ϐ�
	/**�I�u�W�F�N�g x���W*/
	protected float char_x = 0;
	/**�I�u�W�F�N�g y���W*/
	protected float char_y = 0;
	/**�I�u�W�F�N�g ��*/
	protected float char_w=0;
	/**�I�u�W�F�N�g ����*/
	protected float char_h=0;

	/**
	 * �R���X�g���N�^
	 * @param char_x�@��������x���W
	 * @param char_y�@��������y���W
	 * @param char_w�@�I�u�W�F�N�g�̕�
	 * @param char_h�@ �I�u�W�F�N�g�̍���           
	 */
	protected AbstractRect(float char_x, float char_y, float char_w, float char_h) {
		//�X�[�p�[�N���X�Ăяo��
		super(char_x, char_y, char_x + char_w,char_y+ char_h);
		
		//�I�u�W�F�N�g�G���A�������ݒ�
		this.char_x = char_x;
		this.char_y = char_y;
		this.char_w = char_w;
		this.char_h = char_h;
	}

	/**
	 * �����蔻��(�~)
	 * @param enemyRect �G�L�����͈�
	 * @return true:������,false:�͂���
	 */
	public boolean judge(RectF enemyRect) {

		// �����ƓG�̒��S�ԋ������v�Z
		float dx = centerX() - enemyRect.centerX();
		float dy = centerY() - enemyRect.centerY();
		float distance = (float) Math.sqrt(dx * dx + dy * dy);

		// ���S�ԋ��������a�̍��v��菬���������瓖����B
		if (distance <= ((enemyRect.width() + width()) * 0.5f)) {
			return true;
		}
		return false;
	}

	/**
	 * ������G���A��Ԃ�
	 * @return RectF ������G���A
	 */
/*	public RectF getArea() {
		// ������G���A�ƃL�����̈ʒu�𓝈�
		//myRect.offsetTo(char_x, char_y);
		return this;
	}*/

	/**
	 * �I�u�W�F�N�g�̍�����W�̐ݒ�
	 * @param x
	 * @param y
	 */
	public void offset(float x, float y) {
		char_x = x;
		char_y = y;

		// ������G���A�ƃL�����̈ʒu�𓝈�
		set(char_x,char_y,char_x+char_w,char_y+char_h);
	}
	
	/**
	 * �L�����o�X�̊O�ɂł���
	 * 
	 * @return true:�G���A�O�@false:�G���A��
	 */
	public boolean isOutside(final RectF viewRect) {
		//�G���A�O���H
		if ( ! intersect(viewRect) ){
			return true;
		}
		return false;
	}

}

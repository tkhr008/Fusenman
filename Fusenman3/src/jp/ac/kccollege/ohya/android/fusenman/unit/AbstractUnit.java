package jp.ac.kccollege.ohya.android.fusenman.unit;

import java.util.HashMap;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;

/** �Q�[�����j�b�g�N���X */
public abstract class AbstractUnit extends AbstractRect {

	// static�ϐ�----------------------------------------------------------------
	/**�T�C�Y�̎��*/
	protected static enum Size {
		SS,S, M, L,LL,XL
	}; 
	/**�L�����C���[�W�z��*/
	protected static Drawable[] images;
	protected static HashMap<Enum<CharType>,Drawable> images2;
	
	// �C���X�^���X�ϐ�----------------------------------------------------------------
	protected CharType myType=null;
	
	/**�������g�̉摜*/
	protected Drawable myImage; 
	 /**�T�C�Y*/
	protected Size size = Size.M;
	/**���*/
	protected Status status = Status.INIT;
	/**�������̑��x*/
	protected float hSpeed = 0; 
	/**�c�����̑��x*/
	protected float vSpeed=0;
	/**�����W */
	protected float char_z = 0;
	/**�L�����̃f�t�H���g�̕�*/
	protected float charDef_w=0;
	/**�L�����̃f�t�H���g�̍���*/
	protected float charDef_h=0;
	
	 /**�L�����^�C�v*/
	protected int type=0;
	/**�A���t�@�l*/
	protected int alpha = 255; 
	/**�c�@*/
	protected int life=1; 
	/**�҂�����*/
	protected int waitTime=0;	
	/**�㏸���邩�ǂ���*/	
	protected boolean up = true;	
	
	//�R���X�g���N�^----------------------------------------------------------------
	/**
	 * �R���X�g���N�^
	 * @param type �L�����^�C�v
	 * @param char_x
	 *            �@ ��������x���W
	 * @param char_y
	 *            �@��������y���W
	 * @param char_w
	 *            �@�I�u�W�F�N�g�̕�
	 * @param char_h
	 *            �I�u�W�F�N�g�̍���
	 */
	AbstractUnit(int type,float _x, float _y, float _w, float _h){
		super(_x, _y, _w, _h);
		this.type = type;//�L�����^�C�v�̐ݒ�
		charDef_w = _w;
		charDef_h = _h;
	}
	AbstractUnit(float _x, float _y, float _w, float _h){
		super(_x, _y, _w, _h);
		charDef_w = _w;
		charDef_h = _h;
	}
	//static ���\�b�h----------------------------------------------------------------
	/**�Q�[�����j�b�g�̉摜��ێ�*/
	/*
	public static void setUnitImages(Drawable[] images){
		AbstractUnit.images = images;
	}
	*/
	public static void setUnitImages(HashMap<Enum<CharType>,Drawable> images){
		AbstractUnit.images2 = images;
	}	
	// ���ۃ��\�b�h----------------------------------------------------------------
	/** ������ */
	public abstract void init();
	/** �J�n */
	public abstract void start(GameView view);	
	/** ������Ԃ̍X�V */
	public abstract void process(GameView view);
	/** �`�� * @param canvas �L�����o�X */
	public abstract void draw(Canvas canvas);
	/**�_���[�W*/
	public abstract void damage();

	// ��ۃ��\�b�h----------------------------------------------------------------
	/**�T�C�Y�ύX*/
	protected void resize(Size newSize) {
		if (size == newSize) {return;}//�ύX�Ȃ�
		
		float value = 0;
		switch (newSize) {	
		case SS:	value = 0.25f;		break;		
		case S:		value = 0.5f;		break;
		case M:		value = 1.0f;		break;
		case L:		value = 1.5f;		break;
		case LL:	value = 2.0f;		break;
		case XL:	value = 2.5f;		break;
		}
		//�V�����T�C�Y�ŋ�`�̒l��ݒ�
		char_w = charDef_w * value;
		char_h = charDef_h * value;
		size = newSize;// ���݂̃T�C�Y�̍X�V

	}
	/**�T�C�Y�ɂ��{�����擾*/
/*	protected float  getSize() {
		float value=0;
		switch (size) {	
		case SS:	value = 0.25f;		break;		
		case S:		value = 0.5f;		break;
		case M:		value = 1.0f;		break;
		case L:		value = 1.5f;		break;
		case LL:	value = 2.0f;		break;
		case XL:	value = 2.5f;		break;
		}
		return value;

	}*/
	/**�T�C�Y�A�b�v*/
	protected void sizeUp(){
		switch(size){
		case SS:	resize(Size.S);break;
		case S:	resize( Size.M);break;
		case M:	resize( Size.L);break;
		case L:	resize( Size.LL);break;
		case LL:	resize( Size.XL);break;
		case XL:	break;
		}
	}
	
	/**�T�C�Y�_�E��*/
	protected void sizeDown(){
		switch(size){
		case XL:	resize( Size.LL);break;
		case LL:	resize( Size.L);break;
		case L:	resize( Size.M);break;
		case M:	resize( Size.S);break;
		case S:	resize( Size.SS);break;
		case SS:	break;
		}
	}
	
	/**��炬�����
	 * @param speed �c�����̑��x
	 * @param range �c�����̐U�ꕝ
	 */
	protected void yuragi(float speed,float range){

		if (up){
			vSpeed -= speed;// ����
		}else{
			vSpeed += speed;// ����
		}
		char_y += (int) vSpeed;// y�����ړ�
	
		if (vSpeed > range || vSpeed <= -range){// �㏸�̐؂�ւ�
			up = !up;
		}
	}
	
	/**�������g�����ł�����
	 * @return dead ���Ńt���O
	 */
	public boolean isDead(){
		return status.isDead();
	}
	
	/**�������g���_���[�W�����ǂ���
	 * @return boolean �_���[�W�t���O
	 */
	public boolean isDamage(){
		return status.isDamage();
	}	
	
	/**�����̃^�C�v��Ԃ�*/
	public int getType(){
		return type;
	}
	
	/**���g�̏�Ԃ�Ԃ�*/
	public Status getStatus(){
		return status;
	}
	
	/** �U������ */
	public void attack() {
		if(status.isLive()){
			status = Status.ATTACK;//�U��
		}
	}
	
	/**��������*/
	public void fixDirection(float x, float y){
		double radian = Math.atan2(x - char_x, y- char_y);
		vSpeed = (float)(Math.cos(radian)*vSpeed);
		hSpeed = (float)(Math.sin(radian)*hSpeed);

	}
	
	/**�L�����^�C�v�̈�v*/
	public boolean equals(CharType type){
		if(myType == null){
			return false;
		}else{
			return myType.equals(type);
		}
	}

}

package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import android.graphics.Canvas;

/**Player�N���X
 * @author Takahiro Ohya
 */
public abstract class AbstractPlayer extends AbstractUnit {

	//static�ϐ�
	/**���݂̖�����*/
	static int currentNum=0;
	
	//static���\�b�h
	/**���݂̖�������Ԃ�*/
	public static int getCurrentNum(){
		return currentNum;
	}
	
	/**�R���X�g���N�^
	 * @param type �L�����^�C�v
	 * @param _x �����x���W
	 * @param _y �����y���W
	 * @param _w �L�����̕�
	 * @param _h �L�����̍���
	 */
	public AbstractPlayer(final int type, float _x, float _y,float _w, float _h){
		super(type,_x,_y,_w, _h);
		init();//��������
	}
	
	/**������*/
	public void init(){
		status = Status.INIT;
		currentNum++;//�ǉ�
		//myImage = images[type];//�摜�̐ݒ�
		myImage = images2.get(CharType.ANPANMAN);
		myImage.setVisible(false, true);//�s��
	}

	/**��������*/
	public void ready(){
		status = Status.READY;
	}

	/**��������*/
	public void live(){
		status = Status.LIVE;//����
	}
	
	/**�_���[�W����*/
	public void damage(){
		status = Status.DAMAGE;
		life--;//���C�t��
	}

	/**���ŏ���*/
	public void dead(){
		status = Status.DEAD;//����
		currentNum--;
	}

	/** �`��
	 * @param�L�����o�X�@��ʕ`��p
	 */
	public void draw(Canvas c){

		if(status == Status.DEAD || status == Status.INIT){
			return;
		}
		
		//�`��
		myImage.setBounds((int)left,(int)top,(int)right,(int)bottom);
		myImage.draw(c);
	}
}
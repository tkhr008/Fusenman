package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;

/**�G�̒e�N���X*/
public class ShotOfEnemy extends AbstractEnemy{
	
	private float shotSpeed=4.0f;//�e�̑��x
	
	/**�R���X�g���N�^
	 * @param char_x ����X���W
	 * @param char_y�@����Y���W
	 * */
	ShotOfEnemy(){
		super(0,0,25,25);//super(x,y,w,h)	
		myType = CharType.ESHOT;
		init();
	}
	
	@Override
	public void init(){
		//super.init();//�G�e�͓G���ƃJ�E���g���Ȃ�����
		status = Status.LIVE;//�X�e�[�^�X�ύX	
		myImage = images2.get(myType);//�摜�̃��Z�b�g
		alpha=255;
	}
	/**�J�n����*/
	//@Override
	public void start(){
		//super.start();
		//shotSpeed=3.0f;
		status = Status.LIVE;//�X�e�[�^�X�ύX	
		//myImage = images[type];		//�摜�̃��Z�b�g
		//myImage = images2.get(myType);
		//alpha=255;
		init();
	}
	
	//�C���X�^���X���\�b�h
	/**������Ԃ̍X�V	 */
	@Override
	public void process(GameView view){

		switch(status){
		
		case LIVE:	//����
			//��ʂ̊O�ɂł������
			if (isOutside(view.getDrawRect() ) ) {
				dead();//����
				break;
			}
			char_x += hSpeed;	//X�ړ�
			char_y += vSpeed;	//Y�ړ�
			break;
			
		default:
			break;
		}
		
		//���݂̈ʒu�X�V
		offset(char_x,char_y);
	}
	
	/**��������*/
	public void fixDirection(float x, float y){
		double radian = Math.atan2(x - char_x, y- char_y);
		vSpeed = (float)(Math.cos(radian)*shotSpeed);
		hSpeed = (float)(Math.sin(radian)*shotSpeed);

	}
	
	/**�_���[�W���󂯂���*/
	@Override
	public void damage(){
		dead();//�Փ˂ŏ���
	}
	
	/**���ł����� */
	@Override
	public void dead(){
		//super.dead();		//�e���͓G�̐��Ƃ��ăJ�E���g���Ȃ�����
		status = Status.DEAD;

	}
}

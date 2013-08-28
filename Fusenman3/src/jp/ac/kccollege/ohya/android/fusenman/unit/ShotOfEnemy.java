package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;

/**�G�̒e�N���X*/
public class ShotOfEnemy extends AbstractEnemy{
	
	private float shotSpeed=2.0f;//�e�̑��x
	
	/**�R���X�g���N�^
	 * @param type�@�L�����̃^�C�v
	 * @param char_x ����X���W
	 * @param char_y�@����Y���W
	 * */
	ShotOfEnemy(int type){
		//super(�L�����^�C�v,x,y,w,h)	
		super(type,0,0,25,25);
	}
	
	@Override
	public void init(){
		shotSpeed=2.0f;
		status = Status.LIVE;//�X�e�[�^�X�ύX	
		myImage = images[type];		//�摜�̃��Z�b�g
		alpha=255;
	}
	/**�J�n����*/
	//@Override
	public void start(){
		//super.start();
		shotSpeed=3.0f;
		status = Status.LIVE;//�X�e�[�^�X�ύX	
		myImage = images[type];		//�摜�̃��Z�b�g
		alpha=255;
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
		status = Status.DEAD;
		//�e���͓G�̐��Ƃ��ăJ�E���g���Ȃ�
	}
}

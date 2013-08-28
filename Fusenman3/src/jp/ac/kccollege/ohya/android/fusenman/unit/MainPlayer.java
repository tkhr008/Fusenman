package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;



/**MainPlayer�N���X
 * @author Takahiro Ohya
 */
public class MainPlayer
	extends AbstractPlayer implements InterfaceShooter{

	//�C���X�^���X�ϐ�
	/**�c�@*/
	private final int LIFE = 3;
	
	/** �R���X�g���N�^ */
	public MainPlayer(int type){
		//super(�L�����^�C�v,x,y,w,h)
		super(type,0, 0, 50,50);
	}

	/**������*/
	public void init(){
		super.init();
		life=LIFE;//�c�@�̃��Z�b�g
		size= Size.M;//�T�C�Y�ύX
		//myImage = images2.get(CharType.AHIRU);
	}
	
	/**��������(���G����)*/
	public void ready(){
		super.ready();
		waitTime = 50;//���G�^�C���̐ݒ�
		alpha = 150;//�A���t�@�l�̕ύX
		myImage.mutate().setAlpha(alpha);
	}

	/**�J�n����*/	
	@Override
	public void start(GameView view) {
		//�����ɔz�u	
		offset(view.getDrawRect().centerX(),view.getDrawRect().centerY() );
	}

	/**
	 * ������Ԃ̍X�V
	 * @param view �Q�[���r���[
	 **/
	public void process(GameView view) {
		
		//��Ԃɂ���ĕ���
		switch(status){
		
		case INIT://������
			ready();
			start(view);
			break;
			
		case READY://����
			if(waitTime-- <= 0){
				live();
			}
			break;		
			
		case DAMAGE://�_���[�W
			if(life > 0){
				ready();
			}else{
				dead();
			}
			break;
		
		case LIVE://����
			// �����x�Z���T�[�̒l�ɂ��ړ�
			//move(view.gravity_x, view.gravity_y,view.gravity_z);
			//��炬
			yuragi(0.25f,1.0f);
			
			break;
			
		default:
			break;
		}
		
		
		//���݂̈ʒu�X�V
		offset(char_x,char_y);
	}
	
	/** �ړ� */
	public void move(int diffX, int diffY) {
		char_x += diffX;
		char_y += diffY;
		//lotate((int)char_x);
	}
 
	/** �����x�Z���T�[�ɂ��ړ� */
	private void move(float gravity_x, float gravity_y,float gravity_z) {

		// �����x �������A�v���Ȃ̂ŁAgravity_x,gravity_y������
		char_x += (int) ((gravity_y * 100) / 100) - 4;// �����x��ǉ�( 4�͒����l�j
		char_y += (int) ((gravity_x * 100) / 100) - 4;// �����x��ǉ�( 4�͒����l�j
			
		//z�����̉����x�ɂ���ăT�C�Y�ύX
		if(char_z - gravity_z  < -4 && gravity_z > 12){
			sizeUp();
			
		}else if(char_z - gravity_z  > 8 && gravity_z < 0){
			sizeDown();
		}
		char_z = gravity_z;
	}
	
	/**��������*/
	public void live(){
		myImage = images[type];//�摜�̃��Z�b�g
		alpha = 255;//�����x0%
		myImage.setAlpha(alpha);//�A���t�@�l�̕ύX		
		myImage.clearColorFilter();//�J���[�t�B���^�̃��Z�b�g
		super.live();
	}

	/**�_���[�W����*/
	public void damage(){
		super.damage();
		//�J���[�t�B���^�ݒ�
		myImage.setColorFilter(Color.RED, Mode.MULTIPLY);
	}

	/**���ŏ���*/
	public void dead(){
		super.dead();
		myImage=images[BOMBIMAGE];
		myImage.clearColorFilter();//�J���[�t�B���^�̃��Z�b�g
	}
	
	/**�e����*/
	@Override
	public int shoot() {
		super.live();//�����փX�e�[�^�X�ύX
		return 1;
	}
}

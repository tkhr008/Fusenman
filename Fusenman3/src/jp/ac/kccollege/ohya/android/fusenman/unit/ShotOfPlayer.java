package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;

/**�e�N���X*/
public class ShotOfPlayer extends AbstractPlayer{

	/**�R���X�g���N�^
	 * @param type�@�L�����̃^�C�v
	 * @param size �T�C�Y
	 * @param char_x ����X���W
	 * @param char_y�@����Y���W
	 * */
	ShotOfPlayer(int type){
		//super(�L�����^�C�v,x,y,w,h)
		super(type,0,0,50,50);
	}	
	ShotOfPlayer(){
		//super(�L�����^�C�v,x,y,w,h)
		super(0,0,50,50);
		myType = CharType.PSHOT;
		init();//��������
	}		
	//static���\�b�h
	/**�X�s�[�h�ύX*/
	private  static float changeSpeed(Size size){
		float speed=0;
		switch(size){
		case SS:	speed =6;	break;
		case S:	speed =6;	break;
		case M:	speed =4;	break;
		case L:	speed =4;	break;
		case LL:	speed =2;	break;
		case XL:	speed =2;	break;
		}
		return speed;
	}

	//�C���X�^���X���\�b�h
	/**������*/
	public void init(){
		super.init();
		life=1;
		resize(size);//�T�C�Y�ύX
		hSpeed = changeSpeed(size);//�X�s�[�h�ύX
		//myType = CharType.PSHOT;
		myImage = images2.get(myType);
	}
	
	/**�J�n����*/
	@Override
	public void start(GameView view) {
		myImage.setVisible(true, true);//��
	}

	/**������Ԃ̍X�V	 */
	@Override
	public void process(GameView view){
		//��Ԃɂ���ĕ���
		switch(status){
		
		case INIT://������
			start(view);
			live();//������
			break;
						
		case LIVE://����
			
			//��ʉE�[�܂ňړ�
			if (! isOutside(view.getDrawRect())) {
				char_x += hSpeed;
				char_y += vSpeed;
				yuragi(0.15f,2.0f);//��炬
		
			}else{
				dead();//����
			}
			break;
		
		case DAMAGE://�_���[�W
			if(life <= 0){
				dead();
			}
			break;
			
		default:
			break;
		}
		
		//���݂̈ʒu�X�V
		offset(char_x,char_y);
	}

	/**���ł������̏���*/
	public void dead(){
		super.dead();
		char_x = 0;
		char_y = 0;
		myImage.setVisible(false, true);//�s��
		
	}

}

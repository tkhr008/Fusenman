package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;
/**�G�L�����@�L���[*/
public class Killer extends AbstractEnemy {

	/** �R���X�g���N�^ */
	public Killer(int type){
		//super(�L�����^�C�v,x,y,w,h)		
		super(type,0,0,50,30);
	}

	@Override
	public void init(){
		super.init();
		hSpeed = 5;//�����X�s�[�h�ݒ�
		life=2;//���C�t�ݒ�
	}
	
	/* (�� Javadoc)������Ԃ̍X�V */
	@Override
	public void process(GameView view) {
		super.process(view);

		switch(status){
	
		case LIVE://����
		case DAMAGE://�_���[�W
			char_x -= hSpeed; // �ړ�
			break;
		default:
			break;
		}
		
		//���݂̈ʒu�X�V
		offset(char_x,char_y);
	}

}

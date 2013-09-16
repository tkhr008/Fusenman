package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;

/**�G�L�����@�L���[*/
public class Tsubame extends AbstractEnemy {

	/** �R���X�g���N�^ */
	public Tsubame(){
		super(0,0,50,50);//super(x,y,w,h)
		myType = CharType.TSUBAME;
		init();
	}	
	@Override
	public void start(GameView view) {
		super.start(view);
		hSpeed=5;//�������̑��x
	}

	/* ������Ԃ̍X�V */
	@Override
	public void process(GameView view) {
		super.process(view);

		switch(status){
		
		case LIVE://����
		case DAMAGE://�_���[�W
			char_x -= hSpeed; // x�����ړ�
			yuragi(0.15f,2.0f);//��炬
			break;
		default:
			break;
		}
		
		//���݂̈ʒu�X�V
		offset(char_x,char_y);
	}
}

package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;

/**�G�L�����@�J���X*/
public class Karasu extends AbstractEnemy{
	
	/**�R���X�g���N�^*/
	public Karasu(int type){
		//super(�L�����^�C�v,x,y,w,h)
		super(type,0,0,50,50);
	}
	
	/**������*/
	@Override	
	public void init(){
		super.init();
		hSpeed=4;//�����X�s�[�h�ݒ�
		life=1;//���C�t�ݒ�
	}

	/* (�� Javadoc)������Ԃ̍X�V	 */
	@Override
	public void process(GameView view) {
		super.process(view);
		
		switch(status){
		
		case LIVE:
		case DAMAGE:
			char_x -= hSpeed;	//x�����ړ�
			yuragi(0.15f,4.0f);	//��炬			
			break;
			
		default:
			break;
		}
		//���݂̈ʒu�X�V
		offset(char_x,char_y);
	}
}

package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;

public class Anpanman extends AbstractEnemy{

	/**�R���X�g���N�^*/
	public Anpanman(){
		super(0,0,65,55);//super(x,y,w,h)
		myType = CharType.ANPANMAN;
		init();
	}	
	/**������*/
	@Override
	public void init(){
		super.init();
		hSpeed = 5;//x�����X�s�[�h�l
		chanceOfShot = 1.0f;//�e�𔭎˂���m��
	}	

	/* (�� Javadoc)������Ԃ̍X�V	 */
	@Override 
	public void process(GameView view) {
		super.process(view);
		
		switch(status){

		case DAMAGE://�_���[�W
		case LIVE://����			
			char_x -= hSpeed; // �ړ�
			char_y -= (int)(Math.random()*5);//0�`4�Ń����_���㏸

			if(shotReady(chanceOfShot)){
				attack();//chanceOfShot�̊m���ōU��
			}
			break;
			
		default:
			break;
		}
		
		//���݂̈ʒu�X�V
		offset(char_x,char_y);
	}
	
}

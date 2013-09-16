package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;
import jp.ac.kccollege.ohya.android.fusenman.unit.InterfaceShooter;

/**�X�e�[�W�{�X�Ǘ��N���X*/
public class StageBoss3 extends AbstractEnemy
implements InterfaceShooter {

	/**����*/
	private float accel=3;
	
	/** �R���X�g���N�^ */
	public StageBoss3() {
		super(0, 0, 100, 100);// super(x,y,w,h)
		myType = CharType.BOSS3;
		init();
	}
	/**������*/
	@Override
	public void init(){
		super.init();
		hSpeed = accel;
		life=100;
		chanceOfShot = 4.0f;//�e�𔭎˂���m��
	}

	/**�J�n*/
	@Override
	public void start(GameView view) {
		super.start(view);
		accel += 3;//�����l�̑���
		hSpeed = (float)(Math.random()*accel);//x���X�s�[�h�̕ω�
	}
	
	/**������Ԃ̍X�V */
	@Override
	public void process(GameView view) {
		super.process(view);

		switch(status){
	
		case LIVE://����
		case DAMAGE://�_���[�W
			char_x -= hSpeed; // �ړ�
			char_y -= (int)(Math.random()*5);//�㏸
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

	/**�_���[�W���󂯂���*/
	public void damage(){
		super.damage();
		if(life%10==0)
		hSpeed = -8;//����ړ�
	}
	
	/**�e����*/
	@Override
	public CharType shoot() {
		live();
		return CharType.ESHOT;
	}
}

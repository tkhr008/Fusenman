package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;
import jp.ac.kccollege.ohya.android.fusenman.unit.InterfaceShooter;

/**�X�e�[�W�{�X�Ǘ��N���X*/
public class StageBoss2 extends AbstractEnemy
implements InterfaceShooter {

	/**����*/
	private float accel=3;
	
	/** �R���X�g���N�^ */
	public StageBoss2(int type){
		//super(�L�����^�C�v,x,y,w,h)	
		super(type,0,0,200,120);
	}

	/**������*/
	@Override
	public void init(){
		super.init();
		hSpeed = accel;//�����l�̐ݒ�
		life=70;//���C�t�̐ݒ�
		chanceOfShot = 3.0f;//�e�𔭎˂���m��
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
			if(shotReady(chanceOfShot)){
				attack();//chanceOfShot�̊m���ōU��
			}
			//break;//�Ӑ}�I
			
		case DAMAGE://�_���[�W
			char_x = (char_x - hSpeed)*1.01f;//��������
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
		if(life%10==0){
			hSpeed = -8;//10�_���[�W���ƂɌ���ړ�
		}
	}
	
	/**�e����*/
	@Override
	public int shoot() {
		live();
		return Fusenman.SHOT_OF_ENEMY ;
	}
}
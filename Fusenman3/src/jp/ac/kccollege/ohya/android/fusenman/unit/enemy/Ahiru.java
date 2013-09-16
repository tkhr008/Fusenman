package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;

public class Ahiru extends AbstractEnemy{

	private float gravity=0;//�d��
	
	/**�R���X�g���N�^*/
	public Ahiru(){
		super(0,0,50,50);
		myType = CharType.AHIRU;
		init();
	}
	
	/**������	 */
	@Override
	public void init(){
		super.init();
		life=3;
	}
	
	/**�J�n����	 */	
	@Override
	public void start(GameView view) {
		super.start(view);	
		vSpeed = (int) (char_x%20+10);//10�`24�̏c�������x
		hSpeed = char_y%20+3;//3�`22�̑��x
		gravity=1;//�d��
	}

	/**������Ԃ̍X�V	
	 * @param GameView view
	 */
	@Override
	public void process(GameView view) {
		super.process(view);

		switch(status){
			
		case LIVE:
		case DAMAGE:
			char_x -= hSpeed;	//X�ړ�
			char_y -= vSpeed;	//Y�ړ�
			vSpeed -= gravity;
			break;
			
		default:
			break;
		}
		
		//���W�ϊ��e�X�g�iOpenGL�̏ꍇ�j
		float x = GameView.toGlPoint((int)char_x,true,true);
		char_x = GameView.toCanvasPoint(x,true,true); 
		float y = GameView.toGlPoint((int)char_y,false,true);
		char_y = GameView.toCanvasPoint(y,false,true); 
		
		//���݂̈ʒu�X�V
		offset(char_x,char_y);
	}
	
	/**�_���[�W����*/
	public void damage(){
		super.damage();
	}

}

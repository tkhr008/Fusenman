package jp.ac.kccollege.ohya.android.fusenman.unit;

import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import jp.ac.kccollege.ohya.android.framework.game2D.GameView;


/** ��ʂɃ��b�Z�[�W��\������N���X */
public class Item extends AbstractUnit {

	// �C���X�^���X�ϐ�
	private float gravity=0;//�d��
	private int numberOfFrames=0;
	AnimationDrawable  anim;
	private int index=1;
	
	/**�R���X�g���N�^*/
	public Item(int type){
		super(type,0,0,50,50);
		myImage = images[type];// �摜�̐ݒ�
		anim = (AnimationDrawable)images[type];
		//numberOfFrames = ((AnimationDrawable)(myImage)).getNumberOfFrames();
		numberOfFrames = anim.getNumberOfFrames();
		init();
	}
	
	/**������	 */
	@Override
	public void init(){
		status = Status.INIT;
		life=3;

	}
	
	/**�J�n����	 */	
	@Override
	public void start(GameView view) {
		status = Status.START;
		hSpeed=4;//�����X�s�[�h�ݒ�
		hSpeed = char_y%20+3;//3�`22�̑��x
		// �o���ʒu�̌���
		float bottom = view.getDrawRect().bottom;
		float top = view.getDrawRect().top;
		char_y = (int) (Math.random() * (bottom - char_h + top));// �����_��
		char_x = view.getDrawRect().right;
	}

	/**������Ԃ̍X�V	
	 * @param GameView view
	 */
	@Override
	public void process(GameView view) {

		switch(status){
		case INIT:// ������
			start(view);
			break;
			
		case START:// �J�n
		case READY:// ����
			if (waitTime-- <= 0) {
				live();//������
			}
			break;			
		case LIVE:
		case DAMAGE:
			char_x -= hSpeed;	//X�ړ�
			char_y -= vSpeed;	//Y�ړ�
			yuragi(0.15f,4.0f);	//��炬		
			break;
			
		default:
			break;
		}
		
		//���݂̈ʒu�X�V
		offset(char_x,char_y);
	}
	
	/**�_���[�W����*/
	public void damage(){

	}
	protected void live() {
		if (status == Status.DAMAGE) {
			myImage = images[type];// �摜�̃��Z�b�g
		}
		status = Status.LIVE;
	}
	@Override
	public void draw(Canvas canvas) {
		if (status == Status.DEAD || status == Status.INIT) {
			return;
		}
		// �`��
		//anim.setBounds((int) left, (int) top, (int) right, (int) bottom);
		//anim.draw(canvas);
		
		if(numberOfFrames-1 > index){
			index++;
		}else{
			index=1;
		}

		//(((AnimationDrawable)myImage).getFrame((int)index)).draw(canvas);
		//myImage=((AnimationDrawable)myImage).getFrame((int)index);
		//myImage= anim.getFrame(index);
		
		myImage.setBounds((int) left, (int) top, (int) right, (int) bottom);
		myImage=anim.getFrame(index);
		myImage.draw(canvas);
	}
}

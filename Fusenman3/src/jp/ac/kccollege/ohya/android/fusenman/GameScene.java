package jp.ac.kccollege.ohya.android.fusenman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractPlayer;


public class GameScene extends AnimationScene {
	/**���[�V�����C�x���g*/
	private MotionEvent event;
	/**���݂�x���W*/
	private int currentX =0;
	/**���݂�y���W*/
	private int currentY = 0;
	/**���[�u�J�n����x���W*/
	private int savedX=0;
	/**���[�u�J�n����y���W*/
	private int savedY=0;
	/**���[�u�����ǂ���*/
	private boolean moving = false;
	
	/** �Q�[���J�n���� */
	private long gameStarted;	
	/** �v���C�c���� */
	private final static long TIME = 300;
	/** �c���� */
	private long remainedTime;

	/** �y�C���g */
	private Paint paint;

	@Override
	public void init(GameView view) {
		super.init(view);

		paint = new Paint();
		paint.setTextAlign(Paint.Align.RIGHT);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setTextSize(32);
	
	}

	
	@Override
	public void start(GameView view) {
		super.start(view);	

		gameStarted = System.currentTimeMillis();//�J�n���Ԃ�ۑ�
		//���W�̏�����
		savedX=0;		savedY=0;	
		currentX=0;	currentY=0;
		//�ړ��`���OFF
		moving=false;
		
		//////////////

		//�Q�[���I�[�o�[��̍ĊJ�������珉����
		if(mainPlayer.isDead()){
			mainPlayer.init();
		}
		//�G�̐���
		enemyFactory.start(stageCount);
		//item test
		itemFactory.create(CharType.ITEM1);
	}

	@Override
	public void process(GameView view) {
		//�X�[�p�[�N���X�̃��\�b�h�Ăяo��
		super.process(view);
		//�c�莞�Ԃ̐ݒ�
		remainedTime = TIME - (System.currentTimeMillis() - gameStarted) / 1000;
		
		if(AbstractPlayer.getCurrentNum() <= 0) {			
			view.startScene(FusenmanScenes.OVER);
			return;
		}
		//�G�̒ǉ�
		if(AbstractEnemy.getCurrentNum() <= 0) {
			
			if( ! enemyFactory.createUnits()){
				view.startScene(FusenmanScenes.STAGECLEAR);
				return;
			}
			
		}		
		
		//�^�b�`�C�x���g�擾
		while ( (event = view.event()) != null) {
			
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.d("Motion", "ActionDown");

				/*���܂��_�E�����������Ȃ�����
				savedX = (int)event.getX();//���W�̕ۑ�
				savedY = (int)event.getY(); 
				moving = true;
				*/
				break;
				
			case MotionEvent.ACTION_UP://��ʂ���w�𗣂�����
				Log.d("Motion", "ActionUp");
				if(moving){
					moving=false;//�h���b�O�I��
				}
				mainPlayer.attack();//�U��
				break;
				
			case MotionEvent.ACTION_MOVE:// ��ʂ��h���b�O������
				Log.d("Motion", "ActionMove");
				currentX = (int)event.getX() ;	//���݂�X,Y���W
				currentY = (int)event.getY() ;
				
				if(moving){//�ړ��`�撆��������
					mainPlayer.move(currentX - savedX,  currentY - savedY);//�����̈ړ�
				}else{	//���񂾂�����
					moving = true;//�ړ��`��̏����J�n
				}
				
				savedX = currentX;	//�P�O��X���W�X�V
				savedY  = currentY;	//�P�O��Y���W�X�V				
				
				break;
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
//debug
		canvas.drawText("�c��G���F"+AbstractEnemy.getCurrentNum(), canvas.getWidth()/3*1, 48,paint);
		canvas.drawText("Players:"+AbstractPlayer.getCurrentNum(), canvas.getWidth()/3, 48*2,paint);
		canvas.drawText("Items:"+itemUnits.size(), canvas.getWidth()/3, 48*3,paint);
		
		canvas.drawText(remainedTime + "�b", canvas.getWidth()/3*2, 48*3,paint);
		canvas.drawText("Enemies:"+enemyUnits.size(), canvas.getWidth()/3*2, 48*2,paint);
		canvas.drawText("Images:"+unitImages2.size(), canvas.getWidth()/3*2, 48,paint);
	}

}

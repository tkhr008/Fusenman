package jp.ac.kccollege.ohya.android.fusenman;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.framework.game2D.Scene;
import jp.ac.kccollege.ohya.android.fusenman.unit.EnemyFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.ItemFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.PlayerFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractUnit;
import jp.ac.kccollege.ohya.android.fusenman.unit.MainPlayer;

abstract class AnimationScene extends Fusenman implements Scene {
	
	/**�f�[�^���������ς݂��ǂ����H*/
	private static boolean inited = false;

	/**������
	 * @see jp.ac.kccollege.ohya.android.framework.game2D.Scene#init(jp.ac.kccollege.ohya.android.framework.game2D.GameView)
	 */
	@Override
	public void init(GameView view) {
		//�f�[�^���������ς݂��ǂ����H
		//�����̔h���V�[���Ԃŋ��ʂ̃f�[�^����񂵂����������Ȃ�
		if (inited) {
			return;
		}
		//�����ɏ������������L�q
		inited = true;

		// �摜���t�@�C������ǂݍ���
		Resources res = view.getResources();

		unitImages2.put(CharType.PLAYER, res.getDrawable(R.drawable.fusenman))	;
		unitImages2.put(CharType.PSHOT, res.getDrawable(R.drawable.shot))	;
		unitImages2.put(CharType.TSUBAME, res.getDrawable(R.drawable.tsubame))	;
		unitImages2.put(CharType.KARASU, res.getDrawable(R.drawable.karasu))	;
		unitImages2.put(CharType.KILLER, res.getDrawable(R.drawable.killer))	;
		unitImages2.put(CharType.AHIRU, res.getDrawable(R.drawable.ahiru))	;
		unitImages2.put(CharType.ANPANMAN, res.getDrawable(R.drawable.anpanman))	;
		unitImages2.put(CharType.BOMB, res.getDrawable(R.drawable.bomb))	;
		unitImages2.put(CharType.ESHOT, res.getDrawable(R.drawable.e_shot))	;
		unitImages2.put(CharType.ITEM1, (AnimationDrawable)res.getDrawable(R.drawable.anim))	;
		unitImages2.put(CharType.BOSS1, res.getDrawable(R.drawable.karasu));
		unitImages2.put(CharType.BOSS2, res.getDrawable(R.drawable.tsubame));
		unitImages2.put(CharType.BOSS3, res.getDrawable(R.drawable.anpanman));

		//���j�b�g�摜�̐ݒ�
		AbstractUnit.setUnitImages(unitImages2);	

		// �w�i�摜���r�b�g�}�b�v�Ƃ��ēǂݍ���
		bgImageFar = BitmapFactory.decodeResource(res, R.drawable.back_win);
		bgImageNear = BitmapFactory.decodeResource(res, R.drawable.clouds);
	
		// �w�i�摜�̒���
		bgFar = new BackGround(bgImageFar,view.getDrawRect(),2);
		bgNear = new BackGround(bgImageNear,view.getDrawRect(),6);
		
		//�v���C���[���X�g�̐���
		playerFactory = PlayerFactory.getInstance();
		mainPlayer = (MainPlayer)playerFactory.create(CharType.PLAYER);//���@�̐��� 
		playerUnits = playerFactory.getUnitList();
		
		//�G���X�g�̐���
		enemyFactory = EnemyFactory.getInstance();
		enemyFactory.setTarget(mainPlayer);//�G���猩���^�[�Q�b�g�ݒ�
		enemyUnits = enemyFactory.getUnitList();
		
		//�A�C�e�����X�g�̐���
		itemFactory=ItemFactory.getInstance();
		itemUnits = itemFactory.getUnitList();
	}


	@Override
	public void start(GameView view) {
		//�V�[�����؂�ւ�������ɍŏ��ɌĂяo����鏈��
	}


	/**�X�V����*/
	@Override
	public void process(GameView view) {
		
		//���j�b�g�̕�����ԍX�V����
		enemyUnits.process(view,playerUnits);
		playerUnits.process(view,enemyUnits);
		itemUnits.process(view, itemUnits);

	}


	/**�`�揈��*/
	@Override
	public void draw(Canvas canvas) {

		// �w�i�摜�̕`��
		bgFar.draw(canvas);
		bgNear.draw(canvas);
		
		//���j�b�g�̕`��
		playerUnits.draw(canvas);
		enemyUnits.draw(canvas);
		itemUnits.draw(canvas);
	}

}

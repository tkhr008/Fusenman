package jp.ac.kccollege.ohya.android.fusenman;

import java.util.HashMap;

import jp.ac.kccollege.ohya.android.fusenman.unit.EnemyFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.ItemFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.PlayerFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.MainPlayer;
import jp.ac.kccollege.ohya.android.fusenman.unit.UnitList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Fusenman {

	/*------------------------------------------------------------------------------
 	�Q�[���S�̂Ŏg�p����ϐ��̒�`
	 ------------------------------------------------------------------------------*/
	// �C���[�W�摜�@�̓T�u�N���X�Ŏg�p����̂�protected 
	/**�w�i*/
	protected static Bitmap bgImageFar;
	protected static Bitmap bgImageNear;
	protected static BackGround bgFar;
	protected static BackGround bgNear;	
	
	//�I�u�W�F�N�g�Ǘ��ϐ�
	/**�v���C���[*/
	protected static MainPlayer mainPlayer; 
	
	/**���j�b�g���X�g*/
	protected static UnitList playerUnits = null;
	protected static UnitList enemyUnits = null;
	protected static UnitList itemUnits = null;
	
	/**�Ǘ��N���X*/
	protected static EnemyFactory enemyFactory=null;
	protected static PlayerFactory playerFactory=null;
	protected static ItemFactory itemFactory=null;	
	
	/**�L�����^�C�v�Ǘ�*/
	public static enum CharType{
		PLAYER,PSHOT,
		TSUBAME,KARASU,AHIRU,KILLER,ANPANMAN,ESHOT,
		BOSS1,BOSS2,BOSS3,
		MESSAGE1,MESSAGE2,MESSAGE3,MESSAGE4,MESSAGE5,
		BOMB,
		ITEM1,ITEM2;
	}
	/**�摜�Ǘ����X�g*/
	protected HashMap<Enum<CharType>,Drawable> unitImages2
		= new HashMap<Enum<CharType>, Drawable>();
	
	/**�X�e�[�W��*/
	public static int stageCount = 1; 

}

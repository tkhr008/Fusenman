package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;

/**���j�b�g�����t�@�N�g���[*/
public class PlayerFactory extends AbstractUnitFactory  {

	/** �V���O���g�� */
	private static PlayerFactory singleton = new PlayerFactory();

	
	/**�Q�ƕϐ��@�L����1��*/
	//private AbstractUnit unit = null;
	
	/** �f�t�H���g�R���X�g���N�^ */
	private PlayerFactory() {
		/* �V���O���g���f�U�C���p�^�[���̓K�p�̂��߁A
		 * �R���X�g���N�^�����J
		 */
		list = new UnitList(this);
	}

	/**
	 * �C���X�^���X���擾
	 * @return ���j�b�g�t�@�N�g���[�̃C���X�^���X
	 */
	public static PlayerFactory getInstance() {
		return singleton;
	}

	/**�L�����̐���*/
	public AbstractUnit createUnit(CharType type) {
		
		AbstractUnit unit = null;	
		
		switch (type) {
		case PSHOT:
			unit = new ShotOfPlayer();
			break;
		case PLAYER:
			unit = new MainPlayer();
			break;
			/*
		case Fusenman.MESSAGE1:
			unit = new Message("���޽����(߁��)��!",5,12);
			break;
		case Fusenman.MESSAGE2:
			unit = new Message("EVOLVE",5,12);
			break;
		case Fusenman.MESSAGE3:
			unit = new Message("�A�[�c�u�[�g�L�����v�I",5,12);
			break;
		case Fusenman.MESSAGE4:
			unit = new Message("�n�}�t�F�X�J�Ó��V���Q�V��(�y)�I",8,16);
			break;
		case Fusenman.MESSAGE5:
			unit = new Message("�����Ƀ��b�Z�[�W��\���ł����",12,10);
			break;
			*/
		default:
			break;
		}
		return unit;
	}
	/** ���j�b�g�̓o�^*/
	@Override
	protected void registUnit(AbstractUnit unit) {
		list.add(unit);
	}


	/**�J�n*/
	public void start(int stageCount){

	}

}

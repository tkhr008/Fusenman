package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;

/**���j�b�g�����t�@�N�g���[*/
public class ItemFactory extends AbstractUnitFactory  {

	/** �V���O���g�� */
	private static ItemFactory singleton = new ItemFactory();

	/** �f�t�H���g�R���X�g���N�^ */
	private ItemFactory() {
		/* �V���O���g���f�U�C���p�^�[���̓K�p�̂��߁A
		 * �R���X�g���N�^�����J
		 */
		list = new UnitList(this);
	}

	/**
	 * �C���X�^���X���擾
	 * @return ���j�b�g�t�@�N�g���[�̃C���X�^���X
	 */
	public static ItemFactory getInstance() {
		return singleton;
	}

	/**�L�����̐���*/
	public AbstractUnit createUnit(CharType type) {
		
		AbstractUnit unit = null;	
		
		switch (type) {
		case ITEM1:
			unit = new Item();
			break;
		case ITEM2:
			unit = new Item();
			break;
			
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

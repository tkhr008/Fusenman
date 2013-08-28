package jp.ac.kccollege.ohya.android.fusenman.unit;

/**���j�b�g�����t�@�N�g���[*/
public abstract class AbstractUnitFactory  {
	
	/**���X�g*/
	protected UnitList list;
	
//��ۃ��\�b�h
	/**
	 * ���j�b�g�̐���
	 * @param type ���j�b�g�̃^�C�v
	 * @return �����������j�b�g
	 */
	public final AbstractUnit create(int type) {
	    // ���j�b�g����
	    AbstractUnit unit = createUnit(type);
	    // ���j�b�g�o�^
	    registUnit(unit);
	    return unit;
	}
	public final AbstractUnit create(int type,AbstractUnit from){
	    // ���j�b�g����
	    AbstractUnit unit = createUnit(type,from);
	    // ���j�b�g�o�^
	    registUnit(unit);
	    return unit;
	}
	/**
	 * ���j�b�g�̐���
	 * @param type ���j�b�g�̃^�C�v
	 * @param from �J�n�ʒu�̃��j�b�g
	 * @return �����������j�b�g
	 */
	protected  AbstractUnit createUnit(int type, AbstractUnit from){
	    // ���j�b�g����
	    AbstractUnit unit = createUnit(type);

	    // from���j�b�g�̒������W���擾
		float char_x = from.centerX();
		float char_y = from.centerY();

		unit.offset(char_x, char_y);// �V���j�b�g�̒������W�ݒ�
		return unit;
	}
	/**
	 * ���j�b�g�̐���
	 * @param type ���j�b�g�̃^�C�v
	 * @param from �J�n�ʒu�̃��j�b�g
	 * 	@param to �U���Ώۂ̃��j�b�g
	 * @return �����������j�b�g
	 */
	protected  AbstractUnit createUnit(int type, AbstractUnit from, AbstractUnit to){
	    // ���j�b�g����
	    AbstractUnit unit = createUnit(type);

	    // from���j�b�g�̒������W���擾
		float char_x = from.centerX();
		float char_y = from.centerY();

		unit.offset(char_x, char_y);// �V���j�b�g�̒������W�ݒ�
		unit.fixDirection(to.char_x, to.char_y);
		return unit;
	}
	
	/**���X�g��Ԃ�*/
	public UnitList getUnitList(){
		return list;
	}
	
//���ۃ��\�b�h
	/** 
	 * ���j�b�g�̃C���X�^���X�𐶐�
	 */
	protected abstract AbstractUnit createUnit(int type);
	
	/**
	 * ���j�b�g�̓o�^
	 */
	protected abstract void registUnit(AbstractUnit unit);


}

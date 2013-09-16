package jp.ac.kccollege.ohya.android.fusenman.unit;

import java.util.ArrayList;
import java.util.Iterator;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.Status;
import android.graphics.Canvas;


public class UnitList extends ArrayList<AbstractUnit> {

	//static
	private static final long serialVersionUID = 1L;
	
	//�C���X�^���X�ϐ�
	private AbstractUnitFactory unitFactory=null;
	
	/** �Q�Ɨp */
	private AbstractUnit unit = null;
	private AbstractUnit myUnit = null;
	private AbstractUnit vsUnit = null;
	private AbstractUnit drawUnit = null;
	private Iterator<AbstractUnit> it = null;
	private Iterator<AbstractUnit> vsIt = null;
	
	/** �f�t�H���g�R���X�g���N�^ */
	public UnitList() {
		/* �V���O���g���f�U�C���p�^�[���K�p�̂��߁A�R���X�g���N�^�����J*/
	}
	/** �f�t�H���g�R���X�g���N�^ */
	public UnitList(AbstractUnitFactory unitFactory) {
		/* �V���O���g���f�U�C���p�^�[���K�p�̂��߃R���X�g���N�^�����J*/
		this.unitFactory = unitFactory;
	}

	/**���j�b�g�ė��p*/
	private boolean isReuseUnit(final CharType type,AbstractUnit from){
		AbstractUnit unit = null;
		
		// ���ŃC���X�^���X������ꍇ����������
		unit = checkType(type);
		
		//�C���X�^���X�����݂��Ă����ꍇ
		if (unit != null) {
			unit.offset(from.centerX(), from.centerY());
			unit.size = from.size;
			unit.init();	
			return true;
		}
		return false;
	}
	/**�ė��p�\���m�F*/
	public AbstractUnit checkType(final int type){
		
		if(isEmpty()){	return null;	}
		
		try{
			// �����^�C�v�̃I�u�W�F�N�g���c���Ă��邩
			it = iterator(); // ���[�v�p
			unit = null;
			
			while (it.hasNext() == true) {
				unit = it.next();// ���o��
				if(unit.getType() == type && unit.isDead()){
					return unit;
				}
			}
		} catch (java.util.NoSuchElementException e) {
			e.printStackTrace();
		}
		return null;
	}
	////////////////////
	public AbstractUnit checkType(final CharType type){
		
		if(isEmpty()){	return null;	}
		
		try{
			// �����^�C�v�̃I�u�W�F�N�g���c���Ă��邩
			it = iterator(); // ���[�v�p
			unit = null;
			
			while (it.hasNext() == true) {
				unit = it.next();// ���o��
				if(unit.equals(type) && unit.isDead()){
					return unit;
				}
			}
		} catch (java.util.NoSuchElementException e) {
			e.printStackTrace();
		}
		return null;
	}	
	/**�v���Z�X*/
	public void process(GameView view,UnitList vsUnitList){

		if(this == null){
			return;
		}
		
		try {
			it = iterator();//�C�e���[�^���擾
			myUnit=null;
			while (it.hasNext() == true) {
				
				myUnit = it.next();// ���X�g���烆�j�b�g���擾
				myUnit.process(view);//���j�b�g�ʂ̃v���Z�X����
				
				switch(myUnit.getStatus()){
				case LIVE:	//�����������蔻��
					vsIt = vsUnitList.iterator();//�C�e���[�^���擾
					vsUnit=null;
					
					while (vsIt.hasNext() == true) {
						vsUnit = vsIt.next();// ���X�g���瑊����擾
						//������Ԋm�F
						if(vsUnit.getStatus()!=Status.LIVE){
							continue;
						}
						//�G�Ɩ����̊֌W�������瓖���蔻��
						if( (myUnit instanceof AbstractPlayer && vsUnit instanceof AbstractEnemy)
								||(myUnit instanceof AbstractEnemy && vsUnit instanceof AbstractPlayer)){
							//�����蔻��					
							if(myUnit.judge(vsUnit)){
								myUnit.damage();
								vsUnit.damage();
							}//if
						}//if
					}//while	
					break;
					
				case ATTACK://�U������
					if(myUnit instanceof InterfaceShooter){
						//�e�L�����^�C�v�̎擾
						CharType type =((InterfaceShooter)(myUnit)).shoot();
						//�ė��p�܂��͐V�K����
						if(!isReuseUnit(type,myUnit)){
							unitFactory.create(type, myUnit);
						}
					}
					break;
					
				case DEAD://����
					it.remove();
					break;
					
				default:
					break;
				}
					
			}//while
			trimToSize();// ���X�g�̃��T�C�Y
			
		} catch (java.util.NoSuchElementException e) {
			e.printStackTrace();
		} catch (java.util.ConcurrentModificationException e) {
			e.printStackTrace();
		}//try
	}
	
	/**���X�g�̕`��*/
	public void draw(Canvas c) {
		try{
			Iterator<AbstractUnit> it = iterator();
			drawUnit = null;
			while (it.hasNext() == true) {
				drawUnit = it.next();// ���X�g���烆�j�b�g���擾
				drawUnit.draw(c);// �`��
			}
		} catch (java.util.NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	/**���X�g�̍폜*/
	public void deleteDeadUnit() {
		try{
			Iterator<AbstractUnit> it = iterator();
			unit = null;
			while (it.hasNext() == true) {
				unit = it.next();// ���X�g���烆�j�b�g���擾
				if(unit.isDead()){
					it.remove();
				}
			}
			trimToSize();
			
		} catch (java.util.NoSuchElementException e) {
			e.printStackTrace();
		}
	}
}
package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.enemy.*;

/**���j�b�g�����t�@�N�g���[*/
public class EnemyFactory extends AbstractUnitFactory  {

	/** �V���O���g�� */
	private static EnemyFactory singleton = new EnemyFactory();

	
	/**�Q�ƕϐ��@�G�L����1��*/
	private AbstractUnit unit = null;
	
	/**�Q�ƕϐ��@�U���Ώۃ��j�b�g*/
	private AbstractUnit target = null;
	
	/**���݂̃^�[��*/
	private int turn=1;

	/**�G�̏o������ */
	private int[][] order= {
			//{�^�[��, �G�^�C�v, �o����}
			{1,3,1},
			{2,2,3},
			{3,111,1},
			{3,1001,1},
			{4,112,1},			
			{4,2,2},
			{4,4,1},
			{4,5,1},
			{4,3,2},
			{5,2,4},
			{5,3,2},
			{6,115,1},
			{6,4,2},
			{6,2,6},
			{7,3,1},
			{7,4,2},
			{7,2,3},
			{8,5,3},
			{8,115,1},
			{8,2,3},
			{9,113,1},
			{9,1003,1},
			{10,115,1},	
			{10,5,3},
			{11,4,2},
			{12,3,6},
			{12,2,3},
			{13,4,4},
			{13,6,3},
			{14,111,1},
			{14,1002,2},
			{0,0,0},
	};

	/** �f�t�H���g�R���X�g���N�^ */
	private EnemyFactory() {
		/* �V���O���g���f�U�C���p�^�[���̓K�p�̂��߁A
		 * �R���X�g���N�^�����J
		 */
		list = new UnitList(this);
	}

	/**
	 * �C���X�^���X���擾
	 * @return ���j�b�g�t�@�N�g���[�̃C���X�^���X
	 */
	public static EnemyFactory getInstance() {
		return singleton;
	}

	/**�^�[�Q�b�g�̐ݒ�*/
	public void setTarget(AbstractUnit target){
		this.target = target;
	}
	
	/**�L�����̐���*/
	public AbstractUnit createUnit(CharType type) {
		
		AbstractUnit unit = null;
		
		switch (type) {
		case KARASU:
			unit = new Karasu();
			break;
		case TSUBAME:
			unit = new Tsubame();
			break;
		case KILLER:
			unit = new Killer();
			break;
		case AHIRU:
			unit = new Ahiru();
			break;
		case ANPANMAN:
			unit = new Anpanman();
			break;
		case BOSS1:
			unit = new StageBoss1();
			break;
		case BOSS2:
			unit = new StageBoss2();
			break;
		case BOSS3:
			unit = new StageBoss3();
			break;
		case ESHOT:
			unit = new ShotOfEnemy();
			break;
		case MESSAGE1:
			unit = new Message("���޽����(߁��)��!",5,12);
			break;
		case MESSAGE2:
			unit = new Message("EVOLVE",5,12);
			break;
		case MESSAGE3:
			unit = new Message("�A�[�c�u�[�g�L�����v�I",5,12);
			break;
		case MESSAGE4:
			unit = new Message("�n�}�t�F�X�J�Ó��V���Q�V��(�y)�I",8,16);
			break;
		case MESSAGE5:
			unit = new Message("�����Ƀ��b�Z�[�W��\���ł����",12,10);
			break;
		default:
			break;
		}
		return unit;
	}
	/**�L�����̐���*/
	public AbstractUnit createUnit(CharType type, AbstractUnit from) {

		if(target != null){
			unit = super.createUnit(type,from,target);			
		}else{
			unit =  super.createUnit(type,from);						
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
		if(AbstractEnemy.getCurrentNum() <=0){
			turn=1;
			//����stageCount�ɂ����order��ς���
			createUnits(turn++,order);
		}
	}


		
	/**�����̒l���������_���ɓG�𐶐�*/
	//////////////////////
	/*
	public boolean createUnits(int createNum){
		
		if(this ==null){//���g��null��������
			return false;
		}

		int size=AbstractEnemy.getCurrentNum();//���݂̃T�C�Y
		int addNum = createNum - size ;//�ǉ�����G�̐�

		if(createNum < size){ //�T�C�Y������������
			return false;
		}
		
		// �G�̐���
		for (int i = size+1; i < size + addNum; i++) {
			int type = (int)(Math.random() * createNum);
			super.create(type);
		}
		return true;
	}
	*/
	///////////////

	/**�I�[�_�[���ɓG�𕡐�����*/
	public boolean createUnits(){
		return createUnits(turn++,order);
	}
	
	/**�I�[�_�[���ɓG�𕡐�����*/
	private boolean createUnits(final int turn, final int[][] order){
		
		// �G�̐���
		for (int i = 0; i < order.length; i++) {
			int readTurn = order[i][0];
			if(readTurn == 0){
				return false;
			}
			if(turn == readTurn){
				int readType = order[i][1];//�L�����^�C�v
				int readNum = order[i][2];//������		
				for(int j = 0; j< readNum; j++){
					//�o�^�ς݂̃��j�b�g�����邩�ǂ���
					//unit = list.checkType(readType);
					unit = list.checkType(toriaezu(readType));
					
					if(unit == null){
						//super.create(readType);
						super.create(toriaezu(readType));
						//units.regist(unit);
					}else{
						unit.init();//������
						//registUnit(unit);//�o�^
					}
				}
				
			}else if(turn < readTurn){
				break;//�L�����̐����ニ�[�v�𔲂���
			}
		}		
		return true;
	}
	
	//toriaezu
	CharType toriaezu(int type){
		CharType c = null;
		
		switch(type){
		case 2:
			c = CharType.TSUBAME;
			break;
		case 3:
			c = CharType.KARASU;
			break;
		case 4:
			c = CharType.KILLER;
			break;
		case 5:
			c = CharType.AHIRU;
			break;
		case 6:
			c = CharType.ANPANMAN;
			break;
		case 1001:
			c = CharType.BOSS1;
			break;
		case 1002:
			c = CharType.BOSS2;
			break;
		case 1003:
			c = CharType.BOSS3;
			break;
		case 111:
			c = CharType.MESSAGE1;
			break;
		case 112:
			c = CharType.MESSAGE2;
			break;
		case 113:
			c = CharType.MESSAGE3;
			break;
		case 114:
			c = CharType.MESSAGE4;
			break;
		case 115:
			c = CharType.MESSAGE5;
			break;
		}
		return c;
	}
	
}

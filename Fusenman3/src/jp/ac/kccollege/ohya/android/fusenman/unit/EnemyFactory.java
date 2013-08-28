package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.fusenman.Fusenman;
import jp.ac.kccollege.ohya.android.fusenman.unit.enemy.*;

/**���j�b�g�����t�@�N�g���[*/
public class EnemyFactory extends AbstractUnitFactory  {

	/** �V���O���g�� */
	private static EnemyFactory singleton = new EnemyFactory();

	
	/**�Q�ƕϐ��@�G�L����1��*/
	private AbstractUnit unit = null;
	
	/**�Q�ƕϐ��@�U���Ώۃ��j�b�g*/
	private AbstractUnit target = null;
	
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
	public AbstractUnit createUnit(int type) {
		
		AbstractUnit unit = null;	
		
		switch (type) {
		case Fusenman.KARASU:
			unit = new Karasu(type);
			break;
		case Fusenman.TSUBAME:
			unit = new Tsubame(type);
			break;
		case Fusenman.KILLER:
			unit = new Killer(type);
			break;
		case Fusenman.AHIRU:
			unit = new Ahiru(type);
			break;
		case Fusenman.ANPANMAN:
			unit = new Anpanman(type);
			break;
		case Fusenman.BOSS1:
			unit = new StageBoss1(Fusenman.KARASU);
			break;
		case Fusenman.BOSS2:
			unit = new StageBoss2(Fusenman.KILLER);
			break;
		case Fusenman.BOSS3:
			unit = new StageBoss3(Fusenman.ANPANMAN);
			break;
		case Fusenman.SHOT_OF_ENEMY:
			unit = new ShotOfEnemy(type);
			break;
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
		default:
			break;
		}
		return unit;
	}

	/**�L�����̐���*/
	public AbstractUnit createUnit(int type, AbstractUnit from) {

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
	
	/**���݂̃^�[��*/
	private int turn=1;
	/**�G�̏o������ */
	private int[][] order= {
			//{�^�[��, �G�^�C�v, �o����}
			{1,Fusenman.KARASU,1},
			{2,Fusenman.TSUBAME,3},
			{3,Fusenman.MESSAGE1,1},
			{3,Fusenman.MESSAGE2,1},
			{3,Fusenman.BOSS1,1},
			{4,Fusenman.TSUBAME,2},
			{4,Fusenman.MESSAGE3,1},
			{4,Fusenman.MESSAGE4,1},
			{4,Fusenman.KARASU,2},
			{5,Fusenman.TSUBAME,4},
			{5,Fusenman.KARASU,2},
			{5,Fusenman.MESSAGE5,1},
			{5,Fusenman.KILLER,2},
			{5,Fusenman.TSUBAME,6},
			{7,Fusenman.MESSAGE2,1},
			{7,Fusenman.BOSS1,2},
			{7,Fusenman.TSUBAME,3},
			{8,Fusenman.AHIRU,3},
			{8,Fusenman.MESSAGE5,1},
			{8,Fusenman.TSUBAME,3},
			{9,Fusenman.MESSAGE3,1},
			{9,Fusenman.BOSS3,1},	
			{10,Fusenman.AHIRU,3},
			{11,Fusenman.KILLER,2},
			{11,Fusenman.MESSAGE5,1},
			{12,Fusenman.KARASU,6},
			{12,Fusenman.TSUBAME,3},
			{13,Fusenman.KILLER,4},
			{13,Fusenman.ANPANMAN,3},
			{14,Fusenman.BOSS2,2},
			{0,0,0},
	};
	

	/**�J�n*/
	public void start(int stageCount){
		if(AbstractEnemy.getCurrentNum() <=0){
			turn=1;
			//����stageCount�ɂ����order��ς���
			createUnits(turn++,order);
		}
	}


		
	/**�����̒l���������_���ɓG�𐶐�*/
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

	/**�I�[�_�[���ɓG�𕡐�����*/
	public boolean createUnits(){
		return createUnits(turn++,order);
	}
	
	/**�I�[�_�[���ɓG�𕡐�����*/
	private boolean createUnits(final int turn, final int[][] order){
		
/*		if(this ==null){//���g��null��������
			return false;
		}*/
		
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
					unit = list.checkType(readType);
					if(unit == null){
						super.create(readType);
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
}

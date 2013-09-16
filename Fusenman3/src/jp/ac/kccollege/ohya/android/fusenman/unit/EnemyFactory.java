package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.enemy.*;

/**ユニット生成ファクトリー*/
public class EnemyFactory extends AbstractUnitFactory  {

	/** シングルトン */
	private static EnemyFactory singleton = new EnemyFactory();

	
	/**参照変数　敵キャラ1体*/
	private AbstractUnit unit = null;
	
	/**参照変数　攻撃対象ユニット*/
	private AbstractUnit target = null;
	
	/**現在のターン*/
	private int turn=1;

	/**敵の出現順序 */
	private int[][] order= {
			//{ターン, 敵タイプ, 出現数}
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

	/** デフォルトコンストラクタ */
	private EnemyFactory() {
		/* シングルトンデザインパターンの適用のため、
		 * コンストラクタを非公開
		 */
		list = new UnitList(this);
	}

	/**
	 * インスタンスを取得
	 * @return ユニットファクトリーのインスタンス
	 */
	public static EnemyFactory getInstance() {
		return singleton;
	}

	/**ターゲットの設定*/
	public void setTarget(AbstractUnit target){
		this.target = target;
	}
	
	/**キャラの生成*/
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
			unit = new Message("中ﾎﾞｽｷﾀ━(ﾟ∀ﾟ)━!",5,12);
			break;
		case MESSAGE2:
			unit = new Message("EVOLVE",5,12);
			break;
		case MESSAGE3:
			unit = new Message("アーツブートキャンプ！",5,12);
			break;
		case MESSAGE4:
			unit = new Message("ハマフェス開催日７月２７日(土)！",8,16);
			break;
		case MESSAGE5:
			unit = new Message("ここにメッセージを表示できるよ",12,10);
			break;
		default:
			break;
		}
		return unit;
	}
	/**キャラの生成*/
	public AbstractUnit createUnit(CharType type, AbstractUnit from) {

		if(target != null){
			unit = super.createUnit(type,from,target);			
		}else{
			unit =  super.createUnit(type,from);						
		}
		return unit;
	}
	
	/** ユニットの登録*/
	@Override
	protected void registUnit(AbstractUnit unit) {
		list.add(unit);
	}
	
	/**開始*/
	public void start(int stageCount){
		if(AbstractEnemy.getCurrentNum() <=0){
			turn=1;
			//今後stageCountによってorderを変える
			createUnits(turn++,order);
		}
	}


		
	/**引数の値だけランダムに敵を生成*/
	//////////////////////
	/*
	public boolean createUnits(int createNum){
		
		if(this ==null){//自身がnullだったら
			return false;
		}

		int size=AbstractEnemy.getCurrentNum();//現在のサイズ
		int addNum = createNum - size ;//追加する敵の数

		if(createNum < size){ //サイズ未満だったら
			return false;
		}
		
		// 敵の生成
		for (int i = size+1; i < size + addNum; i++) {
			int type = (int)(Math.random() * createNum);
			super.create(type);
		}
		return true;
	}
	*/
	///////////////

	/**オーダー順に敵を複数生成*/
	public boolean createUnits(){
		return createUnits(turn++,order);
	}
	
	/**オーダー順に敵を複数生成*/
	private boolean createUnits(final int turn, final int[][] order){
		
		// 敵の生成
		for (int i = 0; i < order.length; i++) {
			int readTurn = order[i][0];
			if(readTurn == 0){
				return false;
			}
			if(turn == readTurn){
				int readType = order[i][1];//キャラタイプ
				int readNum = order[i][2];//生成数		
				for(int j = 0; j< readNum; j++){
					//登録済みのユニットがあるかどうか
					//unit = list.checkType(readType);
					unit = list.checkType(toriaezu(readType));
					
					if(unit == null){
						//super.create(readType);
						super.create(toriaezu(readType));
						//units.regist(unit);
					}else{
						unit.init();//初期化
						//registUnit(unit);//登録
					}
				}
				
			}else if(turn < readTurn){
				break;//キャラの生成後ループを抜ける
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

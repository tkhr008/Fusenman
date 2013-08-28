package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.fusenman.Fusenman;
import jp.ac.kccollege.ohya.android.fusenman.unit.enemy.*;

/**ユニット生成ファクトリー*/
public class EnemyFactory extends AbstractUnitFactory  {

	/** シングルトン */
	private static EnemyFactory singleton = new EnemyFactory();

	
	/**参照変数　敵キャラ1体*/
	private AbstractUnit unit = null;
	
	/**参照変数　攻撃対象ユニット*/
	private AbstractUnit target = null;
	
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
			unit = new Message("中ﾎﾞｽｷﾀ━(ﾟ∀ﾟ)━!",5,12);
			break;
		case Fusenman.MESSAGE2:
			unit = new Message("EVOLVE",5,12);
			break;
		case Fusenman.MESSAGE3:
			unit = new Message("アーツブートキャンプ！",5,12);
			break;
		case Fusenman.MESSAGE4:
			unit = new Message("ハマフェス開催日７月２７日(土)！",8,16);
			break;
		case Fusenman.MESSAGE5:
			unit = new Message("ここにメッセージを表示できるよ",12,10);
			break;
		default:
			break;
		}
		return unit;
	}

	/**キャラの生成*/
	public AbstractUnit createUnit(int type, AbstractUnit from) {

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
	
	/**現在のターン*/
	private int turn=1;
	/**敵の出現順序 */
	private int[][] order= {
			//{ターン, 敵タイプ, 出現数}
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
	

	/**開始*/
	public void start(int stageCount){
		if(AbstractEnemy.getCurrentNum() <=0){
			turn=1;
			//今後stageCountによってorderを変える
			createUnits(turn++,order);
		}
	}


		
	/**引数の値だけランダムに敵を生成*/
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

	/**オーダー順に敵を複数生成*/
	public boolean createUnits(){
		return createUnits(turn++,order);
	}
	
	/**オーダー順に敵を複数生成*/
	private boolean createUnits(final int turn, final int[][] order){
		
/*		if(this ==null){//自身がnullだったら
			return false;
		}*/
		
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
					unit = list.checkType(readType);
					if(unit == null){
						super.create(readType);
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
}

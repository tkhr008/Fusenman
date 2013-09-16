package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;

/**ユニット生成ファクトリー*/
public class PlayerFactory extends AbstractUnitFactory  {

	/** シングルトン */
	private static PlayerFactory singleton = new PlayerFactory();

	
	/**参照変数　キャラ1体*/
	//private AbstractUnit unit = null;
	
	/** デフォルトコンストラクタ */
	private PlayerFactory() {
		/* シングルトンデザインパターンの適用のため、
		 * コンストラクタを非公開
		 */
		list = new UnitList(this);
	}

	/**
	 * インスタンスを取得
	 * @return ユニットファクトリーのインスタンス
	 */
	public static PlayerFactory getInstance() {
		return singleton;
	}

	/**キャラの生成*/
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
			*/
		default:
			break;
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

	}

}

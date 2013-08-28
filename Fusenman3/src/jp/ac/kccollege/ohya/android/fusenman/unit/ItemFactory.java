package jp.ac.kccollege.ohya.android.fusenman.unit;

import jp.ac.kccollege.ohya.android.fusenman.Fusenman;

/**ユニット生成ファクトリー*/
public class ItemFactory extends AbstractUnitFactory  {

	/** シングルトン */
	private static ItemFactory singleton = new ItemFactory();

	/** デフォルトコンストラクタ */
	private ItemFactory() {
		/* シングルトンデザインパターンの適用のため、
		 * コンストラクタを非公開
		 */
		list = new UnitList(this);
	}

	/**
	 * インスタンスを取得
	 * @return ユニットファクトリーのインスタンス
	 */
	public static ItemFactory getInstance() {
		return singleton;
	}

	/**キャラの生成*/
	public AbstractUnit createUnit(int type) {
		
		AbstractUnit unit = null;	
		
		switch (type) {
		case Fusenman.ITEM1:
			unit = new Item(type);
			break;
		case Fusenman.ITEM2:
			unit = new Item(type);
			break;
			
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

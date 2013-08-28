package jp.ac.kccollege.ohya.android.fusenman.unit;

/**ユニット生成ファクトリー*/
public abstract class AbstractUnitFactory  {
	
	/**リスト*/
	protected UnitList list;
	
//具象メソッド
	/**
	 * ユニットの生成
	 * @param type ユニットのタイプ
	 * @return 生成したユニット
	 */
	public final AbstractUnit create(int type) {
	    // ユニット生成
	    AbstractUnit unit = createUnit(type);
	    // ユニット登録
	    registUnit(unit);
	    return unit;
	}
	public final AbstractUnit create(int type,AbstractUnit from){
	    // ユニット生成
	    AbstractUnit unit = createUnit(type,from);
	    // ユニット登録
	    registUnit(unit);
	    return unit;
	}
	/**
	 * ユニットの生成
	 * @param type ユニットのタイプ
	 * @param from 開始位置のユニット
	 * @return 生成したユニット
	 */
	protected  AbstractUnit createUnit(int type, AbstractUnit from){
	    // ユニット生成
	    AbstractUnit unit = createUnit(type);

	    // fromユニットの中央座標を取得
		float char_x = from.centerX();
		float char_y = from.centerY();

		unit.offset(char_x, char_y);// 新ユニットの中央座標設定
		return unit;
	}
	/**
	 * ユニットの生成
	 * @param type ユニットのタイプ
	 * @param from 開始位置のユニット
	 * 	@param to 攻撃対象のユニット
	 * @return 生成したユニット
	 */
	protected  AbstractUnit createUnit(int type, AbstractUnit from, AbstractUnit to){
	    // ユニット生成
	    AbstractUnit unit = createUnit(type);

	    // fromユニットの中央座標を取得
		float char_x = from.centerX();
		float char_y = from.centerY();

		unit.offset(char_x, char_y);// 新ユニットの中央座標設定
		unit.fixDirection(to.char_x, to.char_y);
		return unit;
	}
	
	/**リストを返す*/
	public UnitList getUnitList(){
		return list;
	}
	
//抽象メソッド
	/** 
	 * ユニットのインスタンスを生成
	 */
	protected abstract AbstractUnit createUnit(int type);
	
	/**
	 * ユニットの登録
	 */
	protected abstract void registUnit(AbstractUnit unit);


}

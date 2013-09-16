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
	
	//インスタンス変数
	private AbstractUnitFactory unitFactory=null;
	
	/** 参照用 */
	private AbstractUnit unit = null;
	private AbstractUnit myUnit = null;
	private AbstractUnit vsUnit = null;
	private AbstractUnit drawUnit = null;
	private Iterator<AbstractUnit> it = null;
	private Iterator<AbstractUnit> vsIt = null;
	
	/** デフォルトコンストラクタ */
	public UnitList() {
		/* シングルトンデザインパターン適用のため、コンストラクタを非公開*/
	}
	/** デフォルトコンストラクタ */
	public UnitList(AbstractUnitFactory unitFactory) {
		/* シングルトンデザインパターン適用のためコンストラクタを非公開*/
		this.unitFactory = unitFactory;
	}

	/**ユニット再利用*/
	private boolean isReuseUnit(final CharType type,AbstractUnit from){
		AbstractUnit unit = null;
		
		// 消滅インスタンスがある場合復活させる
		unit = checkType(type);
		
		//インスタンスが存在していた場合
		if (unit != null) {
			unit.offset(from.centerX(), from.centerY());
			unit.size = from.size;
			unit.init();	
			return true;
		}
		return false;
	}
	/**再利用可能か確認*/
	public AbstractUnit checkType(final int type){
		
		if(isEmpty()){	return null;	}
		
		try{
			// 同じタイプのオブジェクトが残っているか
			it = iterator(); // ループ用
			unit = null;
			
			while (it.hasNext() == true) {
				unit = it.next();// 取り出し
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
			// 同じタイプのオブジェクトが残っているか
			it = iterator(); // ループ用
			unit = null;
			
			while (it.hasNext() == true) {
				unit = it.next();// 取り出し
				if(unit.equals(type) && unit.isDead()){
					return unit;
				}
			}
		} catch (java.util.NoSuchElementException e) {
			e.printStackTrace();
		}
		return null;
	}	
	/**プロセス*/
	public void process(GameView view,UnitList vsUnitList){

		if(this == null){
			return;
		}
		
		try {
			it = iterator();//イテレータを取得
			myUnit=null;
			while (it.hasNext() == true) {
				
				myUnit = it.next();// リストからユニットを取得
				myUnit.process(view);//ユニット個別のプロセス処理
				
				switch(myUnit.getStatus()){
				case LIVE:	//生存時当たり判定
					vsIt = vsUnitList.iterator();//イテレータを取得
					vsUnit=null;
					
					while (vsIt.hasNext() == true) {
						vsUnit = vsIt.next();// リストから相手を取得
						//生存状態確認
						if(vsUnit.getStatus()!=Status.LIVE){
							continue;
						}
						//敵と味方の関係だったら当たり判定
						if( (myUnit instanceof AbstractPlayer && vsUnit instanceof AbstractEnemy)
								||(myUnit instanceof AbstractEnemy && vsUnit instanceof AbstractPlayer)){
							//当たり判定					
							if(myUnit.judge(vsUnit)){
								myUnit.damage();
								vsUnit.damage();
							}//if
						}//if
					}//while	
					break;
					
				case ATTACK://攻撃処理
					if(myUnit instanceof InterfaceShooter){
						//弾キャラタイプの取得
						CharType type =((InterfaceShooter)(myUnit)).shoot();
						//再利用または新規生成
						if(!isReuseUnit(type,myUnit)){
							unitFactory.create(type, myUnit);
						}
					}
					break;
					
				case DEAD://消滅
					it.remove();
					break;
					
				default:
					break;
				}
					
			}//while
			trimToSize();// リストのリサイズ
			
		} catch (java.util.NoSuchElementException e) {
			e.printStackTrace();
		} catch (java.util.ConcurrentModificationException e) {
			e.printStackTrace();
		}//try
	}
	
	/**リストの描画*/
	public void draw(Canvas c) {
		try{
			Iterator<AbstractUnit> it = iterator();
			drawUnit = null;
			while (it.hasNext() == true) {
				drawUnit = it.next();// リストからユニットを取得
				drawUnit.draw(c);// 描画
			}
		} catch (java.util.NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	/**リストの削除*/
	public void deleteDeadUnit() {
		try{
			Iterator<AbstractUnit> it = iterator();
			unit = null;
			while (it.hasNext() == true) {
				unit = it.next();// リストからユニットを取得
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
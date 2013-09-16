package jp.ac.kccollege.ohya.android.fusenman;

import java.util.HashMap;

import jp.ac.kccollege.ohya.android.fusenman.unit.EnemyFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.ItemFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.PlayerFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.MainPlayer;
import jp.ac.kccollege.ohya.android.fusenman.unit.UnitList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Fusenman {

	/*------------------------------------------------------------------------------
 	ゲーム全体で使用する変数の定義
	 ------------------------------------------------------------------------------*/
	// イメージ画像　はサブクラスで使用するのでprotected 
	/**背景*/
	protected static Bitmap bgImageFar;
	protected static Bitmap bgImageNear;
	protected static BackGround bgFar;
	protected static BackGround bgNear;	
	
	//オブジェクト管理変数
	/**プレイヤー*/
	protected static MainPlayer mainPlayer; 
	
	/**ユニットリスト*/
	protected static UnitList playerUnits = null;
	protected static UnitList enemyUnits = null;
	protected static UnitList itemUnits = null;
	
	/**管理クラス*/
	protected static EnemyFactory enemyFactory=null;
	protected static PlayerFactory playerFactory=null;
	protected static ItemFactory itemFactory=null;	
	
	/**キャラタイプ管理*/
	public static enum CharType{
		PLAYER,PSHOT,
		TSUBAME,KARASU,AHIRU,KILLER,ANPANMAN,ESHOT,
		BOSS1,BOSS2,BOSS3,
		MESSAGE1,MESSAGE2,MESSAGE3,MESSAGE4,MESSAGE5,
		BOMB,
		ITEM1,ITEM2;
	}
	/**画像管理リスト*/
	protected HashMap<Enum<CharType>,Drawable> unitImages2
		= new HashMap<Enum<CharType>, Drawable>();
	
	/**ステージ数*/
	public static int stageCount = 1; 

}

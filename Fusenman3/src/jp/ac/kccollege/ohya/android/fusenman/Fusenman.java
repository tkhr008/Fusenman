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
	public static final int PLAYER = 0;
	public static final int SHOT_OF_PLAYER = 1;
	public static final int TSUBAME = 2;
	public static final int KARASU = 3;
	public static final int KILLER = 4;
	public static final int AHIRU = 5;
	public static final int ANPANMAN = 6;
	public static final int BOMB = 7;
	public static final int SHOT_OF_ENEMY = 8;
	public static final int ITEM1 = 9;
	public static final int ITEM2 = 10;
	
	public static final int BOSS1 = 1001;
	public static final int BOSS2 = 1002;
	public static final int BOSS3 = 1003;
	public static final int MESSAGE1= 111;
	public static final int MESSAGE2= 112;
	public static final int MESSAGE3 = 113;
	public static final int MESSAGE4 = 114;
	public static final int MESSAGE5 = 115;

	public static enum CharType{
		PLAYER,PSHOT,TSUBAME,KARASU,AHIRU,KILLER,ANPANMAN,ESHOT,ITEM1,ITEM2,BOMB,BOSS1,BOSS2,BOSS3,
		MESSAGE1,MESSAGE2,MESSAGE3,MESSAGE4,MESSAGE5;
	}
	protected HashMap<Enum<CharType>,Drawable> unitImages2 = new HashMap<Enum<CharType>, Drawable>();
	
	/**キャラ画像管理配列*/
	private static final int CHAR_COUNT = 10;
	protected static Drawable[] unitImages = new Drawable[CHAR_COUNT];	
	//ゲームステータス
	/**初回の敵の数*/
	protected static int enemyNum = 3; 
	/**ステージ数*/
	public static int stageCount = 1; 

}

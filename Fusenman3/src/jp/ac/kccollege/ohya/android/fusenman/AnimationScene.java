package jp.ac.kccollege.ohya.android.fusenman;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.framework.game2D.Scene;
import jp.ac.kccollege.ohya.android.fusenman.unit.EnemyFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.ItemFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.PlayerFactory;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractUnit;
import jp.ac.kccollege.ohya.android.fusenman.unit.MainPlayer;

abstract class AnimationScene extends Fusenman implements Scene {
	
	/**データが初期か済みかどうか？*/
	private static boolean inited = false;

	/**初期化
	 * @see jp.ac.kccollege.ohya.android.framework.game2D.Scene#init(jp.ac.kccollege.ohya.android.framework.game2D.GameView)
	 */
	@Override
	public void init(GameView view) {
		//データが初期か済みかどうか？
		//複数の派生シーン間で共通のデータを一回しか初期化しない
		if (inited) {
			return;
		}
		//ここに初期化処理を記述
		inited = true;

		// 画像をファイルから読み込む
		Resources res = view.getResources();

		unitImages2.put(CharType.PLAYER, res.getDrawable(R.drawable.fusenman))	;
		unitImages2.put(CharType.PSHOT, res.getDrawable(R.drawable.shot))	;
		unitImages2.put(CharType.TSUBAME, res.getDrawable(R.drawable.tsubame))	;
		unitImages2.put(CharType.KARASU, res.getDrawable(R.drawable.karasu))	;
		unitImages2.put(CharType.KILLER, res.getDrawable(R.drawable.killer))	;
		unitImages2.put(CharType.AHIRU, res.getDrawable(R.drawable.ahiru))	;
		unitImages2.put(CharType.ANPANMAN, res.getDrawable(R.drawable.anpanman))	;
		unitImages2.put(CharType.BOMB, res.getDrawable(R.drawable.bomb))	;
		unitImages2.put(CharType.ESHOT, res.getDrawable(R.drawable.e_shot))	;
		unitImages2.put(CharType.ITEM1, (AnimationDrawable)res.getDrawable(R.drawable.anim))	;
		unitImages2.put(CharType.BOSS1, res.getDrawable(R.drawable.karasu));
		unitImages2.put(CharType.BOSS2, res.getDrawable(R.drawable.tsubame));
		unitImages2.put(CharType.BOSS3, res.getDrawable(R.drawable.anpanman));

		//ユニット画像の設定
		AbstractUnit.setUnitImages(unitImages2);	

		// 背景画像をビットマップとして読み込み
		bgImageFar = BitmapFactory.decodeResource(res, R.drawable.back_win);
		bgImageNear = BitmapFactory.decodeResource(res, R.drawable.clouds);
	
		// 背景画像の調整
		bgFar = new BackGround(bgImageFar,view.getDrawRect(),2);
		bgNear = new BackGround(bgImageNear,view.getDrawRect(),6);
		
		//プレイヤーリストの生成
		playerFactory = PlayerFactory.getInstance();
		mainPlayer = (MainPlayer)playerFactory.create(CharType.PLAYER);//自機の生成 
		playerUnits = playerFactory.getUnitList();
		
		//敵リストの生成
		enemyFactory = EnemyFactory.getInstance();
		enemyFactory.setTarget(mainPlayer);//敵から見たターゲット設定
		enemyUnits = enemyFactory.getUnitList();
		
		//アイテムリストの生成
		itemFactory=ItemFactory.getInstance();
		itemUnits = itemFactory.getUnitList();
	}


	@Override
	public void start(GameView view) {
		//シーンが切り替わった時に最初に呼び出される処理
	}


	/**更新処理*/
	@Override
	public void process(GameView view) {
		
		//ユニットの物理状態更新処理
		enemyUnits.process(view,playerUnits);
		playerUnits.process(view,enemyUnits);
		itemUnits.process(view, itemUnits);

	}


	/**描画処理*/
	@Override
	public void draw(Canvas canvas) {

		// 背景画像の描画
		bgFar.draw(canvas);
		bgNear.draw(canvas);
		
		//ユニットの描画
		playerUnits.draw(canvas);
		enemyUnits.draw(canvas);
		itemUnits.draw(canvas);
	}

}

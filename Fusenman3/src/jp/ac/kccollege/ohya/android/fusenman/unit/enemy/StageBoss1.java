package jp.ac.kccollege.ohya.android.fusenman.unit.enemy;

import jp.ac.kccollege.ohya.android.framework.game2D.GameView;
import jp.ac.kccollege.ohya.android.fusenman.Fusenman.CharType;
import jp.ac.kccollege.ohya.android.fusenman.unit.AbstractEnemy;
import jp.ac.kccollege.ohya.android.fusenman.unit.InterfaceShooter;

/** ステージボス管理クラス */
public class StageBoss1 extends AbstractEnemy
implements InterfaceShooter {

	/** 加速 */
	private float accel = 3;

	/** コンストラクタ */
	public StageBoss1() {
		// super(キャラタイプ,x,y,w,h)
		super(0, 0, 100, 100);
		myType = CharType.BOSS1;
		init();
	}
	
	/** 初期化 */
	@Override
	public void init() {
		super.init();
		hSpeed = accel;
		life = 30;
		chanceOfShot = 2.0f;// 弾を発射する確率
	}

	/** 開始 */
	@Override
	public void start(GameView view) {
		super.start(view);
		accel += 3;// 加速値の増加
		hSpeed = (float) (Math.random() * accel);// x軸スピードの変化
	}

	/** 物理状態の更新 */
	@Override
	public void process(GameView view) {
		super.process(view);

		switch (status) {

		case LIVE:// 生存
		case DAMAGE:// ダメージ

			if (shotReady(chanceOfShot)) {// 準備ができたら攻撃
				attack();
			}
			char_x -= hSpeed; // 移動
			yuragi(0.3f, 8.0f); // ゆらぎ
			break;

		default:
			break;
		}

		// 現在の位置更新
		offset(char_x, char_y);
	}

	/** ダメージを受けた時 */
	public void damage() {
		super.damage();
		if (life % 8 == 0) {
			hSpeed = -8;// 一定のダメージごとに後方移動
			sizeUp();
		}
	}

	/**弾発射*/
	@Override
	public CharType shoot() {
		live();
		return CharType.ESHOT;
	}
}

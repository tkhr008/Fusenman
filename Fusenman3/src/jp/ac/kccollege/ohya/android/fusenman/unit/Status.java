package jp.ac.kccollege.ohya.android.fusenman.unit;

public enum Status {

	/**初期化*/	
	INIT,
	/**準備中*/
	READY,
	/**開始*/
	START,
	/**生存*/
	LIVE,
	/**ダメージ*/
	DAMAGE,
	/**消滅*/	
	DEAD,
	/**攻撃*/
	ATTACK;
		
    /** 初期化中 */
    public boolean isInit() {
        return INIT == this;
    }
    
    /** 準備中 */
    public boolean isReady() {
        return READY == this;
    }

    /** 生存中 */
    public boolean isLive() {
        return LIVE== this;
    }
    
    /**ダメージ*/
    public boolean isDamage(){
    	return DAMAGE == this;
    }
    
    /**消滅中*/
    public boolean isDead(){
    	return DEAD == this;
    }

    /**攻撃中*/
    public boolean isAttack(){
    	return ATTACK== this;
    }
}

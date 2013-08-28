package jp.ac.kccollege.ohya.android.fusenman.unit;

public enum Status {

	/**������*/	
	INIT,
	/**������*/
	READY,
	/**�J�n*/
	START,
	/**����*/
	LIVE,
	/**�_���[�W*/
	DAMAGE,
	/**����*/	
	DEAD,
	/**�U��*/
	ATTACK;
		
    /** �������� */
    public boolean isInit() {
        return INIT == this;
    }
    
    /** ������ */
    public boolean isReady() {
        return READY == this;
    }

    /** ������ */
    public boolean isLive() {
        return LIVE== this;
    }
    
    /**�_���[�W*/
    public boolean isDamage(){
    	return DAMAGE == this;
    }
    
    /**���Œ�*/
    public boolean isDead(){
    	return DEAD == this;
    }

    /**�U����*/
    public boolean isAttack(){
    	return ATTACK== this;
    }
}

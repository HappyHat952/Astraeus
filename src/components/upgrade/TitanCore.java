package components.upgrade;

public class TitanCore extends Upgrade
{
	public static final int SIZE = 10;
	public static final float USE_TIME_SCALAR = 1f;
	public static final float COOLDOWN_SCALAR = 1f;
	public static final int PLATING = 3000;
	public static final float ACCURACY_BONUS = 0f;
	public static final int SHIELD = 2000;
	public static final float SHIELD_REGEN_PER_SECOND = 5f;	
	public static final float BLOCK = .4f;

	public static final float POWER_BONUS = 3f;

	public TitanCore()
	{
		super();
	}
	
	public void onAddition()
	{
		super.onAddition();
		
		getOwner().addBlock(BLOCK);
		getOwner().increaseMaxPlating(PLATING);		
		getOwner().increaseMaxShield(SHIELD);
		getOwner().increaseShieldRegen(SHIELD_REGEN_PER_SECOND);
		getOwner().applyUseMultiplier(USE_TIME_SCALAR);
		getOwner().applyCooldownTimeMultiplier(COOLDOWN_SCALAR);
		getOwner().applyAccuracyBonus((ACCURACY_BONUS));
		getOwner().applyMinRangeMultiplier(0);
		getOwner().increasePower(POWER_BONUS);

	}

}

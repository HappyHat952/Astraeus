package components.weapon.utility;

public class RepairBeam extends HealingWeapon
{
	public static final int SIZE = 1;
	public static final int MAX_RANGE = 600;
	public static final float HEAL = 6f;
	public static final int USE_TIME = 0;
	public static final int COOLDOWN = 60;
	public static final int MASS = 4;
	public static final float ACCURACY = 1.00f;

	public static final int ANIM_BEAM_WIDTH = 14;
	public static final int ANIM_BEAM_DURATION = USE_TIME+COOLDOWN;

	public RepairBeam()
	{
		super();
		size = SIZE;
		cooldown = COOLDOWN;
		useTime = USE_TIME;
		damageType = DAMAGE_TYPE;
		weaponType = WEAPON_TYPE;
		mass = MASS;
		maxRange = MAX_RANGE;
		accuracy = ACCURACY;

		beamWidth = ANIM_BEAM_WIDTH;
		beamDuration = ANIM_BEAM_DURATION;
		healAmount = HEAL;
		useSlow = USE_SLOW_LIGHT_AVERAGE;

	}



}

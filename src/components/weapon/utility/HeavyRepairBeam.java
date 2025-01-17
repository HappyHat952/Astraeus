package components.weapon.utility;

import components.weapon.Weapon;

public class HeavyRepairBeam extends HealingWeapon
{
	public static final int SIZE = 2;
	public static final int MAX_RANGE = RepairBeam.MAX_RANGE;
	public static final float HEAL = RepairBeam.HEAL * Weapon.HEAVY_POWER_SCALAR;
	public static final int MASS = (int) (RepairBeam.MASS * Weapon.HEAVY_MASS_SCALAR);
	public static final int USE_TIME = RepairBeam.USE_TIME;
	public static final int COOLDOWN = RepairBeam.COOLDOWN;
	public static final float ACCURACY = RepairBeam.ACCURACY;
	public static final int ANIM_BEAM_WIDTH = 14;
	public static final int ANIM_BEAM_DURATION = USE_TIME+COOLDOWN;

	public HeavyRepairBeam()
	{
		super();
		size = SIZE;
		cooldown = COOLDOWN;
		useTime = USE_TIME;
		mass = MASS;
		maxRange = MAX_RANGE;
		accuracy = ACCURACY;
		beamWidth = ANIM_BEAM_WIDTH;
		beamDuration = ANIM_BEAM_DURATION;
		healAmount = HEAL;
		adjective = "Heavy";
		useSlow = USE_SLOW_LIGHT_AVERAGE * HEAVY_MASS_SCALAR;

	}






}

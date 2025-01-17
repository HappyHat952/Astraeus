package components.weapon.kinetic;

import components.weapon.Weapon;

public class HeavyAutocannon extends KineticWeapon
{
	public static final int SIZE = 2;
	public static final int MAX_RANGE = Autocannon.MAX_RANGE;
	public static final float DAMAGE = Autocannon.DAMAGE * Weapon.HEAVY_POWER_SCALAR;
	public static final int MASS = (int) (Autocannon.MASS * Weapon.HEAVY_MASS_SCALAR);
	public static final int USE_TIME = Autocannon.USE_TIME;
	public static final int COOLDOWN = Autocannon.COOLDOWN;
	public static final float ACCURACY = Autocannon.ACCURACY;
	public static final int NUM_SHOTS = Autocannon.NUM_SHOTS;
	public static final int BULLET_TRAVEL_TIME_MAX = Autocannon.BULLET_TRAVEL_TIME_MAX;
	public static final int BULLET_DELAY = Autocannon.BULLET_DELAY;

	public HeavyAutocannon()
	{
		super(BULLET_DELAY);
		size = SIZE;
		damage = DAMAGE;
		maxRange = MAX_RANGE;
		cooldown = COOLDOWN;
		useTime = USE_TIME;
		accuracy = ACCURACY;
		numShots = NUM_SHOTS;
		maxTravelTime = BULLET_TRAVEL_TIME_MAX;
		damageType = DAMAGE_TYPE;
		weaponType = WEAPON_TYPE;
		mass = MASS;
		adjective = "Heavy";
		soundPitch = 1.2f;
		soundVolume = .8f;
		animationSize = 8;
		useSlow = USE_SLOW_LIGHT_MINOR * HEAVY_MASS_SCALAR;
	}






}

package components.weapon.kinetic;

public class Autocannon extends KineticWeapon
{
	public static final int SIZE = 1;
	public static final int MINERAL_COST = 1;
	public static final int MAX_RANGE = 400;
	public static final int DAMAGE = 16;
	public static final int NUM_SHOTS = 3;
	public static final int USE_TIME = 60;
	public static final int COOLDOWN = 180;
	public static final int BULLET_DELAY = 3;
	public static final int BULLET_TRAVEL_TIME_MAX = 15;
	public static final float ACCURACY = .20f;
	public static final int MASS = 3;

	public Autocannon()
	{
		super(BULLET_DELAY);
		size = SIZE;
		damage = DAMAGE;
		maxRange = MAX_RANGE;
		cooldown = COOLDOWN;
		useTime = USE_TIME;
		accuracy = ACCURACY;
		numShots = NUM_SHOTS;
		damageType = DAMAGE_TYPE;
		weaponType = WEAPON_TYPE;
		mass = MASS;
		maxTravelTime = BULLET_TRAVEL_TIME_MAX;
		adjective = "";
		soundPitch = 1.2f;
		soundVolume = .8f;
		animationSize = 8;
		useSlow = USE_SLOW_LIGHT_MINOR;
	}



}

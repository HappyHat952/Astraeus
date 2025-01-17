package components.weapon.explosive;

public class Missile extends ExplosiveWeapon
{
	public static final int SIZE = 1;
	public static final int MAX_RANGE = 800;
	public static final int DAMAGE = 25;
	public static final int RADIUS = 100;
	public static final float ACCURACY = 0;
	public static final int USE_TIME = 120;
	public static final int COOLDOWN = 120;
	public static final int MASS = 7;

	public Missile()
	{
		super();
		size = SIZE;
		damage = DAMAGE;
		maxRange = MAX_RANGE;
		cooldown = COOLDOWN;
		useTime = USE_TIME;
		accuracy = ACCURACY;
		damageType = DAMAGE_TYPE;
		weaponType = WEAPON_TYPE;
		mass = MASS;
		radius = RADIUS;
		adjective = "";
		soundPitchMin = .95f;
		soundPitchMax = 1.1f;
		useSlow = USE_SLOW_LIGHT_MAJOR;

	}



}

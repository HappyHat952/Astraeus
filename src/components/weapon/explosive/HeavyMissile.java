package components.weapon.explosive;

import components.weapon.Weapon;

public final class HeavyMissile extends ExplosiveWeapon
{
	public static final int SIZE = 2;
	public static final int MAX_RANGE = Missile.MAX_RANGE;
	public static final float DAMAGE = Missile.DAMAGE * Weapon.HEAVY_POWER_SCALAR;
	public static final int MASS = (int) (Missile.MASS * Weapon.HEAVY_MASS_SCALAR);

	public static final int USE_TIME = Missile.USE_TIME;
	public static final int COOLDOWN = Missile.COOLDOWN;
	public static final float ACCURACY = Missile.ACCURACY;
	public static final int RADIUS = Missile.RADIUS;

	public HeavyMissile()
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
		adjective = "Heavy";
		soundPitchMin = .95f;
		soundPitchMax = 1.1f;
		useSlow = USE_SLOW_LIGHT_MAJOR  * HEAVY_MASS_SCALAR;
	}


}

package components.weapon.utility;

import animations.AnimationManager;
import animations.projectile.AnimProjectileBulletCombat;
import components.DamageType;
import components.weapon.WeaponTargetEntity;
import components.weapon.WeaponType;
import conditions.instant.Damage;
import objects.entity.Entity;
import objects.entity.missile.MissileEntity;
import org.newdawn.slick.Color;

public class AntiMissileSystem extends WeaponTargetEntity
{
	public static final int SIZE = 1;
	public static final int MAX_RANGE = 700;
	public static final float DAMAGE = 3;
	public static final int USE_TIME = 1;
	public static final int COOLDOWN = 10;
	public static final float ACCURACY = 1.0f;
	public static final int BULLET_TRAVEL_TIME_MAX = 30;
	public static final int BULLET_SIZE = 5;

	public static final WeaponType WEAPON_TYPE = WeaponType.UTILITY;
	public static final DamageType DAMAGE_TYPE = DamageType.TRUE;
	public static final int MASS = 10;

//	public static final int ANIM_BEAM_WIDTH = 3;
//	public static final int ANIM_BEAM_DURATION = 5;


	public AntiMissileSystem()
	{
		super();
		size = SIZE;
		damage = DAMAGE;
		maxRange = MAX_RANGE;
		cooldown = COOLDOWN;
		useTime = USE_TIME;
		accuracy = ACCURACY;
		mass = MASS;
		weaponType = WEAPON_TYPE;
		damageType = DAMAGE_TYPE;
		maxTravelTime = BULLET_TRAVEL_TIME_MAX;
		useSlow = USE_SLOW_LIGHT_AVERAGE * SIZE;
		name = "Anti Missile System";
	}

	public void applyMod()
	{

	}


	public int getMinRange() 						{	return 0;											}

	public boolean rotateUser()						{	return false;	}

	
	protected void playAudio()
	{
		//Sounds.laser.play(getOwner().getPosition(), 1.50f);
	}

	protected void animation(Entity target)
	{	
		AnimationManager.add(new AnimProjectileBulletCombat(getOwner(), target, BULLET_SIZE, getActualTravelTime(target), Color.gray));

//		AnimationManager.add(new AnimBeamBurst(getOwner(), target, ANIM_BEAM_WIDTH, ANIM_BEAM_DURATION));		
	}

	protected void activation(Entity target)
	{
		// Shadowflight missiles have a 50% chance to evade damage
//		if(target instanceof MissileShadowflight)
//		{
//			if(Math.random() < ShadowflightMissile.EVADE_CHANCE)
//			{
//				return;
//			}
//		}

		target.addCondition(new Damage(getDamage(), DAMAGE_TYPE, getActualTravelTime(target)));
	}
	
	public void use()
	{
		MissileEntity m;

		if(canUse())
		{
			m = getOwner().getNearestEnemyMissile();
			
			if(canUse(m))
			{

				useLockTimer = getUseTime();
				start(m);
			}
		}		
	}
	
	
	
	public void use(Entity e)
	{
		use();
	}


}

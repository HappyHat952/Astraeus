package components.weapon.utility;

import components.DamageType;
import components.mod.utility.NarcissusMod;
import components.weapon.Weapon;
import components.weapon.WeaponType;
import conditions.buffs.Fast;
import objects.entity.unit.Unit;

public class SpeedBoost extends Weapon
{
	public static final int SIZE = 1;
	public static final float SPEED_MULTIPLIER = 2.5f;
	public static final int DURATION = 360;
	public static final int USE_TIME = 1;
	public static final int COOLDOWN = 900;

	public static final WeaponType WEAPON_TYPE = WeaponType.UTILITY;
	public static final DamageType DAMAGE_TYPE = DamageType.NONE;
	public static final int MASS = 0;

	public SpeedBoost()
	{
		super();
		size = SIZE;
		cooldown = COOLDOWN;
		useTime = USE_TIME;
		duration = DURATION;
		damageType = DAMAGE_TYPE;
		weaponType = WEAPON_TYPE;
		mass = MASS;
		useSlow = USE_SLOW_NONE;
	}

	public void applyMod()
	{

	}

	public boolean appliesCondition()				{ 	return true;					}
	public boolean hasAppliedCondition(Unit u)		{	return u.getConditions().containsType(Fast.class);	}
	public int getAppliedConditionTimeLeft(Unit u)	{	return u.getConditions().get(Fast.class).getTimeLeft();}
	public int getDuration()						{ 	return DURATION;	}

	protected void playAudio()
	{
		
	}
	
	protected void animation() 
	{
		
	}
	
	protected void activation() 
	{
		if(getOwner().hasMod(NarcissusMod.class))
		{
			getOwner().addCondition(new Fast(DURATION, NarcissusMod.EFFICACY_SCALE));
		}
		else
		{
			getOwner().addCondition(new Fast(DURATION));
		}
	}

}

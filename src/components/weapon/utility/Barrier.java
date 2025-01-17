package components.weapon.utility;

import components.DamageType;
import components.mod.utility.NarcissusMod;
import components.weapon.Weapon;
import components.weapon.WeaponType;
import conditions.buffs.Fast;
import conditions.buffs.Fortified;
import conditions.debuffs.Pulse;
import objects.entity.unit.Unit;

public class Barrier extends Weapon
{
	public static final int SIZE = 1;
	public static final int DURATION = 600;
	public static final int USE_TIME = 1;
	public static final int COOLDOWN = 1800;

	public static final WeaponType WEAPON_TYPE = WeaponType.UTILITY;
	public static final DamageType DAMAGE_TYPE = DamageType.NONE;
	public static final int MASS = 2;

	public Barrier()
	{
		super();
		size = SIZE;
		cooldown = COOLDOWN;
		useTime = USE_TIME;
		duration = DURATION;
		damageType = DAMAGE_TYPE;
		weaponType = WEAPON_TYPE;
		mass = MASS;
		useSlow = USE_SLOW_LIGHT_MINOR * SIZE;

	}

	public void applyMod()
	{

	}

	public boolean appliesCondition()				{ 	return true;					}
	public boolean hasAppliedCondition(Unit u)		{	return u.getConditions().containsType(Fortified.class);	}
	public int getAppliedConditionTimeLeft(Unit u)	{	return u.getConditions().get(Fortified.class).getTimeLeft();}

	protected void playAudio()
	{
		//Sounds.aegis.play(getOwner().getPosition());
		//AudioManager.playLaser(owner.getPosition(), 1.5f);
	}
	
	protected void animation() 
	{
		
	}

	protected void activation() 
	{
		if(getOwner().hasMod(NarcissusMod.class))
		{
			getOwner().addCondition(new Fortified(DURATION, NarcissusMod.EFFICACY_SCALE));
		}
		else
		{
			getOwner().addCondition(new Fortified(DURATION));
		}
	}
	


}

package components.weapon.utility;

import animations.AnimationManager;
import animations.circles.AnimEMP;
import components.DamageType;
import components.mod.utility.AthenaMod;
import components.mod.utility.SisyphusMod;
import components.weapon.Weapon;
import components.weapon.WeaponType;
import conditions.Condition;
import conditions.debuffs.Stun;
import objects.entity.unit.Unit;

import java.util.ArrayList;

public class ElectromagneticPulse extends Weapon
{
	public static final int SIZE = 2;
	public static final int DURATION = 180;
	public static final int USE_TIME = 60;
	public static final int COOLDOWN = 4000;
	public static final int RADIUS = 500;
	public static final int ANIM_DURATION = 80;
	public static final WeaponType WEAPON_TYPE = WeaponType.UTILITY;
	public static final int MASS = 20;
	public static final DamageType DAMAGE_TYPE = DamageType.NONE;
	public static final float ACCURACY = 1.0f;

	public ElectromagneticPulse()
	{
		super();
		size = SIZE;
		cooldown = COOLDOWN;
		useTime = USE_TIME;
		duration = DURATION;
		damageType = DAMAGE_TYPE;
		weaponType = WEAPON_TYPE;
		mass = MASS;
		radius = RADIUS;
		accuracy = ACCURACY;
		useSlow = USE_SLOW_LIGHT_MAJOR * SIZE;
	}

	public int getMinRange()						{ 	return 0;												}

	public boolean appliesCondition()				{ 	return true;								}
	public boolean hasAppliedCondition(Unit u)		{	return u.getConditions().containsType(Stun.class);	}
	public int getAppliedConditionTimeLeft(Unit u)	{	return u.getConditions().get(Stun.class).getTimeLeft();}
	public Class<? extends Condition> getAppliedConditionType()		{ 	return Stun.class;					}

	public void applyMod()
	{
		if(getOwner().hasMod(SisyphusMod.class))
		{
			name = SisyphusMod.NAME_EMP;
			cooldown = Math.round(cooldown * (1-SisyphusMod.COOLDOWN_REDUCTION));
		}

		if(getOwner().hasMod(AthenaMod.class))
		{
			name = "Tactical EMP";
			radius += Math.round(radius * AthenaMod.AREA_INCREASE);
		}
	}

	protected void playAudio()
	{
		
	}
	
	protected void animation() 
	{
		AnimationManager.add(new AnimEMP(getOwner(), getRadius()*2, ANIM_DURATION));

	}
	
	protected void activation() 
	{
		ArrayList<Unit> enemies = getOwner().getEnemies();
		
		for(Unit u : enemies)
		{
			float d = getOwner().getDistance(u);
			if(d <  getRadius())
			{
				int delay = (int) (ANIM_DURATION * (d / getRadius()));
				applyStun(u, delay);
//				applyPulse(u, delay);
			}	
		}
	}
	
	public void applyStun(Unit u, int delay)
	{
		if(u.hasCondition(Stun.class) && u.getCondition(Stun.class).getDuration() < DURATION)
		{
			u.getCondition(Stun.class).setDuration(DURATION);
		}
		else
		{
			u.addCondition(new Stun(DURATION, delay));
		}
	}
	
	
//	public void applyPulse(Unit u, int delay)
//	{
//		if(u.hasCondition(Pulse.class) && u.getCondition(Pulse.class).getDuration() < DURATION)
//		{
//			u.getCondition(Pulse.class).setDuration(DURATION);
//		}
//		else
//		{
//			u.addCondition(new Pulse(DURATION, delay));
//		}
//	}
	
	


}

package components.weapon.utility;

import animations.AnimationManager;
import animations.beams.AnimBeamConstantTransparent;
import components.DamageType;
import components.mod.offense.PoseidonMod;
import components.weapon.WeaponTargetUnit;
import components.weapon.WeaponType;
import conditions.buffs.Fast;
import conditions.buffs.Fortified;
import conditions.debuffs.Pull;
import conditions.debuffs.Push;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Unit;

public class Pullbeam extends WeaponTargetUnit
{
	public static final int SIZE = 2;
	public static final int MAX_RANGE = 500;
	public static final float PULL_FORCE = 5f;
	public static final int USE_TIME = 0;
	public static final int COOLDOWN = 30;

	public static final WeaponType WEAPON_TYPE = WeaponType.UTILITY;
	public static final DamageType DAMAGE_TYPE = DamageType.NONE;
	public static final int MASS = 8;
	public static final float ACCURACY = 1.0f;

	public static final int ANIM_BEAM_WIDTH = 11;
	public static final int ANIM_BEAM_DURATION = USE_TIME+COOLDOWN;
	public static final int ANIM_BEAM_ALPHA = 75;

	public Pullbeam()
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
		useSlow = USE_SLOW_LIGHT_AVERAGE * SIZE;
	}
	public void applyMod()
	{

	}
	protected void playAudio()
	{

	}
	
	protected void animation(Unit a, boolean isHit) 
	{	
		AnimationManager.add(new AnimBeamConstantTransparent(getOwner(), a, ANIM_BEAM_WIDTH, ANIM_BEAM_DURATION, ANIM_BEAM_ALPHA - 25, ANIM_BEAM_ALPHA + 25));		
	}
	
	protected void activation(Unit target, boolean isHit) 
	{
		if(isHit && !(target instanceof BaseShip) && !target.hasCondition(Fast.class)  && !target.hasCondition(Fortified.class))
		{
			// Pull is reduced by mass as a % of maximum mass

			target.addCondition(new Pull(1, 0, PULL_FORCE, getOwner()));
//
//
//			float pullFactor = 1 - (target.getMass() / 100.0f);
//			target.changeSpeedForcedMovement(PULL_FORCE * pullFactor, target.getAngleToward(getOwner().getCenterX(), getOwner().getCenterY()));
			target.markPulled();
		}
	
	}
	

}

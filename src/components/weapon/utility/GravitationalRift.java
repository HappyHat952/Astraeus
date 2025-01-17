package components.weapon.utility;

import components.DamageType;
import components.mod.utility.AthenaMod;
import components.mod.utility.SisyphusMod;
import components.weapon.WeaponTargetUnit;
import components.weapon.WeaponType;
import engine.states.Game;
import objects.entity.unit.Unit;
import objects.zone.RiftZone;

public class GravitationalRift extends WeaponTargetUnit
{
	public static final int SIZE = 2;
	public static final int MAX_RANGE = 600;
	public static final float PULL_FORCE = 1.20f;
	public static final int USE_TIME = 60;
	public static final int COOLDOWN = 2000;
	public static final int RADIUS = 250;
	public static final int DURATION = 6*60;

	public static final DamageType DAMAGE_TYPE = DamageType.NONE;
	public static final int MASS = 20;
	public static final WeaponType WEAPON_TYPE = WeaponType.UTILITY;

	public static final float ACCURACY = 1.0f;

	public GravitationalRift()
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
		maxRange = MAX_RANGE;
	}

	public void applyMod()
	{
		if(getOwner().hasMod(SisyphusMod.class))
		{
			name = SisyphusMod.NAME_RIFT;
			cooldown = Math.round(cooldown * (1-SisyphusMod.COOLDOWN_REDUCTION));
		}

		if(getOwner().hasMod(AthenaMod.class))
		{
			name = "Tactical Rift";
			radius += Math.round(radius * AthenaMod.AREA_INCREASE);
		}
	}

	protected void playAudio()
	{

	}
	
	protected void animation(Unit a, boolean isHit) 
	{	

	}
	
	protected void activation(Unit target, boolean isHit) 
	{
		Game.addZone(new RiftZone(target.getX(), target.getY(), getDuration(), getOwner().getPlayer(), getRadius(), PULL_FORCE));
	}
	

}

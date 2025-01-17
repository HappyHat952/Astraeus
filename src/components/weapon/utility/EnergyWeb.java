package components.weapon.utility;

import components.DamageType;
import components.mod.utility.AthenaMod;
import components.weapon.Weapon;
import components.weapon.WeaponType;
import engine.states.Game;
import objects.zone.WebZone;

public class EnergyWeb extends Weapon
{
	public static final int SIZE = 2;
	public static final int MAX_RANGE = 0;

	public static final int USE_TIME = 1;
	public static final int COOLDOWN = 1;
	public static final int RADIUS = 500;
	public static final int DURATION = 2;
	public static final float DAMAGE = 0;
	public static final float SLOW = .60f;

	public static final DamageType DAMAGE_TYPE = DamageType.PULSE;
	public static final int MASS = 10;
	public static final WeaponType WEAPON_TYPE = WeaponType.UTILITY;

	public static final float ACCURACY = 1.0f;

	public EnergyWeb()
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
		useSlow = USE_SLOW_LIGHT_MINOR * SIZE;

	}

	public void applyMod()
	{
		if(getOwner().hasMod(AthenaMod.class))
		{
			name = "Tactical Web";
			radius += Math.round(radius * AthenaMod.AREA_INCREASE);
		}
	}

	protected void playAudio()
	{

	}
	
	protected void animation()
	{	
		//AnimationManager.add(new AnimBeamConstantTransparent(getOwner(), a, ANIM_BEAM_WIDTH, ANIM_BEAM_DURATION, ANIM_BEAM_ALPHA - 25, ANIM_BEAM_ALPHA + 25));		
	}
	
	protected void activation()
	{
		Game.addZone(new WebZone(getOwner().getX(), getOwner().getY(), getDuration(), SLOW, DAMAGE, getOwner().getPlayer(), getRadius()));
	}
	

}

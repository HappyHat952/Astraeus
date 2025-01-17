package components.weapon.utility;

import components.DamageType;
import components.mod.utility.AthenaMod;
import components.mod.utility.SisyphusMod;
import components.weapon.Weapon;
import components.weapon.WeaponType;
import conditions.buffs.Powerful;
import engine.Utility;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class CommandRelay extends Weapon
{
	public static final int SIZE = 2;
	public static final int DURATION = 2;
	public static final int USE_TIME = 1;
	public static final int COOLDOWN = 1;
	public static final int RADIUS = 250;

	public static final WeaponType WEAPON_TYPE = WeaponType.UTILITY;
	public static final DamageType DAMAGE_TYPE = DamageType.NONE;
	public static final int MASS = 20;
	
	float[] percents;

	public void applyMod()
	{
		if(getOwner().hasMod(AthenaMod.class))
		{
			name = "Tactical Command Relay";
			radius += Math.round(radius * AthenaMod.AREA_INCREASE);
		}

//		System.out.println(radius);
	}
	
	public CommandRelay()
	{
		super();
		
		
		percents = new float[3];
		
		for(int i = 0; i < percents.length; i++)
		{
			percents[i] = i * 1.0f / percents.length;
		//	System.out.println(percents[i]);
		}

		size = SIZE;
			cooldown = COOLDOWN;
			useTime = USE_TIME;
			duration = DURATION;
			damageType = DAMAGE_TYPE;
			weaponType = WEAPON_TYPE;
			mass = MASS;
			radius = RADIUS;
			useSlow = USE_SLOW_LIGHT_MAJOR * SIZE;

	}




	public boolean appliesCondition()				{ 	return true;					}
	public boolean hasAppliedCondition(Unit u)		{	return u.getConditions().containsType(Powerful.class);	}
	public int getAppliedConditionTimeLeft(Unit u)	{	return u.getConditions().get(Powerful.class).getTimeLeft();}

	
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
		ArrayList<Unit> allies = getOwner().getAlliesInRadius(radius);
		
		for(Unit u : allies)
		{
			if(u != getOwner())
			{
				if(u.hasCondition(Powerful.class))
				{
					u.getCondition(Powerful.class).setDuration(2);
				}
				else
				{
					u.addCondition(new Powerful(2));
				}
			}
		}		
	}	
	
	public void onAddition()
	{
		super.onAddition();
	}
	
	public void render(Graphics g)
	{
		if(inUse() || onCooldown())
		{
			g.setColor(Utility.modifyAlpha(getOwner().getPlayer().getColorPrimary(), 60));

			
			g.setLineWidth(7);
			g.drawOval(getOwner().getCenterX() - radius, getOwner().getCenterY() - radius, radius*2, radius*2);
		
			g.setColor(Utility.modifyAlpha(getOwner().getPlayer().getColorSecondary(), 30));
			for(int i = 0; i < percents.length; i++)
			{
				g.drawOval(getOwner().getCenterX() - radius*percents[i], getOwner().getCenterY() - radius*percents[i], radius*2*percents[i], radius*2*percents[i]);
			}
		}

	}
	
	public void update() 
	{
		super.update();
		
		for(int i = 0; i < percents.length; i++)
		{
			percents[i] += .003;
			
			if(percents[i] > 1)
			{
				percents[i] = 0;
			}
		}
	}


}

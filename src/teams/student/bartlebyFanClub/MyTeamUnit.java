package teams.student.bartlebyFanClub;

import components.weapon.Weapon;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import player.Player;

import java.util.ArrayList;

public abstract class MyTeamUnit extends Unit 
{

	final double id = Math.random();
	protected boolean defending;

	ArrayList<Integer> numAllies = new ArrayList<Integer>();

	public MyTeamUnit(Player p)
	{
		super(p);
	}
	
	public BartlebyFanClub getPlayer()
	{
		return (BartlebyFanClub) super.getPlayer();
	}
	
	public void action() 
	{
		attack(getWeaponOne());
		attack(getWeaponTwo());
		movement();
	}
		
	public void attack(Weapon w)
	{
		if(w == null)
		{
			return;
		}

		Unit enemy = Analysis.getLowestEnemyHealthInRadius(this, w.getMaxRange());

		if(enemy != null && w != null)
		{
			w.use(enemy);	
		}
	}
		
	public void movement()
	{
		Unit enemy = getNearestEnemy();

		if(enemy != null)
		{		
			if(getDistance(enemy) > getMaxRange())
			{
				moveTo(enemy);
			}
			else
			{
				turnTo(enemy);
				turnAround();
				move();
			}
		}
	}

	public boolean isDefending()
	{
		int fleet = getAllies().size();
        return fleet < 60;
	}

	public float getId()
	{
		return (float) id;
	}

public boolean inDistress()
{
    return getPercentEffectiveHealth() < .75f;
}


	public void draw(Graphics g) 
	{

	}
}
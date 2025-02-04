package teams.student.bartlebyFanClub.units;


import components.weapon.Weapon;

import components.weapon.economy.Drillbeam;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.bartlebyFanClub.BartlebyFanClub;
import teams.student.bartlebyFanClub.MyTeamUnit;

public class Miner extends MyTeamUnit
{
	
	public Miner(BartlebyFanClub p)
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.LIGHT);
		setStyle(Style.BOXY);
		add(Drillbeam.class);
	}

	public void action() 
	{
		harvest(getNearestNode(), getWeaponOne());

		Unit e = getNearestEnemy();
		Unit a = getNearestAlly(SmartFighter.class);
		Unit h = getHomeBase();

		if(getDistance(e) < getMaxRange() * 1.2f)
		{
			if(getDistance(a) < getDistance(h))
			{
				moveTo(a);
			}
			else
			{
				moveTo(h);
			}
		}

		Node n = getNearestNode();
		if(n == null)
		{
			moveTo(a);
			if(getDistance(e) > getMaxRange() * 1.75f)
			{
				moveTo(e);
				super.action();
			}
			if(getDistance(e) < getMaxRange() * 0.95)
			{
				if(getDistance(a) < getDistance(h))
				{
					moveTo(a);
				}
				else
				{
					moveTo(h);
				}
			}
		}
	}

	public void harvest(Node n, Weapon w)
	{
		// Approach the node
		if(getDistance(n) > w.getMaxRange() * .5f && n != null)
		{
			moveTo(n);
		}
		
		// Back up if I'm close to my minimum range
		else if(getDistance(n) < w.getMinRange() * 1.5f)
		{
			turnTo(n);
			turnAround();
			move();
		}

		Unit e = getNearestEnemy();
		Unit a = getNearestAlly(SmartFighter.class);
		Unit h = getHomeBase();

		if(getDistance(e) < getMaxRange() * 1.2f)
		{
			if (getDistance(a) < getDistance(h))
			{
				moveTo(a);
			} else {
				moveTo(h);
			}
		}
				
		w.use(n);
	}
}

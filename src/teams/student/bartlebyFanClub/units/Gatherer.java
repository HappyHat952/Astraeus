package teams.student.bartlebyFanClub.units;


import components.weapon.economy.Collector;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import teams.student.bartlebyFanClub.BartlebyFanClub;
import teams.student.bartlebyFanClub.MyTeamUnit;

public class Gatherer extends MyTeamUnit
{
	public double id;
	public boolean taken = false;
	public Gatherer(BartlebyFanClub p)
	{
		super(p);
		id = Math.random();
	}
	
	public void design()
	{
		setFrame(Frame.LIGHT);
		setStyle(Style.BUBBLE);
		add(Collector.class);
	}

	public void action() 
	{
		Unit enemy = getNearestEnemy();
		Unit a = getNearestAlly(SmartFighter.class);
		Unit h = getHomeBase();

		spreadOut();

		if(getDistance(enemy) < getMaxRange() * 1.2f)
		{
			moveTo(h);
//			if(getDistance(a) < getDistance(h))
//			{
//				moveTo(a);
//			}
//			else
//			{
//				moveTo(h);
//			}
		}
		else
		{
			gatherResources();
		}

		if(isFull())
		{
			returnResources();
		}

		Resource r = getNearestResource();
		if(r == null)
		{
			moveTo(getNearestEnemy());
			super.action();
		}
	}

	public void returnResources()
	{
		if(isFull())
		{
			moveTo(getHomeBase());
			deposit();
		}
	}
	public double getID()
	{
		return id;
	}

	public void spreadOut()
	{
		float f = countGatherersInRadius(getMaxRange() * 1.35f);

		if(f > 3)
		{
			turnTo(getNearestAlly(Gatherer.class));
			turnAround();
			move();
		}
	}

	public int countGatherersInRadius(float radius)
	{
		int count = 0;
		for(int i = 0; i < getAlliesInRadius(radius).size(); i++)
		{
			if(getAlliesInRadius(radius).get(i).hasWeapon(Collector.class) && getAlliesInRadius(radius).get(i).hasCapacity())
			{
				count += 1;
			}
			else
			{
				i += 1;
			}
		}
		return count;
	}

	public void gatherResources()
	{
		if(hasCapacity())
		{
			Resource r = getNearestResource();

			if(r != null)
			{
				moveTo(r);
				((Collector) getWeaponOne()).use(r);
			}
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
	}


}

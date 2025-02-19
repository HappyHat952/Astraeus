package teams.student.bartlebyFanClub.units;

import components.weapon.Weapon;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.bartlebyFanClub.BartlebyFanClub;
import teams.student.bartlebyFanClub.MyTeamUnit;

import java.util.ArrayList;

public class GatherWarden extends MyTeamUnit 
{
	Unit parent;
	boolean hasParent = false;
	public ArrayList<Double> taken;
	public GatherWarden(BartlebyFanClub p)
	{
		super(p);
		taken = new ArrayList<Double>();
	}

	public void design()
	{
		setFrame(Frame.ASSAULT);
		setStyle(Style.WEDGE);
		add(Laser.class);
		add(Laser.class);	
	}
	
public void movement()
{
	findGatherer();
	kill();
}
public void kill()
{
	attack(getWeaponOne());
	attack(getWeaponTwo());
}
public void attack(Weapon w)
{
	Unit e = getNearestEnemy();
	if(getDistance(e) < getMaxRange())
	{
		w.use(e);
	}
}
public void findGatherer()
{
	if(!hasParent)
	{
		for(Unit g : getAlliesExcludeBaseShip())
		{
			double id;
			if(g != null && g instanceof Gatherer)
			{
				id = ((Gatherer) g).getID();
				if(single(id))
				{
					parent = g;
					taken.add(id);
					hasParent = true;
				}
			}
		}
	}
	if(parent != null)
	{
		turnTo(parent);
		move();
	}
}
public boolean single(double id)
{
	for(double a : taken)
	{
		if(a == id) return false;
	}
	return true;
}

}


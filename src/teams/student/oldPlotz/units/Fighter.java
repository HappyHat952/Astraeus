package teams.student.oldPlotz.units;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.oldPlotz.OldPlotz;
import teams.student.oldPlotz.PlotzUnit;

public class Fighter extends PlotzUnit
{
	
	public Fighter(OldPlotz p)
	{
		super(p);
	}
	
	public void design()
	{	
		setFrame(Frame.HEAVY);
		setModel(Model.CRUISER);
		setStyle(Style.ARROW);

		add(Autocannon.class);
		add(Laser.class);
		add(Shield.class);
		add(Plating.class);

	}

	@Override
	public void movement() {
		Unit enemy = getNearestEnemy();

		if(enemy != null)
		{
			if(getDistance(enemy) > getMaxRange())
			{
				moveTo(enemy);
			}
			else
			{
				turnTo(getHomeBase());
				move();
			}
		}
	}
}

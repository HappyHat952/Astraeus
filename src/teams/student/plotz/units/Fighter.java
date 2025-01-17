package teams.student.plotz.units;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.plotz.Plotz;
import teams.student.plotz.PlotzUnit;

public class Fighter extends PlotzUnit
{
	
	public Fighter(Plotz p)
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



}

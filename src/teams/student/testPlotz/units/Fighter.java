package teams.student.testPlotz.units;

import components.upgrade.Munitions;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import components.weapon.explosive.HeavyMissile;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.TestPlotzUnit;

public class Fighter extends TestPlotzUnit
{

	String weapon;
	public Fighter(TestPlotz p) {super(p); }
	

	public void design()
	{
		setFrame(Frame.HEAVY);
		setModel(Model.ARTILLERY);
		setStyle(Style.WEDGE);
		add(HeavyLaser.class);

		if (weapon != null && weapon.equals("missile"))
		{

		}
		else
		{
			//add(HeavyLaser.class);
		}
		add(Shield.class);
		add(Munitions.class);



	}



}

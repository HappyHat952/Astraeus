package teams.student.januaryPlotz.units;

import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.energy.HeavyLaser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.januaryPlotz.JanPlotzUnit;
import teams.student.januaryPlotz.JanuaryPlotz;


public class Fighter extends JanPlotzUnit
{

	String weapon;
	public Fighter(JanuaryPlotz p) {super(p); }
	

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

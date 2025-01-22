package teams.student.spaceTrees.units;

//import components.upgrade.Plating;
//import components.upgrade.Shield;

import components.weapon.explosive.Missile;
import components.weapon.kinetic.HeavyAutocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import teams.student.spaceTrees.SpaceTrees;
import teams.student.spaceTrees.SpaceTreesUnit;

public class Sniper extends SpaceTreesUnit {

	public Sniper(SpaceTrees p) {
		super(p);
	}

	public void design() {
		setFrame(Frame.MEDIUM);
		setStyle(Style.SHARK);

		add(HeavyAutocannon.class);
		add(Missile.class);

//		add(Shield.class);
//		add(Plating.class);
	}
	
}

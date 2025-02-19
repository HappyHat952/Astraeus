package teams.student.plotz.units;


import components.weapon.Weapon;
import components.weapon.economy.Drillbeam;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.plotz.Plotz;
import teams.student.plotz.PlotzUnit;

public class Miner extends PlotzUnit
{
	
	public Miner(Plotz p)
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.ARTILLERY);
		setStyle(Style.BOXY);
		add(Drillbeam.class);
	}

	public void action() 
	{
		harvest(getNearestNode(), getWeaponOne());
	}

	public void harvest(Node n, Weapon w)
	{
		// Approach the node
		if(getDistance(n) > w.getMaxRange() * .5f)
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
				
		w.use(n);
	}
	
}

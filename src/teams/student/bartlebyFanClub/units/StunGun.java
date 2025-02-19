package teams.student.bartlebyFanClub.units;

import components.upgrade.Plating;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.utility.ElectromagneticPulse;
import components.weapon.utility.Pullbeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.bartlebyFanClub.BartlebyFanClub;
import teams.student.bartlebyFanClub.MyTeamUnit;

import java.util.ArrayList;

public class StunGun extends MyTeamUnit
{

	ArrayList alliesInRadius = getAlliesInRadius(getMaxRange() * 1.2f);
	ArrayList enemiesInRadius = getEnemiesInRadius(getMaxRange() * 1.7f);


	public StunGun(BartlebyFanClub p)
	{
		super(p);
	}

	public void design()
	{
		setFrame(Frame.MEDIUM);
		setModel(Model.ARTILLERY);
		setStyle(Style.ROCKET);

		add(ElectromagneticPulse.class);
		add(Plating.class);
	}

	public void action()
	{
		Unit e = getNearestEnemy();
		Unit a = getNearestAlly(SmartFighter.class);

		if(getWeaponOne().onCooldown()) //if weapon on cooldown run away
		{
			moveTo(getHomeBase());
		}
		else if(!getWeaponOne().onCooldown()) //if weapon is not on cooldown
		{
			if(getDistance(a) >= getMaxRange()) //and ally is futher than max range
			{
				moveTo(a);

				if(e.hasWeapon(Pullbeam.class))
				{
					moveTo(e);
				}
			}
		}
		else
		{
			moveTo(getHomeBase());
		}

		if(getDistance(e) < getWeaponOne().getRadius() / 1.3f) //if closest enemy is in range
		{
			if(!e.hasWeapon(Collector.class) || !e.hasWeapon(Drillbeam.class)) //and enemy is not a collector or gatherer
			{
				getWeaponOne().use();
			}
		}

	}

}
package teams.student.testPlotz.units;

import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.energy.HeavyLaser;
import components.weapon.utility.AntiMissileSystem;
import components.weapon.utility.CommandRelay;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.geom.Point;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.TestPlotzUnit;
import teams.student.testPlotz.analysis.OverallAnalysis;

public class Fighter extends TestPlotzUnit
{

	String weapon;
	boolean hasLeft;
	public Fighter(TestPlotz p) {super(p);
	hasLeft = false;}
	

	public void design()
	{

		setModel(Model.ARTILLERY);
		setFrame(Frame.HEAVY);
		setStyle(Style.WEDGE);

		add(HeavyLaser.class);
		add(Munitions.class);
		add(Shield.class);

	}

	public void movement() {
		if (!hasLeft) {
			moveTo(getHomeBase());
			if (getAlliesInRadius(150, Fighter.class).size() >= ( (OverallAnalysis.getAlly().getMyUnits().size())) * .09f) {
				hasLeft = true;
			}
		}
		else if (getCurEffectiveHealth()/getMaxEffectiveHealth() >.2f)
		{
			Unit enemy = getNearestCriticalFighter(this, getMaxRange());

			if (enemy == null)
			{
				enemy = getNearestEnemyUnit();
			}
			if (enemy != null && enemy.getHomeBase().isDamaged())
			{
				moveTo(enemy.getHomeBase());
			}

			if (OverallAnalysis.getCurrentStage() == OverallAnalysis.FIGHT)
			{

				if(enemy != null)
				{
					if(getDistance(enemy) > getMaxRange()*.95f)
					{
						moveTo(enemy);
					}
					else
					{
						turnTo(enemy);
						turnAround();
						move();
					}
				}
			}
			else if (OverallAnalysis.getCurrentStage() == OverallAnalysis.BUILD)
			{
				Unit miner = getNearestUnit(OverallAnalysis.getAlly().getPlayer(), Miner.class);

				if(enemy != null && getDistance(enemy) < 3000)
				{
					moveTo(enemy);
				}
				else if (miner != null)
				{
					moveTo(miner);
				}


			}


		}
		else
		{
			Unit ally = getNearestAlly();
			Point rally = getNearestRallyPoint();

//			if (getDistance(rally)< getDistance(ally))
//			{
//				moveTo(rally);
//			}
//			else
//
			if(getDistance(ally) > getMaxRange())
			{
				moveTo(ally);
			}
			else
			{
				turnTo(ally);
				turnAround();
				move();
			}
			if (getDistance(ally)< getSize())
			{
				turnTo(ally);
				turnAround();
				move();
			}

		}


	}



}

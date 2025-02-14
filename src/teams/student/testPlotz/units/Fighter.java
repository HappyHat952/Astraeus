package teams.student.testPlotz.units;

import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.energy.HeavyLaser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Point;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.TestPlotzUnit;
import teams.student.testPlotz.analysis.OverallAnalysis;

public class Fighter extends TestPlotzUnit
{

	String weapon;

	public Fighter(TestPlotz p) {super(p); }
	

	public void design()
	{

		setModel(Model.ARTILLERY);
		setFrame(Frame.HEAVY);
		setStyle(Style.WEDGE);

		add( HeavyLaser.class);
		add(Munitions.class);
		add(Shield.class);

	}

	public void movement()
	{

		if (getCurEffectiveHealth()/getMaxEffectiveHealth() >.2f)
		{
			Unit enemy = getNearestCriticalFighter(this, (int)(getMaxRange()*1.2f));

			if (enemy == null)
			{
				enemy = getNearestEnemyUnit();
			}
			if (enemy != null && enemy.getHomeBase().isDamaged())
			{
				moveTo(enemy.getHomeBase());
				color = Color.orange;
				targetUnit = enemy.getHomeBase();
			}

			if (OverallAnalysis.getCurrentStage() == OverallAnalysis.FIGHT)
			{

				if(enemy != null)
				{
					if(getDistance(enemy) > getMaxRange()*.95f)
					{
						moveTo(enemy);
						color = Color.green;
						targetUnit = enemy;
					}
					else
					{
						turnTo(enemy);
						turnAround();
						move();
						color = Color.red;
						targetUnit = this;
					}
				}
			}
			else if (OverallAnalysis.getCurrentStage() == OverallAnalysis.BUILD)
			{
				Unit miner = getNearestUnit(OverallAnalysis.getAlly().getPlayer(), Miner.class);

				if(enemy != null && getDistance(enemy) < 3000)
				{
					moveTo(enemy);
					color = Color.cyan;
					targetUnit = enemy;
				}
				else if (miner != null)
				{
					moveTo(miner);
					color = Color.pink;
					targetUnit = miner;
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
				color = Color.pink;
				targetUnit = ally;
			}
			else
			{
				turnTo(ally);
				turnAround();

				color = Color.red;
				targetUnit = this;
			}
			if (getDistance(ally)< getSize())
			{
				turnTo(ally);
				turnAround();
				move();
				color = Color.red;
				targetUnit = this;
			}

		}


	}



}

package teams.student.testPlotz;

import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import player.Player;
import teams.student.testPlotz.analysis.OverallAnalysis;
import teams.student.testPlotz.units.Miner;

import java.util.ArrayList;


public abstract class TestPlotzUnit extends Unit
{
	protected float destinationX;
	protected float destinationY;
	public static Point enemyPoint;
	public static Point allyPoint;
	public boolean isHealing;
	public boolean attacking;
	public static int groupSize;
	private static float k;
	private static int spreadValue;
	private static Point rallyPoint;

	protected Unit targetUnit;
	protected Color color;


	public TestPlotzUnit(Player p)
	{
		super(p);
		destinationX = 0;
		destinationY = 0;
		attacking = false;
		isHealing = false;
		rallyPoint = getHomeBase().getPosition();
		spreadValue = 500;
		k=.1f;
	}
	
	public TestPlotz getPlayer()
	{
		return (TestPlotz) super.getPlayer();
	}

	public static void setRallyPoint(float kFactor)
	{
		k=kFactor;
	}
	public static void setSpreadValue(float sValue)
	{
		spreadValue=(int)sValue;
	}
	public static void setGroupSize(int group_size) { groupSize = group_size;}
	public static Point getCustomRallyPoint(int i){	 	return new Point(rallyPoint.getX(), rallyPoint.getY() + (float) spreadValue*i);}
	public static Point getEnemyFighter(){ 				return enemyPoint;}
	public static Point getAllyBuilder(){				return allyPoint;}


//	public static Point getRallyPoint(){
//
//		//fix this code later. This is simply where fighters retreat to. They should be proactive.
//		if (SpaceTrees.strategy.equals("tooLateToBuild"))
//		{
//			enemyPoint = EnemyAnalysis.getPlayer().getMyBase().getPosition();
//			allyPoint = AllyAnalysis.getPlayer().getMyBase().getPosition();
//		}
//		else {
//			enemyPoint = EnemyAnalysis.getAvgFighterLocation();
//			allyPoint = AllyAnalysis.getAvgMinerAndGathererLocation();
//		}
//
//		float x = (1.0f-k)*allyPoint.getX()+k*enemyPoint.getX();
//		float y = (1.0f-k)*allyPoint.getY()+k*enemyPoint.getY();
//
//		if (Math.abs(allyPoint.getY()) > 900)
//		{
//			y = allyPoint.getY()/Math.abs(allyPoint.getY()) *900;
//			allyPoint.setY(y);
//		}
//		if (allyPoint.getX() < AllyAnalysis.getPlayer().getMyBase().getX())
//		{
//			x = AllyAnalysis.getPlayer().getMyBase().getX()+ 500;
//			allyPoint.setX(x);
//		}
//
//		rallyPoint = new Point(x,y);
//
//		return rallyPoint;
//	}
//
	public void action() 
	{
		attack(getWeaponOne());
		attack(getWeaponTwo());
		movement();
	}

	public void attack(Weapon w) {

		Unit enemy = getNearestCriticalFighter(this, (int)(getMaxRange()*.9f));
		if (enemy == null) {
			enemy = getNearestEnemy();
		}
		if (enemy != null && w != null) {
			w.use(enemy);
		}
	}

	public static Unit getLowestEffectiveHealthFighter(Unit origin, int radius) {
		ArrayList<Unit> targets = origin.getEnemiesInRadius(radius);
		Unit critical = null;
		float lowestHealth = Float.MAX_VALUE;

		for (Unit u : targets) {
			//need help with this
			float health = u.getCurEffectiveHealth()*(1- u.getDodgeChance());


			if (u!=origin && health < lowestHealth && !isPassive(u) && !u.equals(origin)) {
				lowestHealth = health;
				critical = u;
			}
		}
		return critical;
	}

	public static Unit getNearestCriticalFighter(Unit origin, int radius) {
		ArrayList<Unit> targets = origin.getEnemiesInRadius(radius);
		Unit critical = null;
		//float lowestHealth = Float.MAX_VALUE;
		int maxPoints = Integer.MIN_VALUE;

		for (Unit u : targets) {
			//need help with this
			int points =0;
			points -= u.getCurEffectiveHealth()*(1- u.getDodgeChance());

			if (u.getWeaponOne()!= null)
			{
				points += u.getWeaponOne().getDamage();
			}
			if (u.getWeaponTwo()!= null)
			{
				points += u.getWeaponTwo().getDamage();
			}


			if (u!=origin && points > maxPoints && !isPassive(u) && !u.equals(origin)) {
				maxPoints = points;
				critical = u;
			}
		}
		return critical;
	}

	public static Unit getNearestFighter(Unit origin)
	{
		ArrayList<Unit> fighters= OverallAnalysis.getEnemy().getFighters();
		int minDistance = 30000;
		Unit nearFighter= null;
		for (Unit f: fighters)
		{
			if (f.getDistance(origin)< minDistance && !f.equals( origin))
			{
				nearFighter = f;
			}
		}
		return nearFighter;
	}

	public static boolean isPassive(Unit u) {
		// Returns if a unit is a gatherer or miner
		return (u.hasWeapon(Collector.class) || u.hasWeapon(Drillbeam.class));
	}
		
	public void movement()
	{

		if (getCurEffectiveHealth()/getMaxEffectiveHealth() >.2f)
		{
			Unit enemy = getLowestEffectiveHealthFighter(this, getMaxRange());

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
	
	public void draw(Graphics g)
	{
		if(targetUnit != null && color != null)
		{
			g.setColor(color);
			g.fillRect(targetUnit.getX(), targetUnit.getY(), 5,5);
		}
	}
	public Point getNearestRallyPoint()
	{
		Point nearP = OverallAnalysis.getAllRallyPoints().get(0);
		for (Point p: OverallAnalysis.getAllRallyPoints())
		{
			if (getDistance(p)< getDistance(nearP))
			{
				nearP = p;
			}
		}
		return nearP;
	}
}

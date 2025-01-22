package teams.student.spaceTrees;

import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import objects.GameObject;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import player.Player;
import teams.student.spaceTrees.analysis.AllyAnalysis;
import teams.student.spaceTrees.analysis.Analysis;
import teams.student.spaceTrees.analysis.EnemyAnalysis;

import java.util.ArrayList;

public abstract class SpaceTreesUnit extends Unit {
	
	// Declares destination x & y (for drawing purposes)
	protected float destinationX;
	protected float destinationY;
	public static Point eFighter;
	public static Point aBuilder;
	public boolean isHealing;
	public boolean attacking;
	public static int groupSize;
	private static float k;
	private static int spreadValue;
	private static Point rallyPoint;


	private static float rallyPercent; // 0 closer to gatherers, 1 closer to enemy avg.
	
	public SpaceTreesUnit(Player p) {
		super(p);
		destinationX = 0;
		destinationY = 0;
		attacking = false;
		isHealing = false;
		rallyPoint = getHomeBase().getPosition();
		rallyPercent = (float)0;
		spreadValue = 500;
		k=.1f;
	}

	// STATIC methods

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
	public static Point getEnemyFighter(){ 				return eFighter;}
	public static Point getAllyBuilder(){				return aBuilder;}
	public static Point getRallyPoint(){

		if (SpaceTrees.strategy.equals("tooLateToBuild"))
		{
			eFighter = EnemyAnalysis.getPlayer().getMyBase().getPosition();
			aBuilder = AllyAnalysis.getPlayer().getMyBase().getPosition();
		}
		else {
			eFighter = EnemyAnalysis.getAvgFighterLocation();
			aBuilder = AllyAnalysis.getAvgMinerAndGathererLocation();
		}

		float x = (1.0f-k)*aBuilder.getX()+k*eFighter.getX();
		float y = (1.0f-k)*aBuilder.getY()+k*eFighter.getY();

		if (Math.abs(aBuilder.getY()) > 900)
		{
			y = aBuilder.getY()/Math.abs(aBuilder.getY()) *900;
			aBuilder.setY(y);
		}
		if (aBuilder.getX() < AllyAnalysis.getPlayer().getMyBase().getX())
		{
			x = AllyAnalysis.getPlayer().getMyBase().getX()+ 500;
			aBuilder.setX(x);
		}

		rallyPoint = new Point(x,y);

		return rallyPoint;
	}

	public SpaceTrees getPlayer() {
		return (SpaceTrees) super.getPlayer();
	}

	public void action() {
		movement();
		attack(getWeaponOne());
		attack(getWeaponTwo());
	}

	public void attack(Weapon w) {
//		Unit enemy = getNearestEnemy();
		Unit enemy = Analysis.getNearestCriticalUnit(this, getMaxRange());
		if (enemy == null) {
			enemy = getNearestEnemy();
		}
		if (enemy != null && w != null) {
			w.use(enemy);
		}
	}


	public void setDestination (float x, float y)
	{
		destinationX = x;
		destinationY = y;

	}

	public void setDestination ( GameObject o)
	{
		destinationX = o.getX();
		destinationY = o.getY();

	}

	public void setDestination ( Point p)
	{
		destinationX = p.getX();
		destinationY = p.getY();

	}

	public void movement() {
		if (getCurEffectiveHealth()>0.85)
		{
			isHealing = false;
		}

//	if (!hasEnoughAllies()) {
//		moveTo(getHomeBase());
//	}
//
//		Unit enemy = getNearestEnemy();
//
//		if (enemy != null) {
//			if (getDistance(enemy) > getMaxRange()) {
//				moveTo(enemy);
//			} else {
//				turnTo(enemy);
//				turnAround();
//				move();
//			}
//		}
	}

	public void draw(Graphics g) {
		//g.setColor(Color.white);
		//g.drawLine(x, y, destinationX, destinationY);
	}

	// Unit methods

	public boolean hasEnoughAllies() {

			int count = 0;
			for (Unit u : getAlliesInRadius(500)) {
				if (!Analysis.isPassive(u)) {
					count++;
				}
			}
			return (count > groupSize);
	}

	public boolean enoughAlliesDamaged()
	{
		int count = 0;
		for (Unit u: getAlliesInRadius(500))
		{
			if (!Analysis.isPassive(u) && u.isDamaged())
			{
				count ++;
			}
		}
		return (count > 2);
	}
	public Unit getNearestEnemyGatherer() {
		// Nearest enemy gatherer (not used)
		// Initialize variables
		ArrayList<Unit> enemies = getEnemies();
		Unit nearestGatherer = null;
		float nearestDistance = Float.MAX_VALUE;
		// Loop through all your enemies
		for(Unit e : enemies)
		{
			// Check if it is a worker *and* compare your distance to the record
			if(e.hasWeapon(Collector.class) && getDistance(e) <= nearestDistance)
			{
				nearestGatherer = e;
				nearestDistance = getDistance(e);
			}
		}
		return nearestGatherer;
	}

	public Unit getNearestLowHealthGatherer() {
		// Nearest enemy gatherer (not used)
		// Initialize variables
		ArrayList<Unit> ally = getAllies();
		Unit nearestGatherer = null;
		float nearestDistance = Float.MAX_VALUE;
		float lowestHealth = 1;
		// Loop through all your enemies
		for(Unit e : ally)
		{
			// Check if it is a worker *and* compare your distance to the record
			if(e.hasWeapon(Collector.class) && getDistance(e) <= nearestDistance && e.getPercentEffectiveHealth()<=lowestHealth)
			{
				nearestGatherer = e;
				nearestDistance = getDistance(e);
			}
		}
		return nearestGatherer;
	}

	public Unit getNearestFighter() {
		// Gets nearest fighter
		ArrayList<Unit> enemies = getEnemies();
		Unit nearestFighter = null;
		float nearestDistance = Float.MAX_VALUE;
		// Loop through all your enemies
		for(Unit e : enemies)
		{
			// Check if it is a worker *and* compare your distance to the record
			if(!e.hasWeapon(Collector.class) && !e.hasWeapon(Drillbeam.class) && getDistance(e) <= nearestDistance)
			{
				nearestFighter = e;
				nearestDistance = getDistance(e);
			}
		}
		return nearestFighter;
	}

	@Override
	public int getPullTimer() {
		return super.getPullTimer();
	}

	public boolean isHealing()
	{
		return isHealing;
	}

	public void setHealing()
	{
		isHealing = true;
	}
}

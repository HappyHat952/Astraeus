package teams.student.testPlotz.units;


import components.weapon.Weapon;
import components.weapon.economy.Drillbeam;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.geom.Point;
import teams.student.testPlotz.analysis.ResourceManager;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.TestPlotzUnit;

import java.util.ArrayList;

public class Miner extends TestPlotzUnit
{

	Node target;

	public Miner(TestPlotz p)
	{
		super(p);
		target = getNearestNode();
	}

	public void design()
	{
		setFrame(Frame.LIGHT);
		setStyle(Style.BOXY);
		setModel(Model.ARTILLERY);
		add(Drillbeam.class);
	}


	public void action() {

		if (!(getPlayer().getAllNodes().isEmpty()))
		{
			target = getSafeNearestNode();
			if (getPercentEffectiveHealth()<0.6) {
				moveTo(getHomeBase());
			}
			else if (getPercentEffectiveHealth()<0.9)
			{
				attack(getNearestEnemyUnit(), getWeaponOne());
			}
			else {
				harvest(target, getWeaponOne());
			}
		}
		else {
			finalAttack();
		}

	}

	public void harvest(Node n, Weapon w)
	{
		// Approach the node
		if(getDistance(n) > w.getMaxRange() * .4f)
		{
			destinationX = n.getCenterX();
			destinationY = n.getCenterY();
			moveTo(n);
		}

		// Back up if I'm close to my minimum range
		else if(getDistance(n) < w.getMinRange() * 1.5f)
		{
			turnTo(n);
			turn( (float)Math.random()*360);
			move();
		}

		w.use(n);
	}


	public void attack(Unit enemy, Weapon w)
	{
		if (enemy != null) {
			if (getDistance(enemy) <= w.getMaxRange() * 0.4f) {
				moveTo(enemy);
				destinationX = enemy.getCenterX();
				destinationY = enemy.getCenterY();
			} else if (getDistance(enemy) >= w.getMinRange() * 1.5f) {
				turnTo(enemy);
				turn(180);
				move();
			} else {
				turnTo(enemy);
				move();
			}
			w.use(enemy);
		}
	}

	private void finalAttack() {
		Unit enemy = getNearestEnemy();
		Unit tank;
		//Unit tank = getNearestUnit(getPlayer(), Tank.class);
		if (enemy != null) {
//			if (tank != null) {
//				// If a tank exists & is close, go to an enemy; if its far away go to the tank
//				if (getDistance(tank) < 300) {
//					moveTo(enemy);
//					destinationX = enemy.getCenterX();
//					destinationY = enemy.getCenterY();
//				} else {
//					moveTo(tank);
//					destinationX = tank.getCenterX();
//					destinationY = tank.getCenterY();
//				}
//			}
		 if (getDistance(enemy) > getMaxRange()) {
				// If there's no tank, just do standard movement
				turnTo(enemy);
				move();
				destinationX = enemy.getCenterX();
				destinationY = enemy.getCenterY();
			} else {
				turnTo(enemy);
				turnAround();
				move();
				destinationX = getHomeBase().getCenterX();
				destinationY = getHomeBase().getCenterY();
			}

		}
	}

	public void setTarget(Node n)
	{
		target = n;
	}

	public Node getSafeNearestNode(){
		float nearestDistance = Float.MAX_VALUE;
		Node nearestNode = null;
		ArrayList<Node> nodes =  ResourceManager.getSafeNodes();

		if (nodes == null) {
			return getNearestNode();
		}
		for(Node n : nodes)
		{
			if(n.isInBounds() && getDistance(n.getPosition()) < nearestDistance)
			{
				nearestNode = n;
				nearestDistance = getDistance(n.getPosition());
			}
		}

		return nearestNode;
	}

	public Point getDestination() {
		if (target != null) {
			return target.getPosition();
		} else {
			return getHomeBase().getPosition();
		}
	}
}

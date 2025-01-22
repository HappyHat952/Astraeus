package teams.student.spaceTrees.units;


import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.energy.HeavyLaser;
import components.weapon.explosive.HeavyMissile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.spaceTrees.SpaceTrees;
import teams.student.spaceTrees.SpaceTreesUnit;
import teams.student.spaceTrees.analysis.AllyAnalysis;
import teams.student.spaceTrees.analysis.EnemyAnalysis;


public class Fighter extends SpaceTreesUnit {

	String weapon;
	int cluster;
	public Fighter(SpaceTrees p) {
		super(p);
		cluster = (int)(Math.random()*3)-1;
	}
	public Fighter(SpaceTrees p, String weapon) {


		super(p);
		this.weapon = weapon;
		cluster = (int)(Math.random()*3)-1;
	}


	public void design()
	{
		setFrame(Frame.HEAVY);
		setModel(Model.ARTILLERY);
		setStyle(Style.WEDGE);

		if (weapon != null && weapon.equals("missile"))
		{
			add(HeavyMissile.class);
		}
		else
		{
			add(HeavyLaser.class);
		}
		add(Shield.class);
		add(Munitions.class);



	}


	public void action()
	{
		movement();
		if (getWeaponOne().canUse())
		{
			attack(getWeaponOne());
		}
// You can modify this method to have different behavior
	}


	public void movement() {

		if (getPlayer().getMyBase().isDamaged())
		{
			moveTo(getCustomRallyPoint(cluster));
		}
		else if (getOpponent().getMyBase().isDamaged() && hasEnoughAllies())
		{
			moveTo(getOpponent().getMyBase());
		}
		else if (!hasEnoughAllies()) {
//			if (getPlayer().strategy.equals("build") && (getNearestAlly(Tank.class)!= null))
//			{
//				moveTo(getNearestAlly(Tank.class));
//			}

			if (getPlayer().strategy.equals("build"))
			{
				moveTo(getCustomRallyPoint(cluster));
				System.out.println("CLUSTER: "+cluster +getCustomRallyPoint(cluster).getX()+", "+getCustomRallyPoint(cluster).getY());
			}
			else
			{
				moveTo(getRallyPoint());
			}
			if (getDistance(getNearestEnemy())<getMaxRange()* 1.25)
			{
				basicFightMovement();
			}

		}
		if (enoughAlliesDamaged())
		{
			basicFightMovement();
		}
		else {
			basicFightMovement();
			// Looks for nearest tank
		}

	}

	public void basicFightMovement()
	{
		Unit tank = getNearestUnit(getPlayer(), Tank.class);
		Unit ally = getNearestUnit(getPlayer(), Fighter.class);
		Unit enemy = getNearestEnemy();

		if (getPlayer().strategy.equals("tooLateToBuild") || (getPlayer().strategy.equals("general strategy") && EnemyAnalysis.getFighterSize()> AllyAnalysis.getFighterSize()))
		{
			enemy = getNearestUnitExclude(getOpponent(), Gatherer.class);
		}

		if (enemy != null) {
			if (tank != null && ally != null) {
				// If a tank exists & is close, go to an enemy; if its far away go to the tank
				if (getDistance(tank) < 300) {
					moveTo(enemy.getX()-getWeaponOne().getMaxRange(), enemy.getY());
					destinationX = enemy.getCenterX();
					destinationY = enemy.getCenterY();
				} else if (getDistance(tank)<800){
					moveTo(tank);
					destinationX = tank.getCenterX();
					destinationY = tank.getCenterY();
				} else if (getDistance(ally)<800)
				{
					moveTo(ally);
				}
//					else if (getDistance(ally)< 300)
//					{
//						moveTo(ally);
//						destinationX = ally.getCenterX();
//						destinationY = ally.getCenterY();
//					}
				else {
					moveTo(getCustomRallyPoint(cluster));
				}
				// DON'T BE TOO CLOSE TO OTHERS
			} else if (getDistance(enemy) > getMaxRange()){
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
		else
		{
			moveTo(getCustomRallyPoint(cluster));
		}
	}

}


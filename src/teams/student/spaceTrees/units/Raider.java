package teams.student.spaceTrees.units;

import components.upgrade.Shield;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.explosive.Missile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.geom.Point;
import teams.student.spaceTrees.SpaceTrees;
import teams.student.spaceTrees.SpaceTreesUnit;

import java.util.ArrayList;


public class Raider extends SpaceTreesUnit {

    private int offset;

    public Point back;
    public Raider(SpaceTrees p)
    {
        super(p);
    }

    public void design()
    {
        setFrame(Frame.LIGHT);
        setModel(Model.STRIKER);
        setStyle(Style.CANNON);
        add(Shield.class);
        add(Missile.class);
        back = new Point(4000f, getEnemyBase().getCenterY());
        if (Math.random() <= 0.5)
        {
            offset = 1;
        }
        else
        {
            offset = -1;
        }
    }

    public void action()
    {
        attack();
        movement();
    }

    public void attack()
    {
//        Unit nearAllEnmy = getMyNearestEnemy();// closest of all enemy ships
//        Unit nearResEnmy = getBestWorker(); // closest of all resource-collecting ships
//
//        //if a nearest enemy exists and its distance is less than our max range, avoid by switching sides.
//        if (nearAllEnmy != null && getDistance(nearAllEnmy) < nearAllEnmy.getMaxRange() + 100) {
//
//            if (y > nearAllEnmy.getY())
//            {
//                offset = 1;
//            }
//            else
//            {
//                offset = -1;
//            }
//
//            moveTo(x-3000, 3000*offset);
//            setDestination(x-3000, 3000*offset);
//        }

        // if a resource enemy doesn't exist, go to the home base-- THIS IS WHY IT CIRCLES @ end
//        else if (nearResEnmy == null && nearAllEnmy != null)
//        {
//            moveTo(nearAllEnmy);
//            setDestination(nearAllEnmy);
//
//            if (getDistance(nearAllEnmy)< getMaxRange()-50)
//            {
//                getWeaponOne().use(nearAllEnmy);
//            }
//        }
//
//        //if a resource enemy is further than max range, go to it.
//        else if(getDistance(nearResEnmy) > getMaxRange())
//        {
//            moveTo(nearResEnmy);
//            setDestination(nearResEnmy);
//
//            if (getDistance(nearResEnmy) - getMaxRange() < 50) {
//                getWeaponOne().use(nearResEnmy);
//            }
//        }
//
//        //attack nearest Res Enemy
//        else
//        {
//            getWeaponOne().use(nearResEnmy);
//        }
    }

    public void movement()
    {
        if(getTimeAlive() < 25 * 60)
        {
            moveTo(getEnemyBase().getX(), 2500*offset);
            setDestination(getEnemyBase().getX(), 2500*offset);
        }
    }

    public Unit getNearestEnemyMinerOrGatherer()
    {
        float nearestDistance = Float.MAX_VALUE;
        Unit nearestUnit = null;
        ArrayList<Unit> units =  getEnemiesExcludeBaseShip();

        for(Unit u : units)
        {
            if(this != u && (u.hasWeapon(Drillbeam.class) || u.hasWeapon(Collector.class)) && getDistance(u) < nearestDistance)
            {
                nearestUnit = u;
                nearestDistance = getDistance(u);

            }
        }

        return nearestUnit;
    }

    public Unit getMyNearestEnemy()
    {
        float nearestDistance = Float.MAX_VALUE;
        Unit nearestUnit = null;
        ArrayList<Unit> units =  getEnemies();

        for(Unit u : units)
        {
            if(this != u && (!u.hasWeapon(Drillbeam.class) && !u.hasWeapon(Collector.class)) && getDistance(u) < nearestDistance)
            {
                nearestUnit = u;
                nearestDistance = getDistance(u);

            }
        }

        return nearestUnit;
    }

//    public Unit getBestWorker() {
////        float furthestDistance = Float.MIN_VALUE;
////        Unit bestUnit = null;
////        ArrayList<Unit> units = getEnemiesExcludeBaseShip();
////
////        for(Unit u : units) {
////            ArrayList<Unit> enemies = u.getAlliesInRadius(400);
////            if (enemies != null) {
////
////                if ((u.hasWeapon(Drillbeam.class) || u.hasWeapon(Collector.class)) && u.getDistance() > furthestDistance) {
////                    bestUnit = u;
////                    furthestDistance = getDistance(u);
////
////                }
////            }
////        }
//
////        return bestUnit;
//    }

//    public Unit findNearestFighter() {
//
//    }

//    public void draw(Graphics g) {
//        g.setColor(Color.pink);
//        g.fillOval(getBestWorker().getCenterX(), getBestWorker().getCenterY(), 200, 200);
//        g.setColor(Color.red);
//        g.fillOval(back.getCenterX(), back.getCenterY(), 200, 200);
//    }
}
package teams.student.testPlotz.units;

import components.upgrade.Shield;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.energy.Laser;
import components.weapon.utility.SpeedBoost;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.TestPlotzUnit;

import java.util.ArrayList;

public class Distractor extends TestPlotzUnit {
    private int offset;
    protected  Unit nearAllEnmy;
    protected  Unit nearResEnmy;
    public Distractor(TestPlotz p) {
        super(p);
        if (Math.random() <= 0.5)
        {
            offset = 1;
        }
        else
        {
            offset = -1;
        }
        nearAllEnmy = getMyNearestEnemy(); // closest of all enemy ships
        nearResEnmy = getSafestGatherer();
    }

    @Override
    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.STRIKER);
        setStyle(Style.SHARK);

        add(Laser.class);
        add(SpeedBoost.class);
        add(Shield.class);
    }

    @Override
    public void action()
    {
        if (getTimeAlive() < 15 * 60) {
            nearResEnmy = getSafestGatherer(); // closest of all resource-collecting ships
        }

//        if (nearResEnmy == null || !nearResEnmy.isAlive()) { // ADD SAFETY RATING
            nearResEnmy = getSafestGatherer(); // so it doesnt reset until its dead
//        }
        nearAllEnmy = getMyNearestEnemy(); // closest of all enemy ships


        // If its just created, go to rally point ->2500 pixels n or s of homebase
        if(getTimeAlive() < 15 * 60)
        {
            moveTo(getHomeBase().getX(), 2500);
            getWeaponTwo().use();
//            setDestination(getHomeBase().getX(), 2500);
        }

        if (!this.isInBounds()) {
            moveTo(getEnemyBase());
        }

        //if a nearest enemy exists and its distance is less than our max range, avoid by switching sides.
        if (nearAllEnmy != null && getDistance(nearAllEnmy) < nearAllEnmy.getMaxRange() + 100) {

            turnTo(nearResEnmy);
            getWeaponTwo().use();
            move();

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
////            setDestination(x-3000, 3000*offset);
//            getWeaponTwo().use();
        } else if (getDistance(getEnemyBase()) < 300) {
            turnTo(getEnemyBase());
            turnAround();
        }

        // if a resource enemy doesn't exist, go to the home base-- THIS IS WHY IT CIRCLES @ end
//        else if (nearResEnmy == null && nearAllEnmy != null)
//        {
//            moveTo(nearAllEnmy);
////            setDestination(nearAllEnmy);
//
//            if (getDistance(nearAllEnmy)< getMaxRange()-50)
//            {
//                getWeaponOne().use(nearAllEnmy);
//            }
//        }

        //if a resource enemy is further than max range, go to it.
        else if(getDistance(nearResEnmy) > getMaxRange())
        {
            moveTo(nearResEnmy);
//            setDestination(nearResEnmy);

            if (getDistance(nearResEnmy) - getMaxRange() < 50) {
                getWeaponOne().use(nearResEnmy);
            }
        }

        //attack nearest Res Enemy
        else
        {
            getWeaponOne().use(nearResEnmy);
        }
    }

    public Unit getSafestGatherer()
    {
        float bestScore = Float.MAX_VALUE;
        Unit bestTarget = null;
        ArrayList<Unit> gatherers = getEnemiesExcludeBaseShip();

        for (Unit u : gatherers) {
            if (u.hasWeapon(Collector.class)) {
                float distance = getDistance(u);
                float danger = 0;

                for (Unit enemy : getEnemiesIncludeBaseShip()) {
                    if (!enemy.hasWeapon(Collector.class) && !enemy.hasWeapon(Drillbeam.class)) {
                        float d = enemy.getDistance(u);
                        if (d < 1500) {
                            danger += (1500 - d) / 2;
                        }
                    }
                }

                float score = distance + danger;
                if (score < bestScore) {
                    bestScore = score;
                    bestTarget = u;
                }
            }
        }
        return bestTarget;
    }

    public Unit getMyNearestEnemy()
    {
        float nearestDistance = Float.MAX_VALUE;
        Unit nearestUnit = null;
        ArrayList<Unit> units =  getEnemiesIncludeBaseShip();

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

    public final ArrayList<Unit> getEnemiesIncludeBaseShip()
    {
        ArrayList<Unit> enemyUnits = new ArrayList<Unit>();
        ArrayList<Unit> enemies = getEnemies();

        for(Unit u : enemies)
        {
            enemyUnits.add(u);
        }

        return enemyUnits;
    }
}

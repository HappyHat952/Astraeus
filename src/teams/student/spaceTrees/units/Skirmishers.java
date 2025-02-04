package teams.student.spaceTrees.units;

import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.kinetic.Autocannon;
import components.weapon.utility.SpeedBoost;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.spaceTrees.SpaceTrees;
import teams.student.spaceTrees.SpaceTreesUnit;

import java.util.ArrayList;

public class Skirmishers extends SpaceTreesUnit
{
    // Not sure how all this code works, Aishwaryaa has no comments
    private int offset;

    public Skirmishers(SpaceTrees p)
    {
        super(p);
        // Determines if it will go up or down at the start
        if (Math.random() <= 0.5)
        {
            offset = 1;
        }
        else
        {
            offset = -1;
        }
    }

    public void design()
    {
        setFrame(Frame.LIGHT);
        setStyle(Style.ARROW);
        add(Autocannon.class);
        add(SpeedBoost.class);
    }

    public void action()
    {
        Unit nearAllEnmy = getMyNearestEnemy(); // closest of all enemy ships
        Unit nearResEnmy = getNearestEnemyMinerOrGatherer(); // closest of all resource-collecting ships

        // If its just created, go to rally point ->2500 pixels n or s of homebase
        if(getTimeAlive() < 15 * 60)
        {
            moveTo(getHomeBase().getX(), 2500*offset);
            setDestination(getHomeBase().getX(), 2500*offset);
        }

        //if a nearest enemy exists and its distance is less than our max range, avoid by switching sides.
        if (nearAllEnmy != null && getDistance(nearAllEnmy) < nearAllEnmy.getMaxRange() + 100) {

            if (y > nearAllEnmy.getY())
            {
                offset = 1;
            }
            else
            {
                offset = -1;
            }

            moveTo(x-3000, 3000*offset);
            setDestination(x-3000, 3000*offset);
            getWeaponTwo().use();
        }

        // if a resource enemy doesn't exist, go to the home base-- THIS IS WHY IT CIRCLES @ end
        else if (nearResEnmy == null && nearAllEnmy != null)
        {
            moveTo(nearAllEnmy);
            setDestination(nearAllEnmy);

            if (getDistance(nearAllEnmy)< getMaxRange()-50)
            {
                getWeaponOne().use(nearAllEnmy);
            }
        }

        //if a resource enemy is further than max range, go to it.
        else if(getDistance(nearResEnmy) > getMaxRange())
        {
            moveTo(nearResEnmy);
            setDestination(nearResEnmy);

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

package teams.student.bartlebyFanClub.units;

import components.upgrade.HeavyPlating;
import components.weapon.kinetic.Autocannon;
import components.weapon.kinetic.HeavyAutocannon;
import components.weapon.utility.Pullbeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.bartlebyFanClub.BartlebyFanClub;
import teams.student.bartlebyFanClub.MyTeamUnit;

public class PeeWee extends MyTeamUnit
{

    public PeeWee(BartlebyFanClub p)
    {
        super(p);
    }

    public void design()
    {
        setFrame(Frame.ASSAULT);
        setModel(Model.ARTILLERY);
        setStyle(Style.PINCER);
        add(Autocannon.class);
        add(HeavyAutocannon.class);
        add(HeavyPlating.class);
    }

    public void movement()
    {
        Unit e = getNearestEnemy();
        Unit a = getNearestAlly();
        Unit pullbeamEnemy = getPullbeamEnemy();
        //Unit f = getNearestAlly(Tank.class);

        dbgMessage(getDistance(pullbeamEnemy));

        if(e != null)
        {
            if(getDistance(pullbeamEnemy) > getMaxRange() * 3f)
            {
                moveTo(pullbeamEnemy);
            }
//           else if(getDistance(f) > getMaxRange())
//            {
//                moveTo(f);
//            }
            else if(getDistance(e) < getMinRange() * 1.2)
            {
                turnTo(e);
                turnAround();
                move();
            }
            else if(getDistance(getEnemyBase()) < getEnemyBase().getMaxRange())
            {
                moveTo(getHomeBase());
            }
            else
            {
                moveTo(e);
            }
        }


    }

    public Unit getPullbeamEnemy()
    {
        for(Unit u : getEnemiesExcludeBaseShip())
        {
            if(u != this && u.hasWeapon(Pullbeam.class))
            {
                return u;
            }
        }

        return null;
    }

    public Unit getNearestPullbeamEnemyInRange()
    {
        boolean first = true;
        float dis = 0;
        Unit a = null;
        for(Unit u : getEnemiesInRadius(getMaxRange() * 1.5f))
        {
            if(first)
            {
                if(hasPullbeam(u))
                {
                    dis = getDistance(u);
                    first = false;
                    a = u;
                }
            }
            else if(hasPullbeam(u))
            {
                if (dis < getDistance(u))
                {
                    dis = getDistance(u);
                    a = u;
                }
            }
        }
        return a;
    }

    public boolean hasPullbeam(Unit p)
    {
        return p.hasWeapon(Pullbeam.class);
    }

}

package teams.student.plotz.units;

import components.upgrade.HeavyPlating;
import components.upgrade.Plating;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.plotz.Plotz;
import teams.student.plotz.PlotzUnit;
import teams.student.plotz.analysis.OverallAnalysis;

import java.util.ArrayList;

import static teams.student.plotz.analysis.OverallAnalysis.FIGHT;

public class Tank extends PlotzUnit {

    public Tank(Plotz p) {

        super(p);

    }

    public void design() {
        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.ORB);

        add(Autocannon.class);
        add(Plating.class);
        add(HeavyPlating.class);
    }

    public void action()

    {
        movement();
        // You can modify this method to have different behavior
        if (getWeaponOne().canUse())
        {
            attack(getWeaponOne());
        }



//        else if (getWeaponTwo().canUse())
//        {
//            attack(getWeaponTwo());
//        }

    }


    public void movement() {
//        if (getHomeBase().isDamaged())
//        {
//            moveTo(getRallyPoint());
//        }
//        if (getOpponent().getMyBase().isDamaged() && hasEnoughAllies())
//        {
//            moveTo(getCustomRallyPoint(cluster));
//        }
//        else if (!hasEnoughAllies()) {
//            moveTo(getCustomRallyPoint(cluster));
//        }
//        else
//        {
        //moveTo(getRallyPoint());
        if (OverallAnalysis.getCurrentStage() == FIGHT)
        {
            Unit enemy = getNearestCriticalFighter(this, 10000);
//            if(getDistance(enemy) > 100)
//            {
//                moveTo(enemy);
//            }
//            else
//            {
//                turnTo(enemy);
//                turnAround();
//                move();
//            }

            if (enemy!= null)
            {

                moveTo(enemy);
            }
            else {
                moveTo(getHomeBase());
            }

        }
        else if (OverallAnalysis.getCurrentStage() == OverallAnalysis.BUILD)
        {
            Unit base = getHomeBase();
            Unit enemy = getNearestEnemy();

            if(enemy != null && getDistance(enemy) < 3000)
            {
                moveTo(enemy);
            }
            else if (base != null)
            {
                moveTo(base);
            }


        }




//            // If the weapon is on cooldown
//            if(getWeaponOne().onCooldown())
//            {
//                turnTo(enemy);
//                turnAround();
//                move();
//
//            }
//            else
//            {
//                moveTo(enemy);
//            }

    }

    public Unit getNearestEnemyFighter()
    {
        ArrayList<Unit> allEnemy = getEnemies();
        if (allEnemy.size()>0)
        {
            Unit closest = null;
            float smallDist = Float.MAX_VALUE;

            for (Unit e: allEnemy)
            {
                if (!isPassive(e) && getDistance(e)< smallDist)
                {
                    closest = e;
                    smallDist = getDistance(e);
                }
            }
            return closest;
        }
        return null;

    }

}

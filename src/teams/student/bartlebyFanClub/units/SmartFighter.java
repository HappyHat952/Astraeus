package teams.student.bartlebyFanClub.units;

import components.mod.Mod;
import components.mod.offense.AresMod;
import components.upgrade.HeavyShield;
import components.upgrade.Munitions;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import components.weapon.kinetic.HeavyAutocannon;
import components.weapon.utility.ElectromagneticPulse;
import components.weapon.utility.HealingWeapon;
import components.weapon.utility.Pullbeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.bartlebyFanClub.BartlebyFanClub;
import teams.student.bartlebyFanClub.MyTeamUnit;

import java.util.ArrayList;

public class SmartFighter extends MyTeamUnit
{
    public SmartFighter(BartlebyFanClub p)
    {
        super(p);
        defending = true;
    }

    public void design()
    {
        setFrame(Frame.HEAVY);
        setModel(Model.ARTILLERY);
        setStyle(Style.CANNON);

        add(HeavyAutocannon.class);
        add(Shield.class);
        //add(Plating.class);
        add(Munitions.class);
    }

    public void movement()
    {
        Unit a = getNearestAlly();
        Unit e = getNearestEnemy();
        //Unit t = getNearestAlly(Tank.class);

        float h = getHomeBase().getDistance(getEnemyBase()) /2;

        dbgMessage(getDistance(getHomeBase()));

     if(this.isDefending())
     {
         if(getHomeBase().getDistance(e) < h) //enemy in base territory
         {
             if(getDistance(e) < getMaxRange() * 1.2f || getWeaponOne().onCooldown())
             {
                 moveTo(getHomeBase());
             }
             else
             {
                 moveTo(e);
             }
         }
         else
         {
             moveTo(getHomeBase());
         }
     }
     else
     {
         //moveTo(t);

//         if(e != null && t != null)
//         {
//             if(getDistance(e) < getDistance(t) || getDistance(t) > getMaxRange())
//             {
//                 moveTo(t);
//             }
//             else
//             {
//                 spreadOut();
//             }
//             if(getDistance(e) > getMaxRange())
//             {
//                 moveTo(e);
//             }
//             else
//             {
//                 spreadOut();
//             }
//         }
     }

    }

//    public void goBehindTank()
//    {
//        Unit e = getNearestEnemy();
//        Unit t = getNearestAlly(Tank.class);
//        float ed = getDistance(e);
//
//        float ea = getAngleToward(e.getX(), e.getY());
//
//        turnTo(ea);
//        if(getDistance(e) > getDistance(t) && )
//
//    }

    public void spreadOut()
    {
        float f = countFightersInRadius(getMaxRange() * 0.8f);

        if(f > 4)
        {
            turnTo(getNearestAlly(SmartFighter.class));
            turnAround();
            move();
        }
    }

    public int countFightersInRadius(float radius)
    {
        int count = 0;
        for(int i = 0; i < getAlliesInRadius(radius).size(); i++)
        {
            if(getAlliesInRadius(radius).get(i).hasWeapon(ElectromagneticPulse.class) || getAlliesInRadius(radius).get(i).hasWeapon(Collector.class) || getAlliesInRadius(radius).get(i).hasWeapon(Drillbeam.class) || getAlliesInRadius(radius).get(i).hasWeapon(HealingWeapon.class))
            {
                i += 1;
            }
            else
            {
                count += 1;
            }
        }
        return count;
    }


    public float distanceToHomebase()
    {
        return getDistance(getHomeBase());
    }

    public boolean defending()
    {
        //change to allow for a switch between defending and attacking 3/4
        return distanceToHomebase() < getDistance(getEnemyBase()) / 2;
    }

    public Unit getDamsel()
    {
        for(Unit u : getAllies())
        {
            if(u != this && u.hasWeapon(Collector.class) || u.hasWeapon(Drillbeam.class))
            {
                return u;
            }
        }

        return null;
    }

}


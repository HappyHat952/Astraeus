package teams.student.spaceTrees.units;


import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.utility.RepairBeam;
import engine.states.Game;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.geom.Point;
import player.Player;
import teams.student.spaceTrees.ManageHealer;
import teams.student.spaceTrees.SpaceTrees;
import teams.student.spaceTrees.SpaceTreesUnit;
import teams.student.spaceTrees.analysis.Analysis;

import java.util.ArrayList;


public class Healer extends SpaceTreesUnit {

    Unit weak = null;
    ManageHealer manager;
    private int countX, countY;
    public int count=0;

    public Healer(SpaceTrees p, ManageHealer manager)
    {
        super(p);
        this.manager = manager; // manager class manages healers
    }

    public void setWeakUnit(Unit weak) {
        this.weak = weak; // setting a weak unit for this healer to heal
    }

    public void design()
    {
        setFrame(Frame.LIGHT);
        setModel(Model.BASTION);
        setStyle(Style.DAGGER);
        add(RepairBeam.class);
        add(RepairBeam.class);


    }

    public void onBuild()
    {
        manager.addHealer(this);
    }

    public void action() {
        Unit e = getMyNearestEnemy();


//        if (e != null && getDistance(e) < e.getMaxRange() + 100)
//        {
//            moveFromEnemy(e);
//        }
        if(weak == null && !hasEnoughAllies())
        {
            moveTo(getHomeBase());

            //moveTo((getEnemyBase().getX() - getHomeBase().getX())/2, getHomeBase().getY()); // if no weak unit then go an wait at some location
        }
        else if (weak == null && getNearestAlly(Tank.class) != null)
        {
            moveTo(getNearestAlly(Tank.class));
        }
        else if (weak == null && getNearestAlly(Fighter.class) != null)
        {
            moveTo(getNearestAlly(Fighter.class));
        }
        else if(getDistance(weak) > getWeaponOne().getMaxRange()) // move within range of the weak unit
        {
            moveTo(weak);
        }
        else if (!weak.isDead() && getDistance(weak)> getWeaponOne().getMinRange())
        {
            moveTo(weak);
            getWeaponOne().use(weak);
            getWeaponTwo().use(weak);

        }
        else
        {
            getWeaponOne().use(weak);
            getWeaponTwo().use(weak);


            if(weak.isDead() || weak.getPercentEffectiveHealth() == 1) // if weak unit is fully healed or dead, set the weak unit to null. the manager will assign another weak unit to heal
            {
                weak = null;
            }
        }

    }

    public boolean isBusy() { // if this healer is currently repairing a weak unit then it is busy
        if(weak == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    private void moveFromEnemy(Unit e)
    {
        float newX, newY;
        if(getX() >= e.getX())
        {
            newX = getX() + 500f;
            if (newX >= Game.getMapRightEdge())
            {
                newX = Game.getMapRightEdge() - 20;
                countX++;
            }
            else
            {
                countX = 0;
            }
        }
        else
        {
            newX = getX() - 500f;
            if (newX <= Game.getMapLeftEdge())
            {
                newX =  Game.getMapLeftEdge() + 20;
                countX++;
            }
            else
            {
                countX = 0;
            }
        }


        if(getY() >= e.getY())
        {
            newY = getY() + 500f;
            if (newY >= Game.getMapBottomEdge())
            {
                newY = Game.getMapBottomEdge() - 20;
                countY++;
            }
            else
            {
                countY = 0;
            }
        }
        else
        {
            newY = getY() - 500f;
            if (newY <= Game.getMapTopEdge())
            {
                newY = Game.getMapTopEdge() + 20;
                countY++;
            }
            else
            {
                countY = 0;
            }
        }
        if(countX >= 5 || countY >= 5)
        {
            moveTo(getHomeBase());
        }
        else
        {
            moveTo(newX, newY);
        }
    }

    private Unit getMyNearestEnemy()
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

    public void action1()
    {
        moveTo(getAveragePlayerFighters(getPlayer()));
//        moveTo((getWeakestPlayer(getPlayer(), 9999999)));
        Weapon w = getWeaponOne();




        if (getDistance(getAveragePlayerFighters(getPlayer()))<450)
        {
            Unit weak = getWeakestPlayer(getPlayer(),450);


            if (getDistance(weak)<= w.getMaxRange()*0.4f)
            {
                moveTo(weak);
            }


            w.use(weak);
        }


    }


    public Point getAveragePlayerFighters(Player p)
    {


        float x = 0;
        float y = 0;
        float count = 0;


        for (Unit u : p.getAllUnits())
        {
            if(Analysis.isPassive(u))
            {
                x+=u.getX();
                y+=u.getY();
                count ++;
            }


        }


        x /= count;
        y /= count;
        x = destinationX;
        y = destinationY;

        return new Point(x,y);
    }


    public Unit getWeakestPlayer(Player p, int range)
    {
        float lowestHealth = 1;
        Unit weak = null;


        for (Unit u: p.getAllUnits())
        {
            if (getDistance(u.getPosition())<range && u.getPercentEffectiveHealth()<lowestHealth)
            {
                weak = u;
            }
        }


        return weak;
    }

    public Unit getWeakestPlayer()
    {
        float lowestHealth = 1;
        Unit weak = null;
        for (Unit u: getAllies())
        {
            if (u.getPercentEffectiveHealth() < lowestHealth)
            {
                weak = u;
            }
        }
        return weak;
    }
}
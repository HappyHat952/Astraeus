package teams.student.plotz.analysis;

import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.explosive.Missile;
import components.weapon.utility.Pullbeam;
import objects.entity.unit.Unit;
import org.newdawn.slick.geom.Point;
import player.Player;
import teams.student.plotz.units.Commander;

import java.util.ArrayList;

public class PlayerAnalysis {

    private Player player;

    private int timer;

    private boolean hasRelay;


    private ArrayList<Unit> myUnits = new ArrayList<>();
    private ArrayList<Unit> myFighterUnits = new ArrayList<>();
    private ArrayList<Unit> myPullerUnits = new ArrayList<>();
    private ArrayList<Unit> myGathererUnits = new ArrayList<>();
    private ArrayList<Unit> myMinerUnits = new ArrayList<>();

    private Point unitAverage;
    private Point fighterAverage;
    private Point pullerAverage;
    private Point gatherAverage;
    private Point minerAverage;

    public Player getPlayer(){ return player;}
    public int getValue(){ return player.getFleetValueUnit();}

    public boolean hasRelay(){ return hasRelay;}

    public ArrayList<Unit> getMyUnits(){ return myUnits;}
    public ArrayList<Unit> getFighters(){ return myFighterUnits;}
    public ArrayList<Unit> getMyPullerUnits(){ return myPullerUnits;}
    public ArrayList<Unit> getMyGathererUnts(){ return myGathererUnits;}
    public ArrayList<Unit> getMyMinerUnits(){ return myMinerUnits;}

    public int getNumUnits(){ return myUnits.size();}
    public int getNumFighterUnits(){ return myFighterUnits.size();}
    public int getNumPullerUnits(){ return myPullerUnits.size();}
    public int getNumGathererUnits() { return myGathererUnits.size();}
    public int getNumMinerUnits() { return myMinerUnits.size();}

    public Point getUnitAveragePoint(){ return unitAverage;}
    public Point getFighterAveragePoint(){ return fighterAverage;}
    public Point getPullerAveragePoint(){ return pullerAverage;}
    public Point getGatherAveragePoint(){ return gatherAverage;}
    public Point getMinerAveragePoint(){ return minerAverage;}

    public PlayerAnalysis (Player p){
        player = p;
    }

    public void update()
    {
//        timer ++;
//        if (timer %60 == 0)
//        {
//
//        }
        resetBooleans();
        resetUnitLists();
        loopUnits();
        setAverages();
//        timer = 0;
    }

    public void resetBooleans()
    {
        hasRelay = false;
    }

    public void setAverages()
    {
        unitAverage = getAveragePoint(myUnits);
        fighterAverage = getAveragePoint(myFighterUnits);
        pullerAverage = getAveragePoint(myPullerUnits);
        gatherAverage = getAveragePoint(myGathererUnits);
        minerAverage = getAveragePoint(myMinerUnits);
    }

    public void resetUnitLists()
    {
        myUnits = player.getMyUnits();
        myFighterUnits = new ArrayList<>();
        myPullerUnits = new ArrayList<>();
        myGathererUnits = new ArrayList<>();
        myMinerUnits = new ArrayList<>();
    }

    public void loopUnits()
    {
        for (Unit u: myUnits)
        {

            putUnitInLists(u);
            if (u instanceof Commander)
            {
                hasRelay = true;
            }
        }
    }
    public void putUnitInLists(Unit u)
    {
        //puts units in list.
        if (!u.hasComponent(Collector.class) && !u.hasComponent(Drillbeam.class)) {
            myFighterUnits.add(u);
        }
        else if (u.hasComponent(Collector.class)) {
            myGathererUnits.add(u);
        }
        //MINER units counted and put in list
        else if (u.hasComponent(Drillbeam.class)){
            myFighterUnits.add(u);
        }
        else if (u.hasComponent(Pullbeam.class))
        {
            myPullerUnits.add(u);
        }
    }

    public String getPrimaryWeapon() {
        ArrayList<Weapon> weapons = new ArrayList<>();
        int fighterCount = 0;

        for (Unit u: player.getAllUnits()) {
            if (!u.hasWeapon(Drillbeam.class) && !u.hasWeapon(Collector.class))
            {
               fighterCount++;
                if (u.getWeaponOne() != null) {
                   weapons.add(u.getWeaponOne());
               }
                if (u.getWeaponTwo() != null) {
                    weapons.add(u.getWeaponTwo());
                }
            }
        }

        int count = 0;
        for (Weapon w: weapons) {
            if (w instanceof Missile) {
                count++;
            }
        }
        if (((float) count /fighterCount) >= 0.2f) {
            return "missile";
        }
        return "other";


    }

    public Point getAveragePoint(ArrayList<Unit> list)
    {
        float xSum =0;
        float ySum =0;
        for (Unit u: list)
        {
            xSum+= u.getX();
            ySum+= u.getY();
        }
        return new Point((int)(xSum/list.size()), (int)(ySum/list.size()));
    }
}

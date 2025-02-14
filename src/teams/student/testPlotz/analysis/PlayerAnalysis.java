package teams.student.testPlotz.analysis;

import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.utility.Pullbeam;
import objects.entity.unit.Unit;
import player.Player;
import teams.student.testPlotz.units.Commander;

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

    public Player getPlayer(){ return player;}

    public boolean hasRelay(){ return hasRelay;}

    public ArrayList<Unit> getMyUnits(){ return myUnits;}
    public ArrayList<Unit> getFighters(){ return myFighterUnits;}
    public ArrayList<Unit> getMyPullerUnits(){ return myPullerUnits;}
    public ArrayList<Unit> getMyGathererUnits(){ return myGathererUnits;}
    public ArrayList<Unit> getMyMinerUnits(){ return myMinerUnits;}

    public int getNumUnits(){ return myUnits.size();}
    public int getNumFighterUnits(){ return myFighterUnits.size();}
    public int getNumPullerUnits(){ return myPullerUnits.size();}
    public int getNumGathererUnits() { return myGathererUnits.size();}
    public int getNumMinerUnits() { return myMinerUnits.size();}



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
//        timer = 0;
    }

    public void resetBooleans()
    {
        hasRelay = false;
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
}

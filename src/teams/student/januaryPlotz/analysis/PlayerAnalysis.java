package teams.student.januaryPlotz.analysis;

import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.utility.Pullbeam;
import objects.entity.unit.Unit;
import player.Player;

import java.util.ArrayList;

public class PlayerAnalysis {

    Player player;

    int timer;



    static ArrayList<Unit> myUnits = new ArrayList<>();
    static ArrayList<Unit> myFighterUnits = new ArrayList<>();
    static ArrayList<Unit> myPullerUnits = new ArrayList<>();
    static ArrayList<Unit> myGathererUnits = new ArrayList<>();
    static ArrayList<Unit> myMinerUnits = new ArrayList<>();

    public Player getPlayer(){ return player;}

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



    public PlayerAnalysis (Player p){
        player = p;
    }

    public void update()
    {
        timer ++;
        if (timer %60 == 0)
        {
            resetUnitLists();
            loopUnits();
            timer = 0;
        }
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
}

package teams.student.plotz;

import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import engine.Values;
import objects.entity.unit.Unit;
import org.newdawn.slick.geom.Point;
import player.Player;
import teams.student.plotz.units.Catcher;

import java.util.ArrayList;

// stores information about players
public class AllyAnalysis {
    static Player player;
    static int totalSize;
    static int fighterSize;
    static int gathererSize;
    static int minerSize;
    static int minGatSize; // enemy miner AND gatherer total
    static float fighterAvgMaxSpeed;
    static Point avgFighterLocation;
    static Point avgMinGatLocation;
    static Point nearestFighterLocation;

    static String strategy;

    static ArrayList<Unit> allUnits = new ArrayList<>();
    static ArrayList<Unit> allFighterUnits = new ArrayList<>();
    static ArrayList<Unit> allGathererUnits = new ArrayList<>();
    static ArrayList<Unit> allMinerUnits = new ArrayList<>();

    // SETS THE PLAYER

    static public void setPlayer( Player p){ player = p; }


    static public Player getPlayer(){							return player;}

    static public int getTotalSize(){							return totalSize;}
    static public int getFighterSize(){					    	return fighterSize;}
    static public int getMinerSize(){							return minerSize;}
    static public int getGathererSize(){						return gathererSize;}
    static public int getMinerAndGathererSize(){				return minGatSize;}

    static public ArrayList<Unit> getAllUnits(){				return allUnits;}
    static public ArrayList<Unit> getFighters(){				return allFighterUnits;}
    static public ArrayList<Unit> getMiners(){					return allMinerUnits;}
    static public ArrayList<Unit> getGatherers(){				return allGathererUnits;}

    //static public ArrayList<Unit> getMinerAndGathers(){			return allEnemyMinerUnits;}


    static public float getAverageFighterSpeed(){					return fighterAvgMaxSpeed;};
    static public Point getAvgFighterLocation(){					return avgFighterLocation;}
    static public Point getAvgMinerAndGathererLocation(){			return avgMinGatLocation;}
    static public Point getNearestFighter(){						return nearestFighterLocation;}


    public static void analyzePlayer()
    {
        //OPPONENT: General Variables
        allUnits = player.getMyUnits();
        totalSize = allUnits.size();

        //temp variables
        float tempSpeed = 0;
        float xFight =0;
        float yFight = 0;
        float xMinGat = 0;
        float yMinGat = 0;
        float tempDistance = Float.MIN_VALUE;

        //loops through all ENEMIES to find data
        for (Unit e: allUnits)
        {
            //FIGHTER units counted and put in list, adds to total speed
            if (!e.hasComponent(Collector.class) && !e.hasComponent(Drillbeam.class)) {

                if(!allFighterUnits.contains(e))
                {
                    allFighterUnits.add(e);
                    fighterSize = allFighterUnits.size();
                }

                tempSpeed += e.getMaxSpeed();
                xFight += e.getX();
                yFight += e.getY();
            }
            //GATHERER units counted and put in list
            else if (e.hasComponent(Collector.class) && !(e instanceof Catcher)) {

                if(!allGathererUnits.contains(e))
                {
                    allGathererUnits.add(e);
                    gathererSize = allGathererUnits.size();
                }

                xMinGat += e.getX();
                yMinGat += e.getY();
            }
            //MINER units counted and put in list
            else if (e.hasComponent(Drillbeam.class)) {

                if (!allMinerUnits.contains(e))
                {
                    allMinerUnits.add(e);
                    minerSize = allMinerUnits.size();
                }

                xMinGat += e.getX();
                yMinGat += e.getY();
            }

        }

        //cleans up all lists
        for (int i = 0; i < allUnits.size(); i++) {
            if (allUnits.get(i).isDead()) {
                allUnits.remove(i);
                i--;
            }
        }

        for (int i = 0; i < allFighterUnits.size(); i++) {
            if (allFighterUnits.get(i).isDead())
            {
                allFighterUnits.remove(i);
                i--;
            }
        }
        for (int i = 0; i < allGathererUnits.size(); i++) {
            if (allGathererUnits.get(i).isDead())
            {
                allGathererUnits.remove(i);
                i--;
            }
        }
        for (int i = 0; i < allMinerUnits.size(); i++) {
            if (allMinerUnits.get(i).isDead())
            {
                allMinerUnits.remove(i);
                i--;
            }
        }
        fighterSize = allFighterUnits.size();
        minerSize = allMinerUnits.size();
        gathererSize = allGathererUnits.size();
        minGatSize = gathererSize + minerSize;

        minGatSize = gathererSize + minerSize;

        //AVERAGE SPEED & LOCATION
        fighterAvgMaxSpeed =  tempSpeed/totalSize/ Values.SPEED;
        avgFighterLocation = new Point(xFight/fighterSize, yFight/fighterSize);
        avgMinGatLocation = new Point (xMinGat/minGatSize, yMinGat/minGatSize);


    }

    public static void resetVariables()
    {
        Player player = null;
        int totalSize=0;
        int fighterSize=0;
        int gathererSize=0;
        int minerSize=0;


        minGatSize=0; // enemy miner AND gatherer total
        fighterAvgMaxSpeed=0;


        avgFighterLocation=null;
        avgMinGatLocation=null;
        nearestFighterLocation=null;

        strategy=null;

        allUnits = new ArrayList<>();
        allFighterUnits = new ArrayList<>();
        allGathererUnits = new ArrayList<>();
        allMinerUnits = new ArrayList<>();
    }

}

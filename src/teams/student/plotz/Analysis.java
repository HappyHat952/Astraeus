package teams.student.plotz;

import java.util.ArrayList;

import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.utility.Pullbeam;
import engine.Utility;
import engine.Values;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import org.newdawn.slick.Color;
import player.Player;

import org.newdawn.slick.geom.Point;
import teams.student.plotz.*;

public class Analysis {
// This class contains methods relating to enemy & ally movement, as well as general math & statistics.

    //Enemy info
    static Player enemy;
    static int enemyTotalSize;
    static int enemyFighterSize;
    static int enemyGathererSize;
    static int enemyMinerSize;
    static int enemyMinGatSize; // enemy miner AND gatherer total
    static float enemyFighterAvgMaxSpeed;
    static Point enemyAvgFighterLocation;
    static Point enemyAvgMinGatLocation;
    static Point enemyNearestFighterLocation;

    static String enemyStrategy;

    static ArrayList<Unit> allEnemyUnits = new ArrayList<>();
    static ArrayList<Unit> allEnemyFighterUnits = new ArrayList<>();
    static ArrayList<Unit> allEnemyGathererUnits = new ArrayList<>();
    static ArrayList<Unit> allEnemyMinerUnits = new ArrayList<>();


    //Ally info
    static Player ally;
    static int allyTotalSize;
    static int allyFighterSize;
    static int allyGathererSize;
    static int allyMinerSize;
    static int allyMinGatSize; // enemy miner AND gatherer total
    static float allyFighterAvgMaxSpeed;
    static Point allyAvgFighterLocation;
    static Point allyAvgMinGatLocation;

    static String allyStrategy;

    static ArrayList <Unit> allAllyUnits = new ArrayList<>();
    static ArrayList<Unit> allAllyFighterUnits = new ArrayList<>();
    static ArrayList<Unit> allAllyGathererUnits = new ArrayList<>();
    static ArrayList<Unit> allAllyMinerUnits = new ArrayList<>();

    public static float averageFighterNum =0 ;
    static int count =1;
    static String strategy = "default";
    static String win = "tossup";

    //ACCESSORS
    //enemy
    static public Player getEnemy(){									return enemy;}

    static public int getEnemyTotalSize(){								return enemyTotalSize;}
    static public int getEnemyFighterSize(){							return enemyFighterSize;}
    static public int getEnemyMinerSize(){								return enemyMinerSize;}
    static public int getEnemyGathererSize(){							return enemyGathererSize;}
    static public int getEnemyMinerAndGathererSize(){					return enemyGathererSize + enemyMinerSize;}

    static public ArrayList<Unit> getAllEnemyUnits(){					return allEnemyUnits;}
    static public ArrayList<Unit> getEnemyFighters(){					return allEnemyFighterUnits;}
    static public ArrayList<Unit> getEnemyMiners(){						return allEnemyMinerUnits;}
    static public ArrayList<Unit> getEnemyGatherers(){					return allEnemyMinerUnits;}
    //static public ArrayList<Unit> getEnemyMinerAndGathers(){			return allEnemyMinerUnits;}


    static public float getAverageFighterSpeedEnemy(){					return enemyFighterAvgMaxSpeed;};
    static public Point getEnemyAvgFighterLocation(){					return enemyAvgFighterLocation;}
    static public Point getEnemyAvgMinerAndGathererLocation(){			return enemyAvgMinGatLocation;}
    static public Point getNearestEnemyFighter(){						return enemyNearestFighterLocation;	}

    //ally
    static public Player getAlly(){										return ally;}

    static public int getAllyTotalSize(){								return allyTotalSize;}
    static public int getAllyFighterSize(){								return allyFighterSize;}
    static public int getAllyMinerSize(){								return allyMinerSize;}
    static public int getAllyGathererSize(){							return allyGathererSize;}
    static public int getAllyMinerAndGathererSize(){					return allyGathererSize + allyMinerSize;}

    static public ArrayList<Unit> getAllALlyUnits(){					return allAllyUnits;}
    static public ArrayList<Unit> getAllyFighters(){					return allAllyFighterUnits;}
    static public ArrayList<Unit> getALlyMiners(){						return allAllyMinerUnits;}
    static public ArrayList<Unit> getAllyGatherers(){					return allAllyMinerUnits;}
    //static public ArrayList<Unit> getAllyMinerAndGathers(){			return allAllyMinerUnits.add(allAllyGathererUnits);}

    static public float getAverageFighterSpeedAlly(){					return allyFighterAvgMaxSpeed;};
    static public Point getAllyAvgFighterLocation(){					return allyAvgFighterLocation;}
    static public Point getAllyAvgMinerAndGathererLocation(){			return allyAvgMinGatLocation;}



//STATE OF GAME

    public static String getStateOfGame( Player p) { setWinStatement(p); 			return win;}


    public static void setWinStatement(Player p){
        win = "me";
    }


    // STRATEGY NAME
    public static String getEnemyStrategy( Player p ) {		setEnemyStrategy( p );		return strategy;	}

    public static void setEnemyStrategy(Player p){

        // Opponent is FIGHTING if it is closer to our base
        if (p.getMyBase().getDistance(getNearestX(p),0)*2 > p.getOpponent().getMyBase().getDistance(getNearestX(p),0)  )
        {
            strategy = "fight";
        }
        else {
            strategy = "build";
        }

        if (getAverageFighterSpeedEnemy()>14)
        {
            strategy += " fast";
        }
        else {
            strategy += " slow";
        }

    }

    // MATH/STATS Methods: Average speed, fighters, etc.

    // should be called with SPACE TREES as all -- updated EVERY frame
    public static void analyzePlayers(Player spaceTree)
    {
        //ALLY: General Variables
        ally = spaceTree;
        allAllyUnits = ally.getMyUnits();
        allyTotalSize = allAllyUnits.size();

        //temp variables
        float tempSpeed = 0;
        float xFight =0;
        float yFight = 0;
        float xMinGat = 0;
        float yMinGat = 0;

        //loops through all ALLIES to find data
        for (Unit a: allAllyUnits)
        {
            //FIGHTER units counted and put in list
            if (!a.hasComponent(Collector.class) && !a.hasComponent(Drillbeam.class)) {

                if(!allAllyFighterUnits.contains(a))
                {
                    allAllyFighterUnits.add(a);
                    allyFighterSize = allAllyFighterUnits.size();
                }

                tempSpeed += a.getMaxSpeed();
                xFight += a.getX();
                yFight += a.getY();
            }
            //GATHERER units counted and put in list
            else if (a.hasComponent(Collector.class)) {

                if (!allAllyGathererUnits.contains(a))
                {
                    allAllyGathererUnits.add(a);
                    allyGathererSize = allAllyGathererUnits.size();
                }

                xMinGat += a.getX();
                yMinGat += a.getY();
            }
            //MINER units counted and put in list
            else if (a.hasComponent(Drillbeam.class)){

                if (!allAllyMinerUnits.contains(a)) {
                    allAllyMinerUnits.add(a);
                    allyMinerSize = allAllyMinerUnits.size();
                }

                xMinGat += a.getX();
                yMinGat += a.getY();
            }
        }

        allyMinGatSize = allyGathererSize + allyMinerSize;

        //AVERAGE SPEED & LOCATION
        allyFighterAvgMaxSpeed =  tempSpeed/allyTotalSize/Values.SPEED;
        allyAvgFighterLocation = new Point(xFight/allyFighterSize, yFight/allyFighterSize);
        allyAvgMinGatLocation = new Point (xMinGat/allyMinGatSize, yMinGat/allyMinGatSize);

        //OPPONENT: General Variables
        Player opp = ally.getOpponent();
        enemy = opp;
        allEnemyUnits = opp.getMyUnits();
        enemyTotalSize = allEnemyUnits.size();

        //temp variables
        tempSpeed = 0;
        xFight =0;
        yFight = 0;
        xMinGat = 0;
        yMinGat = 0;

        //loops through all ENEMIES to find data
        for (Unit e: allEnemyUnits)
        {
            //FIGHTER units counted and put in list, adds to total speed
            if (!e.hasComponent(Collector.class) && !e.hasComponent(Drillbeam.class)) {

                if(!allEnemyFighterUnits.contains(e))
                {
                    allEnemyFighterUnits.add(e);
                    enemyFighterSize = allEnemyFighterUnits.size();
                }

                tempSpeed += e.getMaxSpeed();
                xFight += e.getX();
                yFight += e.getY();
            }
            //GATHERER units counted and put in list
            else if (e.hasComponent(Collector.class)) {

                if(!allEnemyGathererUnits.contains(e))
                {
                    allEnemyGathererUnits.add(e);
                    enemyGathererSize = allEnemyGathererUnits.size();
                }

                xMinGat += e.getX();
                yMinGat += e.getY();
            }
            //MINER units counted and put in list
            else if (e.hasComponent(Drillbeam.class)) {

                if (!allEnemyMinerUnits.contains(e))
                {
                    allEnemyMinerUnits.add(e);
                    enemyMinerSize = allEnemyMinerUnits.size();
                }

                xMinGat += e.getX();
                yMinGat += e.getY();
            }

        }

        enemyMinGatSize = enemyGathererSize + enemyMinerSize;

        //AVERAGE SPEED & LOCATION
        enemyFighterAvgMaxSpeed =  tempSpeed/enemyTotalSize/Values.SPEED;
        enemyAvgFighterLocation = new Point(xFight/enemyFighterSize, yFight/enemyFighterSize);
        enemyAvgMinGatLocation = new Point (xMinGat/enemyMinGatSize, yMinGat/enemyMinGatSize);




    }

    public static void analyzePlayers()
    {
        if (EnemyAnalysis.getPlayer ()!= null && AllyAnalysis.getPlayer()!=null)
        {
            EnemyAnalysis.analyzePlayer();
            AllyAnalysis.analyzePlayer();
        }


    }
    public static void printNewAnalysis(Player p)
    {
        p.addMessage(p.getName().toUpperCase(), Color.green);
        p.addMessage("Total Size: " + allyTotalSize, Color.green);
        p.addMessage("Miner + Gatherer Total: " + allyMinGatSize, Color.green);
        p.addMessage("Average Max Speed Fighter: " + allyFighterAvgMaxSpeed, Color.green);

        p.addMessage("");
        p.addMessage(p.getOpponent().getName().toUpperCase(), Color.red);
        p.addMessage("Total Size: " + enemyTotalSize, Color.red);
        p.addMessage("Miner + Gatherer Total: " + enemyMinGatSize, Color.red);
        p.addMessage("Average Max Speed Fighter: " + enemyFighterAvgMaxSpeed, Color.red);
        if (allEnemyFighterUnits.size()>3)
        {
            p.addMessage("speed of enemy" + allEnemyFighterUnits.get(3).getMaxSpeed());
        }

    }

    public static void printNewAnalysis(Player p, int s)
    {
        p.addMessage(p.getName().toUpperCase(), Color.green);
        p.addMessage("Total Size: " + AllyAnalysis.getTotalSize(), Color.green);
        p.addMessage("Miner + Gatherer Total: " + AllyAnalysis.getMinerAndGathererSize(), Color.green);
        p.addMessage("Average Max Speed Fighter: " + AllyAnalysis.getAverageFighterSpeed(), Color.green);

        p.addMessage("");

        p.addMessage(p.getOpponent().getName().toUpperCase(), Color.red);
        p.addMessage("Total Size: " + EnemyAnalysis.getTotalSize(), Color.red);
        p.addMessage("Miner + Gatherer Total: " + EnemyAnalysis.getMinerAndGathererSize(), Color.red);
        p.addMessage("Average Max Speed Fighter: " + EnemyAnalysis.getAverageFighterSpeed(), Color.red);
        if (allEnemyFighterUnits.size()>3)
        {
            p.addMessage("speed of enemy" + allEnemyFighterUnits.get(3).getMaxSpeed());
        }
    }
    public static float getNearestX(Player p)
    {
        float x =  p.getOpponent().getMyBase().getNearestEnemy().getX();
        return x;
    }

//    public static void setGroupSize(int i)
//    {
//        PlotzUnit.setGroupSize(i);
//    }

    //OLD STUFF

    public static float getAverageMaxSpeed(Player p) {
        // Returns average max speed of all enemy units
        ArrayList<Unit> units = p.getMyUnits();
        float total = 0;
        int count = 0;
        for (Unit u : units) {
            if (!isPassive(u)) {
                total += u.getMaxSpeed() / Values.SPEED;
                count++;
            }
        }
        if (units.size() == 0) {
            return 0;
        }
        return (float) total / count;
    }

    //Average Fighter X value
    public static float getAverageFighterX(Player p)
    {
        ArrayList<Unit> units = p.getMyUnits();
        float total = 0;
        int count = 0;
        for (Unit u: units)
        {
            if (!isPassive(u))
            {
                total += u.getX();
                count ++;
            }
        }
        return (float) total/count;
    }

    //Nearest X Value



    public static float getAllyFighterPercentage() {
        // Returns percentage of enemy ships that are not miners or gatherers
        return ((float)allyFighterSize)/allyTotalSize * 100;
    }
    public static float getEnemyFighterPercentage() {
        // Returns percentage of enemy ships that are not miners or gatherers
        return ((float)enemyFighterSize)/enemyTotalSize * 100;
    }

    public static float getAllyMinerAndGathererPercentage() {
        // Returns percentage of enemy ships that are not miners or gatherers
        return ((float)allyGathererSize + allyMinerSize)/allyTotalSize * 100;
    }

    public static float getEnemyMinerAndGathererPercentage() {
        // Returns percentage of enemy ships that are not miners or gatherers
        return ((float)enemyGathererSize + enemyMinerSize)/enemyTotalSize * 100;
    }

    public static float getShipConcentration(Player p) {
        // Returns the average distance between ships
        ArrayList<Unit> units = fighterList(p);

        if (units.isEmpty()) {
            return 0;
        }

        float xTotal = 0;
        float yTotal = 0;
        float midX = 0;
        float midY = 0;
        float totalDistance = 0;

        for (Unit u : units) {
            xTotal = xTotal + u.getX();
            yTotal = yTotal + u.getY();
        }

        midX = (float) xTotal / units.size();
        midY = (float) yTotal / units.size();

        for (Unit u : units) {
            totalDistance += Utility.distance(u.getX(), u.getY(), midX, midY);
        }


        return totalDistance / units.size();

    }

// RESOURCE Methods: Relates to resource manager (in progress)

    // HELPER Methods: Smaller methods
    public static boolean isPassive(Unit u) {
        // Returns if a unit is a gatherer or miner
        return (u.hasWeapon(Collector.class) || u.hasWeapon(Drillbeam.class));
    }

    // GET UNITS Methods: Get specific unit(s)
    public static ArrayList<Unit> getPullers(Player p) {
        ArrayList<Unit> units = new ArrayList<Unit>();

        for (Unit u : p.getMyUnits()) {
            if (u.hasComponent(Pullbeam.class)) {
                units.add(u);
            }
        }
        return units;
    }
    public static Unit getNearestCriticalUnit(Unit origin, int radius) {
        ArrayList<Unit> targets = origin.getEnemiesInRadius(radius);
        Unit critical = null;
        float lowestHealth = Float.MAX_VALUE;

        for (Unit u : targets) {
            float health = u.getCurEffectiveHealth();
            if (health < lowestHealth) {
                lowestHealth = health;
                critical = u;
            }
        }
        return critical;
    }

    public static Unit getNearestCriticalFighter(Unit origin, int radius) {
        ArrayList<Unit> targets = origin.getEnemiesInRadius(radius);
        Unit critical = null;
        float lowestHealth = Float.MAX_VALUE;

        for (Unit u : targets) {
            float health = u.getCurEffectiveHealth();
            if (!isPassive(u) && health < lowestHealth) {
                lowestHealth = health;
                critical = u;
            }
        }
        return critical;
    }

    public static Unit getNearestCriticalWorker(Unit origin, int radius) {
        ArrayList<Unit> targets = origin.getEnemiesInRadius(radius);
        Unit critical = null;
        float lowestHealth = Float.MAX_VALUE;

        for (Unit u : targets) {
            float health = u.getCurEffectiveHealth();
            if (isPassive(u) && health < lowestHealth) {
                lowestHealth = health;
                critical = u;
            }
        }
        return critical;
    }

    public static ArrayList<Unit> fighterList(Player p) {
        ArrayList<Unit> fighters = new ArrayList<Unit>();

        for (Unit u : p.getMyUnits())
        {
            if (!isPassive(u))
            {
                fighters.add(u);
            }
        }
        return fighters;
    }


}
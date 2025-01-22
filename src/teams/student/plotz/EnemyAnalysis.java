package teams.student.plotz;

import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.utility.Pullbeam;
import engine.Values;
import engine.states.Game;
import objects.entity.unit.Unit;
import org.newdawn.slick.geom.Point;
import player.Player;

import java.util.ArrayList;

// stores information about players
public class EnemyAnalysis {
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
    static Unit nearestFighter;

    static boolean MinersClustered;
    static boolean fightersClustered;
    static boolean hasPullers;

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
    static public ArrayList<Unit> getGatherers(){				return allMinerUnits;}

    //static public ArrayList<Unit> getMinerAndGathers(){			return allEnemyMinerUnits;}


    static public boolean getFightersIsCluster(){               return getIsCluster(allFighterUnits, 3, avgFighterLocation, 500);}
    static public boolean hasPullers() {                        return hasPullers;}
    static public float getAverageFighterSpeed(){				return fighterAvgMaxSpeed;};
    static public Point getAvgFighterLocation(){				return avgFighterLocation;}
    static public Point getAvgMinerAndGathererLocation(){		return avgFighterLocation;}
    static public Point getNearestFighter(){					return nearestFighterLocation;}


    public static void analyzePlayer() {
        //OPPONENT: General Variables
        allUnits = player.getMyUnits();
        totalSize = allUnits.size();



        //cleans up all lists
        for (int i = 0; i < allUnits.size(); i++) {
            if (allUnits.get(i).isDead()) {
                allUnits.remove(i);
                i--;
            }
        }

        for (int i = 0; i < allFighterUnits.size(); i++) {
            if (allFighterUnits.get(i).isDead()) {
                allFighterUnits.remove(i);
                i--;
            }
        }
        for (int i = 0; i < allGathererUnits.size(); i++) {
            if (allGathererUnits.get(i).isDead()) {
                allGathererUnits.remove(i);
                i--;
            }
        }
        for (int i = 0; i < allMinerUnits.size(); i++) {
            if (allMinerUnits.get(i).isDead()) {
                allMinerUnits.remove(i);
                i--;
            }
        }
        //temp variables
        float tempSpeed = 0;
        float xFight = 0;
        float yFight = 0;
        float xMinGat = 0;
        float yMinGat = 0;
        float tempDistance = Float.MIN_VALUE;
        if (player.getMyBase()!= null && player.getOpponent().getMyBase()!= null)
        {
            tempDistance = player.getMyBase().getDistance(player.getOpponent().getMyBase());
        }
        //loops through all ENEMIES to find data
        for (Unit e : allUnits) {
            if (!e.isDead())
            {
                if (e.hasComponent(Pullbeam.class))
                {
                    hasPullers = true;
                }

                //FIGHTER units counted and put in list, adds to total speed
                if (!e.hasComponent(Collector.class) && !e.hasComponent(Drillbeam.class)) {

                    if (!allFighterUnits.contains(e)) {
                        allFighterUnits.add(e);
                        fighterSize = allFighterUnits.size();
                    }
                    if(e.getDistance(player.getOpponent().getMyBase())<tempDistance)
                    {
                        nearestFighterLocation = e.getPosition();
                        tempDistance = e.getDistance(player.getOpponent().getMyBase());
                    }

                    tempSpeed += e.getMaxSpeed();
                    xFight += e.getX();
                    yFight += e.getY();
                }
                //GATHERER units counted and put in list
                else if (e.hasComponent(Collector.class)) {

                    if (!allGathererUnits.contains(e)) {
                        allGathererUnits.add(e);
                        gathererSize = allGathererUnits.size();
                    }

                    xMinGat += e.getX();
                    yMinGat += e.getY();
                }
                //MINER units counted and put in list
                else if (e.hasComponent(Drillbeam.class)) {

                    if (!allMinerUnits.contains(e)) {
                        allMinerUnits.add(e);
                        minerSize = allMinerUnits.size();
                    }

                    xMinGat += e.getX();
                    yMinGat += e.getY();
                }
            }

        }


        fighterSize = allFighterUnits.size();
        minerSize = allMinerUnits.size();
        gathererSize = allGathererUnits.size();
        minGatSize = gathererSize + minerSize;


        //AVERAGE SPEED & LOCATION
        fighterAvgMaxSpeed = tempSpeed / totalSize / Values.SPEED;


        if (Game.getTime() % 15 == 0) {
            avgFighterLocation = getFocusLocation(allFighterUnits);
            avgMinGatLocation = getFocusLocation(allMinerUnits);
        }
        if (avgMinGatLocation == null && player.getMyBase()!= null)
        {
            avgMinGatLocation = player.getMyBase().getPosition();
        }
        if (avgFighterLocation == null)
        {
            avgFighterLocation = new Point(0,0);
        } if (avgMinGatLocation == null)
        {
            avgMinGatLocation = new Point (0,0);
        }


        boolean tempFightersClustered = getIsCluster(allFighterUnits, 3, avgFighterLocation, 500);
        if (fightersClustered != tempFightersClustered)
        {
            fightersClustered = tempFightersClustered;
            System.out.println(fightersClustered);
        }



    }

    public static Point getFocusLocation(ArrayList <Unit> list)
    {

        Point tempPoint = getMedianLocation(list);
        boolean isClustered = getIsCluster(list, 3, tempPoint, 500);

        if(!isClustered && nearestFighterLocation != null)
        {
            return nearestFighterLocation;
        }
        return tempPoint;

    }

    private static Point getMedianLocation (ArrayList <Unit> list) {
        ArrayList<Float> xList = new ArrayList<Float>();
        ArrayList<Float> yList = new ArrayList<Float>();

//        float xMed = player.getMyBase().getX();
//        float yMed = player.getMyBase().getY();
        float xMed =0;
        float yMed = 0;

        for (Unit u : list) {

            // setup sequential x
            if (xList.isEmpty()) {
                xList.add(u.getX());
            }

            //sequential sort
            else {
                int i = 0;
                boolean found = false;

                while (i < xList.size() && !found) {
                    if (u.getX() > xList.get(i)) {
                        i++;
                    }
                    // if smaller than or equal to current value, replace
                    else {
                        found = true;
                    }
                }
                xList.add(i, u.getX());
            }

            // setup sequential y
            if (yList.isEmpty()) {
                yList.add(u.getY());
            }

            //sequential sort
            else {
                int i = 0;
                boolean found = false;

                while (i < yList.size() && !found) {
                    if (u.getY() > yList.get(i)) {
                        i++;
                    }
                    // if smaller than or equal to current value, replace
                    else {
                        found = true;
                    }
                }
                yList.add(i, u.getY());
            }
        }

        if (!xList.isEmpty() && !yList.isEmpty())
        {
            xMed = xList.get(xList.size()/2);
            yMed = yList.get(yList.size()/2);
        }

//        System.out.print(": "+list.size());
//        System.out.print("\nx: ");
////        for (Float i: xList)
////        {
////            System.out.print(i+ " ");
////        }
//        System.out.print("--- "+xMed);
//        System.out.print("\ny: ");
////        for (Float i: yList)
////        {
////            System.out.print(i+ " ");
////        }
//        System.out.println("--- "+yMed);
//
        return (new Point (xMed, yMed));

    }

    private static boolean getIsCluster (ArrayList <Unit> list, int num, Point p, float range)
    {
        int inRange = 0;
        for (Unit u:list){
            if (u.getDistance(p)< range)
            {
                inRange ++;
            }
        }
        if (inRange >= num)
        {
            return true;
        }
        return false;
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
        nearestFighter=null;

        allUnits = new ArrayList<>();
        allFighterUnits = new ArrayList<>();
        allGathererUnits = new ArrayList<>();
        allMinerUnits = new ArrayList<>();

        fightersClustered=false;
        hasPullers =false;


    }



}

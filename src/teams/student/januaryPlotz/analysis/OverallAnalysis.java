package teams.student.januaryPlotz.analysis;

import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import teams.student.januaryPlotz.units.Gatherer;
import teams.student.januaryPlotz.JanuaryPlotz;
import teams.student.januaryPlotz.units.Miner;

import java.util.ArrayList;

public class OverallAnalysis {

    private static JanuaryPlotz plotz;

    public static final int BUILD = 0;
    public static final int FIGHT = 1;
    public static final int FINAL = 2;

    private static int currentStage;

    private static Point coreRallyPoint;
    private static Point enemyPt;
    private static Point allyPt;
    private static PlayerAnalysis enemy;
    private static PlayerAnalysis ally;
    private static float rallyPercent;

    private static ArrayList<Point>  rallyPts;

    private int numRallyPoint;
    private int clusterSize;

    public OverallAnalysis(JanuaryPlotz p)
    {
        enemy = new PlayerAnalysis(p.getOpponent());
        ally = new PlayerAnalysis(p);
        plotz = p;


        clusterSize = 4;
    }

    public static PlayerAnalysis getAlly(){ return ally;}
    public static PlayerAnalysis getEnemy(){ return enemy;}
    public static Point getCoreRallyPoint(){ return coreRallyPoint;}
    public static ArrayList<Point> getAllRallyPoints(){ return rallyPts;}
    public static float getRallyPercent(){ return rallyPercent;}
    public static int getCurrentStage(){ return currentStage;}

    public void update() {
        //setting rally points;
        if (ally != null && enemy != null) {
            ally.update();
            enemy.update();
            setRallyDetails();
            setCoreRallyPoint();
            setAllRallyPoint();
        }

        //determining the stage
        if (ally.getPlayer().getMineralsMined() < 150) {
            currentStage = BUILD;
        } else
        {
            currentStage = FIGHT;
        }

    }

    public static void draw(Graphics g)
    {
        //display current stage
        g.setColor(Color.white);
        ally.getPlayer().addMessage(""+ currentStage);

        //draws enemy to ally
        g.setColor(Color.red);
        g.setLineWidth(4);
        g.drawLine(enemyPt.getX(),enemyPt.getY(),allyPt.getX(),allyPt.getY());
        g.resetLineWidth();

        //draws rally point.
        g.setColor(Color.blue);
        g.fillOval(coreRallyPoint.getX(), coreRallyPoint.getY(), 20,20);
        g.setColor(Color.white);
        g.drawString(""+rallyPercent, coreRallyPoint.getX(), coreRallyPoint.getY());

        for (Point p: rallyPts)
        {
            g.setColor(Color.cyan);
            g.fillOval(p.getX(),p.getY(), 15,15);
        }


//        for (Unit u: ally.getMyGathererUnts()) {
//           Point p = ((Gatherer) u).getDestination();
//            g.drawLine(u.getX(), u.getY(), p.getX(), p.getY());
//        }
//
//        for (Unit u: ally.getMyMinerUnits()) {
//            Point p = ((Miner) u).getDestination();
//            g.drawLine(u.getX(), u.getY(), p.getX(), p.getY());
//        }
    }

    public void setRallyDetails()
    {
        rallyPercent = (float)ally.getPlayer().getFleetValueUnit()/(float)(ally.getPlayer().getFleetValueUnit() + enemy.getPlayer().getFleetValueUnit());
        //numRallyPoint = ally.getNumFighterUnits()/ clusterSize;
        numRallyPoint = 4;
        if (numRallyPoint == 0)
        {
            numRallyPoint = 1;
        }
    }

    public void setCoreRallyPoint()
    {
         enemyPt = enemy.getPlayer().getMyBase().getPosition();
         allyPt  = ally.getPlayer().getMyBase().getPosition();

        float x = (1.0f - rallyPercent) * allyPt.getX() + rallyPercent * enemyPt.getX();
        float y = (1.0f - rallyPercent) * allyPt.getY() + rallyPercent * enemyPt.getY();


        coreRallyPoint = new Point(x, y);
    }

    public void setAllRallyPoint()
    {
        rallyPts = new ArrayList<>();
        float length = ally.getPlayer().getMyBase().getDistance(enemy.getPlayer().getMyBase());
        for (float i = -(float)numRallyPoint/2; i<=(float)numRallyPoint/2; i++)
        {
            rallyPts.add(new Point(coreRallyPoint.getX(), coreRallyPoint.getY() + length*.5f*i));
        }
    }






}

package teams.student.testPlotz.units;

import components.weapon.economy.Collector;
import components.weapon.energy.Laser;
import components.weapon.explosive.Missile;
import components.weapon.utility.SpeedBoost;
import engine.states.Game;
import objects.GameObject;
import objects.entity.missile.MissileEntity;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.analysis.Block;
import teams.student.testPlotz.analysis.OverallAnalysis;

import java.util.ArrayList;
import org.newdawn.slick.geom.Point;

public class Raider extends Distractor {

    private static Unit gather;
    private static Point rally;
    private Unit fighter;
    private Point rallyPoint;

    private Block myBlock;
    private Point p;

    public Raider(TestPlotz p) {
        super(p);
    }

    public void design() {
        setFrame(Frame.LIGHT);
        setStyle(Style.DRAGON);

        add(Laser.class);
        add(SpeedBoost.class);



    }

    public void setBlock(Block block){ myBlock = block;}

    public static Point getRally(){ return rally;}
    public static Unit getGatherer(){ return gather;}
    

//    public void action() {
        //FIRST: establish all units!
//        if (gather == null || !getGatherIsVulnerable(gather))
//        {
//            gather = getGather();
//        }
//
        // set a rally point safe and far from enemy ships. For now, in the middle top or bottom based on current gather.
//        float rX = (getEnemyBase().getX() + getHomeBase().getX())/2;
//        float fighterY = OverallAnalysis.getEnemy().getFighterAveragePoint().getY();
//        if (gather.getY()> fighterY)
//        {
//            rally = new Point(rX, fighterY +90 );
//        }
//        else;
//        {
//            rally = new Point(rX, fighterY -90);
//        }
//
//        //NEXT: movement
//        if (x< rally.getX())
//        {
//            turnTo(rally);
//        }
//        else
//        {
//            turnTo(gather);
//        }
//
//        if (getEnemiesInRadius(950).size()<2)
//        {
//            getWeaponTwo().use();
//        }
//        else if (!getIfImSafe())
//        {
//            turnTo(getNearestThreat());
//            turnAround();
//            getWeaponTwo().use();
//        }
//        move();
//
//         Unit nearest = getLowestHealthGatherInRadius((int)(getMaxRange()*.95));
//
//        if (getDistance(gather)< getMaxRange()*1.4 && nearest != null)
//        {
//            getWeaponOne().use(nearest);
//        }
//        else if (getDistance(gather)< getMaxRange()*.95)
//        {
//            getWeaponOne().use(gather);
//        }
//
//    }

    public void draw(Graphics g)
    {
        if (p!= null)
        {
            g.setColor(Color.gray);
            g.drawLine(p.getX(), p.getY(), getX(), getY());
        }
        if (gather!= null)
        {
            g.setColor(Color.orange);
            g.setLineWidth(3);
            g.drawRect(gather.getX(),gather.getY(),gather.getWidth(), gather.getHeight());
            g.resetLineWidth();
        }
    }


    public void action()
    {
        Block b = null;

        //FIRST: establish all units!
        if ((gather == null || !getGatherIsVulnerable(gather) || gather.isDead()|| getBlock(gather) == null ||
                getBlock(gather).getDifference()<=-2))
        {
            gather = getGather();
        }

        // set a rally point safe and far from enemy ships. For now, in the middle top or bottom based on current gather.
        float rX = (getEnemyBase().getX() + getHomeBase().getX())/2;
        float fighterY = OverallAnalysis.getEnemy().getFighterAveragePoint().getY();
        if ( gather != null && gather.getY()> fighterY)
        {
            rally = new Point(rX, (fighterY + Game.getMapTopEdge())/2 );
        }
        else
        {
            rally = new Point(rX, (fighterY +Game.getMapBottomEdge())/2);
        }

        //NEXT: movement
        if (x< rally.getX())
        {
            turnTo(rally);
            getWeaponTwo().use();
        } else if (myBlock != null && gather != null)
        {
            b = TestPlotz.getBlocks().getNearestBlock(gather, -3, myBlock);

            if (b == null)
            {

                p = getRally();
            }
            else {
                p = b.getMidPoint();
            }
            moveTo(p);
        }



        if (myBlock != null && myBlock.getDifference()>= -2 || getNearestMissileLockedOnMe()!= null )
        {
            getWeaponTwo().use();
        }
        else if (!getIfImSafe())
        {
            turnTo(getNearestThreat());
            turnAround();
            getWeaponTwo().use();
        }
        move();

         Unit nearest = getLowestHealthGatherInRadius((int)(getMaxRange()*.95));
         if (gather != null && getDistance(gather)< getMaxRange()*.95)
         {
             getWeaponOne().use(gather);
         }

        if (nearest != null && gather != null && getDistance(gather)>getMaxRange()*1.1f)
        {
            getWeaponOne().use(nearest);
        }







    }

    public boolean getGatherIsVulnerable(Unit u)
    {
        int fCount =0;
        for (Unit f: u.getAlliesInRadius(400))
        {
            if (!isPassive(f))
            {
                fCount++;
            }
        }
        if (getDistance(getNearestEnemyMissile())<500)
        {
            fCount+=2;
        }
        return fCount<=2;
    }
    public boolean getIfImSafe()
    {
        int fCount =0;
        for (Unit f: this.getEnemiesInRadius(400))
        {
            if (!isPassive(f))
            {
                fCount++;
            }
        }
        if (getNearestEnemyMissile() != null && getNearestEnemyMissile().getDistance(this)< 200)
        {
            fCount++;
        }
        return fCount<=2;
    }

    public Unit getLowestHealthGatherInRadius(int radius)
    {
        ArrayList<Unit> allEnemy = getEnemiesInRadius(radius);
        if (allEnemy.size()>0)
        {
            Unit lowestGather = allEnemy.get(0);
            float lowHealth =Integer.MAX_VALUE;


            for (Unit u: allEnemy)
            {
                if (isPassive(u) && u.getCurEffectiveHealth()< lowHealth)// checks its a gatherer
                {
                    lowHealth = u.getCurEffectiveHealth();
                    lowestGather = u;
                }

            }
            return lowestGather;
        }
        return null;


    }

    public Unit getGather()
    {
        ArrayList<Unit> allEnemy = getEnemies();
        Unit bestGather = null;
        if (!allEnemy.isEmpty())
        {
            bestGather = allEnemy.get(0);
        }
        int highScore =Integer.MIN_VALUE;


        for (Unit u: allEnemy)
        {
            if (isPassive(u))// checks its a gatherer
            {
                int score=0;
                int radius = 750;
                for (Unit e: u.getAlliesInRadius(radius))
                {
                    if (!isPassive(e)) {
                        score -= (radius - u.getDistance(e));
                    }
                    else if (isPassive(u))
                    {
                        score += (radius - u.getDistance(e));
                    }
                    score -= u.getDistance(this);
                }
                if (score >highScore)
                {
                    highScore = score;
                    bestGather = u;
                }
            }
        }
        return bestGather;
    }

    public ArrayList<Unit> getRaidersAroundMe()
    {
        ArrayList<Unit> allUnits = getAlliesInRadius(getMaxRange());
        ArrayList<Unit> raiders = new ArrayList<>();
        for (Unit u: allUnits)
        {
            if (u instanceof Raider)
            {
                raiders.add(u);
            }
        }
        return raiders;
    }

    public Block getBlock(Unit u)
    {
        return TestPlotz.getBlocks().getBlock(u);
    }
    public Block getBlock(Point p)
    {
        return TestPlotz.getBlocks().getBlock(p);
    }

    public GameObject getNearestThreat()
    {

    //returns the nearest threat (enemy or missile) at any moment)
        ArrayList<Unit> allEnemy = getEnemies();
        Unit nearestE = getEnemyBase();
        float nearestEDist = Float.MAX_VALUE;
        MissileEntity missile = getNearestEnemyMissile();

        for (Unit e: allEnemy)
        {
            if (!isPassive(e) && getDistance(e)<nearestEDist)
            {
                nearestEDist = getDistance(e);
                nearestE = e;
            }
        }
        if (getDistance(missile)< getDistance(nearestE))
        {
            return missile;
        }
        else {
            return nearestE;
        }
    }


//        if (getTimeAlive() < 15 * 60) {
//            nearResEnmy = getSafestGatherer(); // closest of all resource-collecting ships
//        }
//
////        if (nearResEnmy == null || !nearResEnmy.isAlive()) { // ADD SAFETY RATING
//        nearResEnmy = getSafestGatherer(); // so it doesnt reset until its dead
////        }
//        nearAllEnmy = getMyNearestEnemy(); // closest of all enemy ships
//
//
//        // If its just created, go to rally point ->2500 pixels n or s of homebase
//        if (getTimeAlive() < 15 * 60) {
//            moveTo(getHomeBase().getX(), 2500);
//            getWeaponTwo().use();
////            setDestination(getHomeBase().getX(), 2500);
//        }
//
//        if (!this.isInBounds()) {
//            moveTo(nearResEnmy);
//        }
//
//        //if a nearest enemy exists and its distance is less than our max range, avoid by switching sides.
//        if (nearAllEnmy != null && getDistance(nearAllEnmy) < nearAllEnmy.getMaxRange() + 100) {
//
//            turnTo(nearResEnmy);
//            getWeaponTwo().use();
//            move();
//
//        } else if (getDistance(getEnemyBase()) < 300) {
//            turnTo(getEnemyBase());
//            turnAround();
//        }
//
//        //if a resource enemy is further than max range, go to it.
//        else if (getDistance(nearResEnmy) > getMaxRange()) {
//            moveTo(nearResEnmy);
//
//            if (getDistance(nearResEnmy) - getMaxRange() < 50) {
//                getWeaponOne().use(nearResEnmy);
//            }
//        }
//    }


}


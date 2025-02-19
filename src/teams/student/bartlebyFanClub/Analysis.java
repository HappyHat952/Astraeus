package teams.student.bartlebyFanClub;

import components.weapon.utility.Pullbeam;
import objects.entity.unit.Unit;

import java.util.ArrayList;

public class Analysis
{
//    public int homeRadius()
//    {
//        int h = getDistance(getHomeBase());
//    }

    public float getCurAvgEnemyHealthInRadius(Unit origin, int radius)
    {
        float count = 0;
        ArrayList<Unit> eir = origin.getEnemiesInRadius(radius);

        for(int i = 0; i < eir.size(); i++)
        {
            count += eir.get(i).getCurEffectiveHealth();
        }

        float avg = count / eir.size();

        return avg;
    }

    public float getMaxAvgEnemyHealthInRadius(Unit origin, int radius)
    {
        float count = 0;
        ArrayList<Unit> eir = origin.getEnemiesInRadius(radius);

        for(int i = 0; i < eir.size(); i++)
        {
            count += eir.get(i).getMaxEffectiveHealth();
        }

        float max = count / eir.size();

        return max;
    }

    public boolean enemiesHaveLowHealthInRadius(Unit origin, int radius)
    {
        return getCurAvgEnemyHealthInRadius(origin, radius) <= getMaxAvgEnemyHealthInRadius(origin, radius) / 3;
    }

    public boolean enemyHasLowHealthInRadius(Unit origin, int radius)
    {
        Unit e = origin.getNearestEnemyUnit();
        float curH = e.getCurEffectiveHealth();
        float maxH = e.getMaxEffectiveHealth();
        return curH < maxH / 3;
    }

    public static Unit getLowestEnemyHealthInRadius(Unit origin, int radius)
    {
        float lowestHealth = 0;
        Unit lowestEnemy = null;
        ArrayList<Unit> enemies = origin.getEnemiesInRadius(radius);

        for(int i = 0; i < enemies.size(); i++)
        {
            if(lowestHealth <= 0)
            {
                lowestHealth = enemies.get(i).getCurEffectiveHealth();
                lowestEnemy = enemies.get(i);
            }
            else
            {
                if(enemies.get(i).getCurEffectiveHealth() < lowestHealth)
                {
                    lowestHealth = enemies.get(i).getCurEffectiveHealth();
                    lowestEnemy = enemies.get(i);
                }
            }
        }
        return lowestEnemy;
    }

    public Unit getHighestEnemyHealthInRadius(Unit origin, int radius)
    {
        float highHealth = 0;
        Unit highEnemy = null;
        ArrayList<Unit> enemies = origin.getEnemiesInRadius(radius);

        for(int i = 0; i < enemies.size(); i++)
        {
            if(enemies.get(i).getCurEffectiveHealth() > highHealth)
            {
                highHealth = enemies.get(i).getCurEffectiveHealth();
                highEnemy = enemies.get(i);
            }
        }
        return highEnemy;
    }

    public Unit getLowestAllyHealthInRadius(Unit origin, int radius)
    {
        float lowestHealth = 0;
        Unit lowestAlly = null;
        ArrayList<Unit> allies = origin.getAlliesInRadius(radius);

        for(int i = 0; i < allies.size(); i++)
        {
            if(lowestHealth <= 0)
            {
                lowestHealth = allies.get(i).getCurEffectiveHealth();
                lowestAlly = allies.get(i);
            }
            else
            {
                if(allies.get(i).getCurEffectiveHealth() < lowestHealth)
                {
                    lowestHealth = allies.get(i).getCurEffectiveHealth();
                    lowestAlly = allies.get(i);
                }
            }
        }
        return lowestAlly;
    }

    public Unit getHighestAllyHealthInRadius(Unit origin, int radius)
    {
        float highHealth = 0;
        Unit highAlly = null;
        ArrayList<Unit> enemies = origin.getEnemiesInRadius(radius);

        for(int i = 0; i < enemies.size(); i++)
        {
            if(enemies.get(i).getCurEffectiveHealth() > highHealth)
            {
                highHealth = enemies.get(i).getCurEffectiveHealth();
                highAlly = enemies.get(i);
            }
        }
        return highAlly;
    }

 /*   public Unit getLowestAllyHealthInFleet()
    {
        float lowestHealth = 0;
        Unit lowestAlly = null;
        ArrayList<Unit> fleet = getPlayer().countMyUnits();

        for(int i = 0; i < fleet.size(); i++)
        {
            if(lowestHealth <= 0)
            {
                lowestHealth = fleet.get(i).getCurEffectiveHealth();
                lowestAlly = fleet.get(i);
            }
            else
            {
                if(fleet.get(i).getCurEffectiveHealth() < lowestHealth)
                {
                    lowestHealth = fleet.get(i).getCurEffectiveHealth();
                    lowestAlly = fleet.get(i);
                }
            }
        }
        return lowestAlly;
    } */

    public boolean enemyHasPullbeamInRadius(Unit origin, int radius)
    {
        ArrayList <Unit> epb = origin.getEnemiesInRadius(radius);
        for(int i = 0; i < epb.size(); i++)
        {
            if(epb.get(i).hasWeapon(Pullbeam.class))
            {
                return true;
            }
        }
        return false;
    }
}

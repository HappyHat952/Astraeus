package teams.student.spaceTrees;

import objects.entity.unit.Unit;
import player.Player;
import teams.student.spaceTrees.units.Healer;

import java.util.ArrayList;

// Manager class for healers. assigns weak units to healers to heal
public class ManageHealer
{

    public ArrayList<Healer> healers;
    private ArrayList<Unit> units;
    private Player p;

    public ManageHealer(Player p)
    {
        healers = new ArrayList<Healer>(); // stores list of healers to manage
        units = new  ArrayList<Unit>(); // list of units that are currently being repaired
        this.p = p;
    }

    // assigns a weak unit to a healer if it is not busy.
    public void assign()
    {
        cleanup();
        for (Healer h: healers)
        {
            if (!h.isBusy())
            {
                Unit u = getWeakestPlayer(h);
                while (u != null)
                {
                    if (!units.contains(u)) // if the weak unit is already assigned then move to the next weak unit
                    {
                        h.setWeakUnit(u);
                        units.add(u);
                        break;
                    }
                    else
                    {
                        u = getWeakestPlayer(h);
                    }
                }
            }
        }
    }

    public int getFreeHealerCount() {
        int i = 0;
        for (Healer h: healers)
        {
            if(!h.isBusy()) {
                i++;
            }
        }
        return i;
    }

    public int getBusyHealerCount() {
        int i = 0;
        for (Healer h: healers)
        {
            if(h.isBusy()) {
                i++;
            }
        }
        return i;
    }

    public void addHealer(Healer h) {
        healers.add(h);
    }

    // remove dead/repaired units from the list
    private void cleanup()
    {
        for (int i = 0; i < units.size(); i++)
        {
            if(units.get(i).isDead() || units.get(i).getPercentEffectiveHealth() == 1)
            {
                units.remove(i);
                i--;
            }
        }
    }

    public Unit getWeakestPlayer(Healer h)
    {
        float lowestHealth = 1;
        Unit weak = null;
        for (Unit u: p.getMyUnits())
        {
            if (h != u && !units.contains(u) && u.getPercentEffectiveHealth() < lowestHealth)
            {
                weak = u;
                lowestHealth = u.getPercentEffectiveHealth();
            }
        }
        return weak;
    }
}
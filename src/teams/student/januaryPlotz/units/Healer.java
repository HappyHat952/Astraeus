package teams.student.januaryPlotz.units;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.utility.ElectromagneticPulse;
import components.weapon.utility.RepairBeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.januaryPlotz.JanPlotzUnit;
import teams.student.januaryPlotz.JanuaryPlotz;


import java.util.ArrayList;

public class Healer extends JanPlotzUnit {
    public Healer(JanuaryPlotz p) {
        super(p);
    }

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.DAGGER);

        add(RepairBeam.class);
        add(Shield.class);
        add(Plating.class);
    }

    public void action() {
        super.action();
    }

    public void attack(Weapon w) {
        heal(w);
    }

    public void movement() {
        Unit fighterAlly = getNearestAlly(Fighter.class);
        Unit a = getLowestAllyHealthInRadius(this, getMaxRange() * 4);
        Unit e = getNearestEnemy();

        if (getDistance(e) < getDistance(fighterAlly)) {
            turnTo(fighterAlly);
            move();
        }
        if (a != null) {
            moveTo(a);
        } else if (fighterAlly != null && getDistance(fighterAlly) > getMaxRange() * 2.5f) {
            moveTo(fighterAlly);
        }
    }

    public void heal(Weapon w) {
        Unit ally = getLowestAllyHealthInRadius(this, getMaxRange() * 4);

        if (ally != null && w != null) {
            w.use(ally);
        }
    }

    public Unit getLowestAllyHealthInRadius(Unit origin, int radius) {
        float lowestHealth = 0;
        Unit lowestAlly = null;
        ArrayList<Unit> allies = origin.getAlliesInRadius(radius);

        for (int i = 0; i < allies.size(); i++) {
            if (lowestHealth <= 0) {
                lowestHealth = allies.get(i).getCurEffectiveHealth();
                lowestAlly = allies.get(i);
            } else {
                if (allies.get(i).getCurEffectiveHealth() < lowestHealth && !allies.get(i).hasWeapon(ElectromagneticPulse.class) && !allies.get(i).hasWeapon(Collector.class) && !allies.get(i).hasWeapon(Drillbeam.class)) {
                    lowestHealth = allies.get(i).getCurEffectiveHealth();
                    lowestAlly = allies.get(i);
                }
            }
        }

        return lowestAlly;
    }
}



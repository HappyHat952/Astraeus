package teams.student.testPlotz.units;

import components.mod.utility.NarcissusMod;
import components.upgrade.HeavyShield;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.explosive.Missile;
import components.weapon.utility.ElectromagneticPulse;
import components.weapon.utility.RepairBeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.geom.Point;
import teams.student.plotz.EnemyAnalysis;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.TestPlotzUnit;

import java.awt.*;
import java.util.ArrayList;

public class Distractor extends TestPlotzUnit {

    public Distractor(TestPlotz p) {
        super(p);
    }

    @Override
    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.STRIKER);
        setStyle(Style.SHARK);

        add(Missile.class);
        add(NarcissusMod.class);
        add(Shield.class);
    }

    @Override
    public void action() {
        super.action();
    }

    public void attack(Weapon w) {
        Unit enemy = getNearestCriticalFighter(this, getMaxRange());
        if (enemy == null) {
            enemy = getNearestEnemy();
        }
        if (enemy != null && w != null) {
            w.use(enemy);
        }
    }

    public void movement() {
        Point destination = EnemyAnalysis.getNearestGatherer(this);
        turnTo(destination);
        move();
    }
}

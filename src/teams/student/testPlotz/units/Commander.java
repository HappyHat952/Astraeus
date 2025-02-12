package teams.student.testPlotz.units;

import components.upgrade.Shield;
import components.weapon.utility.AntiMissileSystem;
import components.weapon.utility.CommandRelay;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.geom.Point;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.analysis.OverallAnalysis;
import teams.student.testPlotz.analysis.PlayerAnalysis;

import java.util.ArrayList;

import static teams.student.testPlotz.analysis.OverallAnalysis.getEnemy;

public class Commander extends Fighter{

    public Commander(TestPlotz p) {
        super(p);
    }

    public void design()
    {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.BOXY);


            add(CommandRelay.class);
        add(Shield.class);
    }
//    public void movement() {
//        ArrayList<Unit> allyFighters = OverallAnalysis.getAlly().getFighters();
//
//        for (Unit a: allyFighters) {
//        }
//
//        moveTo(partner);
//        getWeaponOne().use();
//
//    }

}

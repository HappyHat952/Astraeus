package teams.student.plotz.units;

import components.upgrade.Shield;
import components.weapon.utility.CommandRelay;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.plotz.Plotz;

public class Commander extends Fighter{

    public Commander(Plotz p) {
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

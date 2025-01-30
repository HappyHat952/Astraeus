package teams.student.testPlotz.units;

import components.upgrade.Shield;
import components.weapon.utility.CommandRelay;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.testPlotz.TestPlotz;

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
}

package teams.student.testPlotz.units;

import components.weapon.energy.Laser;
import components.weapon.utility.SpeedBoost;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.TestPlotzUnit;

public class Protector extends TestPlotzUnit {
    public Protector(TestPlotz t)
    {
       super(t);

    }

    @Override
    public void design() {
        setFrame(Frame.MEDIUM);

        setModel(Model.STRIKER);

        setStyle(Style.PINCER);
        add(SpeedBoost.class);
        add(Laser.class);
    }
}

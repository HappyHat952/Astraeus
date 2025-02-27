package teams.student.plotz.units;

import components.weapon.energy.Laser;
import components.weapon.utility.SpeedBoost;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.plotz.Plotz;
import teams.student.plotz.PlotzUnit;

public class Protector extends PlotzUnit {
    public Protector(Plotz t)
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

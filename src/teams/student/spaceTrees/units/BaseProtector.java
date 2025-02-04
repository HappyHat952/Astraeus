package teams.student.spaceTrees.units;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.spaceTrees.SpaceTrees;
import teams.student.spaceTrees.SpaceTreesUnit;

public class BaseProtector extends SpaceTreesUnit{

    public BaseProtector(SpaceTrees p) {
        super(p);
    }

    @Override
    public void design() {
        setFrame(Frame.HEAVY);
        setModel(Model.CRUISER);
        setStyle(Style.WEDGE);


        add(Autocannon.class);
        add(Laser.class);
        add(Shield.class);
        add(Plating.class);
    }

    @Override
    public void action()
    {
        Weapon a = getWeaponOne(); //autocannon (short range)
        Weapon l = getWeaponTwo(); // laser (mid range)
        moveTo(getHomeBase());
        if(!getNearestEnemiesInRadius(l.getMaxRange(), 3).isEmpty())
        {

            if(!getNearestEnemiesInRadius(a.getMaxRange(), 3).isEmpty()) {
                if (a.canUse()) {
                    a.use(getNearestEnemiesInRadius(a.getMaxRange(), 3).get(0));
                    setDestination(getNearestEnemiesInRadius(a.getMaxRange(), 3).get(0));
                }
            }

            else if (l.canUse())
            {
                l.use( getNearestEnemiesInRadius(l.getMaxRange(), 3).get(0));
                setDestination(getNearestEnemiesInRadius(l.getMaxRange(), 3).get(0));
            }
        }
    }
}

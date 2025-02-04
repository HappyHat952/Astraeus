package teams.student.spaceTrees.units;

import components.upgrade.Plating;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.spaceTrees.SpaceTrees;

public class ProtectorTank extends Tank{

    public ProtectorTank(SpaceTrees p) {

        super(p);

    }

    @Override
    public void design() {
        super.design();
        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.ORB);

        add(Autocannon.class);
        add(Autocannon.class);
        add(Plating.class);
        add(Plating.class);
    }

//    public void movement() {
//        if (((SpaceTrees) getPlayer()).countUnit(Gatherer.class)) {
//            moveTo(getHomeBase());
//        }
//        else
//        {
//            Unit enemy = getNearestEnemy();
//
//
//            // If the weapon is on cooldown
//            if(getWeaponOne().onCooldown())
//            {
//                turnTo(enemy);
//                turnAround();
//                move();
//                destinationX = getHomeBase().getCenterX();
//                destinationY = getHomeBase().getCenterY();
//            }
//            else
//            {
//                moveTo(enemy);
//                destinationX = enemy.getCenterX();
//                destinationY = enemy.getCenterY();
//            }
//        }

 //   }
}

package teams.student.testPlotz.units;

import components.upgrade.HeavyPlating;
import components.upgrade.Plating;
import components.weapon.kinetic.Autocannon;
import engine.states.Game;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.TestPlotzUnit;

public class Tank extends TestPlotzUnit {

    public Tank(TestPlotz p) {

        super(p);

    }

    public void design() {
        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.ORB);

        add(Autocannon.class);
        add(Plating.class);
        add(HeavyPlating.class);
    }

    public void action()

    {
        movement();
        // You can modify this method to have different behavior
        if (getWeaponOne().canUse())
        {
            attack(getWeaponOne());
        }



//        else if (getWeaponTwo().canUse())
//        {
//            attack(getWeaponTwo());
//        }

    }


    public void movement() {
//        if (getHomeBase().isDamaged())
//        {
//            moveTo(getRallyPoint());
//        }
//        if (getOpponent().getMyBase().isDamaged() && hasEnoughAllies())
//        {
//            moveTo(getCustomRallyPoint(cluster));
//        }
//        else if (!hasEnoughAllies()) {
//            moveTo(getCustomRallyPoint(cluster));
//        }
//        else
//        {
        //moveTo(getRallyPoint());
        Unit enemy = getNearestEnemy();
        moveTo(enemy);


//            // If the weapon is on cooldown
//            if(getWeaponOne().onCooldown())
//            {
//                turnTo(enemy);
//                turnAround();
//                move();
//
//            }
//            else
//            {
//                moveTo(enemy);
//            }

    }

}

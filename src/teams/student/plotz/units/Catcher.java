package teams.student.plotz.units;
import components.weapon.economy.Collector;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import teams.student.plotz.Plotz;
import teams.student.plotz.PlotzUnit;


public class Catcher extends PlotzUnit
{


    public Catcher(Plotz p)
    {
        super(p);
    }

    public void design()
    {
        setFrame(Frame.LIGHT);
        setModel(Model.TRANSPORT);
        setStyle(Style.BUBBLE);
        add(Collector.class);
    }


    public void action()
    {
        gatherResources();
        returnResources();
    }


    public void returnResources()
    {
//        if(!isEmpty())
//        {
        moveTo(getHomeBase());
        deposit();
//        }
    }


    public void gatherResources()
    {
        if (getDistance(getHomeBase()) > 200) {
            moveTo(getHomeBase());
        }
        if(getCargoPercent() < .5f) {
            Resource r = getNearestResource();
            if (r != null) {
                if (getHomeBase().getDistance(r) < 200) {
                    moveTo(r);
                    destinationX = r.getX();
                    destinationY = r.getY();
                    ((Collector) getWeaponOne()).use(r);
                } else {
                    destinationX = getHomeBase().getX();
                    destinationY = getHomeBase().getY();
                    moveTo(getHomeBase());
                }


            }
        }
    }


}

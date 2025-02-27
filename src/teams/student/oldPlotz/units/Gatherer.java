package teams.student.oldPlotz.units;
import components.weapon.economy.Collector;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.resource.Resource;
import org.newdawn.slick.geom.Point;
import teams.student.oldPlotz.OldPlotz;
import teams.student.oldPlotz.PlotzUnit;
import teams.student.oldPlotz.ResourceManager;

import java.util.ArrayList;

public class Gatherer extends PlotzUnit {
	public int timer = 0;
	public int dumpTimer;
	final private int TIME = 500;
	public Resource myResource;
	public boolean isAssigned;
	private boolean a;
	public Gatherer(OldPlotz p) {
		super(p);
	}

	public void design() {
		setFrame(Frame.LIGHT);
		setModel(Model.TRANSPORT);
		setStyle(Style.BUBBLE);
		add(Collector.class);
		myResource = null;
		isAssigned = false;
		dumpTimer = 0;
		a = false;
	}


	public void action() {
		if (isFull()) {
			returnResources();
		} else {
			gatherResources();
		}

	}

	public Point getFutureHomeBasePosition() {

		float distance = Math.abs(getHomeBase().getCenterX() - getX());


		float timeToBase = distance / getCurSpeed();


		float futureX = getHomeBase().getCenterX() + (getHomeBase().getSpeedX() * timeToBase);
		float futureY = getHomeBase().getCenterY();

		return new Point((int) futureX, (int) futureY);
	}


	public void returnResources() {
//		fix dump logic

//        turnTo(getHomeBase());
//        if (getDistance(getHomeBase()) < 3000) {
//            timer++;
//            if (timer > TIME) {
//				turnTo(getHomeBase());
//				dump();
//                dumpTimer = TIME;
//                timer = 0;
//
//            } else {
//				if (a) {
//					turnTo(getHomeBase());
//					a = false;
//				} else {
//					turnAround();
//					a = true;
//				}
//			}



		timer++;
            if (timer > TIME) {
				turnTo(getFutureHomeBasePosition());
				dump();
                dumpTimer = TIME;
                timer = 0;


            } else {
//				if (a) {
					moveTo(getFutureHomeBasePosition());
//					a = false;
//				} else {
//					turnAround();
//					move();
//					a = true;
//				}
//			}
        }

//		if(isFull())
//		{
//			moveTo(getHomeBase());
//			deposit();
//		}


	}


	public void gatherResources() {
		if (hasCapacity()) {
			if (dumpTimer > 0) {
				moveTo(getEnemyBase());
				dumpTimer--;
			} else {
				if (!isAssigned) {
					myResource = getNearestAvailable();
					ResourceManager.takenResources.add(myResource);
					isAssigned = true;
				}

				if (myResource == null) {
					moveTo(getHomeBase());
					destinationX = getHomeBase().getCenterX();
					destinationY = getHomeBase().getCenterY();
					myResource = getNearestAvailable();
				} else {
//                    if (!ResourceManager.isSafe(myResource)) {
//                        myResource = getNearestAvailable();
//                        ResourceManager.takenResources.remove(myResource);
//                    } else
//                    {
					moveTo(myResource);
					destinationX = myResource.getCenterX();
					destinationY = myResource.getCenterY();
					if (getDistance(myResource) <= getMaxRange() * .65f) {
						((Collector) getWeaponOne()).use(myResource);
					}
					if (myResource.isPickedUp()) {
						isAssigned = false;
//                        }
					}
				}
			}
		}


	}

	public Resource getNearestAvailable() {
		float nearestDistance = Float.MAX_VALUE;
		Resource nearestResource = null;
		ArrayList<Resource> resources = ResourceManager.getSafeResources();
//      ArrayList<Resource> resources = objects.resource.ResourceManager.getResources();


		if (resources == null) {
			return null;
		}
		for(Resource r : resources)
		{
			if(r.isInBounds() && !r.isPickedUp() && getDistance(r.getPosition()) < nearestDistance && !ResourceManager.takenResources.contains(r))
			{
				nearestResource = r;
				nearestDistance = getDistance(r.getPosition());
				destinationX = r.getCenterX();
				destinationY = r.getCenterY();
			}
		}
		return nearestResource;
	}



}






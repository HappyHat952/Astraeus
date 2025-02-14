package teams.student.testPlotz.units;


import components.upgrade.Shield;
import components.weapon.economy.Collector;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.resource.Resource;
import org.newdawn.slick.geom.Point;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.analysis.ResourceManager;
import teams.student.testPlotz.TestPlotzUnit;

import java.util.ArrayList;

public class Gatherer extends TestPlotzUnit
{

	public int timer = 0;
	public int dumpTimer;
	public Resource myResource;
	public boolean isAssigned;

	public Gatherer(TestPlotz p) {
		super(p);
	}

	public void design() {
		setFrame(Frame.LIGHT);
		setModel(Model.TRANSPORT);
		setStyle(Style.BUBBLE);
		add(Collector.class);
//		add(Shield.class);
		myResource = null;
		isAssigned = false;
		dumpTimer = 0;
	}


	public void action() {
		if (isFull()) {
			returnResources();
		} else {
			gatherResources();
		}

	}


	public void returnResources() {
//        moveTo(getHomeBase());
//        if (getDistance(getHomeBase()) < 3000) {
//            timer++;
//            if (timer > 240) {
//                dump();
//                dumpTimer = 500;
//                timer = 0;
//
//            }
//        }

		if (isFull()) {
			moveTo(getHomeBase());
			deposit();
		}

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
					if (!ResourceManager.isSafe(myResource)) {
						myResource = getNearestAvailable();
						ResourceManager.takenResources.remove(myResource);
					} else {
						moveTo(myResource);
						destinationX = myResource.getCenterX();
						destinationY = myResource.getCenterY();
//						if (getDistance(myResource) <= getMaxRange() * .65f) { prevents spinning
							((Collector) getWeaponOne()).use(myResource);
//						}
						if (myResource.isPickedUp()) {
							isAssigned = false;
						}
					}
				}
			}
		}


	}

	public Resource getNearestAvailable () {
		float nearestDistance = Float.MAX_VALUE;
		Resource nearestResource = null;
		ArrayList<Resource> resources = ResourceManager.getSafeResources();
		if (resources == null) {
			return null;
		}
		for (Resource r : resources) {
			if (r.isInBounds() && !r.isPickedUp() && getDistance(r.getPosition()) < nearestDistance && !ResourceManager.takenResources.contains(r)) {
				nearestResource = r;
				nearestDistance = getDistance(r.getPosition());
				destinationX = r.getCenterX();
				destinationY = r.getCenterY();
			}
		}
		return nearestResource;
	}

	public Point getDestination() {
		if (myResource != null) {
			return myResource.getPosition();
		} else {
			return getHomeBase().getPosition();
		}
	}

}

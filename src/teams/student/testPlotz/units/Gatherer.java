// NEW GATHERER

package teams.student.testPlotz.units;

import components.weapon.economy.Collector;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.resource.Resource;
import org.newdawn.slick.geom.Point;
import teams.student.testPlotz.TestPlotz;
import teams.student.testPlotz.TestPlotzUnit;
import teams.student.testPlotz.analysis.ResourceManager;

import java.util.ArrayList;

public class Gatherer extends TestPlotzUnit
{

	public int timer = 0;
	public int dumpTimer;
	public Resource myResource;
	private ArrayList<Resource> myResources;
	public boolean isAssigned;

	public Gatherer(TestPlotz p) {
		super(p);
	}

	public void design() {
		setFrame(Frame.LIGHT);
		setModel(Model.TRANSPORT);
		setStyle(Style.BUBBLE);
		add(Collector.class);

		myResource = null;
		myResources = new ArrayList<>();
		isAssigned = false;
		dumpTimer = 0;
	}

	public void throwResources() {
		float xVelocity = getSpeedX();
		float yVelocity = getSpeedY();

		float baseXVelocity = getHomeBase().getSpeedX();
		float baseYVelocity = getHomeBase().getSpeedY();

		float distance = getDistance(getHomeBase());

		float time = distance/yVelocity;



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
		throwResources();

		if (isFull()) {
			moveTo(getHomeBase());
			deposit();
		}

	}


	// ADD A CHECK FOR NULL< IF THERES NOT ENOUGH RESOURCES
	public void gatherResources() {
		if (hasCapacity()) {
			if (dumpTimer > 0) {
				moveTo(getEnemyBase());
				dumpTimer--;
			} else {
				if (myResource == null || myResources.isEmpty() || !ResourceManager.isSafe(myResource))
				{
					isAssigned = false;
					moveTo(getHomeBase());
					destinationX = getHomeBase().getCenterX();
					destinationY = getHomeBase().getCenterY();
				}
				if (!isAssigned) {

					if (getNearestAvailable()!= null)
					{
						myResource = getNearestAvailable();
						myResources.add(myResource);
						ResourceManager.takenResources.add(myResource);
					}

					// changed loop:
					int i = 0;
					Resource newBud = getBuddy(myResource);
					while ( i< 3 && newBud != null && myResource!= null)
					{
						myResources.add(newBud);
						ResourceManager.takenResources.add(newBud);
						newBud = getBuddy(myResource);
						i++;
					}


					isAssigned = true;
				}

				else {

						Resource currentDestinationResource;

						float nearestDistance = Float.MAX_VALUE;
						Resource nearestResource = null;
						for (Resource r : myResources) {
							if (r.isInBounds() && !r.isPickedUp() && getDistance(r.getPosition()) < nearestDistance) {
								nearestResource = r;
								nearestDistance = getDistance(r.getPosition());
								destinationX = r.getCenterX();
								destinationY = r.getCenterY();
							}
						}
						currentDestinationResource = nearestResource;
						if (currentDestinationResource == null) {
							currentDestinationResource = myResources.getFirst();
						}
						moveTo(currentDestinationResource);
						destinationX = currentDestinationResource.getCenterX();
						destinationY = currentDestinationResource.getCenterY();
						((Collector) getWeaponOne()).use(currentDestinationResource);
						if (currentDestinationResource.isPickedUp()) {
							myResources.remove(currentDestinationResource);
						}
						if (myResources.isEmpty()) {
							isAssigned = false;
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

	public Resource getBuddy(Resource re){
		float nearestDistance = Float.MAX_VALUE;
		Resource nearestResource = null;
		ArrayList<Resource> resources = ResourceManager.getSafeResources();
		if (resources == null) {
			return null;
		}
		for (Resource r: resources) {
			if (r.isInBounds() && !r.isPickedUp() && re.getDistance(r.getPosition()) < nearestDistance && !ResourceManager.takenResources.contains(r)) {
				nearestResource = r;
				nearestDistance = re.getDistance(r.getPosition());
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




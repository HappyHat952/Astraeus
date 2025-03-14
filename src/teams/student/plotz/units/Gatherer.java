// NEW GATHERER

package teams.student.plotz.units;

import components.weapon.economy.Collector;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.resource.Resource;
import org.newdawn.slick.geom.Point;
import teams.student.plotz.Plotz;
import teams.student.plotz.PlotzUnit;
import teams.student.plotz.analysis.ResourceManager;

import java.util.ArrayList;

import teams.student.plotz.analysis.ResourceManager;

public class Gatherer extends PlotzUnit
{

	public int timer = 0;
	public int dumpTimer;
	private final int TIME = 180;
	public Resource myResource;
	private ArrayList<Resource> myResources;
	public static ArrayList<Resource> thrown;
	public boolean isAssigned;

	public Gatherer(Plotz p) {
		super(p);
	}

	public static void setThrown()
	{
		thrown = new ArrayList<>();
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
		float xVel = getSpeedX();
		float yVel = getSpeedY();

		Point futureBase = getFutureHomeBasePosition();
		float futureBaseX = futureBase.getX();
		float futureBaseY = futureBase.getY();


		float gathererX = getCenterX();
		float gathererY = getCenterY();

		// gets direcion from the gatherer to the base
		float directionX = futureBaseX - gathererX;
		float directionY = futureBaseY - gathererY;

		// pythagorean to get magnitude of direction
		float magnitude = (float) Math.sqrt(directionX * directionX + directionY * directionY);
		if (magnitude != 0) {
			directionX /= magnitude;
			directionY /= magnitude;
		}

		// same thing but for velocity vector
		float velocityMagnitude = (float) Math.sqrt(xVel * xVel + yVel * yVel);
		float velocityDirX = 0;
		float velocityDirY = 0;
		if (velocityMagnitude != 0) {
			velocityDirX = xVel / velocityMagnitude;
			velocityDirY = yVel / velocityMagnitude;
		}


		// multiply each vector component, gets the amount of similarity there is (1 is completely aligned)
		float alignment = (directionX * velocityDirX) + (directionY * velocityDirY);
		int num = getCargo();

		moveTo(futureBase);

		if (getDistance(getHomeBase()) < 1800) {
			deposit();
		} else if (alignment > 0.98 && getDistance(getHomeBase()) < 2200 && getDistance(getHomeBase()) > 1800) {

			dump();
			myResources.clear();
			for (Resource r : getNearestResources(num, new ArrayList<Resource>())) {
				thrown.add(r);
			}
			myResource = null;
			isAssigned = false;
		}

	}

	private ArrayList<Resource> getNearestResources( int num, ArrayList<Resource> res)
	{
		//first, get the current nearest resource (do loop)
		int dist = Integer.MAX_VALUE;
		Resource near = null;
		for (Resource r: getPlayer().getAllResources())
		{
			if (res!= null && !res.contains(r) && getDistance(r)<dist)
			{
				dist = (int)getDistance(r);
				near = r;
			}
		}
		if (res!= null)
		{
			res.add(near);
		}


		if (num>0)
		{
			return getNearestResources(num - 1, res);
		}
		else {
			return res;
		}


		// if the number isn't 0, call itself and add nearest to loop.
	}

	public void action() {
		if (isFull()) {
			returnResources();
		} else {
			gatherResources();
		}

	}

	public Point getFutureHomeBasePosition() {
		float distance = getDistance(getHomeBase());
		float timeToBase = distance / getCurSpeed();


		float futureX = getHomeBase().getCenterX() + (getHomeBase().getSpeedX() * timeToBase);
		float futureY = getHomeBase().getCenterY() + (getHomeBase().getSpeedY() * timeToBase);

		return new Point((int) futureX, (int) futureY);
	}

	public void returnResources() {
		throwResources();
	}


	// ADD A CHECK FOR NULL< IF THERES NOT ENOUGH RESOURCES
	public void gatherResources() {
		if (hasCapacity()) {
			if (dumpTimer > 0) {
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
			if (r.isInBounds() && !r.isPickedUp() && getDistance(r.getPosition()) < nearestDistance &&!thrown.contains(r) && !ResourceManager.takenResources.contains(r)) {
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
			if (r.isInBounds() && !r.isPickedUp() && re!=null && re.getDistance(r.getPosition()) < nearestDistance && !thrown.contains(r)&& !ResourceManager.takenResources.contains(r)) {
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




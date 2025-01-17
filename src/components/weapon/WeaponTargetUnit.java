package components.weapon;

import objects.entity.Entity;

public abstract class WeaponTargetUnit extends WeaponTargetEntity
{
	public WeaponTargetUnit()
	{
		super();
	}
	
	abstract protected void animation(objects.entity.unit.Unit target, boolean isHit);
	abstract protected void activation(objects.entity.unit.Unit target, boolean isHit);
	
	protected void triggerActivationEffects(Entity target)
	{
		super.triggerActivationEffects(target);
	
		objects.entity.unit.Unit myTarget = (objects.entity.unit.Unit) target;
		boolean isHit = myTarget.rollToHit(this);
		
		//System.out.println(this + " my accuracy " + getAccuracy());
		
		animation(myTarget, isHit);
		activation(myTarget, isHit);

	}
	
	
	protected void animation(Entity target)
	{	
		// do nothing
	}
	
	protected void activation(Entity target)
	{
		// do nothing
	}
	

	

}

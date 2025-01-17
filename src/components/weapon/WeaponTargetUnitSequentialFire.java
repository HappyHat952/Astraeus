package components.weapon;

import objects.entity.Entity;

public abstract class WeaponTargetUnitSequentialFire extends WeaponTargetUnit
{
	
	protected int shotsLeft;
	protected int delayLeft;
	protected int delayTotal;
	
	protected objects.entity.unit.Unit myTarget;
	
	public WeaponTargetUnitSequentialFire(int delayTotal)
	{
		super();
		this.delayTotal = delayTotal;
	}

	protected void triggerActivationEffects(Entity target)
	{
		myTarget = (objects.entity.unit.Unit) target;
		shotsLeft = getNumShots();
		useCooldownTimer = getCooldown();
		playAudio();
	}
	
	public void update()
	{
		super.update();
		
		if(delayLeft > 0)
		{
			delayLeft--;
		}
		
		if(shotsLeft > 0 && delayLeft == 0)
		{
			boolean isHit = myTarget.rollToHit(this);
						
			//System.out.println(this + " my accuracy " + getAccuracy());

			
			activation(myTarget, isHit);
			animation(myTarget, isHit);
						
			shotsLeft--;
			
			if(shotsLeft == 0)
			{
				myTarget = null;
			}
			else
			{
				delayLeft = delayTotal;
			}
		}
	}
	
	protected void activation(objects.entity.unit.Unit target)
	{
		// do nothing
	}
	
	protected void animation(objects.entity.unit.Unit target)
	{
		// do nothing
	}

}

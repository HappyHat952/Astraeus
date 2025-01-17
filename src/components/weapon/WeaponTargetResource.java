package components.weapon;

import engine.Settings;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import org.newdawn.slick.Graphics;

public abstract class WeaponTargetResource extends Weapon
{
	/*************** Data ***************/

	private Resource lockedTarget;

	/*************** Constructor ***************/

	/*************** Every Frame ***************/

	public void update()
	{	
		updateTimers();

	;
		// Every frame make sure my target is still valid and in range, and that I can act, or break the lock
		if(lockedTarget != null && !lockedTarget.isPickedUp() && inRange(lockedTarget) && getOwner().canAct())
		{
			// Then check if I can actually fire - (includes a check for useLockTimer)
			if(canUse(lockedTarget))
			{
				triggerActivationEffects(lockedTarget);
				end();
			}
			
			// Keep rotating toward the target
			else if(!(getOwner() instanceof BaseShip) && rotateUser())
			{
				getOwner().rotate(getOwner().getAngleToward(lockedTarget.getCenterX(), lockedTarget.getCenterY()));
			}
	

		}
		
		// If not, abandon the attack
		else
		{
			end();
		}
	}
	
	public void render(Graphics g)
	{
		if(Settings.dbgShowWeaponAim && lockedTarget != null && inRange(lockedTarget))
		{
			g.setColor(getOwner().getPlayer().getColorPrimary().darker().darker());
			g.setLineWidth(1);
			g.drawLine(getOwner().getCenterX(), getOwner().getCenterY(), lockedTarget.getCenterX(), lockedTarget.getCenterY());
			g.resetLineWidth();
		}
	}

	/*************** Use Methods ***************/
	
	public boolean canUse(Resource target)
	{
		return super.canUse() && target != null && inRange(target);
	}
		
	public void use(Resource target)
	{
		if (canUse(target))
		{
			// Lock onto target and wait to fire.
			if(!(getOwner() instanceof BaseShip))
			{
				getOwner().turnTo(target);
			}
			useLockTimer = getUseTime();
			start(target);
		}
	}
	
	public void use()
	{
		getOwner().dbgMessage("Warning: called use() on + " + this + " without a target.");
	}
	
	protected void activation()
	{
		// No activation effect on self by default
	}
	
	protected void animation()
	{
		// No animation effect on self by default
	}
	
	public void start(Resource target)
	{
		super.start();
		lockedTarget = target; 
	}
	
	public void end()
	{
		super.end();
		lockedTarget = null;
	}
	
	abstract protected void animation(Resource target);
	abstract protected void activation(Resource target);

	protected void triggerActivationEffects(Resource target)
	{
		animation(target);
		activation(target);
		useCooldownTimer = getCooldown();
		playAudio();
	}
	
	public float getRangePercent(Resource target)
	{
		return getOwner().getDistance(target) / (float) getMaxRange();
	}
	
	public int getActualTravelTime(Resource target)
	{
		return (int) (getMaxTravelTime() * getRangePercent(target));
	}
	
	public int getMaxTravelTime()
	{
		return 1;
	}
	
}

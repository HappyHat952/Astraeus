package components.weapon;


import components.Component;
import components.DamageType;
import conditions.Condition;
import objects.GameObject;
import objects.entity.Entity;

public abstract class Weapon extends Component 
{



	/*************** Data ***************/

	protected int useCooldownTimer;
	protected int useLockTimer;
	protected boolean using;

	protected float damage;
	protected int numShots;
	protected int maxRange;
	protected int cooldown;
	protected int useTime;
	protected float accuracy;
	protected int maxTravelTime;
	protected int radius;
	protected int duration;
	protected int blockPenetration;
	protected String adjective;

	protected DamageType damageType;
	protected WeaponType weaponType;

	protected float useSlow;

	protected float USE_SLOW_NONE = .0f;
	protected float USE_SLOW_LIGHT_MINOR = .20f;
	protected float USE_SLOW_LIGHT_AVERAGE = .25f;
	protected float USE_SLOW_LIGHT_MAJOR = .30f;

	/*************** Constructor ***************/
	
	public Weapon()
	{
		super();

		damage = 0;
		maxRange = 0;
		numShots = 1;
		cooldown = 1;
		useTime = 1;
		accuracy = -1;
		radius = 0;
		duration = 0;
		maxTravelTime = 1;
		damageType = DamageType.TRUE;
		weaponType = WeaponType.UTILITY;
		mass = 0;
		blockPenetration = 0;
		adjective = "";
		useSlow = USE_SLOW_LIGHT_AVERAGE;
	}

	public abstract void applyMod();

	/*************** Information ***************/

	public float getBlockPenetration() 				{	return blockPenetration ;		}
	public int getMaxRange() 						{	return (int) (maxRange + getOwner().getMaxRangeBonus());		}
	public int getMinRange() 						{	return (int) (getMaxRange() * getOwner().getMinRangeMultiplier());					}
	public int getCooldown() 						{	return (int) (cooldown * getOwner().getCooldownTimeMultiplier());		}
	public int getUseTime()							{	return (int) (useTime * getOwner().getUseTimeMultiplier());			}
	public float getAccuracy()						{	return accuracy + getOwner().getAccuracyBonus();		}
	public int getNumShots()						{	return numShots;									}
	public int getMaxTravelTime()					{	return maxTravelTime;								}
	public int getRadius() 							{	return radius;										}
	public int getDuration()						{	return duration;									}
	public String getAdjective()					{	return adjective;		}

	public int getCooldownTimer()									{ 	return useCooldownTimer;		}
	public int getUseTimer()										{	return useLockTimer;			}
	public boolean onCooldown()										{	return getCooldownTimer() != 0;	}
	public Class<? extends Condition> getAppliedConditionType()		{ 	return null;					}

	public boolean appliesCondition()				{ 	return false;					}
	public WeaponType getWeaponType()				{	return weaponType;				}
	public DamageType getDamageType()				{	return damageType;				}

	public float getUseSlow()						{	return useSlow;					}
	public boolean hasAppliedCondition(objects.entity.unit.Unit u)		{	return false;					}
	public int getAppliedConditionTimeLeft(objects.entity.unit.Unit u)	{	return 0;						}

	public boolean rotateUser()						{	return true;	}

	public boolean inRange(GameObject e)
	{
		return getOwner().getDistance(e) >= getMinRange() && getOwner().getDistance(e) <= getMaxRange();
	}

	
	/*************** Every Frame ***************/
	
	public void update() 
	{	
		updateTimers();
		
		if(inUse() && canUse())
		{
			activation();
			animation();
			playAudio();
			end();
			useCooldownTimer = getCooldown();
		}
		
		// Cancel if interrupted by condition
		if(inUse() && !getOwner().canAct())
		{
			end();
		}
	}
	
	public void updateTimers()
	{
		if(getOwner().canAct() && useCooldownTimer > 0)
		{
			useCooldownTimer--;
		}
		if(getOwner().canAct() && useLockTimer > 0)
		{
			useLockTimer--;
		}
	}
	
	/*************** Use Methods ***************/
	
	public void use() 
	{		
		if (!inUse() && canUse())
		{
			start();
			useLockTimer = getUseTime();
		}
	}
	
	public void use(Entity e)
	{
		use();
	}
	
	public boolean canUse()
	{
		return getOwner() != null && getOwner().canAct() && useCooldownTimer == 0 && useLockTimer == 0;
	}
	
	public boolean canUse(Entity e)
	{
		return canUse();
	}
		
	public boolean inUse()
	{
		return using;
	}

	protected void start()
	{
		startConditions();
		using = true;
	}
	
	protected void end()
	{
		endConditions();
		using = false;
	}
	
	protected void startConditions() 
	{
//		getOwner().addCondition(new Use(mass));
	}

	protected void endConditions() 
	{
//		getOwner().removeAllConditions(Use.class);
	}
	
	public int getUseProgress()
	{
		return getUseTime() - getUseTimer();
	}
	
	public int getCooldownProgress()
	{
		return getCooldown() - getCooldownTimer();
	}
	
	/*************** Effects ***************/

	abstract protected void activation();
	abstract protected void animation();

	protected void playAudio()
	{	
	 
	}
	
	public String toString()
	{
		return getAdjective() + getName();
	}


	public  float getDamage()
	{
		return damage * getOwner().getPower();
	}

}

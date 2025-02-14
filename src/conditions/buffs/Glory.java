package conditions.buffs;

import animations.AnimationManager;
import animations.effects.AnimationOmen;
import conditions.Buff;

public class Glory extends Buff
{
	final float REPAIR_BONUS = .50f;
	final int TIME_REDUCTION = 1;

	public Glory(int duration)
	{
		super(duration);
		repair = REPAIR_BONUS;
	}

	public void updateFrame()
	{
//		AnimationManager.add(new AnimationOmen(getOwner()));
		getOwner().getConditions().reduceDebuffs(TIME_REDUCTION);

	}
}

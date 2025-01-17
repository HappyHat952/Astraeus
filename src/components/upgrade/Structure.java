package components.upgrade;

public class Structure extends Upgrade
{
	public static final int SIZE = 1;
	public static final int STRUCTURE = 350;
	public static final float HULL_REPAIR_EFFICIENCY = .20f;
	public static final float DEFENSE = .03f;

	public Structure()
	{
		super();
	}
	
	public void onAddition()
	{
		super.onAddition();
		getOwner().increaseMaxStructure(STRUCTURE);
		getOwner().increaseHullRepairEfficiency((HULL_REPAIR_EFFICIENCY));
		getOwner().addBlock(DEFENSE);

	}

}

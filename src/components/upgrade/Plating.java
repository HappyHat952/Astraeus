package components.upgrade;

public class Plating extends Upgrade
{
	public static final int SIZE = 1;
	public static final int PLATING = 400;
	public static final float DEFENSE = .08f;
	public static final int MASS = 6;

	public Plating()
	{
		super();
		mass = MASS;
	}
	
	public void onAddition()
	{
		super.onAddition();		
		getOwner().increaseMaxPlating(PLATING);		
		getOwner().addBlock(DEFENSE);
	}


}

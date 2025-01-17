package objects.entity.unit;

public enum Model
{	
	CRUISER, BASTION, ARTILLERY, STRIKER, TRANSPORT, PROTOTYPE;

	// Cruiser

	public final static int CRUISER_MASS = 10;
	public final static int CRUISER_STRUCTURE = 50;
	public final static float CRUISER_BLOCK = .05f;
	public final static float CRUISER_POWER = .10f;
	public final static float CRUISER_ACCURACY = .10f;

	// Bastion
	public final static int BASTION_MASS = 10;
	public final static int BASTION_STRUCTURE = 200;
	public final static float BASTION_BLOCK = .10f;

	// Destroyer
	public final static int ARTILLERY_MASS = 10;
	public final static float ARTILLERY_POWER = .20f;
	public final static int ARTILLERY_RANGE = 50;

	// Transport
	public final static int TRANSPORT_MASS = 10;
	public final static int TRANSPORT_CAPACITY = 2;

	// Striker
	public final static int STRIKER_MASS = 5;
	public final static float STRIKER_ACCURACY = .25f;

	// Striker
	public final static int PROTOTYPE_MASS = 15;
	public final static int PROTOTYPE_COMPONENTS = 1;


	public int getMass()
	{
		switch (this)
		{
			case CRUISER: 	return CRUISER_MASS;
			case BASTION: 	return BASTION_MASS;
			case ARTILLERY: return ARTILLERY_MASS;
			case STRIKER: 	return STRIKER_MASS;
			case TRANSPORT: return TRANSPORT_MASS;
			case PROTOTYPE: return PROTOTYPE_MASS;
			default: 		return Integer.MAX_VALUE;
		}
	}
	
	public String toString()
	{
		switch (this)
        {
            case CRUISER: 	return "Cruiser";
            case BASTION: 	return "Bastion";
            case ARTILLERY: return "Artillery";
            case STRIKER: 	return "Striker";
            case TRANSPORT: return "Transport";
			case PROTOTYPE: return "Prototype";
			default: 		return "?";
        }
	}

	public int getStructure()
	{
		switch(this)
		{
			case CRUISER:	return CRUISER_STRUCTURE;
			case BASTION:	return BASTION_STRUCTURE;
			default:		return 0;
		}
	}

	public float getBlock()
	{
		switch(this)
		{
			case CRUISER:	return CRUISER_BLOCK;
			case BASTION:	return BASTION_BLOCK;
			default:		return 0;
		}
	}


	public float getAccuracy()
	{
		switch(this)
		{
			case CRUISER:	return CRUISER_ACCURACY;
			case STRIKER:	return STRIKER_ACCURACY;
			default:		return 0;
		}
	}

	public float getPower()
	{
		switch(this)
		{
			case CRUISER:	return CRUISER_POWER;
			case ARTILLERY:	return ARTILLERY_POWER;
			default:		return 0;
		}
	}

	public int getRange()
	{
		switch(this)
		{
			case ARTILLERY:	return ARTILLERY_RANGE;
			default:		return 0;
		}
	}

	public int getComponents()
	{
		switch(this)
		{
			case PROTOTYPE: return PROTOTYPE_COMPONENTS;
			default:		return 0;
		}
	}

	public int getCapacity()
	{
		switch(this)
		{
			case TRANSPORT: return TRANSPORT_CAPACITY;
			default:		return 0;
		}
	}
	
	

}

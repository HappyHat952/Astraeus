package components.mod.offense;

import components.mod.Mod;

public class ArtemisMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 6;

	public static final int MISSLE_RANGE_BONUS = 200;

	public static String NAME = "Artemissile";

	public ArtemisMod()
	{
		super();
		mass = MASS;
		name = NAME;

	}

}

package components.mod.offense;

import components.mod.Mod;

public class PoseidonMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;

	public static final float LASER_PUSH_EFFECT = 20f;
	public static final int LASER_PUSH_DURATION = 20;
	public static final float LASER_USE_TIME_SCALAR = 1.00f;
	public static final float LASER_COOLDOWN_SCALAR = 1.00f;

	public static String NAME = "Tidal Blast";

	public PoseidonMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}

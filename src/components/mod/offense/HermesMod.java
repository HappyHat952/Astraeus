package components.mod.offense;

import components.mod.Mod;
import components.weapon.explosive.Missile;

public class HermesMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;

	public static float USE_TIME_SCALAR = .8f;
	public static int REDUCED_USE_TIME = (Math.round(Missile.USE_TIME * USE_TIME_SCALAR));
	public static int INCREASED_COOLDOWN = REDUCED_USE_TIME;

	public static String NAME = "Swiftlight Missile";

	public HermesMod()
	{
		super();
		mass = MASS;
		name = NAME;

	}

}

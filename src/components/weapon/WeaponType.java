package components.weapon;

import engine.Values;
import org.newdawn.slick.Color;

public enum WeaponType
{
	ENERGY, KINETIC, EXPLOSIVE, UTILITY, RESOURCE;

	public Color getColor()
	{
		if(this == ENERGY)
		{
			return Values.COLOR_SHIELD;
		}
		else if(this == KINETIC)
		{
			return Values.COLOR_PLATING;
		}
		else if(this == EXPLOSIVE)
		{
			return Values.COLOR_STRUCTURE;
		}
		else if(this == RESOURCE)
		{
			return new  Color(204, 164, 148);
		}
		else
		{
			return Values.COLOR_UTILITY;
		}
	}
}

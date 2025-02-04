package teams.student.plotz;

import components.weapon.Weapon;
import objects.GameObject;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import player.Player;

public abstract class PlotzUnit extends Unit
{

	protected float destinationX;
	protected float destinationY;
	public PlotzUnit(Player p)
	{
		super(p);
		destinationX = 0;
		destinationY = 0;
	}
	
	public Plotz getPlayer()
	{
		return (Plotz) super.getPlayer();
	}

	public void setDestination (float x, float y)
	{
		destinationX = x;
		destinationY = y;

	}
	public void setDestination ( GameObject o)
	{
		destinationX = o.getX();
		destinationY = o.getY();

	}

	public void setDestination ( Point p)
	{
		destinationX = p.getX();
		destinationY = p.getY();

	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.drawLine(x, y, destinationX, destinationY);
	}
	public void action() 
	{
		attack(getWeaponOne());
		attack(getWeaponTwo());
		movement();
	}
		
	public void attack(Weapon w)
	{
		Unit enemy = getNearestEnemy();

		if(enemy != null && w != null)
		{
			w.use(enemy);	
		}
	}
		
	public void movement()
	{
		Unit enemy = getNearestEnemy();

		if(enemy != null)
		{		
			if(getDistance(enemy) > getMaxRange())
			{
				moveTo(enemy);
			}
			else
			{
				turnTo(enemy);
				turnAround();
				move();
			}
		}
	}
}

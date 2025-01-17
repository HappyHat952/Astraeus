package teams.student.plotz;

import components.weapon.Weapon;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import player.Player;

public abstract class PlotzUnit extends Unit
{	
	public PlotzUnit(Player p)
	{
		super(p);
	}
	
	public Plotz getPlayer()
	{
		return (Plotz) super.getPlayer();
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
	
	public void draw(Graphics g) 
	{

	}
}

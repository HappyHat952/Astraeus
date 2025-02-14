package teams.student.simple;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.simple.units.Fighter;
import teams.student.simple.units.Gatherer;
import teams.student.simple.units.Miner;

public class Simple extends Player
{	
	public void setup()
	{		
		setName("Simple");
		setTeamImage("src/teams/student/simple/teamLogo.png");
		setTitle("A Copy Of My Team");

		setColorPrimary(170, 170, 170);
		setColorSecondary(200, 200, 50);
		setColorAccent(255, 255, 255);

	}
	
	public void strategy() 
	{
		if(getFleetValueUnitPercentage(Gatherer.class) < .2f)
		{
			buildUnit(new Gatherer(this));
		}
		else if(getFleetValueUnitPercentage(Miner.class) < .2f)
		{
			buildUnit(new Miner(this));
		}
		else
		{
			buildUnit(new Fighter(this));
		}		
	}
			
	public void draw(Graphics g) 
	{
		
	}
	
}

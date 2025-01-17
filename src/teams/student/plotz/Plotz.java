package teams.student.plotz;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.plotz.units.Fighter;
import teams.student.plotz.units.Gatherer;
import teams.student.plotz.units.Miner;

public class Plotz extends Player
{	
	public void setup()
	{		
		setName("Plotz");
		setTeamImage("src/teams/student/myTeam/plotzLogo.png");
		setTitle("plotzing to success");

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

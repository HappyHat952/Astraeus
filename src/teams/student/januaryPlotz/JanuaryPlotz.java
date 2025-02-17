package teams.student.januaryPlotz;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.januaryPlotz.analysis.OverallAnalysis;
import teams.student.januaryPlotz.units.*;

public class JanuaryPlotz extends Player
{
	OverallAnalysis overall;
	public void setup()
	{		
		setName("Plotz");
		setTeamImage("src/teams/student/januaryPlotz/plotzLogo.png");
		setTitle("The Plotzful Four");

		setColorPrimary(96, 130, 182);
		setColorSecondary(201, 15, 2);
		setColorAccent(0, 0, 0);

		overall = new OverallAnalysis(this);

	}
	
	public void strategy() 
	{
		if (OverallAnalysis.getCurrentStage() == OverallAnalysis.BUILD)
		{
			buildUnits(.25f,.25f,.3f, .2f,0f);
		}
		else if (OverallAnalysis.getCurrentStage() == OverallAnalysis.FIGHT)
		{
			buildUnits(.12f, .12f, .56f, .2f,0f);
		}


		overall.update();
	}

	private void buildUnits ( float gather, float miner, float fighter, float tank, float healer)
	{
		if (getFleetValueUnit(Healer.class)< 4)
		{
			buildUnit(new Healer(this));
		}
		else if (getFleetValueUnitPercentage(Gatherer.class)< gather)
		{
			buildUnit(new Gatherer(this));
		}
		else if (getFleetValueUnitPercentage(Miner.class)< miner && !getAllNodes().isEmpty())
		{
			buildUnit(new Miner(this));
		}
		else if (getFleetValueUnitPercentage(Fighter.class)< fighter)
		{
			buildUnit(new Fighter(this));
		}
		else if (getFleetValueUnitPercentage(Tank.class)< tank)
		{
			buildUnit(new Tank(this));
		}
		else {
			buildUnit(new Fighter(this));
		}
		}

			
	public void draw(Graphics g) 
	{
		OverallAnalysis.draw(g);
	}
	
}

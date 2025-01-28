package teams.student.testPlotz;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.testPlotz.analysis.OverallAnalysis;
import teams.student.testPlotz.units.*;

public class TestPlotz extends Player
{
	OverallAnalysis overall;
	public void setup()
	{		
		setName("Test Plotz");
		setTeamImage("src/teams/student/testPlotz/plotzLogo.png");
		setTitle("Team");

		setColorPrimary(23, 23, 170);
		setColorSecondary(23, 200, 23);
		setColorAccent(255, 23, 255);

		overall = new OverallAnalysis(this);

	}
	
	public void strategy() 
	{
		if (OverallAnalysis.getCurrentStage() == OverallAnalysis.BUILD)
		{
			buildUnits(.2f,.2f,.4f, .2f,0f);
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

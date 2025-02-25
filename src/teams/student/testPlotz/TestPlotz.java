package teams.student.testPlotz;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.testPlotz.analysis.BlockManager;
import teams.student.testPlotz.analysis.OverallAnalysis;
import teams.student.testPlotz.analysis.ResourceManager;
import teams.student.testPlotz.units.*;

public class TestPlotz extends Player
{
	OverallAnalysis overall;
	BlockManager blocks;
	public void setup()
	{		
		setName("Plotz");
		setTeamImage("src/teams/student/testPlotz/plotzLogo.png");
		setTitle("The Plotzful Four");

		setColorPrimary(96, 130, 182);
		setColorSecondary(201, 15, 2);
		setColorAccent(0, 0, 0);

		overall = new OverallAnalysis(this);
		blocks = new BlockManager(this);

	}
	
	public void strategy() 
	{
		if (!overall.getEnemy().getPlayer().equals(getOpponent()))
		{
			overall = new OverallAnalysis(this);
		}
		if (OverallAnalysis.getCurrentStage() == OverallAnalysis.BUILD)
		{
			buildUnits(.25f,.25f,.3f, .2f,0f);
		}
		else if (OverallAnalysis.getCurrentStage() == OverallAnalysis.FIGHT)
		{
			buildUnits(.12f, .12f, .51f, .25f,0f);
		}


		overall.update();
		blocks.update();
	}

	private void buildUnits ( float gather, float miner, float fighter, float tank, float healer)
	{
		if (getFleetValueUnit(Distractor.class)< 1) {
			buildUnit(new Distractor(this));
		}
//		if (getFleetValueUnit(Raider.class)<15 && OverallAnalysis.getCurrentStage() == OverallAnalysis.BUILD)
//		{
//			buildUnit(new Raider(this));
//		}
		else if (getFleetValueUnit(Healer.class)< 2) {
			buildUnit(new Healer(this));
		}
		if (getFleetValueUnitPercentage(Tank.class)< tank)
		{
			buildUnit(new Tank(this));
		}
//		else if (getFleetValueUnit(Commander.class)< 1) {
//			buildUnit(new Commander(this));
//		}
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
//			if (OverallAnalysis.getAlly().hasRelay())
//			{
				buildUnit(new Fighter(this));
//			}
//			else
//			{
//				buildUnit(new Commander(this));
//			}

		}

		else {
			buildUnit(new Fighter(this));
		}
		}

			
	public void draw(Graphics g) 
	{
		blocks.draw(g);
		OverallAnalysis.draw(g);
		ResourceManager.draw(g);
		if(getFleetValueUnit(Raider.class)>0 && Raider.getRally()!= null)
		{
			g.setColor(Color.orange);
			g.drawString("Raider Rally", Raider.getRally().getX(), Raider.getRally().getY());
		}
	}
	
}

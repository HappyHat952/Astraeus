package teams.student.plotz;

import objects.entity.unit.Unit;
import objects.resource.Resource;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import player.Player;
import teams.student.plotz.analysis.BlockManager;
import teams.student.plotz.analysis.OverallAnalysis;
import teams.student.plotz.analysis.ResourceManager;
import teams.student.plotz.units.*;

import java.util.ArrayList;

public class Plotz extends Player
{
	private static OverallAnalysis overall;
	private static BlockManager blocks;
	public void setup()
	{		
		setName("Plotz");
		setTeamImage("src/teams/student/plotz/plotzLogo.png");
		setTitle("The Plotzful Four");

		setColorPrimary(96, 130, 182);
		setColorSecondary(201, 15, 2);
		setColorAccent(0, 0, 0);

		overall = new OverallAnalysis(this);
		blocks = new BlockManager(this);

		Raider.setGatherer();
		Gatherer.setThrown();

	}

	public static BlockManager getBlocks(){ return blocks;}
	public static OverallAnalysis overall(){ return overall;}
	
	public void strategy() 
	{
		if (!overall.getEnemy().getPlayer().equals(getOpponent()))
		{
			overall = new OverallAnalysis(this);
		}
		if (OverallAnalysis.getCurrentStage() == OverallAnalysis.BUILD)
		{
			//buildUnits(.25f,.25f,.3f, .2f,0f);
			buildUnits(.3f,.3f,.3f,.2f,0f);
		}
		else if (OverallAnalysis.getCurrentStage() == OverallAnalysis.FIGHT)
		{
			buildUnits(.12f, .12f, .51f, .25f,0f);
		}
		else if (OverallAnalysis.getCurrentStage() == OverallAnalysis.FINAL)
		{
			buildUnits(0f,0f, .9f, .1f, 0);
		}


		overall.update();
		blocks.update();
	}

	private void buildUnits ( float gather, float miner, float fighter, float tank, float healer)
	{
		if (getFleetValueUnit(Distractor.class)< 1) {
			buildUnit(new Distractor(this));
		}
		else if (getFleetValueUnit(Raider.class)< 20 && OverallAnalysis.getCurrentStage() == OverallAnalysis.BUILD) {
			buildUnit(new Raider(this));
		}
		if (getFleetValueUnitPercentage(Tank.class)< tank)
		{
			buildUnit(new Tank(this));
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

		else {
			buildUnit(new Fighter(this));
		}
		}

			
	public void draw(Graphics g) 
	{
		blocks.draw(g);
		OverallAnalysis.draw(g);
		ResourceManager.draw(g);

		for (Unit u: getMyUnits())
		{
			u.draw(g);
		}
		addMessage(""+Raider.getGatherer());

		for (Unit gatherer : OverallAnalysis.getAlly().getMyGathererUnts()) {
			Point futurePosition = ((Gatherer) gatherer).getFutureHomeBasePosition();

			// line
			g.setColor(Color.red);
			g.drawLine(gatherer.getX(), gatherer.getY(), futurePosition.getX(), futurePosition.getY());

			// future pos
			g.setColor(Color.blue);
			g.fillOval(futurePosition.getX(), futurePosition.getY(), 6, 6);
		}

		for (Resource r: Gatherer.thrown) {
			g.setColor(Color.pink);
			g.fillOval(r.getX(), r.getY(), 50, 50);
		}
		addMessage(String.valueOf(Gatherer.thrown.size()));

	}
	
}

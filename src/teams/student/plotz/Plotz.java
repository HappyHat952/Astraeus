package teams.student.plotz;

import engine.states.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.plotz.units.Catcher;
import teams.student.plotz.units.Fighter;
import teams.student.plotz.units.Gatherer;
import teams.student.plotz.units.Miner;
import objects.entity.node.Node;
import objects.resource.Resource;
import teams.student.plotz.Analysis;

import java.util.ArrayList;

import static teams.student.plotz.ResourceManager.*;

public class Plotz extends Player
{

	private ArrayList<Resource> safeResources;
	private ArrayList<Node> safeNodes;
	public static String strategy;
	public boolean start;

	public void setup()
	{		
		setName("Plotz");
		setTeamImage("src/teams/student/plotz/plotzLogo.png");
		setTitle("plotzing to success");

		setColorPrimary(170, 170, 170);
		setColorSecondary(200, 200, 50);
		setColorAccent(255, 255, 255);

		start = true;

		safeResources = getSafeResources();
		safeNodes = getSafeNodes();

		AllyAnalysis.resetVariables();
		EnemyAnalysis.resetVariables();

		AllyAnalysis.setPlayer(this);
		EnemyAnalysis.setPlayer(getOpponent());

		Analysis.analyzePlayers(this);
		Analysis.analyzePlayers();
	}


	public static String getStrategy()
	{
		return strategy;
	}

	public void strategy()
	{
//
//		if(countMyUnits(Catcher.class)<2) {
//			buildUnit(new Catcher(this));
//		} else
			if (getFleetValueUnitPercentage(Gatherer.class) < .20f) {
			buildUnit(new Gatherer(this));
		} else if (getFleetValueUnitPercentage(Miner.class) < .20f) {
			buildUnit(new Miner(this));
		} else {
			buildUnit(new Fighter(this));
		}
		// AGAINST SMALL ATTACK
		// identify where attack is happening
		// pool up there

		Analysis.setGroupSize(7);
//		BUILD RESOURCES:
//		 when we have fewer resources
//		 when we are going against healer
//		 at the very beginning
//		if (Game.getTime() > 600*60 )
//		{
//			strategy = "tooLateToBuild";
//			PlotzUnit.setRallyPoint((float) 1f);
//			buildUnits(0f,0f,0.85f, 0.1f, 0f);
//		}
//
//		else if (Game.getTime() <200*60)
//		{
//			strategy = "build";
//			PlotzUnit.setRallyPoint((float) 0.1);
//			buildUnits(0.4f,0.4f,0.1f,0.1f,0);
//		}
//		else {
//			strategy = "general fight";
//			PlotzUnit.setRallyPoint(1f);
//			buildUnits(0.2f, 0.2f, 0.4f, 0.2f, 0.f);
//		}
		//DEFEND MINERS AND GATHERERS:
		// when skirmishers are coming
		// also heavily protect the base

		// AGAINST BIG ARMY
		// need skirmishers (break their economy)
		// bigger enemies and mods (determine later
		// few baits

		// AGAINST PULLERS
//		if (EnemyAnalysis.hasPullers() && strategy.equals("generalFight"))
//		{
//			Analysis.setGroupSize(10);
//		}
//		else if (getMineralsMined()> getOpponent().getMineralsMined()*1.2f)
//		{
//			Analysis.setGroupSize(8);
//		}
//		else
//		{
//			Analysis.setGroupSize(7);
//		}
//		manageHealers.assign();
	}



	public void draw(Graphics g) {
		// Displays math method information (not fully finished yet)

		Analysis.analyzePlayers();
		Analysis.printNewAnalysis(this, 5);

		addMessage(String.valueOf(takenResources.size()));
		if (Game.getTime() % 120 == 0) {
			Analysis.analyzePlayers();
			safeResources = getSafeResources();
			safeNodes = getSafeNodes();
		}
		if (!takenResources.isEmpty()) {
			for (Resource r: takenResources) {
				if (r != null && !r.isPickedUp()) {
					g.setColor(Color.yellow);
					g.fillOval(r.getCenterX(), r.getCenterY(), 10, 10);
				}
			}
		}
		if (safeResources != null) {
			this.addMessage("Safe resources " + safeResources.size(), Color.blue);
			for (Resource r : safeResources) {
				g.setColor(Color.green);
				g.fillOval(r.getCenterX(), r.getCenterY(), 30, 30);
			}
		}
		if (safeNodes != null) {
			this.addMessage("Safe nodes " + safeNodes.size(), Color.blue);
			for (Node n : safeNodes) {
				g.setColor(Color.green);
				g.drawOval(n.getCenterX(), n.getCenterY(), 80, 80);
			}
		}
//		if (EnemyAnalysis.getAvgFighterLocation()!= null && PlotzUnit.getEnemyFighter() != null && PlotzUnit.getAllyBuilder() != null)
//		{
//			g.drawLine(PlotzUnit.getEnemyFighter().getX(), PlotzUnit.getEnemyFighter().getY(), PlotzUnit.getAllyBuilder().getX(), PlotzUnit.getAllyBuilder().getY());
//			g.setColor( Color.green);
//			g.fillOval(PlotzUnit.getRallyPoint().getX(), PlotzUnit.getRallyPoint().getY(), 40, 40);
//		}

//		this.addMessage("Average Fleet Size: " + (int) Analysis.getAverageFleetSize(this));
//		this.addMessage("PREDICTED WIN: "+Analysis.getStateOfGame(this));
//		this.addMessage("OPPONENT STATS", Color.white);
//		this.addMessage("Strategy: " + Analysis.getEnemyStrategy(getOpponent()), Color.magenta);
//		this.addMessage("Average Speed: " + (int) Analysis.getAverageMaxSpeed(getOpponent()), Color.red);
//		this.addMessage("Fighter Percentage: " + (int)Analysis.getFighterPercentage(getOpponent()) + "%", Color.red);
//		this.addMessage("Team Concentration: " + (int) Analysis.getShipConcentration(getOpponent()), Color.red);
//		this.addMessage("Damage: "+ (int) getDamageTaken(), Color.blue);
//		this.addMessage("resources: "+ getMineralsMined() , Color.blue);


		//lines on graph.

		//draws average FIGHTING enemy location
		/*float Oppx = Analysis.getAverageFighterX(getOpponent());
		g.setColor( getOpponent().getColorPrimary());
		g.drawLine(Oppx, Game.getMapTopEdge(), Oppx, Game.getMapBottomEdge());

		//draws NEAREST enemy location
		Oppx = Analysis.getNearestX(getOpponent());
		g.setColor( getOpponent().getColorPrimary());
		g.drawLine(Oppx, Game.getMapTopEdge(), Oppx, Game.getMapBottomEdge());*/
	}

}

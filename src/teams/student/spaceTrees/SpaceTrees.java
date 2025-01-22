package teams.student.spaceTrees;

import engine.states.Game;
import objects.entity.node.Node;
import objects.resource.Resource;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.spaceTrees.analysis.AllyAnalysis;
import teams.student.spaceTrees.analysis.Analysis;
import teams.student.spaceTrees.analysis.EnemyAnalysis;
import teams.student.spaceTrees.units.*;

import java.util.ArrayList;

import static teams.student.spaceTrees.ResourceManager.getSafeNodes;
import static teams.student.spaceTrees.ResourceManager.getSafeResources;
import static teams.student.spaceTrees.analysis.Analysis.printNewAnalysis;

public class SpaceTrees extends Player {
	public boolean start;
	public static String strategy;

	public ResourceManager resman;
	private ArrayList<Resource> safeResources = getSafeResources();
	private ArrayList<Node> safeNodes = getSafeNodes();
	ManageHealer manageHealers;

	int totalNodeNum;

	private boolean firstTime;

	public void setup() {
		setName("Space Trees");
		setTeamImage("src/teams/student/spaceTrees/spaceTreeLogo.png");
		setTitle("Buggy but Better");

		start = true;
		setColorPrimary(51, 104, 67);
		setColorSecondary(198, 175, 49);
		setColorAccent(95, 158, 77);

		AllyAnalysis.resetVariables();
		EnemyAnalysis.resetVariables();

		AllyAnalysis.setPlayer(this);
		EnemyAnalysis.setPlayer(getOpponent());

		Analysis.analyzePlayers(this);
		Analysis.analyzePlayers();
		manageHealers = new ManageHealer(this);
		firstTime = true;
	}

	public static String getStrategy()
	{
		return strategy;
	}

	public void strategy()
	{

		if (firstTime)
		{
			totalNodeNum = getAllNodes().size();
			firstTime = false;
		}
		// AGAINST SMALL ATTACK
		// identify where attack is happening
		// pool up there

		Analysis.setGroupSize(7);
		//BUILD RESOURCES:
			// when we have fewer resources
			// when we are going against healer
			// at the very beginning
		if (Game.getTime() > 600*60 )
		{
			strategy = "tooLateToBuild";

			buildUnits(0f,0f,0.8f, .2f, 0f);

			if(AllyAnalysis.getFighterSize()>EnemyAnalysis.getFighterSize()*1.2)
			{
				SpaceTreesUnit.setRallyPoint((float) 0.9f);
				SpaceTreesUnit.setSpreadValue(150);
			}
			else if (AllyAnalysis.getFighterSize()>EnemyAnalysis.getFighterSize())
			{
				SpaceTreesUnit.setRallyPoint((float)0.5f);
				SpaceTreesUnit.setSpreadValue(100);
			}
			else
			{
				SpaceTreesUnit.setRallyPoint(0f);
				SpaceTreesUnit.setSpreadValue(20);
			}

		}
		else {
			strategy = "build";
			buildUnits(.2f,.2f,.3f, 0.3f, 0f);
		}





//		else if (getAllNodes().size()> (int)(.35f *totalNodeNum))
//		{
//			strategy = "build";
//			SpaceTreesUnit.setSpreadValue(500);
//			SpaceTreesUnit.setRallyPoint((float) 0.1);
//			buildUnits(0.4f,0.4f,0.1f,0.1f,0);
//		}
//		else {
//			strategy = "general fight";
//			SpaceTreesUnit.setSpreadValue(300);
//			SpaceTreesUnit.setRallyPoint(1f);
//			buildUnits(0.2f, 0.2f, 0.3f, 0.3f, 0.f);
//		}

		//DEFEND MINERS AND GATHERERS:
			// when skirmishers are coming
			// also heavily protect the base

		// AGAINST BIG ARMY
			// need skirmishers (break their economy)
			// bigger enemies and mods (determine later
			// few baits

		// AGAINST PULLERS

		if(EnemyAnalysis.hasPullers())
		{
			Analysis.setGroupSize(10);
		}
		manageHealers.assign();
	}

	private void buildUnits ( float gather, float miner, float fighter, float tank, float healer)
	{
		if (getFleetValueUnitPercentage(Gatherer.class)< gather)
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
		else if (getFleetValueUnitPercentage(Healer.class)< healer)
		{
			buildUnit(new Healer(this, manageHealers));
		}
		else {
			if (EnemyAnalysis.getFightersIsCluster())
			{
				buildUnit(new Fighter (this, "missile"));
			}
			else
			{
				buildUnit(new Fighter(this));
			}
		}
	}


	public void draw(Graphics g) {
		// Displays math method information (not fully finished yet)

		Analysis.analyzePlayers();
		printNewAnalysis(this, 5);



		if (EnemyAnalysis.getAvgFighterLocation()!= null && SpaceTreesUnit.getEnemyFighter() != null && SpaceTreesUnit.getAllyBuilder() != null)
		{
			g.setColor(Color.red);
			g.setLineWidth(20);
			g.drawLine(SpaceTreesUnit.getEnemyFighter().getX(), SpaceTreesUnit.getEnemyFighter().getY(), SpaceTreesUnit.getAllyBuilder().getX(), SpaceTreesUnit.getAllyBuilder().getY());
			g.setColor( Color.green);
			g.setLineWidth(2);
			g.fillOval(SpaceTreesUnit.getRallyPoint().getX(), SpaceTreesUnit.getRallyPoint().getY(), 40, 40);

			g.setColor(Color.yellow);
			//g.fillOval(SpaceTreesUnit.getCustomRallyPoint(0).getX(), SpaceTreesUnit.getCustomRallyPoint(0).getY(), 40, 40 );
			g.fillOval(SpaceTreesUnit.getCustomRallyPoint(-1).getX(), SpaceTreesUnit.getCustomRallyPoint(-1).getY(), 40, 40 );
			g.fillOval(SpaceTreesUnit.getCustomRallyPoint(1).getX(), SpaceTreesUnit.getCustomRallyPoint(1).getY(), 40, 40 );
		}

		// Displays math method information (not fully finished yet)
		if (Game.getTime() % 120 == 0) {
			Analysis.analyzePlayers();
			safeResources = getSafeResources();
			safeNodes = getSafeNodes();
		}
		if (safeResources != null) {
			this.addMessage("Safe resources" + safeResources.size(), Color.blue);
			for (Resource r : safeResources) {
				g.setColor(Color.green);
				g.fillOval(r.getCenterX(), r.getCenterY(), 30, 30);
				g.drawString(String.valueOf(r.getCurSpeed()), r.getX(), r.getY());
			}
		}
		if (safeNodes != null) {
			this.addMessage("Safe nodes" + safeNodes.size(), Color.blue);
			for (Node n : safeNodes) {
				g.setColor(Color.green);
				g.drawOval(n.getCenterX(), n.getCenterY(), 80, 80);
			}
		}

//		this.addMessage("Average Fleet Size: " + (int) Analysis.getAverageFleetSize(this));
//		this.addMessage("PREDICTED WIN: "+Analysis.getStateOfGame(this));
//		this.addMessage("OPPONENT STATS", Color.white);
//		this.addMessage("Strategy: "+Analysis.getEnemyStrategy(getOpponent()), Color.magenta);
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

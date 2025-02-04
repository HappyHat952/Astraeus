package teams.student.bartlebyFanClub;

import org.newdawn.slick.Graphics;

import player.Player;
import teams.student.bartlebyFanClub.units.*;

public class BartlebyFanClub extends Player
{
	public int maxRes;
	public void setup()
	{		
		setName("Bartleby Fan Club");
		setTeamImage("src/teams/student/bartlebyFanClub/bartlebyFanClubLogo.png");
		setTitle("Late To The Scene");

		setColorPrimary(227, 37, 107);
		setColorSecondary(49, 235, 204);
		setColorAccent(255, 255, 255);

	}

	@Override
	public void opening()
	{
		super.opening();
		maxRes = getMaxResources();
	}

	public void strategy()
	{
		float r = getResourcePercentage();

		if(r <= 0) //endgame - there are no resources
		{
			if(countMyUnits(Healer.class) < 5)
			{
				buildUnit(new Healer(this));
			}
			else if(countMyUnits(StunGun.class) < 5)
			{
				buildUnit(new StunGun(this));
			}
//			else if(getFleetValueUnitPercentage(Tank.class) < 0.2f)
//			{
//				buildUnit(new Tank(this));
//			}
			else if(getFleetValueUnitPercentage(SmartFighter.class) < 0.7f)
			{
				buildUnit(new SmartFighter(this));
			}
			else
			{
				buildUnit(new SmartFighter(this));
			}
		}
		else if(r <= 0.55) //midgame? - half the resources are gone
		{
			if(getFleetValueUnitPercentage(Gatherer.class) < 0.2f && countMyUnits(Gatherer.class) < 20)
			{
				buildUnit(new Gatherer(this));
			}
			else if(getFleetValueUnitPercentage(Miner.class) < 0.2f && countMyUnits(Miner.class) < 20)
			{
				buildUnit(new Miner(this));
			}
			else if(countMyUnits(Healer.class) < 3)
			{
				buildUnit(new Healer(this));
			}
			else if(countMyUnits(StunGun.class) < 3)
			{
				buildUnit(new StunGun(this));
			}
//			else if(getFleetValueUnitPercentage(Tank.class) < 0.15f)
//			{
//				buildUnit(new Tank(this));
//			}
			else if(getFleetValueUnitPercentage(SmartFighter.class) < .55f)
			{
				buildUnit(new SmartFighter(this));
			}
			else
			{
				buildUnit(new SmartFighter(this));
			}
		}
		else if(r < 10) //beginning - all resources
		{
			if(countMyUnits(SmartFighter.class) < 15 && countMyUnits() > 25)
			{
				buildUnit(new SmartFighter(this));
			}
			if(getFleetValueUnitPercentage(Gatherer.class) < 0.4f && countMyUnits(Gatherer.class) < 21)
			{
				buildUnit(new Gatherer(this));
				if(countMyUnits(GatherWarden.class) < 3 && countMyUnits() > 22)
				{
					buildUnit(new GatherWarden(this));
				}
			}
			else if(getFleetValueUnitPercentage(Miner.class) < 0.3f && countMyUnits(Miner.class) < 16)
			{
				buildUnit(new Miner(this));
			}
			else if(countMyUnits(Healer.class) < 1)
			{
				buildUnit(new Healer(this));
			}
			else if(countMyUnits(StunGun.class) < 1)
			{
				buildUnit(new StunGun(this));
			}
//			else if(getFleetValueUnitPercentage(Tank.class) < 0.1f && countMyUnits() > 25)
//			{
//				buildUnit(new Tank(this));
//			}
			else if(getFleetValueUnitPercentage(SmartFighter.class) < 0.4f)
			{
				buildUnit(new SmartFighter(this));
			}
			else
			{
				buildUnit(new SmartFighter(this));
			}
		}
	}
			
	public void draw(Graphics g) 
	{
		
	}

	public float getResourcePercentage()
	{
		float resources = 0;

		for(int i = 0; i < getAllResources().size(); i++)
		{
			resources += 1;
		}

		for(int i = 0; i < getAllNodes().size(); i++)
		{
			resources += getAvgNodeResources();
		}

		return resources / maxRes;
	}

	public int getMaxResources()
	{
		int maxResources = 0;

		for(int i = 0; i < getAllResources().size(); i++)
		{
			maxResources += 1;
		}

		for(int i = 0; i < getAllNodes().size(); i++)
		{
			maxResources += getAvgNodeResources();
		}

		return maxResources;
	}

	public int getAvgNodeResources()
	{
		int res = 0;
		for(int i = 0; i < getAllNodes().size(); i++)
		{
			res += 10; // add thing to make avg a constant instead of num
		}
		return res;
	}


}

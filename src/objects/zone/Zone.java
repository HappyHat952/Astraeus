package objects.zone;

import engine.states.Game;
import objects.GameObject;
import objects.entity.unit.Unit;
import player.Player;

import java.util.ArrayList;

abstract public class Zone extends GameObject
{
	private Player player;
	protected float duration;
	protected float radius;
	protected int time;
	
	protected boolean targetsEnemies;
	protected boolean targetsAllies;
	protected boolean targetsAll;
	
	private ArrayList<Unit> allUnits;
	private ArrayList<Unit> alliedUnits;
	private ArrayList<Unit> enemyUnits;

	
	public Zone(float x, float y) 
	{
		super(x, y);
		duration = Integer.MAX_VALUE;
		init();
	}
	
	public Zone(float x, float y, int duration) 
	{
		super(x, y);
		this.duration = duration;
		init();
	}
	
	public Zone(float x, float y, int duration, Player player) 
	{
		super(x, y);
		this.duration = duration;
		this.player = player;
		init();
	}
	
	private void init()
	{
		allUnits = new ArrayList<Unit>();
		alliedUnits = new ArrayList<Unit>();
		enemyUnits = new ArrayList<Unit>();
	}
	
	public boolean isDone()
	{
		return time == duration;
	}

	public void update()
	{
		super.update();
		updateUnits();
		applyEffects();
		time++;
	}
	
	public float getPercentDistance(Unit u)
	{
		return getDistance(u) / radius;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	private void updateUnits()
	{
		allUnits = Game.getUnits();
		alliedUnits.clear();
		enemyUnits.clear();
		
		if(player != null)
			
		for(Unit u : allUnits)
		{
			if(u.getPlayer() == getPlayer())
			{
				alliedUnits.add(u);
			}
			else
			{
				enemyUnits.add(u);
			}
		}
	}
	
	private void applyEffects()
	{
		if(targetsAll)
		{
			applyEffectAll();
		}
		else if(targetsEnemies)
		{
			applyEffectEnemies();
		}
		else if(targetsAllies)
		{
			applyEffectAllies();
		}
	}
	
	private void applyEffectAll()
	{
		for(Unit u : allUnits)
		{
			if(getDistance(u) <= radius)
			{
				applyEffect(u);
			}
		}
	}
	
	
	private void applyEffectEnemies()
	{
		for(Unit u : enemyUnits)
		{
			if(getDistance(u) <= radius)
			{
				applyEffect(u);
			}
		}
	}
	
	
	private void applyEffectAllies()
	{
		for(Unit u : alliedUnits)
		{
			if(getDistance(u) <= radius)
			{
				applyEffect(u);
			}
		}
	}
	
	protected void applyEffect(Unit u)
	{
		
	}
	
}

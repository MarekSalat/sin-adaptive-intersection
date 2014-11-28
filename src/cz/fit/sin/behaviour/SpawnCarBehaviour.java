package cz.fit.sin.behaviour;

import java.util.Random;

import cz.fit.sin.agents.CrossroadAgent;
import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.road.IntRoad;
import jade.core.behaviours.OneShotBehaviour;

@SuppressWarnings("serial")
public class SpawnCarBehaviour extends OneShotBehaviour {
	
	@Override
	public void action() {
		CrossroadAgent c = (CrossroadAgent) myAgent;
		Direction direction = getRandomDirection();
		Orientation orientation = getRandomOrientation();
		IntRoad road = c.getIncomingIntRoad("Main", orientation);
		int n = c.getIncomingIntRoadCount("Main", orientation, direction);		
		road.line.put(direction, (n + 1));		
		System.out.println("+ auto");
		c.refreshCars();
	}
	
	/*nahodny smer*/
	public Orientation getRandomOrientation() {
		Random rand = new Random();
		int n = rand.nextInt(4) + 1;
		switch (n) {
			case 1: return Orientation.NORTH;
			case 2: return Orientation.EAST;
			case 3: return Orientation.SOUTH;
			case 4: return Orientation.WEST;
			default: return Orientation.WEST;		
		}
	}
	
	/*nahodny cil*/
	public Direction getRandomDirection() {
		Random rand = new Random();
		int n = rand.nextInt(3) + 1;
		switch (n) {
			case 1: return Direction.FORWARD;
			case 2: return Direction.LEFT;
			case 3: return Direction.RIGHT;	
			default: return Direction.RIGHT;	
		}
	}
}

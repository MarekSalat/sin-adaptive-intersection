package cz.fit.sin.behaviour;

import cz.fit.sin.agents.CrossroadAgent;
import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;
import jade.core.behaviours.TickerBehaviour;
import jade.core.Agent;

@SuppressWarnings("serial")
public class MoveCarBehaviour extends TickerBehaviour {
	public MoveCarBehaviour (Agent a, long dt) {
		super(a, dt);
	}	
	
	@Override
	public void onTick() {
		CrossroadAgent c = (CrossroadAgent) myAgent;
		if (c.isGreen("Main", Orientation.NORTH, Direction.FORWARD)	&& c.getIncomingIntRoadCount("Main", Orientation.NORTH, Direction.FORWARD) >= 1)	c.removeCar(Orientation.NORTH, Direction.FORWARD);
		if (c.isGreen("Main", Orientation.NORTH, Direction.LEFT)	&& c.getIncomingIntRoadCount("Main", Orientation.NORTH, Direction.LEFT) >= 1) 		c.removeCar(Orientation.NORTH, Direction.LEFT);
		if (c.isGreen("Main", Orientation.NORTH, Direction.RIGHT)	&& c.getIncomingIntRoadCount("Main", Orientation.NORTH, Direction.RIGHT) >= 1) 		c.removeCar(Orientation.NORTH, Direction.RIGHT);		
		if (c.isGreen("Main", Orientation.SOUTH, Direction.FORWARD)	&& c.getIncomingIntRoadCount("Main", Orientation.SOUTH, Direction.FORWARD) >= 1)	c.removeCar(Orientation.SOUTH, Direction.FORWARD); 
		if (c.isGreen("Main", Orientation.SOUTH, Direction.LEFT)	&& c.getIncomingIntRoadCount("Main", Orientation.SOUTH, Direction.LEFT) >= 1)		c.removeCar(Orientation.SOUTH, Direction.LEFT); 		
		if (c.isGreen("Main", Orientation.SOUTH, Direction.RIGHT)	&& c.getIncomingIntRoadCount("Main", Orientation.SOUTH, Direction.RIGHT) >= 1) 		c.removeCar(Orientation.SOUTH, Direction.RIGHT);	
		if (c.isGreen("Main", Orientation.WEST, Direction.FORWARD)	&& c.getIncomingIntRoadCount("Main", Orientation.WEST, Direction.FORWARD) >= 1)		c.removeCar(Orientation.WEST, Direction.FORWARD); 
		if (c.isGreen("Main", Orientation.WEST, Direction.LEFT)		&& c.getIncomingIntRoadCount("Main", Orientation.WEST, Direction.LEFT) >= 1)		c.removeCar(Orientation.WEST, Direction.LEFT); 
		if (c.isGreen("Main", Orientation.WEST, Direction.RIGHT)	&& c.getIncomingIntRoadCount("Main", Orientation.WEST, Direction.RIGHT) >= 1)		c.removeCar(Orientation.WEST, Direction.RIGHT);	
		if (c.isGreen("Main", Orientation.EAST, Direction.FORWARD)	&& c.getIncomingIntRoadCount("Main", Orientation.EAST, Direction.FORWARD) >= 1)		c.removeCar(Orientation.EAST, Direction.FORWARD); 
		if (c.isGreen("Main", Orientation.EAST, Direction.LEFT)		&& c.getIncomingIntRoadCount("Main", Orientation.EAST, Direction.LEFT) >= 1)		c.removeCar(Orientation.EAST, Direction.LEFT); 
		if (c.isGreen("Main", Orientation.EAST, Direction.RIGHT)	&& c.getIncomingIntRoadCount("Main", Orientation.EAST, Direction.RIGHT) >= 1)		c.removeCar(Orientation.EAST, Direction.RIGHT); 		
	}
}

package cz.fit.sin.behaviour;

import cz.fit.sin.agents.CrossroadAgent;
import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.intersectionphases.*;
import cz.fit.sin.model.road.IntRoad;
import jade.core.behaviours.CyclicBehaviour;

@SuppressWarnings("serial")
public class LightsBehaviour extends CyclicBehaviour {
	
	@Override
	public void action() {
		CrossroadAgent c = (CrossroadAgent) myAgent;
		
		System.out.println(c.getGreenPhase().toString());
		
		IntersectionPhase newPhase = c.getEngine().nextPhase(c.getIntersection("Main"), c.getIntersectionList(), c.getGreenPhase());
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}   
		
		IntRoad road = (IntRoad) c.getIntersection("Main").getIncomingRoadFor(Orientation.SOUTH);
		road.line.put(Direction.FORWARD, 15);
		
		//if (!newPhase.equals(c.getGreenPhase())) System.out.println("zmena");
		c.setGreenPhase(newPhase);		
	}
}

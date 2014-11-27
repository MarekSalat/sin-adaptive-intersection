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
	int i = 1;
	
	@Override
	public void action() {
		CrossroadAgent c = (CrossroadAgent) myAgent;
		System.out.print(i);
		System.out.println(c.getGreenPhase().toString());
		
		IntersectionPhase newPhase = c.getEngine().nextPhase(c.getIntersection("Main"), c.getIntersectionList(), c.getGreenPhase());
		
		/*spi*/
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}   
		
		/*if (i == 1) {
			IntRoad road = (IntRoad) c.getIntersection("Main").getIncomingRoadFor(Orientation.SOUTH);
			road.line.put(Direction.RIGHT, 5);
			road.line.put(Direction.FORWARD, 15);
			road.line.put(Direction.LEFT, 15);
		}*/		
		
		/*if (i == 10) {
			IntRoad road = (IntRoad) c.getIntersection("Main").getIncomingRoadFor(Orientation.EAST);
			road.line.put(Direction.RIGHT, 5);
			road.line.put(Direction.FORWARD, 15);
			road.line.put(Direction.LEFT, 15);						
		}*/
		
		/*if (i == 15) {
			IntRoad road = (IntRoad) c.getIntersection("Main").getIncomingRoadFor(Orientation.WEST);
			road.line.put(Direction.RIGHT, 15);
			road.line.put(Direction.FORWARD, 1);
			road.line.put(Direction.LEFT, 1);							
		}*/
		
		
		//i++;
			
		//if (!newPhase.equals(c.getGreenPhase())) System.out.println("zmena");
		c.setGreenPhase(newPhase);		
	}
}

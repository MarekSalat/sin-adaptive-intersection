package cz.fit.sin.behaviour;

import cz.fit.sin.agents.CrossroadAgent;
import cz.fit.sin.model.IntersectionPhase;
import jade.core.behaviours.CyclicBehaviour;

@SuppressWarnings("serial")
public class LightsBehaviour extends CyclicBehaviour {
	int i = 1;
	
	@Override
	public void action() {
		CrossroadAgent c = (CrossroadAgent) myAgent;
		System.out.println(c.getGreenPhase().toString());		
		IntersectionPhase newPhase = c.getEngine().nextPhase(c.getIntersection("Main"), c.getIntersectionList(), c.getGreenPhase());		
		if (!newPhase.equals(c.getGreenPhase())) System.out.println("zmena");
		c.setGreenPhase(newPhase);	
		c.refreshSemaphores();	
		
		/*spi*/
		try {
			Thread.sleep(5500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 		
	}
}
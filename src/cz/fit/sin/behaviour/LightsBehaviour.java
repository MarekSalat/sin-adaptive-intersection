package cz.fit.sin.behaviour;

import cz.fit.sin.agents.CrossroadAgent;
import cz.fit.sin.model.IntersectionPhase;
import jade.core.behaviours.TickerBehaviour;
import jade.core.Agent;

@SuppressWarnings("serial")
public class LightsBehaviour extends TickerBehaviour {
	public LightsBehaviour (Agent a, long dt) {
		super(a, dt);
	}
	
	@Override
	public void onTick() {
		CrossroadAgent c = (CrossroadAgent) myAgent;
		System.out.println(c.getGreenPhase().toString());		
		IntersectionPhase newPhase = c.getEngine().nextPhase(c.getIntersection("Main"), c.getIntersectionList(), c.getGreenPhase());		
		if (!newPhase.equals(c.getGreenPhase())) System.out.println("zmena");
		c.setGreenPhase(newPhase);	
		c.refreshSemaphores();	
	}
}
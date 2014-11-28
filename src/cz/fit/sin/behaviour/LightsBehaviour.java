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
		IntersectionPhase newPhase = c.getEngine().nextPhase(c.getIntersection("Main"), c.getIntersectionList(), c.getGreenPhase());		
		c.setGreenPhase(newPhase);	
		c.refreshSemaphores();			
	}
}
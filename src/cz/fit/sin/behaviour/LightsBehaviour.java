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
		CrossroadAgent agent = (CrossroadAgent) myAgent;
		IntersectionPhase newPhase = agent.getEngine().nextPhase(agent.getIntersection("Main"), agent.getIntersectionPhases(), agent.getGreenPhase());
		if (agent.getGreenPhase() == newPhase) {
			if (CrossroadAgent.LOG) System.out.println("Semaphore phase unchanged");
			return;
		}

		if (CrossroadAgent.LOG) System.out.println("Semaphore phase change");
		agent.setGreenPhase(newPhase);
		agent.refreshSemaphores();
	}
}
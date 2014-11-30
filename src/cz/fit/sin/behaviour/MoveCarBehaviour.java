package cz.fit.sin.behaviour;

import cz.fit.sin.agents.CrossroadAgent;
import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

@SuppressWarnings("serial")
public class MoveCarBehaviour extends TickerBehaviour {
	public MoveCarBehaviour (Agent a, long dt) {
		super(a, dt);
	}	
	
	@Override
	public void onTick() {
		CrossroadAgent agent = (CrossroadAgent) myAgent;

		// remove one car from each outgoing road
		for (Orientation orientation : Orientation.values()) {
			for (Direction direction : Direction.values()) {
				if (direction.equals(Direction.CURRENT))
					continue;

				agent.removeCarFromOutgoingRoad(orientation, direction);
			}
		}

		for (Orientation orientation : Orientation.values()) {
			for (Direction direction : Direction.values()) {
				if (direction.equals(Direction.CURRENT))
					continue;

				// remove car from incoming road if semaphore light is green and next road is not full
				agent.moveCarOnIncomingRoad(orientation, direction);
			}
		}

		agent.refreshCars();
	}
}

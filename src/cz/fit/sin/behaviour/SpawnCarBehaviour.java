package cz.fit.sin.behaviour;

import cz.fit.sin.agents.CrossroadAgent;
import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;
import jade.core.behaviours.OneShotBehaviour;

import java.util.Random;

@SuppressWarnings("serial")
public class SpawnCarBehaviour extends OneShotBehaviour {
	Random rand = new Random();

	@Override
	public void action() {
		CrossroadAgent agent = (CrossroadAgent) myAgent;																				

		int added = 0;
		for (int i = 0; i < 4*3 && added < 5; i++) {
			Orientation orientation = getRandomOrientation();
			Direction direction = getRandomDirection();

			if (!agent.addCarToIncomingRoad(orientation, direction)) {
				continue;
			}
			added++;

			System.out.println("+ auto");
			agent.refreshCars();
		}																			
	}

	/*nahodny smer*/
	private Orientation getRandomOrientation() {
		return Orientation.values()[rand.nextInt(4)];
	}
	
	/*nahodny cil*/
	private Direction getRandomDirection() {
		return Direction.values()[rand.nextInt(3) + 1];
	}
}

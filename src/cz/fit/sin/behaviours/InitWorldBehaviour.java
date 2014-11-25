package cz.fit.sin.behaviours;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import cz.fit.sin.agents.CrossroadAgent;
import cz.fit.sin.agents.EndpointAgent;

@SuppressWarnings("serial")
public class InitWorldBehaviour extends OneShotBehaviour {	
	private int columns;
	private int rows;
	
	/*konstruktor*/
	public InitWorldBehaviour(int rows, int columns) {
		this.columns = columns;
		this.rows = rows;
	}
	
	@Override
	public void action() {
		try {
			spawnCrossroads();
		} catch (StaleProxyException e) {
			System.err.println("Error spawning crossroads agents");
			e.printStackTrace();
		}

		try {
			spawnEndpoints();
		} catch (StaleProxyException e) {
			System.err.println("Error spawning endpoints agents");
			e.printStackTrace();
		}
	}
	
	/*krizovatky*/
	private void spawnCrossroads() throws StaleProxyException {
		AgentContainer container = Runtime.instance().createAgentContainer(new ProfileImpl());	
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				AgentController agent = container.createNewAgent("crossroad-" + i + "-" + j, CrossroadAgent.class.getCanonicalName(), null);
				agent.start();				
			}
		}
	}
	
	/*endpointy*/
	private void spawnEndpoints() throws StaleProxyException {
		AgentContainer container = Runtime.instance().createAgentContainer(new ProfileImpl());	
		
		/*north + south*/
		for (int i = 1; i <= columns; i++) {
			AgentController agent0 = container.createNewAgent("endpoint-n-" + i, EndpointAgent.class.getCanonicalName(), null);
			agent0.start();
			AgentController agent1 = container.createNewAgent("endpoint-s-" + i, EndpointAgent.class.getCanonicalName(), null);
			agent1.start();
		}		
		
		/*east + west*/
		for (int i = 1; i <= rows; i++) {
			AgentController agent0 = container.createNewAgent("endpoint-e-" + i, EndpointAgent.class.getCanonicalName(), null);
			agent0.start();
			AgentController agent1 = container.createNewAgent("endpoint-w-" + i, EndpointAgent.class.getCanonicalName(), null);
			agent1.start();		
		}
	}
}

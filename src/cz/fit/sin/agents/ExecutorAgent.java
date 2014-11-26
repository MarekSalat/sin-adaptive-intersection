package cz.fit.sin.agents;

import cz.fit.sin.gui.GuiRoads;
import cz.fit.sin.model.WorldBuilder;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.world.WorldObject;
import cz.fit.sin.behaviours.InitWorldBehaviour;
import cz.fit.sin.agents.CarAgent;

import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

@SuppressWarnings("serial")
public class ExecutorAgent extends Agent  {
	private GuiRoads gui;
	private WorldBuilder wb;
	
	/*velikost*/
	private int rows;
	private int columns;
		
	/*auta*/
	private AgentContainer carAgentContainer;
	private int carAgents = 0;

	@Override
	protected void setup() {
		System.out.println("Executor " + getAID().getName() + " is ready");
		gui = new GuiRoads(this);	
		//prepareWorld(5,4);																						/*smazat*/
		carAgentContainer = Runtime.instance().createAgentContainer(new ProfileImpl());
		//spawnCarsFromTo("aa","bb");																				/*smazat*/
		//spawnCarsFromTo("bb","cc");																				/*smazat*/
		//spawnCarsFromTo("cc","dd");																				/*smazat*/
		//spawnCarsFromTo("ee","ff");																				/*smazat*/
	}
	
	/*vygeneruje svet*/
	public void prepareWorld (int rows, int columns) {
		this.wb = new WorldBuilder();
		this.rows = rows;
		this.columns = columns;		
		
		/*vytvoreni krizovatek*/
		for (int i = 1; i <= rows; i++ ) {
			for (int j = 1; j <= columns; j++) {
				WorldObject<Intersection> a = wb.add(Intersection.class);
				a.properties.name = "crossroad" + i + "-" + j;							
				
				/*horni hranice*/
				if (i != 1)	wb.connect(a, wb.done().findByName(Intersection.class, "crossroad" + (i-1) + "-" + j), Orientation.NORTH);
				else wb.addRoadsAt(a, Orientation.NORTH);
				
				/*leva hranice*/
				if (j != 1) wb.connect(a, wb.done().findByName(Intersection.class, "crossroad" + i + "-" + (j-1)), Orientation.WEST);
				else wb.addRoadsAt(a, Orientation.WEST);				
				
				/*spodni a prava hranice*/	
				if (i == rows) wb.addRoadsAt(a, Orientation.SOUTH);					
				if (j == columns) wb.addRoadsAt(a, Orientation.EAST);	
			}			
		}
		
		/*chovani*/
		addBehaviour(new InitWorldBehaviour(rows, columns));
	}
	
	/*vytiskne svet*/
	public void printWorld() {	
		for (int i = 1; i <= rows; i++ ) {
			
			if (i == 1) {
				for (int j = 1; j <= columns; j++) {
					System.out.print("a");
				}				
			}
			System.out.print("a");
			
			/*endpointW*/
			
			for (int j = 1; j <= columns; j++) {
							
				
			}			
			/*enpointE*/
		}		
	}
	
	/*vypustit auto*/
	public void spawnCarsFromTo(final String endpointFromName, final String endpointToName) {
		addBehaviour(new OneShotBehaviour() {
			
			@Override
			public void action() {
				String args[] = {endpointFromName, endpointToName};
				try {
					AgentController agent = carAgentContainer.createNewAgent("car-" + carAgents, CarAgent.class.getCanonicalName(), args);
					agent.start();
					carAgents++;
				} catch (StaleProxyException e) {
					System.err.println("Error creating car agents");
					e.printStackTrace();
				}
			}

		});
	}
}
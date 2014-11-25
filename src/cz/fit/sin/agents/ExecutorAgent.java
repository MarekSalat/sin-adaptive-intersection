package cz.fit.sin.agents;

import cz.fit.sin.gui.GuiRoads;
import cz.fit.sin.model.WorldBuilder;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.world.World;
import cz.fit.sin.model.world.WorldObject;
import cz.fit.sin.behaviours.InitWorldBehaviour;

import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

@SuppressWarnings("serial")
public class ExecutorAgent extends Agent  {
	private GuiRoads gui;
	private WorldBuilder wb;
	
	/*velikost*/
	private int rows;
	private int columns;
		
	/*kontejner*/
	private AgentContainer carAgentContainer;

	@Override
	protected void setup() {
		System.out.println("Executor " + getAID().getName() + " is ready");
		gui = new GuiRoads(this);		
		carAgentContainer = Runtime.instance().createAgentContainer(new ProfileImpl());
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
				a.properties.name = i + "-" + j;
				
				/*horni hranice*/
				if (i == 1)	wb.addRoadsAt(a, Orientation.NORTH);
				else wb.connect(a, wb.done().findByName(Intersection.class, (i-1) + "-" + j), Orientation.NORTH);
				
				/*leva hranice*/
				if (j == 1) wb.addRoadsAt(a, Orientation.WEST);	
				else wb.connect(a, wb.done().findByName(Intersection.class, i + "-" + (j-1)), Orientation.WEST);	
				
				/*spodni a prava hranice*/	
				if (i == rows) wb.addRoadsAt(a, Orientation.SOUTH);					
				if (j == columns) wb.addRoadsAt(a, Orientation.EAST);	
			}			
		}
		
		/*chovani*/
		addBehaviour(new InitWorldBehaviour(rows, columns));
	}
}
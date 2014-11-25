package cz.fit.sin.agents;

import cz.fit.sin.gui.GuiRoads;
import jade.core.Agent;

@SuppressWarnings("serial")
public class ExecutorAgent extends Agent  {
	private GuiRoads gui;

	@Override
	protected void setup() {
		System.out.println("ExecutorAgent");
		gui = new GuiRoads();
		gui.setVisible(true);
	}
	
}

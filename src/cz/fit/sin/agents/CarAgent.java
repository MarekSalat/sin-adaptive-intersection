package cz.fit.sin.agents;

import jade.core.Agent;

@SuppressWarnings("serial")
public class CarAgent extends Agent {

	@Override
	protected void setup() {
		
		/*kontrola argumentu*/
		Object args[] = getArguments();
		if (args.length < 2) {
			System.err.println("Unexpected arguments for CarAgent. Call with <src> <dst>");
			doDelete();
		}
		
		
		
		
		
		
	}
	
}

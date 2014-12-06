package cz.fit.sin.behaviour;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import cz.fit.sin.agents.CarAgent;

@SuppressWarnings("serial")
public class CarMessageListener extends CyclicBehaviour { 
	private String conversationId = null;
	
	/*nastavani id konverzace*/
	public CarMessageListener (String conversationId) {
		super();
		this.conversationId = conversationId;
	}
	
	@Override
	public void action() {
		CarAgent carAgent = (CarAgent) myAgent;
		MessageTemplate mt = MessageTemplate.MatchConversationId(conversationId);
		ACLMessage msg = myAgent.receive(mt);		
		if (msg == null) block();
		else carAgent.checkpoint(msg.getSender());		
	}	
}

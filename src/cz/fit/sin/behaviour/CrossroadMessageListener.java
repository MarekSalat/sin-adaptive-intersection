package cz.fit.sin.behaviour;

import cz.fit.sin.agents.CrossroadAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class CrossroadMessageListener extends CyclicBehaviour {
	private String conversationId = null;
	
	/*nastavani id konverzace*/
	public CrossroadMessageListener (String conversationId) {
		super();
		this.conversationId = conversationId;
	}	
	
	@Override
	public void action() {
		CrossroadAgent crossAgent = (CrossroadAgent) myAgent;
		MessageTemplate mt = MessageTemplate.MatchConversationId(conversationId);
		ACLMessage msg = myAgent.receive(mt);	
		if (msg == null) block();		 
		else {
			crossAgent.writeNewStatistics(Long.parseLong(msg.getContent()));
			crossAgent.refreshStatistics();
		}
	}	
}

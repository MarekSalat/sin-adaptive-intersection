package cz.fit.sin.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import cz.fit.sin.behaviour.CarMessageListener;

@SuppressWarnings("serial")
public class CarAgent extends Agent {
	private long start;
	private long end;
	private String carConversationId;
	private Status status;	
	public enum Status {		
		WAITING,
		GOODBYE
	}
	
	@Override
	protected void setup() {
		Object args[] = getArguments();
		if (args.length != 1) {
			System.err.println("Unexpected arguments for CarAgent. Call with <id>");
			doDelete();
		}
		
		start = System.nanoTime(); 
		status = Status.WAITING;
		carConversationId = args[0].toString();
		System.out.println( "agent: " +  getLocalName());
		addBehaviour(new CarMessageListener(carConversationId));
	}
	
	/*mereni casu*/
	public void checkpoint(AID sender) {
		if (status == Status.WAITING) {
			end = System.nanoTime(); 
			status = Status.GOODBYE;			
		}
		
		else {
			sendMessageWithTime(sender);	
			doDelete();
		}
	}
	
	/*posle zpravu*/
	public void sendMessageWithTime(AID sender) {	
		long time = end-start;
		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.setConversationId(carConversationId);		
		request.setContent(Long.toString(time));
		request.addReceiver(sender);
		send(request);					
	}
}

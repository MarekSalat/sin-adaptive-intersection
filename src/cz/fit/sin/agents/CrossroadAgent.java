package cz.fit.sin.agents;

import java.util.Arrays;
import java.util.List;

import jade.core.Agent;

import cz.fit.sin.gui.GuiRoads;

import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.WorldBuilder;
import cz.fit.sin.model.fuzzy.IntersectionFuzzyEngine;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.intersectionphases.ForwardPhase;
import cz.fit.sin.model.intersectionphases.LeftPhase;
import cz.fit.sin.model.intersectionphases.LeftRightPhase;
import cz.fit.sin.model.intersectionphases.RightForwardPhase;
import cz.fit.sin.model.intersectionphases.SimplePhase;
import cz.fit.sin.model.world.WorldObject;
import cz.fit.sin.model.world.World;

import cz.fit.sin.behaviour.LightsBehaviour;

@SuppressWarnings("serial")
public class CrossroadAgent extends Agent {		
	private IntersectionFuzzyEngine engine;
	private List<IntersectionPhase> phases;
	private IntersectionPhase greenPhase;	
	private WorldBuilder wb;
	private GuiRoads gui;
	
	@Override
	protected void setup() {
		System.out.println(getAID().getName() + " is ready");
		engine = new IntersectionFuzzyEngine();
		gui = new GuiRoads(this);	
		prepareWorld();	
		
		/*inicializace fazi*/
		phases = Arrays.asList(
            new SimplePhase(Orientation.NORTH),
            new SimplePhase(Orientation.EAST),
            new SimplePhase(Orientation.SOUTH),
            new SimplePhase(Orientation.WEST),
            new ForwardPhase(Orientation.NORTH),
            new ForwardPhase(Orientation.WEST),
            new LeftPhase(Orientation.NORTH),
            new LeftPhase(Orientation.WEST),
            new LeftRightPhase(Orientation.NORTH),
            new LeftRightPhase(Orientation.EAST),
            new LeftRightPhase(Orientation.SOUTH),
            new LeftRightPhase(Orientation.WEST),
            new RightForwardPhase(Orientation.NORTH),
            new RightForwardPhase(Orientation.WEST)
        );
		
		/*inicializace*/
		setGreenPhase(phases.get(0));		
				
		/*chovani*/
		addBehaviour(new LightsBehaviour());
		//addBehaviour(new GuiRefreshBehaviour());
	}	
	
	/*vygeneruje svet*/
	public void prepareWorld () {
		this.wb = new WorldBuilder();
		WorldObject<Intersection> a = wb.add(Intersection.class);
		a.properties.name = "Main";
		wb.addRoadsAt(a, Orientation.NORTH);
        wb.addRoadsAt(a, Orientation.SOUTH);
        wb.addRoadsAt(a, Orientation.EAST);
        wb.addRoadsAt(a, Orientation.WEST);        
	}
		
	/*vrati svet*/
	public World getWorld() {
		return wb.done();
	}
	
	/*vrati krizovatku*/
	public Intersection getIntersection(String name) {
		return getWorld().findByName(Intersection.class, name).object;
	}
	
	/*vrati seznam fazi*/
	public List<IntersectionPhase> getIntersectionList() {
		return phases;
	}
	
	/*vrati fazi*/
	public IntersectionPhase getGreenPhase() {
		return greenPhase;
	}
	
	/*nastavi fazi*/
	public void setGreenPhase(IntersectionPhase newPhase) {
		greenPhase = newPhase;
	}
	
	/*vrati engine*/
	public IntersectionFuzzyEngine getEngine() {
		return engine;
	}	
}

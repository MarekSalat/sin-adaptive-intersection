package cz.fit.sin.agents;

import java.util.List;
import java.util.ArrayList;
import jade.core.Agent;
import cz.fit.sin.gui.GuiRoads;
import cz.fit.sin.gui.Semaphores;
import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.WorldBuilder;
import cz.fit.sin.model.fuzzy.IntersectionFuzzyEngine;
import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Light;
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
	public IntersectionPhase greenPhase;	
	private WorldBuilder wb;
	private GuiRoads gui;
	private int lights[];
	
	@Override
	protected void setup() {
		System.out.println(getAID().getName() + " is ready");
		engine = new IntersectionFuzzyEngine();
		gui = new GuiRoads(this);
		lights = new int[12];
		prepareWorld();	
		
		/*inicializace fazi*/
		phases = new ArrayList<IntersectionPhase>();
		phases.add(new SimplePhase(Orientation.NORTH));
		phases.add(new SimplePhase(Orientation.EAST));
		phases.add(new SimplePhase(Orientation.SOUTH));
		phases.add(new SimplePhase(Orientation.WEST));
		phases.add(new ForwardPhase(Orientation.NORTH));
		phases.add(new ForwardPhase(Orientation.WEST));
		phases.add(new LeftPhase(Orientation.NORTH));
		phases.add(new LeftPhase(Orientation.WEST));
		phases.add(new LeftRightPhase(Orientation.NORTH));
		phases.add(new LeftRightPhase(Orientation.EAST));
		phases.add(new SimplePhase(Orientation.NORTH));
		phases.add(new LeftRightPhase(Orientation.SOUTH));
		phases.add(new LeftRightPhase(Orientation.WEST));
		phases.add(new RightForwardPhase(Orientation.NORTH));
		phases.add(new RightForwardPhase(Orientation.WEST));
		setGreenPhase(phases.get(0));	
	}	
	
	/*simulace*/
	public void startSimulation() {		
		addBehaviour(new LightsBehaviour());
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
		newPhase.setSemaphoreLights(getIntersection("Main"));
		greenPhase = newPhase;
	}
	
	/*vrati engine*/
	public IntersectionFuzzyEngine getEngine() {
		return engine;
	}
	
	/*test svetel*/
	public int getColor(String name, Orientation orientation, Direction direction) {		
		return (getIntersection(name).getSemaphoreLight(orientation, direction).equals(Light.GREEN) ? 1 : 0);
	}
		
	/*pregeneruje semafory*/
	public void refreshSemaphores() {
		this.lights[Semaphores.NORTH_FORWARD] = getColor("Main", Orientation.NORTH, Direction.FORWARD);	
		this.lights[Semaphores.NORTH_LEFT] = getColor("Main", Orientation.NORTH, Direction.LEFT);	
		this.lights[Semaphores.NORTH_RIGHT] = getColor("Main", Orientation.NORTH, Direction.RIGHT);	
		this.lights[Semaphores.SOUTH_FORWARD] = getColor("Main", Orientation.SOUTH, Direction.FORWARD);	
		this.lights[Semaphores.SOUTH_LEFT] = getColor("Main", Orientation.SOUTH, Direction.LEFT);	
		this.lights[Semaphores.SOUTH_RIGHT] = getColor("Main", Orientation.SOUTH, Direction.RIGHT);	 
		this.lights[Semaphores.WEST_FORWARD] = getColor("Main", Orientation.WEST, Direction.FORWARD);	
		this.lights[Semaphores.WEST_LEFT] = getColor("Main", Orientation.WEST, Direction.LEFT);   
		this.lights[Semaphores.WEST_RIGHT] = getColor("Main", Orientation.WEST, Direction.RIGHT);  		
		this.lights[Semaphores.EAST_FORWARD] = getColor("Main", Orientation.EAST, Direction.FORWARD);  
		this.lights[Semaphores.EAST_LEFT] = getColor("Main", Orientation.EAST, Direction.LEFT);   
		this.lights[Semaphores.EAST_RIGHT] = getColor("Main", Orientation.EAST, Direction.RIGHT);  
		gui.setSemaphores(lights);
	}
	
	/*prida auto*/
	public void addCar() {
		System.out.println("auto");
	}
}
package cz.fit.sin.agents;

import cz.fit.sin.behaviour.LightsBehaviour;
import cz.fit.sin.behaviour.MoveCarBehaviour;
import cz.fit.sin.behaviour.SpawnCarBehaviour;
import cz.fit.sin.gui.GuiRoads;
import cz.fit.sin.gui.Semaphores;
import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.WorldBuilder;
import cz.fit.sin.model.fuzzy.IntersectionFuzzyEngine;
import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Light;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.intersectionphases.*;
import cz.fit.sin.model.road.IntRoad;
import cz.fit.sin.model.road.Road;
import cz.fit.sin.model.world.World;
import cz.fit.sin.model.world.WorldObject;
import cz.fit.sin.utils.Pair;
import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CrossroadAgent extends Agent {		
	private IntersectionFuzzyEngine engine;
	private List<IntersectionPhase> phases;
	public IntersectionPhase greenPhase;	
	private WorldBuilder worldBuilder;
	private GuiRoads gui;
	private int lights[];
	private int cars[];
	
	@Override
	protected void setup() {
		System.out.println(getAID().getName() + " is ready");
		engine = new IntersectionFuzzyEngine();
		gui = new GuiRoads(this);
		lights = new int[12];
		cars = new int[12];
		prepareWorld();	
		
		/*inicializace fazi*/
		phases = new ArrayList<>();
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
		phases.add(new LeftRightPhase(Orientation.SOUTH));
		phases.add(new LeftRightPhase(Orientation.WEST));

		phases.add(new RightForwardPhase(Orientation.NORTH));
		phases.add(new RightForwardPhase(Orientation.WEST));

		setGreenPhase(phases.get(0));	
	}	
	
	/*simulace*/
	public void startSimulation() {
		refreshSemaphores();		
		addBehaviour(new LightsBehaviour(this, 8000));
		addBehaviour(new MoveCarBehaviour(this, 2000));
	}
	
	/*vygeneruje svet*/
	public void prepareWorld () {
		this.worldBuilder = new WorldBuilder();
		WorldObject<Intersection> intersection = worldBuilder.add(Intersection.class);
		intersection.properties.name = "Main";
		worldBuilder.addRoadsAt(intersection, Orientation.NORTH);
        worldBuilder.addRoadsAt(intersection, Orientation.SOUTH);
        worldBuilder.addRoadsAt(intersection, Orientation.EAST);
        worldBuilder.addRoadsAt(intersection, Orientation.WEST);
	}
		
	/*vrati svet*/
	public World getWorld() {
		return worldBuilder.done();
	}
	
	/*vrati krizovatku*/
	public Intersection getIntersection(String name) {
		return getWorld().findByName(Intersection.class, name).object;
	}
	
	/*vrati cestu*/
	public Road getIncomingRoad(String name, Orientation orientation) {
		return getIntersection(name).getIncomingRoadFor(orientation);
	} 
	
	/*vrati pocet aut na ceste*/
	public int getVehicleCountOnIncomingRoad(String name, Orientation orientation, Direction direction) {
		return getIncomingRoad(name, orientation).getVehiclesCount(direction);
	}
	
	/*vrati seznam fazi*/
	public List<IntersectionPhase> getIntersectionPhases() {
		return phases;
	}
	
	/*vrati aktualni fazi*/
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
	
	/*je zelena*/
	public boolean isSemaphoreGreen(String name, Orientation orientation, Direction direction) {
		return getIntersection(name).getSemaphoreLight(orientation, direction).equals(Light.GREEN);
	}
	
	/*test svetel*/
	public int getSemaphoreLightIndex(String name, Orientation orientation, Direction direction) {
		return isSemaphoreGreen(name, orientation, direction) ? 1 : 0;
	}
		
	/*pregeneruje semafory*/
	public void refreshSemaphores() {
		for (Integer index : Semaphores.MAPPING.keySet()) {
			Pair<Orientation, Direction> pair = Semaphores.MAPPING.get(index);
			this.lights[index] = getSemaphoreLightIndex("Main", pair.first, pair.second);
		}

		gui.setSemaphores(lights, getGreenPhaseIndex());
	}
	
	/*pregeneruje auta*/
	public void refreshCars() {
		for (Integer index : Semaphores.MAPPING.keySet()) {
			Pair<Orientation, Direction> pair = Semaphores.MAPPING.get(index);
			this.cars[index] = getVehicleCountOnIncomingRoad("Main", pair.first, pair.second);
		}

		gui.setCars(cars);
	}	

	/*vrati cislo faze*/
	public int getGreenPhaseIndex() {
		return phases.indexOf(greenPhase);
	}
				
	/*prida auto*/
	public void addCar() {		
		addBehaviour(new SpawnCarBehaviour());
	}

	public boolean moveCarOnIncomingRoad(Orientation orientation, Direction direction) {
		IntRoad currentRoad = (IntRoad) getIntersection("Main").getIncomingRoadFor(orientation);
		IntRoad nextRoad = (IntRoad) getIntersection("Main").getOutgoingRoadFor(orientation.toAbsolute(direction));

		if (!isSemaphoreGreen("Main", orientation, direction) || currentRoad.isEmpty() || nextRoad.isFull() || direction.equals(Direction.CURRENT))
			return false;

		currentRoad.line.put(direction, currentRoad.line.get(direction)-1);
		nextRoad.line.put(Direction.FORWARD, nextRoad.line.get(Direction.FORWARD)+1);

		return true;
	}

	public boolean removeCarFromOutgoingRoad(Orientation orientation, Direction direction) {
		IntRoad road = (IntRoad) getIntersection("Main").getOutgoingRoadFor(orientation.toAbsolute(direction));
		if (road.isEmpty() || direction.equals(Direction.CURRENT))
			return false;

		road.line.put(Direction.FORWARD, road.line.get(Direction.FORWARD)-1);

		return true;
	}

	public boolean addCarToIncomingRoad(Orientation orientation, Direction direction) {
		IntRoad road = (IntRoad) getIncomingRoad("Main", orientation);
		if (road.isFull() || direction.equals(Direction.CURRENT))
			return false;

		road.line.put(direction, (road.getVehiclesCount() + 1));

		return true;
	}
}
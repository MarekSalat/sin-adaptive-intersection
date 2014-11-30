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
	public static final int PHASE_CHANGE_PERIOD = 2000;
	public static final int CAR_MOVE_PERIOD = 500;
	public static final int CAR_FLOW = PHASE_CHANGE_PERIOD / CAR_MOVE_PERIOD;

	private IntersectionFuzzyEngine engine;
	private List<IntersectionPhase> phases;
	public IntersectionPhase greenPhase;	
	private WorldBuilder worldBuilder;
	private GuiRoads gui;
	private int lights[];
	private int incomingCars[];
	private int outgoingCars[];
	
	@Override
	protected void setup() {
		System.out.println(getAID().getName() + " is ready");
		engine = new IntersectionFuzzyEngine();
		gui = new GuiRoads(this);
		lights = new int[12];
		incomingCars = new int[12];
		outgoingCars = new int[4];
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
	
	public void startSimulation() {
		refreshSemaphores();		
		addBehaviour(new LightsBehaviour(this, PHASE_CHANGE_PERIOD));
		addBehaviour(new MoveCarBehaviour(this, CAR_MOVE_PERIOD));
	}
	
	/*vygeneruje svet*/
	public void prepareWorld () {
		this.worldBuilder = new WorldBuilder();
		WorldObject<Intersection> intersection = worldBuilder.add(Intersection.class);

//		todo: odkometuj pokud chces do sveta davat i auta pres worldBuilder.add(Vehicle.class); :)
//		worldBuilder.registerFactory(Vehicle.class, new WorldBuilder.Factory() {
//			@Override
//			public Object create() {
//				return new Vehicle();
//			}
//		});

//		todo: odkomentuj pokud chces do sveta misto IntRoad davat QueueRoad
//		worldBuilder.registerFactory(Road.class, new WorldBuilder.Factory() {
//			@Override
//			public Object create() {
//				return new QueueRoad(16);
//			}
//		});

		intersection.properties.name = "Main";
		worldBuilder.addRoadsAt(intersection, Orientation.NORTH);
        worldBuilder.addRoadsAt(intersection, Orientation.SOUTH);
        worldBuilder.addRoadsAt(intersection, Orientation.EAST);
        worldBuilder.addRoadsAt(intersection, Orientation.WEST);
	}
		
	public World getWorld() {
		return worldBuilder.done();
	}
	
	public Intersection getIntersection(String name) {
		return getWorld().findByName(Intersection.class, name).object;
	}
	
	public Road getIncomingRoad(String name, Orientation orientation) {
		return getIntersection(name).getIncomingRoadFor(orientation);
	} 
	
	public Road getOutgoingRoad(String name, Orientation orientation) {
		return getIntersection(name).getOutgoingRoadFor(orientation);
	}

	/*vrati celkovy pocet aut na prijezdovych cestach*/
	public int getVehicleCountOnAllIncomingRoads(String name) {
		int cnt = 0;
		cnt += getIncomingRoad(name, Orientation.SOUTH).getVehiclesCount();
		cnt += getIncomingRoad(name, Orientation.NORTH).getVehiclesCount();	
		cnt += getIncomingRoad(name, Orientation.EAST).getVehiclesCount();	
		cnt += getIncomingRoad(name, Orientation.WEST).getVehiclesCount();					 
		return cnt;	
	}
	
	public int getVehicleCountOnIncomingRoad(String name, Orientation orientation, Direction direction) {
		return getIncomingRoad(name, orientation).getVehiclesCount(direction);
	}
	
	public int getVehicleCountOnOutgoingRoad(String name, Orientation orientation) {
		return getOutgoingRoad(name, orientation).getVehiclesCount();
	}
	
	public List<IntersectionPhase> getIntersectionPhases() {
		return phases;
	}
	
	public IntersectionPhase getGreenPhase() {
		return greenPhase;
	}
	
	public void setGreenPhase(IntersectionPhase newPhase) {
		newPhase.setSemaphoreLights(getIntersection("Main"));
		greenPhase = newPhase;
	}
	
	public IntersectionFuzzyEngine getEngine() {
		return engine;
	}
	
	public boolean isSemaphoreGreen(String name, Orientation orientation, Direction direction) {
		return getIntersection(name).getSemaphoreLight(orientation, direction).equals(Light.GREEN);
	}
	
	public int getSemaphoreLightIndex(String name, Orientation orientation, Direction direction) {
		return isSemaphoreGreen(name, orientation, direction) ? 1 : 0;
	}
		
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
			this.incomingCars[index] = getVehicleCountOnIncomingRoad("Main", pair.first, pair.second);
		}
		
		this.outgoingCars[Semaphores.LEAVE_NORTH] = getVehicleCountOnOutgoingRoad("Main", Orientation.NORTH);
		this.outgoingCars[Semaphores.LEAVE_SOUTH] = getVehicleCountOnOutgoingRoad("Main", Orientation.SOUTH);
		this.outgoingCars[Semaphores.LEAVE_WEST] = getVehicleCountOnOutgoingRoad("Main", Orientation.WEST);
		this.outgoingCars[Semaphores.LEAVE_EAST] = getVehicleCountOnOutgoingRoad("Main", Orientation.EAST);

		gui.setCars(incomingCars);
		gui.setLeaveCars(outgoingCars);
	}	

	public int getGreenPhaseIndex() {
		return phases.indexOf(greenPhase);
	}
				
	public void addCar() {
		addBehaviour(new SpawnCarBehaviour());
	}

	public boolean moveCarOnIncomingRoad(Orientation orientation, Direction direction) {
		IntRoad currentRoad = (IntRoad) getIntersection("Main").getIncomingRoadFor(orientation);
		IntRoad nextRoad = (IntRoad) getIntersection("Main").getOutgoingRoadFor(orientation.toAbsolute(direction));

		if (direction.equals(Direction.CURRENT) || !isSemaphoreGreen("Main", orientation, direction) || currentRoad.isEmpty(direction) || nextRoad.isFull(direction))
			return false;

		currentRoad.line.put(direction, currentRoad.line.get(direction)-1);
		nextRoad.line.put(Direction.FORWARD, nextRoad.line.get(Direction.FORWARD)+1);

		return true;
	}

	public boolean removeCarFromOutgoingRoad(Orientation orientation, Direction direction) {
		IntRoad road = (IntRoad) getIntersection("Main").getOutgoingRoadFor(orientation.toAbsolute(direction));
		if (direction.equals(Direction.CURRENT) || road.isEmpty(Direction.FORWARD))
			return false;

		road.line.put(Direction.FORWARD, road.line.get(Direction.FORWARD)-1);
        
		return true;
	}

	public boolean addCarToIncomingRoad(Orientation orientation, Direction direction) {
		IntRoad road = (IntRoad) getIncomingRoad("Main", orientation);
		if (road.isFull(direction) || direction.equals(Direction.CURRENT))
			return false;

		road.line.put(direction, (road.getVehiclesCount(direction) + 1));

		return true;
	}
}
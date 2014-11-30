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
import cz.fit.sin.model.road.QueueRoad;
import cz.fit.sin.model.road.Road;
import cz.fit.sin.model.vehicles.Vehicle;
import cz.fit.sin.model.world.World;
import cz.fit.sin.model.world.WorldObject;
import cz.fit.sin.utils.Pair;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CrossroadAgent extends Agent {
	public static final int PHASE_CHANGE_PERIOD = 5000;
	public static final int CAR_MOVE_PERIOD = 1000;
	public static final int CAR_OUTFLOW = PHASE_CHANGE_PERIOD / CAR_MOVE_PERIOD;

	private AgentContainer carAgentContainer;
	public IntersectionPhase greenPhase;
	private IntersectionFuzzyEngine engine;
	private List<IntersectionPhase> phases;
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
		phases.add(new SimplePhase(Orientation.NORTH, CAR_OUTFLOW));
		phases.add(new SimplePhase(Orientation.EAST, CAR_OUTFLOW));
		phases.add(new SimplePhase(Orientation.SOUTH, CAR_OUTFLOW));
		phases.add(new SimplePhase(Orientation.WEST, CAR_OUTFLOW));
		phases.add(new ForwardPhase(Orientation.NORTH, CAR_OUTFLOW));
		phases.add(new ForwardPhase(Orientation.WEST, CAR_OUTFLOW));
		phases.add(new LeftPhase(Orientation.NORTH, CAR_OUTFLOW));
		phases.add(new LeftPhase(Orientation.WEST, CAR_OUTFLOW));
		phases.add(new LeftRightPhase(Orientation.NORTH, CAR_OUTFLOW));
		phases.add(new LeftRightPhase(Orientation.EAST, CAR_OUTFLOW));
		phases.add(new LeftRightPhase(Orientation.SOUTH, CAR_OUTFLOW));
		phases.add(new LeftRightPhase(Orientation.WEST, CAR_OUTFLOW));
		phases.add(new RightForwardPhase(Orientation.NORTH, CAR_OUTFLOW));
		phases.add(new RightForwardPhase(Orientation.WEST, CAR_OUTFLOW));
		setGreenPhase(phases.get(0));	
		
		/*kontejner na auta*/
		Profile profile = new ProfileImpl();
		carAgentContainer = Runtime.instance().createAgentContainer(profile);
	}	
	
	/*simulace*/
	public void startSimulation() {
		refreshSemaphores();		
		addBehaviour(new LightsBehaviour(this, PHASE_CHANGE_PERIOD));
		addBehaviour(new MoveCarBehaviour(this, CAR_MOVE_PERIOD));
	}
	
	/*vygeneruje svet*/
	public void prepareWorld () {
		this.worldBuilder = new WorldBuilder();
		WorldObject<Intersection> intersection = worldBuilder.add(Intersection.class);

		/*odkometuj pokud chces do sveta davat i auta pres worldBuilder.add(Vehicle.class); */
		worldBuilder.registerFactory(Vehicle.class, new WorldBuilder.Factory() {
			@Override
			public Object create() {
				return new Vehicle();
			}
		});

		/*odkomentuj pokud chces do sveta misto IntRoad davat QueueRoad*/
		worldBuilder.registerFactory(Road.class, new WorldBuilder.Factory() {
			@Override
			public Object create() {
				return new QueueRoad(WorldBuilder.DEFAULT_ROAD_CAPACITY);
			}
		});

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

	public int getVehicleCountOnAllIncomingRoads(String name) {
		int cnt = 0;
		for (Orientation orientation : Orientation.values()) {
			cnt += getIncomingRoad(name, orientation).getVehiclesCount();
		}
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

	/*posunuti auta*/
	public boolean moveCarOnIncomingRoad(Orientation orientation, Direction direction) {
		QueueRoad currentRoad = (QueueRoad) getIntersection("Main").getIncomingRoadFor(orientation);
		QueueRoad nextRoad = (QueueRoad) getIntersection("Main").getOutgoingRoadFor(orientation.toAbsolute(direction));
		if (direction.equals(Direction.CURRENT) || !isSemaphoreGreen("Main", orientation, direction) || currentRoad.isEmpty(direction) || nextRoad.isFull(direction))
			return false;

		Vehicle vehicle = currentRoad.line.get(direction).getFirst();
		currentRoad.removeFirstVehicle(direction);		
		nextRoad.putVehicle(Direction.FORWARD, vehicle);
		System.out.println("move: " + worldBuilder.done().find(vehicle).properties.id);
		
		return true;
	}

	/*odebrani auta*/
	public boolean removeCarFromOutgoingRoad(Orientation orientation, Direction direction) {
		QueueRoad road = (QueueRoad) getIntersection("Main").getOutgoingRoadFor(orientation.toAbsolute(direction));
		if (direction.equals(Direction.CURRENT) || road.isEmpty(Direction.FORWARD))
			return false;

		Vehicle vehicle = road.line.get(Direction.FORWARD).getFirst();
		road.removeFirstVehicle(Direction.FORWARD);		
        System.out.println("remove: " + worldBuilder.done().find(vehicle).properties.id);
        worldBuilder.done().vehicles.remove(worldBuilder.done().find(vehicle));
		
		return true;
	}

	/*pridani auta*/
	public boolean addCarToIncomingRoad(Orientation orientation, Direction direction) {
		QueueRoad road = (QueueRoad) getIncomingRoad("Main", orientation);
		if (road.isFull(direction) || direction.equals(Direction.CURRENT))
			return false;

		WorldObject<Vehicle> vehicle = worldBuilder.add(Vehicle.class);		
		road.putVehicle(direction, vehicle.object);		
		System.out.println("add: " + vehicle.properties.id.toString()); //SMAZAT
		
		return true;
	}
}
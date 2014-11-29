package cz.fit.sin.gui;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.utils.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Semaphores {
	
	public static final int NORTH_FORWARD = 0;
	public static final int NORTH_LEFT    = 1;
	public static final int NORTH_RIGHT   = 2;
	
	public static final int SOUTH_FORWARD = 3;
	public static final int SOUTH_LEFT    = 4;
	public static final int SOUTH_RIGHT   = 5;
	
	public static final int WEST_FORWARD  = 6;
	public static final int WEST_LEFT     = 7;
	public static final int WEST_RIGHT    = 8;
	
	public static final int EAST_FORWARD  = 9;
	public static final int EAST_LEFT     = 10;
	public static final int EAST_RIGHT    = 11;

	public static final Map<Integer, Pair<Orientation, Direction>> MAPPING;

	static {
		Map<Integer, Pair<Orientation, Direction>> mapping = new HashMap<>();

		mapping.put(NORTH_FORWARD, Pair.of(Orientation.NORTH, Direction.FORWARD ));
		mapping.put(NORTH_LEFT,    Pair.of(Orientation.NORTH, Direction.LEFT    ));
		mapping.put(NORTH_RIGHT,   Pair.of(Orientation.NORTH, Direction.RIGHT   ));
		mapping.put(SOUTH_FORWARD, Pair.of(Orientation.SOUTH, Direction.FORWARD ));
		mapping.put(SOUTH_LEFT,    Pair.of(Orientation.SOUTH, Direction.LEFT    ));
		mapping.put(SOUTH_RIGHT,   Pair.of(Orientation.SOUTH, Direction.RIGHT   ));
		mapping.put(WEST_FORWARD,  Pair.of(Orientation.WEST,  Direction.FORWARD ));
		mapping.put(WEST_LEFT,     Pair.of(Orientation.WEST,  Direction.LEFT    ));
		mapping.put(WEST_RIGHT,    Pair.of(Orientation.WEST,  Direction.RIGHT   ));
		mapping.put(EAST_FORWARD,  Pair.of(Orientation.EAST,  Direction.FORWARD ));
		mapping.put(EAST_LEFT,     Pair.of(Orientation.EAST,  Direction.LEFT    ));
		mapping.put(EAST_RIGHT,    Pair.of(Orientation.EAST,  Direction.RIGHT   ));

		MAPPING = Collections.unmodifiableMap(mapping);
	}
	

	public static final int LEAVE_NORTH   = 0;
	public static final int LEAVE_SOUTH   = 1;
	public static final int LEAVE_WEST    = 2;
	public static final int LEAVE_EAST    = 3;
	
}
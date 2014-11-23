package cz.fit.sin.model.road;

import cz.fit.sin.model.intersection.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Marek Salát
 * Date: 20. 11. 2014
 * Time: 15:36
 */
public class Road {
    public int capacity = 0;

    public Map<Direction, Integer> line = new HashMap<>();

    public RoadEndPoint from;
    public RoadEndPoint to;

    public Road(){
        this(21);
    }

    public Road(int capacity){
        this.capacity = capacity;
        line.put(Direction.LEFT, 0);
        line.put(Direction.RIGHT, 0);
        line.put(Direction.FORWARD, 0);
    }


    public int getVehiclesCount(){
        return line.get(Direction.LEFT) + line.get(Direction.RIGHT) + line.get(Direction.FORWARD);
    }

    public int getVehiclesCount(Direction direction) {
        return  line.get(direction);
    }

    public void setEndPoints(RoadEndPoint from, RoadEndPoint to){
        this.from = from;
        this.to = to;
    }

}

package cz.fit.sin.model.road;

import cz.fit.sin.model.intersection.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Marek Salát
 * Date: 20. 11. 2014
 * Time: 15:36
 */
public class IntRoad extends AbstractRoad {

    public Map<Direction, Integer> line = new HashMap<>();

    public IntRoad(int capacity){
        super(capacity);
        line.put(Direction.LEFT, 0);
        line.put(Direction.RIGHT, 0);
        line.put(Direction.FORWARD, 0);
    }


    @Override
    public int getVehiclesCount(){
        return line.get(Direction.LEFT) + line.get(Direction.RIGHT) + line.get(Direction.FORWARD);
    }

    @Override
    public int getVehiclesCount(Direction direction) {
        return  line.get(direction);
    }

}

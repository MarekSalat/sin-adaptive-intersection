package cz.fit.sin.model.road;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.vehicles.Vehicle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * User: Marek Salát
 * Date: 25. 11. 2014
 * Time: 18:25
 */
public class QueueRoad extends AbstractRoad{

    public Map<Direction, LinkedList<Vehicle>> line = new HashMap<>();

    public QueueRoad(int capacity){
        super(capacity);
        line.put(Direction.LEFT, new LinkedList<Vehicle>());
        line.put(Direction.RIGHT, new LinkedList<Vehicle>());
        line.put(Direction.FORWARD, new LinkedList<Vehicle>());
    }

    public boolean putVehicle(Direction direction, Vehicle vehicle){
        if(getVehiclesCount() >= capacity)
            return false;
        line.get(direction).addLast(vehicle);
        return true;
    }

    public boolean isFirst(Vehicle vehicle){
        return isFirst(Direction.LEFT, vehicle) || isFirst(Direction.RIGHT, vehicle) || isFirst(Direction.FORWARD, vehicle);
    }

    public boolean isFirst(Direction direction, Vehicle vehicle){
        if(line.get(direction).size() <= 0) return false;
        return line.get(direction).getFirst() == vehicle;
    }

    public void removeFirstVehicle(Direction direction){
        line.get(direction).removeFirst();
    }

    @Override
    public int getVehiclesCount() {
        return line.get(Direction.LEFT).size() + line.get(Direction.RIGHT).size() + line.get(Direction.FORWARD).size();
    }

    @Override
    public int getVehiclesCount(Direction direction) {
        return line.get(direction).size();
    }
}

package cz.fit.sin.model.road;

import cz.fit.sin.model.intersection.Direction;

/**
 * User: Marek Salát
 * Date: 25. 11. 2014
 * Time: 18:29
 */
public abstract class AbstractRoad implements Road {
    public int capacity = 0;

    public RoadEndPoint from;
    public RoadEndPoint to;

    public AbstractRoad(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void setEndPoints(RoadEndPoint from, RoadEndPoint to){
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean isFull(){
        return isFull(Direction.FORWARD) && isFull(Direction.LEFT) && isFull(Direction.RIGHT);
    }

    @Override
    public boolean isFull(Direction direction) {
        return getVehiclesCount(direction) >= capacity;
    }

    @Override
    public boolean isEmpty(){
        return getVehiclesCount() <= 0;
    }

    @Override
    public boolean isEmpty(Direction direction){
        return getVehiclesCount(direction) <= 0;
    }
}

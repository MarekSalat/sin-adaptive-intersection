package cz.fit.sin.model.road;

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
}

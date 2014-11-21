package cz.fit.sin.model.road;

/**
 * User: Marek Salát
 * Date: 20. 11. 2014
 * Time: 15:36
 */
public class Road {
    public int capacity = 0;
    public int vehiclesCount = 0;

    public RoadEndPoint from;
    public RoadEndPoint to;

    public Road(){
        this(21);
    }

    public Road(int capacity){
        this.capacity = capacity;
    }


    public int getVehiclesCount(){
        return vehiclesCount;
    }

    public void setEndPoints(RoadEndPoint from, RoadEndPoint to){
        this.from = from;
        this.to = to;
    }
}

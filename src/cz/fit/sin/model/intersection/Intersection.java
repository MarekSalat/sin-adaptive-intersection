package cz.fit.sin.model.intersection;

import cz.fit.sin.model.road.Road;
import cz.fit.sin.model.road.RoadEndPoint;

import java.util.HashMap;
import java.util.Map;

/**
* User: Marek Salát
* Date: 17.11.14
* Time: 9:50
*/
public class Intersection implements RoadEndPoint {

    public Map<Orientation, Road> incoming = new HashMap<>();
    public Map<Orientation, Road> outgoing = new HashMap<>();

    public Map<Orientation, Map<Direction, Semaphore>> semaphores = new HashMap<>();


    public Road getOutgoingRoadFor(Orientation orientation) {
        return outgoing.get(orientation);
    }

    public Road getIncomingRoadFor(Orientation orientation){
        return incoming.get(orientation);
    }

    public int getOutgoingRoadVehicleCount(Orientation orientation) {
        return getOutgoingRoadFor(orientation).getVehiclesCount();
    }

    public int getIncomingRoadVehicleCount(Orientation orientation){
        return getIncomingRoadFor(orientation).getVehiclesCount();
    }

    public int getIncomingRoadVehicleCount(Orientation orientation, Direction direction){
        return getIncomingRoadFor(orientation).getVehiclesCount(direction);
    }

    public void setRedLights() {
        for (Orientation _orientation : semaphores.keySet()) {
            for (Direction _direction : semaphores.get(_orientation).keySet()) {
                setSemaphoreLight(_orientation, _direction, Semaphore.Light.RED);
            }
        }
    }

    public void setSemaphoreLight(Orientation orientation, Direction direction, Semaphore.Light light) {
        getSemaphore(orientation, direction).state = light;
    }

    public Semaphore getSemaphore(Orientation orientation, Direction direction){
        return semaphores.get(orientation).get(direction);
    }

//    public Map<Direction, Semaphore> getSemaphores(Orientation orientation){
//        return semaphores.get(orientation);
//    }
}

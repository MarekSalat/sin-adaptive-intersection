package cz.fit.sin.model.road;

import cz.fit.sin.model.intersection.Direction;

/**
 * User: Marek Salát
 * Date: 23. 11. 2014
 * Time: 13:26
 */
public interface Road {
    boolean isFull();
    boolean isFull(Direction direction);
    boolean isEmpty();
    boolean isEmpty(Direction forward);

    int getVehiclesCount();

    int getVehiclesCount(Direction direction);

    void setEndPoints(RoadEndPoint from, RoadEndPoint to);

}

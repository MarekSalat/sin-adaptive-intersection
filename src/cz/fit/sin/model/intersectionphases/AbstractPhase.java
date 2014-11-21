package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.intersection.Semaphore;

/**
 * User: Marek Salát
 * Date: 20. 11. 2014
 * Time: 13:52
 */
public abstract class AbstractPhase  implements IntersectionPhase {

    abstract public Orientation getOrientation();

    public int getQueueNum(Intersection intersection) {
        return intersection.getIncomingRoadVehicleCount(getOrientation());
    }

    public int getFrontNum(Intersection intersection) {
        int frontNum = 0;

        for (Orientation _orientation : Orientation.values()) {
            if (_orientation == getOrientation())
                continue;

            frontNum += intersection.getOutgoingRoadVehicleCount(_orientation);
        }

        return frontNum;
    }

    public int getRedTime(Intersection intersection) {
        int redTime = 0;
        for (Orientation _orientation : Orientation.values()){
            if(_orientation == getOrientation())
                continue;

            for(Direction _direction : Direction.values()){
                if (_direction == Direction.CURRENT)
                    continue;

                if (intersection.getSemaphore(_orientation, _direction).state != Semaphore.Light.RED)
                    continue;

                redTime += intersection.getIncomingRoadVehicleCount(getOrientation().toAbsolute(_direction));
            }
        }
        return redTime;
    }
}

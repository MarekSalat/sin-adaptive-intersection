package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.intersection.*;
import cz.fit.sin.utils.Pair;

import java.util.List;

/**
 * User: Marek Salát
 * Date: 20. 11. 2014
 * Time: 13:52
 */
public abstract class AbstractPhase  implements IntersectionPhase {
    private final int CAR_OUTFLOW;
    private final boolean OPTIMIZE = false;

    private Orientation orientation;

    public AbstractPhase(Orientation orientation){
        this(orientation, Integer.MAX_VALUE-1);
    }

    public AbstractPhase(Orientation orientation, int carOutflowPerTimeUnit){
        this.orientation = orientation;
        this.CAR_OUTFLOW = carOutflowPerTimeUnit;
    }

    public Orientation getOrientation(){
        return orientation;
    }

    public abstract List<Pair<Orientation, Direction>> getActiveSemaphoresInPhase();

    @Override
    public void setSemaphoreLights(Intersection intersection) {
        intersection.setRedLights();

        for (Pair<Orientation, Direction> pair : getActiveSemaphoresInPhase()){
            intersection.setSemaphoreLight(pair.first, pair.second, Light.GREEN);
        }
    }

    @Override
    public int getQueueNum(Intersection intersection) {
        int finalQueueNum = 0;

        for (Pair<Orientation, Direction> pair : getActiveSemaphoresInPhase()) {
            int queueNum = intersection.getIncomingRoadVehicleCount(pair.first, pair.second);
            finalQueueNum += OPTIMIZE && queueNum > CAR_OUTFLOW ? CAR_OUTFLOW : queueNum;
        }

        return finalQueueNum;
    }

    @Override
    public int getFrontNum(Intersection intersection) {
        int frontNum = 0;

        final boolean []visited = {false, false, false, false};

        for (Pair<Orientation, Direction> pair : getActiveSemaphoresInPhase()) {
            Orientation _orientation = pair.first.toAbsolute(pair.second);

            if(visited[_orientation.ordinal()])
                continue;
            visited[_orientation.ordinal()] = true;

            frontNum += intersection.getOutgoingRoadVehicleCount(_orientation);
        }

        return frontNum;
    }

    @Override
    public int getRedTime(Intersection intersection) {
        int total = 0;
        // get total number of vehicles on incoming roads
        for (Orientation _orientation : Orientation.values()){
            int count = intersection.getIncomingRoadVehicleCount(_orientation);
            total += OPTIMIZE && count > CAR_OUTFLOW ? CAR_OUTFLOW : count;
        }

        return total - getQueueNum(intersection);
    }
}

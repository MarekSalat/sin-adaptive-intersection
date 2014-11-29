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

    private Orientation orientation;

    public AbstractPhase(Orientation orientation){
        this.orientation = orientation;
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

    public int getQueueNum(Intersection intersection) {
        int queueNum = 0;

        for (Pair<Orientation, Direction> pair : getActiveSemaphoresInPhase()) {
            queueNum += intersection.getIncomingRoadVehicleCount(pair.first, pair.second);
        }

        return queueNum;
    }

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

    public int getRedTime(Intersection intersection) {
        int total = 0;
        // get total number of vehicles on incoming roads
        for (Orientation _orientation : Orientation.values()){
            total += intersection.getIncomingRoadVehicleCount(_orientation);
        }

        int onGreen = 0;
        // get number of vehicles on incoming roads for green phase
        for (Pair<Orientation, Direction> pair : getActiveSemaphoresInPhase()) {
            onGreen += intersection.getIncomingRoadVehicleCount(pair.first, pair.second);
        }

        return total - onGreen;
    }
}

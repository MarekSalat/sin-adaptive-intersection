package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.intersection.Semaphore;

import java.util.List;

/**
 * User: Marek Salát
 * Date: 20. 11. 2014
 * Time: 13:52
 */
public abstract class AbstractPhase  implements IntersectionPhase {

    protected static class Pair<L,R> {
        public final L first;
        public final R second;

        public Pair(L left, R right) {
            this.first = left;
            this.second = right;
        }

        public static <L,R> Pair<L,R> of(L left, R right){
            return new Pair<L,R>(left, right);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
            if (second != null ? !second.equals(pair.second) : pair.second != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }
    }

    private Orientation orientation;

    public AbstractPhase(){}

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
            intersection.getSemaphore(pair.first, pair.second).state = Semaphore.Light.GREEN;
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
            if(visited[pair.first.ordinal()])
                continue;
            visited[pair.first.ordinal()] = true;

            frontNum += intersection.getOutgoingRoadVehicleCount(pair.first.toAbsolute(pair.second));
        }

        return frontNum;
    }

    public int getRedTime(Intersection intersection) {
        int total = 0;

        for (Orientation _orientation : Orientation.values()){
            total += intersection.getIncomingRoadVehicleCount(_orientation);
        }

        return total - getFrontNum(intersection);
    }
}

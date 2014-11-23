package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;

import java.util.ArrayList;
import java.util.List;

/**
* User: Marek Salát
* Date: 17.11.14
* Time: 9:51
 *
 *  <-.    ^
 *      \  |
 *  -.   | |  .->
 *    |  | | |
 *    v  | | |
*/
public class SimplePhase extends AbstractPhase {

    List<Pair<Orientation, Direction>> activeSemaphores;

    public SimplePhase(Orientation orientation) {
        super(orientation);

        activeSemaphores = new ArrayList<>();

        activeSemaphores.add(Pair.of(getOrientation(), Direction.LEFT));
        activeSemaphores.add(Pair.of(getOrientation(), Direction.RIGHT));
        activeSemaphores.add(Pair.of(getOrientation(), Direction.FORWARD));
        activeSemaphores.add(Pair.of(getOrientation().toAbsolute(Direction.LEFT), Direction.RIGHT));
    }

    @Override
    public List<Pair<Orientation, Direction>> getActiveSemaphoresInPhase() {
        return activeSemaphores;
    }
}

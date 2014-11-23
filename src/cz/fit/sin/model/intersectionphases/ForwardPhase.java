package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Marek Salát
 * Date: 21. 11. 2014
 * Time: 21:04
 *
 *     |  ^
 *     |  |
 *     |  |
 *     |  |
 *     v  |
 */
public class ForwardPhase extends AbstractPhase {
    List<Pair<Orientation, Direction>> activeSemaphores;

    public ForwardPhase(Orientation orientation) {
        super(orientation);

        activeSemaphores = new ArrayList<>();

        activeSemaphores.add(Pair.of(getOrientation(), Direction.FORWARD));
        activeSemaphores.add(Pair.of(getOrientation().opposite(), Direction.FORWARD));
    }

    @Override
    public List<Pair<Orientation, Direction>> getActiveSemaphoresInPhase() {
        return activeSemaphores;
    }
}

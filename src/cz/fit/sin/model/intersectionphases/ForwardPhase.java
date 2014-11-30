package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.utils.Pair;

import java.util.Arrays;
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
    List<Pair<Orientation, Direction>> activeSemaphores = Arrays.asList(
        Pair.of(getOrientation(), Direction.FORWARD),
        Pair.of(getOrientation().opposite(), Direction.FORWARD)
    );

    public ForwardPhase(Orientation orientation) {
        super(orientation);
    }

    public ForwardPhase(Orientation orientation, int carFlowPerTimeUnit) {
        super(orientation, carFlowPerTimeUnit);
    }

    @Override
    public List<Pair<Orientation, Direction>> getActiveSemaphoresInPhase() {
        return activeSemaphores;
    }
}

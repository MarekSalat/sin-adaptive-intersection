package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.utils.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * User: Marek Salát
 * Date: 21. 11. 2014
 * Time: 21:55
 *
 *     | | ^
 *  <-ˇ  | |
 *       | |  .->
 *       v | |
 */
public class RightForwardPhase extends AbstractPhase {
    List<Pair<Orientation, Direction>> activeSemaphores = Arrays.asList(
        Pair.of(getOrientation(), Direction.RIGHT),
        Pair.of(getOrientation(), Direction.FORWARD),
        Pair.of(getOrientation().opposite(), Direction.RIGHT),
        Pair.of(getOrientation().opposite(), Direction.FORWARD)
    );

    public RightForwardPhase(Orientation orientation) {
        super(orientation);
    }

    @Override
    public List<Pair<Orientation, Direction>> getActiveSemaphoresInPhase() {
        return activeSemaphores;
    }
}

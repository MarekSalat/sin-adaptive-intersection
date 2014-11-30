package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.utils.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * User: Marek Salát
 * Date: 21. 11. 2014
 * Time: 21:58
 *
 * <-.
 *     \
 *  -.  |  .->
 *    | | |
 *    v | |
 *
 */
public class LeftRightPhase extends AbstractPhase {
    List<Pair<Orientation, Direction>> activeSemaphores = Arrays.asList(
        Pair.of(getOrientation(), Direction.LEFT),
        Pair.of(getOrientation(), Direction.RIGHT),
        Pair.of(getOrientation().toAbsolute(Direction.LEFT), Direction.RIGHT)
    );

    public LeftRightPhase(Orientation orientation) {
        super(orientation);
    }

    public LeftRightPhase(Orientation orientation, int carFlowPerTimeUnit) {
        super(orientation, carFlowPerTimeUnit);
    }

    @Override
    public List<Pair<Orientation, Direction>> getActiveSemaphoresInPhase() {
        return activeSemaphores;
    }
}

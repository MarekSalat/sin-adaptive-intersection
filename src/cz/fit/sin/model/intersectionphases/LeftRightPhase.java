package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;

import java.util.ArrayList;
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
    List<Pair<Orientation, Direction>> activeSemaphores;

    public LeftRightPhase(Orientation orientation) {
        super(orientation);

        activeSemaphores = new ArrayList<>();

        activeSemaphores.add(Pair.of(getOrientation(), Direction.LEFT));
        activeSemaphores.add(Pair.of(getOrientation(), Direction.RIGHT));
        activeSemaphores.add(Pair.of(getOrientation().toAbsolute(Direction.LEFT), Direction.RIGHT));
    }

    @Override
    public List<Pair<Orientation, Direction>> getActiveSemaphoresInPhase() {
        return activeSemaphores;
    }
}

package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Orientation;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Marek Salát
 * Date: 21. 11. 2014
 * Time: 21:54
 *
 *         |
 *   <-.   |
 *      \  |
 *       |  \
 *       |   ´->
 *
 */
public class LeftPhase extends AbstractPhase {
    List<AbstractPhase.Pair<Orientation, Direction>> activeSemaphores;

    public LeftPhase(Orientation orientation) {
        super(orientation);

        activeSemaphores = new ArrayList<>();

        activeSemaphores.add(AbstractPhase.Pair.of(getOrientation(), Direction.LEFT));
        activeSemaphores.add(AbstractPhase.Pair.of(getOrientation().opposite(), Direction.LEFT));
    }

    @Override
    public List<AbstractPhase.Pair<Orientation, Direction>> getActiveSemaphoresInPhase() {
        return activeSemaphores;
    }
}

package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.intersection.Semaphore;

/**
* User: Marek Salát
* Date: 17.11.14
* Time: 9:51
 *
 *      ^
 * <-.  |
 *    \ |  .->
 *     || /
 *     |||
*/
public class SimplePhase extends AbstractPhase {

    private Orientation orientation;

    public SimplePhase(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public void setSemaphoreLights(Intersection intersection) {
        intersection.setRedLights();

        intersection.setSemaphoreLight(orientation, Direction.LEFT, Semaphore.Light.GREEN);
        intersection.setSemaphoreLight(orientation, Direction.RIGHT, Semaphore.Light.GREEN);
        intersection.setSemaphoreLight(orientation, Direction.FORWARD, Semaphore.Light.GREEN);
    }

    @Override
    public Orientation getOrientation() {
        return orientation;
    }
}

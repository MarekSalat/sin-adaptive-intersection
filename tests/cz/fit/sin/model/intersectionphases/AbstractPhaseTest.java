package cz.fit.sin.model.intersectionphases;

import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.WorldBuilder;
import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.intersection.Semaphore;
import cz.fit.sin.model.road.IntRoad;
import cz.fit.sin.model.road.Road;
import cz.fit.sin.model.world.World;
import cz.fit.sin.model.world.WorldObject;
import cz.fit.sin.utils.Pair;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AbstractPhaseTest {

    private World world;
    private IntersectionPhase phase;
    private Intersection intersection;

    @Before
    public void setUp() throws Exception {
        WorldBuilder wb = new WorldBuilder();

        wb.registerFactory(Road.class, new WorldBuilder.Factory() {
            @Override
            public Object create() {
                return new IntRoad(111);
            }
        });

        WorldObject<Intersection> a = wb.add(Intersection.class);
        intersection = a.object;

        wb.addRoadsAt(a, Orientation.NORTH);
        wb.addRoadsAt(a, Orientation.EAST);
        wb.addRoadsAt(a, Orientation.SOUTH);
        wb.addRoadsAt(a, Orientation.WEST);

        world = wb.done();

        for (WorldObject<Road> woRoad : world.roads) {
            IntRoad road = (IntRoad) woRoad.object;
            road.line.put(Direction.FORWARD, 100);
            road.line.put(Direction.RIGHT, 10);
            road.line.put(Direction.LEFT, 1);
        }
    }

    @Test
    public void testSetSemaphoreLights() throws Exception {
        phase = new SimplePhase(Orientation.SOUTH);

        phase.setSemaphoreLights(intersection);

        Assert.assertSame(Semaphore.Light.GREEN, intersection.getSemaphoreLight(Orientation.SOUTH, Direction.LEFT));
        Assert.assertSame(Semaphore.Light.GREEN, intersection.getSemaphoreLight(Orientation.SOUTH, Direction.FORWARD));
        Assert.assertSame(Semaphore.Light.GREEN, intersection.getSemaphoreLight(Orientation.SOUTH, Direction.RIGHT));

        Assert.assertSame(Semaphore.Light.RED, intersection.getSemaphoreLight(Orientation.WEST, Direction.LEFT));
        Assert.assertSame(Semaphore.Light.RED, intersection.getSemaphoreLight(Orientation.WEST, Direction.FORWARD));
        Assert.assertSame(Semaphore.Light.GREEN, intersection.getSemaphoreLight(Orientation.WEST, Direction.RIGHT));

        Assert.assertSame(Semaphore.Light.RED, intersection.getSemaphoreLight(Orientation.NORTH, Direction.LEFT));
        Assert.assertSame(Semaphore.Light.RED, intersection.getSemaphoreLight(Orientation.NORTH, Direction.FORWARD));
        Assert.assertSame(Semaphore.Light.RED, intersection.getSemaphoreLight(Orientation.NORTH, Direction.RIGHT));

        Assert.assertSame(Semaphore.Light.RED, intersection.getSemaphoreLight(Orientation.EAST, Direction.LEFT));
        Assert.assertSame(Semaphore.Light.RED, intersection.getSemaphoreLight(Orientation.EAST, Direction.FORWARD));
        Assert.assertSame(Semaphore.Light.RED, intersection.getSemaphoreLight(Orientation.EAST, Direction.RIGHT));
    }

    @Test
    public void testGetQueueNum() throws Exception {
        phase = new SimplePhase(Orientation.SOUTH);
        Assert.assertEquals(1 + 10 + 100 + 10, phase.getQueueNum(intersection));

        phase = new ForwardPhase(Orientation.SOUTH);
        Assert.assertEquals(100 + 100, phase.getQueueNum(intersection));

        phase = new LeftPhase(Orientation.SOUTH);
        Assert.assertEquals(1 + 1, phase.getQueueNum(intersection));

        phase = new LeftRightPhase(Orientation.SOUTH);
        Assert.assertEquals(1 + 10 + 10, phase.getQueueNum(intersection));

        phase = new RightForwardPhase(Orientation.SOUTH);
        Assert.assertEquals(100 + 10 + 100 + 10, phase.getQueueNum(intersection));
    }

    @Test
    public void testGetFrontNum() throws Exception {
        final int count = 1+10+100;

        phase = new SimplePhase(Orientation.SOUTH);
        Assert.assertEquals(4 * count, phase.getFrontNum(intersection));

        phase = new ForwardPhase(Orientation.SOUTH);
        Assert.assertEquals(2 * count, phase.getFrontNum(intersection));

        phase = new LeftPhase(Orientation.SOUTH);
        Assert.assertEquals(2 * count, phase.getFrontNum(intersection));

        phase = new LeftRightPhase(Orientation.SOUTH);
        Assert.assertEquals(3 * count, phase.getFrontNum(intersection));

        phase = new RightForwardPhase(Orientation.SOUTH);
        Assert.assertEquals(4 * count, phase.getFrontNum(intersection));

        phase = new AbstractPhase(Orientation.SOUTH) {
            @Override
            public List<Pair<Orientation, Direction>> getActiveSemaphoresInPhase() {
                return Arrays.asList(
                        Pair.of(getOrientation(), Direction.LEFT),
                        Pair.of(getOrientation().opposite(), Direction.RIGHT)
                );
            }
        };
        Assert.assertEquals(count, phase.getFrontNum(intersection));
    }

    @Test
    public void testGetRedTime() throws Exception {
        final int total = 4*(1+10+100);

        phase = new SimplePhase(Orientation.SOUTH);
        Assert.assertEquals(total - (1 + 10 + 100 + 10), phase.getRedTime(intersection));

        phase = new ForwardPhase(Orientation.SOUTH);
        Assert.assertEquals(total - (100 + 100), phase.getRedTime(intersection));

        phase = new LeftPhase(Orientation.SOUTH);
        Assert.assertEquals(total - (1 + 1), phase.getRedTime(intersection));

        phase = new LeftRightPhase(Orientation.SOUTH);
        Assert.assertEquals(total - (1 + 10 + 10), phase.getRedTime(intersection));

        phase = new RightForwardPhase(Orientation.SOUTH);
        Assert.assertEquals(total - (100 + 10 + 100 + 10), phase.getRedTime(intersection));
    }
}
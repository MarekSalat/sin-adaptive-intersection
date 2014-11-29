package cz.fit.sin.model.fuzzy;

import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.WorldBuilder;
import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.intersectionphases.*;
import cz.fit.sin.model.road.IntRoad;
import cz.fit.sin.model.world.World;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class IntersectionFuzzyEngineTest {

    private World world;
    private Intersection intersection;
    private IntersectionFuzzyEngine engine;
    private List<IntersectionPhase> simplePhases;

    @Before
    public void setUp() throws Exception {
        world = WorldBuilder.createSimpleWorld();
        intersection = world.intersections.get(0).object;

        engine = new IntersectionFuzzyEngine();

        simplePhases = Arrays.asList(new IntersectionPhase[]{
                new SimplePhase(Orientation.NORTH),
                new SimplePhase(Orientation.EAST),
                new SimplePhase(Orientation.SOUTH),
                new SimplePhase(Orientation.WEST),
        });
    }

    @Test
    public void testNextPhase_simplePhases() throws Exception {
        IntRoad road = (IntRoad) intersection.getIncomingRoadFor(Orientation.SOUTH);
        road.line.put(Direction.FORWARD, 15);

        // EAST phase
        IntersectionPhase greenPhase = simplePhases.get(1);

        IntersectionPhase newPhase = engine.nextPhase(intersection, simplePhases, greenPhase);

        Assert.assertNotNull(newPhase);
        Assert.assertNotSame(newPhase, greenPhase);
        // expects SOUTH phase
        Assert.assertSame(newPhase, simplePhases.get(2));

        greenPhase = newPhase;
        newPhase = engine.nextPhase(intersection, simplePhases, greenPhase);
        // still expects SOUTH phase
        Assert.assertSame(newPhase, greenPhase);

        road.line.put(Direction.FORWARD, 0);
        road = (IntRoad) intersection.getIncomingRoadFor(Orientation.NORTH);
        road.line.put(Direction.FORWARD, 10);

        newPhase = engine.nextPhase(intersection, simplePhases, greenPhase);
        Assert.assertNotSame(newPhase, greenPhase);
        // expects NORTH phase
        Assert.assertSame(simplePhases.get(0), newPhase);
    }

    @Test
    public void testNextPhase_foo() throws Exception {
        // 11ec4639 (extend  ???) |> queueNum=0, frontNum=0

        // 10bfbefc (urgency ???) |> queueNum=11, frontNum=0, redTime=33
        // 1c939afb (urgency ???) |> queueNum=16, frontNum=0, redTime=28
        // 1ea3e365 (urgency ???) |> queueNum=17, frontNum=0, redTime=27
        simplePhases = Arrays.asList(
                new IntersectionPhase() {

                    @Override
                    public void setSemaphoreLights(Intersection intersection) {

                    }

                    @Override
                    public int getQueueNum(Intersection intersection) {
                        return 0;
                    }

                    @Override
                    public int getFrontNum(Intersection intersection) {
                        return 0;
                    }

                    @Override
                    public int getRedTime(Intersection intersection) {
                        return 0;
                    }
                },
                new IntersectionPhase() {

                    @Override
                    public void setSemaphoreLights(Intersection intersection) {

                    }

                    @Override
                    public int getQueueNum(Intersection intersection) {
                        return 11;
                    }

                    @Override
                    public int getFrontNum(Intersection intersection) {
                        return 0;
                    }

                    @Override
                    public int getRedTime(Intersection intersection) {
                        return 33;
                    }
                },
                new IntersectionPhase() {
                    @Override
                    public void setSemaphoreLights(Intersection intersection) {

                    }

                    @Override
                    public int getQueueNum(Intersection intersection) {
                        return 16;
                    }

                    @Override
                    public int getFrontNum(Intersection intersection) {
                        return 0;
                    }

                    @Override
                    public int getRedTime(Intersection intersection) {
                        return 28;
                    }
                },
                new IntersectionPhase() {
                    @Override
                    public void setSemaphoreLights(Intersection intersection) {

                    }

                    @Override
                    public int getQueueNum(Intersection intersection) {
                        return 17;
                    }

                    @Override
                    public int getFrontNum(Intersection intersection) {
                        return 0;
                    }

                    @Override
                    public int getRedTime(Intersection intersection) {
                        return 27;
                    }
                }
        );

        IntersectionPhase greenPhase = simplePhases.get(0);

        IntersectionPhase newPhase = engine.nextPhase(null, simplePhases, greenPhase);
        Assert.assertNotSame(newPhase, greenPhase);
    }
}
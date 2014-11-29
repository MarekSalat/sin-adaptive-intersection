package cz.fit.sin.model.intersection;

import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.WorldBuilder;
import cz.fit.sin.model.fuzzy.IntersectionFuzzyEngine;
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
    private List<AbstractPhase> phases;

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

        phases = Arrays.asList(
                new SimplePhase(Orientation.NORTH),
                new SimplePhase(Orientation.EAST),
                new SimplePhase(Orientation.SOUTH),
                new SimplePhase(Orientation.WEST),

                new ForwardPhase(Orientation.NORTH),
                new ForwardPhase(Orientation.WEST),

                new LeftPhase(Orientation.NORTH),
                new LeftPhase(Orientation.WEST),

                new LeftRightPhase(Orientation.NORTH),
                new LeftRightPhase(Orientation.EAST),
                new LeftRightPhase(Orientation.SOUTH),
                new LeftRightPhase(Orientation.WEST),

                new RightForwardPhase(Orientation.NORTH),
                new RightForwardPhase(Orientation.WEST)
        );
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
}
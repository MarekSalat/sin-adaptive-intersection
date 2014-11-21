package cz.fit.sin.model.fuzzy;

import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.WorldBuilder;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.intersectionphases.SimplePhase;
import cz.fit.sin.model.road.Road;
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
    private List<IntersectionPhase> phases;

    @Before
    public void setUp() throws Exception {
        world = WorldBuilder.createSimpleWorld();
        intersection = world.intersections.get(0).object;

        engine = new IntersectionFuzzyEngine();

        phases = Arrays.asList(new IntersectionPhase[]{
                new SimplePhase(Orientation.NORTH),
                new SimplePhase(Orientation.EAST),
                new SimplePhase(Orientation.SOUTH),
                new SimplePhase(Orientation.WEST),
        });
    }

    @Test
    public void testNextPhase() throws Exception {
        Road road = intersection.getIncomingRoadFor(Orientation.SOUTH);
        road.vehiclesCount = 15;

        IntersectionPhase greenPhase = phases.get(1);

        IntersectionPhase newPhase = engine.nextPhase(intersection, phases, greenPhase);

        Assert.assertNotNull(newPhase);
        Assert.assertNotSame(newPhase, greenPhase);
        Assert.assertSame(newPhase, phases.get(2));

        greenPhase = newPhase;
        newPhase = engine.nextPhase(intersection, phases, greenPhase);
        Assert.assertNotNull(newPhase);
        Assert.assertSame(newPhase, greenPhase);
    }
}
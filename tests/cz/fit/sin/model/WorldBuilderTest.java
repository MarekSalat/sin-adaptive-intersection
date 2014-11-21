package cz.fit.sin.model;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.road.Road;
import cz.fit.sin.model.world.World;
import cz.fit.sin.model.world.WorldObject;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Marek Salát
 * Date: 17.11.14
 * Time: 11:06
 */
public class WorldBuilderTest {

    private World world;

    @Before
    public void setUp() throws Exception {
        world = WorldBuilder.createSimpleWorld();
    }

    @Test
    public void testWorldBuilder(){
        WorldBuilder wb = new WorldBuilder();

        WorldObject<Intersection> a = wb.add(Intersection.class);
        a.properties.name = "A";
        WorldObject<Intersection> b = wb.add(Intersection.class);
        b.properties.name = "B";

        WorldBuilder.ConnectResult connectResult = wb.connect(a, b, Orientation.EAST);
        connectResult.b2a.properties.name = "Foo";
        connectResult.semaphores.get(Direction.LEFT).properties.name = "Wall-e";

        WorldBuilder.AddRoadsResult roadsResult = wb.addRoadsAt(a, Orientation.NORTH);
        roadsResult.roadToA.properties.name = "kvetna";

        wb.addRoadsAt(a, Orientation.SOUTH);
        wb.addRoadsAt(a, Orientation.WEST);

        wb.addRoadsAt(b, Orientation.NORTH);
        wb.addRoadsAt(b, Orientation.SOUTH);
        wb.addRoadsAt(b, Orientation.EAST);
    }

    @Test
    public void testBar() throws Exception {
        WorldObject<Intersection> a = world.findByName(Intersection.class, "A");
        WorldObject<Intersection> b = world.findByName(Intersection.class, "B");

        Assert.assertEquals(a.object.getOutgoingRoadFor(Orientation.SOUTH.toAbsolute(Direction.RIGHT)), b.object.incoming.get(Orientation.WEST));
        Assert.assertEquals(b.object.getOutgoingRoadFor(Orientation.NORTH.toAbsolute(Direction.RIGHT)), a.object.incoming.get(Orientation.EAST));

        Assert.assertEquals(3, a.object.semaphores.get(Orientation.SOUTH).size());

        Assert.assertEquals(a, world.findById(Intersection.class, a.properties.id));
        Assert.assertEquals(a, world.find(a.object));
    }

    @Test
    public void testFoo() throws Exception {
        WorldBuilder wb = new WorldBuilder();
        Assert.assertFalse(wb.hasFactory(Road.class));

        wb.registerFactory(Road.class, new WorldBuilder.Factory() {
            @Override
            public Object create() {
                return new Road(20);
            }
        });

        Assert.assertNotNull(wb.getFactory(Road.class));
    }
}

package cz.fit.sin.model;

import cz.fit.sin.model.intersection.Direction;
import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Orientation;
import cz.fit.sin.model.intersection.Semaphore;
import cz.fit.sin.model.road.IntRoad;
import cz.fit.sin.model.road.Road;
import cz.fit.sin.model.road.endpoints.RoadEnding;
import cz.fit.sin.model.road.endpoints.RoadSpawn;
import cz.fit.sin.model.world.Properties;
import cz.fit.sin.model.world.World;
import cz.fit.sin.model.world.WorldObject;

import java.util.HashMap;
import java.util.Map;

/**
 * todo: jeste mi chybi nejak poresit/domyslet jak pri pridavani semaforu upravit propertires atd...
 * User: Marek Salát
 * Date: 17.11.14
 * Time: 10:16
 */
public class WorldBuilder {
    private final World world = new World();

    public WorldBuilder(){
        registerFactory(Road.class, new Factory() {
            @Override
            public Object create() {
                return new IntRoad(16);
            }
        });
    }

    public static interface Factory<T> {
        T create();
    }

    Map<Class, Factory> factories = new HashMap<>();

    public void registerFactory(Class klass, Factory factory){
        factories.put(klass, factory);
    }

    public boolean hasFactory(Class klass) {
        return factories.containsKey(klass);
    }

    public<T> Factory<T> getFactory(Class<T> klass) {
        return (Factory<T>) factories.get(klass);
    }

    public World done(){
        return world;
    }

    public<T> T create(Class<T> klass){
        if (hasFactory(klass))
            return getFactory(klass).create();

        try {
            return klass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public<T> WorldObject<T> add(Class<T> klass){
        return add(klass, create(Properties.class));
    }

    public<T> WorldObject<T> add(Class<T> klass, Properties properties){
        WorldObject<T> worldObject = new WorldObject<>(create(klass), properties);
        world.getAll(klass).add(worldObject);
        return worldObject;
    }

    public static class ConnectResult {
        public WorldObject<Road> a2b;
        public WorldObject<Road> b2a;
        public Map<Direction, WorldObject<Semaphore>> semaphores;

        public ConnectResult(WorldObject<Road> a2b, WorldObject<Road> b2a,Map<Direction, WorldObject<Semaphore>> semaphore) {
            this.a2b = a2b;
            this.b2a = b2a;
            this.semaphores = semaphore;
        }
    }

    public ConnectResult connect(WorldObject<Intersection> a, WorldObject<Intersection> b, Orientation bAt) {

        // createSimpleWorld roads
        WorldObject<Road> a2b = add(Road.class);
        WorldObject<Road> b2a = add(Road.class);

        // connect roads
        a2b.object.setEndPoints(a.object, b.object);
        // set b2a as incoming road for a
        a.object.incoming.put(bAt, b2a.object);
        // set a2b as outgoing road for a
        a.object.outgoing.put(bAt, a2b.object);

        Map<Direction, WorldObject<Semaphore>> semaphores = addSemaphoresAt(a, bAt);

        // connect roads
        b2a.object.setEndPoints(b.object, a.object);
        // set b2a as outgoing road for b
        b.object.outgoing.put(bAt.opposite(), b2a.object);
        // set a2b as incoming road for b
        b.object.incoming.put(bAt.opposite(), a2b.object);

        addSemaphoresAt(b, bAt.opposite());

        return new ConnectResult(a2b, b2a, semaphores);
    }

    public class AddRoadsResult {
        WorldObject<Road> roadToA;
        WorldObject<Road> roadFromA;
        WorldObject<RoadSpawn> roadSpawn;
        WorldObject<RoadEnding> roadEnding;
        Map<Direction, WorldObject<Semaphore>> semaphores;

        public AddRoadsResult(WorldObject<Road> roadToA, WorldObject<Road> roadFromA, WorldObject<RoadSpawn> roadSpawn, WorldObject<RoadEnding> roadEnding, Map<Direction, WorldObject<Semaphore>> semaphores) {
            this.roadToA = roadToA;
            this.roadFromA = roadFromA;
            this.roadSpawn = roadSpawn;
            this.roadEnding = roadEnding;
            this.semaphores = semaphores;
        }
    }
    /**
     * Adds RoadEnding for outgoing road, adds RoadSpawn for incoming roads
     *
     * @param a
     * @param at
     */
    public AddRoadsResult addRoadsAt(WorldObject<Intersection> a, Orientation at) {
        // createSimpleWorld roads
        WorldObject<Road> roadToA = add(Road.class);
        WorldObject<Road> roadFromA = add(Road.class);

        // createSimpleWorld endpoints
        WorldObject<RoadSpawn> roadSpawn = add(RoadSpawn.class); roadSpawn.object.road = roadToA.object;
        WorldObject<RoadEnding> roadEnding = add(RoadEnding.class);

        // connect roads
        roadToA.object.setEndPoints(roadSpawn.object, a.object);
        // set toA as incoming road for a
        a.object.incoming.put(at, roadToA.object);

        Map<Direction, WorldObject<Semaphore>> semaphores = addSemaphoresAt(a, at);

        // connect roads
        roadFromA.object.setEndPoints(a.object, roadEnding.object);
        // set fromA as outgoing road for a
        a.object.outgoing.put(at, roadFromA.object);

        return new AddRoadsResult(roadToA, roadFromA, roadSpawn, roadEnding, semaphores);
    }

    public Map<Direction, WorldObject<Semaphore>> addSemaphoresAt(WorldObject<Intersection> a, Orientation orientation){
        Map<Direction, WorldObject<Semaphore>> woSemaphores = new HashMap<>();
        woSemaphores.put(Direction.LEFT, add(Semaphore.class));
        woSemaphores.put(Direction.FORWARD, add(Semaphore.class));
        woSemaphores.put(Direction.RIGHT, add(Semaphore.class));

        Map<Direction, Semaphore> semaphores = new HashMap<>();
        semaphores.put(Direction.LEFT, woSemaphores.get(Direction.LEFT).object);
        semaphores.put(Direction.FORWARD, woSemaphores.get(Direction.FORWARD).object);
        semaphores.put(Direction.RIGHT, woSemaphores.get(Direction.RIGHT).object);

        a.object.semaphores.put(orientation, semaphores);
        return woSemaphores;
    }

    /**
     * legend: * spawn, + ending
     *
     *
     *         *  +        *  +
     *         |  ^        |  ^
     *         |  |        |  |
     *         v  |        v  |
     *  + <----    <-b2a---    <---- *
     *  * ---->  A --a2b-->  B ----> +
     *         |  ^        |  ^
     *         |  |        |  |
     *         v  |        v  |
     *         +  *        +  *
     */
    public static World createSimpleWorld(){
        WorldBuilder wb = new WorldBuilder();

        wb.registerFactory(Road.class, new Factory() {
            @Override
            public Object create() {
                return new IntRoad(22);
            }
        });

        WorldObject<Intersection> a = wb.add(Intersection.class);
        a.properties.name = "A";
        WorldObject<Intersection> b = wb.add(Intersection.class);
        b.properties.name = "B";

        wb.connect(a, b, Orientation.EAST);

        wb.addRoadsAt(a, Orientation.NORTH);
        wb.addRoadsAt(a, Orientation.SOUTH);
        wb.addRoadsAt(a, Orientation.WEST);

        wb.addRoadsAt(b, Orientation.NORTH);
        wb.addRoadsAt(b, Orientation.SOUTH);
        wb.addRoadsAt(b, Orientation.EAST);

        return wb.done();
    }
}

package cz.fit.sin.model.world;

import cz.fit.sin.model.intersection.Intersection;
import cz.fit.sin.model.intersection.Semaphore;
import cz.fit.sin.model.road.Road;
import cz.fit.sin.model.road.endpoints.RoadEnding;
import cz.fit.sin.model.road.endpoints.RoadSpawn;
import cz.fit.sin.model.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
* User: Marek Salát
* Date: 17.11.14
* Time: 11:00
*/
public class World {

    public List<WorldObject<Intersection>> intersections = new ArrayList<>();
    public List<WorldObject<Semaphore>> semaphores = new ArrayList<>();
    public List<WorldObject<Road>> roads = new ArrayList<>();
    public List<WorldObject<RoadSpawn>> spawns = new ArrayList<>();
    public List<WorldObject<RoadEnding>> ends = new ArrayList<>();
    public List<WorldObject<Vehicle>> vehicles = new ArrayList<>();

    public<T> List getAll(Class<T> klass){
        if(klass.equals(Intersection.class))
            return intersections;

        if(klass.equals(Semaphore.class))
            return semaphores;

        if(klass.equals(Road.class))
            return roads;

        if(klass.equals(RoadSpawn.class))
            return spawns;

        if(klass.equals(RoadEnding.class))
            return ends;

        if(klass.equals(Vehicle.class))
            return vehicles;

        return null;
    }

    public interface FindComparator<T> {
        public boolean condition(WorldObject<T> object);
    }

    public<T> WorldObject<T> find(Class<T> klass, FindComparator comparator){
        for (Object item : getAll(klass)) {
            WorldObject<T> castedItem = (WorldObject<T>) item;
            if(comparator.condition(castedItem))
                return castedItem;
        }
        return null;
    }

    public<T> WorldObject<T> find(final T ref){
        for (Object item : getAll(ref.getClass())) {
            WorldObject<T> castedItem = (WorldObject<T>) item;
            if(castedItem.object == ref)
                return castedItem;
        }
        return null;
    }

    public<T> WorldObject<T> findById(Class<T> klass, final UUID id){
        return find(klass, new FindComparator() {
            @Override
            public boolean condition(WorldObject object) {
                return id.equals(object.properties.id);
            }
        });
    }

    public<T> WorldObject<T> findByName(Class<T> klass, final String name){
        return find(klass, new FindComparator() {
            @Override
            public boolean condition(WorldObject object) {
                return name.equals(object.properties.name);
            }
        });
    }
}

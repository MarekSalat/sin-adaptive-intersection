package cz.fit.sin.model.world;

/**
* User: Marek Salát
* Date: 17.11.14
* Time: 11:00
*/
public class WorldObject<T> {
    public T object;
    public Properties properties;


    public WorldObject(T object, Properties properties) {
        this.object = object;
        this.properties = properties;
    }
}

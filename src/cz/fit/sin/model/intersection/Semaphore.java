package cz.fit.sin.model.intersection;

/**
* User: Marek Salát
* Date: 17.11.14
* Time: 9:49
*/
public class Semaphore {
    public enum Light {
        GREEN,
        YELLOW,
        RED;

        public Light getNext(){
            return values()[(ordinal() + 1) % 3];
        }
    }

    public Light state = Light.RED;
}

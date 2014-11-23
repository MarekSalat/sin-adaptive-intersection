package cz.fit.sin.model.intersection;

/**
* User: Marek Salát
* Date: 23. 11. 2014
* Time: 15:29
*/
public enum Light {
    GREEN,
    YELLOW,
    RED;

    public Light getNext(){
        return values()[(ordinal() + 1) % 3];
    }
}

package cz.fit.sin.model.intersection;

/**
* User: Marek Salát
* Date: 17.11.14
* Time: 10:04
*/
public enum Orientation {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Orientation opposite() {
        return values()[(ordinal() + 2) % 4];
    }

    public Orientation next(){
        return values()[(ordinal() + 1) % 4];
    }

    public Orientation prev() {
        return values()[(ordinal() + 3) % 4];
    }

    /**
     * You are standing at THIS, what is at RIGHT, LEFT, FORWARD?
     * @param direction
     * @return
     */
    public Orientation toAbsolute(Direction direction){
        if (direction == Direction.FORWARD)
            return opposite();

        if(direction == Direction.LEFT)
            return next();

        if(direction == Direction.RIGHT)
            return prev();

        return this;
    }
}

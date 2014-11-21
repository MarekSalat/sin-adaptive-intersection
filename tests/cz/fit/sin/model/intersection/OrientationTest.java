package cz.fit.sin.model.intersection;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: Marek Salát
 * Date: 17.11.14
 * Time: 10:08
 */
public class OrientationTest {

    @Test
    public void testGetOpposite() throws Exception {
        Assert.assertEquals(Orientation.NORTH.opposite(), Orientation.SOUTH);
        Assert.assertEquals(Orientation.SOUTH.opposite(), Orientation.NORTH);
        Assert.assertEquals(Orientation.WEST.opposite(), Orientation.EAST);
        Assert.assertEquals(Orientation.EAST.opposite(), Orientation.WEST);
    }

    @Test
    public void testGetNext() throws Exception {
        Assert.assertEquals(Orientation.NORTH.next(), Orientation.EAST);
        Assert.assertEquals(Orientation.EAST.next(), Orientation.SOUTH);
        Assert.assertEquals(Orientation.SOUTH.next(), Orientation.WEST);
        Assert.assertEquals(Orientation.WEST.next(), Orientation.NORTH);
    }

    @Test
    public void testGetPrev() throws Exception {
        Assert.assertEquals(Orientation.NORTH.prev(), Orientation.WEST);
        Assert.assertEquals(Orientation.WEST.prev(), Orientation.SOUTH);
        Assert.assertEquals(Orientation.SOUTH.prev(), Orientation.EAST);
        Assert.assertEquals(Orientation.EAST.prev(), Orientation.NORTH);
    }

    @Test
    public void testToAbsolute() throws Exception{
        Assert.assertEquals(Orientation.NORTH.toAbsolute(Direction.FORWARD), Orientation.SOUTH);
        Assert.assertEquals(Orientation.SOUTH.toAbsolute(Direction.FORWARD), Orientation.NORTH);
        Assert.assertEquals(Orientation.WEST.toAbsolute(Direction.FORWARD), Orientation.EAST);
        Assert.assertEquals(Orientation.EAST.toAbsolute(Direction.FORWARD), Orientation.WEST);

        Assert.assertEquals(Orientation.NORTH.toAbsolute(Direction.LEFT), Orientation.EAST);
        Assert.assertEquals(Orientation.EAST.toAbsolute(Direction.LEFT), Orientation.SOUTH);
        Assert.assertEquals(Orientation.SOUTH.toAbsolute(Direction.LEFT), Orientation.WEST);
        Assert.assertEquals(Orientation.WEST.toAbsolute(Direction.LEFT), Orientation.NORTH);

        Assert.assertEquals(Orientation.NORTH.toAbsolute(Direction.RIGHT), Orientation.WEST);
        Assert.assertEquals(Orientation.WEST.toAbsolute(Direction.RIGHT), Orientation.SOUTH);
        Assert.assertEquals(Orientation.SOUTH.toAbsolute(Direction.RIGHT), Orientation.EAST);
        Assert.assertEquals(Orientation.EAST.toAbsolute(Direction.RIGHT), Orientation.NORTH);

//        Assert.assertEquals(Orientation.NORTH.toAbsolute(Direction.CURRENT), Orientation.NORTH);
    }
}

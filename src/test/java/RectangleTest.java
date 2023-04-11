import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;


public class RectangleTest {
    private static Class<?> rectangleClass;
    private static Class<?> pointClass;

    @BeforeEach
    public void setUp() throws Exception {
        try {
            rectangleClass = Class.forName("Rectangle");
            pointClass = Class.forName("Point");
        } catch (Exception e) {
            fail("Class Rectangle or Point not found");
        }
    }

    @Test
    public void testClassExists() throws Exception {
        try {
            Class.forName("Rectangle");
        } catch (Exception e) {
            fail("Class Rectangle does not exist");
        }
    }

    // Test whether the data member topLeft exists
    @Test
    public void testTopLeftExists() throws Exception {
        try {
            Field field = Rectangle.class.getDeclaredField("topLeft");
            assertFalse(Modifier.isPrivate(field.getModifiers()));
            assertEquals("Point", field.getType().getSimpleName());
        } catch (Exception e) {
            fail("Field topLeft does not exist");
        }
    }

    @Test
    public void testBottomRightExists() throws Exception {
        try {
            Field field = Rectangle.class.getDeclaredField("bottomRight");
            assertFalse(Modifier.isPrivate(field.getModifiers()));
            assertEquals("Point", field.getType().getSimpleName());
        } catch (Exception e) {
            fail("Field bottomRight does not exist");
        }
    }


    // Test whether the constructor with four int parameters exists
    @Test
    public void testFourParamConstructorExists() throws Exception {
        try {
            Constructor constructor = Rectangle.class.getDeclaredConstructor(int.class, int.class, int.class, int.class);
            assertTrue(Modifier.isPublic(constructor.getModifiers()));
        } catch (Exception e) {
            fail("Constructor with four int parameters does not exist");
        }
    }

    // Test whether the constructor with two Point parameters exists
    @Test
    public void testTwoPointParamConstructorExists() throws Exception {
        try {
            Constructor constructor = Rectangle.class.getDeclaredConstructor(Point.class, Point.class);
            assertTrue(Modifier.isPublic(constructor.getModifiers()));
        } catch (Exception e) {
            fail("Constructor with two Point parameters does not exist");
        }
    }

    // Test whether the constructor with one Rectangle parameter exists
    @Test
    public void testOneRectangleParamConstructorExists() throws Exception {
        try {
            Constructor constructor = Rectangle.class.getDeclaredConstructor(Rectangle.class);
            assertTrue(Modifier.isPublic(constructor.getModifiers()));
        } catch (Exception e) {
            fail("Constructor with one Rectangle parameter does not exist");
        }
    }

    @Test
    public void testRectangleConstructorWith2Parameters() throws Exception {
        try {
            Constructor<Rectangle> constructor = Rectangle.class.getDeclaredConstructor(Point.class, Point.class);
            assertFalse(Modifier.isPrivate(constructor.getModifiers()));

            Class<?> pointClass = Class.forName("Point");
            Constructor<?> pointConstructor = pointClass.getConstructor(int.class, int.class);

            Object topLeft = pointConstructor.newInstance(1, 2);
            Object bottomRight = pointConstructor.newInstance(3, 4);

            Rectangle r1 = constructor.newInstance((Point)topLeft, (Point)bottomRight);

            Field fieldTopLeft = Rectangle.class.getDeclaredField("topLeft");
            fieldTopLeft.setAccessible(true);
            Object topLeftCopy = fieldTopLeft.get(r1);

            Field fieldBottomRight = Rectangle.class.getDeclaredField("bottomRight");
            fieldBottomRight.setAccessible(true);
            Object bottomRightCopy = fieldBottomRight.get(r1);

            assertFalse(topLeft == topLeftCopy); // checking deep copy
            assertFalse(bottomRight == bottomRightCopy); // checking deep copy

            Field fieldX = pointClass.getDeclaredField("x");
            Field fieldY = pointClass.getDeclaredField("y");
            fieldX.setAccessible(true);
            fieldY.setAccessible(true);

            int topLeftX = fieldX.getInt(topLeftCopy);
            int topLeftY = fieldY.getInt(topLeftCopy);
            int bottomRightX = fieldX.getInt(bottomRightCopy);
            int bottomRightY = fieldY.getInt(bottomRightCopy);

            assertEquals(1, topLeftX);
            assertEquals(2, topLeftY);
            assertEquals(3, bottomRightX);
            assertEquals(4, bottomRightY);
        } catch (Exception e) {
            fail("Exception thrown: " + e);
        }
    }

    @Test
    public void testRectangleConstructorWith1Parameter() throws Exception {
        try {
            Constructor<Rectangle> constructor = Rectangle.class.getDeclaredConstructor(Rectangle.class);
            assertFalse(Modifier.isPrivate(constructor.getModifiers()));
            Constructor<Rectangle> twoParamConstructor = Rectangle.class.getDeclaredConstructor(Point.class, Point.class);

            Class<?> pointClass = Class.forName("Point");
            Constructor<?> pointConstructor = pointClass.getConstructor(int.class, int.class);

            Object topLeft = pointConstructor.newInstance(1, 2);
            Object bottomRight = pointConstructor.newInstance(3, 4);

            Object r1 = constructor.newInstance(twoParamConstructor.newInstance(topLeft, bottomRight));

            Field fieldTopLeft = Rectangle.class.getDeclaredField("topLeft");
            fieldTopLeft.setAccessible(true);
            Object topLeftCopy = fieldTopLeft.get(r1);

            Field fieldBottomRight = Rectangle.class.getDeclaredField("bottomRight");
            fieldBottomRight.setAccessible(true);
            Object bottomRightCopy = fieldBottomRight.get(r1);

            assertFalse(topLeft == topLeftCopy); // checking deep copy
            assertFalse(bottomRight == bottomRightCopy); // checking deep copy

            Field fieldX = pointClass.getDeclaredField("x");
            fieldX.setAccessible(true);
            int topLeftX = fieldX.getInt(topLeft);
            int topLeftCopyX = fieldX.getInt(topLeftCopy);

            Field fieldY = pointClass.getDeclaredField("y");
            fieldY.setAccessible(true);
            int topLeftY = fieldY.getInt(topLeft);
            int topLeftCopyY = fieldY.getInt(topLeftCopy);

            assertEquals(topLeftX, topLeftCopyX);
            assertEquals(topLeftY, topLeftCopyY);
        } catch (Exception e) {
            fail("Exception thrown: " + e);
        }
    }

    @Test
    public void testRectangleConstructorWith4Parameters() throws Exception {
        try {
            Constructor<Rectangle> constructor = Rectangle.class.getDeclaredConstructor(int.class, int.class, int.class, int.class);
            assertFalse(Modifier.isPrivate(constructor.getModifiers()));

            int topLeftX = 2;
            int topLeftY = 3;
            int bottomRightX = 4;
            int bottomRightY = 5;

            Object topLeftObj = createPointObject(topLeftX, topLeftY);
            Object bottomRightObj = createPointObject(bottomRightX, bottomRightY);

            Rectangle r1 = constructor.newInstance(topLeftX, topLeftY, bottomRightX, bottomRightY);

            Field fieldTopLeft = Rectangle.class.getDeclaredField("topLeft");
            fieldTopLeft.setAccessible(true);
            Object topLeftCopy = fieldTopLeft.get(r1);

            Field fieldBottomRight = Rectangle.class.getDeclaredField("bottomRight");
            fieldBottomRight.setAccessible(true);
            Object bottomRightCopy = fieldBottomRight.get(r1);

            Class<?> pointClass = Class.forName("Point");
            Field fieldX = pointClass.getDeclaredField("x");
            fieldX.setAccessible(true);
            int topLeftXCopy = fieldX.getInt(topLeftCopy);
            int bottomRightXCopy = fieldX.getInt(bottomRightCopy);

            Field fieldY = pointClass.getDeclaredField("y");
            fieldY.setAccessible(true);
            int topLeftYCopy = fieldY.getInt(topLeftCopy);
            int bottomRightYCopy = fieldY.getInt(bottomRightCopy);

            assertEquals(topLeftX, topLeftXCopy);
            assertEquals(topLeftY, topLeftYCopy);
            assertEquals(bottomRightX, bottomRightXCopy);
            assertEquals(bottomRightY, bottomRightYCopy);
        } catch (Exception e) {
            fail("Exception thrown: " + e);
        }
    }
    private Object createPointObject(int x, int y) throws Exception {
        Class<?> pointClass = Class.forName("Point");
        Constructor<?> constructor = pointClass.getDeclaredConstructor(int.class, int.class);
        return constructor.newInstance(x, y);
    }

}

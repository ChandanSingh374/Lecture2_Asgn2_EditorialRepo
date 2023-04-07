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
    public void setUp() {
        try {
            rectangleClass = Class.forName("Rectangle");
            pointClass = Class.forName("Point");
        } catch (ClassNotFoundException e) {
            fail("Class Rectangle or Point not found");
        }
    }

//    @Test
//    public void testTopLeftDataMemberExists() {
//        try {
//            Field topLeftField = rectangleClass.getDeclaredField("topLeft");
//            assertEquals(pointClass, topLeftField.getType(), "Data member topLeft is not of type Point");
//        } catch (NoSuchFieldException e) {
//            fail("Data member topLeft not found in class Rectangle");
//        }
//    }
//
//    @Test
//    public void testBottomRightDataMemberExists() {
//        try {
//            Field bottomRightField = rectangleClass.getDeclaredField("bottomRight");
//            assertEquals(pointClass, bottomRightField.getType(), "Data member bottomRight is not of type Point");
//        } catch (NoSuchFieldException e) {
//            fail("Data member bottomRight not found in class Rectangle");
//        }
//    }

    @Test
    public void testClassExists() {
        try {
            Class.forName("Rectangle");
        } catch (ClassNotFoundException e) {
            fail("Class Rectangle does not exist");
        }
    }

    // Test whether the data member topLeft exists
    @Test
    public void testTopLeftExists() {
        try {
            Field field = Rectangle.class.getDeclaredField("topLeft");
            assertFalse(Modifier.isPrivate(field.getModifiers()));
            assertEquals("Point", field.getType().getSimpleName());
        } catch (NoSuchFieldException e) {
            fail("Field topLeft does not exist");
        }
    }

    // Test whether the constructor with four int parameters exists
    @Test
    public void testFourParamConstructorExists() {
        try {
            Constructor constructor = Rectangle.class.getDeclaredConstructor(int.class, int.class, int.class, int.class);
            assertTrue(Modifier.isPublic(constructor.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Constructor with four int parameters does not exist");
        }
    }

    // Test whether the constructor with two Point parameters exists
    @Test
    public void testTwoPointParamConstructorExists() {
        try {
            Constructor constructor = Rectangle.class.getDeclaredConstructor(Point.class, Point.class);
            assertTrue(Modifier.isPublic(constructor.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Constructor with two Point parameters does not exist");
        }
    }

    // Test whether the constructor with one Rectangle parameter exists
    @Test
    public void testOneRectangleParamConstructorExists() {
        try {
            Constructor constructor = Rectangle.class.getDeclaredConstructor(Rectangle.class);
            assertTrue(Modifier.isPublic(constructor.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Constructor with one Rectangle parameter does not exist");
        }
    }

    // Test whether the copy constructor of Point works correctly
    public void testPointCopyConstructor() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Constructor<Point> constructor = Point.class.getDeclaredConstructor(int.class, int.class);
            constructor.setAccessible(true);
            Point p1 = constructor.newInstance(2, 3);
            Point p2 = new Point(p1);
            Method getX = Point.class.getDeclaredMethod("getX");
            Method getY = Point.class.getDeclaredMethod("getY");
            getX.setAccessible(true);
            getY.setAccessible(true);
            assertEquals(2, (int) getX.invoke(p2));
            assertEquals(3, (int) getY.invoke(p2));
        } catch (Exception e) {
            fail("Failed to create point using copy constructor");
        }
    }

}

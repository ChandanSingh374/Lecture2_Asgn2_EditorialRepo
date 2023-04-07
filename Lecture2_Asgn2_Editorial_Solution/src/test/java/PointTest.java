import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {
    private static Class<?> pointClass;

    @BeforeEach
    public void setUp() {
        try {
            pointClass = Class.forName("Point");
        } catch (ClassNotFoundException e) {
            fail("Class Point not found");
        }
    }

    @Test
    public void testXDataMemberExists() {
        try {
            Field xField = pointClass.getDeclaredField("x");
            assertEquals(int.class, xField.getType(), "Data member x is not of type int");
        } catch (NoSuchFieldException e) {
            fail("Data member x not found in class Point");
        }
    }

    @Test
    public void testYDataMemberExists() {
        try {
            Field yField = pointClass.getDeclaredField("y");
            assertEquals(int.class, yField.getType(), "Data member y is not of type int");
        } catch (NoSuchFieldException e) {
            fail("Data member y not found in class Point");
        }
    }

    @Test
    public void testTwoParamConstructorExists() {
        try {
            Constructor<?> constructor = pointClass.getDeclaredConstructor(int.class, int.class);
            assertNotNull(constructor, "Two parameter constructor not found in class Point");
        } catch (NoSuchMethodException e) {
            fail("Two parameter constructor not found in class Point");
        }
    }

    @Test
    public void testCopyConstructorExists() {
        try {
            Constructor<?> constructor = pointClass.getDeclaredConstructor(pointClass);
            assertNotNull(constructor, "Copy constructor not found in class Point");
        } catch (NoSuchMethodException e) {
            fail("Copy constructor not found in class Point");
        }
    }

    private void assertNotNull(Constructor<?> constructor, String s) {
    }

    @Test
    public void testTwoParamConstructorSignature() {
        Class<?> c = Point.class;
        try {
            Constructor<?> cons = c.getConstructor(int.class, int.class);
            Class<?>[] paramTypes = cons.getParameterTypes();
            assertEquals(2, paramTypes.length);
            assertEquals(int.class, paramTypes[0]);
            assertEquals(int.class, paramTypes[1]);
        } catch (NoSuchMethodException e) {
            fail("Two param constructor doesn't exist");
        }
    }

    @Test
    public void testTwoParamConstructor() {
        try {
            Class<?> c = Point.class;
            Constructor<?> cons = c.getConstructor(int.class, int.class);
            Point p = (Point) cons.newInstance(3, 4);
            assertEquals(3, p.x);
            assertEquals(4, p.y);
        } catch (Exception e) {
            fail("Failed to create point using two param constructor");
        }
    }

    @Test
    public void testCopyConstructorSignature() {
        Class<?> c = Point.class;
        try {
            Constructor<?> cons = c.getConstructor(Point.class);
            Class<?>[] paramTypes = cons.getParameterTypes();
            assertEquals(1, paramTypes.length);
            assertEquals(Point.class, paramTypes[0]);
        } catch (NoSuchMethodException e) {
            fail("Copy constructor doesn't exist");
        }
    }

    @Test
    public void testCopyConstructor() {
        try {
            Point p1 = new Point(3, 4);
            Class<?> c = Point.class;
            Constructor<?> cons = c.getConstructor(Point.class);
            Point p2 = (Point) cons.newInstance(p1);
            assertNotSame(p1, p2);
            assertEquals(p1.x, p2.x);
            assertEquals(p1.y, p2.y);
        } catch (Exception e) {
            fail("Failed to create point using copy constructor");
        }
    }

}

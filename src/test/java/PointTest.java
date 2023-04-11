import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {
    private static Class<?> pointClass;

    @BeforeEach
    public void setUp() throws Exception {
        try {
            pointClass = Class.forName("Point");
        } catch (Exception e) {
            fail("Class Point not found");
        }
    }

    @Test
    public void testXDataMemberExists() throws Exception {
        try {
            Field xField = pointClass.getDeclaredField("x");
            assertEquals(int.class, xField.getType(), "Data member x is not of type int");
        } catch (Exception e) {
            fail("Data member x not found in class Point");
        }
    }

    @Test
    public void testYDataMemberExists() throws Exception {
        try {
            Field yField = pointClass.getDeclaredField("y");
            assertEquals(int.class, yField.getType(), "Data member y is not of type int");
        } catch (Exception e) {
            fail("Data member y not found in class Point");
        }
    }

    @Test
    public void testTwoParamConstructorSignature() throws Exception {
        try {
            Class<?> c = Point.class;
            Constructor<?> cons = c.getConstructor(int.class, int.class);
            Class<?>[] paramTypes = cons.getParameterTypes();
            assertEquals(2, paramTypes.length);
            assertEquals(int.class, paramTypes[0]);
            assertEquals(int.class, paramTypes[1]);
        } catch (Exception e) {
            fail("Two param constructor doesn't exist");
        }
    }

    @Test
    public void testTwoParamConstructor() throws Exception {
        try {
            Class<?> c = Point.class;
            Constructor<?> cons = c.getConstructor(int.class, int.class);
            Point p = (Point) cons.newInstance(3, 4);
            Field xField = c.getDeclaredField("x"); // get the private 'x' field of the Point class
            xField.setAccessible(true); // set the 'accessible' flag to true
            int xValue = (int) xField.get(p); // get the value of 'x' from the Point instance
            Field yField = c.getDeclaredField("y"); // get the private 'y' field of the Point class
            yField.setAccessible(true); // set the 'accessible' flag to true
            int yValue = (int) yField.get(p); // get the value of 'y' from the Point instance
            assertEquals(3, xValue);
            assertEquals(4, yValue);
        } catch (NoSuchFieldException e) {
            fail("Point class should have 'x' and 'y' fields");
        } catch (Exception e) {
            fail("Failed to create point using two param constructor");
        }
    }

    @Test
    public void testCopyConstructorSignature() throws Exception {
        try {
            Class<?> c = Point.class;
            Constructor<?> cons = c.getConstructor(Point.class);
            Class<?>[] paramTypes = cons.getParameterTypes();
            assertEquals(1, paramTypes.length);
            assertEquals(Point.class, paramTypes[0]);
        } catch (Exception e) {
            fail("Copy constructor doesn't exist");
        }
    }

    @Test
    public void testCopyConstructor() throws Exception {
        try {
            Constructor<Point> cons = Point.class.getDeclaredConstructor(int.class, int.class);
            Point p1 = cons.newInstance(3, 4);
            cons = Point.class.getDeclaredConstructor(Point.class);
            Point p2 = cons.newInstance(p1);
            assertNotSame(p1, p2);
            Field xField = Point.class.getDeclaredField("x"); // get the private 'x' field of the Point class
            xField.setAccessible(true); // set the 'accessible' flag to true
            int xValue1 = (int) xField.get(p1); // get the value of 'x' from p1
            int xValue2 = (int) xField.get(p2); // get the value of 'x' from p2
            assertEquals(xValue1, xValue2);
            Field yField = Point.class.getDeclaredField("y"); // get the private 'y' field of the Point class
            yField.setAccessible(true); // set the 'accessible' flag to true
            int yValue1 = (int) yField.get(p1); // get the value of 'y' from p1
            int yValue2 = (int) yField.get(p2); // get the value of 'y' from p2
            assertEquals(yValue1, yValue2);
        } catch (NoSuchFieldException e) {
            fail("Point class should have 'x' and 'y' fields");
        } catch (Exception e) {
            fail("Failed to create point using copy constructor");
        }
    }
}

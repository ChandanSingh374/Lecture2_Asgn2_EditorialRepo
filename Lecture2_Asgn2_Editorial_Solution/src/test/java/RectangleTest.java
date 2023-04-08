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

    @Test
    public void testBottomRightExists() {
        try {
            Field field = Rectangle.class.getDeclaredField("bottomRight");
            assertFalse(Modifier.isPrivate(field.getModifiers()));
            assertEquals("Point", field.getType().getSimpleName());
        } catch (NoSuchFieldException e) {
            fail("Field bottomRight does not exist");
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

//    @Test
//    public void testRectangleConstructorWith4Parameters() throws Exception {
//        Constructor<Rectangle> constructor = Rectangle.class.getDeclaredConstructor(int.class, int.class, int.class, int.class);
//        assertFalse(Modifier.isPrivate(constructor.getModifiers()));
//
//        int x1 = 1, y1 = 2, x2 = 3, y2 = 4;
//        Rectangle r1 = constructor.newInstance(x1, y1, x2, y2);
//
//        Field fieldTopLeft = Rectangle.class.getDeclaredField("topLeft");
//        fieldTopLeft.setAccessible(true);
//        Point topLeftCopy = (Point) fieldTopLeft.get(r1);
//
//        Field fieldBottomRight = Rectangle.class.getDeclaredField("bottomRight");
//        fieldBottomRight.setAccessible(true);
//        Point bottomRightCopy = (Point) fieldBottomRight.get(r1);
//
//        // compare x1. x2 y1 y2 with copies
//        assertFalse(topLeftCopy == expectedTopLeft); // checking deep copy
//        assertFalse(bottomRightCopy == expectedBottomRight); // checking deep copy
//
//        assertEquals(expectedTopLeft, topLeftCopy);
//        assertEquals(expectedBottomRight, bottomRightCopy);
//    }




    @Test
    public void testRectangleConstructorWith2Parameters() throws Exception {
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
    }




//    @Test
//    public void testRectangleConstructorWith1Parameter() throws Exception {
//        Constructor<Rectangle> constructor = Rectangle.class.getDeclaredConstructor(Rectangle.class);
//        assertFalse(Modifier.isPrivate(constructor.getModifiers()));
//
//        Point topLeft = new Point(1, 2);
//        Point bottomRight = new Point(3, 4);
//        Rectangle r1 = new Rectangle(topLeft, bottomRight);
//
//        Rectangle r2 = constructor.newInstance(r1);
//
//        Field fieldTopLeft = Rectangle.class.getDeclaredField("topLeft");
//        fieldTopLeft.setAccessible(true);
//        Point topLeftCopy = (Point) fieldTopLeft.get(r2);
//
//        Field fieldBottomRight = Rectangle.class.getDeclaredField("bottomRight");
//        fieldBottomRight.setAccessible(true);
//        Point bottomRightCopy = (Point) fieldBottomRight.get(r2);
//
//        assertFalse(topLeft == topLeftCopy); // checking deep copy
//        assertFalse(bottomRight == bottomRightCopy); // checking deep copy
//        // r1 === r2 assertfalse
//
////        assertEquals(topLeft, topLeftCopy);
////        assertEquals(bottomRight, bottomRightCopy);
//    }



//    @Test
//    public void testRectangleConstructorWith1Parameter() throws Exception {
//        Constructor<Rectangle> constructor = Rectangle.class.getDeclaredConstructor(Rectangle.class);
//        assertFalse(Modifier.isPrivate(constructor.getModifiers()));
//
//        Class<?> pointClass = Class.forName("Point");
//        Constructor<?> pointConstructor = pointClass.getConstructor(int.class, int.class);
//
//        Object topLeft = pointConstructor.newInstance(1, 2);
//        Object bottomRight = pointConstructor.newInstance(3, 4);
//        Rectangle r1 = new Rectangle((Point) topLeft, (Point) bottomRight);
//
//        Rectangle r2 = constructor.newInstance(r1);
//
//        Field fieldTopLeft = Rectangle.class.getDeclaredField("topLeft");
//        fieldTopLeft.setAccessible(true);
//        Object topLeftCopy = fieldTopLeft.get(r2);
//
//        Field fieldBottomRight = Rectangle.class.getDeclaredField("bottomRight");
//        fieldBottomRight.setAccessible(true);
//        Object bottomRightCopy = fieldBottomRight.get(r2);
//
//        assertFalse(topLeft == topLeftCopy); // checking deep copy
//        assertFalse(bottomRight == bottomRightCopy); // checking deep copy
//        // r1 === r2 assertfalse
//
//        // assert that the x and y values of the top left and bottom right points are equal
//        assertEquals(fieldTopLeft.getInt(r1), fieldTopLeft.getInt(r2));
//        assertEquals(fieldTopLeft.getInt(r1), fieldBottomRight.getInt(r2));
//        assertEquals(fieldBottomRight.getInt(r1), fieldBottomRight.getInt(r2));
//    }

    @Test
    public void testRectangleConstructorWith1Parameter() throws Exception {
        Constructor<Rectangle> constructor = Rectangle.class.getDeclaredConstructor(Rectangle.class);
        assertFalse(Modifier.isPrivate(constructor.getModifiers()));

        Class<?> pointClass = Class.forName("Point");
        Constructor<?> pointConstructor = pointClass.getConstructor(int.class, int.class);

        Object topLeft = pointConstructor.newInstance(1, 2);
        Object bottomRight = pointConstructor.newInstance(3, 4);

        Rectangle r1 = new Rectangle((Point) topLeft, (Point) bottomRight);
        Rectangle r2 = constructor.newInstance(r1);

        Field fieldTopLeft = Rectangle.class.getDeclaredField("topLeft");
        fieldTopLeft.setAccessible(true);
        Object topLeftCopy = fieldTopLeft.get(r2);

        Field fieldBottomRight = Rectangle.class.getDeclaredField("bottomRight");
        fieldBottomRight.setAccessible(true);
        Object bottomRightCopy = fieldBottomRight.get(r2);

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
    }


//    @Test
//    public void testRectangleConstructorWith4Parameters() throws Exception {
//        Constructor<Rectangle> constructor = Rectangle.class.getDeclaredConstructor(int.class, int.class, int.class, int.class);
//        assertFalse(Modifier.isPrivate(constructor.getModifiers()));
//
//        int topLeftX = 2;
//        int topLeftY = 3;
//        int bottomRightX = 4;
//        int bottomRightY = 5;
//
//        Rectangle r1 = constructor.newInstance(topLeftX, topLeftY, bottomRightX, bottomRightY);
//
//        Field fieldTopLeft = Rectangle.class.getDeclaredField("topLeft");
//        fieldTopLeft.setAccessible(true);
//        Object topLeftObj = fieldTopLeft.get(r1);
//        Field xField = Point.class.getDeclaredField("x");
//        Field yField = Point.class.getDeclaredField("y");
//        xField.setAccessible(true);
//        yField.setAccessible(true);
//        int actualTopLeftX = (int) xField.get(topLeftObj);
//        int actualTopLeftY = (int) yField.get(topLeftObj);
//
//        Field fieldBottomRight = Rectangle.class.getDeclaredField("bottomRight");
//        fieldBottomRight.setAccessible(true);
//        Object bottomRightObj = fieldBottomRight.get(r1);
//        int actualBottomRightX = (int) xField.get(bottomRightObj);
//        int actualBottomRightY = (int) yField.get(bottomRightObj);
//
//        assertEquals(topLeftX, actualTopLeftX);
//        assertEquals(topLeftY, actualTopLeftY);
//        assertEquals(bottomRightX, actualBottomRightX);
//        assertEquals(bottomRightY, actualBottomRightY);
//
//        // Check that deep copy is maintained
//        Object newTopLeftObj = fieldTopLeft.get(r1);
//        Object newBottomRightObj = fieldBottomRight.get(r1);
//        assertFalse(topLeftObj == newTopLeftObj);
//        assertFalse(bottomRightObj == newBottomRightObj);
//    }
@Test
public void testRectangleConstructorWith4Parameters() throws Exception {
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

    assertFalse(topLeftObj == topLeftCopy); // checking deep copy
    assertFalse(bottomRightObj == bottomRightCopy); // checking deep copy


}
    private Object createPointObject(int x, int y) throws Exception {
        Class<?> pointClass = Class.forName("Point");
        Constructor<?> constructor = pointClass.getDeclaredConstructor(int.class, int.class);
        return constructor.newInstance(x, y);
    }


}

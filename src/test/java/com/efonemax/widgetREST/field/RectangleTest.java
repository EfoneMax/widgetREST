package com.efonemax.widgetREST.field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
class RectangleTest {
    Rectangle rectangle;

    @BeforeEach
    void setUp() {
        Point lowerLeftCorner = new Point();
        Point upperRightCorner = new Point(5, 5);
        rectangle = new Rectangle(lowerLeftCorner, upperRightCorner);
    }

    @Test
    void getWidth() {
        int width = rectangle.getWidth();
        assertEquals(5, width);
    }

    @Test
    void getHeight() {
        int height = rectangle.getHeight();
        assertEquals(5, height);
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        Point lowerLeftCorner = new Point();
        Point upperRightCorner = new Point(5, 5);
        Rectangle newRectangle = new Rectangle(lowerLeftCorner, upperRightCorner);

        Rectangle cRectangle = rectangle.clone();

        assertEquals(newRectangle, cRectangle);
        assert cRectangle != rectangle;
    }

    @Test
    void makeItOnePointBigger() throws CloneNotSupportedException {
        Rectangle cRectangle = rectangle.clone();
        cRectangle.makeItOnePointBigger();

        assertNotEquals(cRectangle.getLowerLeftCorner().getX(), rectangle.getLowerLeftCorner().getX());
        assertNotEquals(cRectangle.getLowerLeftCorner().getY(), rectangle.getLowerLeftCorner().getY());
        assertNotEquals(cRectangle.getUpperLeftCorner().getX(), rectangle.getUpperLeftCorner().getX());
        assertNotEquals(cRectangle.getUpperLeftCorner().getY(), rectangle.getUpperLeftCorner().getY());
        assertNotEquals(cRectangle.getUpperRightCorner().getX(), rectangle.getUpperRightCorner().getX());
        assertNotEquals(cRectangle.getUpperRightCorner().getY(), rectangle.getUpperRightCorner().getY());
        assertNotEquals(cRectangle.getLowerRightCorner().getX(), rectangle.getLowerRightCorner().getX());
        assertNotEquals(cRectangle.getLowerRightCorner().getY(), rectangle.getLowerRightCorner().getY());
    }
}
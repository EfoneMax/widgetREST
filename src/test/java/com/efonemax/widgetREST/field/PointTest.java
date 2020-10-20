package com.efonemax.widgetREST.field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class PointTest {
    Point point;

    @BeforeEach
    void setUp() {
       point= new Point();
    }

    @Test
    void testIncreasePointCoordinatesBy500() {
        int x = point.getX();
        int y = point.getY();
        point.increasePointCoordinatesBy500();

        assertEquals(x + 500, point.getX());
        assertEquals(y + 500, point.getY());
    }
}
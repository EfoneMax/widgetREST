package com.efonemax.widgetREST.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Rectangle implements Cloneable {
    private Point lowerLeftCorner;
    private Point upperLeftCorner;
    private Point upperRightCorner;
    private Point lowerRightCorner;

    Rectangle(Point  lowerLeftCorner, Point upperRightCorner) {
        this.lowerLeftCorner = lowerLeftCorner;
        upperLeftCorner = new Point(lowerLeftCorner.getX(), upperRightCorner.getY());
        this.upperRightCorner = upperRightCorner;
        lowerRightCorner = new Point(upperRightCorner.getX(), lowerLeftCorner.getY());
        increaseRectangleCoordinatesBy500();
    }

    public int getWidth() {
        return Math.abs(lowerRightCorner.getX() - lowerLeftCorner.getX());
    }

    public int getHeight() {
        return Math.abs(upperLeftCorner.getY() - lowerLeftCorner.getY());
    }

    public Rectangle clone() throws CloneNotSupportedException {
        return (Rectangle) super.clone();
    }

    public void makeItOnePointBigger() {
        lowerLeftCorner.setX(lowerLeftCorner.getX() - 1);
        lowerLeftCorner.setY(lowerLeftCorner.getY() - 1);

        upperLeftCorner.setX(upperLeftCorner.getX() - 1);
        upperLeftCorner.setY(upperLeftCorner.getY() + 1);

        upperRightCorner.setX(upperRightCorner.getX() + 1);
        upperRightCorner.setY(upperRightCorner.getY() + 1);

        lowerRightCorner.setX(lowerRightCorner.getX() + 1);
        lowerRightCorner.setY(lowerRightCorner.getY() - 1);
    }

    private void increaseRectangleCoordinatesBy500() {
        this.getLowerLeftCorner().increasePCoordinatesBy500();
        this.getUpperLeftCorner().increasePCoordinatesBy500();
        this.getUpperRightCorner().increasePCoordinatesBy500();
        this.getLowerRightCorner().increasePCoordinatesBy500();
    }
}

package com.efonemax.widgetREST.field;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
        Rectangle newRectangle = new Rectangle();
        newRectangle.setLowerLeftCorner(this.lowerLeftCorner.clone());
        newRectangle.setUpperLeftCorner(this.upperLeftCorner.clone());
        newRectangle.setUpperRightCorner(this.upperRightCorner.clone());
        newRectangle.setLowerRightCorner(this.lowerRightCorner.clone());

        return newRectangle;
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
        this.getLowerLeftCorner().increasePointCoordinatesBy500();
        this.getUpperLeftCorner().increasePointCoordinatesBy500();
        this.getUpperRightCorner().increasePointCoordinatesBy500();
        this.getLowerRightCorner().increasePointCoordinatesBy500();
    }
}

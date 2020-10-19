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
    }

    Rectangle(int lowerLeftCornerX, int lowerLeftCornerY, int upperRightCornerX, int upperRightCornerY) {
        this(new Point(lowerLeftCornerX, lowerLeftCornerY), new Point(upperRightCornerX, upperRightCornerY));
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
        lowerRightCorner.setY(lowerLeftCorner.getY() - 1);
    }

    private int addOneOrSubtract(int value) {
        if (value < 0) {
            value -= 1;
        } else {
            value += 1;
        }

        return value;
    }
}

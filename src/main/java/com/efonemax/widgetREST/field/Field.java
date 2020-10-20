package com.efonemax.widgetREST.field;

import com.efonemax.widgetREST.domain.Widget;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class Field {
    private static volatile Field instance;
    private int[][][] fieldArray = new int[1000][1000][1000];

    public static Field getInstance() {
        Field result = instance;
        if (result != null) {
            return result;
        }
        synchronized (Field.class) {
            if (instance == null) {
                instance = new Field();
            }
            return instance;
        }
    }

    public void erase(Widget widget) {
        writeWidgetIdToField(widget, 0);
    }

    public void write(Widget widget) {
        writeWidgetIdToField(widget, widget.getId());
    }

    private void writeWidgetIdToField(Widget widget, Integer widgetId) {
        int width = widget.getWidth();
        int height = widget.getHeight();
        int firstXPoint = widget.getXIndex();
        int firstYPoint = widget.getYIndex();
        int zPoint = widget.getZIndex();

        for (int x = firstXPoint; x <= firstXPoint + width; x++) {
            for (int y = firstYPoint; y <= firstYPoint + height; y++) {
                fieldArray[x][y][zPoint] = widgetId;
            }
        }
    }

    public Set<Integer> getWidgetIdsContainedInTheSubfield(Point lowerLeftPoint, Point upperRightPoint) throws CloneNotSupportedException {
        Rectangle rectangle = new Rectangle(lowerLeftPoint, upperRightPoint);
        Set<Integer> unfilteredWidgetIdsSet = getWidgetIdsByCoordinates(rectangle);
        Set<Integer> widgetIdsOutOfBounds = getWidgetIdsOutOfCoordinates(rectangle);

        unfilteredWidgetIdsSet.removeAll(widgetIdsOutOfBounds);

        return unfilteredWidgetIdsSet;
    }

    private Set<Integer> getWidgetIdsByCoordinates(Rectangle rectangle) {
        Set<Integer> widgetIds = new HashSet<>();
        for (int x = rectangle.getLowerLeftCorner().getX(); x < rectangle.getLowerLeftCorner().getX() + rectangle.getWidth(); x++) {
            for (int y = rectangle.getLowerLeftCorner().getY(); y < rectangle.getLowerLeftCorner().getY() + rectangle.getHeight(); y++) {
                List<Integer> zIndexes = intArrayToList(fieldArray[x][y]);
                widgetIds.addAll(zIndexes);
            }
        }

        return widgetIds;
    }

    private Set<Integer> getWidgetIdsOutOfCoordinates(Rectangle rectangle) throws CloneNotSupportedException {
        Rectangle biggerRectangle = rectangle.clone();
        biggerRectangle.makeItOnePointBigger();

        return getWidgetIdsOnPerimeter(biggerRectangle);
    }

    private Set<Integer> getWidgetIdsOnPerimeter(Rectangle rectangle) {
        Set<Integer> firstResult = getWidgetIdsInTheLine(rectangle.getLowerLeftCorner(), rectangle.getUpperLeftCorner());
        Set<Integer> secondResult = getWidgetIdsInTheLine(rectangle.getUpperLeftCorner(), rectangle.getUpperRightCorner());
        Set<Integer> thirdResult = getWidgetIdsInTheLine(rectangle.getUpperRightCorner(), rectangle.getLowerRightCorner());
        Set<Integer> fourthResult = getWidgetIdsInTheLine(rectangle.getLowerRightCorner(), rectangle.getLowerLeftCorner());

        firstResult.addAll(secondResult);
        firstResult.addAll(thirdResult);
        firstResult.addAll(fourthResult);

        return firstResult;
    }

    private Set<Integer> getWidgetIdsInTheLine(Point p1, Point p2) {
        Set<Integer> widgetIds = new HashSet<>();

        if (p1.getX() == p2.getX()) {
            for (int y = p1.getY(); y <= p2.getY(); y++) {
                widgetIds.addAll(intArrayToList(fieldArray[p1.getX()][y]));
            }
        } else if (p1.getY() == p2.getY()) {
            for (int x = p1.getX(); x <= p2.getX(); x++) {
                widgetIds.addAll(intArrayToList(fieldArray[x][p1.getY()]));
            }
        } else {
            throw new RuntimeException("The line between the points must be vertical or horizontal");
        }
        return widgetIds;
    }

    private List<Integer> intArrayToList(int[] array) {
        return Arrays.stream(array).boxed().collect(Collectors.toList());
    }
}

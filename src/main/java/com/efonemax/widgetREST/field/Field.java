package com.efonemax.widgetREST.field;

import com.efonemax.widgetREST.domain.Widget;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class Field {
    private static volatile Field instance;
    private List<List<List<Long>>> fieldList = new ArrayList<>();

    {
        List<Long> zInitialValues = new ArrayList<>();
        for (int x = 0; x < 1000; x++) {
            zInitialValues.add(x, 0L);
        }

        List<List<Long>> yInitialValues = new ArrayList<>();
        for (int x = 0; x < 10000; x++) {
            yInitialValues.add(x, zInitialValues);
        }

        for (int x = 0; x < 10000; x++) {
            fieldList.add(x, yInitialValues);
        }
    }

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

    public synchronized void update(Widget widget) {
        eraseWidget(widget);
        writeWidget(widget);
    }

    private void eraseWidget(Widget widget) {
        writeWidgetValue(widget, null);
    }

    private void writeWidget(Widget widget) {
        writeWidgetValue(widget, widget.getId());
    }

    private void writeWidgetValue(Widget widget, Long value) {
        int width = widget.getWidth();
        int height = widget.getHeight();
        int firstXPoint = widget.getXIndex();
        int firstYPoint = widget.getYIndex();
        int zPoint = widget.getZIndex();

        for (int x = firstXPoint; x < width; x++) {
            for (int y = firstYPoint; y < height; y++) {
                fieldList.get(x).get(y).set(zPoint, value);
            }
        }
    }

    public Set<Long> getWidgetIdsByCoordinates(Point lowerLeftPoint, Point upperRightPoint) throws CloneNotSupportedException {
        Rectangle rectangle = new Rectangle(lowerLeftPoint, upperRightPoint);
        Set<Long> unfilteredWidgetIdsSet = getWidgetIdsByCoordinates(rectangle);
        Set<Long> widgetIdsOutOfBounds = getWidgetIdsOutOfCoordinates(rectangle);

        unfilteredWidgetIdsSet.removeAll(widgetIdsOutOfBounds);

        return unfilteredWidgetIdsSet;
    }

    private Set<Long> getWidgetIdsByCoordinates(Rectangle rectangle) {
        Set<Long> widgetIds = new HashSet<>();
        for (int x = rectangle.getLowerLeftCorner().getX(); x < rectangle.getWidth(); x++) {
            for (int y = rectangle.getLowerLeftCorner().getY(); y < rectangle.getHeight(); y++) {
                //получаем занятые z индексы
                List<Long> zIndexes = fieldList.get(x).get(y);
                widgetIds.addAll(zIndexes);
            }
        }

        return widgetIds;
    }

    private Set<Long> getWidgetIdsOutOfCoordinates(Rectangle rectangle) throws CloneNotSupportedException {
        Rectangle biggerRectangle = rectangle.clone();
        biggerRectangle.makeItOnePointBigger();

        return getWidgetIdsOnPerimeter(biggerRectangle);
    }

    private Set<Long> getWidgetIdsOnPerimeter(Rectangle rectangle) {
        Set<Long> firstResult = getWidgetIdsInTheLine(rectangle.getLowerLeftCorner(), rectangle.getUpperLeftCorner());
        Set<Long> secondResult = getWidgetIdsInTheLine(rectangle.getLowerLeftCorner(), rectangle.getUpperLeftCorner());
        Set<Long> thirdResult = getWidgetIdsInTheLine(rectangle.getLowerLeftCorner(), rectangle.getUpperLeftCorner());
        Set<Long> fourthResult = getWidgetIdsInTheLine(rectangle.getLowerLeftCorner(), rectangle.getUpperLeftCorner());

        firstResult.addAll(secondResult);
        firstResult.addAll(thirdResult);
        firstResult.addAll(fourthResult);

        return firstResult;
    }

    private Set<Long> getWidgetIdsInTheLine(Point p1, Point p2) {
        Set<Long> widgetIds = new HashSet<>();

        if (p1.getX() == p2.getX()) {
            for (int y = p1.getY(); y <= p2.getY(); y++) {
                widgetIds.addAll(fieldList.get(p1.getX()).get(y));
            }
        } else if (p1.getY() == p2.getY()) {
            for (int x = p1.getX(); x <= p2.getX(); x++) {
                widgetIds.addAll(fieldList.get(x).get(p1.getY()));
            }
        } else {
            throw new RuntimeException("The line between the points must be vertical or horizontal");
        }
        return widgetIds;
    }
}

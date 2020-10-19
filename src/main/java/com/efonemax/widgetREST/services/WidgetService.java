package com.efonemax.widgetREST.services;

import com.efonemax.widgetREST.domain.Widget;
import com.efonemax.widgetREST.field.Point;

import java.util.List;

public interface WidgetService {
    Widget create(Widget widget);

    Widget update(Long id, Widget widget);

    void delete(long id);

    Widget get(long id);

    /*
     * pagination implementation
     */
    List<Widget> getList(int page, int size);

    List<Widget> filterWidgetsByCoordinates(Point lowerLeftPoint, Point upperRightPoint) throws CloneNotSupportedException;
}

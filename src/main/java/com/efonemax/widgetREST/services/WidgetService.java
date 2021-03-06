package com.efonemax.widgetREST.services;

import com.efonemax.widgetREST.domain.Widget;
import com.efonemax.widgetREST.field.Point;

import java.util.List;

public interface WidgetService {
    Widget create(Widget widget) throws CloneNotSupportedException;

    Widget update(Integer id, Widget widget) throws CloneNotSupportedException;

    void delete(Integer id);

    Widget get(Integer id) throws CloneNotSupportedException;

    /*
     * pagination implementation
     */
    List<Widget> getList(int page, int size);

    List<Widget> filterWidgetsByCoordinates(Point lowerLeftPoint, Point upperRightPoint) throws CloneNotSupportedException;
}

package com.efonemax.widgetREST.services;

import com.efonemax.widgetREST.domain.Widget;

import java.util.List;

public interface WidgetService {
    Widget create(Widget widget);
    Widget update(Long id, Widget widget);
    void delete(long id);
    Widget get(long id);
    List<Widget> getList(int page, int size);
}

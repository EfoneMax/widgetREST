package com.efonemax.widgetREST.services.map;

import com.efonemax.widgetREST.domain.Widget;
import com.efonemax.widgetREST.services.WidgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
class WidgetMapServiceTest {
    WidgetService widgetService;
    Widget widgetOne;
    Widget widgetTwo;
    Widget widgetThree;

    @BeforeEach
    void setUp() throws CloneNotSupportedException {
        widgetService = new WidgetMapService();

        widgetOne = Widget.builder().id(1).xIndex(1).yIndex(1).zIndex(1).height(1).width(1).build();
        widgetTwo = Widget.builder().id(2).xIndex(2).yIndex(2).zIndex(2).height(1).width(1).build();
        widgetThree = Widget.builder().id(3).xIndex(3).yIndex(3).zIndex(3).height(1).width(1).build();

        widgetService.create(widgetOne);
        widgetService.create(widgetTwo);
        widgetService.create(widgetThree);
    }

    @Test
    void create() throws CloneNotSupportedException {
        Widget widgetFour = Widget.builder().xIndex(1).yIndex(1).zIndex(1).height(1).width(1).build();
        Widget createdWidget = widgetService.create(widgetFour.clone());
        assertNotNull(createdWidget.getId());
        assertEquals(widgetFour.getXIndex(), createdWidget.getXIndex());
        assertEquals(widgetFour.getYIndex(), createdWidget.getYIndex());
        assertEquals(widgetFour.getZIndex(), createdWidget.getZIndex());
    }

    @Test
    void update() throws CloneNotSupportedException {
        Widget widgetFour = Widget.builder().xIndex(5).yIndex(5).zIndex(5).height(1).width(1).build();
        Widget updatedWidget = widgetService.update(1, widgetFour.clone());
        assertEquals(1, updatedWidget.getId());
        assertEquals(widgetFour.getXIndex(), updatedWidget.getXIndex());
        assertEquals(widgetFour.getYIndex(), updatedWidget.getYIndex());
        assertEquals(widgetFour.getZIndex(), updatedWidget.getZIndex());
    }

    @Test
    void delete() throws CloneNotSupportedException {
        widgetService.delete(1);
        assertNotNull(widgetService.get(1));
        assertNull(widgetService.get(1).getId());
    }

    @Test
    void get() throws CloneNotSupportedException {
        Widget widget = widgetService.get(1);
        assertEquals(widgetOne.getId(), widget.getId());
    }

    @Test
    void getList() {
        List<Widget> widgetList = widgetService.getList(0, 100);
        assertEquals(3, widgetList.size());
    }

    @Test
    void filterWidgetsByCoordinates() {
    }
}
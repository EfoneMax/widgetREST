package com.efonemax.widgetREST.controllers;

import com.efonemax.widgetREST.domain.Widget;
import com.efonemax.widgetREST.field.Point;
import com.efonemax.widgetREST.field.Rectangle;
import com.efonemax.widgetREST.services.WidgetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/widget")
public class WidgetController {
    private final WidgetService service;

    public WidgetController(WidgetService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Widget createWidget(@Valid @RequestBody Widget widget) {
        return service.create(widget);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Widget updateWidget(@PathVariable long id, @RequestBody @Valid Widget widget) {
        return service.update(id, widget);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteWidget(@PathVariable long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Widget getWidget(@PathVariable long id) {
        return service.get(id);
    }

   /* @GetMapping("/list/{coordinates}")
    @ResponseStatus(HttpStatus.OK)
    public List<Widget> getWidgets(@PathVariable List<String> coordinates) throws CloneNotSupportedException {
        return service.filterWidgetsByCoordinates(new Point(Integer.parseInt(coordinates.get(0)), Integer.parseInt(coordinates.get(1))),
                new Point(Integer.parseInt(coordinates.get(2)), Integer.parseInt(coordinates.get(3))));
    }*/

    @GetMapping("/list1")
    @ResponseStatus(HttpStatus.OK)
    public List<Widget> getWidgets(@RequestBody List<Point> pointList) throws CloneNotSupportedException {
        return service.filterWidgetsByCoordinates(pointList.get(0), pointList.get(1));
    }
}

package com.efonemax.widgetREST.controllers;

import com.efonemax.widgetREST.domain.Widget;
import com.efonemax.widgetREST.services.WidgetService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Widget createWidget(@Valid @RequestBody Widget widget) {
        return service.create(widget);
    }

    @PutMapping("/{id}")
    public Widget updateWidget(@PathVariable long id, @RequestBody @Valid Widget widget) {
        return service.update(id, widget);
    }

    @DeleteMapping("/{id}")
    public void deleteWidget(@PathVariable long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public Widget getWidget(@PathVariable long id) {
        return service.get(id);
    }

    @GetMapping("/list")
    public List<Widget> getWidgets(@RequestParam(required = false, defaultValue = "0") int page,
                                   @RequestParam(required = false, defaultValue = "10") int size) {
        return service.getList(page, size);
    }
}

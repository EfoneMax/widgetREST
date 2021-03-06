package com.efonemax.widgetREST.bootstrap;

import com.efonemax.widgetREST.domain.Widget;
import com.efonemax.widgetREST.services.WidgetService;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class WidgetLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final WidgetService service;

    public WidgetLoader(WidgetService service) {
        this.service = service;
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Widget firstWidget = Widget.builder()
                .xIndex(1)
                .yIndex(1)
                .zIndex(1)
                .height(2)
                .width(2)
                .build();

        Widget secondWidget = Widget.builder()
                .xIndex(2)
                .yIndex(2)
                .zIndex(0)
                .height(2)
                .width(2)
                .build();

        Widget thirdWidget = Widget.builder()
                .xIndex(2)
                .yIndex(4)
                .zIndex(1)
                .height(2)
                .width(2)
                .build();

        Widget fourthWidget = Widget.builder()
                .xIndex(9)
                .yIndex(9)
                .zIndex(8)
                .height(2)
                .width(2)
                .build();

        service.create(firstWidget);
        service.create(secondWidget);
        service.create(thirdWidget);
        service.create(fourthWidget);
        System.out.println("Widgets loaded!");
    }
}

package com.efonemax.widgetREST.services.map;

import com.efonemax.widgetREST.domain.Widget;
import com.efonemax.widgetREST.field.Field;
import com.efonemax.widgetREST.field.Point;
import com.efonemax.widgetREST.services.WidgetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Profile({"default", "map"})
public class WidgetMapService implements WidgetService {
    private Map<Integer, Widget> idWidgetMap = new ConcurrentHashMap<>();
    private Map<Integer, Integer> idZIndexMap = new ConcurrentHashMap<>();
    private AtomicInteger maxZIndex = new AtomicInteger();
    private Field field = Field.getInstance();

    @Override
    public Widget create(Widget widget) {
        return createOrUpdate(widget);
    }

    @Override
    public Widget update(Integer id, Widget widget) {
        if (id == null) {
            throw new RuntimeException("Widget id cannot be empty");
        }
        widget.setId(id);
        return createOrUpdate(widget);
    }

    @Override
    public void delete(@Min(1) Integer id) {
        Widget removedWidget = idWidgetMap.remove(id);
        idZIndexMap.remove(id);
        if (removedWidget.getZIndex() == maxZIndex.get()) {
            updateMaxZIndexValue();
        }
    }

    @Override
    public Widget get(@Min(1) Integer id) {
        return idWidgetMap.get(id);
    }

    @Override
    public List<Widget> getList(int page, int size) {
        return idWidgetMap.values().stream()
                .sorted(Comparator.comparing(Widget::getZIndex))
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public List<Widget> filterWidgetsByCoordinates(Point lowerLeftPoint, Point upperRightPoint) throws CloneNotSupportedException {
        Set<Integer> widgetIdsByCoordinates = field.getWidgetIdsByCoordinates(lowerLeftPoint, upperRightPoint);
        return idWidgetMap.entrySet().stream()
                .filter(entry -> widgetIdsByCoordinates.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private Integer getNextId() {
        return idWidgetMap.keySet().isEmpty() ? 1 : Collections.max(idWidgetMap.keySet()) + 1;
    }

    private synchronized Widget createOrUpdate(Widget widget) {
        if (widget == null) {
            throw new RuntimeException("Widget cannot be null");
        }

        if (widget.getId() == null) {
            widget.setId(getNextId());
        }

        updateZIndexes(widget);
        widget.setDateTime(LocalDateTime.now());
        idWidgetMap.put(widget.getId(), widget);
        field.writeWidget(widget);

        return widget;
    }

    private synchronized void updateMaxZIndexValue() {
        maxZIndex.set(idWidgetMap.values().stream()
                .map(Widget::getZIndex)
                .max(Integer::compareTo)
                .get());
    }

    private Optional<Integer> getKeyByValue(Map<Integer, ?> map, Object value) {
        return map.entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private void updateZIndexes(Widget widget) {
        if (widget.getZIndex() == null) {
            widget.setZIndex(maxZIndex.addAndGet(1));
        } else {
            if (idZIndexMap.containsValue(widget.getZIndex())) {
                getKeyByValue(idZIndexMap, widget.getZIndex()).ifPresent(key -> {
                            Widget needToIncreaseZWidget = idWidgetMap.get(key);
                            field.eraseWidget(needToIncreaseZWidget);
                            needToIncreaseZWidget.setZIndex(widget.getZIndex() + 1);
                            createOrUpdate(needToIncreaseZWidget);
                        }
                );
            }

        }
        idZIndexMap.put(widget.getId(), widget.getZIndex());
    }
}

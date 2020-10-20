package com.efonemax.widgetREST.repository;

import com.efonemax.widgetREST.domain.Widget;
import org.springframework.data.repository.CrudRepository;

public interface WidgetRepository extends CrudRepository<Widget, Integer> {
}

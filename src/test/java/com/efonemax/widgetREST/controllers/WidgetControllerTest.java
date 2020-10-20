package com.efonemax.widgetREST.controllers;

import com.efonemax.widgetREST.domain.Widget;
import com.efonemax.widgetREST.services.WidgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class WidgetControllerTest {
    private static final String CONTROLLER_PATH = "/widget";
    @Mock
    WidgetService widgetService;

    WidgetController widgetController;

    MockMvc mockMvc;

    Widget widget = Widget.builder().id(1).xIndex(1).yIndex(1).zIndex(1).height(1).width(1).build();
    Widget widget1 = new Widget();

    String json = "{ \"width\": 3, \"height\": 3, \"zindex\": 1, \"yindex\": 3, \"xindex\": 3 }";

    @BeforeEach
    void setUp() {
        widgetController = new WidgetController(widgetService);
        mockMvc = MockMvcBuilders.standaloneSetup(widgetController).build();
    }

    @Test
    void testCreateWidget() throws Exception {
        when(widgetService.create(any())).thenReturn(widget);
        mockMvc.perform(post(CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON).content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())));
    }

    @Test
    void testUpdateWidget() throws Exception {
        Widget newWidget = widget.clone();
        int newZIndex = 100;
        newWidget.setDateTime(LocalDateTime.now());
        newWidget.setZIndex(newZIndex);

        when(widgetService.update(anyInt(), any(Widget.class))).thenReturn(newWidget);

        mockMvc.perform(put(CONTROLLER_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON).content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.zindex", is(newZIndex)))
                .andExpect(jsonPath("$.['Modification date']", is(notNullValue())));
    }

    @Test
    void testDeleteWidget() throws Exception {
        mockMvc.perform(delete(CONTROLLER_PATH + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetWidget() throws Exception {
        when(widgetService.get(anyInt())).thenReturn(widget);

        mockMvc.perform(get(CONTROLLER_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.width", is(notNullValue())))
                .andExpect(jsonPath("$.height", is(notNullValue())));
    }

    @Test
    void testGetWidgets() throws Exception {
        String coordinates = "[ { \"x\": \"1\", \"y\": \"1\" }, { \"x\": \"8\", \"y\": \"8\" } ]";

        when(widgetService.filterWidgetsByCoordinates(any(), any())).thenReturn(Arrays.asList(widget, widget1));

        mockMvc.perform(get(CONTROLLER_PATH + "/list")
                .contentType(MediaType.APPLICATION_JSON).content(coordinates))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}
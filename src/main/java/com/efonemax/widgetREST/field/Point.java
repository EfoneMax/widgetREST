package com.efonemax.widgetREST.field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Point {
    private int x;
    private int y;

    public void increasePCoordinatesBy500() {
        this.x = x + 500;
        this.y = y + 500;
    }
}

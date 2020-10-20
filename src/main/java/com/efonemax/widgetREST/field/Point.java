package com.efonemax.widgetREST.field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Point implements Cloneable {
    private int x;
    private int y;

    public void increasePointCoordinatesBy500() {
        this.x = x + 500;
        this.y = y + 500;
    }

    public Point clone() throws CloneNotSupportedException {
        return (Point) super.clone();
    }
}

package com.efonemax.widgetREST.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "WIDGET")
public class Widget implements Serializable {
    @Builder
    public Widget(Long id, int xIndex, int yIndex, int zIndex, int width, int height, LocalDateTime dateTime) {
        this.id = id;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.zIndex = zIndex;
        this.width = width;
        this.height = height;
        this.dateTime = dateTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "X_INDEX")
    private int xIndex;

    @Column(name = "Y_INDEX")
    private int yIndex;

    @Column(name = "Z_INDEX")
    private Integer zIndex;

    @Column(name = "WIDTH")
    @Min(value = 1, message = "Width must be greater than 0")
    private int width;

    @Column(name = "HEIGHT")
    @Min(value = 1, message = "Height must be greater than 0")
    private int height;

    @JsonProperty("Modification date")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @Column(name = "MODIFICATION_DATE")
    private LocalDateTime dateTime;
}

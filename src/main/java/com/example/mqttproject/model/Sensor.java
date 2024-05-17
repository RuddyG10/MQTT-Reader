package com.example.mqttproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Sensor {
    private String sensorId;
    private String sensorType;
    private Double value;
    private LocalDateTime dateTime;
}

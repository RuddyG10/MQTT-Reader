package com.example.mqttproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Sensor {
    private String sensorId;
    private String sensorType;
    private Double value;
    private String dateTime;

    public Sensor(String sensorId, String sensorType) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
    }
}

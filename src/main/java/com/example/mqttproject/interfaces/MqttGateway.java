package com.example.mqttproject.interfaces;

import com.example.mqttproject.config.MqttOuboundConfig;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {
    void sendToMqtt(@Payload String data, @Header("mqtt_topic") String topic);
}

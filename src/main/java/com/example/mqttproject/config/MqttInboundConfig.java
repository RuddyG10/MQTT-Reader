package com.example.mqttproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
public class MqttInboundConfig {

    @Value("${mqtt.broker}")
    private String broker;
    @Value("${mqtt.topic}")
    private String defaultTopic;

    @Bean
    public MessageChannel mqttInboundChannel(){
        return new DirectChannel();
    }
    @Bean
    public MessageProducer mqttInbound(){
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(broker,"Estacion",defaultTopic);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }
    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public MessageHandler handler(){
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = (String) message.getHeaders().get("mqtt_topic");
                String payload = (String) message.getPayload();
                System.out.println("Mensaje recibido del topico: "+topic+". "+payload);
            }
        };
    }

}

package com.example.mqttproject.services;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.stereotype.Service;

@Service
public class MqttService {
    private IMqttAsyncClient mqttAsyncClient;
    private MqttPahoClientFactory clientFactory;



    public IMqttAsyncClient getMqttAsyncClient() {
        return mqttAsyncClient;
    }

    public void setMqttAsyncClient(IMqttAsyncClient mqttAsyncClient) throws MqttException {
        this.mqttAsyncClient = mqttAsyncClient;
        mqttAsyncClient.connect();
        System.out.println("Cliente ha sido conectado.");
    }

    public MqttPahoClientFactory getClientFactory() {
        return clientFactory;
    }
    public void disconnect(){
        try{
            if (mqttAsyncClient != null && mqttAsyncClient.isConnected()){
                mqttAsyncClient.disconnect();
                System.out.println("Cliente desconectado.");
                mqttAsyncClient.close();
            }
        }catch (MqttException e){
            e.printStackTrace();
        }
    }
}

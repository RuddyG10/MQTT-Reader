package com.example.mqttproject;

import com.example.mqttproject.interfaces.MqttGateway;
import com.example.mqttproject.model.Sensor;
import com.example.mqttproject.model.Station;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class MqttProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqttProjectApplication.class, args);
    }
    @Bean
    public CommandLineRunner run(MqttGateway mqttGateway,ApplicationContext context){
        return args -> {
            Random random = new Random();
            ObjectMapper objectMapper = new ObjectMapper();

            //lista de sensores
            List<Sensor> sensors = new ArrayList<>();
            Sensor senTemperatura = new Sensor("sensor-1","Temperatura");
            Sensor senVelViento = new Sensor("sensor-2","Velocidad del viento");
            Sensor senPrecipitacion = new Sensor("sensor-3","Precipitacion");
            Sensor senHumedad = new Sensor("sensor-4","Humedad");
            Sensor senPreBarometrica = new Sensor("sensor-5","Presion Barometrica");
            Sensor senDireccViento = new Sensor("sensor-6","Direccion del viento");

            sensors.add(senTemperatura);
            sensors.add(senVelViento);
            sensors.add(senDireccViento);
            sensors.add(senPreBarometrica);
            sensors.add(senHumedad);
            sensors.add(senPrecipitacion);

            //Estaciones
            Station station1 = new Station("estacion-1",sensors);
            Station station2 = new Station("estacion-2",sensors);
            List<Station> stations = new ArrayList<>();
            stations.add(station1);
            stations.add(station2);

            //Iniciacion de proceso de lectura MQTT
            try {
                for (Station station:
                        stations) {
                    System.out.println("Evaluando Estacion: "+station.getStationId()+"\n");
                    for (Sensor sensor:
                            station.getSensors()) {
                        sensor.setDateTime(LocalDateTime.now().toString());
                        switch (sensor.getSensorType()){
                            case "Temperatura":
                                sensor.setValue(15 + (10*random.nextDouble()));
                                break;
                                case "Velocidad del viento":
                                    sensor.setValue(random.nextDouble()*20);
                                    break;
                                    case "Precipitacion":
                                        sensor.setValue(random.nextDouble()*100);
                                        break;
                                    case "Humedad":
                                        sensor.setValue(30+(20*random.nextDouble()));
                                        break;
                                    case "Presion Barometrica":
                                        sensor.setValue(1000 + (20 * random.nextDouble()));
                                        break;
                                    case "direccion del viento":
                                        sensor.setValue(random.nextDouble()*360);
                                        break;
                        }
                        String topic = "estacion/"+station.getStationId()+"/sensores/"+sensor.getSensorType();
                        String sensorData = objectMapper.writeValueAsString(sensor);
                        System.out.println("Enviado datos del sensor: "
                                +sensor.getSensorType()+", de la estacion: "
                                +station.getStationId()+", al topico: "
                                +topic);
                        mqttGateway.sendToMqtt(sensorData,topic);
                        Thread.sleep(5000);

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("Informacion Recibida.\n");
            System.out.println("Cerrando aplicacion...");
            SpringApplication.exit(context,()->0);

        };
    }

}

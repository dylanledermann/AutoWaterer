package com.autowaterer.autowatererbackend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MoistureService {
    
    @Value("${app.arduino.ip}")
    private String ArduinoIP;

    public String getMoisture(){
        System.out.println(ArduinoIP);
        return "text";
    }
}

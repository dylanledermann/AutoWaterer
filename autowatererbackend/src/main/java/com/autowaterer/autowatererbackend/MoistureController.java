package com.autowaterer.autowatererbackend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MoistureController {
    
    private final MoistureObserver moistureObserver;
    private final MoistureService moistureService;
    public MoistureController(MoistureObserver moistureObserver, MoistureService moistureService){
        this.moistureObserver = moistureObserver;
        this.moistureService = moistureService;
    }

    @GetMapping("/api/moisture")
    public int getMoisture(){   
        return moistureObserver.getMoisture();
    }

    @PostMapping("/api/moisture")
    public void sendMoisture(){
        moistureService.sendMoisture();
    }
}

package com.autowaterer.autowatererbackend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MoistureController {
    
    private final MoistureObserver moistureObserver;

    public MoistureController(MoistureObserver moistureObserver){
        this.moistureObserver = moistureObserver;
    }

    @GetMapping("/api/moisture")
    public int moisture(){   
        return moistureObserver.getMoisture();
    }
}

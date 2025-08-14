package com.autowaterer.autowatererbackend;


import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoistureController {
    
    private final MoistureObserver moistureObserver;

    public MoistureController(MoistureObserver moistureObserver){
        this.moistureObserver = moistureObserver;
    }

    @GetMapping("/api/moisture")
    public String moisture(){
        return "{\'Moisture\':\'" + Integer.toString(moistureObserver.getMoisture()) + "\'}";
    }
}

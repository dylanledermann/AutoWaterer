package com.autowaterer.autowatererbackend;

import org.springframework.stereotype.Service;

@Service
public class MoistureObserver {
    private int moisture;

    public MoistureObserver(){
        this.moisture = 0;
    }

    public void setMoisture(int moisture){
        this.moisture = moisture;
    }

    public int getMoisture(){
        return moisture;
    }
}

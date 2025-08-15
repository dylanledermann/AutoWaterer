package com.autowaterer.autowatererbackend;

import org.springframework.stereotype.Service;

@Service
public class MoistureService {
    private final ArduinoWebSocketClientService webSocket;
    public MoistureService(ArduinoWebSocketClientService webSocket){
        this.webSocket = webSocket;
    }

    public void sendMoisture(){
        webSocket.sendMessage("Start moisture");
    }
}

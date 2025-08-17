package com.autowaterer.autowatererbackend;

import org.springframework.stereotype.Service;

@Service
public class MoistureService {
    private final ArduinoWebSocketClient webSocket;
    public MoistureService(ArduinoWebSocketClient webSocket){
        this.webSocket = webSocket;
    }

    public void sendMoisture(){
        webSocket.sendMessage("Start moisture");
    }
}

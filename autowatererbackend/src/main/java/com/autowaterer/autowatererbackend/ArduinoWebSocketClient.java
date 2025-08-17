package com.autowaterer.autowatererbackend;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class ArduinoWebSocketClient extends WebSocketClient {
    // Logger to log messages
    private static final Logger logger = LoggerFactory.getLogger(ArduinoWebSocketClient.class);
    private final MoistureObserver moistureObserver;
    private final FrontendWebSocketServer ws;

    public ArduinoWebSocketClient(@Value("${app.arduino.ip}") String ArduinoIP, @Value("${app.arduino.port}") String ArduinoPort, MoistureObserver moistureObserver, FrontendWebSocketServer ws) throws URISyntaxException {
        super(new URI("ws://" + ArduinoIP + ":" + ArduinoPort));
        this.moistureObserver = moistureObserver;
        this.ws = ws;
    }

    @Override
    public void onOpen(ServerHandshake handshake){
        logger.info("Connected to Arduino");
    }

    @Override
    public void onClose(int code, String reason, boolean remote){
        logger.info("Connection to arduino close: {} - {}", code, reason);
    }

    @Override
    public void onMessage(String message){
        JSONObject json = new JSONObject(message);
        int newMoisture = json.getInt("Moisture");
        if(newMoisture != moistureObserver.getMoisture()){
            moistureObserver.setMoisture(newMoisture);
            ws.broadcastMoisture();
        }
    }

    @Override
    public void onError(Exception e) {
        logger.error("Websocket error: ", e);
    }

    public void sendMessage(String message){
        send(message);
    }

    @PostConstruct
    public void connectToArduino(){
        this.connect();
    }
}

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
public class ArduinoWebSocketClientService extends WebSocketClient {
    // Logger to log messages
    private static final Logger logger = LoggerFactory.getLogger(ArduinoWebSocketClientService.class);
    private final MoistureObserver moistureObserver;

    public ArduinoWebSocketClientService(@Value("${app.arduino.ip}") String ArduinoIP, @Value("${app.arduino.port}") String ArduinoPort, MoistureObserver moistureObserver) throws URISyntaxException {
        super(new URI("ws://" + ArduinoIP + ":" + ArduinoPort));
        this.moistureObserver = moistureObserver;
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
        moistureObserver.setMoisture(json.getInt("Moisture"));
    }

    @Override
    public void onError(Exception e) {
        logger.error("Websocket erro: ", e);
    }

    public void sendMessage(String message){
        send(message);
    }

    @PostConstruct
    public void connectToArduino(){
        this.connect();
    }
}

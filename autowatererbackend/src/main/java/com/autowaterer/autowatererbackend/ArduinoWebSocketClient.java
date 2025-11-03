package com.autowaterer.autowatererbackend;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
    private final MoistureLogger mLog;
    private final MoistureObserver moistureObserver;
    private final int MAX_RECONNECTS = 3;
    private int reconnects = 0;
    private final String ArduinoIP;
    private final String ArduinoPort;

    public ArduinoWebSocketClient(@Value("${app.arduino.ip}") String ArduinoIP, @Value("${app.arduino.port}") String ArduinoPort, MoistureLogger mLog, MoistureObserver moistureObserver) throws URISyntaxException {
        super(new URI("ws://" + ArduinoIP + ":" + ArduinoPort));
        this.ArduinoIP = ArduinoIP;
        this.ArduinoPort = ArduinoPort;
        this.mLog = mLog;
        this.moistureObserver = moistureObserver;
    }

    @Override
    public void onOpen(ServerHandshake handshake){
        logger.info("Connected to Arduino");
        send("day");
        send("week");
        send("month");
        this.reconnects = 0;
    }

    @Override
    public void onClose(int code, String reason, boolean remote){
        logger.info("Connection to arduino close: {} - {}", code, reason);
        try{
            if(this.reconnects < MAX_RECONNECTS){
                Thread.sleep(60000);
                this.reconnects++;
                logger.info("Attempting to reconnect");
                ArduinoWebSocketClient ws = new ArduinoWebSocketClient(this.ArduinoIP, this.ArduinoPort, this.mLog, this.moistureObserver);
                ws.connect();
            }
            else{
                logger.info("Max amount of reconnects occured");
            }
        } catch (Exception e){
            logger.error("Reconnect error: ", e);
        }
    }

    @Override
    public void onMessage(ByteBuffer message){
        message.order(ByteOrder.BIG_ENDIAN);
        if(message.hasArray()){
            byte[]  byteArray = message.array();
            logger.info("Array from ByteBuffer: ");
            for(byte b: byteArray){
                System.out.print(String.valueOf(b & 0xFF) + " ");
            }
            System.out.println();
        }
        else{
            byte[] byteArray = new byte[message.remaining()];
            message.get(byteArray);
            logger.info("Array not from ByteBuffer: ", byteArray.toString());
            for(byte b: byteArray){
                System.out.print(String.valueOf(b & 0xFF) + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void onMessage(String message){
        JSONObject json = new JSONObject(message);
        int newMoisture = json.getInt("Moisture");
        if(newMoisture != moistureObserver.getMoisture()){
            this.mLog.logMoisture(newMoisture);
            moistureObserver.setMoisture(newMoisture);
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

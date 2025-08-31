package com.autowaterer.autowatererbackend;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class FrontendWebSocketServer extends WebSocketServer{

    private static final Logger logger = LoggerFactory.getLogger(ArduinoWebSocketClient.class);
    private MoistureObserver moistureObserver;
    private ArduinoWebSocketClient ws;
    public FrontendWebSocketServer(MoistureObserver moistureObserver, ArduinoWebSocketClient ws) throws UnknownHostException{
        super(new InetSocketAddress(3000));
        logger.info("Socket Address: {}", new InetSocketAddress(3000));
        this.moistureObserver = moistureObserver;
        this.ws = ws;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        logger.info("{} has connected", conn.getRemoteSocketAddress().getAddress().getHostAddress());
        conn.send(Integer.toString(moistureObserver.getMoisture()));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        logger.info("{} has disconnected. {}: {}", conn, code, reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        logger.info("Received message: {}", message);
        if(message.equals("Add Moisture")){
            ws.send("Turn On Water");
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        logger.error("Websocket server error: {}", ex);
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        logger.info("Server started!");
        setConnectionLostTimeout(100);
    }

    public void broadcastMoisture(){
        broadcast(Integer.toString(moistureObserver.getMoisture()));
    }
    
    @PostConstruct
    public void startWebsocket(){
        this.start();
    }
}

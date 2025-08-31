package com.autowaterer.autowatererbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AutowatererbackendApplication {

	public static void main(String[] args) {
		ApplicationContext  context = SpringApplication.run(AutowatererbackendApplication.class, args);
		var moistureLogger = context.getBean(MoistureLogger.class);
		var moistureObserver = context.getBean(MoistureObserver.class);
		var webSocketServer = context.getBean(FrontendWebSocketServer.class);
		var webSocketClient = context.getBean(ArduinoWebSocketClient.class);
		var moistureService = context.getBean(MoistureService.class);
		var moistureController = context.getBean(MoistureController.class);
	}

}

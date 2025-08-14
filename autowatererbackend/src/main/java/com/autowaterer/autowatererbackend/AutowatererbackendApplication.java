package com.autowaterer.autowatererbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AutowatererbackendApplication {

	public static void main(String[] args) {
		ApplicationContext  context = SpringApplication.run(AutowatererbackendApplication.class, args);
		var moistureObserver = context.getBean(MoistureObserver.class);
		var webSocketService = context.getBean(ArduinoWebSocketClientService.class);
		var moistureController = context.getBean(MoistureController.class);
	}

}

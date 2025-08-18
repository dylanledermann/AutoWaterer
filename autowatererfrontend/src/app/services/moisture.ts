import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID, inject, signal } from '@angular/core';
import { catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Moisture {
  moisture = signal("Connecting");
  ws?: WebSocket;

  constructor (@Inject(PLATFORM_ID) private platformId: Object){
    if (isPlatformBrowser(this.platformId)){
      this.initWebSocket();
    }
  }
  
  private initWebSocket(){
    this.ws = new WebSocket('ws://192.168.5.83:3000');

    // Listener for when the websocket opens
    this.ws.addEventListener('open', event => {
      console.log("Websocket connection established");
    });

    // Listener for when the websocket closes
    this.ws.addEventListener('close', event => {
      console.log("Websocket connected closed");
    });

    // Listener for when a message is received from the websocket
    this.ws.addEventListener('message', event => {
      this.moisture.set(event.data);
      return event;
    });

    // Listener for when an error occurs
    this.ws.addEventListener('error', event => {
      console.log("Websocket error: ", event);
    });
  }

  // Send websocket message to water the plant
  waterPlant() {
    this.ws?.send("Add Moisture");
  }
}

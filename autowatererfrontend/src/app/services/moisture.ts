import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Moisture {
  http = inject(HttpClient);
  moisture = signal(0);
  ws = new WebSocket('ws://localhost:3000');
  

  // Websocket creation method
  createMoistureWebsocket(){
    // Listener for when the websocket connects
    this.ws.addEventListener('open', event => {
      console.log("Websocket connection established");
    });

    // Listener for when the websocket closes
    this.ws.addEventListener('close', event => {
      console.log("Websocket connected closed");
    });

    // Listener for when a message is received from the websocket
    this.ws.addEventListener('message', event => {
      this.moisture.set(Number(event.data));
      return event;
    });

    // Listener for when an error occurs
    this.ws.addEventListener('error', event => {
      console.log("Websocket error: ", event);
    });
  }

  // Send websocket message to water the plant
  waterPlant() {
    this.ws.send("Add Moisture");
  }

  // Get moisture from api function
  getMoistureFromApi() {
    const url = `http://localhost:8080/api/moisture`;
    return this.http.get<number>(url);
  }

  // Calls the get moisture from api method. Parses the message for the html and ts
  getMoisture(){
    this.getMoistureFromApi()
      .pipe(
        catchError((err) => {
          console.log(err);
          throw err;
        })
      )
      .subscribe((moisture: number) => {
        this.moisture.set(moisture);
      });

    return this.moisture;
  }

  // Sends post request to the backend that tells the backend to water the plant if possible
  addMoistureFromApi(){
    const url = `http://localhost:8080/api/moisture`;
    return this.http.post(url, null);
  }
}

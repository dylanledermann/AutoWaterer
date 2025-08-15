import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Moisture {
  http = inject(HttpClient);
  moisture = signal(0);
  getMoistureFromApi(){
    const url = `http://localhost:8080/api/moisture`;
    return this.http.get<number>(url);
  }
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
  addMoistureFromApi(){
    const url = `http://localhost:8080/api/moisture`;
    return this.http.post(url, null);
  }
}

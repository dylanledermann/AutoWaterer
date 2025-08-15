import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Moisture {
  http = inject(HttpClient);
  getMoistureFromApi(){
    const url = `http://localhost:8080/api/moisture`;
    return this.http.get<number>(url);
  }
}

import { Component, inject, signal } from '@angular/core';
import { Moisture } from '../services/moisture';
import { catchError } from 'rxjs';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {
  moisture = signal(0);
  moistureApi = inject(Moisture);
  ngOnInit(): void {
    this.moistureApi.getMoistureFromApi()
      .pipe(
        catchError((err) => {
          console.log(err);
          throw err;
        })
      )
      .subscribe((moisture: number) => {
        this.moisture.set(moisture);
      });
  }
}

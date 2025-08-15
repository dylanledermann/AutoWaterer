import { Component, inject, signal } from '@angular/core';
import { Moisture } from '../../services/moisture';
import { catchError } from 'rxjs';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header {
  protected readonly title = signal('Auto Water');
  moistureApi = inject(Moisture);
  waterPlant() {
    this.moistureApi.addMoistureFromApi()
    .pipe(
      catchError((err) => {
        console.log(err);
        throw err;
      })
    )
    .subscribe((m) => {
      console.log(m);
    });;
  }
}

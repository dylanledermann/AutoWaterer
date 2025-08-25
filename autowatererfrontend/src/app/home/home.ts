import { Component, effect, Inject, inject, Renderer2 } from '@angular/core';
import { Moisture } from '../services/moisture';
import {DOCUMENT} from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {
  constructor(
    private renderer: Renderer2,
    @Inject(DOCUMENT) private document: Document
  ){
    effect(() => {
      if(this.moisture() != "Connecting"){
        var adjustedHeight = 100-Number(this.moisture());
        this.changeWaveHeight(adjustedHeight.toString() + "%");
      }
    }
    )
  }
  moistureApi = inject(Moisture);
  moisture = this.moistureApi.moisture;
  changeWaveHeight(height: string){
    const currentValue = getComputedStyle(this.document.documentElement)
      .getPropertyValue('--wave-height');
    this.document.documentElement.style.cssText = "--wave-height: " + height;
    console.log('Current CSS value:', currentValue);
  }
}

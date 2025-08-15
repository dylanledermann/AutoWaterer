import { Component, Inject, inject, Renderer2 } from '@angular/core';
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
  ){}
  moistureApi = inject(Moisture);
  moisture = this.moistureApi.moisture;
  ngOnInit(): void {
    this.moisture = this.moistureApi.getMoisture();
    var adjustedHeight = 100-this.moisture();
    this.changeWaveHeight(adjustedHeight.toString() + "%");
  }
  changeWaveHeight(height: string){
    this.renderer.setStyle(
      this.document.documentElement,
      '--wave-height',
      height
    )
  }
}

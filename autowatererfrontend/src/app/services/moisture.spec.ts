import { TestBed } from '@angular/core/testing';

import { Moisture } from './moisture';

describe('Moisture', () => {
  let service: Moisture;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Moisture);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

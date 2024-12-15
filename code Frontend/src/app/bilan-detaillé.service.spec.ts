import { TestBed } from '@angular/core/testing';

import { BilanDetailleService } from './Services/bilan-detaillé.service';

describe('BilanDetailléService', () => {
  let service: BilanDetailleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BilanDetailleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

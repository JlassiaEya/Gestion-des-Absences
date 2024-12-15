import { TestBed } from '@angular/core/testing';

import { BilanGlobalService } from './Services/bilan-global.service';

describe('BilanGlobalService', () => {
  let service: BilanGlobalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BilanGlobalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

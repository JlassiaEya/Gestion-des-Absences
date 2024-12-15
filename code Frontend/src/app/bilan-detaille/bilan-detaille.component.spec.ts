import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BilanDetailleComponent } from './bilan-detaille.component';

describe('BilanDetailleComponent', () => {
  let component: BilanDetailleComponent;
  let fixture: ComponentFixture<BilanDetailleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BilanDetailleComponent]
    });
    fixture = TestBed.createComponent(BilanDetailleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

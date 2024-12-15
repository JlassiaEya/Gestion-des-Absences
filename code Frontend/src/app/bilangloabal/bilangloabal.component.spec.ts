import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BilangloabalComponent } from './bilangloabal.component';

describe('BilangloabalComponent', () => {
  let component: BilangloabalComponent;
  let fixture: ComponentFixture<BilangloabalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BilangloabalComponent]
    });
    fixture = TestBed.createComponent(BilangloabalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

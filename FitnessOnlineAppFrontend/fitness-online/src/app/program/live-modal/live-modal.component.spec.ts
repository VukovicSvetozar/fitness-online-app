import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveModalComponent } from './live-modal.component';

describe('LiveModalComponent', () => {
  let component: LiveModalComponent;
  let fixture: ComponentFixture<LiveModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LiveModalComponent]
    });
    fixture = TestBed.createComponent(LiveModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

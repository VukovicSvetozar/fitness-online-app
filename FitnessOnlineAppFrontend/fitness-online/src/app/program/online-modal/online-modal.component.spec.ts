import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OnlineModalComponent } from './online-modal.component';

describe('OnlineModalComponent', () => {
  let component: OnlineModalComponent;
  let fixture: ComponentFixture<OnlineModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OnlineModalComponent]
    });
    fixture = TestBed.createComponent(OnlineModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

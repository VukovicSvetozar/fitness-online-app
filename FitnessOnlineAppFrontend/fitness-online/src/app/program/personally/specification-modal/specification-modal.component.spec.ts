import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecificationModalComponent } from './specification-modal.component';

describe('SpecificationModalComponent', () => {
  let component: SpecificationModalComponent;
  let fixture: ComponentFixture<SpecificationModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SpecificationModalComponent]
    });
    fixture = TestBed.createComponent(SpecificationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

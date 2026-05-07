import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Mesapartes } from './mesapartes';

describe('Mesapartes', () => {
  let component: Mesapartes;
  let fixture: ComponentFixture<Mesapartes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Mesapartes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Mesapartes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

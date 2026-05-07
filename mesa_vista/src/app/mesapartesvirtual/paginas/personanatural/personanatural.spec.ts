import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Personanatural } from './personanatural';

describe('Personanatural', () => {
  let component: Personanatural;
  let fixture: ComponentFixture<Personanatural>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Personanatural]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Personanatural);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

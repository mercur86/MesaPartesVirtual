import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Personanajuridica } from './personanajuridica';

describe('Personanajuridica', () => {
  let component: Personanajuridica;
  let fixture: ComponentFixture<Personanajuridica>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Personanajuridica]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Personanajuridica);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

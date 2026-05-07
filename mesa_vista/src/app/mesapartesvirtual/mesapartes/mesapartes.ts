import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-mesapartes',
  imports: [ButtonModule, FormsModule, RouterLink, DialogModule],
  templateUrl: './mesapartes.html',
  styleUrl: './mesapartes.scss'
})
export class Mesapartes {
  aceptarTerminos = false;
  mostrarTerminos = false;
}
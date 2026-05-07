import { Component, Input, Output, EventEmitter } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { SelectModule } from 'primeng/select';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
  selector: 'app-documentos-step',
  imports: [ButtonModule, CardModule, SelectModule, FormsModule, InputTextModule, MessageModule, ProgressSpinnerModule],
  templateUrl: './documentos-step.component.html',
})
export class DocumentosStepComponent {
  @Input() listaTipoDocumento: any[] = [];
  @Input() selectedTipoDocumento: any;
  @Output() selectedTipoDocumentoChange = new EventEmitter<any>();

  @Input() asunto = '';
  @Output() asuntoChange = new EventEmitter<string>();

  @Input() numDoc = '';
  @Output() numDocChange = new EventEmitter<string>();

  @Input() archivo: File | null = null;
  @Input() anexos: File[] = [];

  @Input() activateCallback!: Function;
  @Input() pasoPrevio = 1;
  @Input() modo: 'documentos' | 'final' = 'documentos';

  @Output() fileSelected = new EventEmitter<any>();
  @Output() anexosSelected = new EventEmitter<any>();
  @Output() siguiente = new EventEmitter<void>();
  @Output() finalizar = new EventEmitter<void>();

  @Input() loading = false;
}
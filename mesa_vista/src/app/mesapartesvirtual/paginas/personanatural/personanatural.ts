import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { StepperModule } from 'primeng/stepper';
import { CardModule } from 'primeng/card';
import { SelectModule } from 'primeng/select';
import { FormsModule, NgForm } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { MessageService } from 'primeng/api';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { Usuario } from '@/mesapartesvirtual/models/usuario';
import { Persona } from '@/mesapartesvirtual/models/Persona';
import { BaseMesaPartesComponent } from '../base-mesapartes.component';
import { DocumentosStepComponent } from '../documentos-step/documentos-step.component';

@Component({
  selector: 'app-personanatural',
  imports: [StepperModule, ButtonModule, CardModule, SelectModule, FormsModule,
    InputTextModule, MessageModule, ToastModule, DialogModule, DocumentosStepComponent],
  providers: [MessageService],
  templateUrl: './personanatural.html',
  styleUrl: './personanatural.scss'
})
export class Personanatural extends BaseMesaPartesComponent {
  user = new Usuario();
  persona = new Persona();
  loading = false;
  showSuccessDialog = false;

  get documentoLength(): number {
    return this.selectedDocumentoIdentidad?.code === 'RM' ? 9 : 8;
  }

  initDocumentosIdentidad() {
    this.documentosIdentidad = [
      { name: 'DNI', code: 'NY' },
      { name: 'CARNET DE EXTRANGERÍA', code: 'RM' },
    ];
  }

  onSubmitReniec(form: NgForm) {
    if (form.valid) {
      this.apiService.getDataReniec(this.persona.document_number).subscribe({
        next: (response: Persona) => {
          Object.assign(this.persona, response);
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Persona encontrada',
            life: 5000
          });
        },
        error: (err) => {
          console.error('Error fetching data:', err);
          const numeroDocumento = this.persona.document_number;
          this.persona = new Persona();
          this.persona.document_number = numeroDocumento;
          this.messageService.add({ severity: 'error', summary: 'error', detail: 'Persona no encontrada ingrese datos manualmente', life: 5000 });
        }
      });
    }
  }

  asignarUbigeo() {
    if (this.selectedUbigeo) {
      this.persona.ubigeo = this.selectedUbigeo.id;
    }
  }

  finalizar() {
    if (!this.archivo || !this.asunto.trim() || !this.numDoc.trim() || !this.selectedUbigeo || !this.persona.celular.trim() || !this.persona.correo.trim()) {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Debe completar todos los campos obligatorios', life: 5000 });
      return;
    }

    this.loading = true;

    const ubigeoParts = this.getUbigeoParts();
    if (!ubigeoParts) {
      this.loading = false;
      return;
    }

    const nuAnnExp = new Date().getFullYear().toString();

    const mpv = {
      documento: {
        nuDoc: this.numDoc,
        deAsu: this.asunto,
        deObservacion: '',
        coTipDocAdm: this.selectedTipoDocumento?.cdocTipdoc || '001',
      },
      expediente: {
        nuAnnExp: nuAnnExp,
        coTipoExp: "20"
      },
      remitente: {
        nuDni: this.persona.document_number,
        nuRuc: '',
        coOtros: '',
        telefono: this.persona.celular,
        idDepartamento: ubigeoParts.idDepartamento,
        idProvincia: ubigeoParts.idProvincia,
        idDistrito: ubigeoParts.idDistrito,
        deCorreo: this.persona.correo,
        deDireccion: this.persona.domicilio,
        apePaterno:this.persona.first_last_name,
        apeMaterno:this.persona.second_last_name,
        nombre:this.persona.first_name

      }
    };

    this.apiService.postExpediente(mpv, this.archivo, this.anexos).subscribe({
      next: () => {
        this.loading = false;
        this.showSuccessDialog = true;
      },
      error: (err) => {
        this.loading = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error al enviar la solicitud: ',
          life: 5000
        });
      }
    });
  }

  cerrarDialogoYRedirigir() {
    this.showSuccessDialog = false;
    this.navegarAlInicio();
  }
}

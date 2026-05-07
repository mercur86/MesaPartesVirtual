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
import { Usuario } from '@/mesapartesvirtual/models/usuario';
import { Persona } from '@/mesapartesvirtual/models/Persona';
import { BaseMesaPartesComponent } from '../base-mesapartes.component';
import { DocumentosStepComponent } from '../documentos-step/documentos-step.component';

@Component({
  selector: 'app-personanatural',
  imports: [StepperModule, ButtonModule, CardModule, SelectModule, FormsModule,
    InputTextModule, MessageModule, ToastModule, DocumentosStepComponent],
  providers: [MessageService],
  templateUrl: './personanatural.html',
  styleUrl: './personanatural.scss'
})
export class Personanatural extends BaseMesaPartesComponent {
  user = new Usuario();
  persona = new Persona();
  loading = false;

  initDocumentosIdentidad() {
    this.documentosIdentidad = [
      { name: 'DNI', code: 'NY' },
      { name: 'CARNET DE EXTRANGERÍA', code: 'RM' },
      { name: 'PASAPORTE', code: 'LDN' }
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
        coDepEmi: '00085',
        coDep: '00085',
        tiEmi: '02',
        nuDni: this.persona.document_number,
        coEmpEmi: '00000',
        nuRuc: '',
        coOtros: '',
        coEmpRes: '00000',
        telefono: this.persona.celular,
        idDepartamento: ubigeoParts.idDepartamento,
        idProvincia: ubigeoParts.idProvincia,
        idDistrito: ubigeoParts.idDistrito,
        deCorreo: this.persona.correo,
        deDireccion: this.persona.domicilio
      }
    };

    this.apiService.postExpediente(mpv, this.archivo, this.anexos).subscribe({
      next: () => {
        this.loading = false;
        this.navegarAlInicio();
      },
      error: (err) => {
        this.loading = false;
        console.error('Error al enviar expediente:', err);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error al enviar la solicitud: ' + (err.error?.message || err.message),
          life: 5000
        });
      }
    });
  }
}

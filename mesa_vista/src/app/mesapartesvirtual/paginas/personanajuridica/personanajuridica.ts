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
import { BaseMesaPartesComponent } from '../base-mesapartes.component';
import { Empresa } from '@/mesapartesvirtual/models/Empresa';
import { DocumentosStepComponent } from '../documentos-step/documentos-step.component';

@Component({
  selector: 'app-personanajuridica',
  imports: [StepperModule, ButtonModule, CardModule, SelectModule, FormsModule,
    InputTextModule, MessageModule, ToastModule, DocumentosStepComponent],
  providers: [MessageService],
  templateUrl: './personanajuridica.html',
  styleUrl: './personanajuridica.scss'
})
export class Personanajuridica extends BaseMesaPartesComponent {
  user = new Usuario();
  empresa = new Empresa();
  loading = false;

  initDocumentosIdentidad() {
    this.documentosIdentidad = [
      { name: 'RUC', code: 'RUC' },
    ];
  }

  onSubmitSunat(form: NgForm) {
    if (form.valid) {
      this.apiService.getDataSunat(this.empresa.numero_documento).subscribe({
        next: (response: Empresa) => {
          Object.assign(this.empresa, response);
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Persona jurídica encontrada',
            life: 5000
          });
        },
        error: (err) => {
          console.error('Error fetching data:', err);
          const numeroDocumento = this.empresa.numero_documento;
          this.empresa = new Empresa();
          this.empresa.numero_documento = numeroDocumento;
          this.messageService.add({ severity: 'error', summary: 'error', detail: 'Persona jurídica no encontrada ingrese datos manualmente', life: 5000 });
        }
      });
    }
  }

  asignarUbigeo() {
    if (this.selectedUbigeo) {
      this.empresa.ubigeo = this.selectedUbigeo.id;
    }
  }

  finalizar() {
    if (!this.archivo || !this.asunto.trim() || !this.numDoc.trim() || !this.selectedUbigeo || !this.empresa.celular.trim() || !this.empresa.correo.trim()) {
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
        nuDni: '',
        coEmpEmi: '00000',
        nuRuc: this.empresa.numero_documento,
        coOtros: '',
        coEmpRes: '00000',
        telefono: this.empresa.celular,
        idDepartamento: ubigeoParts.idDepartamento,
        idProvincia: ubigeoParts.idProvincia,
        idDistrito: ubigeoParts.idDistrito,
        deCorreo: this.empresa.correo,
        deDireccion: this.empresa.direccion,
        deNomOtrosRes: this.empresa.razon_social
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
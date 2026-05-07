import { inject, OnInit, Directive } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Api } from '@/mesapartesvirtual/servicios/apiService';
import { Ubigeo } from '@/mesapartesvirtual/models/Ubigeo';
import { TipoDocumento } from '@/mesapartesvirtual/models/tipoDocumento';
import { NgForm } from '@angular/forms';
import { TipoDocumentoIdentidad } from '@/mesapartesvirtual/models/documentosIdentidad';

@Directive()
export abstract class BaseMesaPartesComponent implements OnInit {
  protected messageService = inject(MessageService);
  protected apiService = inject(Api);
  protected router = inject(Router);

  documentosIdentidad: TipoDocumentoIdentidad[] | undefined;
  listaubigeo: Ubigeo[] | undefined = [];
  listaTipoDocumento: any[] = [];
  
  selectedDocumentoIdentidad: TipoDocumentoIdentidad | undefined;
  selectedTipoDocumento: TipoDocumento | undefined;
  selectedUbigeo: Ubigeo | undefined;
  
  asunto = '';
  numDoc = '';
  archivo: File | null = null;
  anexos: File[] = [];

  ngOnInit() {
    this.initDocumentosIdentidad();
    this.cargarTiposDocumento();
  }

  abstract initDocumentosIdentidad(): void;

  cargarTiposDocumento() {
    this.apiService.getData("api/public/listaTipoDocumento").subscribe({
      next: (response: any) => {
        this.listaTipoDocumento = response.data;
      },
      error: (err) => {
        console.error('Error fetching data:', err);
      }
    });
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file && file.type === 'application/pdf') {
      const maxSizeMB = 30;
      const maxSizeBytes = maxSizeMB * 1024 * 1024;
      if (file.size > maxSizeBytes) {
        this.archivo = null;
        this.messageService.add({ severity: 'error', summary: 'Error', detail: `El archivo no puede superar los ${maxSizeMB}MB`, life: 5000 });
        return;
      }
      this.archivo = file;
    } else {
      this.archivo = null;
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Solo se permiten archivos PDF', life: 5000 });
    }
  }

  onAnexosSelected(event: any) {
    const files: FileList = event.target.files;
    if (files && files.length > 0) {
      const maxTotalSizeMB = 450;
      const maxTotalSizeBytes = maxTotalSizeMB * 1024 * 1024;
      const newFiles = Array.from(files) as File[];
      const newFilesSize = newFiles.reduce((acc, file) => acc + file.size, 0);
      if (newFilesSize > maxTotalSizeBytes) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: `Los anexos no pueden superar los ${maxTotalSizeMB}MB en total`, life: 5000 });
        return;
      }
      this.anexos = newFiles;
    }
  }

  buscarUbigeo(event: any) {
    if (event && event.filter.length >= 2) {
      this.apiService.getData("reniec/listar?q=" + event.filter).subscribe({
        next: (response) => {
          this.listaubigeo = response.data;
        },
        error: (err) => {
          console.error('Error fetching data:', err);
        }
      });
    }
  }

  irAlSiguientePaso(form: NgForm, activateCallback: Function, stepPaso: number) {
    form.control.markAllAsTouched();
    let isValid = form.valid;

    if (stepPaso === 3) {
      if (!this.archivo) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Debe seleccionar un archivo PDF', life: 5000 });
        isValid = false;
      }
      if (!this.asunto.trim()) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'El asunto es obligatorio', life: 5000 });
        isValid = false;
      }
      if (!this.numDoc.trim()) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'El número de documento es obligatorio', life: 5000 });
        isValid = false;
      }
    }

    if (isValid) {
      activateCallback(stepPaso);
    } else {
      console.log('Formulario incompleto');
    }
  }

  /**
   * Versión sin NgForm para usar desde el componente hijo DocumentosStepComponent.
   * Valida los campos requeridos directamente en el estado de la clase.
   */
  irAlSiguientePasoDesdeHijo(activateCallback: Function, stepPaso: number) {
    let isValid = true;

    if (!this.archivo) {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Debe seleccionar un archivo PDF', life: 5000 });
      isValid = false;
    }
    if (!this.asunto.trim()) {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'El asunto es obligatorio', life: 5000 });
      isValid = false;
    }
    if (!this.numDoc.trim()) {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'El número de documento es obligatorio', life: 5000 });
      isValid = false;
    }

    if (isValid) {
      activateCallback(stepPaso);
    } else {
      console.log('Formulario incompleto');
    }
  }

  protected getUbigeoParts() {
    if (!this.selectedUbigeo) return null;
    const ubigeo = this.selectedUbigeo.id;
    return {
      idDepartamento: ubigeo.substring(0, 2),
      idProvincia: ubigeo.substring(2, 4),
      idDistrito: ubigeo.substring(4, 6)
    };
  }

  /**
   * Navega al componente inicial (mesa de partes) tras un envío exitoso.
   * Muestra el toast de éxito y redirige después de 1.5 segundos.
   */
  protected navegarAlInicio() {
    this.messageService.add({
      severity: 'success',
      summary: 'Éxito',
      detail: 'Solicitud enviada correctamente. Será redirigido al inicio.',
      life: 3000
    });
    setTimeout(() => {
      this.router.navigate(['/mesapartes']);
    }, 1500);
  }

  /**
   * Navega inmediatamente al componente de mesa de partes.
   */
  volverAlInicio() {
    this.router.navigate(['/mesapartes']);
  }
}

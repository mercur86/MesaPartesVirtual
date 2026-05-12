import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Api {
  //private apiUrl = 'http://localhost:8081';
  private apiUrl = 'http://sgd.emapat.com.pe:9000';

  private http = inject(HttpClient);
  constructor() { }

  getDataReniec(dni: string): Observable<any> {
    const url = `${this.apiUrl}/reniec/buscar?dni=${dni}`;
    return this.http.get(url);
  }

  getDataSunat(ruc: string): Observable<any> {
    const url = `${this.apiUrl}/api/public/buscar?ruc=${ruc}`;
    return this.http.get(url);
  }

  getToken(): Observable<any> {
    const url = `${this.apiUrl}/api/auth/generartoken`;
    return this.http.get(url);
  }


  postData(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }

  getData(ruta: string): Observable<any> {
    const url = `${this.apiUrl}/${ruta}`;
    return this.http.get(url);
  }

  postExpediente(mpv: any, file: File, anexos: File[] = []): Observable<any> {
    const formData = new FormData();
    formData.append('mpv', new Blob([JSON.stringify(mpv)], { type: 'application/json' }));
    formData.append('file', file);

    for (let i = 0; i < anexos.length; i++) {
      formData.append('anexos', anexos[i]);
    }

    const url = `${this.apiUrl}/api/public/expediente`;
    return this.http.post(url, formData);
  }
}
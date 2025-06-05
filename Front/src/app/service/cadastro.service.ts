import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';
import {UsuarioDTO} from '../model/interface/UsuarioDTO';



@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private apiUrl = 'http://localhost:8080/v1/usuarios';

  constructor(private http: HttpClient) {
  }

  registrarUsuario(usuario: UsuarioDTO): Observable<string> {
    return this.http.post(this.apiUrl + '/registrar', usuario, {responseType: 'text'});
  }
}

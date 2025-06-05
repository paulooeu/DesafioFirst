import {Component, OnInit} from '@angular/core';
import {FormBuilder,  FormGroup,  ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField} from '@angular/material/input';
import {MatCard, MatCardContent, MatCardHeader, MatCardModule} from '@angular/material/card';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatError } from '@angular/material/form-field';
import {CommonModule} from '@angular/common';
import {UsuarioDTO} from './model/interface/UsuarioDTO';
import {UsuarioService} from './service/cadastro.service'; // para standalone
import {MatSnackBar,MatSnackBarModule } from '@angular/material/snack-bar';


@Component({
  selector: 'app-root',
  standalone: true,

  imports: [MatCard,MatFormField, MatCardHeader, MatCardContent,MatCardModule,
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatError,
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App  implements OnInit {
  cadastroForm: FormGroup;


  constructor(private fb: FormBuilder,  private usuarioService: UsuarioService,private matSnackBar: MatSnackBar) {

    this.cadastroForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      senha: ['', [Validators.required, Validators.minLength(6)]],
      confirmarSenha: ['', [Validators.required, Validators.minLength(6)]],
    }, {
      validator: this.mustMatch('senha', 'confirmarSenha')
    });
  }

  ngOnInit(): void {}


  mustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];

      if (matchingControl.errors && !matchingControl.errors['mustMatch']) {
        return;
      }

      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({ mustMatch: true });
      } else {
        matchingControl.setErrors(null);
      }
    };
  }


  onSubmit() {


    if (this.cadastroForm.valid) {
      const dto: UsuarioDTO = this.cadastroForm.value;
      // @ts-ignore
      this.usuarioService.registrarUsuario(dto).subscribe({
        next: (response) => {

          this.matSnackBar.open(response, 'Fechar', {
            duration: 3000, // 3 segundos
            panelClass: ['successsnackbar'],
            horizontalPosition:'center',
            verticalPosition:"top"
          });
          this.cadastroForm.reset();
        },
        error: (err) => {

          this.matSnackBar.open(err.error, 'Fechar', {
            duration: 3000, // 3 segundos
            panelClass: ['errorsnackbar'] ,
            horizontalPosition:'center',
            verticalPosition:"top"
          });

        }
      });
    }
  }



}

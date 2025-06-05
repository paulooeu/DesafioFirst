import {ComponentFixture, TestBed} from '@angular/core/testing';
import {App} from './app';
import {UsuarioService} from './service/cadastro.service';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {FormBuilder, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {of, throwError} from 'rxjs';

describe('App', () => {
  let component: App;
  let fixture: ComponentFixture<App>;


  let usuarioServiceSpy: jasmine.SpyObj<UsuarioService>;
  let matSnackBarSpy: jasmine.SpyObj<MatSnackBar>;

  beforeEach(() => {

    usuarioServiceSpy = jasmine.createSpyObj('UsuarioService', ['registrarUsuario']);
    matSnackBarSpy = jasmine.createSpyObj('MatSnackBar', ['open']);


    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, MatSnackBarModule, HttpClientModule, App],

      providers: [
        FormBuilder,
        {provide: UsuarioService, useValue: usuarioServiceSpy},
        {provide: MatSnackBar, useValue: matSnackBarSpy}
      ]
    }).compileComponents();


    fixture = TestBed.createComponent(App);
    component = fixture.componentInstance;

    component.cadastroForm = new FormBuilder().group({
      nome: [''],
      email: [''],
      senha: [''],
      confirmarSenha: ['']
    });
    fixture.detectChanges();


  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('deve enviar os dados corretamente quando o formulário for válido', () => {

    const response = 'Usuário registrado com sucesso';
    usuarioServiceSpy.registrarUsuario.and.returnValue(of(response));


    component.cadastroForm.setValue({
      nome: 'Teste',
      email: 'teste@teste.com',
      senha: '123456',
      confirmarSenha: '123456'
    });


    component.onSubmit();


    expect(usuarioServiceSpy.registrarUsuario).toHaveBeenCalledWith({
      nome: 'Teste',
      email: 'teste@teste.com',
      senha: '123456',
      confirmarSenha: "123456"
    });

    expect(matSnackBarSpy.open).toHaveBeenCalledWith(response, 'Fechar', jasmine.objectContaining({
      duration: 3000,
      panelClass: ['successsnackbar'],
      horizontalPosition: 'center',
      verticalPosition: 'top'
    }));

  });

  it('deve exibir mensagem de erro quando a chamada do serviço falhar', () => {

    const errorResponse = {error: 'Erro ao registrar o usuário'};
    usuarioServiceSpy.registrarUsuario.and.returnValue(throwError(() => errorResponse));


    component.cadastroForm.setValue({
      nome: 'Teste',
      email: 'teste@teste.com',
      senha: '123456',
      confirmarSenha: "123456"
    });


    component.onSubmit();


    expect(matSnackBarSpy.open).toHaveBeenCalledWith(errorResponse.error, 'Fechar', jasmine.objectContaining({
      duration: 3000,
      panelClass: ['errorsnackbar'],
      horizontalPosition: 'center',
      verticalPosition: 'top'
    }));


  });


})

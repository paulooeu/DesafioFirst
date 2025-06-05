import 'zone.js';  // Necessário para a execução de testes em Angular
import { getTestBed } from '@angular/core/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';

// Teste de inicialização
declare const require: any;
const context = require.context('./', true, /\.spec\.ts$/);
context.keys().map(context);

// Inicialização do Angular Testing
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting()
);

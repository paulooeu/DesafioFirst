module.exports = function(config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular-devkit/build-angular'],
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-coverage'),
      require('@angular-devkit/build-angular/plugins/karma')
    ],
    client: {
      jasmine: {
        // Abaixo são algumas configurações do Jasmine, como tempo limite e configuração do modo.
        timeoutInterval: 5000
      },
      clearContext: false // Para manter o log de execução do teste no console
    },
    jasmineHtmlReporter: {
      suppressAll: true // Previne a exibição de mensagens duplicadas
    },
    coverageReporter: {
      dir: require('path').join(__dirname, './coverage'),
      subdir: '.',
      reporters: [{ type: 'html' }, { type: 'lcov' }]
    },
    reporters: ['progress', 'kjhtml'],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['Chrome'], // Ou pode ser ChromeHeadless, dependendo de como você deseja rodar o navegador.
    singleRun: false,
    restartOnFileChange: true
  });
};

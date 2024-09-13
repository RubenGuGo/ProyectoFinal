describe('Home Page', () => {
  it('should load the home page', () => {
    cy.visit('/');
    cy.contains('Bienvenido a Apuestas Deportivas');
    cy.contains('Regístrate Ahora');
    cy.contains('Explora Eventos');
    cy.contains('Explora Tipos de Apuestas');
  });
});


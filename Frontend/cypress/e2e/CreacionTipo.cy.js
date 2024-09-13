describe('Tipo Apuesta List', () => {
  beforeEach(() => {
    cy.visit('/tipo-apuestas');
  });

  it('should load the tipo apuesta list', () => {
    cy.contains('Tipos de Apuestas');
  });

  it('should delete a tipo apuesta', () => {
    cy.intercept('DELETE', '/api/tipoapuestas/*').as('deleteTipoApuesta');
    cy.get('.delete-button').first().click();
    cy.get('.confirm-button').click();
    cy.wait('@deleteTipoApuesta').its('response.statusCode').should('eq', 200);
  });
});

describe('Tipo Apuesta Form', () => {
  it('should create a new tipo apuesta', () => {
    cy.visit('/crear-tipo-apuesta');
    cy.get('#nombre').type('Nuevo Tipo Apuesta');
    cy.get('#descripcion').type('Descripción del tipo de apuesta');
    cy.get('#maxima').type('100');
    cy.get('#minima').type('10');
    cy.get('#combinada').check();
    cy.get('#evento').select('Evento 1');
    cy.get('.btn-save').click();
    cy.url().should('include', '/tipo-apuestas');
  });

  it('should edit an existing tipo apuesta', () => {
    cy.visit('/editar-tipo-apuesta/1');
    cy.get('#nombre').clear().type('Tipo Apuesta Editado');
    cy.get('.btn-save').click();
    cy.url().should('include', '/tipo-apuestas');
  });
});
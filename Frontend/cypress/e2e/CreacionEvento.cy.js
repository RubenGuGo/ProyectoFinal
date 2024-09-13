describe('Evento List', () => {
  beforeEach(() => {
    cy.visit('/eventos');
  });

  it('should load the evento list', () => {
    cy.contains('Eventos');
  });

  it('should delete an evento', () => {
    cy.intercept('DELETE', '/api/eventos/*').as('deleteEvento');
    cy.get('.delete-button').first().click();
    cy.get('.confirm-button').click();
    cy.wait('@deleteEvento').its('response.statusCode').should('eq', 200);
  });
});

describe('Evento Form', () => {
  it('should create a new evento', () => {
    cy.visit('/crear-evento');
    cy.get('#nombre').type('Nuevo Evento');
    cy.get('#descripcion').type('Descripción del evento');
    cy.get('#pais').type('España');
    cy.get('#ciudad').type('Madrid');
    cy.get('#direccion').type('Calle Falsa 123');
    cy.get('#fecha').type('2023-12-31');
    cy.get('#horaInicio').type('10:00');
    cy.get('#horaFinal').type('12:00');
    cy.get('.btn-save').click();
    cy.url().should('include', '/eventos');
  });

  it('should edit an existing evento', () => {
    cy.visit('/editar-evento/1');
    cy.get('#nombre').clear().type('Evento Editado');
    cy.get('.btn-save').click();
    cy.url().should('include', '/eventos');
  });
});
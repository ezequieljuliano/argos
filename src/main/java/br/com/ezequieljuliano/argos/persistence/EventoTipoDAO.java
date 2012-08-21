package br.com.ezequieljuliano.argos.persistence;

import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class EventoTipoDAO extends JPACrud<EventoTipo, Long> {
	
	private static final long serialVersionUID = 1L;
	
}

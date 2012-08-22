package br.com.ezequieljuliano.argos.business;

import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.com.ezequieljuliano.argos.persistence.EventoTipoDAO;
import br.gov.frameworkdemoiselle.annotation.Startup;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@BusinessController
public class EventoTipoBC extends DelegateCrud<EventoTipo, Long, EventoTipoDAO> {

    private static final long serialVersionUID = 1L;
	
//	@Startup
//	@Transactional
//	public void load() {
//		if (findAll().isEmpty()) {
//			insert(new EventoTipo("Logon com Sucesso"));
//			insert(new EventoTipo("Alteração de Dados"));
//			insert(new EventoTipo("Logoff"));
//		}
//	}
    
}

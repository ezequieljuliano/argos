package br.com.ezequieljuliano.argos.business;

import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.com.ezequieljuliano.argos.persistence.EventoTipoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class EventoTipoBC extends DelegateCrud<EventoTipo, Long, EventoTipoDAO> {

    private static final long serialVersionUID = 1L;
    
}

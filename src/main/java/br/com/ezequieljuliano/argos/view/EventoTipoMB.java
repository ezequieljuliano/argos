package br.com.ezequieljuliano.argos.view;

import br.com.ezequieljuliano.argos.business.EventoTipoBC;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import java.util.List;
import javax.inject.Inject;

@ViewController
public class EventoTipoMB extends AbstractEditPageBean<EventoTipo, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EventoTipoBC eventoTipoBC;

	@Override
	@Transactional
	public String delete() {
		this.eventoTipoBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		this.eventoTipoBC.insert(getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		this.eventoTipoBC.update(getBean());
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.eventoTipoBC.load(getId()));
	}
        
        public List<EventoTipo> getList(){
            return eventoTipoBC.findAll();
        }
               
}

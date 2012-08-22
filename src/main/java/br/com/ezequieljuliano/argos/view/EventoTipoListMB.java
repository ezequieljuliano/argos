package br.com.ezequieljuliano.argos.view;

import br.com.ezequieljuliano.argos.business.EventoTipoBC;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

@ViewController
@NextView("/evento_tipo_edit.xhtml")
@PreviousView("/evento_tipo_list.xhtml")
public class EventoTipoListMB extends AbstractListPageBean<EventoTipo, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EventoTipoBC bc;

	@Override
	protected List<EventoTipo> handleResultList() {
		return this.bc.findAll();
	}

	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);

			if (delete) {
				bc.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}

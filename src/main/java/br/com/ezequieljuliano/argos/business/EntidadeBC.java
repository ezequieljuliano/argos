/*
 * Copyright 2012 Ezequiel Juliano Müller.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.ezequieljuliano.argos.business;

import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.domain.Situacao;
import br.com.ezequieljuliano.argos.persistence.EntidadeDAO;
import br.com.ezequieljuliano.argos.util.Utils;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class EntidadeBC extends DelegateCrud<Entidade, String, EntidadeDAO> {

    private static final long serialVersionUID = 1L;
    
    @Inject
    EntidadeDAO dao;

    public void saveOrUpdate(Entidade entidade) {
        if (entidade.getId() == null) {
            entidade.setId(Utils.getUniqueId());
            insert(entidade);
        } else {
            update(entidade);
        }
    }

    public void inativar(Entidade entidade) {
        if (entidade.isAtivo()) {
            entidade.setSituacao(Situacao.inativo);
            saveOrUpdate(entidade);
        }
    }

    public void ativar(Entidade entidade) {
        if (entidade.isInativo()) {
            entidade.setSituacao(Situacao.ativo);
            saveOrUpdate(entidade);
        }
    }
    
}

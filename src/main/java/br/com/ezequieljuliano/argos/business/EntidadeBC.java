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
import br.com.ezequieljuliano.argos.exception.ValidationException;
import br.com.ezequieljuliano.argos.persistence.EntidadeDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import java.util.List;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class EntidadeBC extends GenericBC<Entidade, String, EntidadeDAO> {

    private static final long serialVersionUID = 1L;

    public void saveOrUpdate(Entidade entidade) throws ValidationException {
        //Verifica se código já não foi cadastrado
        Entidade objCodigo = findByCodigo(entidade.getCodigo());
        if (objCodigo != null && !objCodigo.getId().equals(entidade.getId())) {
            throw new ValidationException("Código já cadastrado!");
        }
        //Verifica se o cadastro nacional já não ta cadastrado
        Entidade objCadNacional = findByCadastroNacional(entidade.getCadastroNacional());
        if (objCadNacional != null && !objCadNacional.getId().equals(entidade.getId())) {
            throw new ValidationException("Cadastro Nacional já cadastrado!");
        }
        if (entidade.getId() == null) {
            getDAO().insert(entidade);
        } else {
            getDAO().save(entidade);
        }
    }

    public void inativar(Entidade entidade) throws ValidationException {
        if (entidade.isAtivo()) {
            entidade.setSituacao(Situacao.inativo);
            saveOrUpdate(entidade);
        }
    }

    public void ativar(Entidade entidade) throws ValidationException {
        if (entidade.isInativo()) {
            entidade.setSituacao(Situacao.ativo);
            saveOrUpdate(entidade);
        }
    }

    public Entidade findByCodigo(int codigo) {
        return getDAO().findByCodigo(codigo);
    }

    public Entidade findByCadastroNacional(String cadastroNacional) {
        return getDAO().findByCadastroNacional(cadastroNacional);
    }

    public List<Entidade> findByNome(String nome) {
        return getDAO().findByNome(nome);
    }
}

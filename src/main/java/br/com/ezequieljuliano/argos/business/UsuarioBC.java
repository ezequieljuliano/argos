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

import br.com.ezequieljuliano.argos.domain.Situacao;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.persistence.UsuarioDAO;
import br.com.ezequieljuliano.argos.util.Utils;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import javax.inject.Inject;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, String, UsuarioDAO> {

    private static final long serialVersionUID = 1L;
    
    @Inject
    UsuarioDAO dao;

    public void saveOrUpdate(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(Utils.getUniqueId());
            insert(usuario);
        } else {
            update(usuario);
        }
    }

    public void inativar(Usuario usuario) {
        if (usuario.isAtivo()) {
            usuario.setSituacao(Situacao.inativo);
            saveOrUpdate(usuario);
        }
    }

    public void ativar(Usuario usuario) {
        if (usuario.isInativo()) {
            usuario.setSituacao(Situacao.ativo);
            saveOrUpdate(usuario);
        }
    }
}

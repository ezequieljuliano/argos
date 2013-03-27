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
import br.com.ezequieljuliano.argos.domain.EventoNivel;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.com.ezequieljuliano.argos.domain.Situacao;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.domain.UsuarioPerfil;
import br.com.ezequieljuliano.argos.exception.ValidationException;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class DadosPadraoBC {

    @Inject
    private EventoTipoBC eventoTipoBC;
    @Inject
    private EventoNivelBC eventoNivelBC;
    @Inject
    private EntidadeBC entidadeBC;
    @Inject
    private UsuarioBC usuarioBC;
    @Inject
    private EventoBC eventoBC;

    private void insereEventoTipo() throws ValidationException {
        if (eventoTipoBC.findByCodigo(1) == null) {
            EventoTipo eTipo = new EventoTipo();
            eTipo.setCodigo(1);
            eTipo.setDescricao("Autenticação");
            eTipo.setSituacao(Situacao.ativo);
            eventoTipoBC.saveOrUpdate(eTipo);
        }
        if (eventoTipoBC.findByCodigo(2) == null) {
            EventoTipo eTipo = new EventoTipo();
            eTipo.setCodigo(2);
            eTipo.setDescricao("Autorização");
            eTipo.setSituacao(Situacao.ativo);
            eventoTipoBC.saveOrUpdate(eTipo);
        }
        if (eventoTipoBC.findByCodigo(3) == null) {
            EventoTipo eTipo = new EventoTipo();
            eTipo.setCodigo(3);
            eTipo.setDescricao("Alteração");
            eTipo.setSituacao(Situacao.ativo);
            eventoTipoBC.saveOrUpdate(eTipo);
        }
        if (eventoTipoBC.findByCodigo(4) == null) {
            EventoTipo eTipo = new EventoTipo();
            eTipo.setCodigo(4);
            eTipo.setDescricao("Disponibilidade");
            eTipo.setSituacao(Situacao.ativo);
            eventoTipoBC.saveOrUpdate(eTipo);
        }
        if (eventoTipoBC.findByCodigo(5) == null) {
            EventoTipo eTipo = new EventoTipo();
            eTipo.setCodigo(5);
            eTipo.setDescricao("Recurso");
            eTipo.setSituacao(Situacao.ativo);
            eventoTipoBC.saveOrUpdate(eTipo);
        }
        if (eventoTipoBC.findByCodigo(6) == null) {
            EventoTipo eTipo = new EventoTipo();
            eTipo.setCodigo(6);
            eTipo.setDescricao("Ameaça");
            eTipo.setSituacao(Situacao.ativo);
            eventoTipoBC.saveOrUpdate(eTipo);
        }
        if (eventoTipoBC.findByCodigo(7) == null) {
            EventoTipo eTipo = new EventoTipo();
            eTipo.setCodigo(7);
            eTipo.setDescricao("Exclusão");
            eTipo.setSituacao(Situacao.ativo);
            eventoTipoBC.saveOrUpdate(eTipo);
        }
        if (eventoTipoBC.findByCodigo(8) == null) {
            EventoTipo eTipo = new EventoTipo();
            eTipo.setCodigo(8);
            eTipo.setDescricao("Inclusão");
            eTipo.setSituacao(Situacao.ativo);
            eventoTipoBC.saveOrUpdate(eTipo);
        }
        if (eventoTipoBC.findByCodigo(9) == null) {
            EventoTipo eTipo = new EventoTipo();
            eTipo.setCodigo(9);
            eTipo.setDescricao("Consulta");
            eTipo.setSituacao(Situacao.ativo);
            eventoTipoBC.saveOrUpdate(eTipo);
        }
        if (eventoTipoBC.findByCodigo(10) == null) {
            EventoTipo eTipo = new EventoTipo();
            eTipo.setCodigo(10);
            eTipo.setDescricao("Validação");
            eTipo.setSituacao(Situacao.ativo);
            eventoTipoBC.saveOrUpdate(eTipo);
        }
    }

    private void insereEventoNivel() throws ValidationException {
        if (eventoNivelBC.findByCodigo(1) == null) {
            EventoNivel eNivel = new EventoNivel();
            eNivel.setCodigo(1);
            eNivel.setDescricao("Informação");
            eNivel.setSituacao(Situacao.ativo);
            eNivel.setAlerta(Boolean.TRUE);
            eventoNivelBC.saveOrUpdate(eNivel);
        }
        if (eventoNivelBC.findByCodigo(2) == null) {
            EventoNivel eNivel = new EventoNivel();
            eNivel.setCodigo(2);
            eNivel.setDescricao("Alerta");
            eNivel.setSituacao(Situacao.ativo);
            eNivel.setAlerta(Boolean.TRUE);
            eventoNivelBC.saveOrUpdate(eNivel);
        }
        if (eventoNivelBC.findByCodigo(3) == null) {
            EventoNivel eNivel = new EventoNivel();
            eNivel.setCodigo(3);
            eNivel.setDescricao("Erro");
            eNivel.setSituacao(Situacao.ativo);
            eNivel.setAlerta(Boolean.TRUE);
            eventoNivelBC.saveOrUpdate(eNivel);
        }
        if (eventoNivelBC.findByCodigo(4) == null) {
            EventoNivel eNivel = new EventoNivel();
            eNivel.setCodigo(4);
            eNivel.setDescricao("Debug");
            eNivel.setSituacao(Situacao.ativo);
            eNivel.setAlerta(Boolean.TRUE);
            eventoNivelBC.saveOrUpdate(eNivel);
        }
        if (eventoNivelBC.findByCodigo(5) == null) {
            EventoNivel eNivel = new EventoNivel();
            eNivel.setCodigo(5);
            eNivel.setDescricao("Fatal");
            eNivel.setSituacao(Situacao.ativo);
            eNivel.setAlerta(Boolean.TRUE);
            eventoNivelBC.saveOrUpdate(eNivel);
        }
        if (eventoNivelBC.findByCodigo(6) == null) {
            EventoNivel eNivel = new EventoNivel();
            eNivel.setCodigo(6);
            eNivel.setDescricao("Exceção");
            eNivel.setSituacao(Situacao.ativo);
            eNivel.setAlerta(Boolean.TRUE);
            eventoNivelBC.saveOrUpdate(eNivel);
        }
    }

    private void insereEntidade() throws ValidationException {
        if (entidadeBC.findByCodigo(1) == null) {
            Entidade entidade = new Entidade();
            entidade.setCodigo(1);
            entidade.setCadastroNacional("058");
            entidade.setNome("Ezequiel Juliano Muller");
            entidade.setSituacao(Situacao.ativo);
            entidadeBC.saveOrUpdate(entidade);
        }
        if (entidadeBC.findByCodigo(2) == null) {
            Entidade entidade = new Entidade();
            entidade.setCodigo(2);
            entidade.setCadastroNacional("789");
            entidade.setNome("Microsys");
            entidade.setSituacao(Situacao.ativo);
            entidadeBC.saveOrUpdate(entidade);
        }
    }

    private void insereUsuario() throws ValidationException {
        if (usuarioBC.findByUserName("adm") == null) {
            Usuario user = new Usuario();
            user.setUserName("adm");
            user.setEntidade(entidadeBC.findByCodigo(2));
            user.setEmail("adm@adm.com.br");
            user.setPassword("123");
            user.setPerfil(UsuarioPerfil.administrador);
            user.setSituacao(Situacao.ativo);
            usuarioBC.saveOrUpdate(user);
        }

        if (usuarioBC.findByUserName("normal") == null) {
            Usuario user = new Usuario();
            user.setUserName("normal");
            user.setEmail("normal@normal.com.br");
            user.setPassword("123");
            user.setEntidade(entidadeBC.findByCodigo(2));
            user.setPerfil(UsuarioPerfil.normal);
            user.setSituacao(Situacao.ativo);
            usuarioBC.saveOrUpdate(user);
        }
    }

    public void InsereDados() {
        try {
            insereEventoNivel();
            insereEventoTipo();
            insereEntidade();
            insereUsuario();
        } catch (ValidationException ex) {
            Logger.getLogger(DadosPadraoBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

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
public class CargaDeDadosBC {

    @Inject
    private EventoTipoBC eventoTipoBC;
    
    @Inject
    private EventoNivelBC eventoNivelBC;
    
    @Inject
    private EntidadeBC entidadeBC;
    
    @Inject
    private UsuarioBC usuarioBC;
    
    private String[] eventoTipos = new String[]{"Autenticação", "Autorização", "Alteração",
        "Disponibilidade", "Recurso", "Ameaça", "Exclusão", "Inclusão", "Consulta", "Validação", "Erro"};
    private String[] eventoNiveis = new String[]{"Informação", "Alerta", "Erro", "Debug", "Fatal", "Exceção"};

    private void saveEventoTipos() {
        for (int i = 0; i < eventoTipos.length; i++) {
            if (eventoTipoBC.findByCodigo(i + 1) == null) {
                try {
                    EventoTipo eventoTipo = new EventoTipo();
                    eventoTipo.setCodigo(i + 1);
                    eventoTipo.setDescricao(eventoTipos[i]);
                    eventoTipo.setSituacao(Situacao.ativo);
                    eventoTipoBC.saveOrUpdate(eventoTipo);
                } catch (ValidationException ex) {
                    Logger.getLogger(CargaDeDadosBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void saveEventoNiveis() {
        for (int i = 0; i < eventoNiveis.length; i++) {
            if (eventoNivelBC.findByCodigo(i + 1) == null) {
                try {
                    EventoNivel eventoNivel = new EventoNivel();
                    eventoNivel.setCodigo(i + 1);
                    eventoNivel.setDescricao(eventoNiveis[i]);
                    eventoNivel.setSituacao(Situacao.ativo);
                    eventoNivelBC.saveOrUpdate(eventoNivel);
                } catch (ValidationException ex) {
                    Logger.getLogger(CargaDeDadosBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void saveEntidades() {
        if (entidadeBC.findByCodigo(1) == null) {
            try {
                Entidade entidade = new Entidade();
                entidade.setCodigo(1);
                entidade.setCadastroNacional("99.999.999/9999-99");
                entidade.setNome("Ezequiel Juliano Müller");
                entidade.setSituacao(Situacao.ativo);
                entidadeBC.saveOrUpdate(entidade);
            } catch (ValidationException ex) {
                Logger.getLogger(CargaDeDadosBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void saveUsuarios() {
        if (usuarioBC.findByUserName("admin") == null) {
            try {
                Usuario usuario = new Usuario();
                usuario.setUserName("admin");
                usuario.setPassword("admin");
                usuario.setEmail("ezequieljuliano@gmail.com");
                usuario.setEntidade(entidadeBC.findByCodigo(1));
                usuario.setPerfil(UsuarioPerfil.administrador);
                usuario.setSituacao(Situacao.ativo);
                usuarioBC.saveOrUpdate(usuario);
            } catch (ValidationException ex) {
                Logger.getLogger(CargaDeDadosBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void gerarCargaDeDados() {
        saveEventoTipos();
        saveEventoNiveis();
        saveEntidades();
        saveUsuarios();
    }
    
}

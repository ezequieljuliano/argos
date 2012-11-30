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
import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.domain.EventoCampoCustomizado;
import br.com.ezequieljuliano.argos.domain.EventoNivel;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.com.ezequieljuliano.argos.domain.EventoValorCustomizado;
import br.com.ezequieljuliano.argos.domain.Situacao;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.domain.UsuarioPerfil;
import br.com.ezequieljuliano.argos.exception.ValidationException;
import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import java.sql.Timestamp;
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
    @Inject
    private EventoCampoCustomizadoBC eventoCampoCustomBC;

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
            user.setEntidade(entidadeBC.findByCodigo(1));
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

    private void insereLogs() throws ValidationException, Exception {
        EventoCampoCustomizado ecc = new EventoCampoCustomizado();
        ecc.setDescricao("AAA");
        ecc.setEntidade(entidadeBC.findByCodigo(1));
        eventoCampoCustomBC.saveOrUpdate(ecc);
        
        EventoCampoCustomizado ecc2 = new EventoCampoCustomizado();
        ecc2.setDescricao("BBB");
        ecc2.setEntidade(entidadeBC.findByCodigo(1));
        eventoCampoCustomBC.saveOrUpdate(ecc2);
        
        EventoCampoCustomizado ecc3 = new EventoCampoCustomizado();
        ecc3.setDescricao("CCC");
        ecc3.setEntidade(entidadeBC.findByCodigo(2));
        eventoCampoCustomBC.saveOrUpdate(ecc3);
        
        Evento evento = new Evento();
        evento.setHostName("EZEQUIEL-NB");
        evento.setMensagem("Realizada uma autenticação no sistema MsysComercial");
        evento.setHostIp("192.168.0.10");
        evento.setFonte("MsysComercial");
        evento.setNome("Autenticação no sistema MsysComercial");
        evento.setOcorrenciaDtHr(new Timestamp(new java.util.Date().getTime()));
        evento.setPalavrasChave("AUTENTICAÇÃO;MSYSCOMERCIAL;SISTEMA");
        evento.setHostUser("Ezequiel");
        evento.setEntidade(entidadeBC.findByCodigo(1));
        evento.setEventoNivel(eventoNivelBC.findByCodigo(1));
        evento.setEventoTipo(eventoTipoBC.findByCodigo(1));
              
        EventoValorCustomizado eveVlCustom = new EventoValorCustomizado();
        eveVlCustom.setEventoCampoCustomizado(ecc);
        eveVlCustom.setValor("05845333903");
        evento.addValorCustomizado(eveVlCustom);
        
        EventoValorCustomizado eveVlCustom2 = new EventoValorCustomizado();
        eveVlCustom2.setEventoCampoCustomizado(ecc2);
        eveVlCustom2.setValor("TesteSetor");
        evento.addValorCustomizado(eveVlCustom2);
          
        eventoBC.saveOrUpdate(evento, usuarioBC.findByUserName("adm"));

        Evento evento2 = new Evento();
        evento2.setHostName("LUIZ-NB");
        evento2.setMensagem("Alteração de dados do cliente 50");
        evento2.setHostIp("192.168.0.66");
        evento2.setFonte("Cadastro de Clientes");
        evento2.setNome("Alteração de Clientes");
        evento2.setOcorrenciaDtHr(new Timestamp(new java.util.Date().getTime()));
        evento2.setPalavrasChave("ALTERAÇÃO;CLIENTE;SISTEMA");
        evento2.setHostUser("Luiz");
        evento2.setEntidade(entidadeBC.findByCodigo(1));
        evento2.setEventoNivel(eventoNivelBC.findByCodigo(2));
        evento2.setEventoTipo(eventoTipoBC.findByCodigo(3));
        
//        EventoValorCustomizado eveVlCustom2 = new EventoValorCustomizado();
//        eveVlCustom2.setEvento(evento2);
//        eveVlCustom2.setEventoCampoCustomizado(evc2);
//        eveVlCustom2.setValor("TesteSetor");
//        evento2.getEventoValorCustomizadoList().add(eveVlCustom2);
//        
//        EventoValorCustomizado eveVlCustom3 = new EventoValorCustomizado();
//        eveVlCustom3.setEvento(evento2);
//        eveVlCustom3.setEventoCampoCustomizado(evc);
//        eveVlCustom3.setValor("544654");
//        evento2.getEventoValorCustomizadoList().add(eveVlCustom3);
        
        eventoBC.saveOrUpdate(evento2, usuarioBC.findByUserName("adm"));

        Evento evento3 = new Evento();
        evento3.setHostName("EVANDRO-NB");
        evento3.setMensagem("Exclusão de cliente 120");
        evento3.setHostIp("192.168.0.38");
        evento3.setFonte("Cadastro de Clientes");
        evento3.setNome("Exclusão de Clientes");
        evento3.setOcorrenciaDtHr(new Timestamp(new java.util.Date().getTime()));
        evento3.setPalavrasChave("EXCLUSÃO;CLIENTE;SISTEMA");
        evento3.setHostUser("Evandro");
        evento3.setEntidade(entidadeBC.findByCodigo(2));
        evento3.setEventoNivel(eventoNivelBC.findByCodigo(3));
        evento3.setEventoTipo(eventoTipoBC.findByCodigo(7));
        eventoBC.saveOrUpdate(evento3, usuarioBC.findByUserName("normal"));

        Evento evento4 = new Evento();
        evento4.setHostName("LUCIANO-PC");
        evento4.setMensagem("Exclusão de pedido de venda 600");
        evento4.setHostIp("192.168.0.44");
        evento4.setFonte("Pedidos de Vendas");
        evento4.setNome("Exclusão de Pedidos de Vendas");
        evento4.setOcorrenciaDtHr(new Timestamp(new java.util.Date().getTime()));
        evento4.setPalavrasChave("EXCLUSÃO;PEDIDO DE VENDA;SISTEMA");
        evento4.setHostUser("Luciano");
        evento4.setEntidade(entidadeBC.findByCodigo(2));
        evento4.setEventoNivel(eventoNivelBC.findByCodigo(5));
        evento4.setEventoTipo(eventoTipoBC.findByCodigo(7));
        eventoBC.saveOrUpdate(evento4, usuarioBC.findByUserName("normal"));
    }

        
    @Startup
    public void InsereDados() throws ValidationException, Exception {
        insereEventoNivel();
        insereEventoTipo();
        insereEntidade();
        insereUsuario();
        insereLogs();
    }
}

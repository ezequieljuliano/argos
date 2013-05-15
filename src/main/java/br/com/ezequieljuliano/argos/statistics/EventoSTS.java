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
package br.com.ezequieljuliano.argos.statistics;

import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.util.Data;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public class EventoSTS {

    private List<Evento> eventos = new ArrayList<Evento>();
    private Integer sizeExib = 10;

    public EventoSTS(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<EventoNivelObjSTS> getProcessNiveis() {
        List<EventoNivelObjSTS> list = new ArrayList<EventoNivelObjSTS>();
        for (Evento evento : this.eventos) {
            String descNivel = evento.getEventoNivel().getDescricao();
            EventoNivelObjSTS nivel = new EventoNivelObjSTS(descNivel);
            if (list.contains(nivel)) {
                nivel = list.get(list.indexOf(nivel));
                nivel.incrementar();
            } else {
                list.add(nivel);
            }
        }
        return list;
    }

    public List<EventoTipoObjSTS> getProcessTipos() {
        List<EventoTipoObjSTS> list = new ArrayList<EventoTipoObjSTS>();
        for (Evento evento : this.eventos) {
            String descTipo = evento.getEventoTipo().getDescricao();
            EventoTipoObjSTS tipo = new EventoTipoObjSTS(descTipo);
            if (list.contains(tipo)) {
                tipo = list.get(list.indexOf(tipo));
                tipo.incrementar();
            } else {
                list.add(tipo);
            }
        }
        return list;
    }

    public List<EventoEvolucaoObjSTS> getProcessEvolucao() {
        List<EventoEvolucaoObjSTS> list = new ArrayList<EventoEvolucaoObjSTS>();
        for (Evento evento : this.eventos) {
            String data = Data.dateToString(evento.getOcorrenciaDtHr());
            EventoEvolucaoObjSTS evolucao = new EventoEvolucaoObjSTS(data);
            if (list.contains(evolucao)) {
                evolucao = list.get(list.indexOf(evolucao));
                evolucao.incrementar();
            } else {
                list.add(evolucao);
            }
        }
        return list;
    }

    public List<EventoHostObjSTS> getProcessHosts() {
        List<EventoHostObjSTS> list = new ArrayList<EventoHostObjSTS>();
        for (Evento evento : this.eventos) {
            String descHost = evento.getHostIp();
            EventoHostObjSTS host = new EventoHostObjSTS(descHost);
            if (list.size() < sizeExib) {
                if (list.contains(host)) {
                    host = list.get(list.indexOf(host));
                    host.incrementar();
                } else {
                    list.add(host);
                }
            }
        }
        return list;
    }

    public List<EventoFonteObjSTS> getProcessFontes() {
        List<EventoFonteObjSTS> list = new ArrayList<EventoFonteObjSTS>();
        for (Evento evento : this.eventos) {
            String descFonte = evento.getFonte();
            EventoFonteObjSTS fonte = new EventoFonteObjSTS(descFonte);
            if (list.size() < sizeExib) {
                if (list.contains(fonte)) {
                    fonte = list.get(list.indexOf(fonte));
                    fonte.incrementar();
                } else {
                    list.add(fonte);
                }
            }
        }
        return list;
    }

    public List<EventoSysUserObjSTS> getProcessSysUsers() {
        List<EventoSysUserObjSTS> list = new ArrayList<EventoSysUserObjSTS>();
        for (Evento evento : this.eventos) {
            String descUser = evento.getSysUser();
            EventoSysUserObjSTS user = new EventoSysUserObjSTS(descUser);
            if (list.size() < sizeExib) {
                if (list.contains(user)) {
                    user = list.get(list.indexOf(user));
                    user.incrementar();
                } else {
                    list.add(user);
                }
            }
        }
        return list;
    }

    public List<EventoObjSTS> getProcessEventos() {
        List<EventoObjSTS> list = new ArrayList<EventoObjSTS>();
        for (Evento evento : this.eventos) {
            EventoObjSTS obj = new EventoObjSTS();
            obj.setId(evento.getId());
            obj.setEntidade(evento.getEntidade().getNome());
            obj.setFonte(evento.getFonte());
            obj.setHostIp(evento.getHostIp());
            obj.setHostName(evento.getHostName());
            obj.setNome(evento.getNome());
            obj.setOcorrenciaDtHr(Data.dateToString(evento.getOcorrenciaDtHr()));
            obj.setSysUser(evento.getSysUser());
            if (list.size() < sizeExib) {
                list.add(obj);
            }
        }
        return list;
    }
}

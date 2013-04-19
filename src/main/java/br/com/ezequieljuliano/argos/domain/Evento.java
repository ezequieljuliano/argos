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
package br.com.ezequieljuliano.argos.domain;

import br.com.ezequieljuliano.argos.util.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Document
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @Indexed
    private Date ocorrenciaDtHr;
    
    @Indexed
    private String hostName;
    
    @Indexed
    private String hostIp;
    
    @Indexed
    private String hostUser;
    
    @Indexed
    private String hostMac;
    
    @Indexed
    private String sysUser;
    
    private String mensagem;
    
    private String fonte;
    
    @Indexed
    private String nome;
    
    private String palavrasChave;
    
    @DBRef
    private Entidade entidade;
    
    @DBRef
    private EventoNivel eventoNivel;
    
    @DBRef
    private EventoTipo eventoTipo;

    public Evento() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getHostUser() {
        return hostUser;
    }

    public void setHostUser(String hostUser) {
        this.hostUser = hostUser;
    }

    public String getHostMac() {
        return hostMac;
    }

    public void setHostMac(String hostMac) {
        this.hostMac = hostMac;
    }

    public String getSysUser() {
        return sysUser;
    }

    public void setSysUser(String sysUser) {
        this.sysUser = sysUser;
    }
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getOcorrenciaDtHr() {
        return ocorrenciaDtHr;
    }

    public void setOcorrenciaDtHr(Date ocorrenciaDtHr) {
        this.ocorrenciaDtHr = ocorrenciaDtHr;
    }

    public String getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public EventoNivel getEventoNivel() {
        return eventoNivel;
    }

    public void setEventoNivel(EventoNivel eventoNivel) {
        this.eventoNivel = eventoNivel;
    }

    public EventoTipo getEventoTipo() {
        return eventoTipo;
    }

    public void setEventoTipo(EventoTipo eventoTipo) {
        this.eventoTipo = eventoTipo;
    }

    @Override
    public String toString() { 
        String newLineCharacter = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(newLineCharacter);
        stringBuilder.append("Título: ").append(nome).append(newLineCharacter);
        stringBuilder.append("Data: ").append(Data.dateToString(ocorrenciaDtHr)).append(newLineCharacter);
        stringBuilder.append("Hora: ").append(Data.timeToString(ocorrenciaDtHr)).append(newLineCharacter);
        stringBuilder.append("Usuário do Host: ").append(hostUser).append(newLineCharacter);
        stringBuilder.append("IP do Host: ").append(hostIp).append(newLineCharacter);
        stringBuilder.append("Nome do Host: ").append(hostName).append(newLineCharacter);
        stringBuilder.append("Fonte: ").append(fonte).append(newLineCharacter);
        stringBuilder.append("Tipo: ").append(eventoTipo.getDescricao()).append(newLineCharacter);
        stringBuilder.append("Nível: ").append(eventoNivel.getDescricao()).append(newLineCharacter);
        stringBuilder.append("Palavras-Chave: ").append(palavrasChave).append(newLineCharacter);
        stringBuilder.append("Entidade: ").append(entidade.getCadastroNacional()).append(" - ").append(entidade.getNome()).append(newLineCharacter);
        stringBuilder.append("Mensagem: ").append(mensagem).append(newLineCharacter);
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evento other = (Evento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }
}

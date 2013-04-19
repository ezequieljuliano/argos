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
package br.com.ezequieljuliano.argos.service.to;

import br.com.ezequieljuliano.argos.util.Data;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@XmlRootElement
public class EventoLogTO {

    private String hostName;
    private String hostIp;
    private String hostUser;
    private String hostMac;
    private String sysUser;
    private String cadastroNacional;
    private String mensagem;
    private String fonte;
    private String nome;
    private String ocorrenciaDtHr;
    private String palavrasChave;
    private Integer eventoNivelCodigo;
    private Integer eventoTipoCodigo;

    public EventoLogTO() {
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
    
    public String getCadastroNacional() {
        return cadastroNacional;
    }

    public void setCadastroNacional(String cadastroNacional) {
        this.cadastroNacional = cadastroNacional;
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

    public String getOcorrenciaDtHr() {
        return ocorrenciaDtHr;
    }

    public void setOcorrenciaDtHr(String ocorrenciaDtHr) {
        this.ocorrenciaDtHr = ocorrenciaDtHr;
    }

    public String getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public Integer getEventoNivelCodigo() {
        return eventoNivelCodigo;
    }

    public void setEventoNivelCodigo(Integer eventoNivelCodigo) {
        this.eventoNivelCodigo = eventoNivelCodigo;
    }

    public Integer getEventoTipoCodigo() {
        return eventoTipoCodigo;
    }

    public void setEventoTipoCodigo(Integer eventoTipoCodigo) {
        this.eventoTipoCodigo = eventoTipoCodigo;
    }

    public Date getOcorrenciaDtHrAsDate() {
        return Data.stringToTimeStamp(this.ocorrenciaDtHr);
    }
}

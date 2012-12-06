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
package br.com.ezequieljuliano.argos.constant;

import br.com.ezequieljuliano.argos.config.Configuracoes;
import org.apache.lucene.util.Version;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public class Constantes {

    //LUCENE - ÍNDICES DE DIRETÓRIOS
    private static final String LUCENE_DIRECTORY_INDEX = "/INDEX/";
    private static final String LUCENE_ROOT_FOLDER = "/ARGOS-LUCENE/";
    
    //LUCENE - ÍNDICES DE DADOS
    //EVENTO NÍVEL
    public static final String INDICE_EVENTONIVEL_ID = "INDICE_EVENTONIVEL_ID";
    public static final String INDICE_EVENTONIVEL_CODIGO = "INDICE_EVENTONIVEL_CODIGO";
    public static final String INDICE_EVENTONIVEL_DESCRICAO = "INDICE_EVENTONIVEL_DESCRICAO";
    public static final String INDICE_EVENTONIVEL_SITUACAOCODIGO = "INDICE_EVENTONIVEL_SITUACAOCODIGO";
    public static final String INDICE_EVENTONIVEL_SITUACAO = "INDICE_EVENTONIVEL_SITUCAO";
    public static final String INDICE_EVENTONIVEL_ALERTA = "INDICE_EVENTONIVEL_ALERTA";
    public static final String INDICE_EVENTONIVEL_TUDO = "INDICE_EVENTONIVEL_TUDO";
    //EVENTO TIPO
    public static final String INDICE_EVENTOTIPO_ID = "INDICE_EVENTOTIPO_ID";
    public static final String INDICE_EVENTOTIPO_CODIGO = "INDICE_EVENTOTIPO_CODIGO";
    public static final String INDICE_EVENTOTIPO_DESCRICAO = "INDICE_EVENTOTIPO_DESCRICAO";
    public static final String INDICE_EVENTOTIPO_SITUACAOCODIGO = "INDICE_EVENTOTIPO_SITUACAOCODIGO";
    public static final String INDICE_EVENTOTIPO_SITUACAO = "INDICE_EVENTOTIPO_SITUACAO";
    public static final String INDICE_EVENTOTIPO_TUDO = "INDICE_EVENTOTIPO_TUDO";
    //EVENTO CAMPO CUSTOMIZADO
    public static final String INDICE_EVENTOCAMPOCUSTOMIZADO_ID = "INDICE_EVENTOCAMPOCUSTOMIZADO_ID";
    public static final String INDICE_EVENTOCAMPOCUSTOMIZADO_DESCRICAO = "INDICE_EVENTOCAMPOCUSTOMIZADO_DESCRICAO";
    public static final String INDICE_EVENTOCAMPOCUSTOMIZADO_ENTIDADEID = "INDICE_EVENTOCAMPOCUSTOMIZADO_ENTIDADEID";
    public static final String INDICE_EVENTOCAMPOCUSTOMIZADO_ENTIDADENOME = "INDICE_EVENTOCAMPOCUSTOMIZADO_ENTIDADENOME";
    public static final String INDICE_EVENTOCAMPOCUSTOMIZADO_TUDO = "INDICE_EVENTOCAMPOCUSTOMIZADO_TUDO";
    //ENTIDADE 
    public static final String INDICE_ENTIDADE_ID = "INDICE_ENTIDADE_ID";
    public static final String INDICE_ENTIDADE_CODIGO = "INDICE_ENTIDADE_CODIGO";
    public static final String INDICE_ENTIDADE_CADASTRONACIONAL = "INDICE_ENTIDADE_CADASTRONACIONAL";
    public static final String INDICE_ENTIDADE_NOME = "INDICE_ENTIDADE_NOME";
    public static final String INDICE_ENTIDADE_SITUACAOCODIGO = "INDICE_ENTIDADE_SITUACAOCODIGO";
    public static final String INDICE_ENTIDADE_SITUACAO = "INDICE_ENTIDADE_SITUACAO";
    public static final String INDICE_ENTIDADE_TUDO = "INDICE_ENTIDADE_TUDO";
    //USUARIO
    public static final String INDICE_USUARIO_ID = "INDICE_USUARIO_ID";
    public static final String INDICE_USUARIO_USERNAME = "INDICE_USUARIO_USERNAME";
    public static final String INDICE_USUARIO_PASSWORD = "INDICE_USUARIO_PASSWORD";
    public static final String INDICE_USUARIO_PERFILCODIGO = "INDICE_USUARIO_PERFILCODIGO";
    public static final String INDICE_USUARIO_PERFIL = "INDICE_USUARIO_PERFIL";
    public static final String INDICE_USUARIO_SITUACAOCODIGO = "INDICE_USUARIO_SITUACAOCODIGO";
    public static final String INDICE_USUARIO_SITUACAO = "INDICE_USUARIO_SITUACAO";
    public static final String INDICE_USUARIO_EMAIL = "INDICE_USUARIO_EMAIL";
    public static final String INDICE_USUARIO_APIKEY = "INDICE_USUARIO_APIKEY";
    public static final String INDICE_USUARIO_ENTIDADEID = "INDICE_USUARIO_ENTIDADEID";
    public static final String INDICE_USUARIO_ENTIDADENOME = "INDICE_USUARIO_ENTIDADENOME";
    public static final String INDICE_USUARIO_TUDO = "INDICE_USUARIO_TUDO";
    //USUARIO EVENTO
    public static final String INDICE_USUARIOEVENTO_ID = "INDICE_USUARIOEVENTO_ID";
    public static final String INDICE_USUARIOEVENTO_DATAHORA = "INDICE_USUARIOEVENTO_DATAHORA";
    public static final String INDICE_USUARIOEVENTO_EVENTOID = "INDICE_USUARIOEVENTO_EVENTOID";
    public static final String INDICE_USUARIOEVENTO_EVENTONOME = "INDICE_USUARIOEVENTO_EVENTONOME";
    public static final String INDICE_USUARIOEVENTO_USUSARIOID = "INDICE_USUARIOEVENTO_USUSARIOID";
    public static final String INDICE_USUARIOEVENTO_USUSARIOUSERNAME = "INDICE_USUARIOEVENTO_USUSARIOUSERNAME";
    public static final String INDICE_USUARIOEVENTO_TUDO = "INDICE_USUARIOEVENTO_TUDO";  
    //EVENTO
    public static final String INDICE_EVENTO_ID = "INDICE_EVENTO_ID";
    public static final String INDICE_EVENTO_MENSAGEM = "INDICE_EVENTO_MENSAGEM";
    public static final String INDICE_EVENTO_HOSTNAME = "INDICE_EVENTO_HOSTNAME";
    public static final String INDICE_EVENTO_HOSTUSER = "INDICE_EVENTO_HOSTUSER";
    public static final String INDICE_EVENTO_HOSTIP = "INDICE_EVENTO_HOSTIP";
    public static final String INDICE_EVENTO_FONTE = "INDICE_EVENTO_FONTE";
    public static final String INDICE_EVENTO_NOME = "INDICE_EVENTO_NOME";
    public static final String INDICE_EVENTO_OCORRENCIADTHR = "INDICE_EVENTO_OCORRENCIADTHR";
    public static final String INDICE_EVENTO_PALAVRASCHAVE = "INDICE_EVENTO_PALAVRASCHAVE";
    public static final String INDICE_EVENTO_ENTIDADEID = "INDICE_EVENTO_ENTIDADEID";
    public static final String INDICE_EVENTO_ENTIDADENOME = "INDICE_EVENTO_ENTIDADENOME";
    public static final String INDICE_EVENTO_ENTIDADECADASTRONACIONAL = "INDICE_EVENTO_ENTIDADECADASTRONACIONAL";
    public static final String INDICE_EVENTO_NIVELID = "INDICE_EVENTO_NIVELID";
    public static final String INDICE_EVENTO_NIVELDESCRICAO = "INDICE_EVENTO_NIVELDESCRICAO";
    public static final String INDICE_EVENTO_TIPOID = "INDICE_EVENTO_TIPOID";
    public static final String INDICE_EVENTO_TIPODESCRICAO = "INDICE_EVENTO_TIPODESCRICAO"; 
    public static final String INDICE_EVENTO_VLCUSTOM_VALOR = "INDICE_EVENTO_VLCUSTOM_VALOR";
    public static final String INDICE_EVENTO_VLCUSTOM_CAMPOID = "INDICE_EVENTO_VLCUSTOM_CAMPOID";
    public static final String INDICE_EVENTO_VLCUSTOM_CAMPODESCRICAO = "INDICE_EVENTO_VLCUSTOM_CAMPODESCRICAO";
    public static final String INDICE_EVENTO_VLCUSTOM_CAMPOENTIDADEID = "INDICE_EVENTO_VLCUSTOM_CAMPOENTIDADEID";
    public static final String INDICE_EVENTO_TUDO = "INDICE_EVENTO_TUDO";
    
    public static String getLuceneIndexDirectory() {
        return getLuceneRootFolder() + LUCENE_DIRECTORY_INDEX;
    }

    public static String getLuceneRootFolder() {
        Configuracoes config;
        try {
            config = Configuracoes.load();
            return config.getLuceneIndices() + LUCENE_ROOT_FOLDER;
        } catch (Exception ex) {
            return LUCENE_ROOT_FOLDER;
        }
    }

    public static Version getLuceneVersion() {
        return Version.LUCENE_36;
    }
}

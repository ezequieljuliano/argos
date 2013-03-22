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

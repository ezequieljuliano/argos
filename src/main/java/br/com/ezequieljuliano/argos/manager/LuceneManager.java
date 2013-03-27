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
package br.com.ezequieljuliano.argos.manager;

import br.com.ezequieljuliano.argos.constant.Constantes;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Component
public class LuceneManager implements Serializable {

    private static final long serialVersionUID = 1L;
    /*
     * Faz um inject do diretório criado no AppConfig
     */
    @Autowired
    private Directory directory;

    /*
     * Após construir o objeto carrega os índices do disco para a memória
     */
    @PostConstruct
    public void init() {
        try {
            iniciarServico();
        } catch (IOException e) {
            Logger.getLogger(LuceneManager.class.getName()).log(Level.SEVERE,
                    null, e);
        }
    }

    /*
     * Antes de destruir faz backup dos registros em memória
     */
    @PreDestroy
    public void destroy() {
        backupMemoryToDisco();
    }

    public void backupMemoryToDisco() {
        try {
            //Abre o diretório do disco rígido
            File file = getNewFile();
            //Cria a variável baseado no diretório do disco rígido
            Directory disco = getDirDisco(file);
            //Copia da memória para o disco rígido
            backup(directory, disco);
        } catch (IOException ex) {
            Logger.getLogger(LuceneManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Inicia o serviço do Lucene carregando do disco rígido para a memória
     */
    private void iniciarServico() throws IOException {
        File file = getNewFile();
        Directory disco = getDirDisco(file);
        //Copia do disco rígido para  a memória
        backup(disco, directory);
    }

    /*
     * Faz backup de um diretório para outro
     */
    private void backup(Directory deDiretorio, Directory paraDiretorio) throws IOException {
        for (String file : deDiretorio.listAll()) {
            deDiretorio.copy(paraDiretorio, file, file, null);
        }
    }

    /*
     * Verifica se o sistema operacional é Windows
     */
    private static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf("win") >= 0);
    }

    /*
     * Abre o diretório do disco rígido
     */
    private Directory getDirDisco(File file) {
        if (file == null) {
            file = getNewFile();
        }
        if (isWindows()) {
            try {
                return FSDirectory.open(file); // Abre Diretorio em ambientes Windows
            } catch (IOException ex) {
                Logger.getLogger(LuceneManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                return NIOFSDirectory.open(file); // Abre Diretorio em ambientes Unix
            } catch (IOException ex) {
                Logger.getLogger(LuceneManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private File getNewFile() {
        //Abre ou cria o diretório em disco rígido
        File file = new File(Constantes.getLuceneIndexDirectory());
        //Se o diretório não exitir cria ele
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}

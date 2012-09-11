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
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@ApplicationScoped
public class LuceneManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Produces
    private Directory directory;

    @Inject
    public void init() {
        directory = new RAMDirectory();
        try {
            levantarServico();
        } catch (IOException e) {
            Logger.getLogger(LuceneManager.class.getName()).log(Level.SEVERE,
                    null, e);
        }
    }

    public void levantarServico() throws IOException {
        File file = new File(Constantes.getIndexDirectory());
        if (!file.exists()) {
            file.mkdirs();
        }
        Directory disco = FSDirectory.open(file);
        backup(disco, directory);
    }

    public void backup(Directory deDiretorio, Directory paraDiretoria) throws IOException {
        for (String file : deDiretorio.listAll()) {
            deDiretorio.copy(paraDiretoria, file, file);
        }
    }
}

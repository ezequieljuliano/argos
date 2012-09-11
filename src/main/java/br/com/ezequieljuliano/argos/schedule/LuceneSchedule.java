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
package br.com.ezequieljuliano.argos.schedule;

import br.com.ezequieljuliano.argos.constant.Constantes;
import br.com.ezequieljuliano.argos.manager.LuceneManager;
import java.io.File;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Singleton
public class LuceneSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private Directory directory;
    @Inject
    private LuceneManager luceneManager;

    @Schedule(minute = "*/1", hour = "*")
    public void reIndex() {

        try {
            File file = new File(Constantes.getIndexDirectory());
            Directory disco = FSDirectory.open(file);
            luceneManager.backup(directory, disco);
        } catch (Exception e) {
            Logger.getLogger(LuceneSchedule.class.getName()).log(Level.SEVERE,
                    null, e);
        }

    }
}

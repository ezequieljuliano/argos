/*
 * Copyright 2013 Ezequiel Juliano Müller - ezequieljuliano@gmail.com.
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

import br.com.ezequieljuliano.argos.domain.Level;
import br.com.ezequieljuliano.argos.exception.BusinessException;
import br.com.ezequieljuliano.argos.template.StantardBC;
import br.com.ezequieljuliano.argos.persistence.LevelDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.util.Strings;
import java.util.List;

@BusinessController
public class LevelBC extends StantardBC<Level, String, LevelDAO> {

    public List<Level> findByName(String name) {
        return getDelegate().findByName(name);
    }

    public Level findOneByName(String name) {
        return getDelegate().findOneByName(name);
    }

    public Level validateAndSave(Level obj) throws BusinessException {
        if (Strings.isEmpty(obj.getName())) {
            throw new BusinessException("O nível deve possuir um nome!");
        }
        return save(obj);
    }

}

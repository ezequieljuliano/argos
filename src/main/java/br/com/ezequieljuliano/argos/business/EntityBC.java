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

import br.com.ezequieljuliano.argos.domain.Entity;
import br.com.ezequieljuliano.argos.exception.BusinessException;
import br.com.ezequieljuliano.argos.template.StantardBC;
import br.com.ezequieljuliano.argos.persistence.EntityDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.util.Strings;
import java.util.List;

@BusinessController
public class EntityBC extends StantardBC<Entity, String, EntityDAO> {

    private boolean externalKeyAlreadyExists(String externalKey) {
        Entity entity = findByExternalKey(externalKey);
        return entity != null;
    }

    public List<Entity> findByName(String name) {
        return getDelegate().findByName(name);
    }

    public Entity findByExternalKey(String externalKey) {
        return getDelegate().findByExternalKey(externalKey);
    }

    public List<Entity> findByParent(Entity parent) {
        return getDelegate().findByParent(parent);
    }

    public Entity validateAndSave(Entity obj) throws BusinessException {
        if (Strings.isEmpty(obj.getName())) {
            throw new BusinessException("A entidade deve possuir um nome!");
        }
        if (Strings.isEmpty(obj.getExternalKey())) {
            throw new BusinessException("A entidade deve possuir uma chave externa!");
        }
        if (Strings.isEmpty(obj.getId())) {
            if (externalKeyAlreadyExists(obj.getExternalKey())) {
                throw new BusinessException("Esta chave externa já foi cadastrada no sistema!");
            }
        }
        if (!Strings.isEmpty(obj.getId()) && (obj.getParent() != null)) {
            if (obj.getId().equals(obj.getParent().getId())) {
                throw new BusinessException("Não é possível auto-referenciar uma entidade!");
            }
        }
        return save(obj);
    }

    public void validateAndDelete(String id) throws BusinessException {
        if (!findByParent(load(id)).isEmpty()) {
            throw new BusinessException("Esta entidade está vinculado a outra! Impossível deletar.");
        }
        getDelegate().delete(id);
    }

    public void validateAndDeleteObj(Entity obj) throws BusinessException {
        if (!findByParent(obj).isEmpty()) {
            throw new BusinessException("Esta entidade está vinculado a outra! Impossível deletar.");
        }
        getDelegate().deleteObj(obj);
    }

    public void activate(Entity obj) throws BusinessException {
        if (!Strings.isEmpty(obj.getId())) {
            try {
                obj.activate();
                save(obj);
            } catch (Exception e) {
                throw new BusinessException("Não foi possível ativar a entidade! Erro: " + e.getMessage());
            }
        } else {
            throw new BusinessException("Não foi possível ativar a entidade! Salve ela primeiro.");
        }
    }

    public void inactivate(Entity obj) throws BusinessException {
        if (!Strings.isEmpty(obj.getId())) {
            try {
                obj.inactivate();
                save(obj);
            } catch (Exception e) {
                throw new BusinessException("Não foi possível inativar a entidade! Erro: " + e.getMessage());
            }
        } else {
            throw new BusinessException("Não foi possível inativar a entidade! Salve ela primeiro.");
        }
    }

}

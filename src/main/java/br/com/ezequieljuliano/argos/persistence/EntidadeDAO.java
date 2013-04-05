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
package br.com.ezequieljuliano.argos.persistence;

import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.domain.UsuarioPerfil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Repository
public class EntidadeDAO extends GenericDAO<Entidade, String> {

    private static final long serialVersionUID = 1L;

    public Entidade findByCodigo(int codigo) {
        Query query = new Query(Criteria.where("codigo").is(codigo));
        return getMongoOperations().findOne(query, Entidade.class);
    }

    public Entidade findByCadastroNacional(String cadastroNacional) {
        Query query = new Query(Criteria.where("cadastroNacional").is(cadastroNacional));
        return getMongoOperations().findOne(query, Entidade.class);
    }

    public List<Entidade> findByNome(String nome) {
        Query query = new Query(Criteria.where("nome").regex(nome, "i"));
        return getMongoOperations().find(query, Entidade.class);
    }

    public List<Entidade> findByUsuario(Usuario usuario) {
        List<Entidade> entidades = new ArrayList<Entidade>();
        //Primeiro valida para verificar se o usuário não é administrador
        if (!usuario.getPerfil().equals(UsuarioPerfil.administrador)) {
            //Se for usuário normal valida se tem entidade relacionada 
            if (usuario.getEntidade() != null) {
                //Adiciona na lista por primeiro a entidade vinculada
                entidades.add(usuario.getEntidade());
                findEntidadesFilhas(usuario.getEntidade(), entidades);
            }
        } else {
            //Se for administrador retorna todas as entidades
            entidades = findAll();
        }
        return entidades;
    }

    private void findEntidadesFilhas(Entidade entidade, List<Entidade> returnList) {
        for (Entidade filha : entidade.getEntidadesFilhas()) {
            returnList.add(filha);
            findEntidadesFilhas(filha, returnList);
        }
    }
}

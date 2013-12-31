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

import br.com.ezequieljuliano.argos.config.ArgosMailConfig;
import br.com.ezequieljuliano.argos.domain.Profile;
import br.com.ezequieljuliano.argos.domain.User;
import br.com.ezequieljuliano.argos.exception.BusinessException;
import br.com.ezequieljuliano.argos.template.StantardBC;
import br.com.ezequieljuliano.argos.persistence.UserDAO;
import br.com.ezequieljuliano.argos.util.HashUtil;
import br.com.ezequieljuliano.argos.util.SendMail;
import br.com.ezequieljuliano.argos.util.SendMail.Server;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.util.Strings;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.apache.commons.mail.EmailException;

@BusinessController
public class UserBC extends StantardBC<User, String, UserDAO> {

    @Inject
    private ArgosMailConfig mailConfig;

    private boolean userNameAlreadyExists(String userName) {
        User user = findOneByUserName(userName);
        return user != null;
    }

    private boolean emailAlreadyExists(String email) {
        User user = findOneByEmail(email);
        return user != null;
    }

    private void sendEmailPassWord(User obj, String passWord) {
        try {
            new SendMail()
                    .using(new Server()
                            .hostName(mailConfig.getHostName())
                            .port(mailConfig.getPort())
                            .user(mailConfig.getUser())
                            .passWord(mailConfig.getPassWord())
                            .withSSL(mailConfig.isSSL()))
                    .from(mailConfig.getUser(), "Argos - Logging")
                    .to(obj.getEmail(), obj.getUserName())
                    .subject("Dados de acesso ao sistema de Logging Argos")
                    .message("Seu cadastro está concluído, para acessar o sistema use o usuário "
                            + obj.getUserName() + " e a senha " + passWord + " para enviar logs use a Chave Identificadora " + obj.getIdentifierKey())
                    .send();
        } catch (EmailException ex) {
            Logger.getLogger(UserBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<User> findByUserName(String userName) {
        return getDelegate().findByUserName(userName);
    }

    public User findOneByUserName(String userName) {
        return getDelegate().findOneByUserName(userName);
    }

    public List<User> findByEmail(String email) {
        return getDelegate().findByEmail(email);
    }

    public User findOneByEmail(String email) {
        return getDelegate().findOneByEmail(email);
    }

    public User findByIdentifierKey(String identifierKey) {
        return getDelegate().findByIdentifierKey(identifierKey);
    }

    public User findByUserNameAndPassWord(String userName, String passWord) {
        String passWordEnc = HashUtil.generateHash(passWord, HashUtil.SHA1);
        return getDelegate().findByUserNameAndPassWord(userName, passWordEnc);
    }

    @Override
    public User save(User obj) {
        if (Strings.isEmpty(obj.getIdentifierKey())) {
            obj.generateIdentifierKey();
        }
        if (Strings.isEmpty(obj.getId())) {
            String passWord = obj.getPassWord();
            obj.setPassWord(HashUtil.generateHash(passWord, HashUtil.SHA1));
            sendEmailPassWord(obj, passWord);
        }
        return super.save(obj);
    }

    public User validateAndSave(User obj) throws BusinessException {
        if (Strings.isEmpty(obj.getUserName())) {
            throw new BusinessException("O usuário deve possuir um username!");
        }
        if (Strings.isEmpty(obj.getEmail())) {
            throw new BusinessException("O usuário deve possuir um e-mail!");
        }
        if (Strings.isEmpty(obj.getPassWord())) {
            throw new BusinessException("O usuário deve possuir uma senha!");
        }
        if (obj.getProfiles().isEmpty()) {
            throw new BusinessException("O usuário deve possuir pelo menos um perfil!");
        }

        if (Strings.isEmpty(obj.getId())) {
            if (userNameAlreadyExists(obj.getUserName())) {
                throw new BusinessException("Este nome de usuário já existe no sistema!");
            }
            if (emailAlreadyExists(obj.getEmail())) {
                throw new BusinessException("Este e-mail já existe no sistema!");
            }
        }

        if ((obj.haveProfile(Profile.analyst)) && (obj.getEntity() == null)) {
            throw new BusinessException("Usuário analista necessita ter uma entidade!");
        }
        return save(obj);
    }

    public void activate(User obj) throws BusinessException {
        if (!Strings.isEmpty(obj.getId())) {
            try {
                obj.activate();
                save(obj);
            } catch (Exception e) {
                throw new BusinessException("Não foi possível ativar o usuário! Erro: " + e.getMessage());
            }
        } else {
            throw new BusinessException("Não foi possível ativar a usuário! Salve ele primeiro.");
        }
    }

    public void inactivate(User obj) throws BusinessException {
        if (!Strings.isEmpty(obj.getId())) {
            try {
                obj.inactivate();
                save(obj);
            } catch (Exception e) {
                throw new BusinessException("Não foi possível inativar o usuário! Erro: " + e.getMessage());
            }
        } else {
            throw new BusinessException("Não foi possível inativar o usuário! Salve ele primeiro.");
        }
    }

}

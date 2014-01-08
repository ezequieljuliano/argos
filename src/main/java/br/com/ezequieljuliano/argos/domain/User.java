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
package br.com.ezequieljuliano.argos.domain;

import br.com.ezequieljuliano.argos.util.HashUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = User.COLLECTION_NAME)
public class User implements Serializable {

    public static final String COLLECTION_NAME = "User";

    @Id
    private String id;

    @Indexed
    private String userName;

    @Indexed
    private String passWord;

    @Indexed
    private String email;

    @Indexed
    private String identifierKey;

    private List<Profile> profiles = new ArrayList<Profile>();

    private Situation situation = Situation.active;

    private final List<UserTerm> terms = new ArrayList<UserTerm>();

    @DBRef
    private Entity entity;

    public User() {

    }

    public User(String userName, String passWord, String email, String identifierKey, Profile profile, Situation situation, Entity entity) {
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.identifierKey = identifierKey;
        this.linkProfile(profile);
        this.situation = situation;
        this.entity = entity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentifierKey() {
        return identifierKey;
    }

    public void generateIdentifierKey() {
        this.identifierKey = HashUtil.generateHash(UUID.randomUUID().toString(), HashUtil.SHA1);
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public boolean isActive() {
        return (this.situation == Situation.active);
    }

    public List<UserTerm> getTerms() {
        return terms;
    }

    public void addTerm(LogicalOperator logicalOperator, Term term, FilterMatchMode filterMatchMode, String value) {
        UserTerm ut = new UserTerm(logicalOperator, term, filterMatchMode, value);
        terms.add(ut);
    }

    public void addTerm(UserTerm ut) {
        terms.add(ut);
    }

    public void removeTerm(UserTerm ut) {
        terms.remove(ut);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void linkProfile(Profile profile) {
        this.profiles.add(profile);
    }

    public void unLinkProfile(Profile profile) {
        this.profiles.remove(profile);
    }

    public boolean haveProfile(Profile profile) {
        for (Profile p : profiles) {
            if (p == profile) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public void activate() {
        this.situation = Situation.active;
    }

    public void inactivate() {
        this.situation = Situation.inactive;
    }

}

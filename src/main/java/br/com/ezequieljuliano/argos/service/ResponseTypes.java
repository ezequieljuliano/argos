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
package br.com.ezequieljuliano.argos.service;

public enum ResponseTypes {

    ok(1, "OK"),
    userNotFound(2, "Usuário não encontrado"),
    userInactive(3, "Usuário inativo"),
    entityNotFound(4, "Entidade não encontrada"),
    entityInactive(5, "Entidade inativa"),
    levelNotFound(6, "Nível não encontrado"),
    markerNotFound(7, "Marcador não encontrado"),
    error(8, "Erro desconhecido");

    private final int code;
    private final String reason;

    private ResponseTypes(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

}

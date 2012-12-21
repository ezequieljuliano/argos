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
package br.com.ezequieljuliano.argos.exception;

import br.com.ezequieljuliano.argos.service.EventoLogExceptionTipo;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public class LogServiceException extends Exception {

    private EventoLogExceptionTipo logEx;

    public LogServiceException(EventoLogExceptionTipo logEx) {
        super(logEx.getDescricao());
        this.logEx = logEx;
    }

    public EventoLogExceptionTipo getLogEx() {
        return logEx;
    }
}

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

import java.io.Serializable;

public class UserTerm implements Serializable {

    private LogicalOperator logicalOperator = LogicalOperator.andOperator;

    private Term term = Term.fullText;

    private FilterMatchMode filterMatchMode = FilterMatchMode.contains;

    private String value;

    public UserTerm() {

    }

    public UserTerm(LogicalOperator logicalOperator, Term term, FilterMatchMode filterMatchMode, String value) {
        this.logicalOperator = logicalOperator;
        this.term = term;
        this.filterMatchMode = filterMatchMode;
        this.value = value;
    }

    public LogicalOperator getLogicalOperator() {
        return logicalOperator;
    }

    public void setLogicalOperator(LogicalOperator logicalOperator) {
        this.logicalOperator = logicalOperator;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public FilterMatchMode getFilterMatchMode() {
        return filterMatchMode;
    }

    public void setFilterMatchMode(FilterMatchMode filterMatchMode) {
        this.filterMatchMode = filterMatchMode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

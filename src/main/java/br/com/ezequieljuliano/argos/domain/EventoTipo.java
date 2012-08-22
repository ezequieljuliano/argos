/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ezequieljuliano.argos.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Ezequiel
 */
@Entity
@Table(name = "EventoTipo", schema = "Argos@cassandra_pu")
public class EventoTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    
    @Column
    private String descricao;
    
    public EventoTipo() {
        super();
    }
    
    public EventoTipo(String descricao){
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}

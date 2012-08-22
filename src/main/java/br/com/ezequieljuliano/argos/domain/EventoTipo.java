/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ezequieljuliano.argos.domain;

import br.com.ezequieljuliano.argos.util.UniqId;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column(name = "id")
    private Long id = new Long(UniqId.getInstance().getUniqTime());
    
    @Column(name = "codigo")
    private int codigo;
    
    @Column(name = "descricao")
    private String descricao;

    public EventoTipo() {
        super();
    }

    public EventoTipo(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
   
}
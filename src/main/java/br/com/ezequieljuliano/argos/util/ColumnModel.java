package br.com.ezequieljuliano.argos.util;

import java.io.Serializable;

public class ColumnModel implements Serializable {
    
    private String property;
    private String header;

    public ColumnModel(String property, String header) {
        this.property = property;
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public String getProperty() {
        return property;
    }
    
}

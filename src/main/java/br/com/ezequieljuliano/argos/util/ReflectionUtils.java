package br.com.ezequieljuliano.argos.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReflectionUtils {
    public List extractFieldFromObjectList(List objects, String requiredField) {
        if(objects == null || objects.isEmpty())
            return new ArrayList(); 
        
        Class clazz = objects.get(0).getClass();
        List returnList = new ArrayList();
        for(Object o : objects) {
            for(Field field : clazz.getDeclaredFields()){
                field.setAccessible(true);
                if(field.getName().equals(requiredField)){
                    try {
                        returnList.add(field.get(o));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(ReflectionUtils.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(ReflectionUtils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    
            }
        }
        return returnList;
    }
}

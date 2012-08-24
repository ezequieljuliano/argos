package br.com.ezequieljuliano.argos.util;

import java.util.ArrayList;

public class StringUtil {
    
    public ArrayList<String> asArrayList(String content, String splitSeparator) {
        ArrayList list = new ArrayList(); 
        for(String str : content.split(splitSeparator))
            list.add(str);
        return list;
    }
}

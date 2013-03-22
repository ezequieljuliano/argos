package br.com.ezequieljuliano.argos.util;

import br.com.ezequieljuliano.argos.constant.Constantes;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

/**
 *
 * @author Ezequiel
 */
public class LuceneAnalyzerUtil {

    private LuceneAnalyzerUtil() {}
     
    
    public static StandardAnalyzer get(){
        return new StandardAnalyzer(Constantes.getLuceneVersion());
    }
}

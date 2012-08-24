package br.com.ezequieljuliano.argos.util;

import org.alfredlibrary.validadores.CNPJ;
import org.alfredlibrary.validadores.CPF;

public class CpfCnpjValidator {

    private String cpfCnpj;

    public CpfCnpjValidator(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
    
    public boolean isCnpj(String possibleCnpj){
        possibleCnpj = possibleCnpj.replaceAll("\\.", ""); 
        possibleCnpj = possibleCnpj.replaceAll("-", ""); 
        possibleCnpj = possibleCnpj.replaceAll("/", ""); 
        if(possibleCnpj.length() == 14)
            return true;
        return false;
    }
    
    public boolean isCpf(String possibleCpf){
        possibleCpf = possibleCpf.replaceAll("\\.", ""); 
        possibleCpf = possibleCpf.replaceAll("-", ""); 
        possibleCpf = possibleCpf.replaceAll("/", ""); 
        if(possibleCpf.length() == 11)
            return true;
        return false;
    }
    
    public void validate() throws CnpjValidationException, CpfValidationException{
        if(isCnpj(cpfCnpj) && !CNPJ.isValido(cpfCnpj))
            throw new CnpjValidationException("O CNPJ informado(" + cpfCnpj + ") não é válido.");
        
        if(isCpf(cpfCnpj) && !CPF.isValido(cpfCnpj))
            throw new CpfValidationException("O CPF informado (" + cpfCnpj + ") não é válido.");
    }
    
    public class CnpjValidationException extends Exception {

        public CnpjValidationException(String message) {
            super(message);
        }

    }
    
    public class CpfValidationException extends Exception {

        public CpfValidationException(String message) {
            super(message);
        }

    }
    
}

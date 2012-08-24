/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ezequieljuliano.argos.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author microsys
 */
public class Encrypt {

    public static String md5(String str) {
        String sen = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger hash = new BigInteger(1, md.digest(str.getBytes()));
        sen = hash.toString(16);
        return sen;
    }
}

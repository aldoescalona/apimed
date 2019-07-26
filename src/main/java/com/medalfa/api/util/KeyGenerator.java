/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medalfa.api.util;

import java.util.Random;

/**
 *
 * @author Aldo Escalona
 */
public class KeyGenerator {
    
    public static enum CHARSET{
        ALPHANUMERIC(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "w", "e", "r", "t", "y", "u", "p", "a", "s", "d", "f", "g", "h", "k", "", "z", "x", "c", "v", "b", "n", "m", "Q", "W", "E", "R", "T", "Y", "U", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"}), 
        ALPHA(new String[]{"w", "e", "r", "t", "y", "u", "p", "a", "s", "d", "f", "g", "h", "k", "", "z", "x", "c", "v", "b", "n", "m", "Q", "W", "E", "R", "T", "Y", "U", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"}), 
        ALPHANUMERIC_CAPITAL(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "Q", "W", "E", "R", "T", "Y", "U", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"}), 
        ALPHA_CAPITAL(new String[]{"Q", "W", "E", "R", "T", "Y", "U", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"}), 
        NUMERIC(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"});

        private CHARSET(String[] charset) {
            this.charset = charset;
        }

        public String[] getCharset() {
            return charset;
        }
        
        private String[] charset;
    }
    
    public static String generate(int size) {
        return generate(size, CHARSET.ALPHANUMERIC);
    }
    
    public static String generate(int size, CHARSET cs) {
        String p = "";
        for (int h = 0; h < size; h++) {
            Random rand = new Random();
            int i = rand.nextInt(cs.getCharset().length - 1);
            p += cs.getCharset()[i];
        }
        return p;
    }
    
}

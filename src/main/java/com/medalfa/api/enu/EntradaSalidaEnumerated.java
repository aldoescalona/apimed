/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medalfa.api.enu;

/**
 *
 * @author Aldo
 */
public class EntradaSalidaEnumerated {

        
    public static enum ES_TIPO {

        ENTRADA("Entrada"), SALIDA("Salida");

        private ES_TIPO(String title) {
            this.title = title;

        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
        private String title;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medalfa.api.v1.model;

/**
 *
 * @author Aldo Escalona
 */
public class Busqueda {
    
    private String termino;
    private String ordenCol;
    private boolean asc;

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getOrdenCol() {
        return ordenCol;
    }

    public void setOrdenCol(String ordenCol) {
        this.ordenCol = ordenCol;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }
    
    
    
}

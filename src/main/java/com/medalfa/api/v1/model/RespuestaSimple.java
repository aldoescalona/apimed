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
public class RespuestaSimple {
    
    private Boolean ok;
    private Integer codigo;
    private String mensaje;

    public RespuestaSimple() {
    }
    
    public RespuestaSimple(Boolean ok, Integer codigo, String mensaje) {
        this.ok = ok;
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
}

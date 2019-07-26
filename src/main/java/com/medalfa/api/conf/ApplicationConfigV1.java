/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medalfa.api.conf;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 *
 * @author Aldo Escalona
 */
@javax.ws.rs.ApplicationPath("v1")
public class ApplicationConfigV1 extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();

        resources.add(JacksonJsonProvider.class);

        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.medalfa.api.security.CrossOriginResourceSharingFilter.class);
        resources.add(com.medalfa.api.security.RestSecurityFilter.class);
        resources.add(mx.historicos.api.v1.rest.AuthREST.class);
        resources.add(mx.historicos.api.v1.rest.EntradaSalidaREST.class);
        resources.add(mx.historicos.api.v1.rest.ProductoREST.class);
        resources.add(mx.historicos.api.v1.rest.UsuarioREST.class);
    }

}
